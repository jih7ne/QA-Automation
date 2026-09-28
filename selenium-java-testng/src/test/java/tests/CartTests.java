package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductsPage;
import io.qameta.allure.*;

@Epic("Automation Exercise - Core Flows")
@Feature("Shopping Cart")
public class CartTests extends BaseTest {

    @Test
    @Story("Add To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies a product can be added to the cart and appears there")
    public void testAddProductToCart() {
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.goToProductsPage();

        productsPage.addFirstProductToCart();
        CartPage cartPage = productsPage.viewCartFromModal();

        Assert.assertTrue(cartPage.getCartItemCount() > 0,
                "Expected at least one item in the cart after adding a product.");
    }
}