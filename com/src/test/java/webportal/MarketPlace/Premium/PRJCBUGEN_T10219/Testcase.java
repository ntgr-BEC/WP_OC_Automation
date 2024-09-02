package webportal.MarketPlace.Premium.PRJCBUGEN_T10219;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    String vpnGroupName = "test10219";

    @Feature("MarketPlace.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10219") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify If user can create VPN groups without buying VPN license from market place") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T10219") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteDeviceYesBR(WebportalParam.br2serialNo);
        brrp.deleteGroup(vpnGroupName);        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Add free vpn group, trying to add the second group Insufficient credit pop up should be displayed.;")
    public void step2() {
        
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.br2serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.br2deveiceMac);
        
                        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        brrp.gotoPage();
        brrp.createVPNGroup(vpnGroupName);
        MyCommonAPIs.sleepi(10);
        assertTrue(brrp.checkVpnGroupNumLimit("close"), "Vpn group credits is incorrect.");
    }

}
