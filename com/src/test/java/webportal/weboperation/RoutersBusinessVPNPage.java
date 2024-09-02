package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.RoutersBusinessVPNElement;

public class RoutersBusinessVPNPage extends RoutersBusinessVPNElement {
    Logger logger = Logger.getLogger("RoutersBusinessVPNPage");
    
    public RoutersBusinessVPNPage() {
        logger.info("init...");
    }
    
    // go to specific page
    public void gotoPage() {
        //refresh();
        WebCheck.checkUrl(URLParam.hrefVPNSettings);
        waitReady();
        MyCommonAPIs.sleepi(2);
    }
    
    public void gotoGroupPage(String groupname) {
        //refresh();
        getVPNGroupElement(groupname).doubleClick();
        waitReady();
        MyCommonAPIs.sleepi(2);
    }
    
    public void gotoCentralRouterPage() {
        //refresh();
        WebCheck.checkUrl(URLParam.hrefOfficeRouter);
        waitReady();
        MyCommonAPIs.sleepi(2);
    }
    
    public void gotoRemoteRoutersPage() {
        //refresh();
        WebCheck.checkUrl(URLParam.hrefRemoteRouter);
        waitReady();
        MyCommonAPIs.sleepi(2);
    }
    
    // Common methods
    public void clickNext() {
        MyCommonAPIs.sleepi(1);
        if (btnNext.exists()) {
            btnNext.click();
        } 
        waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void clickBack() {
        if (btnBack.exists()) {
            btnBack.click();
        } 
        waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void modalClickSave() {
        if (btnModalSave.exists()) {
            btnModalSave.click();
        } 
        waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void modalClickCancel() {
        if (btnModalCancel.exists()) {
            btnModalCancel.click();
        } 
        else if (ModalCloseImg.exists()) {
            ModalCloseImg.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void modalClickDelete() {
        if (btnModalDelete.exists()) {
            btnModalDelete.click();
        } 
        waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void modalClickOK() {
        if (btnModalOK.exists()) {
            btnModalOK.click();
        } 
        waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void SetCheckboxSelected(SelenideElement element, boolean selected) {
        setSelected(element, selected);
    }
    
    // page 0 : Business VPN
    public void clickAddVPNGroup() {
        if (cirbtnAdd.exists()) {
            cirbtnAdd.click();
            MyCommonAPIs.sleepi(1);
        } 
        if(btnCreateNext.exists()) {
            btnCreateNext.click();
            MyCommonAPIs.sleepi(1);
        }
        waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public boolean GroupExist(String groupname, String ssid, String centralrouter) {
        if (dataTable.exists()) { 
            boolean result = false;
            int index = 0;
            for (String str: getTableCol(1)) {
                if(str.equals(groupname)) {
                    result = true;
                    break;
                }
                index++;
            }
            
            if (result) {
                String _ssid = getTableCol(2).get(index);
                String _central = getTableCol(3).get(index);
                if (_ssid.equals(ssid) && _central.equals(centralrouter) ) {
                    result = true;
                }
                else {
                    result = false;
                }
            }       
            return result;
        } 
        return false;
    }
    
    public boolean GroupExist(String groupname, String ssid, String centralrouter, String VPNhealth) {
        if (dataTable.exists()) { 
            boolean result = false;
            int index = 0;
            for (String str: getTableCol(1)) {
                if(str.equals(groupname)) {
                    result = true;
                    break;
                }
                index++;
            }
            
            if (result) {
                String _ssid = getTableCol(2).get(index);
                String _central = getTableCol(3).get(index);
                String _health = getTableCol(6).get(index);
                if (_ssid.equals(ssid) && _central.equals(centralrouter) && _health.equals(VPNhealth)) {
                    result = true;
                }
                else {
                    result = false;
                }
            }       
            return result;
        } 
        return false;
    }
    
    public void CheckGroupClinet(String groupname) {
        String hrefSummary = "#/Clients/Clients";
        WebCheck.checkHrefIcon(hrefSummary);
    }
    
    public void DelVPNGroup(String groupname) {
        if(cirbtnListView.exists()) {
            cirbtnListView.click();
            MyCommonAPIs.sleepi(2);
        }
        cirbtnAdd.scrollIntoView(true);
        bvpntable.scrollIntoView("{inline:'end'}");
        MyCommonAPIs.sleepi(1);
        editLine(tableTbody, 1, groupname, 1);
        MyCommonAPIs.waitReady();
        modalClickDelete();
        // add waiting time before next adding group to avoid config failed next time
        MyCommonAPIs.sleepi(60);
    }
    
    public void clickEditVPNGroup(String groupname) {
        editLine(tableTbody, 1, groupname, 0);
        MyCommonAPIs.sleepi(2);
    }
    
    
    public String GraphViewLinkStatus() {
        String status;
        System.out.println(graphLink.getAttribute("stroke"));
        
        if(graphLink.getAttribute("stroke").toUpperCase().equals("#FFCA65")) //graphLink.getCssValue("stroke").equals("rgb(255, 202, 101)")
            status = "up";
        else if (graphLink.getAttribute("stroke").toUpperCase().equals("#FC3858")) //graphLink.getCssValue("stroke").equals("rgb(252, 56, 88)")
            status = "down";
        else if (graphLink.getAttribute("stroke").toUpperCase().equals("#B4B2B5")) //graphLink.getCssValue("stroke").equals("rgb(180, 178, 181)")
            status = "in progress";
        else if (graphLink.getAttribute("stroke").toUpperCase().equals("#00D76F")) //graphLink.getCssValue("stroke").equals("rgb(0, 215, 111)")
            status = "p2p";
        else
            status = "wrong";
        return status;
    }
    
    // page1 : Configure Business VPN
    public void setPage1VPNGroupInfo(String GroupName, String Description) {
        inputGroupName.setValue(GroupName);
        inputDescription.setValue(Description);
        clickNext();
    }
    
    public void setPage1VPNGroupInfo(String GroupName, String Description, String MTU) {
        inputGroupName.setValue(GroupName);
        inputDescription.setValue(Description);
        inputMTU.setValue(MTU);
        clickNext();
    }
    
    public boolean EditPage1VPNGroupInfo(String GroupName, String Description, String MTU,boolean throughVPN, String DomainName) {
        boolean Ret_value;
        Ret_value = true;
        String RealName, RealDesc, RealMTU, RealDomain;
        clickBack();
        inputGroupName.setValue(GroupName);
        inputDescription.setValue(Description);
        inputMTU.setValue(MTU);
        setSelected(DomainNamesthroughVPN, throughVPN);
        if (throughVPN) {
            inputDomainName.setValue(DomainName);
        }
        clickNext();
        clickBack();
        
        RealName = inputGroupName.getValue();
        RealDesc = inputDescription.getValue();
        RealMTU = inputMTU.getValue();
        RealDomain = inputDomainName.getValue();
        
        if (!RealName.equals(GroupName)) {
            Ret_value = false;
            logger.info("Error: Edit Group Name failed， Real Group Name=" + RealName + " ， Expect value=" + GroupName );
        }
        
        if (!RealDesc.equals(Description)) {
            Ret_value = false;
            logger.info("Error: Edit Group Name failed， Real Description=" + RealDesc + " ， Expect value=" + Description );
        }
        
        if (!RealMTU.equals(MTU)) {
            Ret_value = false;
            logger.info("Error: Edit Group Name failed， Real MTU=" + RealMTU + " ， Expect value=" + MTU );
        }
        
        if (!RealDomain.equals(DomainName)) {
            Ret_value = false;
            logger.info("Error: Edit Group Name failed， Real DomainName=" + RealDomain + " ， Expect value=" + DomainName );
        }
        
        return Ret_value;
    }
    
    public boolean setPage1VPNGroupInfo(String GroupName, String Description, String MTU, boolean throughVPN, String DomainName) {
        try {
            inputGroupName.setValue(GroupName);
            inputDescription.setValue(Description);
            inputMTU.setValue(MTU);
            setSelected(DomainNamesthroughVPN, throughVPN);
            if (throughVPN) {
                inputDomainName.setValue(DomainName);
            }
            clickNext();
            // check the page go to Add Routers by checking add central router button
            if (btnAddCentralRouter.exists()) {
                return true;
            }
            return false;
        } catch(Throwable e) {
            return false;
        }
    }
    
    // page2 : Add Routers
    public void clickAddCentralRouter() {
        if (btnAddCentralRouter.exists()) {
            btnAddCentralRouter.click();
        } 
        MyCommonAPIs.sleepi(5);
    }
    
    public void clickAddRemoteRouter() {
        if (btnAddRemoteRouter.exists()) {
            System.out.println("add button exists");
            btnAddRemoteRouter.click();
        } 
        MyCommonAPIs.sleepi(5);
    }
    
    public List<String> getTableCol(int col) {
        return getTextsTable(tableTbody, col);
    }
    
    public boolean addCentralRouterModalExist() {
        if (selectNetwork.exists() && selectRouter.exists() && inputVPNNetworks.exists()) {
            return true;
        }
        return false;
    }
    
    public boolean SelectLocation() {
        try {
            selectNetwork.selectOption(WebportalParam.location1);
        } catch(Throwable e) {
            return false;
        }
        if (selectNetwork.getText().equals(WebportalParam.location1)) {
            return true;
        }
        return false;
    }
    
    public boolean SelectLocation(String location) {
        try{
            selectNetwork.selectOption(location);
        } catch(Throwable e) {
            return false;
        }
        if (selectNetwork.getText().equals(location)) {
            return true;
        }
        return false;
    }
    
    public boolean SelectRouter() {
        try {
            selectRouter.selectOption(WebportalParam.ob1deveiceName);
        } catch(Throwable e) {
            return false;
        }
        if (selectRouter.getText().equals(WebportalParam.ob1deveiceName)) {
            return true;
        }
        return false;
    }
    
    public boolean SelectRouter(String routername) {
        try {
            selectRouter.selectOption(routername);
        } catch (Throwable e) {
            return false;
        }
        if (selectRouter.getText().equals(routername)) {
            return true;
        }
        return false;
    }
    
    public boolean SelectVPNCredits(int creditoption) {
        String select;
        switch(creditoption) {
            case 1:
                select = EmployeeHomeSite_1_5;
                break;
            case 2:
                select = MicroOffice_9_45;
                break;
            case 3:
                select = MicroOffice_15_75;
                break;
            case 4:
                select = SmallOffice_25_125;
                break;
            case 5:
                select = SmallOffice_50_250;
                break;
            default:
                select = EmployeeHomeSite_1_5;
        }
        selectVPNCredits.selectOption(select);
        
        if (selectVPNCredits.getText().equals(select)) {
            return true;
        }
        return false;
    }
    
    public boolean SelectRouterMode(boolean employee) {
        if (employee) {
            textEmployeeHome.click();
            if (radioEmployeeHome.isSelected()) {
                System.out.println("employee home selected");
                return true;
            }
            return false;
        }
        else {
            textBranchOffice.click();
            if (radioBranchOffice.isSelected()) {
                System.out.println("branch office selected");
                return true;
            }
            return false;  
        }
    }
    
    // this function is for invisible checkbox in modal content (vlan profile, router isolation, aggressive keepalive)
    public boolean SetSelected(SelenideElement switchbutton, SelenideElement checkboxinput, boolean selected) {
        boolean currentstatus = checkboxinput.isSelected();
        if (currentstatus == selected) {
            return true;
        }
        switchbutton.click();
        currentstatus = checkboxinput.isSelected();
        if (currentstatus == selected) {
            return true;
        }
        return false;
    }
    
    public boolean RouterExist(String location, String routername, String routermode) {
        if (dataTable.exists()) { 
            boolean result = false;
            int index = 0;
            for (String str: getTableCol(2)) {
                if(str.equals(routername)) {
                    result = true;
                    break;
                }
                index++;
            }
            
            if (result) {
                String loc = getTableCol(1).get(index);
                String mode = getTableCol(3).get(index);
                if (loc.equals(location) && mode.equals(routermode)) {
                    result = true;
                }
                else {
                    result = false;
                }
            }       
            return result;
        } 
        return false;
    }
    
    public boolean RouterExist(String location, String routername, String routermode, String ipaddr, String network) {
        if (dataTable.exists()) { 
            boolean result = false;
            int index = 0;
            for (String str: getTableCol(2)) {
                if(str.equals(routername)) {
                    result = true;
                    break;
                }
                index++;
            }
            
            if (result) {
                String loc = getTableCol(1).get(index);
                String mode = getTableCol(3).get(index);
                String ip = getTableCol(4).get(index);
                String net = getTableCol(5).get(index);
                if (loc.equals(location) && mode.equals(routermode) && ip.equals(ipaddr) && net.equals(network)) {
                    result = true;
                }
                else {
                    result = false;
                }
            }       
            return result;
        } 
        return false;
    }
    
    public boolean RouterExist(String location, String routername, String routermode, String ipaddr, String network, String creditstatus) {
        if (dataTable.exists()) { 
            boolean result = false;
            int index = 0;
            for (String str: getTableCol(2)) {
                if(str.equals(routername)) {
                    result = true;
                    break;
                }
                index++;
            }
            
            if (result) {
                String loc = getTableCol(1).get(index);
                String mode = getTableCol(3).get(index);
                String ip = getTableCol(4).get(index);
                String net = getTableCol(5).get(index);
                String credit = getTableCol(7).get(index);
                System.out.println(credit);
                if (loc.equals(location) && mode.equals(routermode) && ip.equals(ipaddr) && net.equals(network) && credit.equals(creditstatus)) {
                    result = true;
                }
                else {
                    result = false;
                }
            }       
            return result;
        } 
        return false;
    }
    
    public boolean PopWarningModal() {
        if (btnModalOK.exists()) {
            if (!ModalWarning.exists()) {
                return false;
            }
            btnModalOK.click();
            waitReady();
            return true;
        }
        return false;
    }
    
    public void setPage2AddCentralRouter() {
        clickAddCentralRouter();
        SelectLocation();
        SelectRouter();
        inputVPNNetworks.setValue(WebportalParam.ob1VPNNetworks);
        modalClickSave();
        waitReady();
    }
    
    public void setPage2AddCentralRouter(String location, String router) {
        clickAddCentralRouter();
        SelectLocation(location);
        SelectRouter(router);
        modalClickSave();
        waitReady();
    }
    
    public boolean CheckVlanForCentralRouter(String location, String router, String vlan) {
        clickAddCentralRouter();
        SelectLocation(location);
        SelectRouter(router);
        if (selectVLAN.isDisplayed()) {
            //selectVLAN.selectOption(vlan);
            //if (!ipaddress.equals("")) {
            //    inputRouterIp.setValue(ipaddress);
           // }
            //if (!ipsubnet.equals("")) {
             //   inputRouterIpSubnet.setValue(ipsubnet);
            //}
            modalClickCancel();
            return true;
        } else {
            modalClickCancel();
            return false;
        }
        
    }
    
    public void setPage2DelRouter(String routername) {
        editLine(tableTbody, 2, routername, 1);
        modalClickDelete();
    }
    
    public void setPage2AddRemoteRouter() {
        clickAddRemoteRouter();
        SelectLocation(WebportalParam.location2);
        SelectRouter(WebportalParam.ob2deveiceName);
        SelectVPNCredits(1);
        modalClickSave();
    }
    
    public void setPage2AddRemoteRouter(String location, String routername) {
        clickAddRemoteRouter();
        SelectLocation(location);
        SelectRouter(routername);
        SelectVPNCredits(1);
        modalClickSave();
    }
    
    public void setPage2AddRemoteRouter(String location, String routername, int creditoption) {
        clickAddRemoteRouter();
        SelectLocation(location);
        SelectRouter(routername);
        SelectVPNCredits(creditoption);
        modalClickSave();
    }
    
    public void setPage2AddRemoteRouter(String location, String routername, int creditoption, boolean routermode) {
        clickAddRemoteRouter();
        SelectLocation(location);
        SelectRouter(routername);
        SelectVPNCredits(creditoption);
        SelectRouterMode(routermode);
        modalClickSave();
    }
    
    public void setPage2AddRemoteRouter(String location, String routername, int creditoption, boolean routermode, boolean isolation) {
        clickAddRemoteRouter();
        SelectLocation(location);
        SelectRouter(routername);
        SelectVPNCredits(creditoption);
        SelectRouterMode(routermode);
        SetSelected(RouterIsolation, inputRouterIsolation, isolation);
        modalClickSave();
    }
    
    public void setPage2EditRemoteRouter(String routername, boolean routermode) {
        editLine(tableTbody, 2, routername, 0);
        MyCommonAPIs.sleepi(3);
        SelectRouterMode(routermode);
        modalClickSave();
    }
    
    public void setPage2EditRemoteRouter(String routername, boolean routermode, boolean isolation) {
        editLine(tableTbody, 2, routername, 0);
        MyCommonAPIs.sleepi(3);
        SelectRouterMode(routermode);
        SetSelected(RouterIsolation, inputRouterIsolation, isolation);
        modalClickSave();
    }
    
    public void setPage2EditRemoteRouter(String routername, boolean routermode, int creditoption, boolean aggressive) {
        editLine(tableTbody, 2, routername, 0);
        MyCommonAPIs.sleepi(3);
        SelectRouterMode(routermode);
        SelectVPNCredits(creditoption);
        SetSelected(AggrKeepAlive, inputAggrKeepAlive, aggressive);
        modalClickSave();
    }
    
    public void setPage2EditRemoteRouter(String routername, String ipaddress, String ipsubnet) {
        editLine(tableTbody, 2, routername, 0);
        MyCommonAPIs.sleepi(3);
        inputRouterIp.setValue(ipaddress);
        inputRouterIpSubnet.setValue(ipsubnet);
        modalClickSave();
    }
    
    public void setPage2EditRemoteRouter(String routername, String vlan, String ipaddress, String ipsubnet) {
        editLine(tableTbody, 2, routername, 0);
        MyCommonAPIs.sleepi(3);
        selectVLAN.selectOption(vlan);
        if (!ipaddress.equals("")) {
            inputRouterIp.setValue(ipaddress);
        }
        if (!ipsubnet.equals("")) {
            inputRouterIpSubnet.setValue(ipsubnet);
        }
        modalClickSave();
    }
    
    // page3 : Wireless Settings
    public boolean setPage3WirelessSettings(boolean enablevpn, String ssid, int security, String password) {
        try {
            setSelected(VPNWirelessNetwork, enablevpn);
            inputWirelessSSID.setValue(ssid);
            selectSecurity.selectOption(security); // 0: None, 1: WPA2-PSK, 2: WPA-PSK/WPA2-PSK, 3: WPA2-Enterprise
            inputPassword.setValue(password);
            return true;
        }catch(Throwable e) {
            return false;
        }
    }
    
    public boolean setPage3WirelessSettingsEnterprise(boolean enablevpn, String ssid, String ip, String key) {
        try {
            setSelected(VPNWirelessNetwork, enablevpn);
            inputWirelessSSID.setValue(ssid);
            selectSecurity.selectOption(3); // 0: None, 1: WPA2-PSK, 2: WPA-PSK/WPA2-PSK, 3: WPA2-Enterprise
            radiusip.setValue(ip);
            radiuskey.setValue(key);
            return true;
        }catch(Throwable e) {
            return false;
        }
    }
    
    // page4 : Access Control
    public boolean setPage4AccessControl(String device, String macaddr, int policy, String router) {
        try {
            inputDeviceName.setValue(device);
            inputMACAddress.setValue(macaddr);
            selectRules.selectOption(policy);
            selectDevice.selectOption(router);
            modalClickSave();
            return true;
        } catch(Throwable e) {
            return false;
        }
        
    }
    
    public boolean delAccessControl(String device) {
        try {
            editLine(tableTbody, 1, device, 1);
            sleep(1000);
            modalClickDelete();
            sleep(3000);
            return true;
        } catch(Throwable e) {
            return false;
        }
    }
    
    public boolean PolicyExist(String device, String macaddr, String rule, String router) {
        if (dataTable.exists()) { 
            boolean result = false;
            int index = 0;
            for (String str: getTableCol(1)) {
                if(str.equals(device)) {
                    result = true;
                    break;
                }
                index++;
            }
            
            if (result) {
                String _mac = getTableCol(2).get(index);
                String _rule = getTableCol(3).get(index);
                String _router = getTableCol(4).get(index);
                if (_mac.equals(macaddr) && _rule.equals(rule) && _router.equals(router)) {
                    result = true;
                }
                else {
                    result = false;
                }
            }       
            return result;
        } 
        return false;
    }
    
    // central router and remote router page
    public boolean RouterHealth(String router, boolean central, String health) {
        if (dataTable.exists()) { 
            boolean result = false;
            int index = 0;
            for (String str: getTableCol(2)) {
                if(str.equals(router)) {
                    result = true;
                    break;
                }
                index++;
            }
            
            int col = 3;
            if(!central) col = 4;
                
            if (result) {
                String _health = getTableCol(col).get(index);
                if (_health.equals(health)) {
                    result = true;
                }
                else {
                    result = false;
                }
            }       
            return result;
        } 
        return false;
    }
    
}
