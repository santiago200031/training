package org.deeplearning.services;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.deeplearning.controls.DekaAIControl;

@ApplicationScoped
public class DekaAIService {

    @Inject
    private DekaAIControl aiControl;

    public void trainModel() {
        aiControl.train();
    }

    public String makePrediction(String dateString) {
        return aiControl.makePrediction(dateString);
    }
}