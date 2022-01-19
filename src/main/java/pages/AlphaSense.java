package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AlphaSense {

    private final WebDriverWait wait;
    private WebDriver driver;

    private final JavascriptExecutor js;
    private WebElement element;
    private final Actions actions;


    public AlphaSense(WebDriver driver) {
        long timeout = 15;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        this.driver = driver;
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        if (!driver.getTitle().equals("AlphaSense")) {
            throw new IllegalStateException("The loaded page is not Alpha Sense required Page," +
                    " current page title is: " + driver.getTitle());
        }
    }

    By searchBox = By.cssSelector("[data-location=CustomSearchBox]");
    By searchTitleHit = By.xpath("//div[./span[text()='TITLE HIT']]");

    By searchResults = By.cssSelector("[role=rowgroup]");
    By testElement = By.xpath("//*[@role='rowgroup']//*[contains(text(), 'Logo -')]");
    By searchBlock = By.cssSelector("[options='[object Object]']");


    By searchTextField = By.cssSelector("#searchInDocument");
    By searchTextField_1 = By.xpath("//*[contains(@class, 'CodeMirror-placeholder')]");
    By searchTextField_2 = By.xpath("//*[@role='presentation']//span[@role]");




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
        sleep(2000);
    }

    public void scrollToElementAndClick() {
        List<WebElement> list;
        try {
            do {
                actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(searchTitleHit)))
                        .click()
                        .sendKeys(Keys.PAGE_DOWN)
                        .build()
                        .perform();
                list = driver.findElements(testElement);
                sleep(500);
            } while (list.isEmpty());

            list.get(0).click();

            sleep(5000);

        } catch (NoSuchElementException | TimeoutException e) {
            throw new RuntimeException(String.format("Message: %S. Element's locator: '%s'", e, searchBox.toString()));
        }
    }


    public void insertTextToTextField_ (String text) {
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(searchTextField));
            System.out.println("Text before: " + element.getText());
            element.sendKeys(text);
            System.out.println("Text after clearing: " + element.getText());

        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField.toString()));
        }
    }

//    public void insertTextToTextField(String text) {
//        try {
//            element = wait.until(ExpectedConditions.elementToBeClickable(searchTextField_1));
//            System.out.println("Text before: " + element.getText());
//            element.clear();
//            System.out.println("Text after clearing: " + element.getText());
//
//            WebElement element2 = wait.until(ExpectedConditions.elementToBeClickable(searchTextField_2));
//            System.out.println("Text before: " + element2.getText());
//            element2.sendKeys(text);
//            System.out.println("Text after typing: " + element2.getText());
//        } catch (NoSuchElementException | TimeoutException e) {
//            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField_1.toString()));
//        }
//    }

    public void insertTextToTextFieldJS(String text) {
        try {
            //JS click and type text
            element = wait.until(ExpectedConditions.elementToBeClickable(searchTextField_2));
            js.executeScript("arguments[0].click();", element);
            js.executeScript("document.querySelector('[role=presentation] span[role]').value = 'AlphaSense'");
            js.executeScript("arguments[0].click();", element);
            Thread.sleep(10000);
        } catch (NoSuchElementException | TimeoutException | InterruptedException e) {
            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField_2.toString()));
        }
    }

    public void insertTextToTextFieldJS2(String text) {
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField_1));
            System.out.println("Text before: " + element.getText());
            js.executeScript("arguments[0].click();", element);
            js.executeScript("document.querySelector('[role=presentation] span[role]').value='" + text + "'");

//            js.executeScript("document.querySelector('.fusion-custom-menu-item-contents .s').value = 'SW Test Academy'");


            System.out.println("Text after: " + element.getText());

        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField_1.toString()));
        }
    }



    public void getPageTitle (WebDriver driver) {
        System.out.println(driver.getTitle());
    }

    public void getTextFromElement () {
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField_1));
        System.out.println("Text issss: " + element.getText());
    }

    private void sleep (int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
