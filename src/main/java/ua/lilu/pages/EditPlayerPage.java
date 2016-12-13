package ua.lilu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ua.lilu.model.PokerPlayer;

/**
 * Created by Lilu on 08.12.2016.
 */
public class EditPlayerPage {
    public static final String INSERT_URL = "http://80.92.229.236:81/players/insert";
    private WebDriver driver;

    @FindBy(id = "ff14642ac1c__us_login")
    private WebElement usernameInput;

    @FindBy(id = "ff14642ac1c__us_email")
    private WebElement emailInput;

    @FindBy(id = "ff14642ac1c__us_password")
    private WebElement passwordInput;

    @FindBy(id = "ff14642ac1c__confirm_password")
    private WebElement confirmPasswordInput;

    @FindBy(id = "ff14642ac1c__us_fname")
    private WebElement firstNameInput;

    @FindBy(id = "ff14642ac1c__us_lname")
    private WebElement lastNameInput;

    @FindBy(id = "ff14642ac1c__us_city")
    private WebElement cityInput;

    @FindBy(id = "ff14642ac1c__us_address")
    private WebElement addressInput;

    @FindBy(id = "ff14642ac1c__us_phone")
    private WebElement phoneInput;

    @FindBy(linkText = "Insert")
    private WebElement insertButton;

    @FindBy(name = "button_save")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class='form_errors_container']/ul")
    private WebElement errorMsgElement;

    @FindBy(id = "filter_panel_buttons")
    private WebElement resetButton;

    public EditPlayerPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void setUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void setEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void setFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void setCity(String city) {
        cityInput.clear();
        cityInput.sendKeys(city);
    }

    public void setAddress(String address) {
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    public void setPhone(String phone) {
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void clickSave() {
        saveButton.click();
    }

    public void insertPlayer(PokerPlayer player) {
        insertButton.click();
        setUsername(player.getUsername());
        setEmail(player.getEmail());
        setPassword(player.getPassword());
        setConfirmPassword(player.getConfirmPassword());
        setFirstName(player.getFirstName());
        setLastName(player.getLastName());
        setCity(player.getCity());
        setAddress(player.getAddress());
        setPhone(player.getPhone());
        clickSave();
    }

    public PokerPlayer getPlayerFromEditForm(){
        PokerPlayer player = new PokerPlayer();
        player.setEmail(emailInput.getAttribute("value"));
        player.setFirstName(firstNameInput.getAttribute("value"));
        player.setLastName(lastNameInput.getAttribute("value"));
        player.setCity(cityInput.getAttribute("value"));
        player.setAddress(addressInput.getAttribute("value"));
        player.setPhone(phoneInput.getAttribute("value"));
        return player;
    }

    public void editPlayer(PokerPlayer player) {
        setEmail(player.getEmail());
        setFirstName(player.getFirstName());
        setLastName(player.getLastName());
        setCity(player.getCity());
        setAddress(player.getAddress());
        setPhone(player.getPhone());
        clickSave();
    }

    public String getErrorMessage() {
        return errorMsgElement.getText();
    }
}

