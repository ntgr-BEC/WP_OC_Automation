package webportal.TargetFirmware.PRJCBUGEN_T40374;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import util.BRUtils;
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
    @Story("PRJCBUGEN_T40374") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to select a target firwmare and then update the FW by UPDATE ALL option") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40374") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        System.out.println("Downgrade the FW version of the AP ");
     
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
       
        String FWversion= new APUtils(WebportalParam.ap1IPaddress).getVersion(false);
        
        if((!FWversion.contains(WebportalParam.ap1Firmware)) || !(new WirelessQuickViewPage().FWverOnInsight.getText().equals(WebportalParam.ap1Firmware))  ){
            new APUtils(WebportalParam.ap1IPaddress).switchTheFW();
        }
    
    }
    @Step("Test Step 3: Set the target firmware adn then update the FW by UPDATE ALL option ;")
    public void step3() {
        
        new FirmwarePage().gotoFirmwarePage();
        new FirmwarePage().setTargetFW();
        
        if (fmp.waitForUpdateAvailable() && fmp.doUpdate(false)) {
            System.out.println("device is upgraded via cloud to the set TARGET FIRMWARE");
        }
       
        String FWversion= new APUtils(WebportalParam.ap1IPaddress).getVersion(false);
        assertTrue((FWversion.contains(WebportalParam.ap1TargetFirmware) || (new WirelessQuickViewPage().FWverOnInsight.getText().equals(WebportalParam.ap1TargetFirmware))), "The AP is not updateed to the TARGET GFIRMWARE");
            
        
    }
} 