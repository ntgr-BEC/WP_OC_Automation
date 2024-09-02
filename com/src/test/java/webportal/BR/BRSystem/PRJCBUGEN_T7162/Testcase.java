package webportal.BR.BRSystem.PRJCBUGEN_T7162;

import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("BR.BRSystem") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7162") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Check BR detailed info on About page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7162") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP add one BR, keep the device online;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: such as: Name, MAC Address, Firmware Version, Serial Number, Uptime, LAN IP Address, Attached Devcies")
    public void step2() {
        DevicesDashPage page = new DevicesDashPage(false);
        boolean checked = false;
        for (int i = 0; i < $$x(page.sDeviceModel).size(); i++) {
            if (MyCommonAPIs.getText($$x(page.sDeviceModel).get(i)).equals(WebportalParam.br1model)) {
                if (MyCommonAPIs.getText($$x(page.sDeviceName).get(i)).length() == 0) {
                    System.out.println("check for: sDeviceName");
                    break;
                }
                if (MyCommonAPIs.getText($$x(page.sDeviceStatus).get(i)).length() == 0) {
                    System.out.println("check for: sDeviceStatus");
                    break;
                }
                if (MyCommonAPIs.getText($$x(page.sDeviceSerialNo).get(i)).length() == 0) {
                    System.out.println("check for: sDeviceSerialNo");
                    break;
                }
                if (MyCommonAPIs.getText($$x(page.sDeviceModel).get(i)).length() == 0) {
                    System.out.println("check for: sDeviceModel");
                    break;
                }
                if (MyCommonAPIs.getText($$x(page.sDeviceFW).get(i)).length() == 0) {
                    System.out.println("check for: sDeviceFW");
                    break;
                }
                if (MyCommonAPIs.getText($$x(page.sDeviceIp).get(i)).length() == 0) {
                    System.out.println("check for: sDeviceIp");
                    break;
                }
                if (MyCommonAPIs.getText($$x(page.sDeviceUptime).get(i)).length() == 0) {
                    System.out.println("check for: sDeviceUptime");
                    break;
                }
                checked = true;
                break;
            } else {
                System.out.println("check for: device mode");
            }
        }
        assertTrue(checked, "some fields are lost");
    }
}
