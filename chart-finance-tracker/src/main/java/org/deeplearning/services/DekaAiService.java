package org.deeplearning.services;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.deeplearning.controls.DekaAiControl;

@ApplicationScoped
public class DekaAiService {

    @Inject
    private DekaAiControl aiControl;

    public void trainModel() {
        aiControl.trainDeka();
    }

    public String makePrediction(String timestampString) {
        return aiControl.predictDeka(timestampString);
    }
}