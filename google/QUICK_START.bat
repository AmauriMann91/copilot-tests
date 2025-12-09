@echo off
REM =============================================================================
REM Quick Start Guide - BrowserStack Test Execution
REM =============================================================================
REM This batch file demonstrates how to run the tests

echo.
echo ============================================================================
echo BROWSERSTACK QUICK START GUIDE
echo ============================================================================
echo.

REM Step 1: Set environment variables
echo STEP 1: Setting BrowserStack Environment Variables...
set BROWSERSTACK_USERNAME=amaurimoraismann_qL3hsa
set BROWSERSTACK_ACCESS_KEY=v81FXLTPVGqPrG8sJxuM
echo ✓ Environment variables set
echo.

REM Step 2: Display options
echo STEP 2: Available Test Execution Commands
echo.
echo Option 1 - Run BrowserStack Tests (Cloud):
echo   mvn test -Ddriver=browserstack
echo.
echo Option 2 - Run Local Tests:
echo   mvn test -Ddriver=local
echo.
echo Option 3 - Run All Tests:
echo   mvn test
echo.
echo Option 4 - Run Specific Test Class:
echo   mvn test -Dtest=GoogleSearchBrowserStackTest -Ddriver=browserstack
echo.
echo Option 5 - Run Regression Tests on BrowserStack:
echo   mvn test -Dtest=GoogleSearchRegressionTest -Ddriver=browserstack
echo.

REM Step 3: Build project
echo STEP 3: Building Project...
mvn clean compile > nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ Project compiled successfully
) else (
    echo ✗ Build failed
    exit /b 1
)
echo.

echo ============================================================================
echo SETUP COMPLETE
echo ============================================================================
echo.
echo Next steps:
echo 1. Choose one of the commands from Option 1-5 above
echo 2. Run the command in your terminal
echo 3. Check BrowserStack dashboard: https://automate.browserstack.com
echo 4. View test results and video recordings
echo.
echo For more information, see BROWSERSTACK_SETUP.md
echo.
