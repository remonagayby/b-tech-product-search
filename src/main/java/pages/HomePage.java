package pages;

import actions.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends ElementActions {

    public HomePage(WebDriver driver) {
        super();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "search")
    private WebElement searchBox;

    @FindBy(xpath = "//button[@class='action search']")
    private WebElement searchButton;

    public SearchResultsPage searchForKeyword(String keyword) {
        clearElement(searchBox);
        sendKeysToElement(searchBox, keyword);
        clickElement(searchButton);
        return new SearchResultsPage(getDriver());
    }
}