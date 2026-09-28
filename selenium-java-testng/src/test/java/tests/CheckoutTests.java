package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import io.qameta.allure.*;

@Epic("Automation Exercise - Core Flows")
@Feature("Checkout")
public class CheckoutTests extends BaseTest {

    @Test
    @Story("Place Order - Login Before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies a logged-in user can add a product, proceed to checkout, and reach order review")
    public void testPlaceOrderLoginBeforeCheckout() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToLoginPage();
        loginPage.login("jih7ne@gmail.com", "YourCorrectPassword123");

        HomePage loggedInHome = new HomePage(driver);
        ProductsPage productsPage = loggedInHome.goToProductsPage();

        productsPage.addFirstProductToCart();
        CartPage cartPage = productsPage.viewCartFromModal();

        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        Assert.assertTrue(checkoutPage.isReviewOrderVisible(),
                "Expected 'Review Your Order' section during checkout.");

        checkoutPage.addOrderComment("Automated test order - please ignore.");
        checkoutPage.clickPlaceOrder();
    }
}