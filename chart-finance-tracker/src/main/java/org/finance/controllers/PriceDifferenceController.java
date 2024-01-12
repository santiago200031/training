package org.finance.controllers;

import org.finance.models.Finance;
import org.slf4j.Logger;

public abstract class PriceDifferenceController {

    protected abstract Logger getLogger();

    protected abstract int getGauge();

    protected abstract void setGauge(int gauge);

    public float getDifferencePrice(Finance currentFinance, Finance previousFinance) {
        int gauge = getGauge();

        float currentPrice = currentFinance.getPrice();
        float previousPrice = previousFinance.getPrice();

        float difference = currentPrice - previousPrice;
        if (difference > 0) {
            setGauge(gauge + 1);
        }

        if (difference < 0) {
            setGauge(gauge - 1);
        }
        getLogger().info("Gauge: {} ", getGauge());
        return difference;
    }
}
