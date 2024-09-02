package webportal.NAS.PRJCBUGEN_T3576;

import static com.codeborne.selenide.Selenide.sleep;

import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasCloudPage;
import webportal.weboperation.WebportalLoginPage;
public class T3576 extends TestCaseBase implements Config {
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
        devicesDashPage.deleteDeviceYes(webportalParam.nas1SerialNo);
    }

    public void step3() {
        DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
        devicesDashPage.addNewDevice(DEVICE_INFO);
    }

    public void step4() {
        Selenide.sleep(2000);
        Selenide.open(hostnameurl, "", username, password);
        DevicesNasCloudPage devicesNasCloudPage =new DevicesNasCloudPage();
        devicesNasCloudPage.turnOnReadyCloud(webportalParam.loginName, webportalParam.loginPassword);
    }
}
