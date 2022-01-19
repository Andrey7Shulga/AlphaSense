import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AlphaSense;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphaSensePageTest {

    private static WebDriver driver;
    private WebDriverWait wait;


    @BeforeClass
    public static void beforeClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void beforeTest() {
        String url = "https://rc.alpha-sense.com/doc/PR-386ea743f2a90399fb0e4300ddf37d0697abc743";
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }

    @AfterClass
    public static void afterTest() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }



    @Test
    public void checkTheSearchResult () {
        String expectedText;
        WebElement theLastElement;
        AlphaSense alphaSense = new AlphaSense(driver);

        //get the search results
        alphaSense.sendTextToSearchBoxAndClick("AlphaSense");
        //get the last element from searchList
        theLastElement = alphaSense.scrollToTheLastElement();
        //get the last element's text
        expectedText = alphaSense.getTextFromChildHighLightedElement(theLastElement);
        //click the last element to view some results
        alphaSense.clickOnElement(theLastElement);
        //switch to result document's frame
        alphaSense.switchToFrame();
        //assert that the expected text is highlighted
        assertThat(alphaSense.getTextFromFoundElementsInViewer()).anyMatch(a -> a.contains(expectedText));
    }

}
