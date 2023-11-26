package org.finance;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.finance.models.Finance;
import org.finance.utils.FinanceParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@QuarkusTest
public class FinanceAPITest extends BaseTest {

    //TODO make end-point test

    @Inject
    FinanceParser financeParser;

    @Test
    public void testWithOfflineResponse() {
        List<Finance> financeList = financeParser.toFinanceList(originalJsonResponse);
        financeList.forEach(System.out::println);
    }

    @Test
    public void TestWithOnlineResponse() throws Exception {
        String apiUrl = "https://assets.msn.com/service/Finance/Quotes?" +
                "apikey=0QfOX3Vn51YCzitbLaRkTTBadtWpgTN8NZLW0C1SEM&" +
                "activityId=DFF4982D-7051-407F-830C-1D043C27E58C&" +
                "ocid=finance-utils-peregrine&" +
                "cm=de-de&it=edgeid&" +
                "traceparent=00-aabb2bdbe41a4e97a5ce3def603d7810-82729fa839b9438b-00&" +
                "ids=agynsm&wrapodata=false";

        String jsonResponse = makeGetRequest(apiUrl);

        Finance finance = financeParser.toFinance(jsonResponse);
        Assertions.assertNotNull(finance);
        Assertions.assertNotNull(finance.getDisplayName());
        Assertions.assertNotNull(finance.getTimeLastUpdated());
        Assertions.assertNotNull(finance.getId());
    }

    @Test
    public void testWithOnlineResponse() throws Exception {
        String apiUrl = "https://assets.msn.com/service/Finance/Charts?apikey=0QfOX3Vn51YCzitbLaRkTTBadtWpgTN8NZLW0C1SEM&activityId=8A337645-6F3A-44E4-8718-D3AD8965A511&ocid=finance-utils-peregrine&cm=de-de&it=edgeid&traceparent=00-1181b84cc69b423db789e8c738a5cfbf-67944000070e45d2-00&ids=agynsm&type=1M&wrapodata=false&disableSymbol=false";

        String jsonResponse = makeGetRequest(apiUrl);
        System.out.println(jsonResponse);

        Finance finance = financeParser.toFinance(jsonResponse);
        Assertions.assertNotNull(finance);
        Assertions.assertNotNull(finance.getDisplayName());
        Assertions.assertNotNull(finance.getTimeLastUpdated());
        Assertions.assertNotNull(finance.getId());
    }


    public String makeGetRequest(String apiUrl) throws Exception {
        HttpURLConnection connection = openConnection(apiUrl);
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
}