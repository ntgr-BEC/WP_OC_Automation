package webportal.Orbi.IM69_Wifi_Schedule.PRJCBUGEN_T31363;

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
    @Story("PRJCBUGEN_T31363") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that the user can delete the entire created schedule from Wi-Fi schedule page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31363") // It's a testcase id/link from Jira Test Case.
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
        "Test Step 4: In WiFi schedule page create multiple Schedules" +
        "Test Step 5: Now try to delete all the WiFi Schedules"
    )
    public void step3() {
        DevicesOrbiWifiSchedulePage wifischedule = new DevicesOrbiWifiSchedulePage();
        
        // Create first schedule
        wifischedule.clickAdd();
        wifischedule.setScheduleName("PRJCBUGEN-T31363-1");
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
        MyCommonAPIs.sleepi(10);
        
        // Create second schedule
        wifischedule.clickAdd();
        wifischedule.setScheduleName("PRJCBUGEN-T31363-2");
        wifischedule.selectDayCircle("1", true);
        
        wifischedule.expandDayTimePicker("1", true);
        wifischedule.setDayMinute("1", true, "00");
        wifischedule.setDayHour("1", true, "09");
        wifischedule.selectAmPm("1", "AM", true);
        wifischedule.clickDayTimePickerOK("1", true);
        
        wifischedule.expandDayTimePicker("1", false);
        wifischedule.setDayMinute("1", false, "00");
        wifischedule.setDayHour("1", false, "06");
        wifischedule.selectAmPm("1", "PM", false);
        wifischedule.clickDayTimePickerOK("1", false);
        
        wifischedule.clickSave();
        MyCommonAPIs.sleepi(10);
        
        wifischedule.gotoPage();
        // Delete first schedule
        wifischedule.deleteWifiSchedule("PRJCBUGEN-T31363-1");
        boolean result = wifischedule.checkWifiScheduleExists("PRJCBUGEN-T31363-1");
        assertFalse(result,"WiFi Schedule PRJCBUGEN-T31363-1 should not be listed after deleted by user");
        
        // Delete second schedule
        wifischedule.deleteWifiSchedule("PRJCBUGEN-T31363-2");
        result = wifischedule.checkWifiScheduleExists("PRJCBUGEN-T31363-2");
        assertFalse(result,"WiFi Schedule PRJCBUGEN-T31363-2 should not be listed after deleted by user");
        
    }
    
}