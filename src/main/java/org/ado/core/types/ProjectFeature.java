package org.ado.core.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

import java.util.Optional;

/***
 * data object representing project feature
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectFeature extends BaseAbstractMethod {

    @JsonProperty("featureId")
    String featureId;

    @JsonProperty("state")
    String state;

    @JsonProperty("scope")
    Scope scope;

    public Optional<Boolean> getStateAsBoolean() {
        if (state == null) {
            return Optional.empty();
        }
        if (state.equals("enabled") || state.equals("1")) {
            return Optional.of(Boolean.TRUE);
        } else if (state.equals("disabled") || state.equals("0")) {
            return Optional.of(Boolean.FALSE);
        }
        return Optional.empty();
    }

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Scope extends BaseAbstractMethod {
        /***
         * scope value. Undocumented in Azdo api
         */
        @JsonProperty("userScoped")
        boolean userScoped;
        /***
         * scope setting. Undocumented in Azdo api
         */
        @JsonProperty("settingScope")
        String settingScope;
    }
}