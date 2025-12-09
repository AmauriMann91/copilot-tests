package com.google.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * BrowserStack Base Test class
 * Handles both local and BrowserStack remote WebDriver initialization
 */
public class BrowserStackBaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BrowserStackBaseTest.class);
    protected WebDriver driver;
    protected BrowserStackConfig bstackConfig;
    private static final String LOCAL_DRIVER = "local";
    private static final String BROWSERSTACK_DRIVER = "browserstack";

    /**
     * Get the driver type from system property
     * Default is BrowserStack, can be overridden with -Ddriver=local
     */
    protected String getDriverType() {
        return System.getProperty("driver", BROWSERSTACK_DRIVER);
    }

    /**
     * Setup WebDriver before each test
     * Uses BrowserStack remote driver or local Chrome driver based on configuration
     */
    @BeforeMethod
    public void setUp() {
        try {
            String driverType = getDriverType();
            logger.info("Initializing WebDriver with type: " + driverType);

            if (BROWSERSTACK_DRIVER.equalsIgnoreCase(driverType)) {
                setupBrowserStackDriver();
            } else {
                setupLocalDriver();
            }

            logger.info("WebDriver initialized successfully with type: " + driverType);
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver", e);
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }

    /**
     * Setup BrowserStack RemoteWebDriver
     */
    private void setupBrowserStackDriver() throws MalformedURLException {
        try {
            bstackConfig = new BrowserStackConfig();
            
            logger.info("Setting up BrowserStack RemoteWebDriver");
            logger.info("BrowserStack Hub URL: " + bstackConfig.getHubUrl());
            
            // Get Chrome capabilities for BrowserStack
            Map<String, Object> capabilities = bstackConfig.getChromeCapabilities();
            
            // Initialize RemoteWebDriver with Selenium 4 compatible syntax
            org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
            capabilities.forEach((key, value) -> options.setCapability(key, value));
            
            driver = new RemoteWebDriver(
                new URL(bstackConfig.getHubUrl()),
                options
            );
            
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            
            logger.info("BrowserStack RemoteWebDriver initialized successfully");
            logger.info("Session ID: " + ((RemoteWebDriver) driver).getSessionId());
            
        } catch (Exception e) {
            logger.error("Failed to initialize BrowserStack driver", e);
            throw new RuntimeException("BrowserStack driver initialization failed", e);
        }
    }

    /**
     * Setup local Chrome WebDriver
     */
    private void setupLocalDriver() {
        try {
            logger.info("Setting up Local Chrome WebDriver");
            
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            // Disable notifications and other popups
            options.addArguments("--no-first-run");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-logging"});
            options.setExperimentalOption("useAutomationExtension", false);
            
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            
            logger.info("Local Chrome WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize local WebDriver", e);
            throw new RuntimeException("Local WebDriver initialization failed", e);
        }
    }

    /**
     * Quit WebDriver after each test
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                // Log session information for BrowserStack driver
                if (driver instanceof RemoteWebDriver) {
                    RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
                    logger.info("Closing BrowserStack session: " + remoteDriver.getSessionId());
                }
                
                driver.quit();
                logger.info("WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Error closing WebDriver", e);
            }
        }
    }

    /**
     * Get the current session ID for BrowserStack driver
     */
    protected String getSessionId() {
        if (driver instanceof RemoteWebDriver) {
            return ((RemoteWebDriver) driver).getSessionId().toString();
        }
        return null;
    }

    /**
     * Get BrowserStack test status update URL
     */
    protected String getBrowserStackStatusUrl(String sessionId, boolean passed) {
        if (bstackConfig != null) {
            String status = passed ? "passed" : "failed";
            return String.format(
                "https://api.browserstack.com/automate/sessions/%s.json",
                sessionId
            );
        }
        return null;
    }

    /**
     * Update test status on BrowserStack
     */
    protected void updateBrowserStackStatus(boolean passed, String reason) {
        if (driver instanceof RemoteWebDriver && bstackConfig != null) {
            try {
                RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
                String sessionId = remoteDriver.getSessionId().toString();
                
                // Execute script to set test status in BrowserStack
                String script = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" 
                    + (passed ? "passed" : "failed") 
                    + "\", \"reason\": \"" 
                    + reason 
                    + "\"}}";
                
                remoteDriver.executeScript(script);
                logger.info("BrowserStack session status updated: " + (passed ? "PASSED" : "FAILED"));
            } catch (Exception e) {
                logger.warn("Failed to update BrowserStack status", e);
            }
        }
    }
}
