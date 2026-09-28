package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;

public class ContactUsPage extends BasePage {

    private By getInTouchHeading = By.xpath("//h2[text()='Get In Touch']");
    private By nameField = By.name("name");
    private By emailField = By.name("email");
    private By subjectField = By.name("subject");
    private By messageField = By.name("message");
    private By fileUpload = By.name("upload_file");
    private By submitButton = By.name("submit");
    private By successMessage = By.cssSelector(".status.alert-success");
    private By homeButton = By.cssSelector("a[href='/'] .fa-home");

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isGetInTouchVisible() {
        return isDisplayed(getInTouchHeading);
    }

    public void fillForm(String name, String email, String subject, String message, String filePath) {
        type(nameField, name);
        type(emailField, email);
        type(subjectField, subject);
        type(messageField, message);
        wait.until(d -> d.findElement(fileUpload)).sendKeys(filePath);
    }

    public void submitAndAcceptAlert() {
        click(submitButton);
        Alert alert = wait.until(d -> {
            try {
                return d.switchTo().alert();
            } catch (Exception e) {
                return null;
            }
        });
        if (alert != null) {
            alert.accept();
        }
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage);
    }

    public HomePage clickHome() {
        click(homeButton);
        return new HomePage(driver);
    }
}