package org.finance.apis;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.finance.BaseTest;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class FinanceAPITest extends BaseTest {

    //TODO make end-point test and add more tests
    
    @Inject
    FinanceAPI financeAPI;

    @Test
    public void testGetFinanceDekaGlobalChampionsShouldReturnOk() {
        Response financesResponse = financeAPI.getFinanceDekaGlobalChampions();

        Assertions.assertNotNull(financesResponse);
        Assertions.assertEquals(financesResponse.getStatus(), RestResponse.StatusCode.OK);
    }
}