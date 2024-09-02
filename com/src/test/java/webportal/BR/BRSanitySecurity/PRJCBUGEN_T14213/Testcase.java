package webportal.BR.BRSanitySecurity.PRJCBUGEN_T14213;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String groupName = "VpnGroupT14213";

    @Feature("BR.SecuritySanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Stories({ @Story("PRJCBUGEN_T14213"), @Story("PRJCBUGEN_T14354") }) // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify using Webportal user able to create/delete a  VPN Group - In Premium Account") // It's a testcase title from Jira Test Case.
    @TmsLinks({ @TmsLink("PRJCBUGEN-T14213"), @TmsLink("PRJCBUGEN-T14354") }) // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: We will get \"Create VPN  Group\" pop-up , now give the VPN group name and click on save")
    public void step2() {
        brdvgp.gotoPage();
        brdvgp.createVPNGroup(groupName);
        assertTrue(brdvgp.getGroups().contains(groupName), "User should be able to create VPN group successfully without any issues");
    }

    @Step("Test Step 3: In the created VPN group  click on the Delete icon in the VPN Group page")
    public void step3() {
        brrp.gotoPage();
        brrp.deleteGroup(groupName);
        brdvgp.gotoPage();
        assertTrue(!brdvgp.getGroups().contains(groupName), "User should be able to delete the created VPN Group");
    }
}
