package org.finance;

import org.finance.models.Finance;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected Finance finance;

    protected String expectedJson;

    protected String arrayJson;

    @BeforeEach
    public void setUp() {
        finance = getFinance();
        arrayJson = getArrayJson();
        expectedJson = getExpectedJson();
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