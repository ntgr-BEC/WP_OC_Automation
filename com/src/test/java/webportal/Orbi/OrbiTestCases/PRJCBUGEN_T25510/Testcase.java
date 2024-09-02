package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25510;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.param.OrbiGlobalConfig;
import orbi.telnetoperation.OrbiTelnet;
import orbi.weboperation.OrbiAdvancedRouterAPModePage;
import orbi.weboperation.OrbiDebugSettingsPage;
import orbi.weboperation.OrbiLoginPage;
import orbi.weboperation.OrbiAdministrationFirmwareUpdatePage;
import orbi.weboperation.OrbiSatellitenFirmwareUpdatePage;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiFirmwarePage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
import webportal.weboperation.DevicesOrbiSetupWizardPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.FirmwarePage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {

    public String baseip         = WebportalParam.ob2IPaddress;
    public String satelliteip    = "192.168.1.3";
    public String deleteimgcmd   = "rm -rf /tmp/uhttp-upgrade.img";
    public String downloadimgcmd = "tftp -l /tmp/uhttp-upgrade.img -r ./%s -g %s";
    public String upgradecmd     = "fw-upgrade --upgrade";

    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25510") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the auto upgrade functionality of the base router and the satellite") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25510") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2", invocationCount = 5) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // try to add device back if it's not added
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success / Get satellite ip / Delete device / Logout")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        try {
            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
            satelliteip = new DevicesOrbiSatellitesPage().getFirstSatelliteIP();
        }

        catch (Throwable e) {

        }
        try {
            ddp.gotoPage();
            // delete device
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleepi(10);
        } catch (Throwable e) {

        }

        UserManage userManage = new UserManage();
        userManage.logout();

    }

    @Step("Test Step 2: Downgrade satellite and base")
    public void step2() {
        new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, satelliteip);
        OrbiSatellitenFirmwareUpdatePage orbisatefirmwareupdate = new OrbiSatellitenFirmwareUpdatePage();
        orbisatefirmwareupdate.OpenFirmwareUpdatePage();
        String filename = WebportalParam.satellitebeforeimg;
        String filepath = "C:\\tftpd32\\" + filename;
        orbisatefirmwareupdate.FirmwareUpdate(filepath);

        new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, baseip);
        OrbiAdministrationFirmwareUpdatePage orbifirmwareupdate = new OrbiAdministrationFirmwareUpdatePage();
        orbifirmwareupdate.OpenFirmwareUpdatePage();
        filename = WebportalParam.basebeforeimg;
        filepath = "C:\\tftpd32\\" + filename;
        orbifirmwareupdate.FirmwareUpdate(filepath);

        MyCommonAPIs.sleepi(360);

    }

    @Step("Test Step 3: On-board device / Check fw version")
    public void step3() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        // Onboard device
        new DevicesDashPage().addNewDevice(WebportalParam.ob2serialNo, WebportalParam.ob2Name, WebportalParam.ob2MAC_Address.replace("-", ":"));
        MyCommonAPIs.sleepi(300); // wait onboard fw update
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online.");
        assertTrue(new DevicesDashPage().waitDevicesIPvalid(WebportalParam.ob2serialNo), "Device IP not valid.");

        MyCommonAPIs.sleepi(300); // wait stable

        // Check the firmware version is correct
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        System.out.println("!!!!!!!!!!!check here!!!!!!!!!!!!");
        System.out.println(new DevicesOrbiFirmwarePage().getVersion());
        System.out.println(WebportalParam.serverimgver);
        assertTrue(new DevicesOrbiFirmwarePage().getVersion().equals(WebportalParam.serverimgver), "Check the fw version");

    }

    // @Step("Test Step 4: Get satellite ip")
    // public void step4() {
    // satelliteip = new DevicesOrbiSatellitesPage().getFirstSatelliteIP();
    // UserManage userManage = new UserManage();
    // userManage.logout();
    // }
    //
    // @Step("Test Step 5: Restore - Upgrade satellite and base to the firmware for test")
    // public void step5() {
    //
    // if (!WebportalParam.baseafterimg.contains(WebportalParam.serverimgver)) {
    //
    // OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
    //
    // page.OrbibaseEnableTelenet(baseip, WebportalParam.loginDevicePassword);
    // new OrbiTelnet(baseip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
    // new OrbiTelnet(baseip, WebportalParam.loginDevicePassword)
    // .orbiTelnetSendCmd(String.format(downloadimgcmd, WebportalParam.baseafterimg, WebportalParam.TftpSvr));
    // new OrbiTelnet(baseip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
    // MyCommonAPIs.sleepi(500);
    //
    // page.OrbibaseEnableTelenet(satelliteip, WebportalParam.loginDevicePassword);
    // new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
    // new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword)
    // .orbiTelnetSendCmd(String.format(downloadimgcmd, WebportalParam.satelliteafterimg, WebportalParam.TftpSvr));
    // new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
    // MyCommonAPIs.sleepi(300);
    //
    // }
    // }

}
