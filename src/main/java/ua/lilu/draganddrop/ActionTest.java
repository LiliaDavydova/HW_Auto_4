package ua.lilu.draganddrop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

/**
 * Created by Lilu on 08.12.2016.
 */
public class ActionTest {

    private WebDriver driver;
    private ActionPage actionPage;
    private SoftAssert softAssert;

    @BeforeTest
    public void beforeTest() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        actionPage = new ActionPage(driver);
        softAssert = new SoftAssert();
    }

    @Test
    public void dragAndDropTrashTest() {
        actionPage.open();
        WebElement item = actionPage.chooseElementFromList();
        actionPage.dragAndDropToTrash(item);
        String actualMessage = actionPage.getAlertMessage();
        String expectedMessage = "Are you sure that you want to delete?";
        softAssert.assertEquals(actualMessage, expectedMessage, "Wrong text in alert");
        actionPage.clickCancel();
        softAssert.assertTrue(actionPage.findElementInList(item));
        actionPage.dragAndDropToTrash(item);
        actionPage.clickOk();
        softAssert.assertFalse(actionPage.findElementInList(item));
        softAssert.assertAll();
    }

    @Test
    public void dragAndDropSortAscTest() {
        actionPage.open();
        actionPage.sortElementsAsc();
        int size = actionPage.getElementsNumber();
        for (int i = 1; i <= size; i++) {
            WebElement expectedElement = actionPage.findElementByText(i);
            WebElement actualElement = actionPage.findElementByPosition(i);
            softAssert.assertEquals(actualElement.getText(), expectedElement.getText(), "The list wasn't sorted in ascending order");
        }
        softAssert.assertAll();
    }

    @Test
    public void dragAndDropSortDescTest() {
        actionPage.open();
        actionPage.sortElementsDesc();
        int size = actionPage.getElementsNumber();
        int j = size;
        for (int i = 1; i <= size; i++) {
            WebElement expectedElement = actionPage.findElementByText(j);
            WebElement actualElement = actionPage.findElementByPosition(i);
            softAssert.assertEquals(actualElement.getText(), expectedElement.getText(), "The list wasn't sorted in descending order");
            j--;
        }
        softAssert.assertAll();
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}