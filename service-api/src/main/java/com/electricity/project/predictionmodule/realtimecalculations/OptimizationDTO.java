package com.electricity.project.predictionmodule.realtimecalculations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableOptimizationDTO.class)
@JsonDeserialize(as = ImmutableOptimizationDTO.class)
public interface OptimizationDTO {

    static ImmutableOptimizationDTO.Builder builder() {
        return ImmutableOptimizationDTO.builder();
    }

    @JsonProperty(value = "IpsToTurnOff", required = true)
    List<String> getIpsToTurnOff();

    @JsonProperty(value = "IpsToTurnOn", required = true)
    List<String> getIpsToTurnOn();
}
