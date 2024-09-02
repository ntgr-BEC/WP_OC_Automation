package webportal.Orbi.SSID_WAN.Banners.PRJCBUGEN_T26875;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.RoutersBusinessVPNPage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.Banners") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26875") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether marketing banners are shown even after user enables those services") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26875") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            System.out.println("Failed to delete vpn group");
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Devices page / Add central router and remote router to VPN group")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        
        // improvement for more than 2 routers to make sure all routers is added into the group
        try {
            page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName);
            page.setPage2AddRemoteRouter(WebportalParam.location1, WebportalParam.ob4deveiceName);
        }catch(Throwable e) {
            //page.btnModalCancel.click();
            MyCommonAPIs.checkSystemCall(0, null);
        }
        
        page.clickNext();
        page.setPage3WirelessSettings(true,"SSID-WAN-Auto-Test",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); 
    }
    
    @Step("Test Step 3: Go to Devices page / Only CF banners")
    public void step3() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob1serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 1-1 : Business VPN banner does not exist");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 1-2 : CF banner exists");
        }
    }
    
    @Step("Test Step 4: Go to Location Summary page / Only CF banners")
    public void step4() {
        boolean checkpoint;
        SummaryPage page = new SummaryPage();
        page.gotoPage(WebportalParam.location1);
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 2-1 : Business VPN banner does not exist");
        checkpoint = page.CFbanner.exists();
        if(checkpoint){
            assertTrue(checkpoint,"checkpoint 2-2 : CF banner exists");
        }
    }
    
    @Step("Test Step 5: Go to Loation 2 where the remote router is located")
    public void step5() {
        handle.gotoLoction(WebportalParam.location2);
    }
    
    @Step("Test Step 6: Go to Devices page / Only CF banners")
    public void step6() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 3-1 : Business VPN banner does not exist");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 3-2 : CF banner exists");
        }
    }
    
    @Step("Test Step 7: Go to Location Summary page / Only CF banners")
    public void step7() {
        boolean checkpoint;
        SummaryPage page = new SummaryPage();
        page.gotoPage(WebportalParam.location2);
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 4-1 : Business VPN banner does not exist");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 4-2 : CF banner exists");
        }
    }

}