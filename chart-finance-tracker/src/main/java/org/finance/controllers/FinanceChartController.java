package org.finance.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.models.FinanceChart;
import org.finance.utils.FinanceChartParser;

import java.util.List;
import java.util.UUID;

import static org.finance.utils.ExternalApiEndpoints.*;

@ApplicationScoped
public class FinanceChartController {

    @Inject
    FinanceHttpController financeHttpController;
    @Inject
    FinanceChartParser financeChartParser;

    public FinanceChart getDekaGlobalChampions(UUID activityId) {
        String json = financeHttpController.makeHttpRequest(URL_DEKA_CHARTS, activityId);
        return financeChartParser.toFinanceChart(json);
    }

    public List<FinanceChart> getFinanceCharts(UUID activityId) {
        String json = financeHttpController.makeHttpRequest(URL_CHARTS, activityId);
        return financeChartParser.toFinanceChartList(json);
    }
}