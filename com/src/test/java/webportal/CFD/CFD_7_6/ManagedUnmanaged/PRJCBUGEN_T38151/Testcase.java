package webportal.CFD.CFD_7_6.ManagedUnmanaged.PRJCBUGEN_T38151;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {


    @Feature("ManagedUnmanaged") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T38151") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[premium]Test to verify the toggle button its enabled.")
    @TmsLink("PRJCBUGEN_T38151") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        handle.gotoLoction();
        new DevicesDashPage().deleteDevice1(WebportalParam.ap5serialNo);     
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2:Verify Managed Unmanaged switch on device dash page is working fine;")
    public void step2() {
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);
                 
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        assertTrue(new DevicesDashPage().verifyManageUnmanageswiutchIsthereafternewdeviceonboard(WebportalParam.ap5serialNo),"Test case not passed successfully");
    }
}
