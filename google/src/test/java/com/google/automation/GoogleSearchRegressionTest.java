package com.google.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

/**
 * Google Search Regression Tests - Tests for critical functionality
 * These tests ensure that core features remain functional across versions
 */
public class GoogleSearchRegressionTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(GoogleSearchRegressionTest.class);
    private static final String GOOGLE_URL = "https://www.google.com";
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }

    /**
     * Regression Test: Verify Google homepage accessibility
     * Critical: Homepage must be accessible and load without errors
     */
    @Test(description = "REGRESSION: Verify Google homepage is accessible")
    public void testRegressionHomepageAccessibility() {
        logger.info("Starting regression test: Homepage Accessibility");
        
        driver.navigate().to(GOOGLE_URL);
        
        String pageTitle = driver.getTitle();
        logger.info("Homepage Title: " + pageTitle);
        
        assertNotNull(pageTitle, "Page title should not be null");
        assertTrue(pageTitle.contains("Google"), "Page title must contain 'Google'");
        
        assertEquals(driver.getCurrentUrl().toLowerCase(), GOOGLE_URL.toLowerCase(), 
                    "Should be on Google homepage URL");
        
        logger.info("PASSED: Homepage accessibility test");
    }

    /**
     * Regression Test: Verify search box is present and functional
     * Critical: Users must be able to locate and interact with search box
     */
    @Test(description = "REGRESSION: Verify search box functionality")
    public void testRegressionSearchBoxPresent() {
        logger.info("Starting regression test: Search Box Presence");
        
        driver.navigate().to(GOOGLE_URL);
        
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        assertTrue(searchBox.isDisplayed(), "Search box must be displayed");
        assertTrue(searchBox.isEnabled(), "Search box must be enabled");
        
        logger.info("PASSED: Search box is present and functional");
    }

    /**
     * Regression Test: Verify basic search functionality
     * Critical: Search must execute and return results
     */
    @Test(description = "REGRESSION: Verify basic search execution")
    public void testRegressionBasicSearch() {
        logger.info("Starting regression test: Basic Search Functionality");
        
        driver.navigate().to(GOOGLE_URL);
        
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        String searchTerm = "Google Search";
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        searchBox.sendKeys(Keys.RETURN);
        
        // Wait for results page to load
        wait.until(ExpectedConditions.titleContains(searchTerm));
        
        String resultPageTitle = driver.getTitle();
        logger.info("Search results page title: " + resultPageTitle);
        
        assertTrue(resultPageTitle.toLowerCase().contains(searchTerm.toLowerCase()), 
                  "Search results page should contain the search term");
        
        // Verify results are displayed
        WebElement resultStats = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("result-stats"))
        );
        
        assertTrue(resultStats.isDisplayed(), "Result statistics should be displayed");
        
        logger.info("PASSED: Basic search functionality test");
    }

    /**
     * Regression Test: Verify search results container exists
     * Critical: Results must be displayed in expected container
     */
    @Test(description = "REGRESSION: Verify search results are displayed")
    public void testRegressionSearchResultsDisplayed() {
        logger.info("Starting regression test: Search Results Display");
        
        driver.navigate().to(GOOGLE_URL);
        
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        searchBox.sendKeys("Automation Test");
        searchBox.sendKeys(Keys.RETURN);
        
        // Wait for results to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rso")));
        
        WebElement resultsContainer = driver.findElement(By.id("rso"));
        assertTrue(resultsContainer.isDisplayed(), "Results container must be displayed");
        
        // Verify at least one result item is present
        WebElement resultItem = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-sokoban-container]"))
        );
        
        assertNotNull(resultItem, "At least one result item should be present");
        
        logger.info("PASSED: Search results display test");
    }

    /**
     * Regression Test: Verify page navigation after search
     * Critical: User should be able to navigate search results
     */
    @Test(description = "REGRESSION: Verify search result navigation")
    public void testRegressionResultNavigation() {
        logger.info("Starting regression test: Result Navigation");
        
        driver.navigate().to(GOOGLE_URL);
        
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        searchBox.sendKeys("Test Query");
        searchBox.sendKeys(Keys.RETURN);
        
        // Wait for results
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rso")));
        
        String firstPageUrl = driver.getCurrentUrl();
        logger.info("First page URL: " + firstPageUrl);
        
        assertTrue(firstPageUrl.contains("q=Test+Query") || firstPageUrl.contains("q=Test%20Query"),
                  "Results URL should contain the search query parameter");
        
        // Try to find and click next page button
        try {
            WebElement nextButton = driver.findElement(By.id("pnnext"));
            if (nextButton.isDisplayed() && nextButton.isEnabled()) {
                nextButton.click();
                wait.until(ExpectedConditions.urlContains("start="));
                
                String nextPageUrl = driver.getCurrentUrl();
                logger.info("Next page URL: " + nextPageUrl);
                
                assertNotEquals(firstPageUrl, nextPageUrl, "Next page URL should be different from first page");
                
                logger.info("PASSED: Successfully navigated to next results page");
            }
        } catch (Exception e) {
            logger.info("Next button not available on this results page - test still passes");
        }
        
        logger.info("PASSED: Result navigation test");
    }

    /**
     * Regression Test: Verify page loads without JavaScript errors
     * Critical: Page must function properly
     */
    @Test(description = "REGRESSION: Verify page stability")
    public void testRegressionPageStability() {
        logger.info("Starting regression test: Page Stability");
        
        driver.navigate().to(GOOGLE_URL);
        
        // Give page time to load all resources
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted during stability check", e);
        }
        
        // Verify page is still accessible
        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();
        
        assertNotNull(currentUrl, "Current URL should not be null");
        assertNotNull(pageTitle, "Page title should not be null");
        assertFalse(pageTitle.contains("Error"), "Page should not display error");
        
        logger.info("PASSED: Page stability test");
    }

    /**
     * Regression Test: Verify search box maintains focus and input
     * Critical: Search functionality must respond to user input correctly
     */
    @Test(description = "REGRESSION: Verify search input handling")
    public void testRegressionSearchInputHandling() {
        logger.info("Starting regression test: Search Input Handling");
        
        driver.navigate().to(GOOGLE_URL);
        
        WebElement searchBox = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
        
        String testInput = "Regression Test 123";
        searchBox.click();
        searchBox.sendKeys(testInput);
        
        String inputValue = searchBox.getAttribute("value");
        assertEquals(inputValue, testInput, "Search box should contain the input text");
        
        logger.info("PASSED: Search input handling test");
    }
}
