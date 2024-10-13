package com.bookstore.tests;

import com.bookstore.reporting.LOGGER;
import com.bookstore.setup.Base;
import com.bookstore.setup.Constants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTests extends Base {

    @DataProvider
    public Object[][] getBookName() {
        return new Object[][]{
                {"Git Pocket Guide"},
                {"Speaking JavaScript"}
        };
    }

    @Test(dataProvider = "getBookName")
    public void verifyUserIsAbleToPerformBookSearch(String bookName) {
        Assert.assertEquals(bookStoreHomePage.homePageTitle(), Constants.APP_TITLE);
        LOGGER.INFO("Verified book store home page title successfully");

        Assert.assertEquals(bookStoreHomePage.searchBook(bookName), bookName);
        LOGGER.INFO("Verified book search functionality successfully");
    }

}
