package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.DomainNamesThroughVPN.PRJCBUGEN_T27032;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.publicstep.UserManage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;
import orbi.weboperation.OrbiDebugSettingsPage;
import orbi.telnetoperation.OrbiTelnet;

/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.DomainNamesThroughVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27032") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether domain name added in the VPN group is pushed to the newly added Remote router") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27032") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            // TODO: handle exception
        }
        
    }
    
    @Step("Test Step 1: Enable Telnet for remote router")
    public void step1() {
        OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
        boolean result = page.OrbibaseEnableTelenet(WebportalParam.ob2IPaddress, WebportalParam.loginDevicePassword);
        if (!result) {
            // force change password
            page.OrbibaseForceChangePassword(WebportalParam.ob2IPaddress, "password", WebportalParam.loginDevicePassword);
            // enable telnet again
            page.OrbibaseEnableTelenet(WebportalParam.ob2IPaddress, WebportalParam.loginDevicePassword);
        }
    }
    
    
    @Step("Test Step 2: Login to WP / Go to Location 1 where the central router is located")
    public void step2() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    
    @Step("Test Step 3: Go to Routers page / Add a group with domain name for ob1 and ob3 / Add ob2 after the group is created")
    public void step3() {
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400", true, "arlo1.com");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName, 1, true);
        page.clickNext();
        page.setPage3WirelessSettings(true,"PRJCBUGEN-T27032",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
        
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T27032", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        
        page.clickEditVPNGroup("TestGroup");
        util.MyCommonAPIs.sleep(1000);
        page.clickNext();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob2deveiceName, 1, true);
        page.clickNext();
        page.clickNext();
        page.clickNext(); // save
        page.successicon.waitUntil(Condition.appear, 30000, 5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
        
    }
    
    
    @Step("Test Step 4: Check the config is applied to the central router and the remote router")
    public void step4() {
        boolean checkpoint;
        String cmd = "curl http://localhost:9988/api/v1/dnsmasq_upstream_server";
        String output = new OrbiTelnet(WebportalParam.ob2IPaddress , "password").orbiTelnetSendCmd(cmd);
        checkpoint = output.contains("arlo1.com");
        assertTrue(checkpoint,"checkpoint 1 : domain name is applied");
    }
    
}
