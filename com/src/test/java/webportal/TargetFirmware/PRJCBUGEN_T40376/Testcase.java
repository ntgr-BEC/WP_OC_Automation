package webportal.TargetFirmware.PRJCBUGEN_T40376;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.BRUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import util.XMLManagerForTest;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.FirmwarePage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Anusha H
 */
public class Testcase extends TestCaseBase {

    @Feature("TargetFirmware") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_UpdateFw") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether the client gets reconnected automatically after the MANUAL UPDATE  is happened") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-UpdateFw") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        System.out.println("Downgrade the FW version of the AP ");
        new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
        String FWversion= new APUtils(WebportalParam.ap1IPaddress).getVersion(false);
        
        if((!FWversion.contains(WebportalParam.ap1Firmware)) || !(new WirelessQuickViewPage().FWverOnInsight.getText().equals(WebportalParam.ap1Firmware))  ){
            new APUtils(WebportalParam.ap1IPaddress).switchTheFW();
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Repeatly do update firmware to check device can be online in cloud")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
        
    }
    
    @Step("Test Step 2: Check whether the AP is in lower verison if not downgrade it;")
    public void step2() {
        new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
        String FWversion= new APUtils(WebportalParam.ap1IPaddress).getVersion(false);
        
        if((!FWversion.contains(WebportalParam.ap1Firmware)) || !(new WirelessQuickViewPage().FWverOnInsight.getText().equals(WebportalParam.ap1Firmware))  ){
            new APUtils(WebportalParam.ap1IPaddress).switchTheFW();
        }     
    }
    
    @Step("Test Step 3: Add a ssid, connect to client  and check the client listing on INSIGHT;")
    public void step3() {
      
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp14270");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);
                
        assertTrue(new WirelessQuickViewPage().connectClient(locationInfo),"did not connect to client");
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
    }
 
    @Step("Test Step 4: Set the target firmware and then update the FW by MANUAL UPDATE option  ;")
    public void step4() {
        
        new FirmwarePage().gotoFirmwarePage(); 
        new FirmwarePage().setTargetFW();
        
        if (fmp.waitForUpdateAvailable() && fmp.ManualUpdate(false)) {
            System.out.println("device is upgraded via cloud to the set TARGET FIRMWARE");
        }
        new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
        String FWversion= new APUtils(WebportalParam.ap1IPaddress).getVersion(false);
        assertTrue(FWversion.contains(WebportalParam.ap1TargetFirmware), "The AP is not updated to the TARGET GFIRMWARE");
        
    }
    
    @Step("Test Step 5: Wait for 5 mins and check whether the client is reconnected back on insight  ;")
    public void step5() {
        
        MyCommonAPIs.sleepi(180);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        
    }
    
} 