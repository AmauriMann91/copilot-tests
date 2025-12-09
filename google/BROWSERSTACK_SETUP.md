# BrowserStack Integration Guide

This document describes how to run your Google Search automation tests on BrowserStack cloud infrastructure.

## Overview

This project is configured to run tests both locally and on BrowserStack. You can switch between environments using Maven profiles and system properties.

## BrowserStack Credentials

```
Username: amaurimoraismann_qL3hsa
Access Key: v81FXLTPVGqPrG8sJxuM
Hub URL: https://hub.browserstack.com/wd/hub
```

## Setup Instructions

### 1. Set Environment Variables

#### Option A: Using Batch Script (Windows CMD)
```cmd
run-browserstack-tests.bat
```

#### Option B: Using PowerShell Script (Windows PowerShell)
```powershell
.\run-browserstack-tests.ps1
```

#### Option C: Manual Setup (Windows CMD)
```cmd
setx BROWSERSTACK_USERNAME "amaurimoraismann_qL3hsa"
setx BROWSERSTACK_ACCESS_KEY "v81FXLTPVGqPrG8sJxuM"
```

Then in the same session:
```cmd
set BROWSERSTACK_USERNAME=amaurimoraismann_qL3hsa
set BROWSERSTACK_ACCESS_KEY=v81FXLTPVGqPrG8sJxuM
```

#### Option D: Manual Setup (PowerShell)
```powershell
$env:BROWSERSTACK_USERNAME = "amaurimoraismann_qL3hsa"
$env:BROWSERSTACK_ACCESS_KEY = "v81FXLTPVGqPrG8sJxuM"
```

### 2. Verify Environment Variables

#### CMD:
```cmd
echo %BROWSERSTACK_USERNAME%
echo %BROWSERSTACK_ACCESS_KEY%
```

#### PowerShell:
```powershell
Write-Host $env:BROWSERSTACK_USERNAME
Write-Host $env:BROWSERSTACK_ACCESS_KEY
```

## Running Tests

### Run BrowserStack Tests
```bash
mvn test -Ddriver=browserstack
```

### Run Local Tests
```bash
mvn test -Ddriver=local
```

### Run Specific Test Class
```bash
mvn test -Dtest=GoogleSearchBrowserStackTest -Ddriver=browserstack
```

### Run Regression Tests
```bash
mvn test -Dtest=GoogleSearchRegressionTest -Ddriver=browserstack
```

### Run All Tests
```bash
mvn test
```

### Clean Build and Test
```bash
mvn clean test -Ddriver=browserstack
```

### Run Tests with Detailed Logging
```bash
mvn test -Ddriver=browserstack -X
```

## Project Structure

```
google/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/google/automation/
│   └── test/
│       ├── java/
│       │   └── com/google/automation/
│       │       ├── BaseTest.java                    (Local testing base class)
│       │       ├── BrowserStackBaseTest.java        (BrowserStack base class)
│       │       ├── BrowserStackConfig.java          (Configuration utility)
│       │       ├── GoogleSearchTest.java            (Basic Google search tests)
│       │       ├── GoogleSearchRegressionTest.java  (Regression test suite)
│       │       └── GoogleSearchBrowserStackTest.java (BrowserStack-specific tests)
│       └── resources/
│           └── testng.xml
├── pom.xml
├── run-browserstack-tests.bat  (Windows Batch script)
├── run-browserstack-tests.ps1  (PowerShell script)
└── README.md
```

## Test Suites

### 1. GoogleSearchTest
Basic Google search functionality tests running locally or on BrowserStack.
- Homepage load verification
- Search execution
- Logo visibility

### 2. GoogleSearchRegressionTest
Comprehensive regression test suite ensuring critical functionality remains stable.
- Homepage accessibility
- Search box functionality
- Search result display
- Navigation between pages
- Page stability
- Input handling

### 3. GoogleSearchBrowserStackTest
Advanced tests specifically designed for BrowserStack cloud testing.
- Cross-browser testing capabilities
- Performance monitoring
- Session management
- Multiple sequential searches
- Page navigation
- Load time verification

## Classes Overview

### BrowserStackConfig
Manages BrowserStack credentials and capabilities.

