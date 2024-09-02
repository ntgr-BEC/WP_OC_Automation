package webportal.InstantMesh.PRJCBUGEN_T15353;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("InstantMesh") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15353") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Preferred backhaul can be configured for the Extender AP") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15353") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");

        new DevicesDashPage().enterDevice(WebportalParam.ap3serialNo);
        new DevicesApMeshTopologyPage().clickMeshExtenderAp();
        Map<String, String> meshConfigInfo = new HashMap<String, String>();
        meshConfigInfo.put("Root Ap Selection", "Auto");
        new DevicesApMeshTopologyPage().setRootApSelectionConfiguration(meshConfigInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap3serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap2serialNo);
        devInfo.put("Device Name", WebportalParam.ap2deveiceName);
        new DevicesDashPage().addNewDevice(devInfo);
        
        new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));
    }

    @Step("Test Step 2: Check root ap selection setting;")
    public void step2() {
        if (new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")
                && new DevicesDashPage().getDeviceName(WebportalParam.ap2serialNo).equals("")
                && new DevicesDashPage().getDeviceName(WebportalParam.ap3serialNo).equals("")) {
            assertTrue(false, "Need add two root ap for AP1 or AP2 and add one extender ap for AP3.");
        }

        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Root Only");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        new DevicesDashPage().enterDevice(WebportalParam.ap2serialNo);
        new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Root Only");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);

        new DevicesDashPage().enterDevice(WebportalParam.ap3serialNo);
        new DevicesApMeshTopologyPage().setAdvanceMeshModeConfiguration("Extender Only");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap3serialNo);

        new DevicesDashPage().enterDevice(WebportalParam.ap3serialNo);
        new DevicesApMeshTopologyPage().clickMeshExtenderAp();

        Map<String, String> meshConfigInfo = new HashMap<String, String>();
        meshConfigInfo.put("Root Ap Selection", "Manual");
        meshConfigInfo.put("Primary Preferred Root Device", WebportalParam.ap1deveiceName);
        meshConfigInfo.put("Primary Preferred Backhaul", "2.4 GHz");
        meshConfigInfo.put("Secondary Preferred Root Device", WebportalParam.ap2deveiceName);
        meshConfigInfo.put("Secondary Preferred Backhaul", "5 GHz high");
        new DevicesApMeshTopologyPage().setRootApSelectionConfiguration(meshConfigInfo);
        // MyCommonAPIs.sleepi(3 * 60);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap3serialNo);
        new DevicesDashPage().enterDevice(WebportalParam.ap3serialNo);

        assertTrue(new DevicesApMeshTopologyPage().checkExtenderApChNumAndRootAp(WebportalParam.ap1deveiceName, "2.4"),
                "Root ap selection is incorrect.");
    }

}
