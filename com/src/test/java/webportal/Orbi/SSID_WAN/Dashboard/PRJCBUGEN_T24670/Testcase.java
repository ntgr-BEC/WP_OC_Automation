package webportal.Orbi.SSID_WAN.Dashboard.PRJCBUGEN_T24670;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
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
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.Dashboard") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24670") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Business VPN Client graph is shown properly") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24670") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }

 // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User add two VPN groups and they are shown in the table")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        // create 1st VPN group
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription1", "1400");
        page.setPage2AddCentralRouter(); // OB1
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test1", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        
        //util.MyCommonAPIs.sleep(100000); // wait business vpn group setup successfully
        // check 2 VPN groups are listed
        //checkpoint = page.GroupExist("TestGroup", "SSID-WAN-Auto-Test1", WebportalParam.ob1deveiceName, "Healthy");
        //assertTrue(checkpoint, "checkpoint 1 : group 1 exists");
        page.CheckGroupClinet("TestGroup");
        String ClientUrl =  page.getCurrentUrl();
        assertTrue(ClientUrl.contains("Clients/Clients"),"checkpoint 1: Can go to clients page.");
    }
}
