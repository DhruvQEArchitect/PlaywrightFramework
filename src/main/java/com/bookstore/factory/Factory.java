package com.bookstore.factory;

import com.microsoft.playwright.*;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class Factory {

    static String currDir = System.getProperty("user.dir");
    String path;
    private static Factory instance = null;

    private static ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public static Factory getInstance() {
        if (instance == null)
            instance = new Factory();
        return instance;
    }

    public static Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }

    public static Browser getBrowser() {
        return browserThreadLocal.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContextThreadLocal.get();
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public Page startBrowser() {
        boolean mode;
        if (getPropValues("headless").equals("true")) {
            mode = true;
        } else {
            mode = false;
        }
        String browserName = getPropValues("browser");
        playwrightThreadLocal.set(Playwright.create());
        switch (browserName) {
            case "chrome":
                browserThreadLocal.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(mode)));
                break;
            case "chromium":
                browserThreadLocal.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(mode)));
                break;
            case "edge":
                browserThreadLocal.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(mode)));
                break;
            case "firefox":
                browserThreadLocal.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(mode)));
                break;
            case "webkit":
                browserThreadLocal.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(mode)));
                break;
            default:
                System.out.println("This browser is not supported");
                break;
        }
        browserContextThreadLocal.set(getBrowser().newContext());
        pageThreadLocal.set(getBrowserContext().newPage());
        getPage().navigate(getApplicationUnderTest());
        return getPage();
    }

    public String getPropValues(String key) {
        path = currDir + "/src/test/Resources/Framework.properties";
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex.getMessage());
        }
        return properties.getProperty(key);
    }

    public String getApplicationUnderTest() {
        return getPropValues("app.url");
    }

    public static String captureScreenshot() {
        String screenshotDir = currDir + "/Screenshots/";
        File screenshotF = new File(screenshotDir);
        boolean dirExist = screenshotF.mkdir();
        if (dirExist) {
            System.out.println("Screenshots directory created successfully");
            screenshotF.mkdir();

        } else {
            System.out.println("Screenshots directory is already present");
        }
        String screenshotPath = screenshotDir + System.currentTimeMillis() + ".png";
        getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
        return screenshotPath;
    }
}
