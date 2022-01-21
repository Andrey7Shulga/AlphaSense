package services;

import apiServices.ApiCore;
import apiServices.Endpoints;
import io.restassured.response.Response;

public class ApiSteps {

    private final ApiCore apiCore;
    private Response response;

    public ApiSteps() {this.apiCore = new ApiCore();}


    public Response getSearchingInfo (String keyword, Integer slop, boolean positiveOnly, boolean negativeOnly) {
        return apiCore.getSearchResults(keyword, slop, positiveOnly, negativeOnly, Endpoints.getCurrentDocInfo());
    }

}