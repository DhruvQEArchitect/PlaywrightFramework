package com.bookstore.pages;

import com.microsoft.playwright.Page;

public class BookStoreLoginPage {
    Page page;

    private final String userName = "input#userName";
    private final String password = "input#password";
    private final String loginButton = "button#login";
    private final String newUserButton = "button#newUser";
    private final String welcomeMessage = "form#userForm div h2";
    private final String loginToBookStore = "form#userForm div h5";
    private final String pageHeader = "div h1";
    private final String getInvalidMessage = "p#name";

    public BookStoreLoginPage(Page page) {
        this.page = page;
    }

    public boolean getPageHeader() {
        return page.textContent(pageHeader).equals("Login");
    }

    public String getWelcomeMessage() {
        return page.textContent(welcomeMessage) + page.textContent(loginToBookStore);
    }

    public void loginToStore(String user, String pass) {
        page.fill(userName, user);
        page.fill(password, pass);
        page.click(loginButton);
    }

    public boolean getMessage() {
        return page.isVisible(getInvalidMessage);
    }

    public String getLoginPageTitle() {
        return page.title();
    }


}
