package com.electricity.project.predictionmodule.powerstation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableSolarPanelDTO.class)
@JsonDeserialize(as = ImmutableSolarPanelDTO.class)
public interface SolarPanelDTO extends PowerStationDTO {

    static ImmutableSolarPanelDTO.Builder builder() {
        return ImmutableSolarPanelDTO.builder();
    }

    @JsonProperty(value = "optimalTemperature", required = true)
    double getOptimalTemperature();
}
