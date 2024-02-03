package org.deeplearning.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.deeplearning.controls.BTCAIControl;
import org.finance.models.Finance;

@ApplicationScoped
public class BTCAIService {

    @Inject
    private BTCAIControl aiControl;

    public void trainModel() {
        aiControl.train();
    }

    public Finance makePrediction(String dateString) {
        return aiControl.makePrediction(dateString);
    }
}