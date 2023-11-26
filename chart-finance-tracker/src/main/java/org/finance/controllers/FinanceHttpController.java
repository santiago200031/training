package org.finance.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.log4j.Logger;
import org.finance.models.Finance;
import org.finance.models.FinanceChart;
import org.finance.utils.FinanceChartParser;
import org.finance.utils.FinanceParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FinanceHttpController {

    private static final Logger LOGGER = Logger.getLogger(FinanceHttpController.class);

    @Inject
    FinanceParser financeParser;

    @Inject
    FinanceChartParser financeChartParser;

    private String makeHttpRequest(String apiUrl, UUID activityId) {
        String jsonResponse;
        LOGGER.debug("ActivityId: " + activityId);

        try {
            jsonResponse = makeGetRequest(apiUrl, activityId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonResponse;
    }

    public String makeGetRequest(String apiUrl, UUID activityId) throws Exception {
        String apiUrlWithActId = apiUrl.replace("activity_id", activityId.toString());
        LOGGER.debug(apiUrlWithActId);

        HttpURLConnection connection = openConnection(apiUrlWithActId);
        try {
            connection.setRequestMethod("GET");

            int responseCode = getResponseCode(connection);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readResponse(connection);
            } else {
                throw new RuntimeException("HTTP Request Failed with response code: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }
    }


    private HttpURLConnection openConnection(String apiUrl) throws Exception {
        URI uri;
        try {
            uri = new URI(apiUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI: " + apiUrl, e);
        }

        return (HttpURLConnection) uri.toURL().openConnection();
    }

    private int getResponseCode(HttpURLConnection connection) throws Exception {
        return connection.getResponseCode();
    }

    private String readResponse(HttpURLConnection connection) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        }
    }

    public Finance getFinance(UUID activityId, String apiUrl) {
        String jsonResponse = makeHttpRequest(apiUrl, activityId);
        return financeParser.toFinance(jsonResponse);
    }

    public List<Finance> getFinances(UUID activityId, String apiUrl) {
        String jsonResponse = makeHttpRequest(apiUrl, activityId);
        return financeParser.toFinanceList(jsonResponse);
    }

    public FinanceChart getFinanceChart(String apiUrl, UUID activityId) {
        String jsonResponse = makeHttpRequest(apiUrl, activityId);
        return financeChartParser.toFinanceChart(jsonResponse);
    }

    public List<FinanceChart> getFinanceCharts(String apiUrl, UUID activityId) {
        String jsonResponse = makeHttpRequest(apiUrl, activityId);
        return financeChartParser.toFinanceChartList(jsonResponse);
    }
}
