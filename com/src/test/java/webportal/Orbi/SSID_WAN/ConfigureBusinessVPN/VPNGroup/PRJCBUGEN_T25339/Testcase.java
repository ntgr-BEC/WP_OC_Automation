package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VPNGroup.PRJCBUGEN_T25339;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.VPNGroup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25339") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the Delete VPN group.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25339") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User can add a VPN group")
    public void step2() {
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25339", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl(WebportalParam.client2name, WebportalParam.client2wifimac, 0, WebportalParam.ob1deveiceName);
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.sleepi(10); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); //wait 5 mins to let business vpn finish setting up
        page.gotoPage();
        if(!page.GroupExist("TestGroup", "PRJCBUGEN-T25339", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    @Step("Test Step 3: Delete the VPN group and it is not shown in the table")
    public void step3() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.DelVPNGroup("TestGroup");
        checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T25339", WebportalParam.ob1deveiceName);
        assertFalse(checkpoint, "checkpoint 1 : deleted group does not exist");
    }
}
