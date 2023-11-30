package org.finance.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.util.stream.Collectors;

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
            LOGGER.error(e.getMessage());
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

    public String getFinanceAsJson(Finance finance) {
        JsonArray jsonArray = financeParser.financeToJson(finance);
        JsonElement financeAsJsonElement = jsonArray.get(0);
        return financeAsJsonElement.getAsString();
    }

    public List<String> getFinancesAsJson(List<Finance> finances) {
        List<JsonElement> jsonArray = financeParser.financeListToJson(finances);
        return jsonArray.stream()
                .map(JsonElement::getAsString)
                .collect(Collectors.toList());
    }
}