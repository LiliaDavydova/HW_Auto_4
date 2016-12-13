package ua.lilu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Lilu on 08.12.2016.
 */
public class LoginPage {

    public static final String URL = "http://80.92.229.236:81/auth/login";
    public static final String INVALID_CREDENTIALS_ERROR_MSG = "Invalid username or password";
    public static final String EMPTY_CREDENTIALS_ERROR_MSG = "Value is required and can't be empty";
    private WebDriver driver;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "logIn")
    private WebElement logInButton;

    @FindBy(xpath = "//*[@id='username-element']/ul[@class='errors']/li")
    private WebElement errorUsernameMsgElement;

    @FindBy(xpath = "//*[@id='password-element']/ul[@class='errors']/li")
    private WebElement errorPasswordMsgElement;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void open(WebDriver driver) {
        driver.get(URL);
    }

    public void setUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        logInButton.click();
    }

    public String getErrorLoginMessage() {
        return errorUsernameMsgElement.getText();
    }

    public String getErrorPasswordMessage() {
        return errorPasswordMsgElement.getText();
    }
}
