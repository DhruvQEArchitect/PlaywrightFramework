package com.bookstore.pages;

import com.microsoft.playwright.Page;

public class BookStoreHomePage {
    Page page;

    private final String searchBox = "input#searchBox";
    private final String loginButton = "button#login";
    private final String pagination = "span.select-wrap.-pageSizeOptions > select";

    public BookStoreHomePage(Page page) {
        this.page = page;
    }

    public String homePageTitle() {
        return page.title();
    }

    public String searchBook(String input) {
        page.fill(searchBox, input);
        return page.locator("//a[text()='" + input + "']").textContent();
    }

    public BookStoreLoginPage userNavigateToLogin() {
        page.click(loginButton);
        return new BookStoreLoginPage(page);
    }
}
