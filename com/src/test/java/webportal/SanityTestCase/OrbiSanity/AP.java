package webportal.SanityTestCase.OrbiSanity;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import util.SwitchCLIUtils;
import webportal.SanityTestCase.OrbiSanity.Config;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.webelements.DevicesDashPageElements;
import webportal.webelements.DevicesOrbiLANIPPageElement;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiLanIPPage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesOrbiWanIPPage;
import webportal.weboperation.DevicesOrbiConnectedClientsPage;
import webportal.weboperation.WebportalLoginPage;




public class AP extends TestCaseBase implements Config {
    String checkPoint = "";
    String toCheck    = "";
    int index = 0;
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        WebportalParam.enableBatch = true;
        runStepTest(this);
    }
    
    public void test_check_clients_with_ssid() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            try {
                DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
                checkPoint = "check the number of connected clients";
                WebportalParam.printResult((page.getAllConnectedClients().size() > 0) ? true : false, sIp, sModel, checkPoint);
                
            } catch (Throwable e) {
                e.printStackTrace();
            }
            index++;
        }
    }
    
    public void test_check_satellites() {
        
        index = 0;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            try {
                
                DevicesOrbiSatellitesPage page = new DevicesOrbiSatellitesPage();
                checkPoint = "check the number of satellites is correct";
                WebportalParam.printResult((page.getAllIndoorName().size() == numOfSatellites[index]) ? true : false, sIp, sModel, checkPoint);
                
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            index++;
        }
        
    }
    
    public void test_rename_satellite() {
        
        index = 0;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            try {
                DevicesOrbiSatellitesPage page = new DevicesOrbiSatellitesPage();
                page.changeIndoorName(newSatelliteName);
                MyCommonAPIs.sleepsync();
                
                checkPoint = "check satellite can be renamed";
                
                WebportalParam.printResult((new BRUtils(BRUtils.api_satellites_details, 4).Dump().contains(newSatelliteName)) ? true : false, sIp, sModel, checkPoint);
                
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            index++;
        }
        
    }
    
    
    public void test_change_to_router_mode() {
        index = 0;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            try {
                
                DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
                page.setDeviceMode(false);
                
                MyCommonAPIs.sleepsync();
                
                page.gotoPage();
                checkPoint = "check device is in ap mode";
                
                WebportalParam.printResult((page.isRouterMode()) ? true : false, sIp, sModel, checkPoint);
                
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            index++;
        }
    }
}