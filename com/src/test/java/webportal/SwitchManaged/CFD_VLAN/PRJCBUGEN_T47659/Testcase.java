package webportal.SwitchManaged.CFD_VLAN.PRJCBUGEN_T47659;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    // Define VLAN IDs
    private String[] vlanIds             = {"15", "16", "10", "20", "40", "50", "60"};
    private String[] vlanIdstag          = {"1","15", "16", "10", "20"};
    private String[] vlanIdsuntag        = {"40", "50", "60"};
    private String[] vlanIdstagChange    = {"1","15", "16", "10", "20", "40", "50"};
    private String[] vlanIdsuntagChange  = { "60"};
    String url = WebportalParam.serverUrl;
    Map<String, String> GroupconfigSW1 = new HashMap<String, String>();
    Map<String, String> GroupconfigSW2 = new HashMap<String, String>();
   


    @Feature("Switch.CFD_VLAN")
    @Story("PRJCBUGEN_T47659")
    @Description("Verify Group port configuration with 2 switches while 2 concurrent browsers opened")
    @TmsLink("PRJCBUGEN-T47659")
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @Step("Test Step 1: Login and clean up existing VLANs")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();

        // Initialize VLAN page and clean up existing VLANs
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
        MyCommonAPIs.sleepsync();
    }

    @Step("Test Step 2: Create VLANs with specific IDs and names")
    public void step2() {
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();

        // Create each VLAN
        for (String vlanId : vlanIds) {
            String vlanName = "VLAN" + vlanId;  // Changed format to "VLAN15" instead of "VLANName15"

            // Create VLAN without any port assignments
            wvp.newVlan(vlanName, vlanId, 0);
            MyCommonAPIs.sleep(8000);
            // Verify VLAN creation
            wvp.gotoPage();
            List<String> vlans = vlanPage.getVlans();
            System.out.println("vlan is:" + vlans);
            MyCommonAPIs.sleep(3000);
            if (vlans.contains(vlanName) ) {
            micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "------Check Point 4: vlan100 vlan101 is show:" + vlanName);
        }
        }
    }

    @Step("Test Step 3: Tag and untag VLANs")
        public void step3() {

          new WiredGroupPortConfigPage().setVLAN(vlanIdstag, vlanIdsuntag,  SWITCH1_PORTARRAY, GroupconfigSW1);
          new WiredGroupPortConfigPage().setVLAN(vlanIdstag, vlanIdsuntag,  SWITCH1_PORTARRAY2, GroupconfigSW2);

    }
    
    
    @Step("Test Step 4: Now open concurent tab and check SW1 and SW2")
    public void step4() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        MyCommonAPIs.sleepi(20);
        Selenide.switchTo().window(1);
        String currentURL = new MyCommonAPIs().getCurrentUrl();        
        System.out.println("current URL is "+ currentURL);
        MyCommonAPIs.sleepi(20);
        
        
        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();       
        
        String[] vlan_ids = new String[sw1port.length];
        
        for (int i = 0; i < sw1port.length; i++) {
            vlan_ids[i] = devicesSwitchSummaryPage.getPortVlanId(sw1port[i]).replaceAll("VLAN ID\\s*:\\s*", "").replaceAll("\\s+", "") .trim();
            System.out.println(vlan_ids[i]);
        }
        
        boolean isTagged =new WiredGroupPortConfigPage().checkVLANconfigTag(vlanIdstag, vlan_ids);
        boolean isUnTagged =new WiredGroupPortConfigPage().checkVLANconfigUnTag(vlanIdsuntag, vlan_ids);
       
        assertTrue(isTagged, " Tag not found in any port.");
        assertTrue(isUnTagged, " Untag not found in any port.");
;
        
       

        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);           
        String[] vlan_ids1 = new String[sw2port.length];
        
        for (int i = 0; i < sw2port.length; i++) {
            vlan_ids1[i] = devicesSwitchSummaryPage.getPortVlanId(sw2port[i]).replaceAll("VLAN ID\\s*:\\s*", "").replaceAll("\\s+", "") .trim();
            System.out.println(vlan_ids1[i]);
        }
        
        boolean isTagged1 =new WiredGroupPortConfigPage().checkVLANconfigTag(vlanIdstag, vlan_ids1);
        boolean isUnTagged1 =new WiredGroupPortConfigPage().checkVLANconfigUnTag(vlanIdsuntag, vlan_ids1);
       
        assertTrue(isTagged1, " Tag not found in any port.");
        assertTrue(isUnTagged1, " Untag not found in any port.");
        
       
    }
    
    
    @Step("Test Step 6: Tag and untag VLANs")
    public void step6() {
        
        GroupconfigSW1.put("Default_VLAN", "VLAN60 (60)");
        GroupconfigSW2.put("Default_VLAN", "VLAN60 (40)");
        

          new WiredGroupPortConfigPage().setVLAN(vlanIdstagChange, vlanIdsuntagChange,  SWITCH1_PORTARRAY, GroupconfigSW1);
          new WiredGroupPortConfigPage().setVLAN(vlanIdstagChange, vlanIdsuntagChange,  SWITCH1_PORTARRAY2, GroupconfigSW2);

    }
    
    
    @Step("Test Step 7: Now open concurent tab")
    public void step7() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        MyCommonAPIs.sleepi(20);
        Selenide.switchTo().window(1);
        String currentURL = new MyCommonAPIs().getCurrentUrl();        
        System.out.println("current URL is "+ currentURL);
        MyCommonAPIs.sleepi(20);
        
        
        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();       
        
        String[] vlan_ids = new String[sw1port.length];
        
        for (int i = 0; i < sw1port.length; i++) {
            vlan_ids[i] = devicesSwitchSummaryPage.getPortVlanId(sw1port[i]).replaceAll("VLAN ID\\s*:\\s*", "").replaceAll("\\s+", "") .trim();
            System.out.println(vlan_ids[i]);
        }
        
        boolean isTagged =new WiredGroupPortConfigPage().checkVLANconfigTag(vlanIdstagChange, vlan_ids);
        boolean isUnTagged =new WiredGroupPortConfigPage().checkVLANconfigUnTag(vlanIdsuntagChange, vlan_ids);
       
        assertTrue(isTagged, " Tag not found in any port.");
        assertTrue(isUnTagged, " Untag not found in any port.");
        driver.close();
        Selenide.switchTo().window(0);
        
       
        
        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw2serialNo);           
        String[] vlan_ids1 = new String[sw2port.length];
        
        for (int i = 0; i < sw2port.length; i++) {
            vlan_ids1[i] = devicesSwitchSummaryPage.getPortVlanId(sw2port[i]).replaceAll("VLAN ID\\s*:\\s*", "").replaceAll("\\s+", "") .trim();
            System.out.println(vlan_ids1[i]);
        }
        
        boolean isTagged1 =new WiredGroupPortConfigPage().checkVLANconfigTag(vlanIdstagChange, vlan_ids1);
        boolean isUnTagged1 =new WiredGroupPortConfigPage().checkVLANconfigUnTag(vlanIdsuntagChange, vlan_ids1);
       
        assertTrue(isTagged1, " Tag not found in any port.");
        assertTrue(isUnTagged1, " Untag not found in any port.");
    }

    

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        System.out.println("Starting cleanup...");
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
        MyCommonAPIs.sleepsync();
    }
}