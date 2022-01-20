package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import services.UI_Helper;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlphaSense {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    private UI_Helper uiHelper;

    public final static By searchBox = By.cssSelector("[data-location=CustomSearchBox]");
    private final By searchResults = By.cssSelector("[role=rowgroup] > div");
    public final static By highLightedElement = By.cssSelector("span[class='hl']");
    public final static By articleFrame = By.cssSelector("#content-1");
    private final By textFoundElement = By.cssSelector("span[class*='row-blue']");

    public AlphaSense(WebDriver driver) {
        long timeout = 15;
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        uiHelper = new UI_Helper(driver, wait);
        actions = new Actions(driver);
        if (!driver.getTitle().equals("AlphaSense")) {
            throw new IllegalStateException("The loaded page is not Alpha Sense required Page," +
                    " current page title is: " + driver.getTitle());
        }
    }

    public void sendTextToSearchBoxAndClick(String text) {
//        try {
//            actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(searchBox)))
//                    .click()
//                    .sendKeys(text + "\n")
//                    .build()
//                    .perform();
//        } catch (NoSuchElementException | TimeoutException e) {
//            throw new RuntimeException(String.format("Message: %S. Element's locator: '%s'", e, searchBox.toString()));
//        }

    }

    public void switchToFrame () {
        WebElement iFrame = wait.until(ExpectedConditions.presenceOfElementLocated(articleFrame));
        driver.switchTo().frame(iFrame);
    }


//    public WebElement scrollToTheLastElement() {
//            List<WebElement> listOne, listTwo;
//            try {
//                actions.moveToElement(getElementsList(searchResults).get(0))
//                        .click()
//                        .build()
//                        .perform();
//                do {
//                    listOne = getElementsList(searchResults);
//                    actions.sendKeys(Keys.PAGE_DOWN)
//                            .build()
//                            .perform();
//                    listTwo = getElementsList(searchResults);;
//                    sleep(500);
//                } while (!listOne.equals(listTwo));
//            return listTwo.get(listTwo.size()-1);
//        } catch (NoSuchElementException | TimeoutException e) {
//            throw new RuntimeException(String.format("Message: %S. Element's locator: '%s'", e, searchResults.toString()));
//        }
//    }

    public WebElement scrollToTheLastElement() {
        List<WebElement> listOne, listTwo;
        WebElement el = uiHelper.getElementsList(searchResults).get(0);
        uiHelper.moveToElementToClick(el);
            do {
                listOne = uiHelper.getElementsList(searchResults);
                uiHelper.actionUseKeyBoardKey(Keys.PAGE_DOWN);
                uiHelper.sleep(2000);
                listTwo = uiHelper.getElementsList(searchResults);
            } while (!listOne.equals(listTwo));
        return listTwo.get(listTwo.size()-1);
    }

    private List<WebElement> getElementsList (By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<String> getTextFromFoundElementsInViewer () {
        List<WebElement> list = uiHelper.getElementsList(textFoundElement);

        List<String> alist = list.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        for (String a : alist) {
            System.out.println("Найденный текст: " + a);
        }
        return alist;
    }

    public void clickOnElement (WebElement element) {
        Objects.requireNonNull(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException | NoSuchElementException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String getTextFromChildHighLightedElement (WebElement parent) {
        Objects.requireNonNull(parent);
        return parent.findElement(highLightedElement).getText();
    }

    private void sleep (int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
