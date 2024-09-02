package webportal.OlaTestcases.PRJCBUGEN_T13880;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    String organizationName = "PRJCBUGEN_T113878";
    Map<String, String> locationInfo = new HashMap<String, String>();


    @Feature("OlaTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13880") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that the user is able to create an ORG wide SSID Open with ECP for existing Organization") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T113880") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsidYes(locationInfo.get("SSID"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Verify that the user is able to create an organization without an owner")
    public void step2() {
        new OrganizationPage(false).openOrg(organizationName);
        new OrganizationPage(false).goToOrgSsid(organizationName);
        
        locationInfo.put("SSID", "PRJCBUGEN_T113880");
        locationInfo.put("Security", "Open");
        new OrganizationPage(false).CreateOrgSSId(locationInfo);

    }
    
    @Step("Test Step 3: add ECP;")
    public void step3() {
        Map<String, String> ECPInfo = new HashMap<String, String>();
        ECPInfo.put("Walled Garden", "*.jazenetworks.com");
        ECPInfo.put("Secret", "rMVCMqW+UMwXZQn+KYfe8GeXm3E6w9y9");
        ECPInfo.put("Key", "GCDKNGYD2XETKBZ9");
        
        new OrganizationPage(false).OrgSsidEnableEcp(organizationName);
        new OrganizationPage(false).OrgCreateECP(locationInfo.get("SSID"), ECPInfo);
        
        MyCommonAPIs.sleepi(120);
        
        String CMD = "WalledGarden" ;
        String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
        System.out.println(Result);
        assertTrue(Result.contains("*.jazenetworks.com 1"), "ECP walled Garden is not applied");

        
    }
}
