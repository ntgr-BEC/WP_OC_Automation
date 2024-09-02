package webportal.AutoReclaim.PRJCBUGEN_T16872;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    Map<String, String> devInfo = new HashMap<String, String>();

    @Feature("AutoReclaim") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16872") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify auto-reclaim functionality through insight reset for WAC 505/510/540/564") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16872") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        boolean checkApmodel = false;
        if (WebportalParam.ap1Model.contains("505") || WebportalParam.ap1Model.contains("510") || WebportalParam.ap1Model.contains("540")
                || WebportalParam.ap1Model.contains("564")) {
            checkApmodel = true;
        }
        assertTrue(checkApmodel, "Device must be in 505/510/540/564");
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ddp.gotoPage();
        ddp.addNewDevice(devInfo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto location")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: 5. Now click the reset button.")
    public void step2() {
        assertTrue(ddp.getDeviceStatus(WebportalParam.ap1serialNo).contains("onn"), "Device must be in connected status");
        ddp.enterDevice(WebportalParam.ap1serialNo);
        new PublicButton().reloadDevice();

        boolean bDeviceRemoved = false;
        ddp.gotoPage();
        for (int i = 0; i < 30; i++) {
            if (ddp.getDeviceName(WebportalParam.ap1serialNo).equals("")) {
                bDeviceRemoved = true;
                break;
            }
            handle.refresh();
            MyCommonAPIs.sleep(20, "to see if device is removed from account");
        }
        assertTrue(bDeviceRemoved, "Device should be removed");
        ddp.addNewDevice(devInfo);
        ddp.waitDevicesIPvalid(WebportalParam.ap1serialNo);
    }
}
