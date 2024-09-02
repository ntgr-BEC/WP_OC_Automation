package webportal.NAS.PRJCBUGEN_T3635;

import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasNetworkSettingsPage;
import webportal.weboperation.WebportalLoginPage;

public class T3635 extends TestCaseBase implements Config {
    @Test(alwaysRun = true, groups = "p2")
    public void test() {
        step1();
        step2();
        step3();

    }

    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        sleep(5 * 1000);
        AccountPageNas accountPage = new AccountPageNas();
        accountPage.enterLocation(WebportalParam.location1);
    }

    public void step2() {
        DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
        devicesDashPage.enterDevicesNasSummary(SerialNo);
        sleep(5 * 1000);

    }

    public void step3() {
        DevicesNasNetworkSettingsPage devicesNasNetworkSettingsPage = new DevicesNasNetworkSettingsPage();
        sleep(5 * 1000);
        String address = devicesNasNetworkSettingsPage.getMacValue();
        boolean exist = address.contains(macInfo);
        assertTrue(exist);

    }
}
