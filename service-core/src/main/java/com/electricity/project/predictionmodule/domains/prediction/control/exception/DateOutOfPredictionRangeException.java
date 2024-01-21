package com.electricity.project.predictionmodule.domains.prediction.control.exception;

import java.time.LocalDate;

public class DateOutOfPredictionRangeException extends RuntimeException {
    public DateOutOfPredictionRangeException(LocalDate date) {
        super("Date out of prediction range: %s".formatted(date));
    }
}
