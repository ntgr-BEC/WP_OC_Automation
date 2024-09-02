package webportal.BR.BRBackupRestore.PRJCBUGEN_T16931;

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
import webportal.weboperation.DeviceBackupRestorePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    DeviceBackupRestorePage brPage;
    String                  dhcpIp      = "192.168.131.1";
    String                  vlanName    = "testvlan";
    String                  vlanId      = "1931";
    String                  sPort       = "2";
    boolean                 wanMode     = true;
    boolean                 needRestore = false;
    String                  policyName  = "testpolicy";

    @Feature("BR.BRBackupRestore") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16931") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Download configuration and Upload configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16931") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        brPage = new DeviceBackupRestorePage(WebportalParam.br1serialNo);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (needRestore) {
            brdwip.gotoPage();
            brdwip.setDHCP(wanMode);
            brdwip.saveIt();
        }
        deletAllData();
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);
    }

    public void deletAllData() {
        brddchps.gotoPage();
        brddchps.deleteOne(vlanId);

        wvp.gotoPage();
        wvp.deleteVlan(vlanId, true);

        bripsvp.gotoPage();
        bripsvp.deletePolicyNames();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openBR1();
    }

    @Step("Test Step 2: Insight managed BR,deploy all dhcp server ,WAN ip address,vlan,ipsec to BR")
    public void step2() {
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        wvp.editVlanPorts(vlanId, WebportalParam.br1deveiceName, "", sPort, "", true);
        
        brddchps.gotoPage();
        brddchps.addOne(vlanName, dhcpIp);

        brdwip.gotoPage();
        wanMode = brdwip.isDHCP();
        
        bripsvp.gotoPage();
        bripsvp.addPolicy(policyName);

        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);

        brPage.gotoPage("br");
        brPage.deleteBackups();
        brPage.createBackup();
    }
    
    @Step("Test Step 3: Edit dhcp sever,WAN IP,vlan,ipsec via insight")
    public void step3() {
        deletAllData();
        
        brdwip.gotoPage();
        brdwip.setDHCP(!wanMode);
        brdwip.saveIt();
        needRestore = true;
        
        brPage.gotoPage();
        brPage.restoreBackup();
        brPage.deleteBackups();
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 4: Restore configuration file successfully, check on device only.")
    public void step4() {
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);

        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.policyName, false, 0);
        assertTrue(new BRUtils().Dump().contains(policyName), policyName + " must be restored");

        handle.waitRestReady(BRUtils.api_lan_subnet_stats, dhcpIp, false, 0);
        assertTrue(new BRUtils().Dump().contains(dhcpIp), dhcpIp + " must be restored");
        
        // ddp.openBR1();
        //
        // brddchps.gotoPage();
        // assertTrue(brddchps.getListIP().contains(dhcpIp), dhcpIp + " must be restored");
        //
        // wvp.gotoPage();
        // assertTrue(wvp.getVlanIDs().contains(vlanId), vlanId + " must be restored");
        //
        // brdwip.gotoPage();
        // assertTrue(brdwip.isDHCP() == wanMode, wanMode + " must be restored");
        //
        // bripsvp.gotoPage();
        // assertTrue(bripsvp.getPolicyNameList().contains(policyName), policyName + " must be restored");
    }
}
