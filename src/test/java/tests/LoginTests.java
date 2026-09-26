package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    // Test Case 2: Login with correct email and password
    @Test
    public void testValidLogin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToLoginPage();

        loginPage.login("jih7ne@gmail.com", "YourCorrectPassword123");

        Assert.assertTrue(loginPage.isLoggedIn(),
                "Expected to see 'Logged in as' after valid login, but it wasn't shown.");
    }

    // Test Case 3: Login with incorrect email and password
    @Test
    public void testInvalidLogin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToLoginPage();

        loginPage.login("nonexistent_user@example.com", "WrongPassword999");

        Assert.assertTrue(loginPage.isLoginErrorDisplayed(),
                "Expected an error message for invalid login, but none was shown.");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Your email or password is incorrect!");
    }
}