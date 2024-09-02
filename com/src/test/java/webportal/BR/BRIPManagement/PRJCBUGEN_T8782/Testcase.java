package webportal.BR.BRIPManagement.PRJCBUGEN_T8782;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sExpect1  = "192.168.161.1";
    String sExpect2  = "192.168.162.1";
    String sExpect3  = "192.168.163.1";
    String sExpect4  = "192.168.164.1";
    String sToExpect = null;
    String vlanName  = "testvlan";
    String vlanId1   = "2781";
    String vlanId2   = "2782";
    String vlanId3   = "2783";
    String vlanId4   = "2784";
    String sPort     = "2,3,4";
    
    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T8782") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("014-Verify create maximum LAN IP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T8782") // It's a testcase id/link from Jira Test Case.
    @Issue("")
    
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (WebportalParam.br1model.contains("100")) {
            sPort = "2";
        }
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.editVlanPorts("1", WebportalParam.br1deveiceName, sPort, "", "", true);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Insight go to VLAN config page, create 4 VLANs(102~105), add LAN port2/3/4 untag to VLAN 102/103/104, add port 4 tag to VLAN 105;")
    public void step2() {
        wvp.gotoPage();
        wvp.editVlanPorts("1", WebportalParam.br1deveiceName, "", "", sPort, true);
        
        wvp.newVlan(vlanName + vlanId1, vlanId1, 0);
        wvp.editVlanPorts(vlanId1, WebportalParam.br1deveiceName, "2", "", "", true);
        
        wvp.newVlan(vlanName + vlanId2, vlanId2, 0);
        wvp.editVlanPorts(vlanId2, WebportalParam.br1deveiceName, "", "2", "", true);
        
        if (!WebportalParam.br1model.contains("100")) {
            wvp.newVlan(vlanName + vlanId3, vlanId3, 0);
            wvp.editVlanPorts(vlanId3, WebportalParam.br1deveiceName, "3", "", "", true);
            wvp.newVlan(vlanName + vlanId4, vlanId4, 0);
            wvp.editVlanPorts(vlanId4, WebportalParam.br1deveiceName, "4", "", "", true);
        }
    }
    
    @Step("Test Step 3: Insight go to DHCP Servers page, create LANs(2/3/4) and binding to VLAN(102/103/104), set IP and enable DHCP for all LANs;")
    public void step3() {
        handle.openOneBRDevice(true);
        brddchps.gotoPage();
        brddchps.addOne(vlanName + vlanId1, sExpect1);
        if (!WebportalParam.br1model.contains("100")) {
            brddchps.addOne(vlanName + vlanId3, sExpect3);
            brddchps.addOne(vlanName + vlanId4, sExpect4);
            sToExpect = sExpect4;
        } else {
            sToExpect = sExpect2;
        }
    }
    
    @Step("Test Step 4: Config success, checked by Insight and BR500 GUI;")
    public void step4() {
        handle.waitRestReady(BRUtils.api_lan_subnet_stats, sToExpect, false, 0);
        assertTrue(new BRUtils().Dump().contains(sToExpect), "more vlans for dchp should be config");
    }
    
    @Step("Test Step 5: Try to add new LANs and binding to VLAN 105;")
    public void step5() {
        brddchps.btnAdd.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "can create more than 4 vlans");
    }
}
