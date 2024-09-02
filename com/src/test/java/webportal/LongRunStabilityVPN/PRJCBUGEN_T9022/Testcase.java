package webportal.LongRunStabilityVPN.PRJCBUGEN_T9022;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SQLiteStability;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T9022] Check BR500 Router device info on Cloud";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    
    @Feature("LongRunStabilityVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9022") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9022") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        runTest(this);
    }
    
    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }
    
    @Step("Test Step 2: Go to network1, check Router Status is connected;")
    public void step2() {
        stepInfo = "Check Router Status is connected in 1st network";
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        String apNo = WebportalParam.br1serialNo;
        expectResult = "Connected";
        actualResult = "Disconnected";
        try {
            actualResult = ddp.getDeviceStatus(apNo);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (actualResult.equals(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            MyCommonAPIs.sleepsync();
        }
        
        stepInfo = "Check Router uptime in 1st network";
        expectResult = "Not NA";
        actualResult = "NA";
        try {
            actualResult = ddp.getDeviceUptime(apNo, false);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (!actualResult.equals("NA")) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            MyCommonAPIs.sleepsync();
        }
    }
    
    @Step("Test Step 3: Go to network2, check Router Status is connected;")
    public void step3() {
        stepInfo = "Check Router Status is connected in 2nd network";
        handle.gotoLoction(WebportalParam.location2);
        ddp.gotoPage();
        String apNo = WebportalParam.br2serialNo;
        expectResult = "Connected";
        actualResult = "Disconnected";
        try {
            actualResult = ddp.getDeviceStatus(apNo);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (actualResult.equals(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            MyCommonAPIs.sleepsync();
        }
        
        stepInfo = "Check Router uptime in 2nd network";
        expectResult = "Not NA";
        actualResult = "NA";
        try {
            actualResult = ddp.getDeviceUptime(apNo, false);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (!actualResult.equals("NA")) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            MyCommonAPIs.sleepsync();
        }
    }
    
    @Step("Test Step 4: Go to network3, check Router Status is connected;")
    public void step4() {
        if (WebportalParam.locationNumber == 2)
            return;
        stepInfo = "Check Router Status is connected in 3nd network";
        handle.gotoLoction(WebportalParam.location3);
        ddp.gotoPage();
        String apNo = WebportalParam.br3serialNo;
        expectResult = "Connected";
        actualResult = "Disconnected";
        try {
            actualResult = ddp.getDeviceStatus(apNo);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (actualResult.equals(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            MyCommonAPIs.sleepsync();
        }
        
        stepInfo = "Check Router uptime in 3nd network";
        expectResult = "Not NA";
        actualResult = "NA";
        try {
            actualResult = ddp.getDeviceUptime(apNo, false);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (!actualResult.equals("NA")) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }
    }
}
