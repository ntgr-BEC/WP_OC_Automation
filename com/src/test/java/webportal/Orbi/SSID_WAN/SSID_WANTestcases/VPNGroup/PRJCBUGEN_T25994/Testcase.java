package webportal.Orbi.SSID_WAN.SSID_WANTestcases.VPNGroup.PRJCBUGEN_T25994;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SSID_WANTestcases.VPNGroup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25994") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Read only manager have access to edit Business VPN group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25994") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        
        try {
            UserManage userManage = new UserManage();
            userManage.logout();
            new WebportalLoginPage(true).defaultLogin();
            handle.gotoLoction();
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        String currentUrl = util.MyCommonAPIs.getCurrentUrl();
        boolean checkpoint = currentUrl.contains(URLParam.hreforganization);
        assertTrue(checkpoint,"checkpoint 1 : the accout is pro account");
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User add a VPN group")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1100", true, "www.jira.com,www.confluence.com");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test", 2, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client2name, WebportalParam.client2wifimac, 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
    }
    
    @Step("Test Step 3: Louout and login as read only manager / Go to Loation 1 where the central router is located")
    public void step3() {
        UserManage userManage = new UserManage();
        userManage.logout();
        new WebportalLoginPage(true).loginByUserPassword(WebportalParam.readManagerName, WebportalParam.readManagerPassword);
        SelenideElement CloseImg = $x("//div[contains(@style,'display: block')]//img[contains(@src,'whiteCross.png')]");
        if(CloseImg.exists()) {
            CloseImg.click();
            util.MyCommonAPIs.waitReady();
        }
        handle.gotoLoction();
    }
    
    @Step("Test Step 4: Go to Routers page / User cannot edit the VPN group")
    public void step4() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickEditVPNGroup("TestGroup");
        page.clickNext();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400", false, "");
        page.clickNext(); // go to page 3
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test-1", 1, "12345678");
        page.clickNext();
        checkpoint = page.btnNext.exists();
        assertFalse(checkpoint, "checkpoint 1 : no save button");
    }
    
    
}
