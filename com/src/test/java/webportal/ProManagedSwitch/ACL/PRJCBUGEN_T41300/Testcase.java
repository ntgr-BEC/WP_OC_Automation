package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41300;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    
    String vlanName = "testvlan";
    String tmpStr;
    
    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";
    String oldmac;
    
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41300") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("075-Edit custom mac acl,the policy is allow") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41300") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
    @Step("Test Step 1: Create vlan200 on switch")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        wvp.gotoPage();
        wvp.newVlan(vlanName, "200", 0);
        wvp.openVlan(vlanName, "200", 0);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: 1.Policy:Allow\n" + "2.Custom addrss:\n" + "From-11:11:11:11:11:11\n" + "To-22:22:22:22:22:22")
    public void step2() {
        wvp.camData.frommac = "11:11:11:11:11:11";
        wvp.camData.tomac = "22:22:22:22:22:22";
        wvp.enaIpFilterCustom = true;
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclIp);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: On Switch,generated a mac acl binding to vlan200\n" + "--allow,source-11:11:11:11:11:11,des-22:22:22:22:22:22")
    public void step3() {
        handle.waitCmdReady(wvp.camData.frommac, false);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "200");
        assertTrue(SwitchCLIUtils.ACLClass.ispermitACL, "allow mac acl");
        tmpStr = wvp.camData.tomac;
        assertTrue(SwitchCLIUtils.ACLClass.aclResult.contains(tmpStr), "check mac entry" + ": " + tmpStr);
        assertTrue(
                SwitchCLIUtils.ACLClass.aclResult
                        .contains(String.format("permit %s 00:00:00:00:00:00 %s 00:00:00:00:00:00", wvp.camData.frommac, wvp.camData.tomac)),
                "allow,source-11:11:11:11:11:11,des-22:22:22:22:22:22");
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: From:11:11:11:11:11:22\n" + "To:22:22:22:22:22:33")
    public void step4() {
        oldmac = wvp.camData.frommac;
        wvp.openIpFilterMacAuth(oldmac, false);
        wvp.camData.frommac = "11:11:11:11:11:22";
        wvp.camData.tomac = "22:22:22:22:22:33";
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
        MyCommonAPIs.sleepsync();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: 1.On Switch,generated a mac acl binding to vlan200\n" + "--allow,source-11:11:11:11:11:22,des-22:22:22:22:22:33\n"
            + "2.The old mac acl is deleted\n" + "3.All info on webportal display correctly")
    public void step5() {
        List<String> ls = wvp.getAllowDevices();
        assertTrue(ls.contains(wvp.camData.frommac));
        
        handle.waitCmdReady(oldmac, true);
        tmpStr = SwitchCLIUtils.getIpMACACL(false, "200");
        assertTrue(
                SwitchCLIUtils.ACLClass.aclResult
                        .contains(String.format("permit %s 00:00:00:00:00:00 %s 00:00:00:00:00:00", wvp.camData.frommac, wvp.camData.tomac)),
                "allow,source-11:11:11:11:11:22,des-22:22:22:22:22:33");
        assertFalse(SwitchCLIUtils.ACLClass.aclResult.contains(String.format("permit %s 00:00:00:00:00:00", oldmac)),
                "no allow,source-11:11:11:11:11:11,des-22:22:22:22:22:22");
    }
    
}
