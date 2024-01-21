package com.electricity.project.predictionmodule.prediction;

import com.electricity.project.predictionmodule.powerstation.WindTurbineDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;
import org.springframework.stereotype.Component;

@Component
public class WindTurbinePredictor implements PowerStationPredictor<WindTurbineDTO> {

    @Override
    public long makePrediction(WindTurbineDTO powerStation, ForecastHourWeatherDTO weatherForecast) {
        // TODO
        return (long) (Math.random() * 1000);
    }
}
