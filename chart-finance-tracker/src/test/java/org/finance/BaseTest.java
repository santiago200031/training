package org.finance;

import org.finance.models.Finance;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

public class BaseTest {

    protected Finance testFinance;
    protected String testJson;
    protected String originalJsonResponse;

    @BeforeEach
    public void setUp() {
        testFinance = Finance.builder()
                .id("d9f0dbd3-9e37-43e3-9d53-3d41d7b49c97")
                .price(285.32f)
                .priceChange(1.35f)
                .displayName("Deka-GlobalChampions CF")
                .timeLastUpdated("2023-11-16T10:00:34.4349054Z")
                .build();

        testJson = getTestJson();
        originalJsonResponse = getOriginalJsonResponse();

    }

    private String getTestJson() {
        return "{[[" +
               "\"id\":\"d9f0dbd3-9e37-43e3-9d53-3d41d7b49c97\"," +
               "\"price\":285.32,\"priceChange\":1.35," +
               "\"displayName\":\"Deka-GlobalChampions CF\"," +
               "\"timeLastUpdated\":\"2023-11-16T10:00:34.4349054Z\"" +
               "]]}";
    }

    private String getOriginalJsonResponse() {
        return "[\n" +
               "[\n" +
               "{\n" +
               "\"$type\": \"Msn.Finance.DataModel.Shared.UnifiedQuote, Msn.Finance.DataModel\",\n" +
               "\"cryptoRank\": 50,\n" +
               "\"cryptoPrice30dHigh\": 48.1681,\n" +
               "\"cryptoPrice30dLow\": 22.3605,\n" +
               "\"cryptoVolume24h\": 54030090.2972,\n" +
               "\"cryptoMarketCapDominance\": 0.0851,\n" +
               "\"cryptoMaxSupply\": 31415926,\n" +
               "\"cryptoCirculatingSupply\": 26226409,\n" +
               "\"cryptoPercentPriceChange1h\": 0.64,\n" +
               "\"cryptoVolumeChange24h\": 50.0534,\n" +
               "\"cryptoTotalSupply\": 26231561,\n" +
               "\"price\": 41.8997,\n" +
               "\"priceChange\": 2.395405849,\n" +
               "\"priceDayHigh\": 42.0050246,\n" +
               "\"priceDayLow\": 38.8126,\n" +
               "\"pricePreviousClose\": 5.717,\n" +
               "\"priceChangePercent\": 5.717,\n" +
               "\"return1Week\": 3.0865,\n" +
               "\"return1Month\": 82.796,\n" +
               "\"return3Month\": 70.05508,\n" +
               "\"returnYTD\": 38.037,\n" +
               "\"return1Year\": 10.819,\n" +
               "\"return3Year\": 500.607,\n" +
               "\"icon\": \"https://assets.msn.com/weathermapdata/1/static/finance/crypto/icons/Cryptoc2122Image.png\",\n" +
               "\"marketCap\": 1098871135.7201,\n" +
               "\"marketCapCurrency\": \"EUR\",\n" +
               "\"displayName\": \"MultiversX\",\n" +
               "\"shortName\": \"MultiversX\",\n" +
               "\"securityType\": \"cryptocurrency\",\n" +
               "\"instrumentId\": \"c2122\",\n" +
               "\"symbol\": \"EGLD\",\n" +
               "\"timeLastUpdated\": \"2023-11-19T21:42:02.8761496Z\",\n" +
               "\"currency\": \"EUR\",\n" +
               "\"_p\": \"c2122\",\n" +
               "\"id\": \"c2122\",\n" +
               "\"_t\": \"UnifiedQuote\"\n" +
               "}\n" +
               "],\n" +
               "[\n" +
               "{\n" +
               "\"$type\": \"Msn.Finance.DataModel.Shared.UnifiedQuote, Msn.Finance.DataModel\",\n" +
               "\"cryptoRank\": 60,\n" +
               "\"cryptoPrice30dHigh\": 6.1832,\n" +
               "\"cryptoPrice30dLow\": 3.8778,\n" +
               "\"cryptoVolume24h\": 42546999.7261,\n" +
               "\"cryptoMarketCapDominance\": 0.063,\n" +
               "\"cryptoMaxSupply\": 270000000,\n" +
               "\"cryptoCirculatingSupply\": 142271620.712,\n" +
               "\"cryptoPercentPriceChange1h\": 0.0382,\n" +
               "\"cryptoVolumeChange24h\": -33.31,\n" +
               "\"cryptoTotalSupply\": 270000000,\n" +
               "\"price\": 5.7113,\n" +
               "\"priceChange\": 0.19949570899999997,\n" +
               "\"priceDayHigh\": 5.7397,\n" +
               "\"priceDayLow\": 5.4126,\n" +
               "\"pricePreviousClose\": 3.493,\n" +
               "\"priceChangePercent\": 3.493,\n" +
               "\"return1Week\": -1.81,\n" +
               "\"return1Month\": 47.332,\n" +
               "\"return3Month\": 26.628,\n" +
               "\"returnYTD\": -4.28,\n" +
               "\"return1Year\": -5.051,\n" +
               "\"return3Year\": 1157.817,\n" +
               "\"icon\": \"https://assets.msn.com/weathermapdata/1/static/finance/crypto/icons/Cryptoc211pImage.png\",\n" +
               "\"marketCap\": 812605778.6569,\n" +
               "\"marketCapCurrency\": \"EUR\",\n" +
               "\"displayName\": \"Axie Infinity\",\n" +
               "\"shortName\": \"Axie Infinity\",\n" +
               "\"securityType\": \"cryptocurrency\",\n" +
               "\"instrumentId\": \"c211p\",\n" +
               "\"symbol\": \"AXS\",\n" +
               "\"timeLastUpdated\": \"2023-11-19T21:42:02.8765195Z\",\n" +
               "\"currency\": \"EUR\",\n" +
               "\"_p\": \"c211p\",\n" +
               "\"id\": \"c211p\",\n" +
               "\"_t\": \"UnifiedQuote\"\n" +
               "}\n" +
               "],\n" +
               "[\n" +
               "{\n" +
               "\"$type\": \"Msn.Finance.DataModel.Shared.UnifiedQuote, Msn.Finance.DataModel\",\n" +
               "\"cryptoRank\": 37,\n" +
               "\"cryptoPrice30dHigh\": 6.08332,\n" +
               "\"cryptoPrice30dLow\": 1.4945,\n" +
               "\"cryptoVolume24h\": 330405814.01264,\n" +
               "\"cryptoMarketCapDominance\": 0.133,\n" +
               "\"cryptoMaxSupply\": 0,\n" +
               "\"cryptoCirculatingSupply\": 337582938,\n" +
               "\"cryptoPercentPriceChange1h\": -0.112,\n" +
               "\"cryptoVolumeChange24h\": 9.257,\n" +
               "\"cryptoTotalSupply\": 483974522,\n" +
               "\"price\": 5.07813,\n" +
               "\"priceChange\": -0.3502386261,\n" +
               "\"priceDayHigh\": 5.4364,\n" +
               "\"priceDayLow\": 4.7125,\n" +
               "\"pricePreviousClose\": -6.897,\n" +
               "\"priceChangePercent\": -6.897,\n" +
               "\"return1Week\": 4.93,\n" +
               "\"return1Month\": 242.259,\n" +
               "\"return3Month\": 216.993,\n" +
               "\"returnYTD\": 319.135,\n" +
               "\"return1Year\": 379.818,\n" +
               "\"return3Year\": 498.386,\n" +
               "\"icon\": \"https://assets.msn.com/weathermapdata/1/static/finance/crypto/icons/Cryptoc212lImage.png\",\n" +
               "\"marketCap\": 1714410947.3337,\n" +
               "\"marketCapCurrency\": \"EUR\",\n" +
               "\"displayName\": \"THORChain\",\n" +
               "\"shortName\": \"THORChain\",\n" +
               "\"securityType\": \"cryptocurrency\",\n" +
               "\"instrumentId\": \"c212l\",\n" +
               "\"symbol\": \"RUNE\",\n" +
               "\"timeLastUpdated\": \"2023-11-19T21:42:02.8754697Z\",\n" +
               "\"currency\": \"EUR\",\n" +
               "\"_p\": \"c212l\",\n" +
               "\"id\": \"c212l\",\n" +
               "\"_t\": \"UnifiedQuote\"\n" +
               "}\n" +
               "]\n" +
               "]\n";
    }
}