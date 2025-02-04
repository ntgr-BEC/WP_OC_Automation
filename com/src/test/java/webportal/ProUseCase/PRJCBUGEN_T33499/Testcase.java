package webportal.ProUseCase.PRJCBUGEN_T33499;

import static org.testng.Assert.assertFalse;
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
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;

/**
 *
 * @author Vivek
 *
 */
public class Testcase extends TestCaseBase {

    Random r                = new Random();
    int    num              = r.nextInt(10000);
    String mailname         = "case3" + String.valueOf(num);
    String organizationName = "Netgear";
    String mailid           = mailname + "@yopmail.com";
    String password         = "Netgear1@";

    @Feature("ProUseCase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33499") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify create pro account using #register url is working") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33499") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: open the #register url and fill the pro account info")
    public void step1() {
        new HamburgerMenuPage(false).openHashRigisterUrlAndOpenProInfoForm(mailid);
        Map<String, String> proAccountInfo = new HashMap<String, String>();
        proAccountInfo.put("Confirm Email", mailid);
        proAccountInfo.put("Password", "Netgear1@");
        proAccountInfo.put("Confirm Password", "Netgear1@");
        proAccountInfo.put("Country", "United States of America");
        proAccountInfo.put("Phone Number", "0000000000");
        new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);
        new HamburgerMenuPage(false).prologinaftercreatingnewpro(mailid, password);
        
    }

    @Step("Test Step 2: Enter the Liecnce Key and fill the business form")
    public void step2() {
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
        businessInfo.put("Business Name", "Netgear1");
        businessInfo.put("Primary Address of Business", "Strandgatan");
        businessInfo.put("City", "Visby");
        businessInfo.put("State", "Visby");
        businessInfo.put("Zip Code", "62156");
        businessInfo.put("Country", "Sweden");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);
        
    }

    @Step("Test Step 3: Create a organization and location;")
    public void step3() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        assertTrue(OrganizationPage.checkOrganizationIsExist(organizationName), "Organization not existed.");
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", WebportalParam.location2);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();

    }


    @Step("Test Step 4: Allocate Credits ;")
    public void step4() {
        new HamburgerMenuPage().openAccountMgmt();
        new HamburgerMenuPage(false).OpenCreditAllocationPage();
        new HamburgerMenuPage(false).clickCreditsPlus(1, 0, 0);
        new HamburgerMenuPage(false).clickOnAllocateButtonforsave();
    }

    @Step("Test Step 5: Onboarding a dummy device;")
    public void step5() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        new DevicesDashPage(false).addNewdummyDeviceProAccount(firststdevInfo);
    }

}
