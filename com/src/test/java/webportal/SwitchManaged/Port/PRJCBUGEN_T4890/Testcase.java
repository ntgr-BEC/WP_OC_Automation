package webportal.SwitchManaged.Port.PRJCBUGEN_T4890;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchTelnetMNG;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DashboardLocationPage;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4890") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("013-Configure,Delete physical port description") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4890") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: From app Networks/Devices Configuration change port1 ,port 2 description to a string large than 64")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        AccountPage accountPage = new AccountPage();
        accountPage.enterLocation(LOCATION_INFO.get("Location Name"));
        DashboardLocationPage dashboardLocationPage = new DashboardLocationPage();
        dashboardLocationPage.enterDevices();

        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(DEVICE_INFO.get("Serial Number"));

        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort1);

        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = devicesSwitchConnectedNeighboursPortConfiqSummaryPage
                .enterPortSettingPage();

        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyPortDescription(DESCRIPTION_1);
        String actualMaxLength = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getPortDescriptionMaxLength();
        if (actualMaxLength.equals("64")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 2: From app Networks/Devices change port1,port2 description")
    public void step2() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyPortDescription(DESCRIPTION_2);
        MyCommonAPIs.sleepi(60);
    }

    @Step("Test Step 3: Check port 1 ,port 2 description from app and GUI")
    public void step3() {
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        String description = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getPortDescription();

        handle.waitCmdReady("aaaaaaaaaaa", false);
        SwitchTelnetMNG switchTelnet = new SwitchTelnetMNG(WebportalParam.sw1IPaddress);
        String cliDescription = switchTelnet.getPortDescription(WebportalParam.sw1LagPort1CLI);
        System.out.println(description);
        System.out.println(cliDescription);
        System.out.println(description.equals(DESCRIPTION_2));
        System.out.println(cliDescription.contains(DESCRIPTION_2));
        
        if (description.equals(DESCRIPTION_2) && cliDescription.contains(DESCRIPTION_2)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 4: Rload dut，and check port1,port 2 description")
    public void step4() {
//        PublicButton publicButton = new PublicButton();
//        publicButton.rebootDevice();
        ddpmg.gotoPage();
        ddpmg.rebootDevice(WebportalParam.sw1serialNo);
        handle.waitDeviceOnline();

        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        String description = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getPortDescription();
        if (description.equals(DESCRIPTION_2)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        MyCommonAPIs.sleep(4000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyPortDescription("");
    }
}
