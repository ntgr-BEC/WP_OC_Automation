package webportal.LongRunStability.PRJCBUGEN_T9021;

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
import util.MyCommonAPIs;
import util.SQLiteStability;
import util.SwitchCLIUtils;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T9021] Verify the configuration deploy to Switch from Insight";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    String          devNo        = null;
    String          devName      = "LongRun_SW";
    String          devNewName   = "LR-SW-";
    String          devIp        = null;
    
    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9021") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9021") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        devNewName = devNewName + new SimpleDateFormat("MMddHHmm").format(new Date().getTime());
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
    
    @Step("Test Step 2: Go to network2, edit AP name")
    public void step2() {
        handle.gotoLoction(WebportalParam.location2);
        ddp.gotoPage();
        // devNo = ddp.getSWDevice();
        for (String devNo : ddp.getSWDevices()) {
            devName = ddp.getDeviceName(devNo);
            devIp = ddp.getDeviceIP(devNo);
            String devMode = ddp.getDeviceModel(devNo);
            
            expectResult = String.format("Device name should be changed to: '%s'", devNewName);
            stepInfo = "Verify Switch device name can be edit on webportal for " + devNo;
            ddp.editDeviceName(devNo, devNewName);
            String devicename = ddp.getDeviceName(devNo);
            actualResult = String.format("Device name on webportal is: '%s'", devicename);
            if (devicename.equals(devNewName)) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
            } else {
                dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            }
            
            stepInfo = "Check Switch new name is pushed to device: " + devIp;
            MyCommonAPIs.sleepsync();
            try {
                SwitchCLIUtils.setSwitchInfo(devIp, devMode);
                SwitchTelnet switchTelnet = new SwitchTelnet(devIp);
                devicename = switchTelnet.getDeviceName();
                actualResult = String.format("Device name on local device is: '%s'", devicename);
            } catch (Throwable e) {
                devicename = "";
                actualResult = "Not able to connect switch";
            }
            if (devicename.contains(devNewName)) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
            } else {
                dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            }
            ddp.gotoPage();
        }
    }
}
