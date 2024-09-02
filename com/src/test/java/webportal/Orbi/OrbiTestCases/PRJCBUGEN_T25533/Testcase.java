package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25533;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

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
import webportal.webelements.DevicesDashPageElements;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiDHCPServersPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiNetworkSetupPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author ann.fang
 */
public class Testcase extends TestCaseBase {
    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25533") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify creating a DHCP server from the DHCP server page")
    @TmsLink("PRJCBUGEN-T25533") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ddp.gotoPage();
        ddp.openOB2();
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        if(!page.isRouterMode()) {
            page.setDeviceMode(false);
            MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: If device is router mode, change it to AP mode")
    public void step2() {
        ddp.openOB2();
        
    }
    
    @Step("Test Step 3: Change to router to AP mode")
    public void step3() {
        
        DevicesOrbiNetworkSetupPage page1 = new DevicesOrbiNetworkSetupPage();
        page1.gotoPage();
        page1.createNetwork("new vlan", "100", "192.168.100.1");
        
        boolean addvlan = false;
        for (int i = 0; i < 10; i++) {
            MyCommonAPIs.sleepi(60);
            page1.gotoPage();
            String oristr = page1.getNetworks().get(0);
            String lines[] = oristr.split("\\r?\\n");

            for (String str : lines) {
                if (str.equals("new vlan")) {
                    addvlan = true;
                    break;
                }
            }
            if (addvlan)
                break;
        }
        assertTrue(addvlan,"Check if vlan is added");

        boolean dhcpserver = false;
        DevicesOrbiDHCPServersPage page2 = new DevicesOrbiDHCPServersPage();
        page2.gotoPage();

        for (int i = 0; i < 10; i++) {
            MyCommonAPIs.sleepi(60);
            page2.gotoPage();

            for (String str : page2.getListIP()) {
                if (str.equals("192.168.100.1")) {
                    dhcpserver = true;
                    break;
                }
            }
            if (dhcpserver)
                break;

        }
        assertTrue(dhcpserver,"Check if vlan is added");
        
    }
}
