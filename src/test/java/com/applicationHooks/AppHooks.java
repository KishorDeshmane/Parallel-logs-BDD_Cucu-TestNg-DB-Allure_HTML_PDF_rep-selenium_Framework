package com.applicationHooks;

import java.awt.Desktop;
import java.io.File;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.qa.factory.DriverFactory;
import com.qa.utility.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class AppHooks {
	private DriverFactory df;
	private WebDriver driver;
	public static Properties prop;
	public static Properties tdata;
	public static Scenario scn;

	@Before(order = 0)
	public void getProperty() {
		new ConfigManager();
		prop = ConfigManager.getConfigProperties();
		tdata = ConfigManager.getTestDataProperties();
	}

	@Before(order = 1)
	public void launchBrowser( ) {
		String browserName = prop.getProperty("browser");
		df = new DriverFactory(); // like baseUtility class
		driver = df.init_driver(browserName); // like startup method
		
	}

	@BeforeStep
	public void getScenarioInstance(Scenario scenario) {
		AppHooks.scn = scenario;
	}

	public static void log(String message) {
	    if (scn != null) {
	        scn.log(message);
	    } else {
	        System.out.println("LOG: " + message);
	    }
	}
	
	@After(order = 0)
	public void quitBrowser() {
		if (driver != null) {
            System.out.println("Closing browser...");
            driver.quit();
            System.out.println("Browser closed.");
        } else {
            System.out.println("No active browser session found.");
        }
    }

	@After(order = 1)
	public void takeScreenshot(Scenario scenario) {
		if (driver == null) {
	        System.err.println("Cannot take screenshot: WebDriver is null.");
	        return;
	    }
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			// Add in Allure report
			final byte[] sourcePaths = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			// Allure.addAttachment(screenshotName, new ByteArrayInputStream(sourcePaths));
			scenario.attach(sourcePaths, "image/png", screenshotName);
		}
	}

//	@org.testng.annotations.AfterSuite --- Currently not running need to look and study the annotation
	@AfterAll
	public static void generateReports() {
		// Spark Report
//		try {
//			File htmlFile = new File("Reports/SparkReport.html");
//			if (htmlFile.exists()) {
//				Desktop.getDesktop().browse(htmlFile.toURI());
//			} else {
//				System.out.println("Spark HTML report not found: " + htmlFile.getAbsolutePath());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
System.out.println("Scenario End");
		// Allure Report
//		try {
//			ProcessBuilder builder = new ProcessBuilder("C:/allure-2.32.0/bin/allure.bat", "serve", "allure-results");
//			builder.inheritIO();
//			Process process = builder.start();
//			process.waitFor();
//			System.out.println("Allure report served successfully.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Failed to serve Allure report.");
//		}
	}
}
