package webportal.TargetFirmware.PRJCBUGEN_T40369;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.BRUtils;
import util.XMLManagerForTest;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.FirmwarePage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Anusha H
 */
public class Testcase extends TestCaseBase {

    @Feature("TargetFirmware") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40369") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check whether all the AP models are listed in target firmware page [UI verification]") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40369") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Repeatly do update firmware to check device can be online in cloud")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        
    }
    
    @Step("Test Step 2:Verify the elements ;")
    public void step2() {
        
        new FirmwarePage().gotoFirmwarePage();
        assertTrue(new FirmwarePage().ApModelsInTargetFirmware(), "Some elements are missing in set target FW page");
       
        
    }
} 