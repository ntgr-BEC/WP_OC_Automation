package webportal.SwitchManaged.ACL.PRJCBUGEN_T4987;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String        vlanName = "testvlan";
    String        tmpStr;

    String ipaclName = "test-025";
    String ipaclIp   = "13.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4987") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("025-Edit device name,ip address,ip mask for manual ip allow policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4987") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​​​​​--Allow,source ip 13.1.1.1,ip mask 255.255.255.255 Option: access allow from this device")
    public void step2() {
        wvp.ipFilterIpMask = "255.255.255.255";
        wvp.ipFilterMacOpt = 2;
        wvp.enaIpFilterIpMask = true;
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​​​​​The policy deploy to the device")
    public void step3() {
        handle.waitCmdReady(ipaclIp, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(wvp.ipFilterIpMask), "verify the mask is set");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: device name:test-025-new ip address:100.1.1.100 ip mask:0.0.255.255")
    public void step4() {
        wvp.openIpFilterMacAuth(ipaclIp, true);
        ipaclName = "test-025-new";
        ipaclIp = "100.1.1.100";
        wvp.ipFilterIpMask = "0.0.255.255";
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
//    @Step("Test Step 5: ​​​​​​​​​​The info display correctly on app")
//    public void step5() {
//        wvp.openIpFilterMacAuth(ipaclIp, true);
//        wvp.showIpRangeMask();
//        assertEquals(wvp.ipMAMDeviceName.getValue(), ipaclName, "Verify acl name");
//        assertEquals(wvp.ipMAMDeviceIp.getValue(), ipaclIp, "Verify ip");
//        assertEquals(wvp.ipRangeMask.getValue(), wvp.ipFilterIpMask, "Verify ip Mask");
//        wvp.ipMAMDeviceCancel.click();
//    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Allow,source ip 100.1.1.100/0.0.255.255")
    public void step6() {
        handle.waitCmdReady(wvp.ipFilterIpMask, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(ipaclIp), "new ip on device");
        assertTrue(tmpStr.contains(wvp.ipFilterIpMask), "new ip mask on device");
    }

}
