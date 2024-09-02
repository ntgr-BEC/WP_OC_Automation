package webportal.InstantMesh.PRJCBUGEN_T14580;

import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Lavi
 */
public class Testcase extends TestCaseBase {
    String  aAP      = null;
    String  aMac      = null;
    
    boolean toRemove = false;

    @Feature("InstantMesh") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14580") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Mesh device Onboarding flow has a message with \"learn more\" link at the end.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14580") // It's a testcase id/link from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14531") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        boolean checkApmodel = false;
        if (WebportalParam.ap1Model.contains("540") || WebportalParam.ap1Model.contains("564") || WebportalParam.ap1Model.contains("610")  || WebportalParam.ap1Model.contains("620")|| WebportalParam.ap1Model.contains("620")  || WebportalParam.ap1Model.contains("630")) {
            checkApmodel = true;
        }
        assertTrue(checkApmodel, "Device must be in 540/564");
        aAP = handle.getFakeDeviceNo("WAC564");
        aMac = new WirelessQuickViewPage().getRandomMacAddress();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        if (toRemove) {
            ddp.gotoPage();
            ddp.deleteDeviceYes(aAP);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2:  a \"Learn More\" link")
    public void step2() {
        ddp.gotoPage();
        
        ddp.AddNewDevice(aAP, aMac);
        toRemove = true;
        assertTrue(ddp.lnLearnMore.exists(), "a \"Learn More\" link");
    }

}
