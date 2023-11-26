package org.finance.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.controllers.FinanceController;
import org.finance.models.Finance;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FinanceService {

    @Inject
    FinanceController financeController;

    private Finance previousFinanceDeka;

    private Finance previousFinanceBTC;

    public Finance getDekaGlobalChampions(UUID activityId) {
        return financeController.getDekaGlobalChampions(activityId);
    }

    public Finance getBTC(UUID activityId) {
        return financeController.getBTC(activityId);
    }

    public List<Finance> getFinances(UUID activityId) {
        return financeController.getFinances(activityId);
    }

    public Finance getLastDekaFinance() {
        return financeController.getLastDekaFinance();
    }

    public boolean isDekaDataEmpty() {
        return financeController.isDekaDataEmpty();
    }

    public boolean isBTCDataEmpty() {
        return financeController.isBTCDataEmpty();
    }

    public  Finance getPreviousFinanceDeka() {
        if (this.previousFinanceDeka == null) {
            Finance lastDekaFinance = this.getLastDekaFinance();
            this.setPreviousFinanceDeka(lastDekaFinance);
            return lastDekaFinance;
        }
        return this.previousFinanceDeka;
    }

    public void setPreviousFinanceDeka(Finance currentFinance) {
        this.previousFinanceDeka = currentFinance;
    }

    public  Finance getPreviousFinanceBTC() {
        if (this.previousFinanceBTC == null) {
            Finance lastBTCFinance = this.getLastBTCFinance();
            this.previousFinanceBTC = lastBTCFinance;
            return lastBTCFinance;
        }
        return this.previousFinanceBTC;
    }

    public Finance getLastBTCFinance() {
        return financeController.getLastBTCFinance();
    }

    public void setPreviousFinanceBTC(Finance currentBTC) {
        this.previousFinanceBTC = currentBTC;
    }
}
