package ua.lilu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ua.lilu.model.PokerPlayer;

import java.util.List;

/**
 * Created by Lilu on 08.12.2016.
 */
public class PlayersPage {
    private WebDriver driver;

    @FindBy(id = "723a925886__login")
    private WebElement usernameInput;

    @FindBy(id = "723a925886__email")
    private WebElement emailInput;

    @FindBy(id = "723a925886__firstname")
    private WebElement firstNameInput;

    @FindBy(id = "723a925886__lastname")
    private WebElement lastNameInput;

    @FindBy(id = "723a925886__city")
    private WebElement cityInput;

    @FindBy(name = "reset")
    private WebElement resetButton;

    @FindBy(name = "search")
    private WebElement searchButton;

    @FindBy(xpath = "//*[contains(text(),'Player has been deleted')]")
    private WebElement successfulDeleteMsgElement;

    public PlayersPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickReset(){
        resetButton.click();
    }

    public void clickSearch() {
        searchButton.click();
    }

    public void searchPlayerByUsername(PokerPlayer player) {
        clickReset();
        usernameInput.sendKeys(player.getUsername());
        clickSearch();
    }

    public void searchPlayerByEmail(PokerPlayer player) {
        clickReset();
        emailInput.sendKeys(player.getEmail());
        clickSearch();
    }

    public void searchPlayerByFirstName(PokerPlayer player) {
        clickReset();
        firstNameInput.sendKeys(player.getFirstName());
        clickSearch();
    }

    public void searchPlayerByLastName(PokerPlayer player) {
        clickReset();
        lastNameInput.sendKeys(player.getLastName());
        clickSearch();
    }

    public void searchPlayerByCity(PokerPlayer player) {
        clickReset();
        cityInput.sendKeys(player.getCity());
        clickSearch();
    }

    public List<WebElement> selectPlayersFromSearchResult(){
        return driver.findElements(By.xpath(".//div[@id='datagrid_table__723a925886']/table[@class='datagrid_table']/*/tr"));
    }

    public List<WebElement> selectPlayersFromSearchResult(PokerPlayer player){
        return driver.findElements(By.xpath(".//a[.='" + player.getUsername() + "']"));
    }

    public void clickEdit(PokerPlayer player) {
        driver.findElement(By.xpath(".//tr[.//a[text()='" + player.getUsername() + "']]//img[@title='Edit']")).click();
    }

    public void clickDelete(PokerPlayer player) {
        driver.findElement(By.xpath(".//tr[.//a[text()='" + player.getUsername() + "']]//img[@title='Delete']")).click();
    }

    public WebElement getSuccessfulDeleteMsgElement() {
        return successfulDeleteMsgElement;
    }

    }
