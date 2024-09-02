package webportal.Orbi.IM69_Wifi_Schedule.PRJCBUGEN_T31776;

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
import orbi.webelements.DNIOrbiCommanElement;
import orbi.webelements.OrbiAllMenueElements;
import orbi.webelements.OrbiBasicWirelessElements;
import orbi.weboperation.OrbiLoginPage;
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
    @Story("PRJCBUGEN_T31776") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to configure WiFi Schedule with mix of AM and PM time slots") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31776") // It's a testcase id/link from Jira Test Case.
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
        "Test Step 5: Configure Schedule Name , select Days and Time , save the configuration" + 
        "Test Step 6: Select and apply configured WiFi schedule to an SSID"
    )
    public void step3() {
        DevicesOrbiWifiSchedulePage wifischedule = new DevicesOrbiWifiSchedulePage();
        wifischedule.clickAdd();
        wifischedule.setScheduleName("PRJCBUGEN-T31776");
        
        // Monday
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
        
        // Tuesday
        wifischedule.selectDayCircle("2", true);
        
        wifischedule.expandDayTimePicker("2", true);
        wifischedule.setDayMinute("2", true, "30");
        wifischedule.setDayHour("2", true, "08");
        wifischedule.selectAmPm("2", "AM", true);
        wifischedule.clickDayTimePickerOK("2", true);
        
        wifischedule.expandDayTimePicker("2", false);
        wifischedule.setDayMinute("2", false, "00");
        wifischedule.setDayHour("2", false, "06");
        wifischedule.selectAmPm("2", "PM", false);
        wifischedule.clickDayTimePickerOK("2", false);
        
        // Wednesday
        wifischedule.selectDayCircle("3", true);
        
        wifischedule.expandDayTimePicker("3", true);
        wifischedule.setDayMinute("3", true, "00");
        wifischedule.setDayHour("3", true, "09");
        wifischedule.selectAmPm("3", "AM", true);
        wifischedule.clickDayTimePickerOK("3", true);
        
        wifischedule.expandDayTimePicker("3", false);
        wifischedule.setDayMinute("3", false, "30");
        wifischedule.setDayHour("3", false, "06");
        wifischedule.selectAmPm("3", "PM", false);
        wifischedule.clickDayTimePickerOK("3", false);
        
        // Thursday
        wifischedule.selectDayCircle("4", true);
        
        wifischedule.expandDayTimePicker("4", true);
        wifischedule.setDayMinute("4", true, "45");
        wifischedule.setDayHour("4", true, "10");
        wifischedule.selectAmPm("4", "AM", true);
        wifischedule.clickDayTimePickerOK("4", true);
        
        wifischedule.expandDayTimePicker("4", false);
        wifischedule.setDayMinute("4", false, "50");
        wifischedule.setDayHour("4", false, "11");
        wifischedule.selectAmPm("4", "PM", false);
        wifischedule.clickDayTimePickerOK("4", false);
        
        // Friday
        wifischedule.selectDayCircle("5", true);
        
        wifischedule.expandDayTimePicker("5", true);
        wifischedule.setDayMinute("5", true, "00");
        wifischedule.setDayHour("5", true, "07");
        wifischedule.selectAmPm("5", "AM", true);
        wifischedule.clickDayTimePickerOK("5", true);
        
        wifischedule.expandDayTimePicker("5", false);
        wifischedule.setDayMinute("5", false, "00");
        wifischedule.setDayHour("5", false, "09");
        wifischedule.selectAmPm("5", "PM", false);
        wifischedule.clickDayTimePickerOK("5", false);
        
        wifischedule.clickSave();
        
        // Associate the wifi schedule to wifi2
        DevicesOrbiWifiNetworkPage wifinetwork = new DevicesOrbiWifiNetworkPage();
        wifinetwork.editSsid(1);
        wifinetwork.setWifiSchedule(true);
        wifinetwork.selectWifiSchedule("PRJCBUGEN-T31776");
        wifinetwork.clickSave();
        MyCommonAPIs.sleepi(120);
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step(
        "Test Step 7: Verify Orbi SSID should get enabled on configured Time"
    )
    public void step7() {
        // Login Orbi
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage("admin", WebportalParam.loginPassword, WebportalParam.ob2IPaddress);
        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
        OrbiBasicWirelessElements BasicWirelessElements = new OrbiBasicWirelessElements();
        
        util.MyCommonAPIs.waitReady();
        BrAllMenueElements.Wireless.click();
        util.MyCommonAPIs.waitReady();
        Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
        
        BasicWirelessElements.wireless2.click();
        
        
        String day = "document.querySelector('wlan-schedule#wireless_schedule').shadowRoot.querySelector('table#schedule_table>tbody>tr:nth-child(%d)>td:nth-child(2)>span')";
        String mon_time = MyCommonAPIs.getShadowRootElementinnerText(String.format(day, 3));
        String tue_time = MyCommonAPIs.getShadowRootElementinnerText(String.format(day, 4));
        String wed_time = MyCommonAPIs.getShadowRootElementinnerText(String.format(day, 5));
        String thu_time = MyCommonAPIs.getShadowRootElementinnerText(String.format(day, 6));
        String fri_time = MyCommonAPIs.getShadowRootElementinnerText(String.format(day, 7));
        
        // Restore
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openOB2();
        DevicesOrbiWifiNetworkPage wifinetwork = new DevicesOrbiWifiNetworkPage();
        wifinetwork.gotoPage();
        wifinetwork.editSsid(1);
        wifinetwork.setWifiSchedule(false);
        wifinetwork.clickSave();
        MyCommonAPIs.sleepi(120);
        DevicesOrbiWifiSchedulePage wifischedule = new DevicesOrbiWifiSchedulePage();
        wifischedule.deleteWifiSchedule("PRJCBUGEN-T31776");
        
        // Assertion
        assertTrue(mon_time.contains("8:00am-5:00pm"),"Monday timeslot is wrong");
        assertTrue(tue_time.contains("8:30am-6:00pm"),"Tuesday timeslot is wrong");
        assertTrue(wed_time.contains("9:00am-6:30pm"),"Wednesday timeslot is wrong");
        assertTrue(thu_time.contains("10:45am-11:50pm"),"Thursday timeslot is wrong");
        assertTrue(fri_time.contains("7:00am-9:00pm"),"Friday timeslot is wrong");
    }
    
}