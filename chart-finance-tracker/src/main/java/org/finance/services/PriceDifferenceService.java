package org.finance.services;

import org.finance.models.Finance;

public abstract class PriceDifferenceService {
    public abstract float getDifferencePrice(Finance currentFinance, Finance previousFinance);
}