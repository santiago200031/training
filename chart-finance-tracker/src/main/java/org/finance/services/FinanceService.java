package org.finance.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Setter;
import org.finance.controllers.FinanceController;
import org.finance.models.Finance;
import org.finance.models.FinanceOffline;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FinanceService {

    @Inject
    FinanceController financeController;

    @Setter
    private Finance previousFinanceDeka;

    @Setter
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

    public Finance getPreviousFinanceDeka() {
        if (this.previousFinanceDeka == null) {
            Finance lastDekaFinance = this.getLastDekaFinance();
            this.setPreviousFinanceDeka(lastDekaFinance);
            return lastDekaFinance;
        }
        return this.previousFinanceDeka;
    }

    public Finance getPreviousFinanceBTC() {
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

    public String getFinanceAsJson(Finance finance) {
        return financeController.getFinanceAsJson(finance);
    }

    public List<String> getFinancesAsJson(List<Finance> finances) {
        return financeController.getFinancesAsJson(finances);
    }

    public FinanceOffline getDekaLocalFinanceAsJson() {
        return financeController.getDekaGlobalChampionsLocalData();
    }

    public String getFinanceOfflineAsJson(FinanceOffline financeOffline) {
        return financeController.getFinanceAsJson(financeOffline);
    }
}