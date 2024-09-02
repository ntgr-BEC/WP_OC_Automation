package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7859;

import static org.testng.Assert.assertTrue;

import java.util.List;

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
    @Story("PRJCBUGEN_T7859") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[LAN IP] Test to verify the user can change the assigned IP address and save successfully.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7859") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")

    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        try {
//            new WebportalLoginPage(true).defaultLogin();
//            handle.gotoLoction();
//            ddp.gotoPage();
//            ddp.openOBDevice();
//            DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage();
//            page.setDHCP(true, false);
//        } catch (Throwable e) {
//            System.out.println("Failed to restore");
//        }
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

    @Step("Test Step 3: verify the user can change the assigned IP address and save successfully.")
    public void step3() {
        List<String> toret;
        if(WebportalParam.ob2Model.contains("60")) {
            DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage();
            toret = page.changeDHCPPool();
        }else {
            DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage(false);
            DevicesOrbiDHCPServersPage page2 = new DevicesOrbiDHCPServersPage();
            page2.gotoPage();
            page2.openOne("1");
            toret = page.changeDHCPPool();
        }
        
        MyCommonAPIs.sleepsync();
        UserManage userManage = new UserManage();
        userManage.logout();

        handle.waitRestReady(BRUtils.api_lan_ip_settings, toret.get(0), false, 1);
        String result = new BRUtils(BRUtils.api_lan_ip_settings, 1).Dump();
        assertTrue(result.contains(toret.get(0)), "check dhcp ip start");
        assertTrue(result.contains(toret.get(1)), "check dhcp ip end");
    }
}
