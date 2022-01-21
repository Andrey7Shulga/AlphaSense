import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ApiSteps;

public class AlphaSenseApiTest {

    private ApiSteps apiSteps;

    @BeforeEach
    void beforeTest() {
        apiSteps = new ApiSteps();
    }

    @Test
    void getKeyWordSearchingInfo () {
        apiSteps.getSearchingInfo("AlphaSense", "15", false, false);
    }
}
