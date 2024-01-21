package com.electricity.project.predictionmodule.prediction;

import com.electricity.project.predictionmodule.powerstation.PowerStationDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;

public abstract class PowerStationPredictor<T extends PowerStationDTO> {
    public abstract long makePrediction(T powerStation, ForecastHourWeatherDTO weatherForecast);

    protected double convertFromWsToMWh(double watsPerSecond) {
        return watsPerSecond * 60 * 60 / 1000000;
    }

    protected static double convertFromMillibarsToPascals(double millibars) {
        return millibars * 100;
    }

    protected static double convertFromCelsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    protected static double convertFromKphToMps(double kilometersPerHour) {
        return kilometersPerHour * 1000 / 3600;
    }
}
