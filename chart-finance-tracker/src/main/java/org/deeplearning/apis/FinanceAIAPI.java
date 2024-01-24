package org.deeplearning.apis;


import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.deeplearning.services.BTCAIService;
import org.deeplearning.services.DekaAIService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Produces(MediaType.APPLICATION_JSON)
@Path("/financeai")
public class FinanceAIAPI {

    @Inject
    private DekaAIService dekaAIService;

    @Inject
    private BTCAIService btcAIService;

    @GET
    @Path("/trainDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Train the model with the offline data of the CSV file.")
    public Response trainDeka() {
        dekaAIService.trainModel();
        return Response.ok("Training successful").build();
    }

    @POST
    @Path("/predictDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response predictDeka(@RequestBody String date) {
        String prediction = dekaAIService.makePrediction(date);
        return Response.ok(prediction).build();
    }

    @GET
    @Path("/trainBtc")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Train the model with the offline data of the CSV file.")
    public Response trainBTC() {
        btcAIService.trainModel();
        return Response.ok("Training successful").build();
    }

    @POST
    @Path("/predictBtc")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response predictBTC(@RequestBody String date) {
        String prediction = btcAIService.makePrediction(date);
        return Response.ok(prediction).build();
    }
}