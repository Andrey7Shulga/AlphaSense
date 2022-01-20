package services;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class UI_Helper {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private WebElement element;
    private final Actions actions;

    public UI_Helper (WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
    }

    public WebElement collectClickableWebElement (By locator) {
        Objects.requireNonNull(locator);
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            throw new RuntimeException(String.format("Message: %S. Element's locator: '%s'", e, locator));
        }
    }

    public List<WebElement> getElementsList (By locator) {
        Objects.requireNonNull(locator);
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            throw new RuntimeException(String.format("Message: %S. Element's locator: '%s'", e, locator));
        }
    }

    public void switchToFrame (By iFrameBy) {
        WebElement iFrame = wait.until(ExpectedConditions.presenceOfElementLocated(iFrameBy));
        driver.switchTo().frame(iFrame);
    }

    public void moveToElementToClickAndSendText(By elementBy, String text) {
            actions.moveToElement(collectClickableWebElement(elementBy))
                    .click()
                    .sendKeys(text + "\n")
                    .build()
                    .perform();
    }

    public void moveToElementToClick(WebElement element) {
            actions.moveToElement(element)
                    .click()
                    .build()
                    .perform();
    }

    public void actionUseKeyBoardKey(Keys key) {
        actions.sendKeys(key).build().perform();
    }

    public String getTextFromChildElement(WebElement parent, By child) {
        Objects.requireNonNull(parent);
        try {
            return parent.findElement(child).getText();
        } catch (NoSuchElementException e) {
            throw new RuntimeException(String.format("Element is not found. Element's locator: '%s'", child));
        }
    }

    public void clickOnElement (WebElement element) {
        Objects.requireNonNull(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException | NoSuchElementException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public void sleep (int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
