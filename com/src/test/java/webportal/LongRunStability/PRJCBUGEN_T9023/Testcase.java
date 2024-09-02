package webportal.LongRunStability.PRJCBUGEN_T9023;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import util.SQLiteStability;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T9023] Verify the configuration deploy to BR500 Router from Insight";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    String          devNo        = null;
    String          devName      = "LongRun_Router";
    String          devNewName   = "LR_BR_";
    String          vlanName     = "brvlantest";
    String          vlanId       = "3456";
    String          devIp        = null;

    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9023") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9023") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        devNewName = devNewName + new SimpleDateFormat("MMddHH").format(new Date().getTime());
        dbHandle = new SQLiteStability();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Go to network1, edit AP name")
    public void step2() {
        WebportalParam.br1IPaddress = "192.168.11.1";

        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        devNo = ddp.getBRDevice();
        devName = ddp.getDeviceName(devNo);
        devIp = WebportalParam.br1IPaddress;

        expectResult = String.format("Device name should be changed to: '%s'", devNewName);
        stepInfo = "Verify Router device name can be edit on webportal: " + devIp;
        try {
            ddp.editDeviceName(devNo, devNewName);
            String devicename = ddp.getDeviceName(devNo);
            actualResult = String.format("Device name on webportal is: '%s'", devicename);
            if (devicename.equals(devNewName)) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
            } else {
                dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }

        stepInfo = "Check Router new name is pushed to device: " + devIp;
        try {
            handle.waitRestReady(BRUtils.api_device_name, devNewName, false, 0);
            String devicename = new BRUtils().getField("name");
            actualResult = String.format("Device name on local device is: '%s'", devicename);
            if (devicename.contains(devNewName)) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
            } else {
                dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
    }

    @Step("Test Step 3: Go to network1, edit vlan")
    public void step3() {
        stepInfo = "Check new vlan is pushed to device: " + WebportalParam.br1IPaddress;
        expectResult = vlanId + "t";
        try {
            wvp.gotoPage();
            wvp.newVlan(vlanName, vlanId, 0);
            WebportalParam.br1deveiceName = devNewName;
            wvp.editVlanPorts(vlanId, WebportalParam.br1deveiceName, "", "2", "", true);

            handle.waitRestReady(BRUtils.api_lan_port_stats, expectResult, false, 0);
            actualResult = new BRUtils().Dump();
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }

        if (actualResult.contains(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, "Vlan was not added on device");
        }
    }

    @Step("Test Step 4: Go to network1, delete vlan")
    public void step4() {
        wvp.gotoPage();
        wvp.deleteVlan(vlanName, false);

        stepInfo = "Check new vlan is deleted from device: " + WebportalParam.br1IPaddress;
        expectResult = vlanId + "t";
        handle.waitRestReady(BRUtils.api_lan_port_stats, expectResult, true, 0);
        actualResult = new BRUtils().Dump();
        if (!actualResult.contains(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, "Vlan was not deleted on device");
        }
    }

}
