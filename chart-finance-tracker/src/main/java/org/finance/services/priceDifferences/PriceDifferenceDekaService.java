package org.finance.services.priceDifferences;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.controllers.priceDifferences.PriceDifferenceDekaController;
import org.finance.models.Finance;
import org.finance.services.PriceDifferenceService;

@ApplicationScoped
public class PriceDifferenceDekaService extends PriceDifferenceService {
    @Inject
    PriceDifferenceDekaController priceDifferenceController;

    public float getDifferencePrice(Finance currentDeka, Finance previousDeka) {
        return priceDifferenceController.getDifferencePrice(currentDeka, previousDeka);
    }
}