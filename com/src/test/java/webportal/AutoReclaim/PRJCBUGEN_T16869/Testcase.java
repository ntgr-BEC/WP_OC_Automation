package webportal.AutoReclaim.PRJCBUGEN_T16869;

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
    @Story("PRJCBUGEN_T16869") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the auto reclaim functionality for U200/U300/U400 Switches.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16869") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        devInfo.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo.put("Device Name", WebportalParam.sw1deveiceName);
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
        assertTrue(WebportalParam.sw1Model.startsWith("GC"), "Device must be U200/U300/U400");
        assertTrue(ddp.getDeviceStatus(WebportalParam.sw1serialNo).contains("onn"), "Device must be in connected status");
        
        ddp.enterDevice(WebportalParam.sw1serialNo);
        new PublicButton().reloadDevice();
        
        boolean bDeviceRemoved = false;
        ddp.gotoPage();
        for (int i = 0; i < 30; i++) {
            if (ddp.getDeviceName(WebportalParam.sw1serialNo).equals("")) {
                bDeviceRemoved = true;
                break;
            }
            handle.refresh();
            MyCommonAPIs.sleep(20, "to see if device is removed from account");
        }

        assertTrue(bDeviceRemoved, "Device should be removed");
        ddp.addNewDevice(devInfo);
        ddp.waitDevicesIPvalid(WebportalParam.sw1serialNo);
    }
}
