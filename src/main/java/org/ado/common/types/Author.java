package org.ado.common.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


/***
 * Represents the identity of a user object.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author extends BaseAbstractMethod {

    private String displayName;

    private String url;

    private Object _links;

    private String id;

    private String uniqueName;

    private String imageUrl;

    private String descriptor;

}
