package webportal.RfParameters.PRJCBUGEN_T36176;

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
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("RF Paramater") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36176") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify,DTIM iNTERVAL VALUE RANGES BETWEEN 1 TO 255") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T36176") // It's a testcase id/link from Jira Test Case.

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


    
 
    @Step("Test Step 2: OBSERVE DTIM INTERVAL VALUE;")
    public void step2() {
        
      new WirelessQuickViewPage().GoToWirelessSettings();
        MyCommonAPIs.sleepi(10);

//        String s=new WirelessQuickViewPage(false).DragDensityToDtim("1","3");
//        System.out.print(s);
//          assertTrue(s.equals("103"),"Intervals are missing 1st");
            assertTrue((new WirelessQuickViewPage(false).DragDensityToDtim("1","3","1")).equals("99"),"Intervals are missing 1st");
           new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();   
           MyCommonAPIs.sleepi(10);
           assertTrue((new WirelessQuickViewPage(false).DragDensityToDtim("2","2","2")).equals("50"),"Intervals are missing 2nd");
          new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();   
          MyCommonAPIs.sleepi(10);
           assertTrue((new WirelessQuickViewPage(false).DragDensityToDtim("3","4","3")).contains("150"),"Intervals are missing 3rd");
    }
        
//        new WirelessQuickViewPage(false).DropDown5GhzLow.click();
//        
//        String mode = "11a";
//        String mode1 = "11na";
//        String mode2 = "11ac";
//       
//        new WirelessQuickViewPage(false).Radiomode5low.selectOption(mode);
//        assertTrue(new WirelessQuickViewPage(false).RadioMode5ghzlow(mode), "default value is not right");
//        
//        
//        new WirelessQuickViewPage(false).Radiomode5low.selectOption(mode1);
//        assertTrue(new WirelessQuickViewPage(false).RadioMode5ghzlow(mode1), "default value is not right");
//        
//        new WirelessQuickViewPage(false).Radiomode5low.selectOption(mode2);
//        assertTrue(new WirelessQuickViewPage(false).RadioMode5ghzlow(mode2), "default value is not right");        
        

    }
//    
//    @Step("Test Step 3: configured to 11a for 2 GHz WAC505/WAC510;")
//    public void step3() {
//        
//        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap2serialNo);
//        new WirelessQuickViewPage(false).RadioAndChannels.click();
//        MyCommonAPIs.sleepi(10);
//        new WirelessQuickViewPage(false).DropDown5GhzLow.click();
//        
//        
//        String mode = "11a";
//        String mode1 = "11na";
//        String mode2 = "11ac";
//       
//        new WirelessQuickViewPage(false).Radiomode5low.selectOption(mode);
//        assertTrue(new WirelessQuickViewPage(false).RadioMode5ghzlow(mode), "default value is not right");
//        
//        
//        new WirelessQuickViewPage(false).Radiomode5low.selectOption(mode1);
//        assertTrue(new WirelessQuickViewPage(false).RadioMode5ghzlow(mode1), "default value is not right");
//        
//        new WirelessQuickViewPage(false).Radiomode5low.selectOption(mode2);
//        assertTrue(new WirelessQuickViewPage(false).RadioMode5ghzlow(mode2), "default value is not right");        
//        
//
//    }


