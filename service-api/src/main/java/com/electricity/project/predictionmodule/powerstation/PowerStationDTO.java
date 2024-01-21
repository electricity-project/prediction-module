package com.electricity.project.predictionmodule.powerstation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.ZonedDateTime;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutablePowerStationDTO.class)
@JsonDeserialize(as = ImmutablePowerStationDTO.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WindTurbineDTO.class),
        @JsonSubTypes.Type(value = SolarPanelDTO.class)
})
public interface PowerStationDTO {

    static ImmutablePowerStationDTO.Builder builder() {
        return ImmutablePowerStationDTO.builder();
    }

    @JsonProperty(value = "id")
    Long getId();

    @JsonProperty(value = "ipv6", required = true)
    String getIpv6Address();

    @JsonProperty(value = "state", required = true)
    PowerStationState getState();

    @JsonProperty(value = "creationTime", required = true)
    ZonedDateTime getCreationTime();

    @JsonProperty(value = "maxPower", required = true)
    double getMaxPower();
}
