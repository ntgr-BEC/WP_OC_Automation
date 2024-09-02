package webportal.AP.AirBridge.PRJCBUGEN_T19405;

import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessAirBridgeGroupsPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String newGroupName = "PRJCBUGEN_T19405";
    String latitude     = String.format("%s", new Random().nextInt(99));
    String longitude    = String.format("%s", new Random().nextInt(99));
    
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19405") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if the user is able to manually configure and save the distance for master/satellite") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19405") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: 6. Input the latitude and longitude values for master")
    public void step2() {
        ddp.gotoPage();
        ddp.enterDevice(WebportalParam.ap1serialNo);
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.selectSubDevicePage(1);
        wabg.editDeviceConfiguration(latitude, longitude);
        handle.refresh();
        
        assertTrue(MyCommonAPIs.getValue(wabg.txtConfDeviceLatitude).equals(latitude), "verify latitude");
        assertTrue(MyCommonAPIs.getValue(wabg.txtConfDeviceLongitude).equals(longitude), "verify longitude");
    }

    @Step("Test Step 3: 6. Input the latitude and longitude values for satellite")
    public void step3() {
        ddp.gotoPage();
        ddp.enterDevice(WebportalParam.ap2serialNo);
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.selectSubDevicePage(1);
        wabg.editDeviceConfiguration(latitude, longitude);
        handle.refresh();
        
        assertTrue(MyCommonAPIs.getValue(wabg.txtConfDeviceLatitude).equals(latitude), "verify latitude");
        assertTrue(MyCommonAPIs.getValue(wabg.txtConfDeviceLongitude).equals(longitude), "verify longitude");
    }
}
