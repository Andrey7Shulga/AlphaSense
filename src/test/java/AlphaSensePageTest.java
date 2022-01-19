import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AlphaSense;

import java.time.Duration;

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
    public void getTitle () {
        String expectedText;
        WebElement theLastElement;
        AlphaSense alphaSense = new AlphaSense(driver);

        alphaSense.sendTextToSearchBoxAndClick("AlphaSense");
        theLastElement = alphaSense.scrollToElementAndClick();
        expectedText = alphaSense.getTextFromChildHighLightedElement(theLastElement);
        alphaSense.clickOnElement(theLastElement);
//        Assert.assertTrue(alphaSense.getTextFromFoundElementsInViewer().contains(expectedText));
    }

}
