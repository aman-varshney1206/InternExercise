package org.ado.helpers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/***
 * Url helper class to encode the special characters in the url.
 */
public class URLHelper {
    /***
     * Encode the space character in the given string
     */
    public static String encodeSpace(String s) {
        if (s.contains(" ")) return s.replace(" ", "%20");
        return s;
    }

    /***
     * Combine the two functionality above: encode and replace space characters after the fact with '%20'
     */
    public static String encodeSpecialWithSpace(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8).replace("+", "%20");
    }
}