package org.finance.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.models.Finance;
import org.finance.utils.CSVFileProperties;
import org.finance.utils.FinanceCSVReader;
import org.finance.utils.FinanceParser;
import org.finance.utils.ExternalApiEndpoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FinanceController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    FinanceParser financeParser;

    @Inject
    FinanceHttpController financeHttpController;

    @Inject
    FinanceCSVReader financeCSVReader;


    public Finance getDekaGlobalChampions(UUID activityId) {
        String jsonResponse;
        LOGGER.debug("ActivityId: " + activityId);

        try {
            jsonResponse = makeGetRequest(ExternalApiEndpoints.URL_DEKA_FINANCE, activityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return financeParser.jsonToFinance(jsonResponse);
    }

    public Finance getBTC(UUID activityId) {
        String jsonResponse;
        LOGGER.debug("ActivityId: " + activityId);

        try {
            jsonResponse = makeGetRequest(ExternalApiEndpoints.URL_BTC_FINANCE, activityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return financeParser.jsonToFinance(jsonResponse);
    }

    public List<Finance> getFinances(UUID activityId) {
        String jsonResponse;
        LOGGER.debug("ActivityId: " + activityId);

        try {
            jsonResponse = makeGetRequest(ExternalApiEndpoints.URL_FINANCES, activityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return financeParser.jsonToFinanceList(jsonResponse);
    }

    private String makeGetRequest(String apiUrl, UUID activityId) {
        String apiUrlWithActId = apiUrl.replace("activity_id", activityId.toString());
        return financeHttpController.makeHttpRequest(apiUrlWithActId, activityId);
    }

    public Finance getLastDekaFinance() {
        return financeCSVReader
                .readLastFinanceCSV(CSVFileProperties.DEKA_FILE_PATH.getValue());
    }

    public Finance getLastBTCFinance() {
        return financeCSVReader
                .readLastFinanceCSV(CSVFileProperties.BTC_FILE_PATH.getValue());
    }

    public boolean isDekaDataEmpty() {
        return financeCSVReader
                .readFinanceCSV(CSVFileProperties.DEKA_FILE_PATH.getValue())
                .isEmpty();
    }

    public boolean isBTCDataEmpty() {
        return financeCSVReader
                .readFinanceCSV(CSVFileProperties.BTC_FILE_PATH.getValue())
                .isEmpty();
    }
}