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

    private final static String BASE_URI = ConfigReader.getInstance().getProperty("base_uri");
    private final static String APP_HEADER_JSON = ConfigReader.getInstance().getProperty("app_header_json");

    public static RequestSpecification baseRequestSpecJson() {

        log.debug("BASE_URI: {}", BASE_URI);
        log.debug("APP_HEADER: {}", APP_HEADER_JSON);

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
