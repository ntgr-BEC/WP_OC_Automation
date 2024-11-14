package webportal.RfParameters.Pro.PRJCBUGEN_T36179;

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
import webportal.weboperation.AccountPage;
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
    String dtim5;
    String broadcast5;
    String dtim5h_6;
    String broadcast5h_6;
    @Feature("RF Paramater") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36179") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify,RF parameter is a part of copy config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T36179") // It's a testcase id/link from Jira Test Case.


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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        
    }


    @Step("Test Step 2: OBSERVE DTIM INTERVAL VALUE;")
    public void step2() {
   
    
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
           
//          @Step("Test Step 2: Chexk for config push;") 
//          public void step3()
//          {
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"0").contains(dtim2),"Valus mismatch");
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"0").contains(broadcast2),"Valus mismatch");
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"0").contains("125"),"Valus mismatch");
//           
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"1").contains(dtim5),"Valus mismatch");
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"1").contains(broadcast5),"Valus mismatch");
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"1").contains("300"),"Valus mismatch");
//           
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getDtimStatus(WebportalParam.ap1Model,"2").contains(dtim2),"Valus mismatch");
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBroadcaststatus(WebportalParam.ap1Model,"2").contains(broadcast2),"Valus mismatch");
//           assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBeaconStatus(WebportalParam.ap1Model,"2").contains("225"),"Valus mismatch");
//          }
          
          @Step("Test Step 3: Create location and copy config")
          public void step4()
          {
          Map<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("Location Name", "office2");
          locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo.put("Zip Code", "4560");
          locationInfo.put("Country", "Australia");
          new AccountPage().addNetwork(locationInfo);
          new WirelessQuickViewPage().CopyConfig(WebportalParam.Organizations, WebportalParam.location1, "office2" );
          handle.gotoLoction("office2");
          }
          
          @Step("Test Step 4: Create location and copy config")
          public void step5()
          {
              new WirelessQuickViewPage().GoToWirelessSettings();
              MyCommonAPIs.sleepi(10);
              assertTrue((new WirelessQuickViewPage(false).sliderbroadcast("1").getAttribute("aria-valuenow")).equals(dtim2),"Intervals are missing");
              assertTrue((new WirelessQuickViewPage(false).sliderdtm("1").getAttribute("aria-valuenow")).equals(broadcast2),"Intervals are missing");
              new WirelessQuickViewPage(false).beacon("1").getText().equals("125");
              
              MyCommonAPIs.sleepi(5);
              new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();   
              assertTrue((new WirelessQuickViewPage(false).sliderbroadcast("2").getAttribute("aria-valuenow")).equals(dtim5),"Intervals are missing");
              assertTrue((new WirelessQuickViewPage(false).sliderdtm("2").getAttribute("aria-valuenow")).equals(broadcast5),"Intervals are missing");
              new WirelessQuickViewPage(false).beacon("2").getText().equals("300");
              
              if(Model.equals("WAC564") || Model.equals("WAC540") || Model.equals("WAX630")) {
                  MyCommonAPIs.sleepi(5);
                  new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();                      
              assertTrue((new WirelessQuickViewPage(false).sliderbroadcast("3").getAttribute("aria-valuenow")).equals("dtim5h_6"),"Intervals are missing");
              assertTrue((new WirelessQuickViewPage(false).sliderdtm("3").getAttribute("aria-valuenow")).equals("broadcast5h_6"),"Intervals are missing");
              new WirelessQuickViewPage(false).beacon("3").getText().equals("225");
              }
              
              if(Model.equals("WAX630E") || Model.equals("WAX638E") || Model.startsWith("WBE")) {
                  MyCommonAPIs.sleepi(5);
                  new WirelessQuickViewPage(false).DropDown6GhzHighWireless.click();      
              assertTrue((new WirelessQuickViewPage(false).sliderbroadcast("4").getAttribute("aria-valuenow")).equals("dtim5h_6"),"Intervals are missing");
              assertTrue((new WirelessQuickViewPage(false).sliderdtm("4").getAttribute("aria-valuenow")).equals("broadcast5h_6"),"Intervals are missing");
              new WirelessQuickViewPage(false).beacon("4").getText().equals("225");
              }
              
              
              
              
          }
          
          
          
          
          
          
          
          
          
     
          
}

