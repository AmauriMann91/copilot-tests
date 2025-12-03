# UI Automated Tests (Maven + Selenium) part 2

This repository contains a Maven Java project to run UI automated tests with Selenium and WebDriverManager.

## Getting started

- Build and run tests:

```powershell
mvn test
```

- Run tests in headless mode (useful for CI):

```powershell
mvn -Dheadless=true test
```

## Notes

- Tests use Chrome via WebDriverManager which downloads the matching driver binary automatically. Ensure Chrome is installed on the machine running the tests.
- WebDriverManager will automatically download the ChromeDriver version matching your installed Chrome browser.
- Java 8 is configured in the `pom.xml`. Change `maven.compiler.source`/`target` if you need a different Java version.

## Files

- `pom.xml` — Maven configuration with Selenium 3.141, WebDriverManager, and JUnit 4.
- `src/test/java/com/example/ui/GoogleSearchTest.java` — sample test that opens Google and validates the page title.
- `.gitignore` — basic ignores for build and IDE files.

