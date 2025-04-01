package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39787;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesApRadioAndChannelsPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    

    Map<String, String> RFdata = new HashMap<String, String>();
    Map<String, String> RFdata1 = new HashMap<String, String>();
    
    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case
    @Story("PRJCBUGEN_T39787") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user can modify all the Radio settings configuration for each RF profile and save it successfully, once the AP selects this RF Profile these config should be pushed to the AP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39787") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {   
        new DevicesDashPage().GoToDevicesDashPage();
        new DevicesDashPage().UNAssignRF(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).deleteRF(RFdata.get("RFName"));
        System.out.println("start to do tearDown");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
       
    }

    
    @Step("Test Step 2: Create RF profile")
    public void step2() {
        
        RFdata.put("RFName", "Netgear");
        RFdata.put("RFDescription", "BEC Automation Team");
        RFdata.put("Copy Configurations", "Open Office");
        
        
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).CreateRFProfile(RFdata);
        assertTrue(new WirelessQuickViewPage(false).checkRFExist(RFdata.get("RFName")),"RF Not created");
       
    }
    
    @Step("Test Step 3: Edit Radio settings of  RF profile")
    public void step3() {
        
        RFdata.put("5GHz output power", "Half");
        RFdata.put("5GHz channel width", "40MHz");
        RFdata.put("5GHz Radio Mode", "11ac");
        
       new WirelessQuickViewPage(false).assignedRadioSetting(RFdata);
        
    }
    
    @Step("Test Step 4: Assign  RF profile")
    public void step4() {
        new DevicesDashPage().GoToDevicesDashPage();
        String RFName = RFdata.get("RFName");
        new DevicesDashPage().AssignRF(WebportalParam.ap1serialNo, RFName);  
        MyCommonAPIs.sleepi(30);
    }
    
    @Step("Test Step 5: check assigned radio settings profile")
    public void step5() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click(); 
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click();
        MyCommonAPIs.sleepi(10);
        String radioMode = new WirelessQuickViewPage(false).RadioMode5Device.getSelectedText();
        System.out.println("Radio Mode: " + radioMode);
        String channelWidth = new WirelessQuickViewPage(false).channelWidth5Device.getSelectedText();
        System.out.println("Channel Width: " + channelWidth);
        String outputPower = new WirelessQuickViewPage(false).outputpower5Device.getSelectedText();
        System.out.println("Output Power: " + outputPower);
        
        assertTrue(
                radioMode.equals(RFdata.get("5GHz Radio Mode")) &&
                channelWidth.equals(RFdata.get("5GHz channel width")) &&
                outputPower.contains(RFdata.get("5GHz output power")),
                "2.4GHz settings do not match expected values: " + RFdata
            );
      
    }
   
    
    
   

}
