package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.GraphView.PRJCBUGEN_T25909;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.weboperation.OrbiLoginPage;
import testbase.TestCaseBase;
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
    @Feature("SSID_WAN.ConfigureBusinessVPN.GraphView") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25909") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check in Business VPN graph view page when device goes offline we see Red Steady circle around the Orbi device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25909") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            System.out.println("Teardown : Restore WAN IP / Delete VPN group");
            SelenideElement Internet = $x("//span[@id='basic_internet']");
            SelenideElement radioDHCPIP = $x("//input[@id='wan_assign_dhcp']");
            SelenideElement apply = $x("//input[@id='apply']");
            
            new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, WebportalParam.ob1IPaddress);
            util.MyCommonAPIs.waitReady();
            Internet.click();
            util.MyCommonAPIs.waitReady();
            Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
            radioDHCPIP.click();
            util.MyCommonAPIs.sleep(500);
            apply.click();
            util.MyCommonAPIs.sleepsyncorbi();
            
            new WebportalLoginPage(true).defaultLogin();
            handle.gotoLoction();
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            System.out.println("teardown failed");
        }
        
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        
    }

    @Step("Test Step 2: Go to Routers page / Create VPN group")
    public void step2() {
        
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true,"PRJCBUGEN-T25909",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady(); // wait business vpn group setup successfully
        util.MyCommonAPIs.sleep(3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        UserManage userManage = new UserManage();
        userManage.logout();
        
    }
    
    @Step("Test Step 3: Go to central router's local GUI / Modify WAN IP")
    public void step3() {
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
        
        new OrbiLoginPage("admin", WebportalParam.loginDevicePassword, WebportalParam.ob1IPaddress);
        util.MyCommonAPIs.waitReady();
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
    }
    
    @Step("Test Step 4: Check the device is offline in graph view")
    public void step4() {
        boolean checkpoint;
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.cirbtnGraphView.click();
        util.MyCommonAPIs.waitReady();
        
        System.out.println(page.graphCentralCir.getAttribute("stroke"));
        
        checkpoint = page.graphCentralCir.getAttribute("stroke").toUpperCase().equals("#FC3858");
        if(!checkpoint){
            for(int i =1; i<5; i++) {
                util.MyCommonAPIs.sleepi(60);
                page.gotoPage();
                checkpoint = page.graphCentralCir.getAttribute("stroke").toUpperCase().equals("#FC3858");
                if(checkpoint)
                    break;
            }
        }
        assertTrue(checkpoint,"checkpoint 1 : central router is offline");
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
}
