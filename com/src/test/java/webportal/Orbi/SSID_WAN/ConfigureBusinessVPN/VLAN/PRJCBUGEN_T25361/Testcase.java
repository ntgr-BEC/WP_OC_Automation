package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VLAN.PRJCBUGEN_T25361;

import static org.testng.Assert.assertFalse;
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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;


/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25361") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify whether vlan related configuration is shown while Business VPN group has only SRR60.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25361") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User can edit ip address and subnet for SRR60 as remote router")
    public void step2() {
        String central_loc = "";
        String central_router = "";
        String remote_loc = "";
        String remote_router = "";
        
        if (WebportalParam.ob2Model.equals("SRR60")) {
            central_loc = WebportalParam.location2;
            central_router = WebportalParam.ob2deveiceName;
        }
        else if (WebportalParam.ob1Model.equals("SRR60")) {
            central_loc = WebportalParam.location1;
            central_router = WebportalParam.ob1deveiceName;
        }
        else {
            org.testng.Assert.fail("no router is SRR60");
        }
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        checkpoint = page.CheckVlanForCentralRouter(central_loc, central_router, "Employee (20)"); 
        assertFalse(checkpoint, "checkpoint 1 : vlan not exist for SRR60");
       
    }
}
