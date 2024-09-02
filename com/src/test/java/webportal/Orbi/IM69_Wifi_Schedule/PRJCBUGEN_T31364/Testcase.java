package webportal.Orbi.IM69_Wifi_Schedule.PRJCBUGEN_T31364;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
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
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.DevicesOrbiWifiSchedulePage;
import webportal.publicstep.UserManage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("Orbi.IM69_Wifi_Schedule") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31364") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that the user can delete the created Wifi Schedule which is not allocated to any of the Orbi SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31364") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login using proper user name and password in latest Webportal")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
    }
    
    @Step("Test Step 2: Onboard Orbi SXR80 device")
    public void step2() {
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openOB2();
    }

    @Step(
        "Test Step 3: Goto Orbi --> Device Dashboard --> WiFi Schedule page" +
        "Test Step 4: Click on (+) Add Schedule button" +
        "Test Step 5: Try to Configure Schedule Name with more than 32 character"
    )
    public void step3() {
        
        String name33 = "111111111122222222223333333333444";
        String name32 = "11111111112222222222333333333344";
        
        DevicesOrbiWifiSchedulePage wifischedule = new DevicesOrbiWifiSchedulePage();
        wifischedule.clickAdd();
        wifischedule.setScheduleName(name33);
        wifischedule.selectDayCircle("1", true);
        
        wifischedule.expandDayTimePicker("1", true);
        wifischedule.setDayMinute("1", true, "00");
        wifischedule.setDayHour("1", true, "08");
        wifischedule.selectAmPm("1", "AM", true);
        wifischedule.clickDayTimePickerOK("1", true);
        
        wifischedule.expandDayTimePicker("1", false);
        wifischedule.setDayMinute("1", false, "00");
        wifischedule.setDayHour("1", false, "05");
        wifischedule.selectAmPm("1", "PM", false);
        wifischedule.clickDayTimePickerOK("1", false);
        
        wifischedule.clickSave();
        assertTrue(handle.getPageErrorMsg().length() > 1, "check error msg on name too long ");
        MyCommonAPIs.sleepi(10);
        
        wifischedule.setScheduleName(name32);
        wifischedule.clickSave();
        assertFalse(handle.getPageErrorMsg().length() > 1, "check no error message");
        MyCommonAPIs.sleepi(10);
        
        wifischedule.gotoPage();
        wifischedule.deleteWifiSchedule(name32);
        
    }
    
}