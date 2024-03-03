package org.ado.core.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Reference for an async operation.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationReference extends BaseAbstractMethod {

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("url")
    private String url;

}