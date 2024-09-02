package webportal.SanityTestCase.PRJCBUGEN_Reboot;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {

    @Feature("SanityTestCase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_Reboot") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Repeatly do reboot to check device can be online in cloud") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-Reboot") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Repeatly do reboot to check device can be online in cloud")
    public void step1() {
        String devSerialNo = WebportalParam.sw1serialNo;
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        int countPass = 0;
        int countFail = 0;
        boolean isDefault = false;
        boolean fromDevice = true;
        while (true) {
            System.out.println(String.format("==Summary start with total/pass/fail: %s/%s", countPass + countFail, countPass, countFail));
            ddp.gotoPage();
            if (ddp.waitDevicesReConnected(devSerialNo)) {
                if (!isDefault) {
                    if (fromDevice) {
                        SwitchTelnet st = new SwitchTelnet(devSerialNo, WebportalParam.loginDevicePassword);
                        st.switchReboot();
                    } else {
                        ddp.rebootDevice(devSerialNo);
                    }
                } else {
                    ddp.enterDevicesSwitchSummary(devSerialNo, true).reloadDevice();
                }
                countPass++;
            } else {
                countFail++;
                System.out.println("Device is offline before reboot, try again");
            }
        }
    }
}
