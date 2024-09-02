package webportal.InstantMesh.PRJCBUGEN_T14586;

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
    @Story("PRJCBUGEN_T14586") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Mesh Plot with three APs should be diagonally placed.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14586")// It's a testcase id/link from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14537")// It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        boolean checkApmodel = false;
        if (WebportalParam.ap1Model.contains("540") || WebportalParam.ap1Model.contains("564")|| WebportalParam.ap1Model.contains("610") || WebportalParam.ap1Model.contains("610Y")|| WebportalParam.ap1Model.contains("620")  || WebportalParam.ap1Model.contains("630")) {
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

    @Step("Test Step 2: three APs should be vertically placed")
    public void step2() {
        ddp.gotoPage();
        assertTrue(ddp.getDevicesNo("WAC").size() > 2, "Must have at least 3 AP");
        ddp.checkDUT4InstantMesh();
        
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        DevicesApMeshTopologyPage page = new DevicesApMeshTopologyPage();
        
        page.setMeshViewMode(1);
        assertTrue(page.getAPNameGraph().size() > 2, "Check graph for ap graph");
        page.setMeshViewMode(0);
        assertTrue(page.getAPNameList().size() > 2, "Check graph for ap list");
    }
}
