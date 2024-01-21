package com.electricity.project.predictionmodule.powerstation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableWindTurbineDTO.class)
@JsonDeserialize(as = ImmutableWindTurbineDTO.class)
public interface WindTurbineDTO extends PowerStationDTO {

    static ImmutableWindTurbineDTO.Builder builder() {
        return ImmutableWindTurbineDTO.builder();
    }

    @JsonProperty(value = "bladeLength", required = true)
    Long getBladeLength();
}
