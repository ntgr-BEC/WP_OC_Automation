package testbase;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import webportal.param.WebportalParam;

public class TestCaseBaseApi {
    protected static WebportalParam webportalParam;

    private static int passCount = 0;
    private static int failCount = 0;
    private static int totalCount = 0;

    @BeforeSuite
    public void setupWebPortalParam() {
        if (webportalParam == null) {
            webportalParam = new WebportalParam();
            System.out.print("XML Initialized");
        }
    }

    @AfterMethod
    public void logTestCaseResult(ITestResult result) {
        totalCount++;
        String status;
        if (result.getStatus() == ITestResult.SUCCESS) {
            passCount++;
            status = "Pass";
        } else if (result.getStatus() == ITestResult.FAILURE) {
            failCount++;
            status = "Fail";
        } else {
            status = "Skipped";
        }

        String testCaseName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();

        System.out.println("\n#################### Stop TestCase(" + status + "): " + testCaseName);
        System.out.println("Testcase Count: " + passCount + "(P)/" + failCount + "(F)/" + totalCount + "(Total), CurFeature Fail: " + failCount);
        System.out.println("=================================================\n");
    }
}