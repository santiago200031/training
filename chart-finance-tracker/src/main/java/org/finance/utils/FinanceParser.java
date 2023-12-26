package org.finance.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
import java.util.stream.Collectors;

@ApplicationScoped
public class FinanceParser {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    Gson gson;

    public JsonArray financeToJson(Object financeObject) {
        LOGGER.debug("Parsing Finance Object to JSON: " + financeObject);
        JsonArray jsonArrayToReturn = new JsonArray();

        try {
            String json = gson.toJson(financeObject);
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

            List<Finance> financeList = new ArrayList<>();

            JsonArray jsonArray = jsonElement.getAsJsonArray();

            return parseJsonArray(jsonArray, financeList);
        } catch (JsonParseException e) {
            LOGGER.error("Error parsing JSON to List<Finance> object: " + e.getMessage());
        }

        return Collections.emptyList();
    }

    public List<JsonElement> financeListToJson(List<Finance> finances) {
        return finances.stream()
                .map(this::financeToJson)
                .collect(Collectors.toList());
    }

    private List<Finance> parseJsonArray(JsonArray jsonArray, List<Finance> financeList) {
        Type financeType = new TypeToken<Finance>() {
        }.getType();

        for (JsonElement arrayElement : jsonArray) {
            if (arrayElement.isJsonArray()) {
                JsonArray innerArray = arrayElement.getAsJsonArray();
                for (JsonElement jsonElement : innerArray) {

                    Finance finance = gson.fromJson(jsonElement.getAsJsonObject(), financeType);

                    financeList.add(finance);
                }
            } else {
                LOGGER.error("Expected JSON array inside the outer array.");
            }
        }
        return financeList;
    }
}