package webportal.SwitchManaged.Port.PRJCBUGEN_T4897;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4897") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("020-Set Egress rate limit") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4897") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-15243")
    @Test(alwaysRun = true, groups = "p1")
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
        handle.waitCmdReady("traffic-shape " + expectValue, false);
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        Selenide.refresh();
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        String actualRate = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getEgressRateValue();
        if (actualRate.equals(expectValue)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "actual Rate is" + actualRate + ", expect egress value is: " + expectValue);
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress);
        String rate = switchTelnet.getEgressRateValue(WebportalParam.sw1LagPort1CLI);
        if (rate.contains(expectValue)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "actualRate is " + rate + ", expect egress value  is: " + expectValue);
        }
    }

    @Step("Test Step 5: change port  egress rate to 100, and check it on webportal")
    public void step5() {
        Selenide.refresh();
        MyCommonAPIs.sleep(3000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyEgressRate(EGRESS_MAX);
        MyCommonAPIs.sleepsync();
        Selenide.refresh();
        String value = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getEgressRateValue();
        if (value.equals(EGRESS_MAX)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 6: Check egress rate is 100 on CLI")
    public void step6() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress);
        String rate = switchTelnet.getEgressRateValue(WebportalParam.sw1LagPort1CLI);
        if (rate.contains(EGRESS_MAX)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 7: change port egress rate  to 0, and check it on webportal")
    public void step7() {
        Selenide.refresh();
        MyCommonAPIs.sleep(3000);
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyEgressRate(EGRESS_MIN);
        // wait 60 s for command send to switch
        MyCommonAPIs.sleepsync();
        Selenide.refresh();
        String value = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getEgressRateValue();
        if (value.equals(EGRESS_MIN)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult);
        }
    }

    @Step("Test Step 8: Check egress rate 0 on CLI")
    public void step8() {
        // check on dut CLI
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress);
        String rate = switchTelnet.getEgressRateValue(WebportalParam.sw1LagPort1CLI);
        if (rate.contains(EGRESS_MIN)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "expect value is " + EGRESS_MIN + "but, actual value is " + rate);
        }
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
