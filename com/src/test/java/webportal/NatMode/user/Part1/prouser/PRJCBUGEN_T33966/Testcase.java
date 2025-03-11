package webportal.NatMode.user.Part1.prouser.PRJCBUGEN_T33966;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;


import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.*;

/**
*
* @author Ravishankar
*
*/
public class Testcase extends TestCaseBase {

   Map<String, String> ssidInfo = new HashMap<String, String>();

   @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
   @Story("PRJCBUGEN_T33966") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("Add SSID with NAT Mode then add AP ---> reboot AP.and check configure") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33966") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       System.out.println("start to do tearDown");
       new WirelessQuickViewPage().deleteALLSSID();
   }

   // Each step is a single test step from Jira Test Case
   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
       new HamburgerMenuPage(false).closeLockedDialog();
       new OrganizationPage(false).openOrg(WebportalParam.Organizations);

       handle.gotoLoction();
       // new DevicesDashPage().checkDeviceInAdminAccount();
   }

   @Step("Test step 2: DELETE THE AP")
   public void step2() 
   {   new DevicesDashPage(true).gotoPage();
       new DevicesDashPage().deleteDeviceNo(WebportalParam.ap1serialNo);
   }
   @Step("Test Step 3: Create NAT SSID ")
   public void step3() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsidNat(ssidInfo);  

       new DevicesDashPage().AddNewDevice(WebportalParam.ap1serialNo,WebportalParam.ap1macaddress);
       MyCommonAPIs.sleepi(60);
       new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo); 
       MyCommonAPIs.sleepi(30);

       
   }
   @Step("Test Step 4: Check whether the configurations are pushed to the ap")
   public void step4() {
       
       new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
       MyCommonAPIs.sleepi(120);
       assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus(WebportalParam.ap1Model), "CONFIG NOT PUSHED");
   }
   
   @Step("Test Step 5: Check whether the configurations are pushed to the ap")
   public void step5() {

   new DevicesDashPage().waitDevicesReboot(WebportalParam.ap1serialNo);
   
   }
   
   @Step("Test Step 6: Check whether the configurations are pushed to the ap")
   public void step6() {
       
       new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
       MyCommonAPIs.sleepi(120);
       assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus(WebportalParam.ap1Model), "CONFIG NOT PUSHED");
   }

   
   

}