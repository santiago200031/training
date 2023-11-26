package org.finance.controllers.priceDifferences;

import jakarta.enterprise.context.ApplicationScoped;

import org.finance.controllers.PriceDifferenceController;
import org.finance.models.Finance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class PriceDifferenceBTCController extends PriceDifferenceController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private float gauge;


    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected float getGauge() {
        return this.gauge;
    }

    @Override
    protected void setGauge(float gauge) {
        this.gauge = gauge;
    }

    public float getDifferencePrice(Finance currentFinance, Finance previousFinance) {
        return super.getDifferencePrice(currentFinance, previousFinance);
    }
}