package apiServices;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestSpec {

    private static final Logger log = LoggerFactory.getLogger(RequestSpec.class);

    private final String PARAM_URI = ConfigReader.getInstance().getProperty("param_uri");
    private final String APP_HEADER_JSON = ConfigReader.getInstance().getProperty("app_header_json");
    private final String BASE_URI;

    RequestSpec() {
        String var = System.getProperty("uriPath");
        BASE_URI = var == null ? ConfigReader.getInstance().getProperty("base_uri") : PARAM_URI + var;
    }

    public  RequestSpecification baseRequestSpecJson() {

        log.info("BASE_URI: {}", BASE_URI);
        log.info("APP_HEADER: {}", APP_HEADER_JSON);

        return new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .addHeader("accept", APP_HEADER_JSON)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

}
