package com.qa.utility;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import com.qa.factory.DriverFactory;

public class ElementUtil {

	private static final String ALGORITHM = "AES";
	private static final byte[] SECRET_KEY = "MySecretKey12345".getBytes(); // Must be 16 bytes for AES

	public static ElementUtil eu = new ElementUtil();

//	public void waitForPageLoad(WebDriver driver) {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Contants.small_wait));
//		wait.until(new ExpectedCondition<Boolean>() {
//			public Boolean apply(WebDriver wdriver) {
//				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//			}
//		});

	/*
	 * Wait<WebDriver> wait = new WebDriverWait(driver, 30); wait.until(new
	 * Function<WebDriver, Boolean>() { public Boolean apply(WebDriver driver) {
	 * System.out.println("Current Window State       : " +
	 * String.valueOf(((JavascriptExecutor)
	 * driver).executeScript("return document.readyState"))); return String
	 * .valueOf(((JavascriptExecutor)
	 * driver).executeScript("return document.readyState")) .equals("complete"); }
	 * });
	 */
//	}

	public static String encrypt(String plainText) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static String decrypt(String encryptedText) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		return new String(decryptedBytes);
	}

	public String random_email() {
		// Array of common email domains
		String[] emailDomains = { "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "shield.com" };
		// Generate a random username
		String username = generateRandomString(8); // Change 8 to the desired length of the username
		// Select a random domain from the array
		Random random = new Random();
		String domain = emailDomains[random.nextInt(emailDomains.length)];
		// Concatenate the username, "@" symbol, and domain to form the email address
		String emailAddress = username + "@" + domain;
		// Print the generated email address
		System.out.println("Random email address: " + emailAddress);
		return emailAddress;
	}

	// Method to generate a random string of specified length
	public static String generateRandomString(int length) {
		String characters = "abcdefghijklmnopqrstuvwxyz0123456789"; // Include characters that can be used in email
																	// usernames
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}

	public String random_nine_digit() {
		Random random = new Random();
		StringBuilder mobileNumber = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			mobileNumber.append(random.nextInt(10));
		}
		System.out.println("Random 9-digit mobile number: " + mobileNumber.toString());
		return mobileNumber.toString();
	}

	public void window_scrollBy(WebDriver driver, int x, int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// vertical y and horizontal x
		js.executeScript("window.scrollBy(" + x + "," + y + ")");
	}

	public void wait_for_element_to_be_clickable(WebDriver driver, int time, WebElement element) {
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(time));
		wt.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void wait_for_element_to_be_selected(WebDriver driver, int time, WebElement element) {
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(time));
		wt.until(ExpectedConditions.elementSelectionStateToBe(element, true));
	}

	public void wait_for_element_to_be_not_selected(WebDriver driver, int time, WebElement element) {
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(time));
		wt.until(ExpectedConditions.elementSelectionStateToBe(element, false));
	}

	public void wait_for_element_to_be_displayed(WebDriver driver, int time, WebElement element) {
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(time));
		wt.until(ExpectedConditions.visibilityOf(element));
	}

