import com.fasterxml.jackson.databind.JsonNode;
import config.ConfigReader;
import dto.SearchMainInfo;
import dto.Statements;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import services.HelperAPI;
import services.ApiSteps;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphaSenseApiTest {
    private ApiSteps apiSteps;
    private HelperAPI apiHelper;

    @BeforeEach
    void beforeTest() {
        apiHelper = new HelperAPI();
        apiSteps = new ApiSteps();
    }

    @Test
    void getKeyWordSearchingInfoToCheckStatusCode () {
        Response response = apiSteps.getSearchingInfo("AlphaSense", 15, "false", "false");
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void getKeyWordSearchingInfoToCheckBody_DocumentLabel () {
        String DOC_LABEL = ConfigReader.getInstance().getProperty("document");
        SearchMainInfo searchMainInfo = apiSteps
                .getSearchingInfo("AlphaSense", 15, "false", "false")
                .as(SearchMainInfo.class);
        assertThat(searchMainInfo.searchResults.statements.stream()
                .allMatch(e -> e.accessionNumber.equals(DOC_LABEL)))
                .isTrue();
    }

    @Test
    @DisplayName("Checking if any search result has a keyWord")
    void getKeyWordSearchingInfoToCheckBodyForKeyWord () {
        String keyWord = "AlphaSense";
        SearchMainInfo searchMainInfo = apiSteps
            .getSearchingInfo("AlphaSense", 15, "false", "false")
            .as(SearchMainInfo.class);
        //derive a desirable collection
        Collection<Statements> responseList =  searchMainInfo.searchResults.statements;
        //filter a collection to have only useful content, without 'TITLE HIT'
        Collection<Statements> filteredList =
                responseList.stream()
                .filter(e -> e.collapsedStatements.stream().noneMatch(a -> a.equals("fse1")))
                .collect(Collectors.toList());
        assertThat(filteredList.stream()
                .allMatch(e -> e.content.toLowerCase().contains(keyWord.toLowerCase())))
                .isTrue();
    }

    @ParameterizedTest(name = "[{index}] -> ''{0}'', ''{1}''")
    @DisplayName("PositiveOnly and NegativeOnly combinations")
    @CsvSource({
            "false, false, 200",
            "true, false, 200",
            "false, true, 200",
            "true, true, 400"
    })
    void checkPositiveAndNegativeCombinations (String positiveOnly, String negativeOnly, Integer code) {
        Response response = apiSteps.getSearchingInfo("AlphaSense", "15", positiveOnly, negativeOnly);
        assertThat(response.statusCode()).isEqualTo(code);
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
            "AlphaSense, 15, 1!3, false, 400",
            "AlphaSense, 15, false, 123, 400",
            "AlphaSense, 15, 123, !, 400",
            "AlphaSense, 15, true, @34, 400",
            "AlphaSense, 15, 1!3, true, 400",
            "AlphaSense, 15, , , 400",
    })
    void sendBadRequestToCheckStatusCode (String keyword, Object slop, String positiveOnly, String negativeOnly, Integer code) {
        Response response = apiSteps.getSearchingInfo(keyword, slop, positiveOnly, negativeOnly);
        assertThat(response.statusCode()).isEqualTo(code);
    }
}