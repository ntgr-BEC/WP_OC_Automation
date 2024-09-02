package webportal.APDeviceScheduledUpdateTest.PRJCBUGEN_T16539;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
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
public class Testcase extends TestCaseBase {
    String tmpStr = "";

    @Feature("APDeviceScheduledUpdateTest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16539") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether AP,  Switch and BR500 devices goes for successful upgrade  using Update All")
    @TmsLink("PRJCBUGEN-T16539") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        SwitchCLIUtils.setSwitchIp(false);
    }

    @Step("Test Step 1: Downgrade firmware for an AP and BR, Orbi, Switch")
    public void step1() {
        // SwitchCLIUtils.updateSWFirmwareOld(false);
        // SwitchCLIUtils.setSwitchIp(true);
        // SwitchCLIUtils.updateSWFirmwareOld(false);
        //
        // new APUtils(WebportalParam.ap1IPaddress).updateFirmwareOld(null);
        // new APUtils(WebportalParam.ap2IPaddress).updateFirmwareOld(null);
        // new APUtils(WebportalParam.ap3IPaddress).updateFirmwareOld(null);
        //
        // new BRUtils().updateSystemFirmware(WebportalParam.ob1Firmware, 0);
        new BRUtils().updateSystemFirmware(0);
        new BRUtils().updateSystemFirmware(1);
        new BRUtils().updateSystemFirmware(2);
        new APUtils(WebportalParam.ap1IPaddress).updateFirmwareOld(null);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Now  Go-to Firmware page and click on the \"Update All\" button")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();

        ddp.waitDevicesReConnected(WebportalParam.ap1serialNo);

        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForUpdateAvailable(), "SW/BR/AP is not in old firmware");

        assertTrue(fmp.getUpdateCount() > 3, "Some of devices are not in old firmware -- at least 4 devices");

        assertTrue(fmp.doUpdate(true), "Devices were failed to update");
    }
}
