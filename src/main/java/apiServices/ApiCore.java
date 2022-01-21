package apiServices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class ApiCore {

    private static final Logger log = LoggerFactory.getLogger(ApiCore.class);

    public Response getSearchResults(String keyword, Integer slop, boolean positiveOnly, boolean negativeOnly, String endpoint) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecification requestSpecification = apiServices.RequestSpec.baseRequestSpecJson();

        Map<String, Object> query = new HashMap<>();
        query.put("keyword", keyword);
        query.put("slop", slop);
        query.put("positiveOnly", positiveOnly);
        query.put("negativeOnly", negativeOnly);
        query.put("released", "1633003200000");

        log.info("keyword: {}", keyword);
        log.info("slop: {}", slop);
        log.info("positiveOnly: {}", positiveOnly);
        log.info("negativeOnly: {}", negativeOnly);

        return given()
                .spec(requestSpecification)
                .queryParams(query)
                .when().get(endpoint);

    }

}
