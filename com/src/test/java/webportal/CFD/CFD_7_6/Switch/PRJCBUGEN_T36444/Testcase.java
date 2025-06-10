package webportal.CFD.CFD_7_6.Switch.PRJCBUGEN_T36444;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.*;

/**
 *
 * @author RaviShankar
 *
 */
public class Testcase extends TestCaseBase {

    Random random = new Random();
    HashMap <String,String> deviceInfo = new HashMap<String, String>();
    StringBuilder temp =new StringBuilder(WebportalParam.sw1deveiceName);

    @Feature("Onboarding Switches") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36444") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify adding multiple switches in a single location") // It's a testcase                                                                                                                           // Test Case.
    @TmsLink("PRJCBUGEN_T36444") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganization("Netgearnew");
    }

    @Step("Step 1: Login into Insight Pro account as admin")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

        @Step("Step 2: Navigate to Organization and open Location")
        public void step2() {
            Map<String, String> organizationInfo = new HashMap<String, String>();
            organizationInfo.put("Name", "Netgearnew");

            OrganizationPage OrganizationPage = new OrganizationPage();
//            OrganizationPage.addOrganization(organizationInfo);
            OrganizationPage.openOrg("Netgearnew");

            HashMap<String, String> locationInfo = new HashMap<String, String>();
            locationInfo.put("Location Name", "officenew");
            locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
            locationInfo.put("Zip Code", "32003");
            locationInfo.put("Country", "United States of America");
            new AccountPage(false).addNetwork(locationInfo);
            MyCommonAPIs.sleepi(15);
            new HamburgerMenuPage().configCreditAllocation("Netgearnew", 10, 0, 0);
            OrganizationPage.openOrg("Netgearnew");
            MyCommonAPIs.waitReady();
            new AccountPage(false).enterLocation(locationInfo.get("Location Name"));

        }

    @Step("Test Step 3: Onboard Switches")
    public void step3() {

        deviceInfo.put("MAC Address1", WebportalParam.sw1MacAddress);
        deviceInfo.put("Serial Number1",WebportalParam.sw1deveiceName );

        int remainingCount = 5;
        while (remainingCount > 0) {
            System.out.println(deviceInfo);
            new DevicesDashPage(false).addNewdummyDevice(deviceInfo);
            MyCommonAPIs.sleepi(5);
            assertTrue(GenericMethods.checkVisibleElements($$x("//*[text()='" + deviceInfo.get("Serial Number1") + "']")),"Serial Number is not visible");
            System.out.print("Asserted Serial Number is visible"+deviceInfo.get("Serial Number1"));
            String replacer = String.valueOf(random.ints(10, 99).findFirst().getAsInt());
            System.out.print(replacer);
            String serialnew = replaceSerial(temp, replacer);
            deviceInfo.put("Serial Number1", serialnew);
            remainingCount--;
            MyCommonAPIs.sleepi(5); // Wait between additions
        }
        System.out.println("All switches added successfully.");
    }

    private String replaceSerial(StringBuilder serialparam , String replacer) {
        // TODO Auto-generated method stub
        System.out.print("converted number is "+serialparam.replace(11,13,replacer));
        return serialparam.replace(11,13,replacer).toString();
    }


}
