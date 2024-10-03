package webportal.SwitchManaged.System.PRJCBUGEN_T4687;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.BRUtils;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase implements Config {
    
    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4687") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("091- Repeatedly trigger Switch devices firmware online upgrade") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4687") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2-perf")
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: run downgrade/upgrade for sw many times")
    public void step2() {
        /**
         * 0 - sw, 1 - ap, 2 - br, 3 - orbi
         */
        int devType = 0;
        int firmwareupdatecount = 0;
        int failonlinecount = 0;
        int failupgradecount = 0;
        String devSerialNo = WebportalParam.sw1serialNo;
        if (devType == 1) {
            devSerialNo = WebportalParam.ap1serialNo;
        } else if (devType == 2) {
            devSerialNo = WebportalParam.br1serialNo;
        } else if (devType == 3) {
            devSerialNo = WebportalParam.ob1serialNo;
        }
        while (true) {
            System.out.println(String.format("==Summary: firmware updates(%s)/failed online(%s)/failed upgrade(%s)", firmwareupdatecount,
                    failonlinecount, failupgradecount));
            ddpmg.gotoPage();
            if (ddpmg.waitDevicesReConnected(devSerialNo)) {
                boolean bUpdated;
                if (devType == 1) {
                    bUpdated = new APUtils(WebportalParam.ap1IPaddress).updateFirmwareOld(null);
                } else if (devType == 2) {
                    bUpdated = new BRUtils().updateSystemFirmware(0);
                } else if (devType == 3) {
                    bUpdated = new BRUtils().updateSystemFirmware(1);
                } else {
                    bUpdated = SwitchCLIUtils.updateSWFirmwareOld(false);
                }

                if (bUpdated) {
                    firmwareupdatecount++;
                    System.out.println("device is downgraded via local - ok");
                }
            } else {
                System.out.println("device is offline before downgrade - ko");
                // ddpmg.rebootDevice(devSerialNo);
                failonlinecount++;
                continue;
            }
            
            ddpmg.gotoPage();
            if (ddpmg.waitDevicesReConnected(devSerialNo)) {
                fmp.gotoFirmwarePage();
                if (fmp.waitForUpdateAvailable() && fmp.doUpdate(false)) {
                    firmwareupdatecount++;
                    System.out.println("device is upgraded via cloud - ok");
                } else {
                    failupgradecount++;
                    System.out.println("device is upgraded via cloud - ko");
                }
            } else {
                System.out.println("device is offline before upgrade - ko");
                failonlinecount++;
                continue;
            }
        }
    }
}
