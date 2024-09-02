package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VLAN.PRJCBUGEN_T25897;

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
    @Feature("SSID_WAN.ConfigureBusinessVPN.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25897") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Out of box Orbi (SRR60 / SXR80) device added to the B-VPN group while device was offline comes to VPN connected state when it comes online") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25897") // It's a testcase id/link from Jira Test Case.
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
            System.out.println("Failed to restore");
        }
    }
    
    @Step("Test Step 1: Login to remote router's local GUI / Modify WAN IP")
    public void step1() {
        OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
        boolean result = page.OrbibaseEnableTelenet(WebportalParam.ob2IPaddress, WebportalParam.loginDevicePassword);
        if (!result) {
            // force change password
            page.OrbibaseForceChangePassword(WebportalParam.ob2IPaddress, "password", WebportalParam.loginDevicePassword);
        }
        SelenideElement Internet = $x("//span[@id='basic_internet']");
        SelenideElement radioStaticIP = $x("//input[@id='wan_assign_static']");
        SelenideElement wanIPaddr1 = $x("//input[@id='wpethr1']");
        SelenideElement wanIPaddr2 = $x("//input[@id='wpethr2']");
        SelenideElement wanIPaddr3 = $x("//input[@id='wpethr3']");
        SelenideElement wanIPaddr4 = $x("//input[@id='wpethr4']");
        SelenideElement wanMask1 = $x("//input[@id='wmask1']");
        SelenideElement wanMask2 = $x("//input[@id='wmask2']");
        SelenideElement wanMask3 = $x("//input[@id='wmask3']");
        SelenideElement wanMask4 = $x("//input[@id='wmask4']");
        SelenideElement wanGW1 = $x("//input[@id='wgateway1']");
        SelenideElement wanGW2 = $x("//input[@id='wgateway2']");
        SelenideElement wanGW3 = $x("//input[@id='wgateway3']");
        SelenideElement wanGW4 = $x("//input[@id='wgateway4']");
        SelenideElement apply = $x("//input[@id='apply']");
        
        new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, WebportalParam.ob2IPaddress);
        util.MyCommonAPIs.sleepi(10);
        Internet.click();
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        radioStaticIP.click();
        util.MyCommonAPIs.sleep(500);
        wanIPaddr1.setValue("10");
        wanIPaddr2.setValue("0");
        wanIPaddr3.setValue("0");
        wanIPaddr4.setValue("100");
        wanMask1.setValue("255");
        wanMask2.setValue("255");
        wanMask3.setValue("255");
        wanMask4.setValue("0");
        wanGW1.setValue("10");
        wanGW2.setValue("0");
        wanGW3.setValue("0");
        wanGW4.setValue("1");
        util.MyCommonAPIs.sleep(1000);
        apply.click();
        util.MyCommonAPIs.sleepsyncorbi();
        // need to click logout here (to-do)
    }
    
    @Step("Test Step 2: Login to WP / Go to Location 1 where the central router is located")
    public void step2() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 3: Go to Routers page / User add a VPN group / Sync / Check VPN, central router, remoter status")
    public void step3() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25897", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl("DESKTOP-8ETR4H9", "08:be:ac:12:ce:c9", 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); //wait 5 mins to let business vpn finish setting up
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T25897", WebportalParam.ob1deveiceName, "Need Attention")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T25897", WebportalParam.ob1deveiceName, "Need Attention");
        assertTrue(checkpoint,"checkpoint 1 : Business VPN status is Need Attention");
        page.gotoGroupPage("TestGroup");
        page.gotoCentralRouterPage();
        checkpoint = page.RouterHealth(WebportalParam.ob1deveiceName, true, "Healthy");
        assertTrue(checkpoint,"checkpoint 2 : Central router status is Healthy");
        page.gotoRemoteRoutersPage();
        checkpoint = page.RouterHealth(WebportalParam.ob2deveiceName, false, "Broken");
        assertTrue(checkpoint,"checkpoint 3 : Remote router status is Broken");
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 4: Login to remote router's local GUI and restore WAN IP")
    public void step4() {
        SelenideElement Internet = $x("//span[@id='basic_internet']");
        SelenideElement radioDHCPIP = $x("//input[@id='wan_assign_dhcp']");
        SelenideElement apply = $x("//input[@id='apply']");
        
        new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, WebportalParam.ob2IPaddress);
        util.MyCommonAPIs.sleepi(10);
        Internet.click();
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        radioDHCPIP.click();
        util.MyCommonAPIs.sleep(500);
        apply.click();
        util.MyCommonAPIs.sleep(100* 1000);
    }
    
    @Step("Test Step 5: Check VPN status")
    public void step5() {
        boolean checkpoint;
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        util.MyCommonAPIs.sleepsyncorbi();
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T25897", WebportalParam.ob1deveiceName, "Healthy");
        assertTrue(checkpoint,"checkpoint 1 : Business VPN status is Healthy");
        page.gotoGroupPage("TestGroup");
        page.gotoCentralRouterPage();
        checkpoint = page.RouterHealth(WebportalParam.ob1deveiceName, true, "Healthy");
        assertTrue(checkpoint,"checkpoint 2 : Central router status is Healthy");
        page.gotoRemoteRoutersPage();
        checkpoint = page.RouterHealth(WebportalParam.ob2deveiceName, false, "Healthy");
        assertTrue(checkpoint,"checkpoint 3 : Remote router status is Healthy");
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
}
