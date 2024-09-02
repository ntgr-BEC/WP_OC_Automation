package webportal.Orbi.IM69_Wifi_Schedule.PRJCBUGEN_T31361;

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
    @Story("PRJCBUGEN_T31361") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify user are not allowed to delete the WiFi schedule which is associated with Orbi SSID and gets error message") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31361") // It's a testcase id/link from Jira Test Case.
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
        "Test Step 4: Now try to delete the listed WiFi Schedule"
    )
    public void step3() {
        DevicesOrbiWifiSchedulePage wifischedule = new DevicesOrbiWifiSchedulePage();
        wifischedule.clickAdd();
        wifischedule.setScheduleName("PRJCBUGEN-T31361");
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
        
        // Associate the wifi schedule to wifi1
        DevicesOrbiWifiNetworkPage wifinetwork = new DevicesOrbiWifiNetworkPage();
        wifinetwork.editSsid(0);
        wifinetwork.setWifiSchedule(true);
        wifinetwork.selectWifiSchedule("PRJCBUGEN-T31361");
        wifinetwork.clickSave();
        MyCommonAPIs.sleepi(120);
        
        wifischedule.gotoPage();
        boolean result = wifischedule.deleteUsedWifiSchedule("PRJCBUGEN-T31361");
        assertTrue(result,"User should not be able to delete the WiFi Schedule");
        
        wifinetwork.gotoPage();
        wifinetwork.editSsid(0);
        wifinetwork.setWifiSchedule(false);
        wifinetwork.clickSave();
        MyCommonAPIs.sleepi(120);
        
        wifischedule.gotoPage();
        wifischedule.deleteWifiSchedule("PRJCBUGEN-T31361");
        
    }
    
}