@echo off
REM =============================================================================
REM BrowserStack Configuration and Test Execution Script
REM =============================================================================
REM 
REM This script sets up BrowserStack environment variables and runs the tests
REM BrowserStack Credentials:
REM   Username: amaurimoraismann_qL3hsa
REM   Access Key: v81FXLTPVGqPrG8sJxuM
REM
REM =============================================================================

echo.
echo ============================================================================
echo BrowserStack Test Execution Script
echo ============================================================================
echo.

REM Set BrowserStack environment variables (session-specific)
echo Setting BrowserStack environment variables...
set BROWSERSTACK_USERNAME=amaurimoraismann_qL3hsa
set BROWSERSTACK_ACCESS_KEY=v81FXLTPVGqPrG8sJxuM

echo.
echo Environment Variables Set:
echo   BROWSERSTACK_USERNAME: %BROWSERSTACK_USERNAME%
echo   BROWSERSTACK_ACCESS_KEY: (hidden for security)
echo.

REM Display usage information
echo.
echo ============================================================================
echo Usage Instructions
echo ============================================================================
echo.
echo Run BrowserStack Tests:
echo   mvn test -Ddriver=browserstack
echo.
echo Run Local Tests:
echo   mvn test -Ddriver=local
echo.
echo Run Specific Test Suite:
echo   mvn test -Dtest=GoogleSearchBrowserStackTest -Ddriver=browserstack
echo.
echo Run Regression Tests:
echo   mvn test -Dtest=GoogleSearchRegressionTest -Ddriver=browserstack
echo.
echo Run All Tests:
echo   mvn test
echo.
echo ============================================================================
echo.

REM Optionally run tests if parameter provided
if "%1"=="" (
    echo No test execution flag provided. Set environment variables only.
    echo.
    echo To run tests, execute one of the commands listed above.
    echo.
) else if "%1"=="browserstack" (
    echo Starting BrowserStack tests...
    mvn clean test -Ddriver=browserstack
) else if "%1"=="local" (
    echo Starting Local tests...
    mvn clean test -Ddriver=local
) else if "%1"=="all" (
    echo Starting All tests...
    mvn clean test
) else (
    echo Unknown parameter: %1
    echo Use: browserstack, local, or all
)

echo.
echo Script execution completed.
echo.
