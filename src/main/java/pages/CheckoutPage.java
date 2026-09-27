package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By reviewOrderHeading = By.xpath("//h2[text()='Review Your Order']");
    private By commentField = By.name("message");
    private By placeOrderButton = By.xpath("//a[text()='Place Order']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isReviewOrderVisible() {
        return isDisplayed(reviewOrderHeading);
    }

    public void addOrderComment(String comment) {
        type(commentField, comment);
    }

    public void clickPlaceOrder() {
        click(placeOrderButton);
    }
}