package com.example.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertTrue;

public class GoogleSearchTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Toggle headless with -Dheadless=true
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
        }
        driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void openGoogle_homepageTitleContainsGoogle() {
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        assertTrue("Title should contain 'Google' but was: " + title, title.contains("Google"));
    }
}
