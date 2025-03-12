package webportal.MPSK.prouser.PRJCBUGEN_T29791;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("MPSK") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29791") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, warning on creating 33rdMPSK as 'Maximum MPSK has exceeded' from MPSK page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T29791") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteAllMPSKKey();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }
    
    @Step("Test Step 2: Add MPSK Key.")
    public void step2() {
        new WirelessQuickViewPage().deleteAllMPSKKey();
        new WirelessQuickViewPage(false).gotoAddMPSKKey();
        
        
        for(int i =0; i < 32; i++) {
        Random              r           = new Random();
        int                 num         = r.nextInt(10000000);
        String  MPSKname    = "MPSKSSID" + i;
        System.out.println(MPSKname);
        System.out.println("Number of MPSK key value = " +i);
        
        Map<String, String> mpskKeyInfo = new HashMap<String, String>();
        mpskKeyInfo.put("passwordMPSK", "Netgear1@");
        mpskKeyInfo.put("mpskKeyName", MPSKname);
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo),"MPSK Key is not added Successfully");
        }       
        
        Map<String, String> mpskKeyInfo1 = new HashMap<String, String>();
        mpskKeyInfo1.put("passwordMPSK", "Netgear1@");
        mpskKeyInfo1.put("mpskKeyName", "Max");
        String MaxText = new WirelessQuickViewPage(false).addMaxMPSKKeys(mpskKeyInfo1);
        
        assertTrue(MaxText.equals("You have reached the max limit of 32 Multi Pre-Shared Keys. To add a new Key, you must delete an existing Key."), "Not proper message is sent ");
        
        
    }

}
