package webportal.BR.BRSanitySecurity.PRJCBUGEN_T14215;

import static org.testng.Assert.assertTrue;

import org.apache.poi.util.SystemOutLogger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String groupName = "VpnGroupT14215";

    @Feature("BR.SecuritySanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14215") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user able to delete BR500 Device from the added VPN Group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14215") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        brrp.gotoPage();
        brrp.deleteGroup(groupName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        
        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Once the VPN tunnel get established")
    public void step2() {
        brdvgp.gotoPage();
        brdvgp.createVpnGroupWithDevices(groupName);
//        brdvgp.waitLinkHealthy(groupName);
    }

    @Step("Test Step 3: Now delete  one of the BR500 device in the group")
    public void step3() {
        brdvgp.gotoPage();
        int devCount = brdvgp.getDevices(groupName).size();
        System.out.print(devCount);
        brdvgp.deleteOneDevice(groupName);
//        assertTrue(brdvgp.getDevices(groupName).size() != devCount, "User should be able to delete the BR Device from VPN Group ");
    }
    
}
