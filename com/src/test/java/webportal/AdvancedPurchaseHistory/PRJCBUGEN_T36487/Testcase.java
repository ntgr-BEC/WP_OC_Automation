package webportal.AdvancedPurchaseHistory.PRJCBUGEN_T36487;

import static org.testng.Assert.assertTrue;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo     = new HashMap<String, String>();
    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              mailname     = "purhtry" + String.valueOf(num);
    String              locationName = "PurchaseHistoryLoc";

    @Feature("IM-7.2 AdvancedPurchaseHistory") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36487") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Purchase History All Details after On boarded 3 AP") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T36487") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation(locationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a New Account ")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T36487");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear1@");
        accountInfo.put("Confirm Password", "Netgear1@");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Create a location")
    public void step2() {
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(locationName);

    }

    @Step("Test Step 3: On-Boarding First HBB Dummy Device")
    public void step3() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        System.out.println(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
    }

    @Step("Test Step 4: On-Boarding Second HBB Dummy Device")
    public void step4() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap6serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap6macaddress);
        System.out.println(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

    }

    @Step("Test Step 5: On-Boarding Third HBB Dummy Device")
    public void step5() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap7serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap7macaddress);
        System.out.println(firststdevInfo);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

    }

    @Step("Test Step 6: Open Purchase History Page and expand insigt HBB div Credits Section")
    public void step6() {
        new HamburgerMenuPage().openAccountMgmt();
        new HamburgerMenuPage(false).expandHardBundalCreditsSection();

    }

    @Step("Test Step 7: Verify HBB Premium 1 Year Subs Text should be there")
    public void step7() {
        assertTrue(new HamburgerMenuPage(false).VerifyHBBInsightPreOneYearSubsText(), "Insight Premium 1 year subs Texts are not present");

    }

    @Step("Test Step8: Verify The Location Name should be shown Under the Location Section")
    public void step8() {
        assertTrue(new HamburgerMenuPage(false).VerifyTheHBBSubsLocationName(locationName),
                "LocationName Not Showing correct Under the Location Section");

    }

    @Step("Test Step9: Verify the Activation date and Expiry date should be Today's Date")
    public void step9() {
        assertTrue(new HamburgerMenuPage(false).VerifyTheHBBDivActivationDateisTodayDate(1), "Activation Date is not equal to Today's Date");

    }

    @Step("Test Step 10: Verify for HBB 2nd Divice Premium 1 Year Subs Text should be there")
    public void step10() {
        assertTrue(new HamburgerMenuPage(false).VerifyHBBSecondDivInsightPreOneYearSubsText(), "Insight Premium 1 year subs Texts are not present");

    }

    @Step("Test Step11: Verify for HBB 2nd Divice Location Name should be shown Under the Location Section")
    public void step11() {
        assertTrue(new HamburgerMenuPage(false).VerifyTheHBSecondBDivSubsLocationName(locationName),
                "LocationName Not Showing correct Under the Location Section");

    }

    @Step("Test Step12: Verify for HBB 2nd Divice the Activation date and Expiry date should be there")
    public void step12() {
        assertTrue(new HamburgerMenuPage(false).VerifyTheHBBSecondDivActivationDateisTodayDate(1),
                "Activation Date is not equal to Today's Date for second device");

    }

    @Step("Test Step 13: Verify for HBB 3rd Divice Premium 1 Year Subs Text should be there")
    public void step13() {
        assertTrue(new HamburgerMenuPage(false).VerifyHBBThirdDivInsightPreOneYearSubsText(), "Insight Premium 1 year subs Texts are not present");

    }

    @Step("Test Step14: Verify for HBB 3rd Divice Location Name should be shown Under the Location Section")
    public void step14() {
        assertTrue(new HamburgerMenuPage(false).VerifyTheHBThirdBDivSubsLocationName(locationName),
                "LocationName Not Showing correct Under the Location Section");

    }

    @Step("Test Step15: Verify for HBB 3rd Divice the Activation date and Expiry date should be there")
    public void step15() {
        assertTrue(new HamburgerMenuPage(false).VerifyTheHBBThirdDivActivationDateisTodayDate(1),
                "Activation Date is not equal to Today's Date for Third device");

    }

}
