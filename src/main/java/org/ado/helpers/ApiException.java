package org.ado.helpers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/***
 * Represents API inner exception
 */

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiException {

    @JsonProperty("$id")
    private int id;

    @JsonProperty("innerException")
    private String innerException;

    @JsonProperty("message")
    private String message;

    @JsonProperty("typeName")
    private String typeName;

    @JsonProperty("typeKey")
    private String typeKey;

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("eventId")
    private int eventId;

    @Override
    public String toString() {
        return "ApiException{" +
                "id=" + id +
                ", innerException='" + innerException + '\'' +
                ", message='" + message + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeKey='" + typeKey + '\'' +
                ", errorCode=" + errorCode +
                ", eventId=" + eventId +
                '}';
    }
}