package webportal.SearchFilterChooseColumn.ProuseR.PRJCBUGEN_T14656;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("SearchFilterChooseColumn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14656") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Search/ Filter/ Choose column functionality on the notifications section") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14656") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check notifications filter;")
    public void step2() {
//        new DevicesDashPage().deleteDeviceYes(WebportalParam.sw1serialNo);
//        new DevicesDashPage().deleteDeviceYes(WebportalParam.ap1serialNo);

        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
//        new DevicesDashPage().addNewDevice(devInfo);



        devInfo.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo.put("MAC Address1", WebportalParam.sw1MacAddress);
//        new DevicesDashPage().addNewDevice(devInfo);



        evtp.gotoPage();
        evtp.filterDropdown();
        evtp.selectDevice("Switch");    // select only switch

        boolean result = true;

        if (evtp.checkDeviceTypeInList("SW") && !evtp.checkDeviceTypeInList("AP")) {
            System.out.println("1st");
            evtp.filterDropdown();
            evtp.selectDevice("Switch");
            MyCommonAPIs.sleepi(3);
            evtp.filterDropdown();
            evtp.selectDevice("Access Point");    //select only Access point
            if (!evtp.checkDeviceTypeInList("SW") && evtp.checkDeviceTypeInList("AP")) {
                System.out.println("2st");
                evtp.filterDropdown();
                evtp.selectDevice("Access Point");
                MyCommonAPIs.sleepi(3);
                evtp.filterDropdown();
                evtp.selectDevice("Switch");       //select only switch again
                if (evtp.checkDeviceTypeInList("SW") && !evtp.checkDeviceTypeInList("AP")) {
                } else {
                    result = false;
                }
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        assertTrue(result, "Notifications filter is incorrect.");
    }

}
