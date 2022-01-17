package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlphaSense {

    private WebDriverWait wait;
    private JavascriptExecutor js;
    private final long timeout = 10;
    private WebElement element;


    public AlphaSense(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        js = (JavascriptExecutor) driver;
        if (!driver.getTitle().equals("AlphaSense")) {
            throw new IllegalStateException("The loaded page is not Alpha Sense required Page," +
                    " current page title is: " + driver.getTitle());
        }
    }

//    By searchTextField = By.cssSelector("#searchInDocument");
//    By searchTextField = By.xpath("//*[contains(@class, 'CodeMirror-placeholder')]");
    By searchTextField = By.xpath("//span[@role='presentation']");




    public void insertTextToTextField(String text) {
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField));
            System.out.println("Text before: " + element.getText());
//            element.clear();
            element.sendKeys(text);
            System.out.println("Text after: " + element.getText());
        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField.toString()));
        }
    }

    public void insertTextToTextFieldJS(String text) {
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField));
            System.out.println("Text before: " + element.getText());
            String text2 = "arguments[0].setAttribute('value','" + text + "')";
            js.executeScript(text2, element);
            System.out.println("Text after: " + element.getText());


//            String js1 = "arguments[0].setAttribute('value','"+text+"')";
//            js.executeScript(js1, element);

        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField.toString()));
        }
    }

    public void insertTextToTextFieldJS2(String text) {
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField));
            System.out.println("Text before: " + element.getText());
            js.executeScript("arguments[0].click();", element);
            js.executeScript("document.querySelector('*[class*=CodeMirror-placeholder]').value='" + text + "'");

//            js.executeScript("document.querySelector('.fusion-custom-menu-item-contents .s').value = 'SW Test Academy'");


            System.out.println("Text after: " + element.getText());

        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException(String.format("Element is not found by '%s'", searchTextField.toString()));
        }
    }



    public void getPageTitle (WebDriver driver) {
        System.out.println(driver.getTitle());
    }

    public void getTextFromElement () {
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextField));
        System.out.println("Text issss: " + element.getText());
    }

}
