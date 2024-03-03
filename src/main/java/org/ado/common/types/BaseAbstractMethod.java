package org.ado.common.types;


import com.fasterxml.jackson.databind.JsonNode;
import org.ado.exceptions.AdoException;
import org.ado.helpers.JsonMapper;
import org.ado.utils.RestClient;

public abstract class BaseAbstractMethod {
    private final JsonMapper MAPPER = new JsonMapper();

    /**
     * toString() method to convert the POJO to string.
     * @return String result.
     */
    public String toString() {
        String res = null;

        try {
            res = this.MAPPER.convertToString(this);
        } catch (AdoException ignored) { }

        return res;
    }

    /**
     * Helper method to convert the string to Json node.
     * @return JsonNode object.
     */
    public JsonNode toJsonTree() {
        JsonNode res = null;
        try {
            res = this.MAPPER.convertToJson(this.toString());
        } catch (AdoException e) { }

        return res;
    }

    /**
     * Retrieves the continuation token from response header.
     * If the header value "x-ms-continuationtoken"
     * is present then it will be returned or an empty string will be returned.
     * @return String value.
     */
    public String getContinuationToken() {
        return RestClient.Metadata.getResponseHeader("x-ms-continuationtoken");
    }
}
