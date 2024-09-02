package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.RoutersElement;

public class RoutersPage extends RoutersElement {
    Logger logger = Logger.getLogger("RoutersPage");

    public RoutersPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        refresh();
        WebCheck.checkUrl(URLParam.hrefRouters);
        waitReady();
        MyCommonAPIs.sleepi(5);
    }

    public void gotoVPNSettingsPage() {
        refresh();
        WebCheck.checkUrl(URLParam.hrefVPNSettings);
        waitReady();
        MyCommonAPIs.sleepi(5);
    }

    /**
     * open first device
     */
    public void openBR() {
        waitReady();
        editLine(sTableDevices, 1, 1);
    }

    public List<String> getGroups() {
        List<String> groupList = new ArrayList<String>();
        for (SelenideElement se : $$(txtGroupName)) {
            String sItem = se.getAttribute("title");
            logger.info(sItem);
            groupList.add(sItem);
        }

        return groupList;
    }

    public void createVPNGroup(String groupName) {
        logger.info("check to add group: " + groupName);
        if (getGroups().contains(groupName))
            return;

        clickAdd();
        if (btnOK.exists()) {
            btnOK.click();
        }
        MyCommonAPIs.sleepi(10);
        txtName.setValue(groupName);
        btnSave.click();
        waitReady();
    }

    public void clickAdd() {
        for (int i = 0; i < 2; i++) {
            if (btnCreate.exists()) {
                btnCreate.click();
            } else {
                btnAddIcon.click();
            }
            waitReady();
            // skip license issue
            if (!txtName.exists()) {
                clickBoxFirstButton();
            } else {
                break;
            }
        }
    }

    public List<String> getDevices(String groupName) {
        List<String> devList = new ArrayList<String>();
        String toClick = String.format(txtDeviceList, groupName);
        if ($x(toClick).exists())
            return getTexts(toClick);
        else
            return devList;
    }

    public void deleteOneDevice(String groupName) {
        int iGroup = getGroups().indexOf(groupName);
        if (iGroup == -1)
            return;
        if (getDevices(groupName).size() < 1)
            return;
        String toClick = String.format(btnDeviceIcon, groupName);
        $$x(toClick).last().click();
        selectGroupDeviceMenu(groupName, 1);
        clickYesNo(true);
    }

    public void deleteGroup(String groupName) {
        int iGroup = getGroups().indexOf(groupName);
        if (iGroup == -1)
            return;
        String toClick = String.format(btnDeleteGroup, groupName);
        for (int i = 0; i < 3; i++) {
            deleteOneDevice(groupName);
        }

        $x(toClick).click();
        clickYesNo(true);
    }

    public void deleteGroupNew(String groupName) {
        String toClick = "";
        if ($x(String.format(txtGroupCircel + "/../..", groupName)).getAttribute("class").contains("VpnPoolCol")) {
            toClick = String.format(btnDeleteGroup, groupName);
        } else if ($x(String.format(txtGroupCircel + "/..", groupName)).getAttribute("class").contains("VpnPoolCol")) {
            toClick = String.format(btnDeleteGroupExpired, groupName);
        } else {
            toClick = String.format(btnDeleteGroupNew, groupName);
        }
        $x(toClick).click();
        clickYesNo(true);
    }

    public void deleteAllGroups() {
        takess("delete All data");
        List<String> groupList = getGroups();
        for (String groupName : groupList) {
            deleteGroup(groupName);
        }
    }

    public boolean checkVpnGroupNumLimit(String accountType) {
        boolean result = false;
        btnAddGroup.click();
        MyCommonAPIs.sleepi(10);
        if ((accountType.contains("admin") | accountType.contains("close"))
                && getText($x(warningOk + "/../..//p")).contains("You do not have sufficient credits to add a new group")) {
            result = true;
            if (accountType.contains("close")) {
                closewarning.click();
            } else {
                $x(warningOk).click();
            }
            logger.info("Exceeding the upper limit.");
        } else if ((accountType.contains("normal") | accountType.contains("buy now"))
                && $x(warningOk + "/../..//p").getText().contains("You do not have sufficient credits to add a new group")) {
            if (accountType.contains("buy now") && $x(warningOk).exists()) {
                result = true;
                logger.info("Warning displayed.");
                if (closewarning.exists()) {
                    closewarning.click();
                }
            } else {
                $x(warningOk).click();
                waitReady();
                String url = MyCommonAPIs.getCurrentUrl();
                if (url.contains("billing") && url.contains("netgear")) {
                    result = true;
                    logger.info("Its into billing page now.");
                    if (accountType.contains("<<")) {
                        Selenide.back();
                    }
                }
            }
        } else {
            if (closecreategroup.exists()) {
                closecreategroup.click();
            } else if (closewarning.exists()) {
                closewarning.click();
            }
        }
        return result;
    }

    public boolean checkVpnGroupStatus(String groupName) {
        boolean result = false;
        String status = String.format(vpngroupstatus, groupName);
        if (getText($x(status)).contains("Free")) {
            result = true;
            logger.info("Vpn group status is free.");
        }
        return result;
    }

    public boolean checkExpiredVpnGroupStatus(String groupName) {
        boolean result = false;
        String status = String.format(expiredvpnstatus, groupName);
        if ($x(status).exists() && expiredwarning.exists()) {
            result = true;
            logger.info("Vpn group status is expired.");
        }
        return result;
    }

    public boolean checkVpnWarningMessage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (vpnwarning.exists()) {
            if (getText(vpnwarning).contains("Your NETGEAR Insight Subscription comes with 1 FREE VPN Group Credit")) {
                settings.click();
                waitReady();
                if (getText(vpnwarning).contains("Your NETGEAR Insight Subscription comes with 1 FREE VPN Group Credit")) {
                    result = true;
                    logger.info("Warning found.");
                }
            }
        }
        return result;
    }

    public boolean checkVpnExpiredWarningMessage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (vpnexpiredwarning.exists()) {
            if (getText(vpnexpiredwarning).contains("One or more of your VPN Services have expired.")) {
                settings.click();
                waitReady();
                if (getText(vpnexpiredwarning).contains("One or more of your VPN Services have expired.")) {
                    result = true;
                    logger.info("Warning found.");
                }
            }
        }
        return result;
    }

    public void closeVpnWarningMessage() {
        MyCommonAPIs.sleepi(10);
        if (vpnwarning.exists()) {
            vpnwarningclose.click();
        }
    }

    public boolean checkVpnGroupIsExist(String groupName) {
        boolean result = false;
        String groupele = String.format(vpngrouptitle, groupName);
        if ($x(groupele).exists()) {
            result = true;
            logger.info("Vpn group existed.");
        }
        return result;
    }

    ////////////// below functions move to RoutersBusinessVPNPage.java ////////////
    //////////////////////// new functions for SSID-WAN start ////////////////////////
    /*
     * // Common methods
     * public void clickNext() {
     * if (btnNext.exists()) {
     * btnNext.click();
     * }
     * waitReady();
     * }
     * 
     * public void clickBack() {
     * if (btnBack.exists()) {
     * btnBack.click();
     * }
     * waitReady();
     * }
     * 
     * public void modalClickSave() {
     * if (btnModalSave.exists()) {
     * btnModalSave.click();
     * }
     * waitReady();
     * }
     * 
     * public void modalClickCancel() {
     * if (btnModalCancel.exists()) {
     * btnModalCancel.click();
     * }
     * waitReady();
     * }
     * 
     * public void modalClickDelete() {
     * if (btnModalDelete.exists()) {
     * btnModalDelete.click();
     * }
     * waitReady();
     * }
     * 
     * public void modalClickOK() {
     * if (btnModalOK.exists()) {
     * btnModalOK.click();
     * }
     * waitReady();
     * }
     * 
     * public void SetCheckboxSelected(SelenideElement element, boolean selected) {
     * setSelected(element, selected);
     * }
     * 
     * // before Configure Business VPN
     * public void clickSettings() {
     * if (settings.exists()) {
     * settings.click();
     * }
     * waitReady();
     * }
     * 
     * // page 0 : Business VPN
     * public void clickAddVPNGroup() {
     * if (cirbtnAdd.exists()) {
     * cirbtnAdd.click();
     * }
     * waitReady();
     * }
     * 
     * public boolean GroupExist(String groupname, String ssid, String centralrouter) {
     * if (dataTable.exists()) {
     * boolean result = false;
     * int index = 0;
     * for (String str: getTableCol(1)) {
     * if(str.equals(groupname)) {
     * result = true;
     * break;
     * }
     * index++;
     * }
     * 
     * if (result) {
     * String _ssid = getTableCol(2).get(index);
     * String _central = getTableCol(3).get(index);
     * if (_ssid.equals(ssid) && _central.equals(centralrouter) ) {
     * result = true;
     * }
     * else {
     * result = false;
     * }
     * }
     * return result;
     * }
     * return false;
     * }
     * 
     * public boolean GroupExist(String groupname, String ssid, String centralrouter, String VPNhealth) {
     * if (dataTable.exists()) {
     * boolean result = false;
     * int index = 0;
     * for (String str: getTableCol(1)) {
     * if(str.equals(groupname)) {
     * result = true;
     * break;
     * }
     * index++;
     * }
     * 
     * if (result) {
     * String _ssid = getTableCol(2).get(index);
     * String _central = getTableCol(3).get(index);
     * String _health = getTableCol(6).get(index);
     * if (_ssid.equals(ssid) && _central.equals(centralrouter) && _health.equals(VPNhealth)) {
     * result = true;
     * }
     * else {
     * result = false;
     * }
     * }
     * return result;
     * }
     * return false;
     * }
     * 
     * public void DelVPNGroup(String groupname) {
     * if(cirbtnGraphView.exists())
     * editLine(tableTbody, 1, groupname, 1);
     * else
     * cirbtnDelete.click();
     * sleep(1000);
     * modalClickDelete();
     * sleep(1000);
     * }
     * 
     * public void clickEditVPNGroup(String groupname) {
     * editLine(tableTbody, 1, groupname, 0);
     * MyCommonAPIs.sleepi(2);
     * }
     * 
     * // this function cannot work properly due to Seleninum issue for svg element
     * public String GraphViewLinkStatus() {
     * String status;
     * SelenideElement targetsvg = $x("(//*[name()='svg'])[7]");
     * System.out.println(targetsvg);
     * // issue: Selenium cannot found the svg element we want but chrome can found it by xpath or cssselector
     * SelenideElement currlink = $x("(//*[name()='svg'])[7]//*[local-name()='path']"); //xpath
     * //SelenideElement currlink = $("#abstractGraph svg g path"); // cssselector
     * System.out.println(currlink);
     * if(currlink.getCssValue("fill").equals("rgb(255, 202, 101)"))
     * status = "up";
     * else if (currlink.getCssValue("fill").equals("rgb(252, 56, 88)"))
     * status = "down";
     * else if (currlink.getCssValue("fill").equals("rgb(180, 178, 181)"))
     * status = "in progress";
     * else if (currlink.getCssValue("fill").equals("rgb(0, 215, 111)"))
     * status = "p2p";
     * else
     * status = "wrong";
     * return status;
     * 
     * }
     * 
     * // page1 : Configure Business VPN
     * public void setPage1VPNGroupInfo(String GroupName, String Description) {
     * inputGroupName.setValue(GroupName);
     * inputDescription.setValue(Description);
     * clickNext();
     * }
     * 
     * public void setPage1VPNGroupInfo(String GroupName, String Description, String MTU) {
     * inputGroupName.setValue(GroupName);
     * inputDescription.setValue(Description);
     * inputMTU.setValue(MTU);
     * clickNext();
     * }
     * 
     * public boolean setPage1VPNGroupInfo(String GroupName, String Description, String MTU, boolean throughVPN, String
     * DomainName) {
     * try {
     * inputGroupName.setValue(GroupName);
     * inputDescription.setValue(Description);
     * inputMTU.setValue(MTU);
     * setSelected(DomainNamesthroughVPN, throughVPN);
     * if (throughVPN) {
     * inputDomainName.setValue(DomainName);
     * }
     * clickNext();
     * // check the page go to Add Routers by checking add central router button
     * if (btnAddCentralRouter.exists()) {
     * return true;
     * }
     * return false;
     * } catch(Throwable e) {
     * return false;
     * }
     * }
     * 
     * // page2 : Add Routers
     * public void clickAddCentralRouter() {
     * if (btnAddCentralRouter.exists()) {
     * btnAddCentralRouter.click();
     * }
     * waitReady();
     * }
     * 
     * public void clickAddRemoteRouter() {
     * if (btnAddRemoteRouter.exists()) {
     * btnAddRemoteRouter.click();
     * }
     * waitReady();
     * }
     * 
     * public List<String> getTableCol(int col) {
     * return getTextsTable(tableTbody, col);
     * }
     * 
     * public boolean addCentralRouterModalExist() {
     * if (selectNetwork.exists() && selectRouter.exists() && inputVPNNetworks.exists()) {
     * return true;
     * }
     * return false;
     * }
     * 
     * public boolean SelectLocation() {
     * try {
     * selectNetwork.selectOption(WebportalParam.location1);
     * } catch(Throwable e) {
     * return false;
     * }
     * if (selectNetwork.getText().equals(WebportalParam.location1)) {
     * return true;
     * }
     * return false;
     * }
     * 
     * public boolean SelectLocation(String location) {
     * try{
     * selectNetwork.selectOption(location);
     * } catch(Throwable e) {
     * return false;
     * }
     * if (selectNetwork.getText().equals(location)) {
     * return true;
     * }
     * return false;
     * }
     * 
     * public boolean SelectRouter() {
     * try {
     * selectRouter.selectOption(WebportalParam.ob1deveiceName);
     * } catch(Throwable e) {
     * return false;
     * }
     * if (selectRouter.getText().equals(WebportalParam.ob1deveiceName)) {
     * return true;
     * }
     * return false;
     * }
     * 
     * public boolean SelectRouter(String routername) {
     * try {
     * selectRouter.selectOption(routername);
     * } catch (Throwable e) {
     * return false;
     * }
     * if (selectRouter.getText().equals(routername)) {
     * return true;
     * }
     * return false;
     * }
     * 
     * public boolean SelectVPNCredits(int creditoption) {
     * String select;
     * switch(creditoption) {
     * case 1:
     * select = EmployeeHomeSite_1_5;
     * break;
     * case 2:
     * select = MicroOffice_9_45;
     * break;
     * case 3:
     * select = MicroOffice_15_75;
     * break;
     * case 4:
     * select = SmallOffice_25_125;
     * break;
     * case 5:
     * select = SmallOffice_50_250;
     * break;
     * default:
     * select = EmployeeHomeSite_1_5;
     * }
     * selectVPNCredits.selectOption(select);
     * 
     * if (selectVPNCredits.getText().equals(select)) {
     * return true;
     * }
     * return false;
     * }
     * 
     * public boolean SelectRouterMode(boolean employee) {
     * if (employee) {
     * textEmployeeHome.click();
     * if (radioEmployeeHome.isSelected()) {
     * System.out.println("employee home selected");
     * return true;
     * }
     * return false;
     * }
     * else {
     * textBranchOffice.click();
     * if (radioBranchOffice.isSelected()) {
     * System.out.println("branch office selected");
     * return true;
     * }
     * return false;
     * }
     * }
     * 
     * // this function is for invisible checkbox in modal content (vlan profile, router isolation, aggressive keepalive)
     * public boolean SetSelected(SelenideElement switchbutton, SelenideElement checkboxinput, boolean selected) {
     * boolean currentstatus = checkboxinput.isSelected();
     * if (currentstatus == selected) {
     * return true;
     * }
     * switchbutton.click();
     * currentstatus = checkboxinput.isSelected();
     * if (currentstatus == selected) {
     * return true;
     * }
     * return false;
     * }
     * 
     * public boolean RouterExist(String location, String routername, String routermode) {
     * if (dataTable.exists()) {
     * boolean result = false;
     * int index = 0;
     * for (String str: getTableCol(2)) {
     * if(str.equals(routername)) {
     * result = true;
     * break;
     * }
     * index++;
     * }
     * 
     * if (result) {
     * String loc = getTableCol(1).get(index);
     * String mode = getTableCol(3).get(index);
     * if (loc.equals(location) && mode.equals(routermode)) {
     * result = true;
     * }
     * else {
     * result = false;
     * }
     * }
     * return result;
     * }
     * return false;
     * }
     * 
     * public boolean RouterExist(String location, String routername, String routermode, String ipaddr, String network) {
     * if (dataTable.exists()) {
     * boolean result = false;
     * int index = 0;
     * for (String str: getTableCol(2)) {
     * if(str.equals(routername)) {
     * result = true;
     * break;
     * }
     * index++;
     * }
     * 
     * if (result) {
     * String loc = getTableCol(1).get(index);
     * String mode = getTableCol(3).get(index);
     * String ip = getTableCol(4).get(index);
     * String net = getTableCol(5).get(index);
     * if (loc.equals(location) && mode.equals(routermode) && ip.equals(ipaddr) && net.equals(network)) {
     * result = true;
     * }
     * else {
     * result = false;
     * }
     * }
     * return result;
     * }
     * return false;
     * }
     * 
     * public boolean PopWarningModal() {
     * if (btnModalOK.exists()) {
     * if (!ModalWarning.exists()) {
     * return false;
     * }
     * btnModalOK.click();
     * waitReady();
     * return true;
     * }
     * return false;
     * }
     * 
     * public void setPage2AddCentralRouter() {
     * clickAddCentralRouter();
     * SelectLocation();
     * SelectRouter();
     * inputVPNNetworks.setValue(WebportalParam.ob1VPNNetworks);
     * modalClickSave();
     * waitReady();
     * }
     * 
     * public void setPage2AddCentralRouter(String location, String router) {
     * clickAddCentralRouter();
     * SelectLocation(location);
     * SelectRouter(router);
     * modalClickSave();
     * waitReady();
     * }
     * 
     * public void setPage2DelRouter(String routername) {
     * editLine(tableTbody, 2, routername, 1);
     * modalClickDelete();
     * }
     * 
     * public void setPage2AddRemoteRouter() {
     * clickAddRemoteRouter();
     * SelectLocation(WebportalParam.location2);
     * SelectRouter(WebportalParam.ob2deveiceName);
     * SelectVPNCredits(1);
     * modalClickSave();
     * }
     * 
     * public void setPage2AddRemoteRouter(String location, String routername) {
     * clickAddRemoteRouter();
     * SelectLocation(location);
     * SelectRouter(routername);
     * SelectVPNCredits(1);
     * modalClickSave();
     * }
     * 
     * public void setPage2AddRemoteRouter(String location, String routername, int creditoption) {
     * clickAddRemoteRouter();
     * SelectLocation(location);
     * SelectRouter(routername);
     * SelectVPNCredits(creditoption);
     * modalClickSave();
     * }
     * 
     * public void setPage2AddRemoteRouter(String location, String routername, int creditoption, boolean routermode) {
     * clickAddRemoteRouter();
     * SelectLocation(location);
     * SelectRouter(routername);
     * SelectVPNCredits(creditoption);
     * SelectRouterMode(routermode);
     * modalClickSave();
     * }
     * 
     * public void setPage2AddRemoteRouter(String location, String routername, int creditoption, boolean routermode, boolean
     * isolation) {
     * clickAddRemoteRouter();
     * SelectLocation(location);
     * SelectRouter(routername);
     * SelectVPNCredits(creditoption);
     * SelectRouterMode(routermode);
     * SetSelected(RouterIsolation, inputRouterIsolation, isolation);
     * modalClickSave();
     * }
     * 
     * public void setPage2EditRemoteRouter(String routername, boolean routermode) {
     * editLine(tableTbody, 2, routername, 0);
     * MyCommonAPIs.sleepi(1);
     * SelectRouterMode(routermode);
     * modalClickSave();
     * }
     * 
     * public void setPage2EditRemoteRouter(String routername, boolean routermode, boolean isolation) {
     * editLine(tableTbody, 2, routername, 0);
     * MyCommonAPIs.sleepi(1);
     * SelectRouterMode(routermode);
     * SetSelected(RouterIsolation, inputRouterIsolation, isolation);
     * modalClickSave();
     * }
     * 
     * public void setPage2EditRemoteRouter(String routername, boolean routermode, int creditoption, boolean aggressive) {
     * editLine(tableTbody, 2, routername, 0);
     * MyCommonAPIs.sleepi(1);
     * SelectRouterMode(routermode);
     * SelectVPNCredits(creditoption);
     * SetSelected(AggrKeepAlive, inputAggrKeepAlive, aggressive);
     * modalClickSave();
     * }
     * 
     * public void setPage2EditRemoteRouter(String routername, String ipaddress, String ipsubnet) {
     * editLine(tableTbody, 2, routername, 0);
     * MyCommonAPIs.sleepi(1);
     * inputRouterIp.setValue(ipaddress);
     * inputRouterIpSubnet.setValue(ipsubnet);
     * modalClickSave();
     * }
     * 
     * public void setPage2EditRemoteRouter(String routername, String vlan, String ipaddress, String ipsubnet) {
     * editLine(tableTbody, 2, routername, 0);
     * MyCommonAPIs.sleepi(1);
     * selectVLAN.selectOption(vlan);
     * inputRouterIp.setValue(ipaddress);
     * inputRouterIpSubnet.setValue(ipsubnet);
     * modalClickSave();
     * }
     * 
     * // page3 : Wireless Settings
     * public boolean setPage3WirelessSettings(boolean enablevpn, String ssid, int security, String password) {
     * try {
     * setSelected(VPNWirelessNetwork, enablevpn);
     * inputWirelessSSID.setValue(ssid);
     * selectSecurity.selectOption(security); // 0: None, 1: WPA2-PSK, 2: WPA-PSK/WPA2-PSK, 3: WPA2-Enterprise
     * inputPassword.setValue(password);
     * return true;
     * }catch(Throwable e) {
     * return false;
     * }
     * }
     * 
     * // page4 : Access Control
     * public boolean setPage4AccessControl(String device, String macaddr, int policy, String router) {
     * try {
     * inputDeviceName.setValue(device);
     * inputMACAddress.setValue(macaddr);
     * selectRules.selectOption(policy);
     * selectDevice.selectOption(router);
     * modalClickSave();
     * return true;
     * } catch(Throwable e) {
     * return false;
     * }
     * 
     * }
     * 
     * public boolean delAccessControl(String device) {
     * try {
     * editLine(tableTbody, 1, device, 1);
     * sleep(1000);
     * modalClickDelete();
     * sleep(3000);
     * return true;
     * } catch(Throwable e) {
     * return false;
     * }
     * }
     * 
     * public boolean PolicyExist(String device, String macaddr, String rule, String router) {
     * if (dataTable.exists()) {
     * boolean result = false;
     * int index = 0;
     * for (String str: getTableCol(1)) {
     * if(str.equals(device)) {
     * result = true;
     * break;
     * }
     * index++;
     * }
     * 
     * if (result) {
     * String _mac = getTableCol(2).get(index);
     * String _rule = getTableCol(3).get(index);
     * String _router = getTableCol(4).get(index);
     * if (_mac.equals(macaddr) && _rule.equals(rule) && _router.equals(router)) {
     * result = true;
     * }
     * else {
     * result = false;
     * }
     * }
     * return result;
     * }
     * return false;
     * }
     */
    //////////////////////// new functions for SSID-WAN end ////////////////////////

    public boolean lastknowninfoverify() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (lastKnownInfo.exists() && lastKnownInfostarsym.exists() && upTimedeviceDashVerify.exists() && upTimedeviceDashVerify1.exists()) {
            result = true;
            logger.info("Device last known information is verified.");
        }
        return result;
    }

    // AddedByPratik
    public boolean verfiyIPSecVPNonVPNSettingsPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoPage();
        MyCommonAPIs.sleepi(30);
        waitElement(connectedTextVerify);
        if (connectedTextVerify.exists() && greenColorSymbol.exists()) {
            vpnSetting.click();
            MyCommonAPIs.sleepi(30);
            waitElement(ipSecVPNTab);
            if (ipSecVPNTab.exists() && headerIpSecWizard.exists()) {
                logger.info("IP Sec Text Verified on VPN Setting page.");
                result = true;
            }
        }
        return result;
    }

    // AddedByPratik
    public boolean verfiyhuRouterHavingOptionAsPRDevicesAddedInLocation(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoPage();
        MyCommonAPIs.sleepi(30);
        waitElement(connectedTextVerify);
        if (connectedTextVerify.exists() && greenColorSymbol.exists()) {
            vpnSetting.click();
            MyCommonAPIs.sleepi(30);
            waitElement(ipSecVPNTab);
            if (ipSecVPNTab.exists() && headerIpSecWizard.exists()) {
                logger.info("IP Sec Text Verified on VPN Setting page.");
                addVPNGroup.click();
                MyCommonAPIs.sleepi(10);
                waitElement(verifYTexTConfigureIPSec);
                inputGroupName.sendKeys(map.get("Group Name"));
                MyCommonAPIs.sleepi(1);
                inputDescription.sendKeys(map.get("Description"));
                MyCommonAPIs.sleepi(1);
                waitElement(defaultIPSecProfile);
                defaultIPSecProfile.scrollTo();
                System.out.println("Step1:");
                MyCommonAPIs.sleepi(1);
                waitElement(verifYTexTConfigureIPSec);
                System.out.println("Step2:");
                MyCommonAPIs.sleepi(1);
                waitElement(hubRouterText);
                System.out.println("Step3:");
                if (defaultIPSecProfile.exists() && verifYTexTConfigureIPSec.exists() && hubRouterText.exists()) {
                    System.out.println("Step4:");
                    selectDeviceName.selectOption(map.get("Serial Number"));
                    MyCommonAPIs.sleepi(1);
                    System.out.println("Step5:");
                    logger.info("HubRouter is having option of all available PR devices under location");
                    result = true;
                }
            }
        }
        return result;
    }

    // AddedByPratik
    public boolean verfiyafterSelectingHubRouterAsPRDEvicEthenIPSecProfileSelectsautomatically(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoPage();
        MyCommonAPIs.sleepi(30);
        waitElement(connectedTextVerify);
        if (connectedTextVerify.exists() && greenColorSymbol.exists()) {
            vpnSetting.click();
            MyCommonAPIs.sleepi(30);
            waitElement(ipSecVPNTab);
            if (ipSecVPNTab.exists() && headerIpSecWizard.exists()) {
                logger.info("IP Sec Text Verified on VPN Setting page.");
                addVPNGroup.click();
                MyCommonAPIs.sleepi(10);
                waitElement(verifYTexTConfigureIPSec);
                inputGroupName.sendKeys(map.get("Group Name"));
                MyCommonAPIs.sleepi(1);
                inputDescription.sendKeys(map.get("Description"));
                MyCommonAPIs.sleepi(1);
                waitElement(defaultIPSecProfile);
                defaultIPSecProfile.scrollTo();
                System.out.println("Step1:");
                MyCommonAPIs.sleepi(1);
                waitElement(verifYTexTConfigureIPSec);
                System.out.println("Step2:");
                MyCommonAPIs.sleepi(1);
                waitElement(hubRouterText);
                System.out.println("Step3:");
                if (defaultIPSecProfile.exists() && verifYTexTConfigureIPSec.exists() && hubRouterText.exists()) {
                    System.out.println("Step4:");
                    selectDeviceName.selectOption(map.get("Serial Number"));
                    MyCommonAPIs.sleepi(1);
                    System.out.println("Step5:");
                    SelenideElement v1 = defaultOptionIPSecProfile.shouldBe(Condition.visible);
                    System.out.println("Step6:");
                    if (v1.isDisplayed()) {
                        System.out.println("Step7:");
                        logger.info("after Selecting HubRouter As PR Device then IPSec Profile Selects automatically");
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    // AddedByPratik
    public boolean verfiySpokeRouterHasLocationListed(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoPage();
        MyCommonAPIs.sleepi(30);
        waitElement(connectedTextVerify);
        if (connectedTextVerify.exists() && greenColorSymbol.exists()) {
            vpnSetting.click();
            MyCommonAPIs.sleepi(30);
            waitElement(ipSecVPNTab);
            if (ipSecVPNTab.exists() && headerIpSecWizard.exists()) {
                logger.info("IP Sec Text Verified on VPN Setting page.");
                addVPNGroup.click();
                MyCommonAPIs.sleepi(10);
                waitElement(verifYTexTConfigureIPSec);
                inputGroupName.sendKeys(map.get("Group Name"));
                MyCommonAPIs.sleepi(1);
                inputDescription.sendKeys(map.get("Description"));
                MyCommonAPIs.sleepi(1);
                waitElement(defaultIPSecProfile);
                defaultIPSecProfile.scrollTo();
                System.out.println("Step1:");
                MyCommonAPIs.sleepi(1);
                waitElement(verifYTexTConfigureIPSec);
                System.out.println("Step2:");
                MyCommonAPIs.sleepi(1);
                waitElement(hubRouterText);
                System.out.println("Step3:");
                selectDeviceName.selectOption(map.get("Serial Number"));
                MyCommonAPIs.sleepi(1);
                System.out.println("Step4:");
                nextButton.click();
                MyCommonAPIs.sleepi(10);
                if (textLanNetworkSetting.exists()) {
                    System.out.println("Step5:");
                    inputLANIPAddress.sendKeys(map.get("LAN IP Address"));
                    MyCommonAPIs.sleepi(1);
                    System.out.println("Step6:");
                    inputLANSubnet.sendKeys(map.get("LAN Subnet"));
                    MyCommonAPIs.sleepi(1);
                    nextButton.click();
                    MyCommonAPIs.sleepi(10);
                    System.out.println("Step7:");
                    if (spokeRouterDesc.exists() && headerSpokeRouter.exists()) {
                        buttonAddSpokeRouter.click();
                        MyCommonAPIs.sleepi(1);
                        noDataAvailable.exists();
                        System.out.println("Step8:");
                        buttonAddNewSpokeRouter.click();
                        MyCommonAPIs.sleepi(1);
                        selectNetwork.selectOption(map.get("Network Location"));
                        MyCommonAPIs.sleepi(1);
                        System.out.println("Step9:");
                        if (networkNameListed.exists()) {
                            result = true;
                            logger.info("select Router Shows PR Device SerialNo");
                            System.out.println("Step10:");
                        }
                    }
                }
            }
        }
        return result;
    }

    // AddedByPratik
    public boolean verfiyselectRouterShowsPRDeviceSerialNo(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoPage();
        MyCommonAPIs.sleepi(30);
        waitElement(connectedTextVerify);
        if (connectedTextVerify.exists() && greenColorSymbol.exists()) {
            vpnSetting.click();
            MyCommonAPIs.sleepi(30);
            waitElement(ipSecVPNTab);
            if (ipSecVPNTab.exists() && headerIpSecWizard.exists()) {
                logger.info("IP Sec Text Verified on VPN Setting page.");
                addVPNGroup.click();
                MyCommonAPIs.sleepi(10);
                waitElement(verifYTexTConfigureIPSec);
                inputGroupName.sendKeys(map.get("Group Name"));
                MyCommonAPIs.sleepi(1);
                inputDescription.sendKeys(map.get("Description"));
                MyCommonAPIs.sleepi(1);
                waitElement(defaultIPSecProfile);
                defaultIPSecProfile.scrollTo();
                System.out.println("Step1:");
                MyCommonAPIs.sleepi(1);
                waitElement(verifYTexTConfigureIPSec);
                System.out.println("Step2:");
                MyCommonAPIs.sleepi(1);
                waitElement(hubRouterText);
                System.out.println("Step3:");
                selectDeviceName.selectOption(map.get("Serial Number"));
                MyCommonAPIs.sleepi(1);
                System.out.println("Step4:");
                nextButton.click();
                MyCommonAPIs.sleepi(10);
                if (textLanNetworkSetting.exists()) {
                    System.out.println("Step5:");
                    inputLANIPAddress.sendKeys(map.get("LAN IP Address"));
                    MyCommonAPIs.sleepi(1);
                    System.out.println("Step6:");
                    inputLANSubnet.sendKeys(map.get("LAN Subnet"));
                    MyCommonAPIs.sleepi(1);
                    nextButton.click();
                    MyCommonAPIs.sleepi(10);
                    System.out.println("Step7:");
                    if (spokeRouterDesc.exists() && headerSpokeRouter.exists()) {
                        buttonAddSpokeRouter.click();
                        MyCommonAPIs.sleepi(1);
                        noDataAvailable.exists();
                        System.out.println("Step8:");
                        buttonAddNewSpokeRouter.click();
                        MyCommonAPIs.sleepi(1);
                        selectNetwork.selectOption(map.get("Network Location"));
                        MyCommonAPIs.sleepi(5);
                        System.out.println("Step9:");
                        selectRouter.click();
                        MyCommonAPIs.sleepi(5);
                        //optionRouterSelectPR.shouldBe(Condition.visible).hover();
                        MyCommonAPIs.sleepi(5);
                        selectRouter.selectOptionContainingText((map.get("Serial Number1")));
                        MyCommonAPIs.sleepi(10);
/*                        //selectRouter.selectOption((map.get("Serial Number"))+" (Device is configured as hub)");
                        //selectRouter.click();
                        MyCommonAPIs.sleepi(1);
                        //optionRouterSelectPR.hover();
                        selectRouter.selectOptionContainingText((map.get("Serial Number")));*/
                        System.out.println("Step10:");
                        SelenideElement v1 = optionRouterSelectPR.shouldBe(Condition.visible);
                        if (selectRouterText.exists() && v1.exists()) {
                            result = true;
                            logger.info("Spoke Router Has Location Listed under select network options");
                        }
                    }
                }
            }
        }
        return result;
    }

}
