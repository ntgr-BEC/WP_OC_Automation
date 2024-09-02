package webportal.SanityTestCase.PRJCBUGEN_BundleWBC502;

import java.util.ArrayList;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.XMLManagerForTest;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    
    @Feature("SanityTestCase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_UpdateFw502") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Repeatly do different firmware update to check device can be online in cloud") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-UpdateFw502") // It's a testcase id/link from Jira Test Case.
    
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
        String firmwareNodeName = "MultipleFirmware";
        
        /*
         * Read firmware list from ap1 as master
         */
        ArrayList<String> lsFirmware = xmlManager.getValuesFromWebPortAndDut("AP1", firmwareNodeName);
        if (lsFirmware.size() == 0)
            throw new RuntimeException("Check firmware lists from: " + firmwareNodeName);
        
        int passDowngradeCount = 0;
        int passUpgradeCount = 0;
        int failDowngradeCount = 0;
        int failUpgradeCount = 0;
        while (true) {
            System.out.println(String.format(
                    "==Summary: No of Pass Downgraded(%d)/No of Pass Upgraded: (%d)/No of Fail Downgraded(%d)/No of Fail Upgraded: (%d)",
                    passDowngradeCount, passUpgradeCount, failDowngradeCount, failUpgradeCount));
            for (String fw : lsFirmware) {
                boolean downPassed = false;
                ddp.gotoPage();
                if (ddp.isDeviceConnected(WebportalParam.ap1serialNo) && ddp.isDeviceConnected(WebportalParam.ap2serialNo)) {
                    // downgrade satellite first
                    new APUtils(WebportalParam.ap2IPaddress).updateFirmwareOld(fw);
                    if (ddp.waitDevicesReConnected(WebportalParam.ap2serialNo)) {
                        System.out.println("satellite is downgraded via local - ok");
                        // then for master
                        new APUtils(WebportalParam.ap1IPaddress).updateFirmwareOld(fw);
                        if (ddp.waitDevicesReConnected(WebportalParam.ap1serialNo)) {
                            passDowngradeCount++;
                            System.out.println("master is downgraded via local - ok");
                            downPassed = true;
                        } else {
                            System.out.println("master is failed to online");
                        }
                    } else {
                        System.out.println("satellite is failed to online");
                    }
                } else {
                    MyCommonAPIs.sleepsync();
                }

                if (!downPassed) {
                    System.out.println("device is failed to downgrade or offline, try to see if it can do it again");
                    failDowngradeCount++;
                    continue;
                }
                
                ddp.gotoPage();
                if (ddp.isDeviceConnected(WebportalParam.ap1serialNo) && ddp.isDeviceConnected(WebportalParam.ap2serialNo)) {
                    fmp.gotoFirmwarePage();
                    if (fmp.waitForUpdateAvailable() && fmp.doUpdate(true)) {
                        passUpgradeCount++;
                        System.out.println("device is upgraded via cloud - ok");
                    } else {
                        failUpgradeCount++;
                        System.out.println("device is upgraded via cloud - ko");
                    }
                } else {
                    failUpgradeCount++;
                    System.out.println("device is offline before upgrade, try to see if it can online again");
                    MyCommonAPIs.sleepsync();
                    continue;
                }
            }
        }
    }
}
