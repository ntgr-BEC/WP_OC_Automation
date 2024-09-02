package webportal.CFD.WAX630E.PRJCBUGEN_T29750;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    private static final String OnBoardingTest = null;
    String width = "160MHz";
    @Feature("CFD_630E") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29750") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("channel width 160 at location -->same location having 160 supporting device and not supporting device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T29750") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    
    @Step("Test Step 2: chnage chnnel width of location")
    public void step2() {

    new WirelessQuickViewPage().GoToWirelessSettings();
    MyCommonAPIs.sleepi(3);
    new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();
    new WirelessQuickViewPage(false).ChannelWidth5ghz(width);

    
    }
    
    @Step("Test Step 3: Check channel width of AP")
    public void step3() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click(); 
        assertTrue( new WirelessQuickViewPage(false).ChannelWidth5ghzCheck(width),"channel wideth is not applied for AP1");
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap2serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click(); 
        assertTrue( new WirelessQuickViewPage(false).ChannelWidth5ghzCheck(width),"channel wideth is not applied for AP2");
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap2serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click(); 
        assertTrue( new WirelessQuickViewPage(false).ChannelWidth5ghzCheck(width),"channel wideth is not applied for AP3");
        
    }
    
}