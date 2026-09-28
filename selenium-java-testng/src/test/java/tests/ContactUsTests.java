package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.HomePage;
import io.qameta.allure.*;

@Epic("Automation Exercise - Core Flows")
@Feature("Contact Us")
public class ContactUsTests extends BaseTest {

    @Test
    @Story("Contact Form Submission")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies the Contact Us form can be filled, a file uploaded, and submission confirmed")
    public void testContactUsForm() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/test-upload.txt";

        HomePage homePage = new HomePage(driver);
        ContactUsPage contactUsPage = homePage.goToContactUsPage();

        Assert.assertTrue(contactUsPage.isGetInTouchVisible(),
                "Expected 'Get In Touch' heading on Contact Us page.");

        contactUsPage.fillForm(
                "Jihane QA",
                "jihane.qa@example.com",
                "Test Subject",
                "This is an automated test message.",
                filePath
        );

        contactUsPage.submitAndAcceptAlert();

        Assert.assertTrue(contactUsPage.isSuccessMessageDisplayed(),
                "Expected success message after submitting Contact Us form.");
    }
}