package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25495;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.param.OrbiGlobalConfig;
import orbi.weboperation.OrbiAdvancedRouterAPModePage;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiSetupWizardPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.WebportalLoginPage;
/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    public String currentDeviceMode = "Router";
    public String initialNotificationNum = "";
    
    //public SelenideElement notificationNumEle = $x("//*[@class='badge badge-default badge-pill']");
    //public SelenideElement notificationNumEle = $x("//*[@id='notificationDrop']");
    public SelenideElement notificationNumEle = $x("//img[contains(@src,'icon-notifications')]/../span");

    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25495") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the device notifications from the bell icon on the device dashboard") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25495") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success, delete devcie if exist.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        
        if(notificationNumEle.exists()) {
            initialNotificationNum = notificationNumEle.getText();
        }
        else {
            initialNotificationNum = "0";
        }
        
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {;
            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
            currentDeviceMode = new DevicesOrbiSummaryPage(false).getDeviceMode();
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleep(10 * 1000);
        }
        
    }
    
    @Step("Test Step 3: add device in this location")
    public void step3() {        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo.put("Device Name", WebportalParam.ob2Name);

        new DevicesDashPage().addNewDevice(devInfo);  
        MyCommonAPIs.sleepsync();
    }

    
    @Step("Test Step 5: Check the number of notifications")
    public void step5() {
        String currentNotificationNum = notificationNumEle.text();
        System.out.println(currentNotificationNum);
        assertTrue(Integer.parseInt(currentNotificationNum) > Integer.parseInt(initialNotificationNum),"");
    }

}
