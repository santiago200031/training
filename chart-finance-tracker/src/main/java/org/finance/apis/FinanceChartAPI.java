package org.finance.apis;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.finance.models.Finance;
import org.finance.models.FinanceChart;
import org.finance.services.FinanceChartService;
import org.finance.services.UserService;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/financeChart")
public class FinanceChartAPI {

    @Inject
    FinanceChartService financeChartService;

    @Inject
    UserService userService;

    @GET
    @Path("/deka")
    public Response getFinanceDekaGlobalChampions() {
        FinanceChart financeChart = financeChartService.getDekaGlobalChampions(userService.getActivityId());
        if (financeChart == null) {
            return Response.noContent().build();
        }
        return Response.ok(financeChart).build();
    }

    @GET
    @Path("/finances")
    public Response getFinances() throws NoSuchMethodException {
        List<Finance> finance = financeChartService.getFinances(userService.getActivityId());
        if (finance == null) {
            return Response.noContent().build();
        }
        return Response.ok(finance).build();
    }
}