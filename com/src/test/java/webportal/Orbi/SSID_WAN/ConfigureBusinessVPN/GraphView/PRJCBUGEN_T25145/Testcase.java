package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.GraphView.PRJCBUGEN_T25145;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.GraphView") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25145") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Device status shown in Business VPN grap view page is proper") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25145") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        // restore : click list view and delete the vpn group
        if(page.cirbtnListView.exists()) {
            page.cirbtnListView.click();
            util.MyCommonAPIs.waitReady();
        } 
        page.DelVPNGroup("TestGroup");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / Create VPN group / Change to graph view / Check central router and remoter are connected to Insight")
    public void step2() {
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true,"SSID-WAN-Auto-Test",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady(); // wait business vpn group setup successfully
        util.MyCommonAPIs.sleep(5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.waitReady();
        page.cirbtnGraphView.click();
        util.MyCommonAPIs.waitReady();
        checkpoint = page.graphCentralCir.getAttribute("stroke").toUpperCase().equals("#00D76F");
        assertTrue(checkpoint,"checkpoint 1 : central router is connected to Insight");
        checkpoint = page.graphRemote1Cir.getAttribute("stroke").toUpperCase().equals("#00D76F");
        assertTrue(checkpoint,"checkpoint 2 : remote router is connected to Insight");
        
        util.MyCommonAPIs.sleepsyncorbi();
    }
}
