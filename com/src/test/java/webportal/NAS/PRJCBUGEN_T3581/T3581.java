package webportal.NAS.PRJCBUGEN_T3581;

import static com.codeborne.selenide.Selenide.sleep;

import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasCloudPage;
import webportal.weboperation.WebportalLoginPage;

public class T3581 extends TestCaseBase implements webportal.NAS.PRJCBUGEN_T3581.Config {
    @Test(alwaysRun = true, groups = "p2")
    public void test() {
        step1();
        step2();
        step3();
        step4();

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
        devicesDashPage.deleteDeviceYes(WebportalParam.nas1SerialNo);
        sleep(5 * 1000);
    }

    public void step3() {
        Selenide.refresh();
        DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
        devicesDashPage.addNewDevice(DEVICE_INFO);
        sleep(10 * 1000);
    }

    public void step4() {
        Selenide.sleep(2000);
        Selenide.open(hostnameurl, "", username, password);
        DevicesNasCloudPage devicesNasCloudPage = new DevicesNasCloudPage();
        devicesNasCloudPage.turnOnReadyCloud(WebportalParam.loginName, WebportalParam.loginPassword);
    }

}
