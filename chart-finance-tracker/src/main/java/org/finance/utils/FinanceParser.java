package org.finance.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.models.Finance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class FinanceParser {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    Gson gson;

    public JsonArray financeToJson(Finance finance) {
        LOGGER.debug("Parsing Finance Object to JSON: " + finance);
        JsonArray jsonArrayToReturn = new JsonArray();

        try {
            String json = gson.toJson(finance);
            JsonArray jsonArray = new JsonArray();

            jsonArray.add(json);
            jsonArrayToReturn.add(jsonArray);
            return jsonArrayToReturn;

        } catch (JsonParseException e) {
            LOGGER.error("Error parsing Finance to JSON: " + e.getMessage());
        }

        return jsonArrayToReturn;
    }

    public Finance jsonToFinance(String json) {
        LOGGER.debug("Parsing String to Finance Object: " + json);

        try {
            JsonElement jsonElement = gson.fromJson(json, JsonElement.class);

            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                if (!jsonArray.isJsonNull() && !jsonArray.isEmpty()) {
                    return gson.fromJson(jsonArray.get(0), Finance.class);
                }
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return gson.fromJson(jsonObject, Finance.class);
            }
        } catch (JsonParseException e) {
            LOGGER.error("Error parsing JSON to Finance object: " + e.getMessage());
        }

        return null;
    }

    public List<Finance> jsonToFinanceList(String json) {
        LOGGER.debug("Parsing String to List<Finance> Object: " + json);

        try {
            JsonElement jsonElement = gson.fromJson(json, JsonElement.class);

            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                Type financeListType = new TypeToken<List<Finance>>() {
                }.getType();
                return gson.fromJson(jsonArray, financeListType);
            } else if (jsonElement.isJsonObject()) {

                Finance finance = gson.fromJson(jsonElement, Finance.class);
                List<Finance> financeList = new ArrayList<>();
                financeList.add(finance);
                return financeList;
            }
        } catch (JsonParseException e) {
            LOGGER.error("Error parsing JSON to List<Finance> object: " + e.getMessage());
        }

        return Collections.emptyList();
    }
}