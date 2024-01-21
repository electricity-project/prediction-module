package com.electricity.project.predictionmodule.realtimecalculations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableOptimizeProductionDTO.class)
@JsonDeserialize(as = ImmutableOptimizeProductionDTO.class)
public interface OptimizeProductionDTO {

    static ImmutableOptimizeProductionDTO.Builder builder() {
        return ImmutableOptimizeProductionDTO.builder();
    }

    @JsonProperty(value = "powerProductions", required = true)
    List<PowerProductionDTO> getPowerProductions();

    @JsonProperty(value = "powerStationFilter", required = true)
    PowerStationFilterDTO getPowerStationFilter();
}
