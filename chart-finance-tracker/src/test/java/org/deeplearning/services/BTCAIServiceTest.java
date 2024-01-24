package org.deeplearning.services;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BTCAIServiceTest {

    @Inject
    private BTCAIService aiService;

    @Test
    void trainModel() {
        String date = "23.01.2024";
        aiService.trainModel();
        String prediction = aiService.makePrediction(date);
        System.out.println("Prediction of " + date + " is " + prediction);
    }
}