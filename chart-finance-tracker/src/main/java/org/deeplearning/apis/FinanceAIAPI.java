package org.deeplearning.apis;


import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.deeplearning.services.DekaAIService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Produces(MediaType.APPLICATION_JSON)
@Path("/financeai")
public class FinanceAIAPI {

    @Inject
    private DekaAIService aiService;

    @Path("/trainDeka")
    @GET
    public Response trainDeka() {
        aiService.trainModel();
        return Response.ok("Training successful").build();
    }

    @Path("/predictDeka")
    @POST
    public Response predict(@RequestBody String input) {
        String prediction = aiService.makePrediction(input);
        return Response.ok(prediction).build();
    }
}