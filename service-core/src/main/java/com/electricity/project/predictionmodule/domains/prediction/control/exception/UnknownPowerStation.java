package com.electricity.project.predictionmodule.domains.prediction.control.exception;

import com.electricity.project.predictionmodule.powerstation.PowerStationDTO;

public class UnknownPowerStation extends RuntimeException {
    public UnknownPowerStation(PowerStationDTO powerStation) {
        super("Unknown power station: %s".formatted(powerStation));
    }
}
