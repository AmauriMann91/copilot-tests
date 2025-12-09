package com.google.automation;

import java.util.HashMap;
import java.util.Map;

/**
 * BrowserStack Configuration utility class
 * Manages credentials and capabilities for BrowserStack remote testing
 */
public class BrowserStackConfig {
    private static final String BROWSERSTACK_URL = "https://hub.browserstack.com/wd/hub";
    
    private String username;
    private String accessKey;
    private String browserstackLocal;
    private String browserstackLocalIdentifier;

    /**
     * Constructor - initializes BrowserStack credentials from environment variables
     */
    public BrowserStackConfig() {
        this.username = System.getenv("BROWSERSTACK_USERNAME");
        this.accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        this.browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
        this.browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
        
        if (this.username == null || this.username.isEmpty()) {
            throw new RuntimeException("BROWSERSTACK_USERNAME environment variable not set");
        }
        if (this.accessKey == null || this.accessKey.isEmpty()) {
            throw new RuntimeException("BROWSERSTACK_ACCESS_KEY environment variable not set");
        }
    }

    /**
     * Get BrowserStack Hub URL
     */
    public String getHubUrl() {
        return "https://" + username + ":" + accessKey + "@" + BROWSERSTACK_URL.substring(8);
    }

    /**
     * Get basic BrowserStack capabilities
     */
    public Map<String, Object> getBasicCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        
        // Basic capabilities
        capabilities.put("browserName", "Chrome");
        capabilities.put("browserVersion", "latest");
        capabilities.put("platformName", "Windows");
        capabilities.put("platformVersion", "11");
        
        // BrowserStack specific
        capabilities.put("bstack:options", getBStackOptions());
        
        return capabilities;
    }

    /**
     * Get Chrome capabilities for BrowserStack
     */
    public Map<String, Object> getChromeCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        
        // W3C capabilities
        capabilities.put("browserName", "Chrome");
        capabilities.put("browserVersion", "latest");
        capabilities.put("platformName", "Windows");
        
        // BrowserStack specific options (must be in bstack:options)
        capabilities.put("bstack:options", getBStackOptions());
        
        return capabilities;
    }

    /**
     * Get Firefox capabilities for BrowserStack
     */
    public Map<String, Object> getFirefoxCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        
        capabilities.put("browserName", "Firefox");
        capabilities.put("browserVersion", "latest");
        capabilities.put("platformName", "Windows");
        
        capabilities.put("bstack:options", getBStackOptions());
        
        return capabilities;
    }

    /**
     * Get Safari capabilities for BrowserStack
     */
    public Map<String, Object> getSafariCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        
        capabilities.put("browserName", "Safari");
        capabilities.put("browserVersion", "latest");
        capabilities.put("platformName", "macOS");
        capabilities.put("platformVersion", "Sonoma");
        
        capabilities.put("bstack:options", getBStackOptions());
        
        return capabilities;
    }

    /**
     * Get BrowserStack specific options
     */
    private Map<String, Object> getBStackOptions() {
        Map<String, Object> bstackOptions = new HashMap<>();
        
        bstackOptions.put("userName", username);
        bstackOptions.put("accessKey", accessKey);
        bstackOptions.put("buildName", "Google Search Tests - Regression Suite");
        bstackOptions.put("projectName", "Google Search Automation");
        bstackOptions.put("sessionName", "Test Session");
        bstackOptions.put("debug", "true");
        bstackOptions.put("networkLogs", "true");
        bstackOptions.put("consoleLogs", "warnings");
        bstackOptions.put("video", "true");
        
        // Local testing if enabled
        if (browserstackLocal != null && browserstackLocal.equals("true")) {
            bstackOptions.put("local", "true");
            if (browserstackLocalIdentifier != null && !browserstackLocalIdentifier.isEmpty()) {
                bstackOptions.put("localIdentifier", browserstackLocalIdentifier);
            }
        }
        
        return bstackOptions;
    }

    /**
     * Get credentials as base64 encoded string
     */
    public String getEncodedCredentials() {
        String credentials = username + ":" + accessKey;
        return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    /**
     * Get username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get access key
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Check if local testing is enabled
     */
    public boolean isLocalTestingEnabled() {
        return browserstackLocal != null && browserstackLocal.equals("true");
    }

    /**
     * Get local testing identifier
     */
    public String getLocalIdentifier() {
        return browserstackLocalIdentifier;
    }

    /**
     * Get capabilities with custom session name
     */
    public Map<String, Object> getCapabilitiesWithSessionName(String sessionName) {
        Map<String, Object> capabilities = getChromeCapabilities();
        Map<String, Object> bstackOptions = (Map<String, Object>) capabilities.get("bstack:options");
        bstackOptions.put("sessionName", sessionName);
        
        return capabilities;
    }

    /**
     * Get capabilities with custom build name
     */
    public Map<String, Object> getCapabilitiesWithBuildName(String buildName) {
        Map<String, Object> capabilities = getChromeCapabilities();
        Map<String, Object> bstackOptions = (Map<String, Object>) capabilities.get("bstack:options");
        bstackOptions.put("buildName", buildName);
        
        return capabilities;
    }
}
