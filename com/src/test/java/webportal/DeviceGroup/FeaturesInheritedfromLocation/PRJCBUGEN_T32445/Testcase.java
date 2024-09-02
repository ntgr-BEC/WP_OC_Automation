package webportal.DeviceGroup.FeaturesInheritedfromLocation.PRJCBUGEN_T32445;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();

    @Feature("DeviceGroup FeaturesInheritedfromLocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32445") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, SNMP is pushed on CG devices") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32445") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DeviceGroupPage().disableSNMP();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    

    @Step("Test Step 3: Go to SNMP and enable")
    public void step3() {
        new DeviceGroupPage().GoToSNMP(WebportalParam.location1);
        new DeviceGroupPage().EnableSNMP("1.1.1.1", "Automation1@");
        
    }
     
    
    @Step("Test Step 4: check whethe it is pushed to AP;")
    public void step4() {
   
        boolean status = false;
        
        String result = new APUtils(WebportalParam.ap1IPaddress).SNMPEnableStatus(WebportalParam.ap1Model);
        if(result.contains("snmpStatus 1") ) {
            status = true;
        }
        
        assertTrue(status == true , "SNMP  is disabled after enabling");
    }
       

}
