package webportal.InstantMesh.PRJCBUGEN_T14578.copy;

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
import webportal.weboperation.DevicesApMeshTopologyPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author Lavi
 */
public class Testcase extends TestCaseBase {

    @Feature("InstantMesh") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14578") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the List view has that Mesh AP on top, from where the user has navigated to the Mesh page.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14578") // It's a testcase id/link from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14529") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        boolean checkApmodel = false;
        if (WebportalParam.ap1Model.contains("540") || WebportalParam.ap1Model.contains("564") || WebportalParam.ap1Model.contains("620") || WebportalParam.ap1Model.contains("630")|| WebportalParam.ap1Model.contains("610")|| WebportalParam.ap1Model.contains("610Y")) {
            checkApmodel = true;
        }
        assertTrue(checkApmodel, "Device must be in 540/564");
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: We will be able to see that the List view has Mesh AP on top")
    public void step2() {
        ddp.gotoPage();
        ddp.checkDUT4InstantMesh();
        
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        DevicesApMeshTopologyPage page = new DevicesApMeshTopologyPage();
        
        page.setMeshViewMode(0);
        assertTrue(page.getDevicesInfo().get(0).contains("NA"), "Link for root AP should be NA");
        assertTrue(page.getDevicesInfo().get(0).contains("2.4"), "root ap should has 2.4GHz channel");
    }

}
