package webportal.NAS.PRJCBUGEN_T3582;

import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.WebportalLoginPage;

public class T3582 extends TestCaseBase implements webportal.NAS.PRJCBUGEN_T3582.Config {
    @Test(alwaysRun = true, groups = "p2")
    public void test() {
        step1();
        step2();

    }

    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        sleep(10 * 1000);
        AccountPageNas accountPage = new AccountPageNas();
        accountPage.enterLocation(WebportalParam.location1);

    }

    public void step2() {
        DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
        String string = devicesDashPage.confirmDeviceExist();
        assertEquals(errorinfo, string);

    }
    // public void step1(){
    // // 1
    // DashboardLocationPage dashboardLocationPage=new DashboardLocationPage();
    // dashboardLocationPage.enterDevices();
    //
    // //2
    // DevicesDashPage devicesDashPage=new DevicesDashPage();
    // devicesDashPage.addNewDevice(DEVICE_INFO);
    //
    // DevicesNasSummaryPage nasSummaryPage =devicesDashPage.enterDevicesSwitchSummary(serialNumber);
    // String a= nasSummaryPage.getseraNO();
    // assertEquals(a, NASParam.sw1serialNo);
    // }
}
