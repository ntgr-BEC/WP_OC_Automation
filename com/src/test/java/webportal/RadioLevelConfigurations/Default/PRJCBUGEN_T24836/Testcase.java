package webportal.RadioLevelConfigurations.Default.PRJCBUGEN_T24836;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshiwni K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24836") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify  default radio mode is ax for 2.4 GHz and 5 GHz gor WAX APs ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24836") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        
    }

    
    @Step("Test Step 2: Check 2ghz default Radio Mode;")
    public void step2() {
        
        
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap3serialNo);
        devInfo.put("Device Name", WebportalParam.ap3deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap3macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap3serialNo); 
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap3serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click(); 
        
        if(WebportalParam.ap3Model.contains("WBE750") || WebportalParam.ap3Model.contains("WBE758") ) {
            String mode = new WirelessQuickViewPage(false).Radiomode24.getText();
            System.out.println(mode);
            assertTrue(mode.equals("11ax"), "default value is not right");
            
            
            String mode5low = new WirelessQuickViewPage(false).Radiomode5low.getText();
            System.out.println(mode5low);
            assertTrue(mode5low.equals("11be"), "default value is not right");
            
        }else if(WebportalParam.ap3Model.contains("WBE710") || WebportalParam.ap3Model.contains("WBE718")) {
            String mode = new WirelessQuickViewPage(false).Radiomode24.getText();
            System.out.println(mode);
            assertTrue(mode.equals("11be"), "default value is not right");
            
            
            String mode5low = new WirelessQuickViewPage(false).Radiomode5low.getText();
            System.out.println(mode5low);
            assertTrue(mode5low.equals("11be"), "default value is not right");
            
        }else {
        String mode = new WirelessQuickViewPage(false).Radiomode24.getText();
        System.out.println(mode);
        assertTrue(mode.equals("11ax"), "default value is not right");
        
        
        String mode5low = new WirelessQuickViewPage(false).Radiomode5low.getText();
        System.out.println(mode5low);
        assertTrue(mode5low.equals("11ax"), "default value is not right");
        
        }
       

    }
   

}
