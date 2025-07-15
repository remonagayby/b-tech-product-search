package pages;

import actions.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends ElementActions {

    public CheckoutPage(WebDriver driver) {
        super();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@data-role='proceed-to-checkout']")
    private WebElement btnCheckout;

    public Boolean validateCheckoutButton() {
        fluentWait(btnCheckout);
        return btnCheckout.isDisplayed();
    }

}
