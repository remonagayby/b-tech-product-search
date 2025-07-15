package tests;

import dataprovider.DataProviderHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductSearchTest extends BaseTest {

    @Test(dataProvider = "searchData", dataProviderClass = DataProviderHelper.class)
    public void buyIphone16Test(String keyword, int itemIndex) {
        logger.info("Test started: Searching iPhone 16 for '{}', clicking video index {}", keyword, itemIndex);

        homePage.searchForKeyword(keyword)
                .validateProductImage()
                .clickFirstSearchResultCard()
                .validateProductImagesIsDisplayed()
                .clickAddToCart()
                .clickGoToCart()
                .validateCheckoutButton();

        Assert.assertTrue(checkoutPage.validateCheckoutButton(), String.valueOf(true));
    }
}