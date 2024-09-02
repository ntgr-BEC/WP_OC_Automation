package webportal.NAS.PRJCBUGEN_T3605;

import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasSummaryPage;
import webportal.weboperation.WebportalLoginPage;

public class T3605 extends TestCaseBase implements Config {
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
        DevicesNasSummaryPage devicesNasSummaryPage = new DevicesNasSummaryPage();
        String text = devicesNasSummaryPage.rebootYes();
        sleep(10 * 1000);
        System.out.println(text);
        assertEquals(nasreboot, text);
        Selenide.refresh();
    }
}
