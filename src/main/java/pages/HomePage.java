package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage extends BasePage {

    private By signupLoginLink = By.linkText("Signup / Login");

    public HomePage(WebDriver driver) {
        super(driver);
        acceptCookieConsentIfPresent();
    }
    private By loggedInAsIndicator = By.xpath("//a[contains(text(),'Logged in as')]");

    public boolean isLoggedInAsVisible() {
        return isDisplayed(loggedInAsIndicator);
    }
    public LoginPage goToLoginPage() {
        click(signupLoginLink);
        return new LoginPage(driver);
    }

    public SignupPage goToSignupPage() {
        click(signupLoginLink);
        return new SignupPage(driver);
    }
    private By deleteAccountLink = By.cssSelector("a[href='/delete_account']");
    private By accountDeletedHeading = By.cssSelector("h2[data-qa='account-deleted']");

    public void deleteAccountAndVerify() {
        click(deleteAccountLink);
        if (!isDisplayed(accountDeletedHeading)) {
            throw new AssertionError("Expected 'ACCOUNT DELETED!' message after deletion, but it wasn't shown.");
        }
    }
}