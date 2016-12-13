package ua.lilu.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import ua.lilu.pages.LoginPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by Lilu on 08.12.2016.
 */
public class LoginTests {

    WebDriver driver;
    LoginPage loginPage;

    /**
     * Preconditions:
     * 1. Open FireFox browser
     */
    @BeforeTest(groups = {"validation", "functional"})
    public void beforeTest() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Preconditions:
     * 1.  Open application Login Page URL = "http://80.92.229.236:81/auth/login
     */
    @BeforeMethod(groups = {"validation", "functional"})
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        loginPage.open(driver);
    }

    @DataProvider
    public Object[][] loginData() {
        return new Object[][] {
                {"admin", "1234", "Login", "Invalid username or password"},
                {"admin123", "123", "Login", "Invalid username or password"}
        };
    }

    /**
     * Steps to reproduce:
     * 1. Set "admin" to Username
     * 2. Set "123" to Password
     * 3. Click on "Log In" button
     * 4. Verify that title of the page equals to "Players"
     */
    @Parameters({"validLogin", "validPassword", "ttl"})
    @Test(groups = "functional")
    public void positiveTest(String username, String password, String title) {
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickLogin();
        Assert.assertEquals(driver.getTitle(), title, "Wrong title after login");
        Assert.assertNotEquals(driver.getCurrentUrl(), LoginPage.URL, "You are still on login page.");
    }

    /**
     * Steps to reproduce:
     * 1. Set "admin" to Username
     * 2. Set "12345" to Password
     * 3. Click on "Log In" button
     * 4. Verify that title of the page equals to "Login"
     * 5. Verify that "Validation error message is not valid." message is displayed
     */
    @Test(dataProvider = "loginData", groups = "validation")
    public void negativeTestWrongUsernamePasssord(String username, String password, String title, String expectedMsg) {
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickLogin();

        String actualMsg = loginPage.getErrorLoginMessage();
        Assert.assertEquals(driver.getCurrentUrl(), LoginPage.URL, "You are NOT on login page.");
        Assert.assertEquals(driver.getTitle(), title, "Wrong title after unsuccessful login");
        Assert.assertEquals(actualMsg, expectedMsg, "Validation error message is not valid.");
    }

    /**
     * Steps to reproduce:
     * 1. Username is blank
     * 2. Password is blank
     * 3. Click on "Log In" button
     * 4. Verify that title of the page equals to "Login"
     * 5. Verify that "Validation error message is not valid." message is displayed
     */
    @Test(groups = "validation")
    public void negativeTestEmptyFields() {
        loginPage.setUsername("");
        loginPage.setPassword("");
        loginPage.clickLogin();

        String expectedMsg = LoginPage.EMPTY_CREDENTIALS_ERROR_MSG;
        String actualUsernameMsg = loginPage.getErrorLoginMessage();
        String actualPasswordMsg = loginPage.getErrorPasswordMessage();
        Assert.assertEquals(driver.getCurrentUrl(), LoginPage.URL, "You are NOT on login page.");
        Assert.assertEquals(driver.getTitle(), "Login", "Wrong title after unsuccessful login");
        Assert.assertEquals(actualUsernameMsg, expectedMsg, "Validation error message is not valid.");
        Assert.assertEquals(actualPasswordMsg, expectedMsg, "Validation error message is not valid.");
    }

    /**
     * Postconditions:
     * 1. Close FireFox tab
     */
    @AfterTest(groups = {"validation", "functional"})
    public void afterTest() {
        driver.close();
    }

}
