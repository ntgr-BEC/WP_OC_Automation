package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RouteMode.PRJCBUGEN_T25327;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;
import orbi.weboperation.OrbiLoginPage;

/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    String []  predate;
    String []  curdate;
    
    @Feature("SSID_WAN.ConfigureBusinessVPN.RouteMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25327") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether the remote router goes for a reboot when we select Branch office mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25327") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            System.out.println("Teardown : delete VPN group");
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            System.out.println("teardown failed");
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 2 where the remote router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction(WebportalParam.location2);
        ddp.gotoPage();
        predate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, false).split("\\s+");
        //predate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, true);
    }

    @Step("Test Step 2: Go to Routers page / User create a VPN group with router mode set as Branch .")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","Test Description","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob2deveiceName, 2, false);
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25327", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
               
    }
    
    @Step("Test Step 3: Devices configured as home mode router will not go for reboot and password will not get changed .")
    public void step3() {
        boolean checkpoint = false;
        ddp.gotoPage();
        curdate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, false).split("\\s+");
        //curdate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, true);
        
        for(int i =0; i<8 ;i=i+2) {
            if(Integer.parseInt(curdate[i]) > Integer.parseInt(predate[i])) {
                checkpoint = true;
                break;
            }
        }
        assertTrue(checkpoint, "The device is not rebooted");
        
        new OrbiLoginPage("admin", "password", WebportalParam.ob2IPaddress);
        util.MyCommonAPIs.sleepi(10);
        SelenideElement change_pwd = $x("//input[@id='change_pwd']");
        checkpoint = change_pwd.isDisplayed();
        assertTrue(checkpoint, "checkpoint 2 : Password should reset to default.");
        
    }
    
    
    
}
