package org.finance.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.models.Finance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

@ApplicationScoped
public class FinanceParser {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    private Gson gson;

    public String toJson(Finance finance) {
        LOGGER.debug("Parsing Finance Object to JSON: " + finance);
        JsonArray jsonArray0 = new JsonArray();
        JsonArray jsonArray1 = new JsonArray();
        jsonArray1.add(gson.toJson(finance));
        jsonArray0.add(jsonArray1);
        return jsonArray0.toString();
    }

    public Finance toFinance(String json) {
        LOGGER.debug("Parsing String to Finance Object: " + json);
        Type financeType = new TypeToken<List<Finance>>() {
        }.getType();
        List<Finance> finance = gson.fromJson(json, financeType);
        return finance != null && !finance.isEmpty() ? finance.get(0) : null;
    }

    public List<Finance> toFinanceList(String json) {
        Type financeListType = new TypeToken<List<List<Finance>>>() {
        }.getType();
        List<Finance> financeList = gson.fromJson(json, financeListType);
        return financeList != null && !financeList.isEmpty() ? financeList : null;
    }
}