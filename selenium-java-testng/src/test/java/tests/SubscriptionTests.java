package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import io.qameta.allure.*;

@Epic("Automation Exercise - Core Flows")
@Feature("Subscription")
public class SubscriptionTests extends BaseTest {

    @Test
    @Story("Newsletter Subscription")
    @Severity(SeverityLevel.MINOR)
    @Description("Verifies subscribing to the newsletter on the home page shows a success message")
    public void testSubscriptionHomePage() {
        HomePage homePage = new HomePage(driver);
        homePage.subscribeToNewsletter("jihane.qa" + System.currentTimeMillis() + "@example.com");

        Assert.assertTrue(homePage.isSubscribeSuccessVisible(),
                "Expected success message after subscribing.");
    }
}