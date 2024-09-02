package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T26999;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.weboperation.OrbiDebugSettingsPage;
import orbi.weboperation.OrbiLoginPage;
import testbase.TestCaseBase;
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26999") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Business VPN tile is getting enabled when Router is added to Business VPN group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26999") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        try {
            new WebportalLoginPage(true).defaultLogin();
            handle.gotoLoction();
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }
    
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User add a VPN group")
    public void step2() {
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl("DESKTOP-7ETR4H8", "08:be:ac:12:ce:c9", 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); //wait 5 mins to let business vpn finish setting up
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 3: Login to local GUI and check Business VPN tile")
    public void step3() {
        boolean checkpoint;
        SelenideElement mvpn_enabled = $x("//div[@id='mvpn']//b[text()='Connected']");
        
        new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, WebportalParam.ob1IPaddress);
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        checkpoint = mvpn_enabled.exists();
        assertTrue(checkpoint,"checkpoint 1 : Business VPN tile is enabled on central router's local GUI");
        
        OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
        
        // force change password
        page.OrbibaseForceChangePassword(WebportalParam.ob2IPaddress, "password", WebportalParam.loginDevicePassword);
        new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, WebportalParam.ob2IPaddress);
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        checkpoint = mvpn_enabled.exists();
        assertTrue(checkpoint,"checkpoint 2 : Business VPN tile is enabled on remote router's local GUI");
    }
    
}
