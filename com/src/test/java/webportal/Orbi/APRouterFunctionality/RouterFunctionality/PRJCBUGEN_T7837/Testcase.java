package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7837;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.weboperation.OrbiDebugSettingsPage;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.DevicesOrbiDHCPServersPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiLanIPPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    boolean needrestore  = false;
    boolean originalmode = false;
    
    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7837") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user is able to change IP from static to DHCP successfully in router mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7837") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            new WebportalLoginPage(true).defaultLogin();
            handle.gotoLoction();
            ddp.gotoPage();
            ddp.openOBDevice();
            DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage();
            page.setDHCP(true, false);
            MyCommonAPIs.sleepsyncorbi();
        } catch (Throwable e) {
            System.out.println("Failed to restore");
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        
        //OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
        //page.OrbibaseEnableTelenet(WebportalParam.ob2IPaddress, WebportalParam.loginDevicePassword);
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }
    
    @Step("Test Step 3: To disable dhcp server")
    public void step3() {
        
        if(WebportalParam.ob2Model.contains("SRR")) {
            DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage();
            page.setDHCP(false, false);
        }
        else {
            DevicesOrbiDHCPServersPage page = new DevicesOrbiDHCPServersPage();
            page.gotoPage();
            page.setDHCP("1", false);
        }
        
        MyCommonAPIs.sleepsyncorbi(); // change to orbi sync
        UserManage userManage = new UserManage();
        userManage.logout();
        
        handle.waitRestReady(BRUtils.api_lan_ip_settings, "false", false, 1);
        assertTrue(new BRUtils(BRUtils.api_lan_ip_settings, 1).getField("dhcpEnabled").contains("false"), "dhcp should be disabled");
    }
}
