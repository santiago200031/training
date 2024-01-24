package org.finance.controllers.priceDifferences;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.automation.AutomationRobotBTC;
import org.finance.controllers.PriceDifferenceController;
import org.finance.models.Finance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class PriceDifferenceBTCController extends PriceDifferenceController {

    //TODO Move this to application.yaml and get with @ConfigProperty
    private static final boolean HANDLE_BUY_SELL_ACTION = false;
    private static final int DIFFERENCE_TO_PREPARE_HANDLE_ACTION = 30;
    private static final int DIFFERENCE_TO_HANDLE_ACTION = 40;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    private AutomationRobotBTC robotBTC;

    private float previousGauge = 0f;

    private int gauge;

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected int getGauge() {
        return this.gauge;
    }

    @Override
    protected void setGauge(int gauge) {
        this.gauge = gauge;
    }

    public float getDifferencePrice(Finance currentFinance, Finance previousFinance) {
        if (!HANDLE_BUY_SELL_ACTION) {
            return super.getDifferencePrice(currentFinance, previousFinance);
        }

        if (shouldPrepareHandleAction()) {
            robotBTC.doPreparationHandleAction();
        }

        if (this.gauge > this.previousGauge + DIFFERENCE_TO_HANDLE_ACTION) {
            this.previousGauge = this.gauge;
            LOGGER.info("Buying bitcoin...");
            robotBTC.doBuy();

        } else if (this.gauge < this.previousGauge - DIFFERENCE_TO_HANDLE_ACTION) {
            this.previousGauge = this.gauge;
            LOGGER.info("Selling bitcoin...");
            robotBTC.doSell();
        }
        return super.getDifferencePrice(currentFinance, previousFinance);
    }

    private boolean shouldPrepareHandleAction() {
        return this.gauge > this.previousGauge + DIFFERENCE_TO_PREPARE_HANDLE_ACTION ||
               this.gauge < this.previousGauge - DIFFERENCE_TO_PREPARE_HANDLE_ACTION;
    }
}