package pages;

import actions.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class SearchResultsPage extends ElementActions {

    @FindBy(id = "product_view_0")
    private WebElement firstSearchResult;

    @FindBy(xpath = "(//div[contains(@class, 'product-item-view')]//img[@class='product-image-photo'])[1]")
    private WebElement productImage;

    public SearchResultsPage(WebDriver driver) {
        super();
        PageFactory.initElements(driver, this);
    }

    public ProductPage clickFirstSearchResultCard() {
        clickElement(firstSearchResult);
        return new ProductPage(getDriver());
    }

    public SearchResultsPage validateProductImage() {
        isElementDisplayed(productImage);
        logger.info("Element is displayed");
        return this;
    }

}