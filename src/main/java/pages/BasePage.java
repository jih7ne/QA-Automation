package pages;
//every page will extend this class, it owns the driver and the wait logic

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

import utils.ConfigReader;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    protected void click(By locator) {
        logger.info("Clicking element: {}", locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            element.click();
        } catch (ElementClickInterceptedException e) {
            logger.warn("Click intercepted on {} — falling back to JS click", locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(By locator, String text) {
        logger.info("Typing '{}' into element: {}", text, locator);
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        logger.info("Read text '{}' from element: {}", text, locator);
        return text;
    }

    protected boolean isDisplayed(By locator) {
        try {
            boolean shown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
            logger.info("Element {} displayed: {}", locator, shown);
            return shown;
        } catch (TimeoutException e) {
            logger.warn("Element {} was not displayed within timeout", locator);
            return false;
        }
    }

    protected void acceptCookieConsentIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By allowButton = By.cssSelector("button.fc-cta-consent");
            shortWait.until(ExpectedConditions.elementToBeClickable(allowButton)).click();
            logger.info("Cookie consent dialog dismissed");
        } catch (TimeoutException e) {
            logger.info("No cookie consent dialog appeared");
        }
    }
}