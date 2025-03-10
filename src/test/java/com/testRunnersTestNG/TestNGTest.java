package com.testRunnersTestNG;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		
		features = "src/test/resources/features",		
		tags="@Landing_page", //		tags="@Super_admi or @Super_admin", //		tags="@Positive",
		glue = {"stepDefinitions","com/applicationHooks"},
		monochrome=true,
		dryRun = false,
		plugin = {"pretty",
			"html:target/html_report/cucumber_reports.html",
			"junit:target/junit_report/junit_reports.xml",
			"json:target/json_report/json_reports.json",
			"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
			"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
			}
		)
public class TestNGTest extends AbstractTestNGCucumberTests {
	@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
