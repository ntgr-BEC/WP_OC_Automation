package webportal.LongRunStabilityVPN.PRJCBUGEN_T21236;

import java.util.List;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T21236] Check vpn group status after removed then added devices into group again";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    
    @Feature("LongRunStabilityVPN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21236") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21236") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
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
    
    @Step("Test Step 2: On Web portal, remove device from vpn group")
    public void step2() {
        handle.gotoLoction();
        handle.openOneBRDevice(false);
        brdvgp.gotoPage();
        
        brdvgp.deleteDevice(WebportalParam.longrunVpnGroupName, WebportalParam.br1deveiceName);
        brdvgp.deleteDevice(WebportalParam.longrunVpnGroupName, WebportalParam.br2deveiceName);
        if (WebportalParam.locationNumber == 3) {
            brdvgp.deleteDevice(WebportalParam.longrunVpnGroupName, WebportalParam.br3deveiceName);
        }
        
        List<String> devList = brdvgp.getDevices(WebportalParam.longrunVpnGroupName);
        stepInfo = "Check all devices are removed from group: " + WebportalParam.longrunVpnGroupName;
        expectResult = String.format("All devices should be removed from VPN group: '%s'", WebportalParam.longrunVpnGroupName);
        actualResult = String.format("'%s' devices are not able to remove from vpn group", devList);
        if (devList.size() == 0) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        MyCommonAPIs.sleep(5 * 60, "wait vpn to connect");
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);
    }
    
    @Step("Test Step 3: On Web portal, add device to vpn group")
    public void step3() {
        handle.openOneBRDevice(false);
        brdvgp.gotoPage();
        brdvgp.addDeviceGroup(WebportalParam.longrunVpnGroupName);
        
        List<String> devList = brdvgp.getDevices(WebportalParam.longrunVpnGroupName);
        stepInfo = "Check all devices are added to group: " + WebportalParam.longrunVpnGroupName;
        expectResult = String.format("All device are added to VPN group: '%s'", WebportalParam.longrunVpnGroupName);
        actualResult = String.format("Only '%s' devices are added to vpn group", devList);
        if (devList.size() == WebportalParam.locationNumber) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }
        
        MyCommonAPIs.sleep(5 * 60, "wait vpn to connect");
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);
    }

}
