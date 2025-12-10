package com.google.automation;

import org.testng.*;
import java.util.*;

/**
 * Custom TestNG Listener to display formatted test results
 * Shows test names with status in real-time and summary at the end
 */
public class TestResultsListener implements ITestListener, ISuiteListener {
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private Map<String, Integer> classTestCount = new HashMap<>();
    private Map<String, Integer> classPassCount = new HashMap<>();
    private Map<String, Integer> classSkipCount = new HashMap<>();
    private List<String> allTestClasses = new ArrayList<>();
    private int totalTests = 0;
    private int totalPassed = 0;
    private int totalSkipped = 0;
    private long suiteStartTime = 0;

    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    @Override
    public void onStart(ISuite suite) {
        suiteStartTime = System.currentTimeMillis();
        System.out.println("\n" + BOLD + CYAN + "═══════════════════════════════════════════════" + RESET);
        System.out.println(BOLD + CYAN + "          TEST EXECUTION STARTED" + RESET);
        System.out.println(BOLD + CYAN + "═══════════════════════════════════════════════" + RESET + "\n");
    }

    @Override
    public void onFinish(ISuite suite) {
        long suiteEndTime = System.currentTimeMillis();
        long totalTime = (suiteEndTime - suiteStartTime) / 1000;
        int totalFailed = totalTests - totalPassed - totalSkipped;

        System.out.println("\n" + BOLD + CYAN + "═══════════════════════════════════════════════" + RESET);
        System.out.println(BOLD + CYAN + "          FINAL TEST RESULTS" + RESET);
        System.out.println(BOLD + CYAN + "═══════════════════════════════════════════════" + RESET);

        // Print summary table
        System.out.println("\n" + BOLD + "Test Class Summary:" + RESET);
        System.out.println(String.format("%-50s | %s | %s | %s", "Test Class", "Passed", "Skipped", "Failed"));
        System.out.println(repeatString("─", 80));

        for (String testClass : allTestClasses) {
            int passed = classPassCount.getOrDefault(testClass, 0);
            int skipped = classSkipCount.getOrDefault(testClass, 0);
            int total = classTestCount.getOrDefault(testClass, 0);
            int failed = total - passed - skipped;

            String status = failed == 0 ? (GREEN + "✅" + RESET) : (RED + "❌" + RESET);
            String passStr = GREEN + passed + "/" + total + RESET;
            String skipStr = skipped > 0 ? (YELLOW + skipped + RESET) : "0";
            String failStr = failed > 0 ? (RED + failed + RESET) : "0";

            System.out.println(String.format("%-50s | %s | %s | %s  %s", 
                testClass, passStr, skipStr, failStr, status));
        }

        System.out.println("\n" + BOLD + "Overall Results:" + RESET);
        System.out.println(repeatString("─", 80));

        String passStatus = totalFailed == 0 ? GREEN + "✅" : RED + "❌";
        String passResult = totalFailed == 0 ? "PASSED" : "FAILED";
        int testCount = totalTests - totalSkipped;

        System.out.println(String.format("Total Tests:     %d", totalTests));
        System.out.println(String.format("Tests Run:       %d", testCount));
        System.out.println(String.format("Passed:          %s %d/%d %s", GREEN, totalPassed, testCount, RESET));
        System.out.println(String.format("Skipped:         %s %d %s", YELLOW, totalSkipped, RESET));
        System.out.println(String.format("Failed:          %s %d %s", totalFailed > 0 ? RED : GREEN, totalFailed, RESET));
        System.out.println(String.format("Success Rate:    %s %.1f%% %s", GREEN, 
            testCount > 0 ? (totalPassed * 100.0 / testCount) : 0, RESET));
        System.out.println(String.format("Total Time:      %d seconds", totalTime));
        System.out.println("\n" + BOLD + passStatus + " " + passResult + RESET);
        System.out.println(BOLD + CYAN + "═══════════════════════════════════════════════" + RESET + "\n");
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testClass = result.getTestClass().getRealClass().getSimpleName();
        String testMethod = result.getMethod().getMethodName();

        if (!allTestClasses.contains(testClass)) {
            allTestClasses.add(testClass);
        }

        classTestCount.put(testClass, classTestCount.getOrDefault(testClass, 0) + 1);
        totalTests++;

        System.out.print(CYAN + "▶ [" + testClass + "] " + RESET);
        System.out.println(testMethod + "...");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testClass = result.getTestClass().getRealClass().getSimpleName();
        String testMethod = result.getMethod().getMethodName();
        long duration = result.getEndMillis() - result.getStartMillis();

        classPassCount.put(testClass, classPassCount.getOrDefault(testClass, 0) + 1);
        totalPassed++;

        System.out.println(GREEN + "  ✅ PASSED - " + testMethod + " (" + duration + "ms)" + RESET);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testClass = result.getTestClass().getRealClass().getSimpleName();
        String testMethod = result.getMethod().getMethodName();

        System.out.println(RED + "  ❌ FAILED - " + testMethod + RESET);
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println(RED + "  Error: " + throwable.getMessage() + RESET);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testClass = result.getTestClass().getRealClass().getSimpleName();
        String testMethod = result.getMethod().getMethodName();

        classSkipCount.put(testClass, classSkipCount.getOrDefault(testClass, 0) + 1);
        totalSkipped++;

        System.out.println(YELLOW + "  ⊘ SKIPPED - " + testMethod + RESET);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