//	public void wait_for_element_to_be_present_in_dom(WebDriver driver, int time, WebElement locator) {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//	}

	public void wait_for_element_text_be_cleared(WebDriver driver, int count, WebElement element) {
		for (int i = 0; i < count; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			element.sendKeys(Keys.CONTROL + "a");
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	public void wait_for_element_to_be_displayed_in_string_form(WebDriver driver, int time, WebElement element,
			String attribute, String value) {
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(time));
		wt.until(ExpectedConditions.attributeContains(element, attribute, value));
	}

	public void wait_for_to_be_title_is_displayed(WebDriver driver, int time, String title) {
		WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(time));
		wt.until(ExpectedConditions.titleIs((title)));
	}

	public String get_window_handels_pages(WebDriver driver) {
		String homePageWinId = driver.getWindowHandle();
		System.out.println("Home page window ID: " + homePageWinId);
		Set<String> allWinIds = driver.getWindowHandles();
		System.out.println("All windows ID: " + allWinIds);
		allWinIds.remove(homePageWinId);
		System.out.println("After remoing homeWinID, all windows ID: " + allWinIds);
		Iterator<String> itr = allWinIds.iterator();
		String childWinId = itr.next();
		driver.switchTo().window(childWinId);
		// or
		// switch control to child window
		// driver.switchTo().window(allWinIds.iterator().next());
		String title = driver.getTitle();
		System.out.println(title);
		System.out.println("new page title: " + driver.getTitle());
		String url = driver.getCurrentUrl();
		System.out.println("new page ulr: " + driver.getCurrentUrl());
		driver.close();
		// driver.quit();//comment below code
		driver.switchTo().window(homePageWinId);
		// now you are allow to identify any element from home window

		System.out.println("setteled page title: " + driver.getTitle());
		System.out.println("setteled page ulr: " + driver.getCurrentUrl());
		return url;
	}

	public void clickByJS(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", ele);
	}

	public boolean isAlertPresent(WebDriver driver, Duration time) {
		try {
//			WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(time));
			WebDriverWait wt = new WebDriverWait(driver, time);
			wt.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void scroll_till_lement(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", ele);
	}

	public double get_location_of_element_in_x_axis(WebElement element) {
		Point locaaation = element.getLocation();
		double element_x = locaaation.getX();
		System.out.println(element_x);
		return element_x;
	}

	public double get_location_of_element_in_y_axis(WebElement element) {
		Point locaaation = element.getLocation();
		double element_y = locaaation.getY();
		System.out.println(element_y);
		return element_y;
	}

	public void send_text(WebElement element, String value) {
		element.click();
		element.sendKeys(value);
	}

	public void upload_file_auto_it(WebDriver driver, WebElement element, String location_of_exe_file) {
		Actions act = new Actions(driver);
		act.moveToElement(element).click().build().perform();
		try {
			Runtime.getRuntime().exec(location_of_exe_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rightClick(WebElement option, WebDriver driver) {
		Actions action = new Actions(driver);
		action.moveToElement(option).contextClick().build().perform();
	}

	public void mouseHoverWithCords(WebDriver driver, WebElement option, int x, int y) {
		Actions action = new Actions(driver);
		action.moveToElement(option, x, y).perform();
	}

	public void mouseHover(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public void doubleClick(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick().perform();
	}

	public void switchToRequiredFrameUsingName(String name, WebDriver driver) {
		driver.switchTo().frame(name);
	}

	public void switchToRequiredFrameUsingWebElement(WebElement element, WebDriver driver) {
		driver.switchTo().frame(element);
	}

	public void switchToRequiredFrameUsingIndex(int index, WebDriver driver) {
		driver.switchTo().frame(index);
	}

	public void switchControlBackToMainPage(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public WebElement getActiveElement(WebDriver driver) {
		return driver.switchTo().activeElement();
	}

	public void refresh_your_page(WebDriver driver) {
		driver.navigate().refresh();
	}

	public String current_page_title(WebDriver driver) {
		return driver.getTitle();
	}

	public void scroll_up_body_click(int num, WebDriver driver) {
		WebElement e = driver.findElement(By.xpath("//body"));
		for (int i = 0; i < num; i++) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException ek) {
				ek.printStackTrace();
			}
			e.sendKeys(Keys.PAGE_UP);
		}
	}

	public void scroll_down_body_click(int num, WebDriver driver) {
		WebElement e = driver.findElement(By.xpath("//body"));
		for (int i = 0; i < num; i++) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException es) {
				es.printStackTrace();
			}
			e.sendKeys(Keys.PAGE_DOWN);
		}
	}

	public void scroll_end_of_page_with_keyboard_click(int num) {
		for (int i = 0; i < num; i++) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			WebElement el = DriverFactory.getDriver().findElement(By.xpath("//body"));
			el.sendKeys(Keys.PAGE_DOWN);
		}
	}

	public String project_path() {
		String projectPath = System.getProperty("user.dir");
		return projectPath;
	}

	public void navigate_back(WebDriver driver) {
		driver.navigate().back();
	}

	public void opened_second_tab_with_parent_tab() {
		WebElement body = DriverFactory.getDriver().findElement(By.xpath("//body"));
		body.sendKeys(Keys.CONTROL + "\t");
	}

	public void switch_handel_to_the_second_tab(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
	}

}
