package org.finance;

import org.finance.models.Finance;
import org.finance.models.FinanceOffline;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class BaseTest {

    protected Finance finance;

    protected FinanceOffline financeOffline;

    protected String expectedJson;

    protected String arrayJson;

    @BeforeEach
    public void setUp() {
        finance = getFinance();
        arrayJson = getArrayJson();
        expectedJson = getExpectedJson();
        financeOffline = getFinanceOffline();
    }

    private FinanceOffline getFinanceOffline() {
        return FinanceOffline.builder()
                .displayName("Finance Offline Test")
                .prices(List.of(1f, 2f, 3f))
                .pricesChanges(List.of("ChangeDate1", "ChangeDate2"))
                .localDateChanges(List.of("LocalDate1", "LocalDate2"))
                .differencePrices(List.of(0f, 1f, 1f))
                .build();
    }

    private String getExpectedJson() {
        return "{" +
               "\"id\":\"d9f0dbd3-9e37-43e3-9d53-3d41d7b49c97\"," +
               "\"price\":285.32," +
               "\"priceChange\":1.35," +
               "\"displayName\":\"Deka-GlobalChampions CF\"," +
               "\"timeLastUpdated\":\"2023-11-16T10:00:34.4349054Z\"" +
               "}";
    }

    private Finance getFinance() {
        return Finance.builder()
                .id("d9f0dbd3-9e37-43e3-9d53-3d41d7b49c97")
                .price(285.32f)
                .priceChange(1.35f)
                .displayName("Deka-GlobalChampions CF")
                .timeLastUpdated("2023-11-16T10:00:34.4349054Z")
                .build();
    }

    private String getArrayJson() {
        return "[" +
               "{" +
               "\"id\":\"d9f0dbd3-9e37-43e3-9d53-3d41d7b49c97\"," +
               "\"price\":285.32," +
               "\"priceChange\":1.35," +
               "\"displayName\":\"Deka-GlobalChampions CF\"," +
               "\"timeLastUpdated\":\"2023-11-16T10:00:34.4349054Z\"" +
               "}" +
               "]";
    }
}