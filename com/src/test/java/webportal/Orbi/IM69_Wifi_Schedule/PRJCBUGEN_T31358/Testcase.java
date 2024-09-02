package webportal.Orbi.IM69_Wifi_Schedule.PRJCBUGEN_T31358;

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
import webportal.weboperation.DevicesOrbiWifiSchedulePage;
import webportal.publicstep.UserManage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("Orbi.IM69_Wifi_Schedule") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31358") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that the User is able to create SSID Schedule by clicking on the \"Add Schedule\" button") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31358") // It's a testcase id/link from Jira Test Case.
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
    
    @Step("Test Step 2: Onboard Orbi SXR30 device")
    public void step2() {
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openOB2();
    }

    @Step(
        "Test Step 3: Goto Orbi --> Device Dashboard --> WiFi Schedule page " + 
        "Test Step 4: Click on (+) Add Schedule button " + 
        "Test Step 5: Configure Schedule Name , select Days and Time , save the configuration"
    )
    public void step3() {
        DevicesOrbiWifiSchedulePage page = new DevicesOrbiWifiSchedulePage();
        page.clickAdd();
        page.setScheduleName("PRJCBUGEN-T31358");
        page.selectDayCircle("1", true);
        
        page.expandDayTimePicker("1", true);
        page.setDayMinute("1", true, "00");
        page.setDayHour("1", true, "08");
        page.selectAmPm("1", "AM", true);
        page.clickDayTimePickerOK("1", true);
        
        page.expandDayTimePicker("1", false);
        page.setDayMinute("1", false, "00");
        page.setDayHour("1", false, "05");
        page.selectAmPm("1", "PM", false);
        page.clickDayTimePickerOK("1", false);
        
        page.clickSave();
        
        MyCommonAPIs.sleepi(60);
        page.deleteWifiSchedule("PRJCBUGEN-T31358");
        
    }
    
}