package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class CartPage extends BasePage {

    private By cartProductRows = By.cssSelector("#cart_info_table tbody tr");
    private By proceedToCheckoutButton = By.xpath("//a[text()='Proceed To Checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        return driver.findElements(cartProductRows).size();
    }

    public CheckoutPage proceedToCheckout() {
        click(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }
}