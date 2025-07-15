package tests;

import base.BaseClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.CheckoutPage;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;
import java.lang.reflect.Method;

public class BaseTest extends BaseClass {
    protected HomePage homePage;
    protected SearchResultsPage searchResultsPage;
    protected ProductPage productPage;
    protected CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp(Method method) {
        logger.info("==== Starting Test: {} ====", method.getName());
        initiateDriver();

        homePage = new HomePage(getDriver());
        searchResultsPage = new SearchResultsPage(getDriver());
        productPage = new ProductPage(getDriver());
        checkoutPage = new CheckoutPage(getDriver());
    }

    @AfterMethod
    public void tearDown(Method method) {
        logger.info("==== Finished Test: {} ====", method.getName());
        cleanupDriver();
    }
}