package org.finance.apis;

import com.google.gson.Gson;
import jakarta.ws.rs.core.Response;
import org.finance.BaseTest;
import org.finance.models.Finance;
import org.finance.models.FinanceOffline;
import org.finance.services.FinanceService;
import org.finance.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.net.HttpURLConnection.HTTP_OK;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FinanceAPITest extends BaseTest {

    @InjectMocks
    FinanceAPI financeAPI;

    @Mock
    FinanceService financeService;

    @Mock
    UserService userService;

    Gson financeParser = new Gson();

    @BeforeEach
    void setUpMock() {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            when(userService.getActivityId()).thenReturn(UUID.randomUUID());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetFinances() {
        when(userService.getActivityId()).thenReturn(UUID.randomUUID());

        List<Finance> mockedFinances = List.of(Finance.builder().build());
        when(financeService.getFinances(userService.getActivityId()))
                .thenReturn(mockedFinances);
        when(financeService.getFinancesAsJson(Mockito.anyList()))
                .thenReturn(Collections.singletonList(arrayJson));

        Response response = financeAPI.getFinances();

        assertEquals(HTTP_OK, response.getStatus());

        List<Finance> responseEntity = (List<Finance>) response.getEntity();
        assertNotNull(responseEntity);
        assertEquals(1, responseEntity.size());
        verify(financeService).getFinances(userService.getActivityId());
    }

    @Test
    void testGetFinanceDekaGlobalChampions() {
        when(userService.getActivityId()).thenReturn(UUID.randomUUID());

        when(financeService.getDekaGlobalChampions(userService.getActivityId())).thenReturn(finance);

        when(financeService.getFinanceAsJson(finance)).thenReturn(arrayJson);

        Response response = financeAPI.getFinanceDekaGlobalChampions();

        assertEquals(HTTP_OK, response.getStatus());

        var responseEntity = financeParser.fromJson((String) response.getEntity(), List.class);

        assertNotNull(responseEntity);

        String dekaFinance = responseEntity.get(0).toString();

        Map<String, Object> mapped = parseJsonResponseInTest(dekaFinance);

        String expectedDisplayName = finance.getDisplayName().replace(" ", "");
        assertEquals(expectedDisplayName, mapped.get("displayName"));
        assertEquals(String.valueOf(finance.getPrice()), mapped.get("price"));
        assertEquals(String.valueOf(finance.getPriceChange()), mapped.get("priceChange"));
        assertEquals(finance.getId(), mapped.get("id"));
        assertEquals(finance.getTimeLastUpdated(), mapped.get("timeLastUpdated"));

        verify(financeService).getDekaGlobalChampions(userService.getActivityId());
    }


    @Test
    void testGetDekaFinanceLocalData() {
        FinanceOffline mockedFinanceOffline = FinanceOffline.builder().build();
        when(financeService.getDekaLocalFinanceAsJson()).thenReturn(mockedFinanceOffline);

        Response response = financeAPI.getDekaFinanceLocalData();

        assertEquals(HTTP_OK, response.getStatus());
    }

    private Map<String, Object> parseJsonResponseInTest(String jsonResponse) {
        // After gson.fromJson() is in the format: {"id"="value1", "otherKey"="otherValue", ...}
        String[] firstSplit = jsonResponse
                .replace("{", "")
                .replace("}", "")
                .replace(" ", "")
                .split(",");

        Map<String, Object> mapped = new HashMap<>();

        Arrays.stream(firstSplit).forEach(s -> {
            String[] split = s.split("=");
            mapped.put(split[0], split[1]);
        });

        return mapped;
    }
}