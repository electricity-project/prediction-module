package com.electricity.project.predictionmodule.realtimecalculations;

import com.electricity.project.predictionmodule.powerstation.PowerStationState;
import com.electricity.project.predictionmodule.powerstation.PowerStationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Set;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutablePowerStationFilterDTO.class)
@JsonDeserialize(as = ImmutablePowerStationFilterDTO.class)
public interface PowerStationFilterDTO {

    static ImmutablePowerStationFilterDTO.Builder builder() {
        return ImmutablePowerStationFilterDTO.builder();
    }

    @JsonProperty(value = "ipv6Patterns", required = true)
    Set<String> getIpv6Patterns();

    @JsonProperty(value = "statePatterns", required = true)
    Set<PowerStationState> getStatePatterns();

    @JsonProperty(value = "typePatterns", required = true)
    Set<PowerStationType> getTypePatterns();
}
