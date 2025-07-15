package actions;

import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class ElementActions extends BaseClass {

    private static final int TIMEOUT = 10;

    private static WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT));
    }

    protected static void fluentWait(WebElement element) {
        FluentWait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(TIMEOUT))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);

        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void clickElement(WebElement element) {
        try {
            getWait().until(ExpectedConditions.elementToBeClickable(element)).click();
            logger.info("Clicked on element: {}", element);
        } catch (Exception e) {
            String errorMessage = "Exception while clicking the element: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    protected void sendKeysToElement(WebElement element, String keys) {
        try {
            getWait().until(ExpectedConditions.visibilityOf(element)).sendKeys(keys);
            logger.info("Sent keys '{}' to element: {}", keys, element);
        } catch (Exception e) {
            String errorMessage = "Exception while sending keys to element: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    protected void clearElement(WebElement element) {
        try {
            getWait().until(ExpectedConditions.visibilityOf(element)).clear();
            logger.info("Cleared the content of the element: {}", element);
        } catch (Exception e) {
            String errorMessage = "Exception while clearing the element: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    protected void isElementDisplayed(WebElement locator) {
        try {
            getWait().until(ExpectedConditions.visibilityOf(locator)).isDisplayed();
        } catch (Exception e) {
            String errorMessage = "Exception while checking if element is displayed: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

}