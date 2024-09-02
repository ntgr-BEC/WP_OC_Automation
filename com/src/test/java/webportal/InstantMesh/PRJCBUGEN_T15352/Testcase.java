package webportal.InstantMesh.PRJCBUGEN_T15352;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApMeshTopologyPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    String sNo = "";

    @Feature("InstantMesh") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15352") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Mode change from No Mesh to Auto") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15352") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");

        new DevicesDashPage().enterDevice(sNo);
        // new DevicesApMeshTopologyPage().clickMeshRootAp();
        new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Root Only");
        new DevicesDashPage().waitDevicesReConnected(sNo);
        new DevicesDashPage().enterDevice(WebportalParam.ap2serialNo);
        new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Root Only");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check mesh ap advance settings;")
    public void step2() {
        if (new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")
                && new DevicesDashPage().getDeviceName(WebportalParam.ap3serialNo).equals("")) {
            assertTrue(false, "Need add one root ap for AP1 and add one extender ap for AP3.");
        }

        new DevicesDashPage().enterDevice(WebportalParam.ap3serialNo);
        sNo = new DevicesApMeshTopologyPage().returnRootApSNo();
        System.out.println(sNo);
        new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Disable Mesh");
        MyCommonAPIs.sleepi(5 * 60);
        new DevicesDashPage().waitDevicesReConnected(sNo);
        new DevicesDashPage().enterDevice(sNo);

        boolean result = true;

        if (new DevicesApMeshTopologyPage().checkMeshStatusAndAdvanceSettings("disable")) {
            new DevicesDashPage().enterDevice(sNo);
            new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Auto");
            new DevicesDashPage().enterDevice(WebportalParam.ap2serialNo);
            new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Disable Mesh");
            MyCommonAPIs.sleepi(5 * 60);
            new DevicesDashPage().waitDevicesReConnected(sNo);
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap3serialNo);
            new DevicesDashPage().enterDevice(sNo);
            new DevicesApMeshTopologyPage().clickMeshRootAp();

            if (new DevicesApMeshTopologyPage().checkMeshStatusAndAdvanceSettings("")) {
            } else {
                result = false;
            }
        } else {
            result = false;
        }

        assertTrue(result, "Mesh ap advance settings is incorrect.");
    }

}
