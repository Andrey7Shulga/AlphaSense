package services;

import apiServices.ApiCore;
import apiServices.Endpoints;
import io.restassured.response.Response;

public class ApiSteps {

    private final ApiCore apiCore;

    public ApiSteps() {this.apiCore = new ApiCore();}

    public Response getSearchingInfo (String keyword, Object slop, String positiveOnly, String negativeOnly) {
        return apiCore.getSearchResults(keyword, slop, positiveOnly, negativeOnly, Endpoints.getCurrentDocInfo());
    }

}
