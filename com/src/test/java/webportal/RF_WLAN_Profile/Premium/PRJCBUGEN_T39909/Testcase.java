package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39909;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> RFdata                  = new HashMap<String, String>();
    Map<String, String> RFdata1                  = new HashMap<String, String>();
    public List<String> ap24BandChannels        = new ArrayList<>();
    public List<String> ap5BandChannels         = new ArrayList<>();
    public List<String> ap6BandChannels         = new ArrayList<>();
    public List<String> rfProfile24BandChannels = new ArrayList<>();
    public List<String> rfProfile5BandChannels  = new ArrayList<>();
    public List<String> rfProfile6BandChannels  = new ArrayList<>();

    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case
    @Story("PRJCBUGEN_T39909") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user can be able to copy the newly created RF Profile configurations by selecting the copy configuration of default RF profile.") // It's
                                                                                                                                                                     // Case.
    @TmsLink("PRJCBUGEN_T39909") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).deleteRF(RFdata.get("RFName"));
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).deleteRF(RFdata1.get("RFName"));
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();

    }

    @Step("Test Step 2: Create RF Profiles and Verify")
    public void step2() {

        RFdata.put("RFName", "Netgear");
        RFdata.put("RFDescription", "BEC Automation Team");
        RFdata.put("Copy Configurations", "Open Office");
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).CreateRFProfile(RFdata);
        assertTrue(new WirelessQuickViewPage(false).checkRFExist(RFdata.get("RFName")),"RF Not created");

    }

    @Step("Test Step 3: Create RF Profiles and Verify")
    public void step3() {

        RFdata1.put("RFName", "Netgear1");
        RFdata1.put("RFDescription", "BEC Automation Team1");
        RFdata1.put("Copy Configurations", "Netgear");
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).CreateRFProfile(RFdata1);
        assertTrue(new WirelessQuickViewPage(false).checkRFExist(RFdata1.get("RFName")),"RF Not created");

    }

}
