package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41171;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String vlanName = "testvlan";
    String tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "1.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";
    String oldMask;

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41171") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("024-Test IP mask of manual allow policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41171") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create vlan50 on switch")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​​​​​--Allow,source ip 11.1.1.1,ip mask 0.0.0.255")
    public void step2() {
        wvp.ipFilterIpMask = "0.0.0.255";
        oldMask = wvp.ipFilterIpMask;
        wvp.ipFilterMacOpt = 2;
        wvp.enaIpFilterIpMask = true;
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Generate 1 IP acl on switch:\n" + "--Allow,source ip 11.1.1.1,ip mask 0.0.0.255")
    public void step3() {
        handle.waitCmdReady(ipaclIp, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(wvp.ipFilterIpMask));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit IP mask to 0.0.0.0")
    public void step4() {
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.ipFilterIpMask = "0.0.0.0";
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: ​​​​​​​​​​check the display on webportal and device")
    public void step5() {
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.showIpRangeMask();
        assertEquals(wvp.ipRangeMask.getValue(), wvp.ipFilterIpMask);
        wvp.ipMAMDeviceCancel.click();

        handle.waitCmdReady(oldMask, true);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s", ipaclIp)));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Edit IP mask to 255.255.255.255")
    public void step6() {
        Selenide.refresh();
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.ipFilterIpMask = "255.255.255.255";
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 7: ​​​​​​​​​​check the display on webportal and device")
    public void step7() {
        Selenide.refresh();
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleepi(5);
        wvp.openIpFilterMacAuth(ipaclIp, true);
        wvp.showIpRangeMask();
        assertEquals(wvp.ipRangeMask.getValue(), wvp.ipFilterIpMask);
        wvp.ipMAMDeviceCancel.click();

        handle.waitCmdReady(wvp.ipFilterIpMask, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(wvp.ipFilterIpMask));
    }

}
