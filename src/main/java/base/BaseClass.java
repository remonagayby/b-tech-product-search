package base;

import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public abstract class BaseClass {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<Properties> properties = ThreadLocal.withInitial(() -> {
        Properties props = new Properties();
        try (FileInputStream file = new FileInputStream("src/test/resources/config/config.properties")) {
            props.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
        return props;
    });

    protected static final Logger logger = LogManager.getLogger(BaseClass.class);

    public void initiateDriver() {
        Properties props = properties.get();
        String browserName = props.getProperty("browser").toLowerCase();
        String homePageUrl = props.getProperty("homePageUrl");

        WebDriver instance;

        if (browserName.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications", "--incognito");
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
            instance = new ChromeDriver(options);
        } else if (browserName.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);
            options.addArguments("-private");
            instance = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        driver.set(instance);
        instance.manage().window().maximize();
        instance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        instance.get(homePageUrl);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static String captureScreenshot(String testCaseName) throws IOException {
        WebDriver localDriver = getDriver();
        File screenshot = ((TakesScreenshot) localDriver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir")
                + "/src/test/resources/screenshots/" + testCaseName + ".png";
        FileUtils.copyFile(screenshot, new File(screenshotPath));
        return screenshotPath;
    }

    public static void cleanupDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }

    public static String getApiBaseUrl() {
        return properties.get().getProperty("apiBaseUrl");
    }

    public void initializeApiTest() {
        RestAssured.baseURI = getApiBaseUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}