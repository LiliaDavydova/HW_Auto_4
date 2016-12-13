package ua.lilu.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ua.lilu.Randomizer;
import ua.lilu.model.PokerPlayer;
import ua.lilu.pages.EditPlayerPage;
import ua.lilu.pages.LoginPage;
import ua.lilu.pages.PlayersPage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lilu on 08.12.2016.
 */
public class SearchTests {

    WebDriver driver;
    LoginPage loginPage;
    PlayersPage playersPage;
    EditPlayerPage editPlayerPage;
    PokerPlayer player;

    /**
     * Preconditions:
     * 1. Open FireFox browser
     * 2. Open application Login Page URL = "http://80.92.229.236:81/auth/login"
     * 3. Set "admin" to Username
     * 4. Set "123" to Password
     * 5. Click on "Log In" button
     */
    @BeforeTest(groups = "functional")
    @Parameters({"validLogin", "validPassword"})
    public void beforeTest(String login, String password) {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String username = "ld" + Randomizer.generateRandomNumbers(3);
        String email = username + "@test.com";
        player = new PokerPlayer(username, email, "123456", "John",
                "Dou", "Kiev", "Shevchenka street", Randomizer.generateRandomNumbers(10));
        playersPage = new PlayersPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.open(driver);
        loginPage.setUsername(login);
        loginPage.setPassword(password);
        loginPage.clickLogin();
        editPlayerPage = new EditPlayerPage(driver);
        editPlayerPage.insertPlayer(player);
    }

    /**
     * Steps to reproduce:
     * 1. Search player by username
     * 2. Verify that player was found
     */
    @Test(groups = "functional")
    public void searchPlayerByUsernameTest() {
        playersPage.searchPlayerByUsername(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult(player);
        Assert.assertFalse(players.isEmpty(), "Player is NOT found by username");
    }

    /**
     * Steps to reproduce:
     * 1. Search player by email
     * 2. Verify that player was found
     */
    @Test(groups = "functional")
    public void searchPlayerByEmailTest() {
        playersPage.searchPlayerByEmail(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult(player);
        Assert.assertFalse(players.isEmpty(), "Player is NOT found by email");
    }

    /**
     * Steps to reproduce:
     * 1. Search player by first name
     * 2. Verify that player was found
     */
    @Test(groups = "functional")
    public void searchPlayerByFirstNameTest() {
        playersPage.searchPlayerByFirstName(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult();
        Assert.assertFalse(players.isEmpty(), "Player is NOT found by first name");
    }

    /**
     * Steps to reproduce:
     * 1. Search player by last name
     * 2. Verify that player was found
     */
    @Test(groups = "functional")
    public void searchPlayerByLastNameTest() {
        playersPage.searchPlayerByLastName(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult();
        Assert.assertFalse(players.isEmpty(), "Player is NOT found by last name");
    }

    /**
     * Steps to reproduce:
     * 1. Search player by city
     * 2. Verify that player was found
     */
    @Test(groups = "functional")
    public void searchPlayerByCityTest() {
        playersPage.searchPlayerByCity(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult();
        Assert.assertFalse(players.isEmpty(), "Player is NOT found by city");
    }

    /**
     * Postconditions:
     * 1. Search player by username
     * 2. Click on "Delete"
     * 3. Close FireFox tab
     */
    @AfterTest(groups = "functional")
    public void afterTest() {
        playersPage.searchPlayerByUsername(player);
        playersPage.clickDelete(player);
        driver.switchTo().alert().accept();
        driver.close();
    }
}
