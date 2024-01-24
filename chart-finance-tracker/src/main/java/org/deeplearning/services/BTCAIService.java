package org.deeplearning.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.deeplearning.controls.BTCAIControl;

@ApplicationScoped
public class BTCAIService {

    @Inject
    private BTCAIControl aiControl;

    public void trainModel() {
        aiControl.train();
    }

    public String makePrediction(String dateString) {
        return aiControl.makePrediction(dateString);
    }
}