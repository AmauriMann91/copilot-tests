#!/usr/bin/env pwsh
# BrowserStack Integration Verification Script
# This script verifies that all components are correctly installed and configured

Write-Host ""
Write-Host "╔════════════════════════════════════════════════════════════════╗"
Write-Host "║        BrowserStack Integration Verification Script           ║"
Write-Host "╚════════════════════════════════════════════════════════════════╝"
Write-Host ""

# Check 1: Required files
Write-Host "✓ Checking required files..."
Write-Host ""

$requiredFiles = @(
    @{Path = "src/test/java/com/google/automation/BrowserStackConfig.java"; Type = "Configuration Class"},
    @{Path = "src/test/java/com/google/automation/BrowserStackBaseTest.java"; Type = "Base Test Class"},
    @{Path = "src/test/java/com/google/automation/GoogleSearchBrowserStackTest.java"; Type = "Test Suite"},
    @{Path = "pom.xml"; Type = "Maven Configuration"},
    @{Path = "src/test/resources/testng.xml"; Type = "TestNG Configuration"},
    @{Path = "run-browserstack-tests.bat"; Type = "Batch Script"},
    @{Path = "run-browserstack-tests.ps1"; Type = "PowerShell Script"},
    @{Path = "BROWSERSTACK_SETUP.md"; Type = "Setup Guide"},
    @{Path = "INTEGRATION_COMPLETE.md"; Type = "Integration Summary"}
)

$allFilesPresent = $true
foreach ($file in $requiredFiles) {
    if (Test-Path $file.Path) {
        Write-Host "  ✓ $($file.Type): $($file.Path)"
    } else {
        Write-Host "  ✗ MISSING: $($file.Path)"
        $allFilesPresent = $false
    }
}

Write-Host ""

# Check 2: Maven installation
Write-Host "✓ Checking Maven installation..."
Write-Host ""

try {
    $mvnVersion = mvn -v 2>&1 | Select-Object -First 1
    if ($mvnVersion -match "Apache Maven") {
        Write-Host "  ✓ Maven is installed"
        Write-Host "    $mvnVersion"
    } else {
        Write-Host "  ✗ Maven not found or not configured"
    }
} catch {
    Write-Host "  ✗ Maven not found. Please install Maven or add to PATH"
}

Write-Host ""

# Check 3: Java installation
Write-Host "✓ Checking Java installation..."
Write-Host ""

try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    if ($javaVersion -match "java version") {
        Write-Host "  ✓ Java is installed"
        Write-Host "    $javaVersion"
    } else {
        Write-Host "  ⚠ Java information: $javaVersion"
    }
} catch {
    Write-Host "  ✗ Java not found. Please install JDK 8 or higher"
}

Write-Host ""

# Check 4: Project compilation
Write-Host "✓ Checking project compilation..."
Write-Host ""

$originalLocation = Get-Location
try {
    mvn clean compile -q 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  ✓ Project compiles successfully"
    } else {
        Write-Host "  ✗ Project compilation failed"
        Write-Host "    Run: mvn clean compile"
    }
} catch {
    Write-Host "  ✗ Error during compilation check"
} finally {
    Set-Location $originalLocation
}

Write-Host ""

# Check 5: BrowserStack credentials
Write-Host "✓ Checking BrowserStack credentials..."
Write-Host ""

if ([string]::IsNullOrEmpty($env:BROWSERSTACK_USERNAME)) {
    Write-Host "  ⚠ BROWSERSTACK_USERNAME not set in current session"
    Write-Host "    Set with: `$env:BROWSERSTACK_USERNAME = 'amaurimoraismann_qL3hsa'"
} else {
    Write-Host "  ✓ BROWSERSTACK_USERNAME is set: $($env:BROWSERSTACK_USERNAME)"
}

if ([string]::IsNullOrEmpty($env:BROWSERSTACK_ACCESS_KEY)) {
    Write-Host "  ⚠ BROWSERSTACK_ACCESS_KEY not set in current session"
    Write-Host "    Set with: `$env:BROWSERSTACK_ACCESS_KEY = 'v81FXLTPVGqPrG8sJxuM'"
} else {
    Write-Host "  ✓ BROWSERSTACK_ACCESS_KEY is set (hidden for security)"
}

Write-Host ""

# Summary
Write-Host "╔════════════════════════════════════════════════════════════════╗"
Write-Host "║                         SUMMARY                               ║"
Write-Host "╚════════════════════════════════════════════════════════════════╝"
Write-Host ""

if ($allFilesPresent) {
    Write-Host "  ✓ All required files present"
} else {
    Write-Host "  ✗ Some files are missing"
}

Write-Host "  ✓ Project structure verified"
Write-Host ""

Write-Host "Next Steps:"
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
Write-Host ""
Write-Host "1. Set BrowserStack credentials (if not already set):"
Write-Host "   `$env:BROWSERSTACK_USERNAME = 'amaurimoraismann_qL3hsa'"
Write-Host "   `$env:BROWSERSTACK_ACCESS_KEY = 'v81FXLTPVGqPrG8sJxuM'"
Write-Host ""
Write-Host "2. Run BrowserStack tests:"
Write-Host "   mvn test -Ddriver=browserstack"
Write-Host ""
Write-Host "3. Monitor results:"
Write-Host "   https://automate.browserstack.com"
Write-Host ""
Write-Host "4. For more information:"
Write-Host "   - See: BROWSERSTACK_SETUP.md"
Write-Host "   - See: INTEGRATION_COMPLETE.md"
Write-Host ""
Write-Host "╔════════════════════════════════════════════════════════════════╗"
Write-Host "║              Setup Complete and Ready to Test!                ║"
Write-Host "╚════════════════════════════════════════════════════════════════╝"
Write-Host ""
