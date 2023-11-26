package org.finance.utils;

import com.google.gson.JsonArray;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.finance.BaseTest;
import org.finance.models.Finance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class FinanceParserTest extends BaseTest {

    @Inject
    FinanceParser financeParser;

    @Test
    void financeToJsonShouldPass() {
        Finance actualFinance = finance;
        String actualJson = getFinanceJson(actualFinance);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    void jsonToFinanceSingleObjectShouldPass() {
        Finance expectedFinance = finance;

        Finance actualFinance = financeParser.jsonToFinance(arrayJson);

        assertFinanceEquals(expectedFinance, actualFinance);
    }

    @Test
    void financeToJsonAndReverseShouldPass() {
        Finance actualFinance = finance;
        String actualJson = getFinanceJson(actualFinance);

        Assertions.assertEquals(expectedJson, actualJson);

        Finance expectedFinance = finance;
        actualFinance = financeParser.jsonToFinance(arrayJson);

        assertFinanceEquals(expectedFinance, actualFinance);
    }

    private String getFinanceJson(Finance finance) {
        JsonArray jsonArray = financeParser.financeToJson(finance);
        return jsonArray.get(0).getAsString();
    }

    private void assertFinanceEquals(Finance expected, Finance actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected.getPriceChange(), actual.getPriceChange());
        Assertions.assertEquals(expected.getDisplayName(), actual.getDisplayName());
        Assertions.assertEquals(expected.getTimeLastUpdated(), actual.getTimeLastUpdated());
    }
}