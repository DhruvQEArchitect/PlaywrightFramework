package com.bookstore.tests;

import com.bookstore.setup.Base;
import com.bookstore.setup.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends Base {

    @Test
    public void verifyLoginPageHeaders() {
        bookStoreLoginPage = bookStoreHomePage.userNavigateToLogin();
        
        Assert.assertTrue(bookStoreLoginPage.getPageHeader());
        Assert.assertEquals(bookStoreLoginPage.getWelcomeMessage(), "Welcome,Login in Book Store");
    }

    @Test
    public void verifyLoginTestWithInvalidCredentials() {
        bookStoreLoginPage.loginToStore(factory.getPropValues("username"), factory.getPropValues("password"));
        Assert.assertTrue(bookStoreLoginPage.getMessage());
    }

    @Test
    public void verifyLoginPageTitle() {
        Assert.assertEquals(bookStoreLoginPage.getLoginPageTitle(), Constants.LOGIN_PAGE_TITLE);
    }

}
