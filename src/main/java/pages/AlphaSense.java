package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.HelperUI;

import java.util.List;
import java.util.stream.Collectors;

public class AlphaSense {

    private final HelperUI uiHelper;
    private static final Logger log = LoggerFactory.getLogger(AlphaSense.class);

    public final static By searchBox = By.cssSelector("[data-location=CustomSearchBox]");
    public final static By highLightedElement = By.cssSelector("span[class='hl']");
    public final static By articleFrame = By.cssSelector("#content-1");
    private final By textFoundElement = By.cssSelector("span[class*='row-blue']");
    private final By searchResults = By.cssSelector("[role=rowgroup] > div");


    public AlphaSense(WebDriver driver, WebDriverWait wait) {
        uiHelper = new HelperUI(driver, wait);
        if (!driver.getTitle().equals("AlphaSense")) {
            throw new IllegalStateException("The loaded page is not Alpha Sense required Page," +
                    " current page title is: " + driver.getTitle());
        }
    }

    public WebElement scrollToTheLastElement(long sleepTimeout) {
        List<WebElement> listOne, listTwo;
        WebElement el = uiHelper.getElementsList(searchResults).get(0);
        uiHelper.moveToElementToClick(el);
            do {
                listOne = uiHelper.getElementsList(searchResults);
                uiHelper.actionUseKeyBoardKey(Keys.PAGE_DOWN);
                uiHelper.sleep(sleepTimeout);
                listTwo = uiHelper.getElementsList(searchResults);
            } while (!listOne.equals(listTwo));
        return listTwo.get(listTwo.size()-1);
    }

    public List<String> getTextFromFoundElementsInViewer () {
        List<WebElement> list = uiHelper.getElementsList(textFoundElement);
        List<String> aList = list.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        aList.forEach(e -> log.info("Viewer's text found: '{}'", e));
        return aList;
    }
}
