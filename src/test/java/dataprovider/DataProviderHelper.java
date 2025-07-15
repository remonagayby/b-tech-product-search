package dataprovider;

import org.testng.annotations.DataProvider;

public class DataProviderHelper {

    @DataProvider(name = "searchData", parallel = true)
    public Object[][] getSearchData() {
        return new Object[][]{
                {"iphone 16", 1},
        };
    }

}