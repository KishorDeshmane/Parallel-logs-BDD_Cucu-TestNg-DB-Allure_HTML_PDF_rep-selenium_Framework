package com.qa.factory;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.utility.ConfigManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver init_driver(String browser) {
        browser = (browser == null || browser.trim().isEmpty())
                ? ConfigManager.getConfigProperties().getProperty("browser", "chrome").split("#")[0].trim()
                : browser.split("#")[0].trim();

        System.out.println("Starting browser: " + browser);

        int implicitWait = Integer.parseInt(ConfigManager.getConfigProperties().getProperty("implicit.wait", "10").split("#")[0].trim());
        int pageLoadTimeout = Integer.parseInt(ConfigManager.getConfigProperties().getProperty("page.load.timeout", "30").split("#")[0].trim());
        int scriptTimeout = Integer.parseInt(ConfigManager.getConfigProperties().getProperty("script.timeout", "30").split("#")[0].trim());

        boolean isHeadless = Boolean.parseBoolean(ConfigManager.getConfigProperties().getProperty("headless", "false").split("#")[0].trim());
        boolean maximize = Boolean.parseBoolean(ConfigManager.getConfigProperties().getProperty("maximize", "true").split("#")[0].trim());
        boolean isIncognito = Boolean.parseBoolean(ConfigManager.getConfigProperties().getProperty("isIncognito", "false").split("#")[0].trim());

        switch (browser.toLowerCase()) {
            case "chrome":
                tlDriver.set(setupChrome(isHeadless, isIncognito));
                break;
            case "edge":
                tlDriver.set(setupEdge(isHeadless, isIncognito));
                break;
            case "firefox":
                tlDriver.set(setupFirefox(isHeadless, isIncognito));
                break;
            case "safari":
                tlDriver.set(new SafariDriver()); // Safari has limited options
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browser);
        }

        WebDriver driver = getDriver();
        driver.manage().deleteAllCookies();
        if (maximize) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(scriptTimeout));
        return driver;
    }

    private WebDriver setupChrome(boolean isHeadless, boolean isIncognito) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--ignore-certificate-errors");
        if (isHeadless) options.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        if (isIncognito) options.addArguments("--incognito");
        return new ChromeDriver(options);
    }

    private WebDriver setupEdge(boolean isHeadless, boolean isIncognito) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        if (isHeadless) options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage");
        if (isIncognito) options.addArguments("-inprivate");
        return new EdgeDriver(options);
    }

    private WebDriver setupFirefox(boolean isHeadless, boolean isIncognito) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless) options.addArguments("--headless");
        if (isIncognito) options.addArguments("-private");
        options.addPreference("dom.webnotifications.enabled", false);
        return new FirefoxDriver(options);
    }

    public static WebDriver getDriver() {
        if (tlDriver.get() == null) {
            System.out.println("WebDriver is not initialized. Initializing now...");
            new DriverFactory().init_driver(ConfigManager.getConfigProperties().getProperty("browser", "chrome"));
        }
        return tlDriver.get();
    }

    public void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
