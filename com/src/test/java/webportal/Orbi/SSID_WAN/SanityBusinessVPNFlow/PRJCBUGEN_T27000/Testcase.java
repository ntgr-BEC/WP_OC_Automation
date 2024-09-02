package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T27000;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.weboperation.OrbiDebugSettingsPage;
import orbi.weboperation.OrbiLoginPage;
import testbase.TestCaseBase;
import util.Javasocket;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;
import webportal.param.URLParam;
import webportal.publicstep.UserManage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27000") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify whether user able to create VPN group with the Pro VPN license") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27000") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }
    
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        boolean checkpoint;
        new WebportalLoginPage(true).defaultLogin();
        String currentUrl = util.MyCommonAPIs.getCurrentUrl();
        checkpoint = currentUrl.contains(URLParam.hreforganization);
        assertTrue(checkpoint,"checkpoint 1 : the accout is pro account");
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Routers page / User add a VPN group")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "TestDescription", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true, "SSID-WAN-Auto-Test", 1, "12345678");
        page.clickNext();
        page.cirbtnAdd.click();
        page.setPage4AccessControl("DESKTOP-7ETR4H8", "08:be:ac:12:ce:c9", 0, WebportalParam.ob2deveiceName);
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(5000);
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi(); //wait 5 mins to let business vpn finish setting up
        page.gotoPage();
        if(page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy")) {
            util.MyCommonAPIs.sleepsyncorbi();
        }
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "SSID-WAN-Auto-Test", WebportalParam.ob1deveiceName, "Healthy");
        assertTrue(checkpoint,"checkpoint 2 : group exists");
    }
    
}
