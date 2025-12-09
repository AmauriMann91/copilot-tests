# =============================================================================
# BrowserStack Configuration and Test Execution Script (PowerShell)
# =============================================================================
# 
# This script sets up BrowserStack environment variables and runs the tests
# BrowserStack Credentials:
#   Username: amaurimoraismann_qL3hsa
#   Access Key: v81FXLTPVGqPrG8sJxuM
#
# Usage:
#   .\run-browserstack-tests.ps1 [browserstack|local|all]
#
# =============================================================================

Write-Host ""
Write-Host "============================================================================"
Write-Host "BrowserStack Test Execution Script (PowerShell)"
Write-Host "============================================================================"
Write-Host ""

# Set BrowserStack environment variables
Write-Host "Setting BrowserStack environment variables..."
$env:BROWSERSTACK_USERNAME = "amaurimoraismann_qL3hsa"
$env:BROWSERSTACK_ACCESS_KEY = "v81FXLTPVGqPrG8sJxuM"

Write-Host ""
Write-Host "Environment Variables Set:"
Write-Host "  BROWSERSTACK_USERNAME: $($env:BROWSERSTACK_USERNAME)"
Write-Host "  BROWSERSTACK_ACCESS_KEY: (hidden for security)"
Write-Host ""

# Display usage information
Write-Host ""
Write-Host "============================================================================"
Write-Host "Usage Instructions"
Write-Host "============================================================================"
Write-Host ""
Write-Host "Run BrowserStack Tests:"
Write-Host "  mvn test -Ddriver=browserstack"
Write-Host ""
Write-Host "Run Local Tests:"
Write-Host "  mvn test -Ddriver=local"
Write-Host ""
Write-Host "Run Specific Test Suite:"
Write-Host "  mvn test -Dtest=GoogleSearchBrowserStackTest -Ddriver=browserstack"
Write-Host ""
Write-Host "Run Regression Tests:"
Write-Host "  mvn test -Dtest=GoogleSearchRegressionTest -Ddriver=browserstack"
Write-Host ""
Write-Host "Run All Tests:"
Write-Host "  mvn test"
Write-Host ""
Write-Host "============================================================================"
Write-Host ""

# Get the parameter
$testType = $args[0]

if ([string]::IsNullOrEmpty($testType)) {
    Write-Host "No test execution flag provided. Environment variables are set."
    Write-Host ""
    Write-Host "To run tests, execute one of the commands listed above."
    Write-Host ""
} elseif ($testType -eq "browserstack") {
    Write-Host "Starting BrowserStack tests..."
    Write-Host ""
    & mvn clean test -Ddriver=browserstack
} elseif ($testType -eq "local") {
    Write-Host "Starting Local tests..."
    Write-Host ""
    & mvn clean test -Ddriver=local
} elseif ($testType -eq "all") {
    Write-Host "Starting All tests..."
    Write-Host ""
    & mvn clean test
} else {
    Write-Host "Unknown parameter: $testType"
    Write-Host "Use: browserstack, local, or all"
    Write-Host ""
}

Write-Host ""
Write-Host "Script execution completed."
Write-Host ""
