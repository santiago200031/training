package org.finance.services.priceDifferences;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.finance.BaseTest;
import org.finance.models.Finance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class PriceDifferenceDekaServiceTest extends BaseTest {

    @Inject
    PriceDifferenceDekaService priceDifferenceDekaService;

    @Test
    void getDifferencePriceIs0() {
        Finance previousFinance = Finance.builder().price(finance.getPrice()).build();
        float differencePrice = priceDifferenceDekaService.getDifferencePrice(finance, previousFinance);

        Assertions.assertEquals(differencePrice, 0f);
        Assertions.assertEquals(priceDifferenceDekaService.getGauge(), 0);
    }

    @Test
    void getDifferencePriceIs2() {
        Finance previousFinance = Finance.builder().price(finance.getPrice()).build();

        finance.setPrice(finance.getPrice() + 2f);

        float differencePrice = priceDifferenceDekaService.getDifferencePrice(finance, previousFinance);
        Assertions.assertEquals(differencePrice, 2f);
        Assertions.assertEquals(priceDifferenceDekaService.getGauge(), 1);
    }
}