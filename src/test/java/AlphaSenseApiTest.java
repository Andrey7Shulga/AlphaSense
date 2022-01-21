import config.ConfigReader;
import dto.SearchMainInfo;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import services.ApiSteps;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphaSenseApiTest {

    private ApiSteps apiSteps;

    @BeforeEach
    void beforeTest() {
        apiSteps = new ApiSteps();
    }

    @Test
    void getKeyWordSearchingInfoToCheckStatusCode () {
        Response response = apiSteps.getSearchingInfo("AlphaSense", 15, "false", "false");
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void getKeyWordSearchingInfoToCheckBody () {
        String DOC_LABEL = ConfigReader.getInstance().getProperty("document");
        SearchMainInfo searchMainInfo = apiSteps
                .getSearchingInfo("AlphaSense", 15, "false", "false")
                .as(SearchMainInfo.class);

        assertThat(searchMainInfo.searchResults.statements.stream()
                .allMatch(e -> e.accessionNumber.equals(DOC_LABEL)))
                .isTrue();

    }

    @ParameterizedTest(name = "[{index}] -> ''{0}'', ''{1}'', ''{2}'', ''{3}''")
    @DisplayName("Bad Requests")
    @CsvSource({
            ", 15, false, false, 400",
            "AlphaSense, , false, false, 400",
            "AlphaSense, -125874, false, false, 500",
            "AlphaSense, 20.145, false, false, 400",

            "AlphaSense, 15, true, true, 400",
            ", , false, false, 400",
            ", , , , 400",

//            "AlphaSense, 15, 1!3, false, 400",
//            "AlphaSense, 15, false, 123, 400",
//            "AlphaSense, 15, 123, !, 400",
//            "AlphaSense, 15, true, @34, 400",
//            "AlphaSense, 15, 1!3, true, 400",
            "AlphaSense, 15, , , 400",

    })
    void sendBadRequestToCheckStatusCode (String keyword, Object slop, String positiveOnly, String negativeOnly, Integer code) {
        Response response = apiSteps.getSearchingInfo(keyword, slop, positiveOnly, negativeOnly);
        assertThat(response.statusCode()).isEqualTo(code);
    }
}
