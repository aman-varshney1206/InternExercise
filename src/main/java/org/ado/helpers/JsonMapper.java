package org.ado.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ado.enums.ApiExceptionTypes;
import org.ado.exceptions.AdoException;

import java.io.File;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/***
 * Helper class to transform json string to POJO and vice versa
 */
public class JsonMapper extends ObjectMapper {

    public JsonMapper() {
        super();

        // Do not include NULL values in serialized JSON messages
        this.setSerializationInclusion(Include.NON_NULL);
    }

    /***
     * Converts the object to string
     */
    public String convertToString(Object value) throws AdoException {
        try {
            return this.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new AdoException(ApiExceptionTypes.StringValueParsingException.toString(), e.getMessage());
        }
    }

    /***
     * Handles the deserialization of json string to object of given type.
     */
    public <T> T mapJsonResponse(String content, Class<T> valueType) throws AdoException {
        try {
            if (content.contains("innerException"))
                throw new AdoException(this.readValue(content, ApiException.class).getTypeKey(), this.readValue(content, ApiException.class).getMessage());
            if (content.contains("The request is invalid."))
                throw new AdoException();
            if (content.contains("Object moved"))
                throw new AdoException(ApiExceptionTypes.InvalidPersonalAccessTokenException.toString(), "Personal access token passed is invalid; Pass the valid token and try again.");
            return this.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new AdoException(ApiExceptionTypes.ApiResponseParsingException.toString(), e.getMessage());
        }
    }


    /***
     * Handles the deserialization of json string to given object from a file.
     */
    public <T> T mapJsonFromFile(File src, Class<T> valueType) throws AdoException {
        try {
            return this.readValue(src, valueType);
        } catch (Exception e) {
            throw new AdoException(ApiExceptionTypes.FileContentParsingException.toString(), e.getMessage());
        }
    }

    /**
     * Converts a string response to Json string.
     */
    public JsonNode convertToJson(String jsonString) throws AdoException {
        try {
            return this.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new AdoException(ApiExceptionTypes.StringValueParsingException.name(), e.getMessage());
        }
    }
}
