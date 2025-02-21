package webportal.FlexibleProSubscription.PRJCBUGEN_T16021;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String>     devInfo          = new HashMap<String, String>();
    Map<String, String>     devInfoNew       = new HashMap<String, String>();
    HashMap<String, String> locationInfo     = new HashMap<String, String>();
    String                  organizationName = "test16021";
    String                  locationName     = "ntk16021";
    String                  devicesCredits   = "1";

    @Feature("FlexibleProSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16021") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that an error message is displayed if user has used all the credits allocated to the organization") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16021") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        devInfo.put("Serial Number", WebportalParam.ap5serialNo);
        devInfo.put("Device Name", WebportalParam.ap5deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap5macaddress);
        devInfoNew.put("Serial Number", WebportalParam.sw1serialNo);
        devInfoNew.put("Device Name", WebportalParam.sw1deveiceName);
        devInfoNew.put("MAC Address", WebportalParam.sw1MacAddress);
        devInfoNew.put("MAC Address1", WebportalParam.sw1MacAddress);
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

//        new DevicesDashPage().checkDutInNormalAccount("admin", devInfo.get("Serial Number"), devInfo.get("Device Name"), devInfo.get("MAC Address"));
//        new DevicesDashPage().checkDutInNormalAccount("admin", devInfoNew.get("Serial Number"), devInfoNew.get("Device Name"), devInfoNew.get("MAC Address"));
    }

    @Step("Test Step 2: Add new organization and add devices, then check devices credits limit;")
    public void step2() {
//        new DevicesDashPage().deleteDeviceYes(devInfo.get("Serial Number"));
//        new DevicesDashPage().deleteDeviceYes(devInfoNew.get("Serial Number"));

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.addAllocateCredits(organizationName, devicesCredits);
        OrganizationPage.openOrg(organizationName);

        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

        new AccountPage(false).enterLocation(locationName);
        MyCommonAPIs.sleepi(10);
        new DevicesDashPage().addNewDevice(devInfo);
        MyCommonAPIs.sleepi(10);
//        new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));

        assertTrue(new DevicesDashPage().checkDevicesCredits(devInfoNew).contains("you need to allocate more device credits"),
                "Credits limit error.");
    }

}
