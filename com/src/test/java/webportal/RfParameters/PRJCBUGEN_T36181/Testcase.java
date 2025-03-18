package webportal.RfParameters.PRJCBUGEN_T36181;

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
    String Model=WebportalParam.ap1Model;
    String dtim2;
    String broadcast2;
    String beacon2;
    String dtim5;
    String broadcast5;
    String beacon5;
    String dtim5h_6;
    String broadcast5h_6;
    @Feature("RF Paramater") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36181") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify,RF parameter is pushed to all AP's in that location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T36181") // It's a testcase id/link from Jira Test Case.


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


   
           
          @Step("Test Step 2: Chexk for config push;") 
          public void step2()
          {
              dtim2=new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"0");
              broadcast2=new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"0");
              beacon2 = new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"0");
           

              dtim5=new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"1");
              broadcast5=new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"1");
              beacon5= new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"1");
           
          }
           
          
          
          
          @Step("Test Step 3: check for equality")
          public void step3() {
         
          
            new WirelessQuickViewPage().GoToWirelessSettings();
              MyCommonAPIs.sleepi(10);
              
              assertTrue((new WirelessQuickViewPage(false).sliderbroadcast("1").getAttribute("aria-valuenow")).equals(dtim2),"Intervals1  are missing");
              assertTrue((new WirelessQuickViewPage(false).sliderdtm("1").getAttribute("aria-valuenow")).equals(broadcast2),"Intervals2 are missing");
              (new WirelessQuickViewPage(false).beacon("1").getText()).equals(beacon2);
              
              MyCommonAPIs.sleepi(5);
              new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();   
              assertTrue((new WirelessQuickViewPage(false).sliderbroadcast("2").getAttribute("aria-valuenow")).equals(dtim5),"Intervals3 are missing");
              assertTrue((new WirelessQuickViewPage(false).sliderdtm("2").getAttribute("aria-valuenow")).equals(broadcast5),"Intervals4 are missing");
              (new WirelessQuickViewPage(false).beacon("2").getText()).equals(beacon5);
              
            
              }
          
          
          @Step("Test Step 4: Set values")
          public void step4() {
         
          
             new WirelessQuickViewPage().GoToWirelessSettings();
              MyCommonAPIs.sleepi(10);


                  dtim2=new WirelessQuickViewPage(false).DragDensityToDtim("1","3","1");
                  broadcast2=new WirelessQuickViewPage(false).DragDensityToBroadcast("1","2","1");
                 new WirelessQuickViewPage(false).setBeaconvalue("125","1");
                 MyCommonAPIs.sleepi(5);
                 new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();       
                 dtim5=new WirelessQuickViewPage(false).DragDensityToDtim("2","2","2");
                 broadcast5=new WirelessQuickViewPage(false).DragDensityToBroadcast("2","1","2");
                 new WirelessQuickViewPage(false).setBeaconvalue("300","2");
                 MyCommonAPIs.sleepi(5);
                 
                 if(Model.equals("WAC564") || Model.equals("WAC540") || Model.equals("WAX630")) {
                new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();          
                 dtim5h_6=new WirelessQuickViewPage(false).DragDensityToDtim("3","4","3");
                 broadcast5h_6=new WirelessQuickViewPage(false).DragDensityToBroadcast("3","4","3");
                 new WirelessQuickViewPage(false).setBeaconvalue("225","3");
                 }
                 
                 MyCommonAPIs.sleepi(5);
                 if(Model.equals("WAX630E") || Model.equals("WAX638E") || Model.startsWith("WBE")) {          
                 new WirelessQuickViewPage(false).DropDown6GhzHighWireless.click();             
                 dtim5h_6=new WirelessQuickViewPage(false).DragDensityToDtim("4","3","4");
                 broadcast5h_6=new WirelessQuickViewPage(false).DragDensityToBroadcast("4","2","4");
                 new WirelessQuickViewPage(false).setBeaconvalue("225","4");
                 }
          }
          
          
        @Step("Test Step 5: Chexk for config push;") 
        public void step5()
        {
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"0").contains(dtim2),"Valus mismatch");
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"0").contains(broadcast2),"Valus mismatch");
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"0").contains("125"),"Valus mismatch");
         
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"1").contains(dtim5),"Valus mismatch");
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"1").contains(broadcast5),"Valus mismatch");
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"1").contains("300"),"Valus mismatch");
         
         if(Model.equals("WAC564") || Model.equals("WAC540") || Model.equals("WAX630") || Model.equals("WAX630E") || Model.equals("WAX638E") || Model.startsWith("WBE")) {
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"2").contains(dtim5h_6),"Valus mismatch");
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"2").contains(broadcast5h_6),"Valus mismatch");
         assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"2").contains("225"),"Valus mismatch");
         }
        }
          
          

          
        
}

