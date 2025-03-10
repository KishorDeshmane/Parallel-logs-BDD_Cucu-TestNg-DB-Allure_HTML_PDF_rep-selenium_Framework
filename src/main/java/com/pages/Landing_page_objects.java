package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Landing_page_objects {
	private WebDriver driver;

	/* 
	 * 
	 * Log in button
	 * 
	 */
	
	@FindBy(xpath = "//button[text()='Become a partner']")
	private WebElement log_in_button;
	
	
	/* 
	 * 
	 * Constructor 
	 * 
	 */

	public Landing_page_objects(WebDriver driver) {
	    if (driver == null) {
	        throw new IllegalStateException("WebDriver is null in Landing_page_objects. Ensure it is initialized before calling this constructor.");
	    }
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	}


	/*
	 * 
	 *  Methods 
	 *  
	 */

	
	
	public boolean user_log_in_button_is_displayed() {
		return log_in_button.isDisplayed();
	}
	
}
