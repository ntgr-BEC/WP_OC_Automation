package webportal.SanityTestCase.OrbiSanity;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

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
import webportal.weboperation.DevicesOrbiNetworkSetupPage;
import webportal.weboperation.DevicesOrbiDHCPServersPage;
import webportal.weboperation.WebportalLoginPage;


public class Router extends TestCaseBase implements Config {
    String checkPoint = "";
    String toCheck    = "";
    int index = 0;
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        WebportalParam.enableBatch = true;
        runStepTest(this);
    }
    
    public void test_check_ip_and_mac() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        
        index = 0;
        
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            try {
                toCheck = new DevicesOrbiSummaryPage(false).getBaseMAC();
                checkPoint = "check mac address on be consistent with DUT's MAC";
                WebportalParam.printResult((toCheck.equals(baseMAC[index])) ? true : false, sIp, sModel, checkPoint);
                
                toCheck = new DevicesOrbiSummaryPage(false).getBaseIP();
                checkPoint = "check ip address on be consistent with DUT's IP";
                WebportalParam.printResult((toCheck.equals(baseIP[index])) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            index++;
        }
        
    }
    
    public void test_check_clients_with_ssid() {
        
        index = 0;
        
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            try {
                DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
                checkPoint = "check the number of connected clients";
                WebportalParam.printResult((page.getAllConnectedClients().size() == numOfClients[index]) ? true : false, sIp, sModel, checkPoint);
                
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
    
    public void test_add_delete_vlan() {
        
        index = 0;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            if(sModel.equals("SRR60")) {
                System.out.println("----------SRR60 not support add delete vlan----------");
            }
            else {
            
                new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);

                try {

                    DevicesOrbiNetworkSetupPage page1 = new DevicesOrbiNetworkSetupPage();
                    page1.gotoPage();
                    page1.createNetwork(vlanName[index], vlanId[index], startIp[index]);

                    checkPoint = "check vlan is added";
                    boolean addvlan = false;
                    for (int i = 0; i < 10; i++) {
                        TimeUnit.SECONDS.sleep(60);
                        page1.gotoPage();
                        String oristr = page1.getNetworks().get(0);
                        String lines[] = oristr.split("\\r?\\n");

                        for (String str : lines) {
                            if (str.equals(vlanName[index])) {
                                addvlan = true;
                                break;
                            }
                        }
                        if (addvlan)
                            break;
                    }
                    WebportalParam.printResult(addvlan ? true : false, sIp, sModel, checkPoint);

                    checkPoint = "check dhcp server is added";
                    boolean dhcpserver = false;
                    DevicesOrbiDHCPServersPage page2 = new DevicesOrbiDHCPServersPage();
                    page2.gotoPage();

                    for (int i = 0; i < 10; i++) {
                        TimeUnit.SECONDS.sleep(60);
                        page2.gotoPage();

                        for (String str : page2.getListIP()) {
                            if (str.equals(startIp[index])) {
                                dhcpserver = true;
                                break;
                            }
                        }
                        if (dhcpserver)
                            break;

                    }

                    WebportalParam.printResult(dhcpserver ? true : false, sIp, sModel, checkPoint);

                    TimeUnit.SECONDS.sleep(180);
                    boolean allnotfound = true;

                    // Delte dhcp server if it is added successfully
                    if (dhcpserver) {
                        page2.gotoPage();
                        page2.deleteOne(vlanId[index]);
                        TimeUnit.SECONDS.sleep(300);

                        // check dhcp server is deleted
                        checkPoint = "check dhcp server is deleted";

                        for (int i = 0; i < 10; i++) {
                            TimeUnit.SECONDS.sleep(60);
                            page2.gotoPage();
                            allnotfound = true;
                            for (String str : page2.getListIP()) {
                                if (str.equals(startIp[index])) {
                                    allnotfound = false;
                                    break;
                                }
                            }
                            if (allnotfound) {
                                dhcpserver = false;
                                break;
                            }
                        }
                        WebportalParam.printResult(!dhcpserver ? true : false, sIp, sModel, checkPoint);
                    }

                    // Detele new vlan if it is added successfully
                    if (addvlan) {

                        page1.gotoPage();
                        page1.deleteNetwork(vlanName[index]);
                        // TimeUnit.SECONDS.sleep(180);

                        // check vlan is deleted
                        checkPoint = "check vlan is deleted";
                        for (int i = 0; i < 10; i++) {
                            TimeUnit.SECONDS.sleep(60);
                            page1.gotoPage();
                            String oristr = page1.getNetworks().get(0);
                            String lines[] = oristr.split("\\r?\\n");

                            for (String str : lines) {
                                // if found new vlan then allnotfound = false
                                if (str.equals(vlanName[index])) {
                                    allnotfound = false;
                                    break;
                                }
                            }
                            if (allnotfound) {
                                addvlan = false;
                                break;
                            }
                        }
                        WebportalParam.printResult(!addvlan ? true : false, sIp, sModel, checkPoint);
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            
            index++;
        }
        
    }
    
    public void test_create_dhcp_server() {
        index = 0;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            if(sModel.equals("SRR60")) {
                System.out.println("----------SRR60 not support add delete vlan----------");
            }
            else {
            
                try {

                    DevicesOrbiDHCPServersPage page = new DevicesOrbiDHCPServersPage();
                    page.gotoPage();
                    page.addOne(dhcpVlan[index], dhcpip[index]);

                    checkPoint = "check dhcp server is added";
                    boolean dhcpserver = false;

                    page.gotoPage();

                    for (int i = 0; i < 10; i++) {
                        TimeUnit.SECONDS.sleep(60);

                        page.gotoPage();

                        for (String str : page.getListIP()) {
                            if (str.equals(dhcpip[index])) {
                                dhcpserver = true;
                                break;
                            }
                        }
                        if (dhcpserver)
                            break;
                    }
                    WebportalParam.printResult(dhcpserver ? true : false, sIp, sModel, checkPoint);

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            
            }
            index++;
        }
    }
    
    public void test_change_to_ap_mode() {
        
        index = 0;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            new DevicesDashPage().enterDevicesSwitchSummary(serialNum[index], 3);
            
            try {
                
                DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
                page.setDeviceMode(true);
                
                MyCommonAPIs.sleepsync();
                
                page.gotoPage();
                checkPoint = "check device is in ap mode";
                
                WebportalParam.printResult((!page.isRouterMode()) ? true : false, sIp, sModel, checkPoint);
                System.out.println("Finish router mode test");
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            index++;
        }
        
    }
}