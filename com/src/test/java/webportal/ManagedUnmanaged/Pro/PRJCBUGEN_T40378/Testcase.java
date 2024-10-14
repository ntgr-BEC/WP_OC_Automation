package webportal.ManagedUnmanaged.Pro.PRJCBUGEN_T40378;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    int temp;
    int temp1;
    int temp2;
    int                 devNum              = 1;

    @Feature("ManagedUnmanaged") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40378") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[PRO]Test to verify the add one NHB device and verify the toggle button its disabled.")
    @TmsLink("PRJCBUGEN_T40378") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DevicesDashPage(false).deleteDevice1(WebportalParam.ap9serialNo);
        MyCommonAPIs.sleepi(5);
        HashMap<String, String> creditsInfo1 = new HamburgerMenuPage().getCreditAllocationStatus(WebportalParam.Organizations);
        if (creditsInfo1.get("Used Devices Credits").equals("4") && creditsInfo1.get("Unused Devices Credits").equals("1")) {
            new HamburgerMenuPage().deallocateCredit(WebportalParam.Organizations, String.valueOf(devNum), "0");
        } else if (creditsInfo1.get("Used Devices Credits").equals("5") && creditsInfo1.get("Unused Devices Credits").equals("2")) {
            new HamburgerMenuPage().deallocateCredit(WebportalParam.Organizations, String.valueOf(devNum+1), "0");
        } else {
            new HamburgerMenuPage().deallocateCredit(WebportalParam.Organizations, String.valueOf(devNum+2), "0");
        }
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2:Verify Managed Unmanaged switch on device dash page is there or not;")
    public void step2() {
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.creditAllocation11(WebportalParam.Organizations);
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap9serialNo);
        devInfo.put("Device Name", WebportalParam.ap9deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap9macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
        
        MyCommonAPIs.sleepi(5);
        assertTrue(new DevicesDashPage(false).verifyManageUnmanageswiutchIsthereafternewdeviceonboard(WebportalParam.ap9serialNo),"Test case not passed successfully");
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(WebportalParam.Organizations);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("5") && creditsInfo.get("Unused Devices Credits").equals("0"),
                "Allocate credits error.");
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap9serialNo));
        MyCommonAPIs.sleepi(5);
        
        HashMap<String, String> creditsInfo1 = new HamburgerMenuPage().getCreditAllocationStatus(WebportalParam.Organizations);
        assertTrue(
                creditsInfo1.get("Used Devices Credits").equals("4") && creditsInfo1.get("Unused Devices Credits").equals("1"),
                "Allocate credits error.");
    }
}
