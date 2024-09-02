package webportal.BR.BRIPManagement.PRJCBUGEN_T7195;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sExpect = "192.168.168.1";
    
    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7195") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("009-Change LAN1 IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7195") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.refresh();
        brddchps.gotoPage();
        brddchps.setDHCP("1", WebportalParam.br1IPaddress, true);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }
    
    @Step("Test Step 2: Go to BR local GUI, change LAN1 IP address, click apply;")
    public void step2() {
        new BRUtils().setLANIp(sExpect, false);
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 3: Config success, any changes can sync to Cloud, checked by Insight;")
    public void step3() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
        handle.openOneBRDevice(true);
        brddchps.gotoPage();
        brddchps.openOne("1");
        assertTrue(MyCommonAPIs.getValue(brddchps.txtIp).contains(sExpect), "dhcp ip is not changed");
    }
}
