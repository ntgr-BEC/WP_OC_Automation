package webportal.Onboard_recursive.PRJCBUGEN_T14168;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String organizationName1 = "Netgear1";
    String locationName1     = "office1";
    String organizationName2 = "Netgear2";
    String locationName2     = "office2";
    
    Map<String, String> devInfo = new HashMap<String, String>();
    OrganizationPage OrganizationPage = new OrganizationPage();
    

    @Feature("Onboard Pro account ") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14168") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Onboard to New Org and New Loc") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14168") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Delete from One Org and add it to other Org ")
    public void step2() {
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        int Iteration = 0, Fail = 0;
        
        boolean result = true;
        while (result) {
        try {     
        System.out.printf("Status of Device onboard : Fail<%s>/Total<%s>\n", Fail, Iteration);   
        Iteration++;   
        OrganizationPage.openOrg(organizationName1);
        new AccountPage(false).enterLocation(locationName1);
        
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        new DevicesDashPage(false).addNewDevice(devInfo);
        assertTrue(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo),"device connecttion failled");
        
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        
        
        
        OrganizationPage.openOrg(organizationName2);
        new AccountPage(false).enterLocation(locationName2);
        
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
              
        new DevicesDashPage(false).addNewDevice(devInfo);
        assertTrue(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo),"device connecttion failled");
                       
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        }
        catch (Throwable e) {
            Fail++;
            OrganizationPage.openOrg(organizationName1);
            new AccountPage(false).enterLocation(locationName1);
            
            if(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo)){
                new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
            }
            
            OrganizationPage.openOrg(organizationName2);
            new AccountPage(false).enterLocation(locationName2);
            
            if(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo)){
                new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
            }
        }
      
        }

    }

}
