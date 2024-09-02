package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25511;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.param.OrbiGlobalConfig;
import orbi.telnetoperation.OrbiTelnet;
import orbi.weboperation.OrbiAdvancedRouterAPModePage;
import orbi.weboperation.OrbiDebugSettingsPage;
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
    public String satelliteip    = "";
    public String deleteimgcmd   = "rm -rf /tmp/uhttp-upgrade.img";
    public String downloadimgcmd = "tftp -l /tmp/uhttp-upgrade.img -r ./%s -g %s";
    public String upgradecmd     = "fw-upgrade --upgrade";

    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25511") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the manual upgrade functionality of the base router and the satellite") // It's a testcase title from Jira Test
                                                                                                         // Case.
    @TmsLink("PRJCBUGEN-T25511") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2", invocationCount = 30) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success / Get satellite ip / Logout")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        satelliteip = new DevicesOrbiSatellitesPage().getFirstSatelliteIP();
        UserManage userManage = new UserManage();
        userManage.logout();
    }

    @Step("Test Step 2: Downgrade satellite and base")
    public void step2() {
         OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
        
         page.OrbibaseEnableTelenet(baseip, WebportalParam.loginDevicePassword);
         new OrbiTelnet(baseip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
         MyCommonAPIs.sleepi(3);
         new OrbiTelnet(baseip ,
         WebportalParam.loginDevicePassword).orbiTelnetSendCmd(String.format(downloadimgcmd,WebportalParam.basebeforeimg,WebportalParam.TftpSvr));
         MyCommonAPIs.sleepi(3);
         new OrbiTelnet(baseip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
         MyCommonAPIs.sleepi(600);
        
         page.OrbibaseEnableTelenet(satelliteip, WebportalParam.loginDevicePassword);
         new OrbiTelnet(satelliteip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
         MyCommonAPIs.sleepi(3);
         new OrbiTelnet(satelliteip ,
         WebportalParam.loginDevicePassword).orbiTelnetSendCmd(String.format(downloadimgcmd,WebportalParam.satellitebeforeimg,WebportalParam.TftpSvr));
         MyCommonAPIs.sleepi(3);
         new OrbiTelnet(satelliteip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
         MyCommonAPIs.sleepi(600); // wait the fw version is updated on insight

//        OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
//
//        page.OrbibaseEnableTelenet(satelliteip, WebportalParam.loginDevicePassword);
//        new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
//        new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword)
//                .orbiTelnetSendCmd(String.format(downloadimgcmd, WebportalParam.satellitebeforeimg, WebportalParam.TftpSvr));
//
//        page.OrbibaseEnableTelenet(baseip, WebportalParam.loginDevicePassword);
//        new OrbiTelnet(baseip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
//        new OrbiTelnet(baseip, WebportalParam.loginDevicePassword)
//                .orbiTelnetSendCmd(String.format(downloadimgcmd, WebportalParam.basebeforeimg, WebportalParam.TftpSvr));
//
//        new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
//        MyCommonAPIs.sleepi(5);
//        new OrbiTelnet(baseip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
//        MyCommonAPIs.sleepi(720);
    }

    @Step("Test Step 3: Click fw upgrade / Check fw version")
    public void step3() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        FirmwarePage page = new FirmwarePage();
        page.gotoFirmwarePage();
        assertTrue(page.waitForUpdateAvailable(), "waitForUpdateAvailable");
        assertTrue(page.doUpdate(true), "waitForUpdateAvailable");

        // Check the firmware version is correct
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        assertTrue(new DevicesOrbiFirmwarePage().getVersion().equals(WebportalParam.serverimgver), "Check the fw version");
        // MyCommonAPIs.sleepi(180);
    }

    // @Step("Test Step 4: Get satellite ip")
    // public void step4() {
    // satelliteip = new DevicesOrbiSatellitesPage().getFirstSatelliteIP();
    // UserManage userManage = new UserManage();
    // userManage.logout();
    // }
    //
    // @Step("Test Step 5: Restore - Upgrade satellite and base")
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
