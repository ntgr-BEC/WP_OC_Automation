package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VPNGroup.PRJCBUGEN_T26160;

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
    @Story("PRJCBUGEN_T26160") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify FAQ & troubleshooting option clickable and open a Netgear support site or not..") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26160") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        
    }

    
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / Add a group with domain name")
    public void step2() {
        String url;
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        
        if (page.faqbutton.exists()) {
            url = page.faqbutton.getAttribute("href");
            checkpoint = url.contains("kb.netgear.com");
            assertTrue(checkpoint,"checkpoint 1 : FAQ url was correct.");
        } 
        
    }
    
    
}
