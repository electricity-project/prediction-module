package com.electricity.project.predictionmodule.domains.prediction.boundary;

import com.electricity.project.predictionmodule.domains.prediction.control.PredictionService;
import com.electricity.project.predictionmodule.error.ErrorDTO;
import com.electricity.project.predictionmodule.prediction.PredictionDTO;
import com.electricity.project.predictionmodule.prediction.PredictionResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/predict")
@RequiredArgsConstructor
public class PredictionResource {
    private final PredictionService predictionService;

    @PostMapping
    public ResponseEntity<List<PredictionResultDTO>> makePredictionFor(@RequestBody PredictionDTO predictionDto) {
        return ResponseEntity.ok(predictionService.makePredictionFor(predictionDto));
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorDTO> handleRestExceptions(RuntimeException exception) {
        log.error("Exception occurred", exception);
        return ResponseEntity
                .internalServerError()
                .body(ErrorDTO.builder()
                        .error(exception.getMessage())
                        .build());
    }
}
