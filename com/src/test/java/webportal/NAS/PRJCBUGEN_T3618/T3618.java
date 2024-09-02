package webportal.NAS.PRJCBUGEN_T3618;

import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasIpSettingsPage;
import webportal.weboperation.DevicesNasSummaryPage;
import webportal.weboperation.WebportalLoginPage;

public class T3618 extends TestCaseBase implements Config {
    @Test(alwaysRun = true, groups = "p2")
    public void test() {
        step1();
        step2();
        step3();
        step4();
        step5();
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
        sleep(8 * 1000);

    }

    public void step3() {
        DevicesNasIpSettingsPage devicesNasIpSettingsPage = new DevicesNasIpSettingsPage();
        String address1 = devicesNasIpSettingsPage.getipaddress();
        boolean exist = address1.contains(primaryip);
        assertTrue(exist);
        sleep(5000);
    }

    public void step4() {
        DevicesNasSummaryPage devicesNasSummaryPage = new DevicesNasSummaryPage();
        String text = devicesNasSummaryPage.rebootYes();
        sleep(10 * 1000);
        System.out.println(text);
        assertEquals(nasreboot, text);
        Selenide.refresh();
    }

    public void step5() {
        sleep(150 * 1000);
        Selenide.refresh();
        DevicesNasIpSettingsPage devicesNasIpSettingsPage = new DevicesNasIpSettingsPage();
        String address2 = devicesNasIpSettingsPage.getipaddress();
        boolean exist = address2.contains(primaryip);
        assertFalse(exist);

    }
}
