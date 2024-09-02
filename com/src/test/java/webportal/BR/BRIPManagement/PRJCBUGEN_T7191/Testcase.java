package webportal.BR.BRIPManagement.PRJCBUGEN_T7191;

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
    String sDNSNew = "8.8.4.4";
    
    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7191") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("005-Configure new DNS server") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7191") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.refresh();
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
        try {
            handle.openOneBRDevice(true);
            brdwip.gotoPage();
            brdwip.setDHCP(true);
            handle.clickButton(0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
    
    @Step("Test Step 2: Go to BR500-->WAN IP page, set a valid DNS server IP address, deploy to APP")
    public void step2() {
        brdwip.gotoPage();
        MyCommonAPIs.getText(brdwip.txtDNS1);
        brdwip.setStatic(sDNSNew, null);
        handle.clickButton(0);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
        
        handle.waitRestReady(BRUtils.api_device_ip_settings, sDNSNew, false, 0);
        assertTrue(new BRUtils().getField("dnsServer").contains(sDNSNew), "device dns1 is not changed");
    }
}
