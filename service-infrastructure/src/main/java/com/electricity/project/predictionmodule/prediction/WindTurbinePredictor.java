package com.electricity.project.predictionmodule.prediction;

import com.electricity.project.predictionmodule.powerstation.WindTurbineDTO;
import com.electricity.project.predictionmodule.weather.ForecastHourWeatherDTO;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class WindTurbinePredictor extends PowerStationPredictor<WindTurbineDTO> {
    private static final double RD = 287.058;
    private static final double RV = 461.495;
    private static final double MIN_EFFECTIVITY_COEFFICIENT = 5.0;
    private static final double MEAN_MAX_EFFECTIVITY_COEFFICIENT = 15.0;
    private static final double MEAN_POWER_COEFFICIENT = 30.0;
    private static final Random RANDOM = new Random();
    public static final int POWER_COEFFICIENT_STDDEV = 5;
    public static final double MAX_EFFECTIVITY_COEFFICIENT_STDDEV = 2.5;

    @Override
    public long makePrediction(WindTurbineDTO windTurbine, ForecastHourWeatherDTO weatherForecast) {
        return (long) convertFromWsToMWh(countPowerProductionPerSecond(windTurbine, weatherForecast));
    }

    private static double countPowerProductionPerSecond(WindTurbineDTO windTurbine, ForecastHourWeatherDTO weatherForecast) {
        return 0.5 * countAirDensity(weatherForecast) * countWindEnergy(weatherForecast) * countAreaOfTurbine(windTurbine);
    }

    private static double countAirDensity(ForecastHourWeatherDTO weatherForecast) {
        double p1 = 6.1078 * Math.pow(10.0, (7.5 * weatherForecast.getCelsiusTemperature()) / (weatherForecast.getCelsiusTemperature() + 237.3));
        double pv = (double) weatherForecast.getPercentageHumidity() / 100 * p1;
        double pd = convertFromMillibarsToPascals(weatherForecast.getMbPressure()) - pv;
        return (pd / (RD * convertFromCelsiusToKelvin(weatherForecast.getCelsiusTemperature()))) + (pv / (RV * convertFromCelsiusToKelvin(weatherForecast.getCelsiusTemperature())));
    }

    private static double countWindEnergy(ForecastHourWeatherDTO weatherForecast) {
        double windSpeed = convertFromKphToMps(weatherForecast.getKphWindSpeed());

        double powerCoefficient = RANDOM.nextGaussian(MEAN_POWER_COEFFICIENT, POWER_COEFFICIENT_STDDEV);
        double maxEffectivityCoefficient = RANDOM.nextGaussian(MEAN_MAX_EFFECTIVITY_COEFFICIENT, MAX_EFFECTIVITY_COEFFICIENT_STDDEV);

        if (MIN_EFFECTIVITY_COEFFICIENT > windSpeed) {
            return 0.0;
        } else if (maxEffectivityCoefficient < windSpeed) {
            return Math.pow(maxEffectivityCoefficient, 3) * powerCoefficient / 100;
        } else {
            return Math.pow(windSpeed, 3) * powerCoefficient / 100;
        }
    }

    private static double countAreaOfTurbine(WindTurbineDTO windTurbine) {
        return Math.PI * Math.pow(windTurbine.getBladeLength(), 2);
    }
}
