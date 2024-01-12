package org.finance.apis;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.finance.models.Finance;
import org.finance.models.FinanceOffline;
import org.finance.services.FinanceService;
import org.finance.services.UserService;

import java.util.List;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Path("/finance")
public class FinanceAPI {

    @Inject
    FinanceService financeService;

    @Inject
    UserService userService;

    @GET
    @Path("/deka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the DekaGlobalChampions Finance with the current value")
    public Response getFinanceDekaGlobalChampions() {
        Finance finance = financeService.getDekaGlobalChampions(userService.getActivityId());
        if (finance == null) {
            return Response.noContent().build();
        }

        String financeJson = financeService.getFinanceAsJson(finance);
        return Response.ok(financeJson).build();
    }

    @GET
    @Path("/btc")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the Bitcoin Finance with the current value")
    public Response getFinanceBitcoin() {
        Finance finance = financeService.getBTC(userService.getActivityId());
        if (finance == null) {
            return Response.noContent().build();
        }

        String financeJson = financeService.getFinanceAsJson(finance);
        return Response.ok(financeJson).build();
    }

    @GET
    @Path("/finances")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all the Finances with the current value")
    public Response getFinances() {
        UUID activityId = userService.getActivityId();
        List<Finance> finances = financeService.getFinances(activityId);

        if (finances == null) {
            return Response.noContent().build();
        }

        List<String> financesJson = financeService.getFinancesAsJson(finances);
        return Response.ok(financesJson).build();
    }

    @GET
    @Path("/deka-offline")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all the Finances with the current value")
    public Response getDekaFinanceLocalData() {
        FinanceOffline financeOffline = financeService.getDekaLocalFinanceAsJson();

        if (financeOffline == null) {
            return Response.noContent().build();
        }

        String financesJson = financeService.getFinanceOfflineAsJson(financeOffline);
        return Response.ok(financesJson).build();
    }
}