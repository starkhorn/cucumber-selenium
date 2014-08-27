package com.starkhorn.google.web;

import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchStepDefs {
	
	private WebDriver driver;

	@Before
	public void setup() {
		
		driver = getChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	private WebDriver getIEDriver() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("web-drivers/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", url.getPath());
		
		return new InternetExplorerDriver();
	}
	
	private WebDriver getFirefoxDriver() {
		return new FirefoxDriver();
	}
	
	private WebDriver getChromeDriver() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("web-drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", url.getPath());
		
		return new ChromeDriver();
	}
	
	@After
	public void teardown() {
		driver.quit();
	}
	
	@Given("^I am at Google homepage$")
	public void i_am_at_Google_homepage() throws Throwable {
	    driver.get("https://www.google.com");
	}

	@When("^I search for \"(.*?)\"$")
	public void i_search_for(String searchText) throws Throwable {
		WebElement searchInput = getElementOrNull(By.cssSelector("input.gbqfif"));
		
		searchInput.sendKeys(searchText);
		searchInput.sendKeys(Keys.ENTER);
	}

	@Then("^I should see \"(.*?)\" as results$")
	public void i_should_see_as_results(String arg1) throws Throwable {
		WebElement link = getElementOrNull(By.linkText(arg1));
		
		assertNotNull(link);
	}
	
	private WebElement getElementOrNull(By criteria) {
		List<WebElement> elements = getElements(criteria);
		
		return firstElementOrNull(elements);
	}

	private WebElement firstElementOrNull(List<WebElement> elements) {
		return elements.size() > 0 ? elements.get(0) : null;
	}

	private List<WebElement> getElements(By criteria) {
		return driver.findElements(criteria);
	}
	
}
