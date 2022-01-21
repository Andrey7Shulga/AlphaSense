import config.ConfigReader;
import dto.SearchMainInfo;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        Response response = apiSteps.getSearchingInfo("AlphaSense", 15, false, false);
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void getKeyWordSearchingInfoToCheckBody () {
        String DOC_LABEL = ConfigReader.getInstance().getProperty("document");
        SearchMainInfo searchMainInfo = apiSteps
                .getSearchingInfo("AlphaSense", 15, false, false)
                .as(SearchMainInfo.class);

        assertThat(searchMainInfo.searchResults.statements.stream()
                .allMatch(e -> e.accessionNumber.equals(DOC_LABEL)))
                .isTrue();

    }
}
