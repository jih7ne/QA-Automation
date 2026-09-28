package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;

@Epic("Automation Exercise - Core Flows")
@Feature("Authentication")
public class LoginTests extends BaseTest {

    @Test
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies a registered user can log in with correct credentials")
    public void testValidLogin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToLoginPage();

        loginPage.login("jih7ne@gmail.com", "YourCorrectPassword123");

        Assert.assertTrue(loginPage.isLoggedIn(),
                "Expected to see 'Logged in as' after valid login, but it wasn't shown.");
    }

    @Test
    @Story("Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies an appropriate error is shown for invalid login credentials")
    public void testInvalidLogin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToLoginPage();

        loginPage.login("nonexistent_user@example.com", "WrongPassword999");

        Assert.assertTrue(loginPage.isLoginErrorDisplayed(),
                "Expected an error message for invalid login, but none was shown.");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Your email or password is incorrect!");
    }
}