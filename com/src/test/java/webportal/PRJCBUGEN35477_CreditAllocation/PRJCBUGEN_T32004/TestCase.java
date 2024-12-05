package webportal.PRJCBUGEN35477_CreditAllocation.PRJCBUGEN_T32004;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import java.util.Random;

public class TestCase extends TestCaseBase {
    String  organizationName  = "Netgear";
    String  locationName      = "office";
    String  warning           = "Cannot add devices from CSV file. Use CSV template column headings, and save file in .CVS format.";
    boolean micResult         = false;

    @Feature("PRJCBUGEN35477_CreditAllocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32004") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create Multiple locatOrganizationsion Pro") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T32004") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority

    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Create two organizations and allocate credits")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.creditAllocation(organizationName);
    }

    @Step("Test Step 3: Create new location;")
    public void step3() {
        new OrganizationPage(false).openOrg(organizationName);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);

    }

    @Step("Test Step 4: Add 5 dummy non hardbundle device To the Network;")
    public void step4() {
        new OrganizationPage(false).openOrg(organizationName);
        new AccountPage(false).enterLocation("OnBoardingTest");

        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        System.out.println(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

        Map<String, String> firststdevInfo1 = new HashMap<String, String>();
        firststdevInfo1.put("Serial Number1", WebportalParam.ap6serialNo);
        firststdevInfo1.put("MAC Address1", WebportalParam.ap6macaddress);
        System.out.println(firststdevInfo1);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);

        Map<String, String> firststdevInfo2 = new HashMap<String, String>();
        firststdevInfo2.put("Serial Number1", WebportalParam.ap7serialNo);
        firststdevInfo2.put("MAC Address1", WebportalParam.ap7macaddress);
        System.out.println(firststdevInfo2);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo2);

        Map<String, String> firststdevInfo3 = new HashMap<String, String>();
        firststdevInfo3.put("Serial Number1", WebportalParam.ap8serialNo);
        firststdevInfo3.put("MAC Address1", WebportalParam.ap8macaddress);
        System.out.println(firststdevInfo3);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo3);

        Map<String, String> firststdevInfo4 = new HashMap<String, String>();
        firststdevInfo4.put("Serial Number1", WebportalParam.ap9serialNo);
        firststdevInfo4.put("MAC Address1", WebportalParam.ap9macaddress);
        System.out.println(firststdevInfo4);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo4);
        
        Map<String, String> firststdevInfo5 = new HashMap<String, String>();
        firststdevInfo5.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo5.put("MAC Address1", WebportalParam.ap1macaddress);
        System.out.println(firststdevInfo5);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo5);

    }
    
    @Step("Test Step 5: Verify device count on location dashboard and organization dashboard;")
    public void step5() {
        
        new OrganizationPage(false).openOrg(organizationName);
        assertTrue(new OrganizationPage(false).verifyDevicesCountonLocationTab(),"Devices count not showing correctly on location logo dashboard");
        assertTrue(new OrganizationPage(false).verifyDevicesCountonOrganizationTab(),"Devices count not showing correctly on Organization logo dashboard");
    }

}
