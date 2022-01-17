import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Helper {

    private WebElement element;
    private WebDriver driver;
    private final WebDriverWait wait;
    private final long timeout = 10;


    public Helper (WebDriver driver) {
     this.driver = driver;
     wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public void sendKeysToElement(By by, String text) {
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            element.clear();
            element.sendKeys(text);
        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(String.format("Element is not found by '%s'", by.toString()));
        }
    }

    public void clickToElement(By by, String text) {
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(by));
            element.click();
        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(String.format("Element is not clickable by '%s'", by.toString()));
        }
    }


}
