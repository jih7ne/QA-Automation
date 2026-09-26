package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountInfoPage;
import pages.HomePage;
import pages.SignupPage;

public class RegisterTests extends BaseTest {

    // Test Case 1: Register User
    @Test
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