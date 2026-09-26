package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {

    private By nameField = By.cssSelector("input[data-qa='signup-name']");
    private By emailField = By.cssSelector("input[data-qa='signup-email']");
    private By signupButton = By.cssSelector("button[data-qa='signup-button']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public AccountInfoPage signup(String name, String email) {
        type(nameField, name);
        type(emailField, email);
        click(signupButton);
        return new AccountInfoPage(driver);
    }
}