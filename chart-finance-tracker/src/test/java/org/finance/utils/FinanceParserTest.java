package org.finance.utils;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.finance.BaseTest;
import org.finance.models.Finance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
class FinanceParserTest extends BaseTest {

    @Inject
    FinanceParser financeParser;

    @Test
    void toJsonTest() {
        Finance actualFinance = testFinance;

        String actualJson = financeParser.toJson(actualFinance);
        Assertions.assertEquals(testJson, actualJson);
    }

    @Test
    void toFinanceTest() {
        String json = testJson;
        Finance expectedFinance = testFinance;

        Finance actualFinance = financeParser.toFinance(json);
        Assertions.assertEquals(expectedFinance.getId(), actualFinance.getId());
        Assertions.assertEquals(expectedFinance.getPrice(), actualFinance.getPrice());
        Assertions.assertEquals(expectedFinance.getPriceChange(), actualFinance.getPriceChange());
        Assertions.assertEquals(expectedFinance.getDisplayName(), actualFinance.getDisplayName());
        Assertions.assertEquals(expectedFinance.getTimeLastUpdated(), actualFinance.getTimeLastUpdated());
    }
}