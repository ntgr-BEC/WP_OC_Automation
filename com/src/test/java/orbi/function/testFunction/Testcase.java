package orbi.function.testFunction;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.weboperation.OrbiDebugSettingsPage;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesOrbiSetupWizardPage;

/**
 *
 * @author bingke.xue
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Orbi.OnBoarding") // 必须要加，对应目录名
    @Story("PRJCBUGEN_T14101") // 对应testrunkey
    @Description("Verify day 0 factory default Orbi router can be onboarded using the Serial # to AP mode") // 对应用例名字
    @TmsLink("PRJCBUGEN-T14101") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p1") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = false)
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
            put("Device_Mode", DevicesOrbiSetupWizardPage.device_setup_ap_Info.get("Device_Mode"));
            put("Name", WebportalParam.ob2Name);
            put("Serial_Number", WebportalParam.ob2serialNo);
            put("Model", WebportalParam.ob2Model);
            put("Base_MAC_Address", WebportalParam.ob2MAC_Address);
            put("Uptime", "NNA");
            put("Ip_Adress", WebportalParam.ob2IPaddress);
            put("Firmware_Version", WebportalParam.ob2FirmwareVersion);
        }
    };
    
    // step对应jira测试用例里面的步骤，括号里的是描述



    @Step("Test Step 2: Factory default orbi base;")
    public void step2( ) {
        
        String WANIP_1 = "";
/*
        OrbiAdvancedInternetSetupPage orbiadvinternetsetuppage = new OrbiAdvancedInternetSetupPage();
        
        WANIP_1 = orbiadvinternetsetuppage.getWANIp();

//        new OrbiDebugSettingsPage().OrbibaseEnableTelenet("LAN");
//        new OrbiTelnet(OrbiGlobalConfig.orbiLANIp).orbiEnableRemoteManagement();
//        new OrbiTelnet(OrbiGlobalConfig.orbiLANIp).orbiSetRegion("WW");
//        String region_str = new OrbiTelnet(OrbiGlobalConfig.orbiLANIp).orbiGetRegion();


        Selenide.open("http://admin:password@192.168.2.1");
        $("#basic-atd").click();
        Selenide.switchTo().frame("page");
        WANIP_1 = $x("//span[@name='rule_mac'][text()='"+WebportalParam.ob2MAC_Address.toUpperCase().replace('-', ':')+"']/../..//span[@name='rule_ip']").getText();
        System.out.print("WAN IP is this:"+WANIP_1+"\n");
        
        
        String currentDeviceMode = "Router";
        if (!currentDeviceMode.equals("AP")) {
            OrbiAdvancedRouterAPModePage orbiadvrouterapmodepage = new OrbiAdvancedRouterAPModePage();
            orbiadvrouterapmodepage.setDeviceModeVariable(currentDeviceMode);
            orbiadvrouterapmodepage.setDeviceMode("AP");
        }
        
        
        String currentDeviceMode = "AP";
        if (!currentDeviceMode.equals("Router")) {
            OrbiAdvancedRouterAPModePage orbiadvrouterapmodepage = new OrbiAdvancedRouterAPModePage();
            orbiadvrouterapmodepage.setDeviceModeVariable(currentDeviceMode);
            orbiadvrouterapmodepage.setDeviceMode("Router");
        }

        MyNetgearGuiPage aa = new MyNetgearGuiPage();
        aa.defaultLogin();
        assertTrue(aa.checkDeviceInList(WebportalParam.ob2serialNo),"Device  check in device list failed"); 
*/
        new OrbiDebugSettingsPage().OrbibaseEnableTelenet("Router");


//        open("https://admin:"+ WebportalParam.loginDevicePassword +"@"+OrbiGlobalConfig.orbiLANIp);
    }
    
   
 
}
