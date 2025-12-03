# NEW README 
# Google UI Automated Tests

This project contains UI automated tests for Google Chrome browser targeting Google.com.

## Technologies

- **Selenium WebDriver** - Browser automation framework
- **TestNG** - Test framework
- **WebDriverManager** - Automatic WebDriver management
- **Maven** - Build tool
- **Java 11+** - Programming language

## Project Structure

```
google/
├── src/
│   ├── main/
│   │   ├── java/com/google/automation/
│   │   └── resources/
│   └── test/
│       ├── java/com/google/automation/
│       │   ├── BaseTest.java         (Base test class with setup/teardown)
│       │   └── GoogleSearchTest.java (Test cases)
│       └── resources/
│           └── testng.xml            (TestNG configuration)
├── pom.xml
└── README.md
```

## Test Cases

### GoogleSearchTest
- **testGooglePageLoad** - Verifies Google homepage loads correctly
- **testGoogleSearch** - Performs a search and verifies results
- **testGoogleLogoDisplayed** - Verifies Google logo is displayed on homepage

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome browser installed
- Internet connection

## Setup & Installation

1. Navigate to the google folder:
   ```
   cd google
   ```

2. Install dependencies:
   ```
   mvn clean install
   ```

## Running Tests

Run all tests:
```
mvn test
```

Run specific test class:
```
mvn test -Dtest=GoogleSearchTest
```

Run specific test method:
```
mvn test -Dtest=GoogleSearchTest#testGoogleSearch
```

Run tests in headless mode:
```
mvn test -Dheadless=true
```

## Features

- **Automatic WebDriver Management** - WebDriverManager handles ChromeDriver installation
- **Logging** - SLF4J logging for test execution tracking
- **Base Test Class** - Common setup/teardown logic for all tests
- **TestNG Configuration** - XML configuration for test execution

## Dependencies

- selenium-java: 4.15.0
- webdrivermanager: 5.6.3
- testng: 7.8.1
- slf4j-api & slf4j-simple: 2.0.9

## Notes

- Tests use a simple Thread.sleep() for waits in demo purposes. For production, consider using WebDriverWait with explicit waits.
- Chrome browser must be installed on the system
- Tests require internet connection to access google.com
