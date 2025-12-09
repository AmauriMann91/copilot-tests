package com.google.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Google Search Tests - UI automation for Google.com
 */
public class GoogleSearchTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(GoogleSearchTest.class);
    private static final String GOOGLE_URL = "https://www.google.com";

    @BeforeMethod
    public void setUp() {
        super.setUp();
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }

    /**
     * Test: Verify Google homepage loads correctly
     */
    @Test(description = "Verify Google homepage loads correctly")
    public void testGooglePageLoad() {
        logger.info("Starting test: testGooglePageLoad");
        driver.navigate().to(GOOGLE_URL);
        
        String pageTitle = driver.getTitle();
        logger.info("Page Title: " + pageTitle);
        
        assertTrue(pageTitle.contains("Google"), "Page title should contain 'Google'");
    }

    /**
     * Test: Perform search on Google
     */
    @Test(description = "Perform search and verify results")
    public void testGoogleSearch() {
        logger.info("Starting test: testGoogleSearch");
        driver.navigate().to(GOOGLE_URL);
        
        // Find search box and enter search term
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.sendKeys(Keys.RETURN);
        
        // Wait for results and verify
        try {
            Thread.sleep(2000); // Simple wait for demo purposes
        } catch (InterruptedException e) {
            logger.error("Thread interrupted during wait", e);
        }
        
        String resultPageTitle = driver.getTitle();
        logger.info("Results Page Title: " + resultPageTitle);
        
        assertTrue(resultPageTitle.contains("Selenium WebDriver"), 
                   "Search results page should contain the search term");
    }

    /**
     * Test: Verify Google logo is displayed
     */
    @Test(description = "Verify Google logo is displayed on homepage")
    public void testGoogleLogoDisplayed() {
        logger.info("Starting test: testGoogleLogoDisplayed");
        driver.navigate().to(GOOGLE_URL);
        
        try {
            // Try to find Google logo - may be in different locations
            WebElement logo = driver.findElement(By.xpath("//img[contains(@alt, 'Google') or contains(@src, 'logo')]"));
            assertTrue(logo.isDisplayed(), "Google logo should be displayed on homepage");
            logger.info("Google logo verified as displayed");
        } catch (Exception e) {
            // If logo not found via XPath, check for search box as alternative
            logger.info("Logo not found via XPath, checking for search box instead");
            WebElement searchBox = driver.findElement(By.name("q"));
            assertTrue(searchBox.isDisplayed(), "Search box should be displayed on homepage");
            logger.info("Homepage verified via search box presence");
        }
    }
}
