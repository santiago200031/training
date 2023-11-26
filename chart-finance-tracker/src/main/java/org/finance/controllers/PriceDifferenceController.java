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

        if (difference > 0f) {
            setGauge(gauge + 1);

            getLogger().info("Price is increasing...");
            getLogger().info("Increased by " + difference + " EUR");

        } else if (difference < 0f) {
            setGauge(gauge - 1);

            getLogger().info("Price is decreasing...");
            getLogger().info("Decreased by " + difference + " EUR");

        }
        getLogger().debug("Gauge " + getGauge());
        return difference;
    }
}
