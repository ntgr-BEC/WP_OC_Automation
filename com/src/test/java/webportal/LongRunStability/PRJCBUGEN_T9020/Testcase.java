package webportal.LongRunStability.PRJCBUGEN_T9020;

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
import webportal.weboperation.DevicesSwitchPoEPage;
import webportal.weboperation.DevicesSwitchStatisticsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T9020] Check Switch device info on Cloud";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    
    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9020") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9020") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: Go to network1, check Swtich Status is connected;")
    public void step2() {
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        // String swNo = ddp.getSWDevice();
        for (String swNo : ddp.getSWDevices()) {
            String sPoESw = null;
            
            try {
                stepInfo = "Check Swtich Status is connected in 1st network for " + swNo;
                expectResult = "Connected";
                actualResult = ddp.getDeviceStatus(swNo);
                sPoESw = ddp.getDeviceModel(swNo);
                if (actualResult.equals(expectResult)) {
                    dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                } else {
                    dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }

            try {
                stepInfo = "Check Swtich uptime in 1st network for " + swNo;
                expectResult = "Not NA";
                actualResult = ddp.getDeviceUptime(swNo, false);
                if (!actualResult.equals("NA")) {
                    dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                } else {
                    dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }
            
            try {
                stepInfo = "Check Swtich VLANs In Use info in 1st network for " + swNo;
                DevicesSwitchSummaryPage dssp = ddp.enterDevicesSwitchSummary(swNo);
                expectResult = "1, 4089";
                actualResult = dssp.vlanInUse.getText();
                if (actualResult.contains("1") && actualResult.contains("4089")) {
                    dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                } else {
                    dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }

            try {
                if (sPoESw.endsWith("P")) {
                    stepInfo = "Check Swtich PoE info in 1st network for " + swNo;
                    expectResult = "Some port are PoEd";
                    DevicesSwitchStatisticsPage dssp = ddp.enterDevicesSwitchStatistics(swNo);
                    // DevicesSwitchPoEPage dspp = new DevicesSwitchPoEPage();
                    MyCommonAPIs.waitReady();
                    actualResult = "None of port is PoEd";
                    if (dssp.getIPAddr().size() != 0) {
                        dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                    } else {
                        dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }
            ddp.gotoPage();
        }
    }
    
    @Step("Test Step 3: Go to network2, check Swtich Status is connected;")
    public void step3() {
        String sPoESw = null;
        handle.gotoLoction(WebportalParam.location2);
        ddp.gotoPage();
        // String apNo = ddp.getSWDevice();
        for (String apNo : ddp.getSWDevices()) {
            stepInfo = "Check Swtich Status is connected in 2nd network for " + apNo;
            expectResult = "Connected";
            actualResult = ddp.getDeviceStatus(apNo);
            sPoESw = ddp.getDeviceModel(apNo);
            if (actualResult.equals(expectResult)) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
            } else {
                dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            }
            
            try {
                stepInfo = "Check Swtich uptime in 2nd network for " + apNo;
                expectResult = "Not NA";
                actualResult = ddp.getDeviceUptime(apNo, false);
                if (!actualResult.equals("NA")) {
                    dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                } else {
                    dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }
            
            stepInfo = "Check Swtich VLANs In Use info in 2nd network for " + apNo;
            try {
                DevicesSwitchSummaryPage dssp = ddp.enterDevicesSwitchSummary(apNo);
                expectResult = "1, 4089";
                actualResult = dssp.vlanInUse.getText();
                if (actualResult.contains("1") && actualResult.contains("4089")) {
                    dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                } else {
                    dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }
            
            try {
                // alpha GC108PP don't support PoE.
                if (sPoESw.endsWith("P") && !sPoESw.endsWith("GC108PP")) {
                    stepInfo = "Check Swtich PoE info in 2st network for " + apNo;
                    expectResult = "Some port are PoEd";
                    DevicesSwitchPoEPage dspp = new DevicesSwitchPoEPage();
                    actualResult = "None of port is PoEd";
                    if (dspp.getIPAddr().size() != 0) {
                        dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                    } else {
                        dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                MyCommonAPIs.takess();
            }
            ddp.gotoPage();
        }
    }
}
