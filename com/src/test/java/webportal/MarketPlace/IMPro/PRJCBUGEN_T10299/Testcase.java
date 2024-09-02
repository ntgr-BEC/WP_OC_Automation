package webportal.MarketPlace.IMPro.PRJCBUGEN_T10299;

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
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    String vpnGroupName = "test10299";

    @Feature("MarketPlace.IMPro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10299") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that if user(admin) does not have sufficient VPN subscription to create Site to Site VPN tunnel, flag an error, and redirect to Market place to buy the VPN subscription") // It's
                                                                                                                                                                                                    // a
                                                                                                                                                                                                    // testcase
    // title from Jira
    // Test Case.
    @TmsLink("PRJCBUGEN-T10299") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        brrp.deleteGroup(vpnGroupName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Verify that if user(admin) does not have sufficient VPN subscription to create Site to Site VPN tunnel;")
    public void step2() {
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.br2serialNo);
                        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        brrp.gotoPage();
        brrp.createVPNGroup(vpnGroupName);
        MyCommonAPIs.sleepi(10);
        assertTrue(brrp.checkVpnGroupNumLimit("admin"), "Vpn group credits is incorrect.");
    }
}
