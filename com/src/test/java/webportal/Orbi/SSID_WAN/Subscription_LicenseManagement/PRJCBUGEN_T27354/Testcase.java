package webportal.Orbi.SSID_WAN.Subscription_LicenseManagement.PRJCBUGEN_T27354;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

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


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.Subscription_LicenseManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27354") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether insight displays VPN credits which are already added to an existing VPN group while creating a new VPN group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27354") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
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
    
    @Step("Test Step 2: Go to Routers page / User add a VPN group / Add a remote router after the group is created")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        
        // add remote router
        page.clickAddRemoteRouter();
        page.SelectLocation(WebportalParam.location2);
        page.SelectRouter(WebportalParam.ob2deveiceName);
        // count number of credits
        int ori_count = 0;
        for (ori_count=0 ; ; ori_count++) {
            try {
                page.selectVPNCredits.selectOption(ori_count);
            }
            catch(Throwable e) {
                break;
            }
        }
        System.out.println(ori_count);
        page.SelectVPNCredits(1);
        page.modalClickSave();
        
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl("DESKTOP-7ETR4H8", "08:be:ac:12:ce:c9", 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
        
        page.gotoPage();
        
        // add a remote router
        page.clickEditVPNGroup("TestGroup");
        page.clickNext();
        // count number of credits
        page.clickAddRemoteRouter();
        page.SelectLocation(WebportalParam.location2);
        page.SelectRouter(WebportalParam.ob3deveiceName);
        int count = 0;
        for (count=0 ; ; count++) {
            try {
                page.selectVPNCredits.selectOption(count);
            }
            catch(Throwable e) {
                break;
            }
        }
        System.out.println(count);
        page.SelectVPNCredits(1);
        page.modalClickSave();
        
        checkpoint = (ori_count == count + 1);
        System.out.println(ori_count);
        System.out.println(count);
        assertTrue(checkpoint, "checkpoint 1 : VPN credits decreased by 1 after used");
    }
    
}
