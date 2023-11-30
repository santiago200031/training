package org.finance.controllers;

import org.finance.models.Finance;
import org.slf4j.Logger;

public abstract class PriceDifferenceController {

    protected abstract Logger getLogger();

    protected abstract float getGauge();

    protected abstract void setGauge(float gauge);

    public float getDifferencePrice(Finance currentFinance, Finance previousFinance) {
        float gauge = getGauge();

        float currentPrice = currentFinance.getPrice();
        float previousPrice = previousFinance.getPrice();

        float difference = currentPrice - previousPrice;

        if (currentPrice > previousPrice) {
            setGauge(gauge + 1);

        } else if (currentPrice < previousPrice) {
            setGauge(gauge - 1);

        }
        getLogger().info("Gauge: {} ", getGauge());
        return difference;
    }
}
