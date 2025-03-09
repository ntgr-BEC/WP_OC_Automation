package webportal.SwitchManaged.System.PRJCBUGEN_T4945;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
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
    public String expectValue = "";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4945") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("045- Waive the reboot command") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4945") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: set port 1 egress rate at random value")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        devicesSwitchSummaryPage.enterPortConfigSummary(WebportalParam.sw1LagPort1);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        expectValue = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyEgressRateRandom();
    }

    @Step("Test Step 3: Waive the reboot command")
    public void step3() {
        // check sw1 on webportal
        MyCommonAPIs.sleep(3000);
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        PublicButton publicButton = new PublicButton();
        publicButton.publicOptionButton.click();
        publicButton.reBootIcon.click();
        publicButton.NoDontReboot.click();
        DevicesDashPageMNG devicesDashPage1 = new DevicesDashPageMNG();
        devicesDashPage1.devicesStatus(WebportalParam.sw1serialNo).shouldHave(Condition.text(WebportalParam.getLocText("Connected")));
    }

    @Step("Test Step 4: Check configuration on webportal")
    public void step4() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = devicesSwitchSummaryPage
                .enterPortConfigSummary(WebportalParam.sw1LagPort1);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        String actualRate = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getEgressRateValue();
        if (actualRate.equals(expectValue)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "actual Rate is" + actualRate + ", expect egress value is: " + expectValue);
        }
    }

    @Step("Test Step 5: Check device can login")
    public void step5() {
        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        boolean result1 = switchTelnet.isLoginSuccess();
        assertTrue(result1);
        SwitchTelnet.disconnect();
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        Selenide.refresh();
        MyCommonAPIs.sleep(3000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyEgressRate(EGRESS_MAX);
    }
}
