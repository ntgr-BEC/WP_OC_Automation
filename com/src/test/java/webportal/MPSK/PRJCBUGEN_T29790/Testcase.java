package webportal.MPSK.PRJCBUGEN_T29790;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("MPSK") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29790") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, up to 8 MPSK can be created per SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T29790") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteALLSSID();
        new WirelessQuickViewPage().deleteAllMPSKKey();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Add MPSK Key.")
    public void step2() {
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo = new HashMap<String, String>();
        mpskKeyInfo.put("passwordMPSK", "Netgear1@");
        mpskKeyInfo.put("mpskKeyName", "MPSKSSIDTest01");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
           
        Map<String, String> mpskKeyInfo1 = new HashMap<String, String>();
        mpskKeyInfo1.put("passwordMPSK", "Netgear2@");
        mpskKeyInfo1.put("mpskKeyName", "MPSKSSIDTest02");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo1),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo2 = new HashMap<String, String>();
        mpskKeyInfo2.put("passwordMPSK", "Netgear3@");
        mpskKeyInfo2.put("mpskKeyName", "MPSKSSIDTest03");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo2),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo3 = new HashMap<String, String>();
        mpskKeyInfo3.put("passwordMPSK", "Netgear4@");
        mpskKeyInfo3.put("mpskKeyName", "MPSKSSIDTest04");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo3),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo4 = new HashMap<String, String>();
        mpskKeyInfo4.put("passwordMPSK", "Netgear5@");
        mpskKeyInfo4.put("mpskKeyName", "MPSKSSIDTest05");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo4),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo5 = new HashMap<String, String>();
        mpskKeyInfo5.put("passwordMPSK", "Netgear6@");
        mpskKeyInfo5.put("mpskKeyName", "MPSKSSIDTest06");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo5),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo6 = new HashMap<String, String>();
        mpskKeyInfo6.put("passwordMPSK", "Netgear7@");
        mpskKeyInfo6.put("mpskKeyName", "MPSKSSIDTest07");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo6),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo7 = new HashMap<String, String>();
        mpskKeyInfo7.put("passwordMPSK", "Netgear8@");
        mpskKeyInfo7.put("mpskKeyName", "MPSKSSIDTest08");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo7),"MPSK Key is not added Successfully");
        
        new WirelessQuickViewPage().gotoAddMPSKKey();
        
        Map<String, String> mpskKeyInfo8 = new HashMap<String, String>();
        mpskKeyInfo8.put("passwordMPSK", "Netgear9@");
        mpskKeyInfo8.put("mpskKeyName", "MPSKSSIDTest09");
        assertTrue(new WirelessQuickViewPage(false).addMPSKKeys(mpskKeyInfo8),"MPSK Key is not added Successfully");
        
        assertTrue(new WirelessQuickViewPage(false).verifyMPSKKeysareAdded(),"All 9 Numbers of MPSK Keys are not added Successfully.");
        
        
    }

    @Step("Test Step 3: Check default scheduled wifisettings.")
    public void step3() {
        
        Map<String, String> locationInfo7 = new HashMap<String, String>();
        locationInfo7.put("SSID", "apwp19980");
        locationInfo7.put("Band", "Both");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        assertTrue(new WirelessQuickViewPage().add9MPSK(locationInfo7),"Test Case failed");
        
        
        
    }

}
