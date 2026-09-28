package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class AccountInfoPage extends BasePage {

    private By titleMr = By.cssSelector("#id_gender1");
    private By passwordField = By.cssSelector("#password");
    private By daySelect = By.cssSelector("#days");
    private By monthSelect = By.cssSelector("#months");
    private By yearSelect = By.cssSelector("#years");
    private By firstName = By.cssSelector("#first_name");
    private By lastName = By.cssSelector("#last_name");
    private By address1 = By.cssSelector("#address1");
    private By countrySelect = By.cssSelector("#country");
    private By state = By.cssSelector("#state");
    private By city = By.cssSelector("#city");
    private By zipcode = By.cssSelector("#zipcode");
    private By mobileNumber = By.cssSelector("#mobile_number");
    private By createAccountButton = By.cssSelector("button[data-qa='create-account']");
    private By accountCreatedHeading = By.cssSelector("h2[data-qa='account-created']");
    private By continueButton = By.cssSelector("[data-qa='continue-button']");

    public AccountInfoPage(WebDriver driver) {
        super(driver);
    }

    public void fillAndSubmit(String password, String firstNameVal, String lastNameVal,
                               String addressVal, String countryVal, String stateVal,
                               String cityVal, String zipcodeVal, String mobileVal) {
        click(titleMr);
        type(passwordField, password);

        new Select(wait.until(d -> d.findElement(daySelect))).selectByValue("10");
        new Select(wait.until(d -> d.findElement(monthSelect))).selectByVisibleText("May");
        new Select(wait.until(d -> d.findElement(yearSelect))).selectByValue("1998");

        type(firstName, firstNameVal);
        type(lastName, lastNameVal);
        type(address1, addressVal);
        new Select(wait.until(d -> d.findElement(countrySelect))).selectByVisibleText(countryVal);
        type(state, stateVal);
        type(city, cityVal);
        type(zipcode, zipcodeVal);
        type(mobileNumber, mobileVal);

        click(createAccountButton);
    }

    public boolean isAccountCreated() {
        return isDisplayed(accountCreatedHeading);
    }

    public HomePage clickContinue() {
        click(continueButton);
        return new HomePage(driver);
    }
    private By deleteAccountLink = By.cssSelector("a[href='/delete_account']");
    private By accountDeletedHeading = By.cssSelector("h2[data-qa='account-deleted']");

    public void deleteAccount() {
        click(deleteAccountLink);
    }

    public boolean isAccountDeleted() {
        return isDisplayed(accountDeletedHeading);
    }
    public void deleteAccountAndVerify() {
        click(deleteAccountLink);
        System.out.println("URL after clicking delete: " + driver.getCurrentUrl());
        if (!isDisplayed(accountDeletedHeading)) {
            throw new AssertionError("Expected 'ACCOUNT DELETED!' message after deletion, but it wasn't shown. Current URL: " + driver.getCurrentUrl());
        }
    }
}