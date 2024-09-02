package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7844;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.telnetoperation.OrbiTelnet;
import orbi.weboperation.OrbiDebugSettingsPage;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiFirmwarePage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    public String baseip = WebportalParam.ob2IPaddress;
    public String satelliteip = "";
    public String deleteimgcmd = "rm -rf /tmp/uhttp-upgrade.img";
    public String downloadimgcmd = "tftp -l /tmp/uhttp-upgrade.img -r ./%s -g %s";
    public String upgradecmd = "fw-upgrade --upgrade";

    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7844") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[Firmware] Test to verify the functionality of \"upgrade\" button on firmware screen.")
    @TmsLink("PRJCBUGEN-T7844") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")

    public void test() throws Exception {
        new BRUtils().updateSystemFirmware(WebportalParam.ob1Firmware, 1);

        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: Change to router mode / Get satellite ip")
    public void step2() {
        ddp.openOB2();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
        satelliteip = new DevicesOrbiSatellitesPage().getFirstSatelliteIP();
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 3: Downgrade satellite and base")
    public void step3() {
        OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
        
        page.OrbibaseEnableTelenet(baseip, WebportalParam.loginDevicePassword);
        new OrbiTelnet(baseip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
        new OrbiTelnet(baseip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(String.format(downloadimgcmd,WebportalParam.basebeforeimg,WebportalParam.TftpSvr));
        new OrbiTelnet(baseip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
        MyCommonAPIs.sleepi(500);
        
        page.OrbibaseEnableTelenet(satelliteip, WebportalParam.loginDevicePassword);
        new OrbiTelnet(satelliteip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
        new OrbiTelnet(satelliteip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(String.format(downloadimgcmd,WebportalParam.satellitebeforeimg,WebportalParam.TftpSvr));
        new OrbiTelnet(satelliteip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
        MyCommonAPIs.sleepi(500); // wait the fw version is updated on insight
    }

    @Step("Test Step 4: Or click \"Firmware\" Tab, click \"Update\" button.")
    public void step4() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openOB2();
        
        DevicesOrbiFirmwarePage page = new DevicesOrbiFirmwarePage();
        page.doUpgrade();
        assertTrue(new DevicesOrbiFirmwarePage().getVersion().equals(WebportalParam.serverimgver), "Check the fw version");
        MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 5: Get satellite ip")
    public void step5() {
        satelliteip = new DevicesOrbiSatellitesPage().getFirstSatelliteIP();
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 6: Restore - Upgrade satellite and base")
    public void step6() { 
        
        if (!WebportalParam.baseafterimg.contains(WebportalParam.serverimgver)) {
            
            OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
            
            page.OrbibaseEnableTelenet(baseip, WebportalParam.loginDevicePassword);
            new OrbiTelnet(baseip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
            new OrbiTelnet(baseip, WebportalParam.loginDevicePassword)
                    .orbiTelnetSendCmd(String.format(downloadimgcmd, WebportalParam.baseafterimg, WebportalParam.TftpSvr));
            new OrbiTelnet(baseip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
            MyCommonAPIs.sleepi(500);
            
            page.OrbibaseEnableTelenet(satelliteip, WebportalParam.loginDevicePassword);
            new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(deleteimgcmd);
            new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword)
                    .orbiTelnetSendCmd(String.format(downloadimgcmd, WebportalParam.satelliteafterimg, WebportalParam.TftpSvr));
            new OrbiTelnet(satelliteip, WebportalParam.loginDevicePassword).orbiTelnetSendCmd(upgradecmd);
            MyCommonAPIs.sleepi(300);
            
        }
    }
}
