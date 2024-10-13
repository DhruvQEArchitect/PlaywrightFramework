package com.bookstore.setup;

import com.bookstore.factory.Factory;
import com.bookstore.pages.BookStoreHomePage;
import com.bookstore.pages.BookStoreLoginPage;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class Base {

    protected Factory factory;
    Page page;
    protected BookStoreHomePage bookStoreHomePage;
    protected BookStoreLoginPage bookStoreLoginPage;

    @BeforeTest
    public void setup() {
        factory = Factory.getInstance();
        page = factory.startBrowser();
        bookStoreHomePage = new BookStoreHomePage(page);

    }

    @AfterTest
    public void cleanup() {
        page.context().browser().close();
    }

}
