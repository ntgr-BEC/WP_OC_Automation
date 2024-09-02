package webportal.NAS.PRJCBUGEN_T3646;

import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasSummaryPage;
import webportal.weboperation.WebportalLoginPage;

public class T3646 extends TestCaseBase implements Config {
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
        sleep(3 * 1000);

    }

    public void step3() {
        DevicesNasSummaryPage devicesNasSummaryPage = new DevicesNasSummaryPage();
        String aa = devicesNasSummaryPage.getnameinfo();
        assertEquals(name, aa);
        String bb = devicesNasSummaryPage.getmodelinfo();
        assertEquals(model, bb);
        String cc = devicesNasSummaryPage.getfwinfo();
        assertEquals(firmware, cc);
        String dd = devicesNasSummaryPage.getnasantivirusinfo();
        assertEquals(antivirus, dd);
        String ff = devicesNasSummaryPage.getserialnumberinfo();
        assertEquals(SerialNo, ff);
        String ee = devicesNasSummaryPage.gettemp();
        System.out.println(ee);
        boolean exist = ee.contains(tempaturedisk);
        assertTrue(exist);

    }
}
