//package com.qa.factory;
//
//import org.openqa.selenium.WebDriver;
//
//public class LaunchBrowser {
//	
//	 private static WebDriver driver;
//
//	    public static LaunchBrowser startBrowser(String browserName) {
//	        if (driver == null) { 
//	            DriverFactory df = new DriverFactory();
//	            driver = df.init_driver(browserName);
//	        }
//	        return new LaunchBrowser();
//	    }
//
//	    public WebDriver getDriver() {
//	        return driver;
//	    }
//
//	    public static void closeBrowser() {
//	        if (driver != null) {
//	            driver.quit();
//	            driver = null;
//	        }
//	    }
//	}