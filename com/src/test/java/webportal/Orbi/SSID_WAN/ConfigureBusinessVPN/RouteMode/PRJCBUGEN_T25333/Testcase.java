package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.RouteMode.PRJCBUGEN_T25333;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

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
import webportal.publicstep.UserManage;

/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("SSID_WAN.ConfigureBusinessVPN.RouteMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25333") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether remote router password get changed to default when added in VPN group as \"Home Office mode router\"") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25333") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.DelVPNGroup("TestGroup");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        
    }

    @Step("Test Step 2: Go to Routers page / User create a VPN group with router mode set as employee home.")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","Test Description","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25333", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();;
        util.MyCommonAPIs.sleep(5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T25333", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        UserManage userManage = new UserManage();
        userManage.logout();       
    }
    
    @Step("Test Step 3: Devices configured as home mode router will not go for reboot and password will not get changed .")
    public void step3() {
        boolean checkpoint;
        
        new OrbiLoginPage("admin", "password", WebportalParam.ob2IPaddress);
        util.MyCommonAPIs.waitReady();
        SelenideElement change_pwd = $x("//input[@id='change_pwd']");
        checkpoint = change_pwd.isDisplayed();
        assertTrue(checkpoint, "checkpoint 2 : Password should reset to default.");
        
    }
    
}
