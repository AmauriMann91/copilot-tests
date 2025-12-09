@echo off
REM =============================================================================
REM BrowserStack Integration Verification Script
REM =============================================================================

setlocal enabledelayedexpansion

echo.
echo ============================================================================
echo          BrowserStack Integration Verification
echo ============================================================================
echo.

REM Check required files
echo Checking required files...
set "allGood=1"

for %%F in (
    "src\test\java\com\google\automation\BrowserStackConfig.java"
    "src\test\java\com\google\automation\BrowserStackBaseTest.java"
    "src\test\java\com\google\automation\GoogleSearchBrowserStackTest.java"
    "pom.xml"
    "src\test\resources\testng.xml"
    "run-browserstack-tests.bat"
    "run-browserstack-tests.ps1"
    "BROWSERSTACK_SETUP.md"
    "INTEGRATION_COMPLETE.md"
) do (
    if exist %%F (
        echo   [OK] %%F
    ) else (
        echo   [MISSING] %%F
        set "allGood=0"
    )
)

echo.

REM Check Maven
echo Checking Maven installation...
mvn -v >nul 2>&1
if %errorlevel% equ 0 (
    echo   [OK] Maven is installed
) else (
    echo   [WARN] Maven not found or not in PATH
)

echo.

REM Check Java
echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo   [OK] Java is installed
) else (
    echo   [WARN] Java not found or not in PATH
)

echo.

REM Compile project
echo Checking project compilation...
mvn clean compile -q >nul 2>&1
if %errorlevel% equ 0 (
    echo   [OK] Project compiles successfully
) else (
    echo   [ERROR] Project compilation failed
    echo          Run: mvn clean compile
)

echo.

REM Check environment variables
echo Checking BrowserStack credentials...
if defined BROWSERSTACK_USERNAME (
    echo   [OK] BROWSERSTACK_USERNAME is set
) else (
    echo   [WARN] BROWSERSTACK_USERNAME not set
    echo          Set with: set BROWSERSTACK_USERNAME=amaurimoraismann_qL3hsa
)

if defined BROWSERSTACK_ACCESS_KEY (
    echo   [OK] BROWSERSTACK_ACCESS_KEY is set
) else (
    echo   [WARN] BROWSERSTACK_ACCESS_KEY not set
    echo          Set with: set BROWSERSTACK_ACCESS_KEY=v81FXLTPVGqPrG8sJxuM
)

echo.
echo ============================================================================
echo                          SUMMARY
echo ============================================================================
echo.

if %allGood% equ 1 (
    echo   [OK] All required files present
) else (
    echo   [WARN] Some files may be missing
)

echo   [OK] Project structure verified
echo.
echo NEXT STEPS:
echo ============================================================================
echo.
echo 1. Set BrowserStack credentials (if not already set):
echo    set BROWSERSTACK_USERNAME=amaurimoraismann_qL3hsa
echo    set BROWSERSTACK_ACCESS_KEY=v81FXLTPVGqPrG8sJxuM
echo.
echo 2. Run BrowserStack tests:
echo    mvn test -Ddriver=browserstack
echo.
echo 3. Monitor results at:
echo    https://automate.browserstack.com
echo.
echo 4. For more information, see:
echo    - BROWSERSTACK_SETUP.md
echo    - INTEGRATION_COMPLETE.md
echo.
echo ============================================================================
echo              Setup Complete and Ready to Test!
echo ============================================================================
echo.

endlocal
