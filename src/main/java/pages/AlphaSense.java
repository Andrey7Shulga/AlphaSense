package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlphaSense {

    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions actions;


    public AlphaSense(WebDriver driver) {
        long timeout = 15;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        if (!driver.getTitle().equals("AlphaSense")) {
            throw new IllegalStateException("The loaded page is not Alpha Sense required Page," +
                    " current page title is: " + driver.getTitle());
        }
    }

    By searchBox = By.cssSelector("[data-location=CustomSearchBox]");
    By searchResults = By.cssSelector("[role=rowgroup] > div");
    By highLightedElement = By.cssSelector("span[class='hl']");

//    By testElement = By.xpath("//*[@role='rowgroup']//*[contains(text(), 'Logo -')]");
//    By searchBlock = By.cssSelector("[options='[object Object]']");
//    By searchTextField = By.cssSelector("#searchInDocument");
//    By searchTextField_1 = By.xpath("//*[contains(@class, 'CodeMirror-placeholder')]");
//    By searchTextField_2 = By.xpath("//*[@role='presentation']//span[@role]");

    By articleFrame = By.cssSelector("#content-1");
    By textFoundElement = By.cssSelector("span[class*='row-blue']");


    public void sendTextToSearchBoxAndClick(String text) {
        try {
            actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(searchBox)))
                    .click()
                    .sendKeys(text + "\n")
                    .build()
                    .perform();
        } catch (NoSuchElementException | TimeoutException e) {
            throw new RuntimeException(String.format("Message: %S. Element's locator: '%s'", e, searchBox.toString()));
        }
    }


    public WebElement scrollToElementAndClick() {
            List<WebElement> listOne, listTwo;
            try {
                actions.moveToElement(getElementsList(searchResults).get(0))
                        .click()
                        .build()
                        .perform();
                do {
                    listOne = getElementsList(searchResults);
                    actions.sendKeys(Keys.PAGE_DOWN)
                            .build()
                            .perform();
                    listTwo = getElementsList(searchResults);;
                    sleep(500);
                } while (!listOne.equals(listTwo));
            return listTwo.get(listTwo.size()-1);
        } catch (NoSuchElementException | TimeoutException e) {
            throw new RuntimeException(String.format("Message: %S. Element's locator: '%s'", e, searchResults.toString()));
        }
    }

    private List<WebElement> getElementsList (By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public List<String> getTextFromFoundElementsInViewer () {
        List<WebElement> list = getElementsList(textFoundElement);
        return list.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
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


//    public void insertTextToTextFieldJS(String text) {
//        try {
//            //JS click and type text
//            element = wait.until(ExpectedConditions.elementToBeClickable(searchTextField_2));
//            js.executeScript("arguments[0].click();", element);
//            js.executeScript("document.querySelector('[role=presentation] span[role]').value = 'AlphaSense'");
//            js.executeScript("arguments[0].click();", element);
//            Thread.sleep(10000);
//        } catch (NoSuchElementException | TimeoutException | InterruptedException e) {
//            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField_2.toString()));
//        }
//    }
//
//    public void insertTextToTextFieldJS2(String text) {
//        try {
//            element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField_1));
//            System.out.println("Text before: " + element.getText());
//            js.executeScript("arguments[0].click();", element);
//            js.executeScript("document.querySelector('[role=presentation] span[role]').value='" + text + "'");
//
////            js.executeScript("document.querySelector('.fusion-custom-menu-item-contents .s').value = 'SW Test Academy'");
//
//
//            System.out.println("Text after: " + element.getText());
//
//        } catch (NoSuchElementException | TimeoutException e) {
//            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField_1.toString()));
//        }
//    }
//
//
//
//    public void getPageTitle (WebDriver driver) {
//        System.out.println(driver.getTitle());
//    }
//
//    public void getTextFromElement () {
//        element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField_1));
//        System.out.println("Text issss: " + element.getText());
//    }

    private void sleep (int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
