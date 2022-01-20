import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AlphaSense;
import services.UI_Helper;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphaSensePageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private UI_Helper uiHelper;

    private final static String TEST_URL = ConfigReader.getInstance().getProperty("testUI_url");
    private final static String WAIT_TIMEOUT = ConfigReader.getInstance().getProperty("waitTimeOut");

    @BeforeAll
    static void beforeClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
     void beforeTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(WAIT_TIMEOUT)));
        driver.manage().window().maximize();
        uiHelper = new UI_Helper(driver, wait);
        driver.get(TEST_URL);
    }

    @AfterEach
     void afterTest() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }


    @Test
     void checkTheSearchResult () {
        String expectedText;
        WebElement theLastElement;
        AlphaSense alphaSense = new AlphaSense(driver, wait);

        //get the search results
        uiHelper.moveToElementToClickAndSendText(AlphaSense.searchBox, "AlphaSense");
        //get the last element from searchList
        theLastElement = alphaSense.scrollToTheLastElement(2000);
        //get the last element's highlighted text
        expectedText = uiHelper.getTextFromChildElement(theLastElement, AlphaSense.highLightedElement);
        //click the last element to view results
        uiHelper.clickOnElement(theLastElement);
        //switch to result document's frame
        uiHelper.switchToFrame(AlphaSense.articleFrame);
        //assert that the expected text is highlighted
        assertThat(alphaSense.getTextFromFoundElementsInViewer()).anyMatch(a -> a.contains(expectedText));
    }

}
