package tests;
//it handles browser's launch / tear down for every test class



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Redirect common ad-serving domains to a dead address so their iframes never load
        options.addArguments("--host-resolver-rules="
                + "MAP doubleclick.net 0.0.0.0,"
                + "MAP googlesyndication.com 0.0.0.0,"
                + "MAP googleadservices.com 0.0.0.0,"
                + "MAP google-analytics.com 0.0.0.0,"
                + "MAP googletagservices.com 0.0.0.0,"
                + "MAP adservice.google.com 0.0.0.0,"
                + "MAP pagead2.googlesyndication.com 0.0.0.0");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}