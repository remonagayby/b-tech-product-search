package pages;

import actions.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends ElementActions {

    public ProductPage(WebDriver driver) {
        super();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "mtImageContainerMagic")
    private WebElement productImages;

    @FindBy(id = "product-addtocart-button")
    private WebElement btnAddToCart;

    @FindBy(xpath = "//button//span[text()='Go to cart']")
    private WebElement btnGoToCart;

    public ProductPage clickAddToCart() {
        clickElement(btnAddToCart);
        return this;
    }

    public ProductPage validateProductImagesIsDisplayed() {
        isElementDisplayed(productImages);
        return this;
    }

    public CheckoutPage clickGoToCart() {
        clickElement(btnGoToCart);
        return new CheckoutPage(getDriver());
    }
}