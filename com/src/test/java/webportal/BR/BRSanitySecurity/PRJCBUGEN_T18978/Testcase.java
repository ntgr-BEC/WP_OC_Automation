package webportal.BR.BRSanitySecurity.PRJCBUGEN_T18978;

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
    String groupName = "VpnGroupT1897x";

    @Feature("BR.SecuritySanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Stories({ @Story("PRJCBUGEN_T18977"), @Story("PRJCBUGEN_T18978") }) // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add / Delete BR to VPN Group") // It's a testcase title from Jira Test Case.
    @TmsLinks({ @TmsLink("PRJCBUGEN-T18977"), @TmsLink("PRJCBUGEN-T18978") }) // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: 4. Click On ADD device on Existing Group and Select the device ")
    public void step2() {
        brdvgp.gotoPage();
        brdvgp.createVpnGroupWithDevices(groupName);
        assertTrue(brdvgp.getDevices(groupName).size() > 0, "devices are not added to group");
    }
    
    @Step("Test Step 3: 4. Click On Device and Select Remove Device ")
    public void step3() {
        brdvgp.gotoPage();
        brdvgp.deleteDevices(groupName);
        assertTrue(brdvgp.getDevices(groupName).size() == 0, "devices are not removed from group");
    }
    
}
