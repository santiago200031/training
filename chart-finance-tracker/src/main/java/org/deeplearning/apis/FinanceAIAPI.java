package org.deeplearning.apis;


import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.deeplearning.plots.PlotFinance;
import org.deeplearning.services.BTCAIService;
import org.deeplearning.services.DekaAIService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.finance.models.Finance;
import org.finance.services.FinanceService;

@Produces(MediaType.APPLICATION_JSON)
@Path("/financeai")
public class FinanceAIAPI {

    @Inject
    private DekaAIService dekaAIService;

    @Inject
    private BTCAIService btcAIService;

    @Inject
    private FinanceService financeService;

    @GET
    @Path("/neural-network/trainDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Train the model with the offline data of the CSV file.")
    public Response trainDeka() {
        dekaAIService.trainModel();
        return Response.ok("Training successful").build();
    }

    @POST
    @Path("/neural-network/predictDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response predictDeka(@RequestBody String date) {
        Finance finance = dekaAIService.predictWithNeuralNetwork(date);

        if (finance == null) {
            return Response.noContent().build();
        }

        String financeJson = financeService.getFinanceAsJson(finance);
        return Response.ok(financeJson).build();
    }

    @POST
    @Path("/network-network/visualizeDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response visualizeDeka() {
        PlotFinance plotFinance = dekaAIService.visualizeModel();

        if (plotFinance == null) {
            return Response.noContent().build();
        }

        return Response.ok(plotFinance.getSavedPath()).build();
    }

    @POST
    @Path("/network-network/visualizeBTC")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction for Bitcoin of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response visualizeBTC() {
        PlotFinance plotFinance = btcAIService.visualizeModel();

        if (plotFinance == null) {
            return Response.noContent().build();
        }

        return Response.ok(plotFinance.getSavedPath()).build();
    }

    @GET
    @Path("/neural-network/trainBTC")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Train the model with the offline data of the CSV file.")
    public Response trainBTC() {
        btcAIService.trainModel();
        return Response.ok("Training successful").build();
    }

    @POST
    @Path("/neural-network/predictBTC")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response predictBTC(@RequestBody String date) {
        Finance finance = btcAIService.predictWithNeuralNetwork(date);

        if (finance == null) {
            return Response.noContent().build();
        }

        String financeJson = financeService.getFinanceAsJson(finance);
        return Response.ok(financeJson).build();
    }

    @POST
    @Path("/function/calculateFunctionDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Calculate the Polynomial Regression of the data for prediction.")
    public Response calculateFunctionDeka() {
        dekaAIService.calculatePolynomialFunction();
        return Response.ok("Successful").build();
    }

    @POST
    @Path("/function/calculateFunctionBTC")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Calculate the Polynomial Regression of the data for prediction.")
    public Response calculateFunctionBTC() {
        btcAIService.calculatePolynomialFunction();
        return Response.ok("Successful").build();
    }

    @POST
    @Path("/function/predictDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with polynomial regression.")
    public Response predictDekaWithPolynomialRegression(@RequestBody String date) {
        Finance finance = dekaAIService.predictWithPolynomialRegressionModel(date);

        if (finance == null) {
            return Response.noContent().build();
        }

        String financeJson = financeService.getFinanceAsJson(finance);
        return Response.ok(financeJson).build();
    }

    @POST
    @Path("/function/predictBTC")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with polynomial regression.")
    public Response predictBTCWithPolynomialRegression(@RequestBody String date) {
        Finance finance = btcAIService.predictWithPolynomialRegressionModel(date);

        if (finance == null) {
            return Response.noContent().build();
        }

        String financeJson = financeService.getFinanceAsJson(finance);
        return Response.ok(financeJson).build();
    }

    @POST
    @Path("/function/visualizeDeka")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response visualizeFunctionDeka() {
        PlotFinance plotFinance = dekaAIService.visualizeRegressionFunction();

        if (plotFinance == null) {
            return Response.noContent().build();
        }

        return Response.ok(plotFinance.getSavedPath()).build();
    }

    @POST
    @Path("/function/visualizeBTC")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Make a prediction of the price in some day (dd.mm.yyyy) with the trained model.")
    public Response visualizeFunctionBTC() {
        PlotFinance plotFinance = btcAIService.visualizeRegressionFunction();

        if (plotFinance == null) {
            return Response.noContent().build();
        }

        return Response.ok(plotFinance.getSavedPath()).build();
    }
}