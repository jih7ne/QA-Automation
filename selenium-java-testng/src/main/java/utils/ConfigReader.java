package utils;
//this file reads config.properties so nothing is hardcoded on the tests
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            String path = "src/test/resources/config.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("config.properties not found at src/test/resources", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicitWait"));
    }
}