package com.electricity.project.predictionmodule.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableErrorDTO.class)
@JsonDeserialize(as = ImmutableErrorDTO.class)
public interface ErrorDTO {
    static ImmutableErrorDTO.Builder builder() {
        return ImmutableErrorDTO.builder();
    }

    @JsonProperty(value = "error", required = true)
    String getError();
}
