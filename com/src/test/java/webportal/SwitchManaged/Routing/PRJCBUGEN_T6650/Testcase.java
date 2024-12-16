package webportal.SwitchManaged.Routing.PRJCBUGEN_T6650;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "150";
    String mask     = "255.255.255.0";
    String ip1      = "100.1.1.1";
    String ip2      = "100.2.2.1";

    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6650") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("027-Try to save configurations with no mask/no ip address for one switch/no ip address for one of the siwtches") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6650") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

//        wvp.gotoPage();
//        wvp.newVlan(vlanName, vlanId, 0);

        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }

    @Step("Test Step 2: Add ip addrss for the two siwtches")
    public void step2() {
        rtp.deleteVlanRoute(vlanId);
        rtp.addIpToVlan(vlanName, "", ip1, ip2);
        new MyCommonAPIs().sleepi(4);     
        assertTrue(handle.getPageErrorMsg().contains("enter subnet"), "mask");      
        // rtp.addIpToVlan(vlanId, mask, "", "");
        // assertTrue(handle.getPageErrorMsg().contains("Enter a valid IP Address"), "no ip");
        rtp.addIpToVlan1(vlanName, mask, "", ip2);
        new MyCommonAPIs().sleepi(4);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid ip address"), "ip one");
//        rtp.addIpToVlan(vlanId, "", "", ip2);
//        assertTrue(handle.getPageErrorMsg().contains("enter subnet"), "mask");
    }

}
