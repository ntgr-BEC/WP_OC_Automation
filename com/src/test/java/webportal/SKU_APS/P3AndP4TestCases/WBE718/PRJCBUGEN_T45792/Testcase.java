package webportal.SKU_APS.P3AndP4TestCases.WBE718.PRJCBUGEN_T45792;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T45792") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify the channel for 5 ghz for WBE718 in Australia") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T45792") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
//        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.defaultLogin();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T24065");
        accountInfo.put("Email Address", mailname + "@yopmail.com");
        accountInfo.put("Confirm Email", mailname + "@yopmail.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Australia");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
   

    @Step("Test Step 2: Create new location;")
    public void step2() {
        
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
         new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        
    }

    
    @Step("Test Step 4: Check 5 Ghz band channels are same as showing on tera term;")
    public void step4() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click(); 
        assertTrue(new WirelessQuickViewPage(false).verifyAndCompareUIChannelsandTeraTermChannelsforBand("5GHz"), "5 Ghz band channels are not same as showing on tera term");
        
    }

}
