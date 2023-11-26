package org.finance.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.controllers.FinanceChartController;
import org.finance.models.Finance;
import org.finance.models.FinanceChart;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FinanceChartService {

    @Inject
    FinanceChartController financeChartController;

    public FinanceChart getDekaGlobalChampions(UUID activityId) {
        return financeChartController.getDekaGlobalChampions(activityId);
    }

    public List<Finance> getFinances(UUID activityId) throws NoSuchMethodException {
        throw new NoSuchMethodException("Implement method");
    }
}
