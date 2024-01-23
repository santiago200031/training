package org.finance.controllers.priceDifferences;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.finance.controllers.PriceDifferenceController;
import org.finance.models.Finance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class PriceDifferenceDekaController extends PriceDifferenceController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Setter(AccessLevel.PROTECTED)
    @Getter(AccessLevel.PUBLIC)
    private int gauge;

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    public float getDifferencePrice(Finance currentFinance, Finance previousFinance) {
        return super.getDifferencePrice(currentFinance, previousFinance);
    }
}