**Methods:**
- `getHubUrl()` - Returns BrowserStack hub URL with credentials
- `getChromeCapabilities()` - Chrome browser capabilities
- `getFirefoxCapabilities()` - Firefox browser capabilities
- `getSafariCapabilities()` - Safari browser capabilities
- `getEncodedCredentials()` - Base64 encoded credentials
- `isLocalTestingEnabled()` - Check if local testing is enabled
- `getCapabilitiesWithSessionName(String)` - Custom session naming
- `getCapabilitiesWithBuildName(String)` - Custom build naming

### BrowserStackBaseTest
Base class for all BrowserStack tests. Handles both local and remote WebDriver initialization.

**Methods:**
- `setUp()` - Initialize WebDriver (local or BrowserStack)
- `tearDown()` - Close WebDriver and update test status
- `getDriverType()` - Get driver type from system property
- `getSessionId()` - Get BrowserStack session ID
- `updateBrowserStackStatus(boolean, String)` - Update test result on BrowserStack

## Configuration Options

### Capabilities Configuration
Edit `BrowserStackConfig.java` to modify:
- Browser versions
- Platform/OS
- Build name
- Project name
- Session names
- Debug logging
- Video recording
- Network logs

### Local Testing
To enable local testing:
```cmd
set BROWSERSTACK_LOCAL=true
set BROWSERSTACK_LOCAL_IDENTIFIER=your_identifier
```

Then run:
```bash
mvn test -Ddriver=browserstack
```

## BrowserStack Dashboard

Monitor your test execution:
1. Visit: https://automate.browserstack.com
2. Login with your credentials
3. View:
   - Active sessions
   - Completed tests
   - Video recordings
   - Network logs
   - Console logs
   - Screenshots

## Troubleshooting

### Environment Variables Not Set
**Problem:** Tests fail with "BROWSERSTACK_USERNAME environment variable not set"

**Solution:** 
1. Verify variables are set using appropriate method for your shell
2. If using `setx`, ensure you open a new terminal window
3. Run the configuration script again

### Connection Issues
**Problem:** Cannot connect to BrowserStack hub

**Solution:**
1. Check internet connection
2. Verify credentials are correct
3. Check BrowserStack status: https://status.browserstack.com
4. Verify firewall allows outbound connections to hub.browserstack.com:443

### Test Timeout
**Problem:** Tests timeout on BrowserStack

**Solution:**
1. Increase timeout values in `BrowserStackBaseTest.java`
2. Check BrowserStack session logs for details
3. Verify element selectors are correct for target browsers

### Browser Not Available
**Problem:** Requested browser/version not available

**Solution:**
1. Check available browsers at: https://www.browserstack.com/automate/browsers
2. Update capabilities in `BrowserStackConfig.java`

## Maven Dependencies

The project includes:
- Selenium WebDriver 4.10.0
- TestNG 6.14.3
- WebDriverManager 5.7.0
- BrowserStack Local Java 1.2.8
- SLF4J 1.7.36
- GSON 2.10.1
- Apache HttpClient5 5.2.1

## Switching Between Local and BrowserStack

### Using System Property
```bash
mvn test -Ddriver=local    # Local testing
mvn test -Ddriver=browserstack  # BrowserStack testing
```

### Default Behavior
If `-Ddriver` is not specified, defaults to BrowserStack.

## Parallel Execution

Tests can run in parallel on BrowserStack. Configure in `testng.xml`:
```xml
<suite parallel="tests" thread-count="2">
```

## Performance Tips

1. Use implicit waits appropriately
2. Avoid excessive `Thread.sleep()` calls
3. Use WebDriverWait for element waits
4. Close unused browser windows
5. Clear browser cache between tests

## Security Considerations

1. **Never commit credentials** - Use environment variables
2. **Rotate credentials regularly** - Use BrowserStack account settings
3. **Use local identifiers** - For sensitive local testing
4. **Monitor active sessions** - Check BrowserStack dashboard
5. **Use VPN** - For additional security when testing locally

## Support

For BrowserStack issues, visit:
- Documentation: https://www.browserstack.com/automate/java
- Support: https://www.browserstack.com/support
- Status Page: https://status.browserstack.com

## Next Steps

1. Set up environment variables
2. Run local tests first: `mvn test -Ddriver=local`
3. Verify BrowserStack credentials
4. Run BrowserStack tests: `mvn test -Ddriver=browserstack`
5. Check BrowserStack dashboard for results
6. Review logs and videos for any issues

---

**Last Updated:** December 2025
