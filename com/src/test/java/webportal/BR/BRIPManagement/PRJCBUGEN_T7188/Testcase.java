package webportal.BR.BRIPManagement.PRJCBUGEN_T7188;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String sExpect = "192.168.3.223";

    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7188") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Set static IP address from DHCP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7188") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.refresh();
        try {
            handle.openOneBRDevice(true);
            brdwip.gotoPage();
            brdwip.setDHCP(true);
            handle.clickButton(0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Turn off \"Assign IP address automatically\", and set valid static IP\r\n"
            + "address/gateway/DNS from APP, all parameter is same as DHCP get info;")
    public void step2() {
        String sMode = "1"; // 1 - static, 2 - dhcp
        brdwip.gotoPage();
        brdwip.setDHCP(false);
        String sget = MyCommonAPIs.getValue(brdwip.txtIp);
        handle.clickButton(0);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
        handle.sleep(30, "wait conf to dut");
        for (int i = 0; i < 30; i++) {
            String scheck = new BRUtils(BRUtils.api_device_ip_settings, 0).getField("protocolMode");
            if (scheck.equals(sMode)) {
                break;
            }
            MyCommonAPIs.sleepi(2);
        }
        assertTrue(new BRUtils().getField("protocolMode").equals(sMode), "device wlan ip was not changed to static");

        // wait device online
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
    }

    @Step("Test Step 3: IM APP go to WAN IP page, set valid static IP address/gateway/DNS from APP,\r\n"
            + "keep some parameter different with before(such as: IP/DNS is different);")
    public void step3() {
        brdwip.gotoPage();
        assertFalse(brdwip.cbDhcp.is(Condition.checked), "device is not in static");
        brdwip.txtIp.setValue(sExpect);
        handle.clickButton(0);
        handle.waitRestReady(BRUtils.api_device_ip_settings, sExpect, false, 0);
        sExpect = new BRUtils().getField("ipAddress");
        assertTrue(sExpect.contains(sExpect), "device wlan ip was not changed to new static");
    }

    @Step("Test Step 4: Reload DUT and check IP info again")
    public void step4() {
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.br1serialNo, 1);
        new PublicButton().rebootDevice();
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
        sExpect = new BRUtils(BRUtils.api_device_ip_settings, 0).getField("ipAddress");
        assertTrue(sExpect.equals(sExpect), "device ip mode is lost after reload");
    }
}
