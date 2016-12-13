package ua.lilu.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import ua.lilu.Randomizer;
import ua.lilu.model.PokerPlayer;
import ua.lilu.pages.EditPlayerPage;
import ua.lilu.pages.LoginPage;
import ua.lilu.pages.PlayersPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lilu on 08.12.2016.
 */
public class CRUDUserTests {
    WebDriver driver;
    LoginPage loginPage;
    PlayersPage playersPage;
    EditPlayerPage editPlayerPage;
    PokerPlayer player;
    
    private SoftAssert softAssert = new SoftAssert();

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
        String username = "ld" + Randomizer.generateRandomNumbers(3);
        String email = username + "@test.com";
        player = new PokerPlayer(username, email, "123456", "John",
                "Dou", "Kiev", "Shevchenka street", Randomizer.generateRandomNumbers(10));
    }

    /**
     * Preconditions:
     * 1. Open application Login Page URL = "http://80.92.229.236:81/auth/login"
     * 2. Set "admin" to Username
     * 3. Set "123" to Password
     * 4. Click on "Log In" button
     */
    @BeforeMethod(groups = {"validation", "functional"})
    @Parameters({"validLogin", "validPassword"})
    public void beforeMethod(String validLogin, String validPassword) {
        playersPage = new PlayersPage(driver);
        editPlayerPage = new EditPlayerPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.open(driver);
        loginPage.setUsername(validLogin);
        loginPage.setPassword(validPassword);
        loginPage.clickLogin();
    }

    @DataProvider
    public Object[][] invalidEmailPlayerData() {
        return new Object[][] {
                {Randomizer.generateRandomNumbers(9), "invalidEmail@", "123456", "John",
                        "Dou", "Kiev", "Shevchenka street", "123456789"},
                {Randomizer.generateRandomNumbers(9), "invalidEmailcom.ua", "123456", "John",
                        "Dou", "Kiev", "Shevchenka street", "123456789"}
        };
    }

    /**
     * Steps to reproduce:
     * 1. Click on "Insert"
     * 2. Set new username to Username
     * 3. Set new email to E-mail
     * 4. Set new password to Password
     * 5. Set the same password to Confirm Password
     * 6. Set new first name to First Name
     * 7. Set new last name to Last Name
     * 8. Set city to City
     * 9. Set address to Address
     * 10. Set phone to Phone
     * 11. Click on "Save" button
     * 12. Verify that title of the page equals to "Players" after saving new player
     * 13. Search player by username
     * 14. Click "Edit"
     * 15. Verify that Email equals to Email
     * 16. Verify that First Name equals to First Name
     * 17. Verify that Last Name equals to Last Name
     * 18. Verify that City equals to City
     * 19. Verify that Address equals to Address
     * 20. Verify that Phone equals to Phone
     */
    @Test(groups = "functional")
    public void createPlayerPositiveTest() {
        editPlayerPage.insertPlayer(player);
        String actualTitle = driver.getTitle();
        String expectedTitle = "Players";
        softAssert.assertEquals(actualTitle, expectedTitle, "Wrong title after save Player");
        playersPage.searchPlayerByUsername(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult(player);
        softAssert.assertFalse(players.isEmpty(), "Player is NOT found");
        softAssert.assertEquals(1, players.size(), "More than one player is found");
        playersPage.clickEdit(player);
        PokerPlayer actualPlayer = editPlayerPage.getPlayerFromEditForm();
        softAssert.assertEquals(actualPlayer.getEmail(), player.getEmail(), "Wrong Email for created player");
        softAssert.assertEquals(actualPlayer.getFirstName(), player.getFirstName(), "Wrong First Name for created player");
        softAssert.assertEquals(actualPlayer.getLastName(), player.getLastName(), "Wrong Last Name for created player");
        softAssert.assertEquals(actualPlayer.getCity(), player.getCity(), "Wrong City for created player");
        softAssert.assertEquals(actualPlayer.getAddress(), player.getAddress(), "Wrong Address for created player");
        softAssert.assertEquals(actualPlayer.getPhone(), player.getPhone(), "Wrong Phone for created player");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Click on "Insert"
     * 2. Click on "Save" button (don't enter any value)
     * 3. Verify that title of the page equals to "Players - Insert"
     * 4. Verify that "Error message for empty username is absent." message is displayed
     * 5. Verify that "Error message for empty email is absent." message is displayed
     * 6. Verify that "Error message for empty password is absent." message is displayed
     * 7. Verify that "Error message for empty confirm password is absent." message is displayed
     */
    @Test(groups = "validation")
    public void createEmptyPlayerNegativeTest() {
        PokerPlayer invalidPlayer = new PokerPlayer();
        editPlayerPage.insertPlayer(invalidPlayer);
        softAssert.assertEquals(driver.getCurrentUrl(), EditPlayerPage.INSERT_URL, "You are NOT on insert page.");
        softAssert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after unsuccessful insert");
        String errorMessage = editPlayerPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("Username: Value is required and can't be empty"), "Error message for empty username is absent.");
        softAssert.assertTrue(errorMessage.contains("E-mail: Value is required and can't be empty"), "Error message for empty email is absent.");
        softAssert.assertTrue(errorMessage.contains("Password: Value is required and can't be empty"), "Error message for empty password is absent.");
        softAssert.assertTrue(errorMessage.contains("Confirm Password: Value is required and can't be empty"), "Error message for empty confirm password is absent.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Click on "Insert"
     * 2. Set valid username to Username
     * 3. Set invalid email to E-mail
     * 4. Set valid password to Password
     * 5. Set valid confirm password to Confirm Password
     * 6. Set valid first name to First Name
     * 7. Set valid last name to Last Name
     * 8. Set valid city to City
     * 9. Set valid address to Address
     * 10. Set valid phone to Phone
     * 11. Click on "Save" button
     * 12. Verify that title of the page equals to "Players - Insert"
     * 13. Verify that message about invalid email is displayed
     */
    @Test(dataProvider = "invalidEmailPlayerData", groups = "validation")
    public void createInvalidEmailNegativeTest(String username, String email, String password, String firstName, String lastName, String city, String address, String phone) {
        PokerPlayer invalidPlayer = new PokerPlayer(username, email, password, firstName, lastName, city, address, phone);
        editPlayerPage.insertPlayer(invalidPlayer);
        softAssert.assertEquals(driver.getCurrentUrl(), EditPlayerPage.INSERT_URL, "You are NOT on insert page.");
        softAssert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after unsuccessful insert");
        String errorMessage = editPlayerPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("E-mail: '" + email + "' is no valid email address in the basic format local-part@hostname"), "Error message for invalid email is absent.");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Click on "Insert"
     * 2. Set valid username to Username
     * 3. Set valid email to E-mail
     * 4. Set some password to Password
     * 5. Set other password to Confirm Password
     * 6. Set valid first name to First Name
     * 7. Set valid last name to Last Name
     * 8. Set valid city to City
     * 9. Set valid address to Address
     * 10. Set valid phone to Phone
     * 11. Click on "Save" button
     * 12. Verify that title of the page equals to "Players - Insert"
     * 13. Verify that message that passwords are different is displayed
     */
    @Test(groups = "validation")
    public void confirmPasswordNegativeTest() {
        PokerPlayer invalidPlayer = new PokerPlayer(Randomizer.generateRandomNumbers(9), "invalidEmail", "123456", "John",
                "Dou", "Kiev", "Shevchenka street", "123456789");
        invalidPlayer.setConfirmPassword("654321");
        editPlayerPage.insertPlayer(invalidPlayer);
        softAssert.assertEquals(driver.getCurrentUrl(), EditPlayerPage.INSERT_URL, "You are NOT on insert page.");
        softAssert.assertEquals(driver.getTitle(), "Players - Insert", "Wrong title after unsuccessful insert");
        String errorMessage = editPlayerPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("Confirm Password: Does not match Password"), "Confirm Password should be the same as Password");
        softAssert.assertAll();
    }


    /**
     * Steps to reproduce:
     * 1. Search player by username
     * 2. Click "Edit"
     * 3. Set new email to E-mail
     * 4. Set new first name to First Name
     * 5. Set new last name to Last Name
     * 6. Set new city to City
     * 7. Set new address to Address
     * 8. Set new phone to Phone
     * 9. Click on "Save" button
     * 10. Search player by username
     * 11. Verify that Email equals to Email
     * 12. Verify that First Name equals to First Name
     * 13. Verify that Last Name equals to Last Name
     * 14. Verify that City equals to City
     * 15. Verify that Address equals to Address
     * 16. Verify that Phone equals to Phone
     */
    @Test(groups = "functional", dependsOnMethods = "createPlayerPositiveTest", alwaysRun = true)
    public void editPlayerPositiveTest() {
        playersPage.searchPlayerByUsername(player);
        playersPage.clickEdit(player);
        player.setEmail(player.getUsername() + "@mail.ru");
        player.setFirstName("Alice");
        player.setLastName("West");
        player.setCity("Kharkov");
        player.setAddress("Nauky street");
        player.setPhone("0971234567");
        editPlayerPage.editPlayer(player);
        playersPage.searchPlayerByUsername(player);
        playersPage.clickEdit(player);
        PokerPlayer actualPlayer = editPlayerPage.getPlayerFromEditForm();
        softAssert.assertEquals(actualPlayer.getEmail(), player.getEmail(), "Wrong Email after edit");
        softAssert.assertEquals(actualPlayer.getFirstName(), player.getFirstName(), "Wrong First Name after edit");
        softAssert.assertEquals(actualPlayer.getLastName(), player.getLastName(), "Wrong Last Name after edit");
        softAssert.assertEquals(actualPlayer.getCity(), player.getCity(), "Wrong City after edit");
        softAssert.assertEquals(actualPlayer.getAddress(), player.getAddress(), "Wrong Address after edit");
        softAssert.assertEquals(actualPlayer.getPhone(), player.getPhone(), "Wrong Phone after edit");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Search player by username
     * 2. Click "Delete"
     * 3. Click "Cancel" on Alert
     * 4. Search player by username
     * 5. Verify player was found
     */
    @Test(groups = "functional", dependsOnMethods = "editPlayerPositiveTest", alwaysRun = true)
    public void deletePlayerNegativeTest() {
        playersPage.searchPlayerByUsername(player);
        playersPage.clickDelete(player);
        driver.switchTo().alert().dismiss();
        playersPage.searchPlayerByUsername(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult(player);
        softAssert.assertFalse(players.isEmpty(), "Player must NOT be deleted");
        softAssert.assertAll();
    }

    /**
     * Steps to reproduce:
     * 1. Search player by username
     * 2. Click "Delete"
     * 3. Click "Ok" on Alert
     * 4. Verify message equals to "Player has been deleted"
     * 5. Search player by username
     * 6. Verify player was not found
     */
    @Test(groups = "functional", dependsOnMethods = "deletePlayerNegativeTest", alwaysRun = true)
    public void deletePlayerPositiveTest() {
        playersPage.searchPlayerByUsername(player);
        playersPage.clickDelete(player);
        driver.switchTo().alert().accept();
        WebElement element = playersPage.getSuccessfulDeleteMsgElement();
        softAssert.assertNotNull(element, "Message after successful delete is NOT valid.");
        playersPage.searchPlayerByUsername(player);
        List<WebElement> players = playersPage.selectPlayersFromSearchResult(player);
        softAssert.assertTrue(players.isEmpty(), "Player was NOT deleted");
        softAssert.assertAll();
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