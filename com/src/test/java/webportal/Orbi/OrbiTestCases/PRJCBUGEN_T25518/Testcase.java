package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25518;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.param.OrbiGlobalConfig;
import orbi.weboperation.OrbiAdvancedBackupSettingsPage;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiSetupWizardPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    public String currentDeviceMode = "Router";

    @Feature("Orbi.OrbiTestCases") // 必须要加，对应目录名
    @Story("PRJCBUGEN_T25518") // 对应testrunkey
    @Description("Test to verify the summary dashboard has proper device details") // 对应用例名字
    @TmsLink("PRJCBUGEN-T25518") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    //Device details info:
    public final static Map<String, String> Device_Details_Info = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Connnected_status", "Connected");
            put("Device_Mode", DevicesOrbiSetupWizardPage.device_setup_router_Info.get("Device_Mode"));
            put("Name", WebportalParam.ob2Name);
            put("Serial_Number", WebportalParam.ob2serialNo);
            put("Model", WebportalParam.ob2Model);
            put("Base_MAC_Address", WebportalParam.ob2MAC_Address);
            put("Uptime", "NNA");
            put("Ip_Adress", OrbiGlobalConfig.orbiLANIp);
            put("Firmware_Version", WebportalParam.ob2FirmwareVersion);
        }
    };
    public final static Map<String, String> SSIDInfo = new HashMap<String, String>() {/**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
        
            put("SSIDName", WebportalParam.ob2WifiNetworkName1);
            put("SSIDStatus", "Enabled");
            put("SSIDSecurity", "WPA2-PSK");
        }
    };
    
    // step对应jira测试用例里面的步骤，括号里的是描述

    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Verify device status;")
    public void step2() {
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        assertTrue(new DevicesOrbiSummaryPage().checkDeviceDetails(Device_Details_Info),"Device summry check failed"); 
    }
  
}
