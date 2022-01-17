import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AlphaSense;

import java.time.Duration;

public class AlphaSensePageTest {

    private WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void beforeTest() {
        String url = "https://rc.alpha-sense.com/doc/PR-386ea743f2a90399fb0e4300ddf37d0697abc743";
        long timeout = 10;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        driver.get(url);
    }

    @After
    public void afterTest() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }



    @Test
    public void getTitle () {
        AlphaSense alphaSense = new AlphaSense(driver);
        alphaSense.insertTextToTextFieldJS("AlphaSense");
//        alphaSense.getTextFromElement();
    }

}
