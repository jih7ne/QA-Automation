package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountInfoPage;
import pages.HomePage;
import pages.SignupPage;
import tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;

@Epic("Automation Exercise - Core Flows")
@Feature("Authentication")
public class RegisterTests extends BaseTest {

    @Test
    @Story("Registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies a new user can register, gets logged in automatically, and account can be deleted")
    public void testRegisterUser() {
        String uniqueEmail = "jihane.qa" + System.currentTimeMillis() + "@example.com";

        HomePage homePage = new HomePage(driver);
        SignupPage signupPage = homePage.goToSignupPage();

        AccountInfoPage accountInfoPage = signupPage.signup("Jihane QA", uniqueEmail);

        accountInfoPage.fillAndSubmit(
                "TestPass123",      // password
                "Jihane",           // first name
                "QA",               // last name
                "123 Test Street",  // address
                "India",          // country
                "Tetouan",          // state
                "Tetouan",          // city
                "93000",            // zipcode
                "0600000000"        // mobile
        );

        Assert.assertTrue(accountInfoPage.isAccountCreated(),
                "Expected 'ACCOUNT CREATED!' message, but it wasn't shown.");

        HomePage afterSignup = accountInfoPage.clickContinue();
        Assert.assertTrue(afterSignup.isLoggedInAsVisible(),
                "Expected to be logged in automatically after registration.");
        Assert.assertTrue(afterSignup.isLoggedInAsVisible(),
                "Expected to be logged in automatically after registration.");

        afterSignup.deleteAccountAndVerify();
    }

}