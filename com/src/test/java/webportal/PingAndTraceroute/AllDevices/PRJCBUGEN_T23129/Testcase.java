package webportal.PingAndTraceroute.AllDevices.PRJCBUGEN_T23129;

import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    String                                domain       = "www.bing.com";
    String                                tmpStr1;
    String                                tmpStr2;
    String                                tmpStr3;
    String                                CompleteResultAP;
    String                                CompleteResultOrbi;
    String                                CompleteResultswitch;
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);
    Map<String, String> pingTraceroute = new HashMap<String, String>();

    @Feature("PingAndTraceroute") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23129") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, if there is no history of ping or trace route test > view previous result option is not visible") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23129") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        if (troubleshoot.dnslookupclose.isDisplayed()) {
            troubleshoot.dnslookupclose.click();
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one ap")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Click on x on AP under device tab;")
    public void step2() {
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
    }
    

    @Step("Test Step 3: Add AP;")
    public void step3() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    }
    
    @Step("Test Step 4: Check ViewPrevios Result;")
    public void step4() {
        
        troubleshoot.GotoDeviceDashboardTroubleShoot(WebportalParam.ap1serialNo);
        troubleshoot.DevicePing.click(); 
        assertTrue(!troubleshoot.ViewPreviosResult.isDisplayed(),"view Previous result is avilable");
        troubleshoot.DeviceTraceroute.click(); 
        assertTrue(!troubleshoot.ViewPreviosResult.isDisplayed(),"view Previous result is avilable");
    }
}
