package org.ado.utils;

import org.ado.enums.CustomHeader;
import org.ado.enums.RequestMethod;
import org.ado.exceptions.AdoException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

/**
 * BaseRestClient that encapsulates the logic for managing request and response for REST API.
 */
public abstract class BaseRestClient {
    private static final String AUTHORIZATION = "Authorization";
    private static HttpClient.Redirect REDIRECT_POLICY = HttpClient.Redirect.NORMAL;
    private static final HttpClient CLIENT = HttpClient.newBuilder().followRedirects(REDIRECT_POLICY).build();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /**
     * Encodes the personal access token to base 64
     */
    private static String encodePersonalAccessToken(String token) {
        return "Basic " +
                Base64.getEncoder().encodeToString(("" + ":" + token).getBytes());
    }

    /***
     * Http request builder
     */
    private static HttpRequest.Builder build(String requestUrl, String token) {
        if (token == null)
            return HttpRequest
                    .newBuilder()
                    .uri(URI.create(requestUrl));

        return HttpRequest
                .newBuilder()
                .uri(URI.create(requestUrl))
                .setHeader(AUTHORIZATION, encodePersonalAccessToken(token));
    }

    /***
     * Response from API for the given request
     */
    private static <T> CompletableFuture<HttpResponse<T>> request(HttpRequest r, HttpResponse.BodyHandler<T> handler,
                                                                  boolean callback) {
        if (callback)  return CLIENT.sendAsync(r, handler);
        return HTTP_CLIENT.sendAsync(r, handler);
    }

    /**
     * Get the current redirect policy.
     */
    public static HttpClient.Redirect getRedirectPolicy() {
        return REDIRECT_POLICY;
    }

    /**
     * Set the current redirect policy.
     */
    public static void setRedirectPolicy(HttpClient.Redirect redirectPolicy) {
        REDIRECT_POLICY = redirectPolicy;
    }

    /**
     * Sends a GET request to REST API with basic authentication
     */
    public static <T> CompletableFuture<HttpResponse<T>> get(String requestUrl, String token, HttpResponse.BodyHandler<T> handler,
                                                             CustomHeader contentType, boolean callback) {
        return request(build(requestUrl, token).GET().header(contentType.getName(), contentType.getValue()).build(),
                handler, callback);
    }

    /***
     * Sends a POST request to REST API with basic authentication, content length of the request and request body
     */
    public static <T> CompletableFuture<HttpResponse<T>> post(String requestUrl, String token, HttpRequest.BodyPublisher publisher,
                                                              HttpResponse.BodyHandler<T> handler, CustomHeader contentType,
                                                              boolean callback) throws AdoException {
        return request(
                build(requestUrl, token)
                        .POST(publisher)
                        .header(contentType.getName(), contentType.getValue())
                        .build(), handler, callback);
    }

    /**
     * Sends a PATCH request to REST API with basic authentication and request body
     */
    public static <T> CompletableFuture<HttpResponse<T>> patch(String requestUrl, String token, HttpRequest.BodyPublisher publisher,
                                                               HttpResponse.BodyHandler<T> handler, CustomHeader contentType,
                                                               boolean callback) throws AdoException {
        return request(
                build(requestUrl, token)
                        .method(RequestMethod.PATCH.name(), publisher)
                        .header(contentType.getName(), contentType.getValue())
                        .build(), handler, callback);
    }

    /***
     * Sends a PUT request to REST API with basic authentication and request body
     */
    public static <T> CompletableFuture<HttpResponse<T>> put(String requestUrl, String token, HttpRequest.BodyPublisher publisher,
                                                             HttpResponse.BodyHandler<T> handler, CustomHeader contentType,
                                                             boolean callback) throws AdoException {
        return request(
                build(requestUrl, token)
                        .PUT(publisher)
                        .header(contentType.getName(), contentType.getValue())
                        .build(), handler, callback);
    }

    /**
     * Sends a DELETE request to REST API with basic authentication
     */
    public static <T> CompletableFuture<HttpResponse<T>> delete(String requestUrl, String token, HttpResponse.BodyHandler<T> handler) {
        return request(build(requestUrl, token).DELETE().build(), handler, false);
    }

    /**
     * Manages the request method and response from REST API.
     */
    public static <T> CompletableFuture<HttpResponse<T>> response(RequestMethod requestMethod, String requestUrl,
                                                                  String token, HttpRequest.BodyPublisher publisher,
                                                                  HttpResponse.BodyHandler<T> handler,
                                                                  CustomHeader contentType,
                                                                  boolean callback) throws AdoException {
        if (contentType == null) contentType = CustomHeader.JSON;

        if (requestMethod == RequestMethod.GET)
            return get(requestUrl, token, handler, contentType, callback);

        if (requestMethod == RequestMethod.POST)
            return post(requestUrl, token, publisher, handler, contentType, callback);

        if (requestMethod == RequestMethod.PATCH)
            return patch(requestUrl, token, publisher, handler, contentType, callback);

        if (requestMethod == RequestMethod.PUT)
            return put(requestUrl, token, publisher, handler, contentType, callback);

        if (requestMethod == RequestMethod.DELETE)
            return delete(requestUrl, token, handler);

        return null;
    }

    /**
     * Helper method to create the request with request url and access token.
     */
    protected static HttpRequest.Builder getBuilder(String requestUrl, String token) {
        return build(requestUrl, token);
    }

    /**
     * Helper method to get the response based on built http request.
     */
    protected static <T> CompletableFuture<HttpResponse<T>> getResponse(HttpRequest r, HttpResponse.BodyHandler<T> handler,
                                                                        boolean callback) {
        if (callback)  return CLIENT.sendAsync(r, handler);
        return HTTP_CLIENT.sendAsync(r, handler);
    }

}