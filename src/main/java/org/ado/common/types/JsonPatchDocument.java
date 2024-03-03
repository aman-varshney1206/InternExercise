package org.ado.common.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.enums.PatchOperation;

/**
 * Model for path operation.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPatchDocument extends BaseAbstractMethod{

    private String from;

    @JsonProperty("op")
    private PatchOperation operation;

    private String path;

    private Object value;

}