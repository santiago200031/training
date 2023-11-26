package org.finance.apis;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.finance.models.Finance;
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
    @Produces
    public Response getFinanceDekaGlobalChampions() {
        Finance finance = financeService.getDekaGlobalChampions(userService.getActivityId());
        if (finance == null) {
            return Response.noContent().build();
        }
        return Response.ok(finance).build();
    }

    @GET
    @Path("/finances")
    @Produces
    public Response getFinances() {
        UUID activityId = userService.getActivityId();
        List<Finance> finance = financeService.getFinances(activityId);

        if (finance == null) {
            return Response.noContent().build();
        }
        return Response.ok(finance).build();
    }
}