package com.electricity.project.predictionmodule.prediction;

import com.electricity.project.predictionmodule.powerstation.PowerStationState;
import lombok.Getter;

import java.util.Set;

@Getter
public enum PowerStationsScope {
    ALL_POWER_STATIONS(Set.of(PowerStationState.values())),
    WORKING_POWER_STATIONS(Set.of(PowerStationState.WORKING, PowerStationState.STOPPED, PowerStationState.STOPPED_BY_USER));

    private final Set<PowerStationState> states;

    PowerStationsScope(Set<PowerStationState> states) {
        this.states = states;
    }
}
