package webportal.Orbi.SSID_WAN.Banners.PRJCBUGEN_T27171;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.Banners") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27171") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to see the Banners after refresh / moving to different pages even after closing the banners without logging out") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27171") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located / reonboard OB1")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob1serialNo);
        util.MyCommonAPIs.sleep(120 * 1000);
        new DevicesDashPage().addNewDevice(WebportalParam.ob1serialNo, WebportalParam.ob1deveiceName, WebportalParam.ob1deveiceMac);
        util.MyCommonAPIs.sleep(180 * 1000);
    }

    @Step("Test Step 2: Go to Devices page / Banners disappear after clicking x and refresh cannot restore it")
    public void step2() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob1serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        page.VPNClose.click();
        util.MyCommonAPIs.waitReady();
        if(page.CFClose.exists()) {
            page.CFClose.click();
            util.MyCommonAPIs.waitReady();
        }
        
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 1-1 : Business VPN banner does not exist");
        checkpoint = page.CFbanner.exists();
        assertFalse(checkpoint,"checkpoint 1-2 : CF banner does not exist");
        
        util.MyCommonAPIs.checkSystemCall(0, null);
        util.MyCommonAPIs.waitReady();
        
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 1-3 : Business VPN banner does not exist after refresh");
        checkpoint = page.CFbanner.exists();
        assertFalse(checkpoint,"checkpoint 1-4 : CF banner does not exist after refresh");
    }
    
    @Step("Test Step 3: Go to Location Summary page / Banners disappear after clicking x and refresh cannot restore it")
    public void step3() {
        boolean checkpoint;
        SummaryPage page = new SummaryPage();
        page.gotoPage(WebportalParam.location1);
        page.VPNClose.click();
        util.MyCommonAPIs.waitReady();
        if(page.CFClose.exists()) {
            page.CFClose.click();
            util.MyCommonAPIs.waitReady();
        }
        
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 2-1 : Business VPN banner does not exist");
        checkpoint = page.CFbanner.exists();
        assertFalse(checkpoint,"checkpoint 2-2 : CF banner does not exist");
        
        util.MyCommonAPIs.checkSystemCall(0, null);
        util.MyCommonAPIs.waitReady();
        
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 2-3 : Business VPN banner does not exist after refresh");
        checkpoint = page.CFbanner.exists();
        assertFalse(checkpoint,"checkpoint 2-4 : CF banner does not exist after refresh");
        
    }
    
    @Step("Test Step 4: Louout and login / Go to Loation 1 where the central router is located")
    public void step4() {
        UserManage userManage = new UserManage();
        userManage.logout();
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 5: Go to Devices page / Check the banners")
    public void step5() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob1serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 5-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 5-2 : CF banner exists");
        }
    }
    
    @Step("Test Step 6: Go to Location Summary page / Check the banners")
    public void step6() {
        boolean checkpoint;
        SummaryPage page = new SummaryPage();
        page.gotoPage(WebportalParam.location1);
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 6-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 6-2 : CF banner exists");
        }
    }
    
    
    @Step("Test Step 7: Go to Loation 2 where the remote router is located / reonboard OB2")
    public void step7() {
        handle.gotoLoction(WebportalParam.location2);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        util.MyCommonAPIs.sleep(120 * 1000);
        new DevicesDashPage().addNewDevice(WebportalParam.ob2serialNo, WebportalParam.ob2deveiceName, WebportalParam.ob2MAC_Address);
        util.MyCommonAPIs.sleep(180 * 1000);
    }
    
    @Step("Test Step 8: Go to Devices page / Banners disappear after clicking x and refresh cannot restore it")
    public void step8() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        page.VPNClose.click();
        util.MyCommonAPIs.waitReady();
        if(page.CFClose.exists()) {
            page.CFClose.click();
            util.MyCommonAPIs.waitReady();
        }
        
        
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 3-1 : Business VPN banner does not exist");
        checkpoint = page.CFbanner.exists();
        assertFalse(checkpoint,"checkpoint 3-2 : CF banner does not exist");
        
        util.MyCommonAPIs.checkSystemCall(0, null);
        util.MyCommonAPIs.waitReady();
        
        checkpoint = page.VPNbanner.exists();
        assertFalse(checkpoint,"checkpoint 3-3 : Business VPN banner does not exist after refresh");
        checkpoint = page.CFbanner.exists();
        assertFalse(checkpoint,"checkpoint 3-4 : CF banner does not exist after refresh");
        
    }
    
    @Step("Test Step 9: Go to Location Summary page / Banners disappear after clicking x and refresh cannot restore it")
    public void step9() {
        
        if (!WebportalParam.location2.equals(WebportalParam.location1)) {
            boolean checkpoint;
            SummaryPage page = new SummaryPage();
            page.gotoPage(WebportalParam.location2);
            page.VPNClose.click();
            util.MyCommonAPIs.waitReady();
            if(page.CFClose.exists()) {
                page.CFClose.click();
                util.MyCommonAPIs.waitReady();
            }
            
            
            checkpoint = page.VPNbanner.exists();
            assertFalse(checkpoint,"checkpoint 4-1 : Business VPN banner does not exist");
            checkpoint = page.CFbanner.exists();
            assertFalse(checkpoint,"checkpoint 4-2 : CF banner does not exist");
        
            util.MyCommonAPIs.checkSystemCall(0, null);
            util.MyCommonAPIs.waitReady();
        
            checkpoint = page.VPNbanner.exists();
            assertFalse(checkpoint,"checkpoint 4-3 : Business VPN banner does not exist after refresh");
            checkpoint = page.CFbanner.exists();
            assertFalse(checkpoint,"checkpoint 4-4 : CF banner does not exist after refresh");
        }
    }
    
    @Step("Test Step 10: Louout and login / Go to Loation 2 where the remote router is located")
    public void step10() {
        UserManage userManage = new UserManage();
        userManage.logout();
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction(WebportalParam.location2);
    }
    
    @Step("Test Step 11: Go to Devices page / Check the banners")
    public void step11() {
        boolean checkpoint;
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
        DevicesOrbiSummaryPage page = new DevicesOrbiSummaryPage();
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 7-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 7-2 : CF banner exists");
        }
        
    }
    
    @Step("Test Step 12: Go to Location Summary page / Check the banners")
    public void step12() {
        boolean checkpoint;
        SummaryPage page = new SummaryPage();
        page.gotoPage(WebportalParam.location2);
        checkpoint = page.VPNbanner.exists();
        assertTrue(checkpoint,"checkpoint 8-1 : Business VPN banner exists");
        checkpoint = page.CFbanner.exists();
        if(checkpoint) {
            assertTrue(checkpoint,"checkpoint 8-2 : CF banner exists");
        }
        
    }
    
}