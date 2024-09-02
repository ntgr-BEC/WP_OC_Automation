package webportal.BR.BRIPManagement.PRJCBUGEN_T7192;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sExpect  = "192.192.192.1";
    String vlanName = "testvlan";
    String vlanId   = "1192";
    
    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7192") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Enable LAN DHCP Server") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7192") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }
    
    @Step("Test Step 2: Go to BR-->DHCP Servers page, check default configuration;")
    public void step2() {
        brddchps.gotoPage();
        brddchps.openOne("1");
        assertTrue(MyCommonAPIs.getValue(brddchps.txtIp).equals(WebportalParam.br1IPaddress), "dhcp is not right");
    }
    
    @Step("Test Step 3: Change IP address and DHCP IP pool range;")
    public void step3() {
        String nextip = handle.nextIP(MyCommonAPIs.getValue(brddchps.txtIpStart));
        brddchps.enableDHCP(true);
        brddchps.setIp(brddchps.txtIpStart, nextip);
        handle.clickButton(0);
        
        handle.waitRestReady(BRUtils.api_lan_ip_settings, nextip, false, 0);
        assertTrue(new BRUtils().getField("startIp").contains(nextip), "dhcp new range is not changed");
    }
}
