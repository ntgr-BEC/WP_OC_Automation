package webportal.SwitchManaged.ACL.PRJCBUGEN_T4990;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
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
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Issue("PRJCBUGEN-9730")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4990") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("028-Edit device name,ip address,ip mask for manual deny policy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4990") // It's a testcase id/link from Jira Test Case.
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
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​​​​​1.Policy:Deny\n" + "2.Device name:test@028\n" + "3.IP / mask:100.1.1.100/0.255.255.255\n"
            + "4.Access type: access deny from this device")
    public void step2() {
        ipaclName = "test@028";
        ipaclIp = "100.1.1.100";
        wvp.ipFilterIpMask = "0.255.255.255";
        wvp.ipFilterMacOpt = 2;
        wvp.enaIpFilterIpMask = true;
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​​​​​1.On webportal,all info display correctly\n" + "2.On device,generate 2 IP acls\n"
            + "--deny,source ip 100.1.1.100,mask 0.255.255.255\n" + "--permit all")
    public void step3() {
        List<String> ls = wvp.getAllowDevices();
        assertTrue(ls.contains(ipaclIp));
        ls = wvp.getAllowDevicesName();
        assertTrue(ls.contains(ipaclName));

        handle.waitCmdReady(ipaclIp, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        tmpStr = String.format("deny %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "deny,source ip 100.1.1.100,mask 0.255.255.255"+": "+tmpStr);
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Verify Mask"+": "+tmpStr);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit device name,IP and ip mask")
    public void step4() {
        ipaclName = "test!0028";
        wvp.ipFilterIpMask = "0.0.255.255";
        wvp.openIpFilterMacAuth(ipaclIp, true);
        ipaclIp = "100.1.1.111";
        wvp.editVlanIpFilteringDeny(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: 1.On app,all info display correctly\n" + "2.On device,generate 2 IP acls\n"
            + "--deny,source ip 100.1.1.111,mask 0.0.255.255\n" + "--permit all")
    public void step5() {
        handle.waitCmdReady(wvp.ipFilterIpMask, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(true, "50");
        tmpStr = String.format("deny %s", ipaclIp);
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "deny,source ip 100.1.1.111,mask 0.0.255.255"+": "+tmpStr);
        tmpStr = wvp.ipFilterIpMask;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "Verify Mask"+": "+tmpStr);
    }

}
