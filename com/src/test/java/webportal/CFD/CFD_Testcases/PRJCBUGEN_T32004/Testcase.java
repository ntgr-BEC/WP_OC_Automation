package webportal.CFD.CFD_Testcases.PRJCBUGEN_T32004;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Anusha H
 */
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    OrganizationPage OrganizationPage = new OrganizationPage();
    String organizationName1 = "Netgear";
    String organizationName2 = "Netgear1";
    String locationName     = "office1";
    Map<String, String> locationInfo = new HashMap<String, String>();
    
    @Feature("CFD.CFD_Testcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32004") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Device count at org level and location level;") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T32004") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage.gotoPage();
        OrganizationPage.deleteOrganization(organizationName1);
        MyCommonAPIs.sleepi(10);
        OrganizationPage.deleteOrganization(organizationName2);
        System.out.println("start to do tearDown");
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Check create pro account success.")
    public void step1() {
//        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
        
            mailname = mailname + "@mailinator.com";
            if (new HamburgerMenuPage(false).checkCreateProAccountPage("checkNext:" + mailname)) {
                Map<String, String> proAccountInfo = new HashMap<String, String>();
                proAccountInfo.put("Confirm Email", mailname);
                proAccountInfo.put("Password", "Netgear1@");
                proAccountInfo.put("Confirm Password", "Netgear1@");
                proAccountInfo.put("Country", "Ireland");
                proAccountInfo.put("Phone Number", "1234567890");
                new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

                Map<String, String> businessInfo = new HashMap<String, String>();
                businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
                businessInfo.put("Business Name", "Netgear");
                businessInfo.put("Primary Address of Business", "New Ross");
                businessInfo.put("City", "Wexford");
                businessInfo.put("State", "Wexford");
                businessInfo.put("Zip Code", "KR62");
                businessInfo.put("Country", "Ireland");
                businessInfo.put("Business Phone Number", "1234567890");
                new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);

                assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");    
   }
   }
    
    @Step("Test Step 2: Create two organizations org1(allocate 5 credits) and org2 ;")
    public void step2() {
        
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName1);
        
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName1);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(30);       
        
        OrganizationPage.addAllocateCredits(organizationName1, "5", "0", "0");
        MyCommonAPIs.sleepi(2);
        
        organizationInfo.put("Name", organizationName2);
        
        OrganizationPage.addOrganization(organizationInfo);
   
    }
    
    @Step("Test Step 3: Onboard a AP;")
    public void step3() {
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        Map<String, String> firststdevInfo1= new HashMap<String, String>();
        Map<String, String> firststdevInfo2= new HashMap<String, String>();
        Map<String, String> firststdevInfo3= new HashMap<String, String>();
        Map<String, String> firststdevInfo4= new HashMap<String, String>();
        Map<String, String> firststdevInfo5= new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap2serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap2macaddress);       
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        firststdevInfo1.put("Serial Number1", WebportalParam.ap3serialNo);
        firststdevInfo1.put("MAC Address1", WebportalParam.ap3macaddress);       
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);
        
        firststdevInfo2.put("Serial Number1", WebportalParam.ap4serialNo);
        firststdevInfo2.put("MAC Address1", WebportalParam.ap4macaddress);       
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo2);
        
        firststdevInfo3.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo3.put("MAC Address1", WebportalParam.ap5macaddress);       
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo3);
        
        firststdevInfo4.put("Serial Number1", WebportalParam.ap6serialNo);
        firststdevInfo4.put("MAC Address1", WebportalParam.ap6macaddress);       
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo4);
        
        firststdevInfo5.put("Serial Number1", WebportalParam.ap7serialNo);
        firststdevInfo5.put("MAC Address1", WebportalParam.ap7macaddress);       
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo5);     
          
    }
    
    
    @Step("Test Step 4: Go to location dashboard to check device count;")
    public void step4() {
       
        assertTrue(new HamburgerMenuPage().checkDeviceCount(WebportalParam.location1, WebportalParam.Organizations),"device count not matches");          
    }  
    }


    
  