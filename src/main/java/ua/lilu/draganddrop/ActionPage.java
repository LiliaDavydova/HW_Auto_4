package ua.lilu.draganddrop;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ua.lilu.Randomizer;

import java.io.File;
import java.util.List;

/**
 * Created by Lilu on 08.12.2016.
 */
public class ActionPage {

    @FindBy(xpath = ".//*[@id='sortable']/li")
    private List<WebElement> sourceElements;

    @FindBy(id = "drop")
    private WebElement trash;

    private WebDriver driver;
    private final String URL = new File("src/main/resources/drag_and_drop2/index.html").getAbsolutePath();

    public ActionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    public WebElement chooseElementFromList() {
        int index = Randomizer.generateRandomNumber(6);
        return sourceElements.get(index);
    }

    public void dragAndDropToTrash(WebElement sourceElement) {
        Actions action = new Actions(driver);
        action.dragAndDrop(sourceElement, trash)
                .perform();
    }

    public void dragAndDrop(WebElement sourceElement, WebElement targetElement) {
        Actions action = new Actions(driver);
        action.dragAndDrop(sourceElement, targetElement)
                .perform();
    }

    public String getAlertMessage() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void clickCancel() {
        driver.switchTo().alert().dismiss();
    }

    public boolean findElementInList(WebElement item) {
        return sourceElements.contains(item);
    }

    public void clickOk() {
        driver.switchTo().alert().accept();
    }

    public int getElementsNumber() {
        return sourceElements.size();
    }

    public WebElement findElementByText(int i){
        return driver.findElement(By.xpath(".//*[@id='sortable']/li[text()=" + i + "]"));
    }

    public WebElement findElementByPosition(int i) {
        return driver.findElement(By.xpath(".//*[@id='sortable']/li[" + i + "]"));
    }

    public void sortElementsAsc() {
        int size = getElementsNumber();
        for (int i = 1; i <= size; i++) {
            WebElement element = findElementByText(i);
            WebElement targetElement = findElementByPosition(i);
            dragAndDrop(element, targetElement);
        }
    }

    public void sortElementsDesc() {
        int size = getElementsNumber();
        int j = size;
        for (int i = 1; i <= size; i++) {
            WebElement element = findElementByText(j);
            WebElement targetElement = findElementByPosition(i);
            dragAndDrop(element, targetElement);
            j--;
        }
    }
}