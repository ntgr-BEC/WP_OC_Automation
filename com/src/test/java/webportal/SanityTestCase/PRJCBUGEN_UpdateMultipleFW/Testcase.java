package webportal.SanityTestCase.PRJCBUGEN_UpdateMultipleFW;

import java.util.ArrayList;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.BRUtils;
import util.XMLManagerForTest;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {

    @Feature("SanityTestCase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_UpdateFw") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Repeatly do different firmware update to check device can be online in cloud") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-UpdateFw") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Repeatly do update firmware to check device can be online in cloud")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();

        XMLManagerForTest xmlManager = new XMLManagerForTest();
        /**
         * 0 - sw, 1 - ap, 2 - br, 3 - orbi
         */
        int devType = 1;
        String firmwareNodeName = "MultipleFirmware";

        /*
         * Read firmware list and device serial
         */
        String devSerialNo = WebportalParam.sw1serialNo;
        ArrayList<String> lsFirmware = xmlManager.getValuesFromWebPortAndDut("SW1", firmwareNodeName);
        if (devType == 1) {
            devSerialNo = WebportalParam.ap1serialNo;
            lsFirmware = xmlManager.getValuesFromWebPortAndDut("AP1", firmwareNodeName);
        } else if (devType == 2) {
            devSerialNo = WebportalParam.br1serialNo;
            lsFirmware = xmlManager.getValuesFromWebPortAndDut("BR1", firmwareNodeName);
        } else if (devType == 3) {
            devSerialNo = WebportalParam.ob1serialNo;
            lsFirmware = xmlManager.getValuesFromWebPortAndDut("ORBI1", firmwareNodeName);
        }

        if (lsFirmware.size() == 0)
            throw new RuntimeException("Check firmware lists from: " + firmwareNodeName);

        int passDowngradeCount = 0;
        int passUpgradeCount = 0;
        int failDowngradeCount = 0;
        int failUpgradeCount = 0;
        int runTime = 0;
        while (true) {
        //while (runTime < 10 ) {
            System.out.println(String.format(
                    "==Summary: No of Pass Downgraded(%d)/No of Pass Upgraded: (%d)/No of Fail Downgraded(%d)/No of Fail Upgraded: (%d)",
                    passDowngradeCount, passUpgradeCount, failDowngradeCount, failUpgradeCount));
            for (String fw : lsFirmware) {
                ddp.gotoPage();
                if (ddp.waitDevicesReConnected(devSerialNo)) {
                    boolean bUpdated;
                    if (devType == 1) {
                        bUpdated = new APUtils(WebportalParam.ap1IPaddress).updateFirmwareOld(fw);
                    } else if (devType == 2) {
                        bUpdated = new BRUtils().updateSystemFirmware(fw, 0);
                    } else if (devType == 3) {
                        bUpdated = new BRUtils().updateSystemFirmware(fw, 1);
                    } else {
                        bUpdated = new BRUtils().updateSystemFirmware(fw, 2);
                    }

                    if (bUpdated) {
                        passDowngradeCount++;
                        System.out.println("device is downgraded via local - ok");
                    }
                } else {
                    System.out.println("device is offline before downgrade - ko");
                    ddp.rebootDevice(devSerialNo);
                    failUpgradeCount++;
                    continue;
                }

                ddp.gotoPage();
                if (ddp.waitDevicesReConnected(devSerialNo)) {
                    fmp.gotoFirmwarePage();
                    if (fmp.waitForUpdateAvailable() && fmp.doUpdate(false)) {
                        passUpgradeCount++;
                        System.out.println("device is upgraded via cloud - ok");
                    } else {
                        System.out.println("device is upgraded via cloud - ko");
                    }
                } else {
                    failDowngradeCount++;
                    System.out.println("device is offline before upgrade - ko");
                    continue;
                }
                runTime ++;
            }
        }
    }
}
