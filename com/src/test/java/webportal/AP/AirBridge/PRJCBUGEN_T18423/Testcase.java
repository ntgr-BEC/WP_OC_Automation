package webportal.AP.AirBridge.PRJCBUGEN_T18423;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessAirBridgeGroupsPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String abGroupName     = "grp18423";
    String newLocationName = String.format("T18423L%s", new Random().nextInt(999));
    
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18423") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the air bridge group should not be deleted upon deleting network location for a premium account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18423") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        AccountPage AccountPage = new AccountPage();
        AccountPage.deleteLocation(newLocationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

    }

    @Step("Test Step 2: Create an account and create network locations")
    public void step2() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", newLocationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
    }

    @Step("Test Step 3: Click on air bridge groups and then click on the plus icon to add new groups")
    public void step3() {
        handle.gotoLoction(newLocationName);
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.createGroup(abGroupName, false);
    }
    
    @Step("Test Step 4: Now, delete the network location")
    public void step4() {
        AccountPage AccountPage = new AccountPage();
        AccountPage.deleteLocation(newLocationName);
        handle.gotoLoction();
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        assertTrue(wabg.getGroups().contains(abGroupName), "verify group still exists in " + abGroupName);
    }

}
