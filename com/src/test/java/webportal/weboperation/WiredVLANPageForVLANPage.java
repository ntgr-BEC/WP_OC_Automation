/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WiredVLANForVLANElement;

/**
 * @author Netgear
 */
public class WiredVLANPageForVLANPage extends WiredVLANForVLANElement {
    /**
     *
     */
    public Logger        logger;
    public WiredVLANPage wlpPoint = new WiredVLANPage();

    public WiredVLANPageForVLANPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefWiredSetting);
        MyCommonAPIs.sleep(8000);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public WiredVLANPageForVLANPage(boolean noPage) {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void gotoPage() {
        logger.info(String.format("gotoVlanPage ..."));
        WebCheck.checkHrefIcon(URLParam.hrefWiredSetting);
    }

    public void clickPorts(String dutName, String[] ports) {
        if (ports != null) {
            if (ports[0].equals("selectall")) {
                selectAllClick(dutName).click();
                clickPort(WebportalParam.sw1ManagePort, 0); // prevent pvid for rtk
            } else if (ports[0].equals("deselectall")) {
                selectAllClick(dutName).click();
                deSelectAllClick(dutName).click();
            } else {
                for (int i = 0; i < ports.length; i++) {
                    portChoice(dutName, ports[i]).click();
                    // loGo.hover(); // fail here, comment it and check, not sure the purpose of this action
                }
            }
        }
    }

    public void clickPortsMode(String dutName, String mode) {
        if (mode.equals("untag")) {
            untagClick(dutName).click();
        }
        if (mode.equals("tag")) {
            tagClick(dutName).click();
        }
        if (mode.equals("delete")) {
            deleteClick(dutName).click();
        }
    }

    public List<String> getVlans() {
        return wlpPoint.getVlans();
    }

    public void addDataVlanWithPorts(String name, String id, String dut1Name, String[] ports1, String port1Mode, String dut2Name, String[] ports2,
            String port2Mode, String video) {
        NetworkSetupPage nsp = new NetworkSetupPage();
        nsp.gotoPage();
        nsp.createNetwork("testnet", 0, name, id);
        gotoPage();
        openVlan(name, id, 0);

        if ((video != null) && (video == "true")) {
            clickElementIdViaJs(btnVideoid); // btnVideo.click();
        }
        if (showPortMember.isDisplayed()) {
            showPortMember.click();
        }
        if (dut1Name != null) {
            clickPorts(dut1Name, ports1);
            clickPortsMode(dut1Name, port1Mode);
        }
        if (dut2Name != null) {
            clickPorts(dut2Name, ports2);
            clickPortsMode(dut2Name, port2Mode);
        }

        clickSave();
        waitReady();
        sleepsync();
    }

    public void openVlan(String name, String vlanid, int vlanType) {
        wlpPoint.openVlan(name, vlanid, vlanType);
    }

    public void deleteVlan(String vlanid) {
        wlpPoint.deleteVlan(vlanid, true);
    }

    public void deleteAllVlan() {
        wlpPoint.deleteAllVlan();
    }

    public void addVoiceVlanWithPorts(String name, String id, String dut1Name, String[] ports1, String port1Mode, String dut2Name, String[] ports2,
            String port2Mode, String video) {
        NetworkSetupPage nsp = new NetworkSetupPage();
        nsp.gotoPage();
//        List<String> VLAnIDS= new WiredVLANPage().getVlanIDs();  
//        System.out.println(VLAnIDS);
//        System.out.println(VLAnIDS.contains(name));
//        if(VLAnIDS.contains(name)) {  
        if (!nsp.getNetworks().contains(name)) {
        nsp.createNetwork("testnet" + id, 1, name, id);
        }
        gotoPage();
        openVlan(name, id, 1);

        if (showPortMember.isDisplayed()) {
            showPortMember.click();
        }
        if (dut1Name != null) {
            clickPorts(dut1Name, ports1);
            clickPortsMode(dut1Name, port1Mode);
        }
        if (dut2Name != null) {
            clickPorts(dut2Name, ports2);
            clickPortsMode(dut2Name, port2Mode);
        }

        clickSave();
        waitReady();
        MyCommonAPIs.sleep(20000);
    }

    public void editVlanWithPorts(String name, String id, String newname, String dut1Name, String[] ports1, String port1Mode, String dut2Name,
            String[] ports2, String port2Mode, String video) {
        openVlan(name, id, 0);
        vlanName.setValue(newname);
        // vlanId.setValue(id);
        scrollTo(false);
        if (showPortMember.isDisplayed()) {
            showPortMember.click();
        }
        scrollTo(false);

        if (dut1Name != null) {
            clickPorts(dut1Name, ports1);
            clickPortsMode(dut1Name, port1Mode);
        }
        if (dut2Name != null) {
            clickPorts(dut2Name, ports2);
            clickPortsMode(dut2Name, port2Mode);
        }

        clickSave();
        waitReady();
        sleepsync();
    }

    public void addVideoVlanWithPorts(String name, String id, String dut1Name, String[] ports1, String port1Mode, String dut2Name, String[] ports2,
            String port2Mode, String video) {
        NetworkSetupPage nsp = new NetworkSetupPage();
        nsp.gotoPage();
        nsp.createNetwork("testnet" + id, 2, name, id);
        gotoPage();
        openVlan(name, id, 2);

        // vlanId.setValue(id);
        // if (showPortMember.exists())
        // {
        // showPortMember.click();
        // }

        if (dut1Name != null) {
            clickPorts(dut1Name, ports1);
            clickPortsMode(dut1Name, port1Mode);
        }
        if (dut2Name != null) {
            clickPorts(dut2Name, ports2);
            clickPortsMode(dut2Name, port2Mode);
        }

        clickSave();
        waitReady();
        sleepsync();
    }

    public void addCustomVlanWithPorts(String name, String id, String dut1Name, String[] ports1, String port1Mode, String dut2Name, String[] ports2,
            String port2Mode, String video) {
        NetworkSetupPage nsp = new NetworkSetupPage();
        nsp.gotoPage();
        nsp.createNetwork("testnet", 0, name, id);
        gotoPage();
        openVlan(name, id, 0);

        scrollTo(false);
        if (showPortMember.isDisplayed()) {
            showPortMember.click();
        }
        scrollTo(false);

        if (dut1Name != null) {
            clickPorts(dut1Name, ports1);
            clickPortsMode(dut1Name, port1Mode);
        }
        if (dut2Name != null) {
            clickPorts(dut2Name, ports2);
            clickPortsMode(dut2Name, port2Mode);
        }

        clickSave();
        waitReady();
        sleepsync();
    }

    /**
     * @return return false if element is invisible, true to visible
     */
    public Boolean checkManageVlanDelete() {
        logger.info("start to check");
        try {
            SelenideElement el = getElement(vlanlistTableCol);
            el.hover();
            SelenideElement deleteel = getElement(vlanItemDel);
            if (deleteel.isDisplayed()) {
                logger.info("del btn is show");
                return true;
            } else
                return false;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPortsUntag(int portIndex) {
        for (int i = 1; i <= portIndex; i++) {
            if (!portMember(Integer.toString(i)).getAttribute("class").toLowerCase().contains("accessport")) {
                logger.info(String.format("isPortsUntag: check port<%s> fail", Integer.toString(i)));
                return false;
            }
        }
        return true;
    }

    public void clickPublicPorts(String dutName, String[] ports) {
        if (ports != null) {
            if (ports[0].equals("selectall")) {
                selectAllClick(dutName).click();
                clickPort(WebportalParam.sw1ManagePort, 0);
            } else if (ports[0].equals("deselectall")) {
                selectAllClick(dutName).click();
                deSelectAllClick(dutName).click();
            } else {
                for (int i = 0; i < ports.length; i++) {
                    publicportChoice(dutName, ports[i]).click();
                    loGo.hover();
                }
            }
        }
    }

    public void clickPublicPortsMode(String dutName, String mode) {
        if (mode.equals("untag")) {
            publicUntagClick(dutName).click();
        }
        if (mode.equals("tag")) {
            publicTagClick(dutName).click();
        }
        if (mode.equals("delete")) {
            publicDeleteClick(dutName).click();
        }
    }

    public void clickEmployeePorts(String dutName, String[] ports) {
        if (ports != null) {
            if (ports[0].equals("selectall")) {
                selectAllClick(dutName).click();
                clickPort(WebportalParam.sw1ManagePort, 0);
            } else if (ports[0].equals("deselectall")) {
                selectAllClick(dutName).click();
                deSelectAllClick(dutName).click();
            } else {
                for (int i = 0; i < ports.length; i++) {
                    employeeportChoice(dutName, ports[i]).click();
                    loGo.hover();
                }
            }
        }
    }

    public void clickEmployeePortsMode(String dutName, String mode) {
        if (mode.equals("untag")) {
            employeeUntagClick(dutName).click();
        }
        if (mode.equals("tag")) {
            employeeTagClick(dutName).click();
        }
        if (mode.equals("delete")) {
            employeeDeleteClick(dutName).click();
        }
    }

    public void clickGuestPorts(String dutName, String[] ports) {
        if (ports != null) {
            if (ports[0].equals("selectall")) {
                selectAllClick(dutName).click();
                clickPort(WebportalParam.sw1ManagePort, 0);
            } else if (ports[0].equals("deselectall")) {
                selectAllClick(dutName).click();
                deSelectAllClick(dutName).click();
            } else {
                for (int i = 0; i < ports.length; i++) {
                    guestportChoice(dutName, ports[i]).click();
                    loGo.hover();
                }
            }
        }
    }

    public void clickGuestPortsMode(String dutName, String mode) {
        if (mode.equals("untag")) {
            guestUntagClick(dutName).click();
        }
        if (mode.equals("tag")) {
            guestTagClick(dutName).click();
        }
        if (mode.equals("delete")) {
            guestDeleteClick(dutName).click();
        }
    }

    public void addGuestEmplyeeVlanWithPorts(String name, String id, String dut1Name, String[] publicports, String[] employeeports,
            String[] guestports, String dut2Name, String[] publicports2, String[] employeeports2, String[] guestports2, String video,
            String voice) {
        // not support, manager port must be in uplink
        click(vlanAdd);
        btnClickHere.click();
        MyCommonAPIs.waitReady();
        vlanName.setValue(name);
        vlanId.setValue(id);

        if (dut1Name != null) {
            showPublicPort.click();
            clickPublicPorts(dut1Name, publicports);
            clickPublicPortsMode(dut1Name, "untag");
            showEmployeePort.click();
            clickEmployeePorts(dut1Name, employeeports);
            clickEmployeePortsMode(dut1Name, "untag");
            showGuestPort.click();
            clickGuestPorts(dut1Name, guestports);
            clickGuestPortsMode(dut1Name, "untag");

        }
        if (dut2Name != null) {
            showPublicPort.click();
            clickPublicPorts(dut2Name, publicports2);
            clickPublicPortsMode(dut2Name, "untag");
            showEmployeePort.click();
            clickEmployeePorts(dut2Name, employeeports2);
            clickEmployeePortsMode(dut2Name, "untag");
            showGuestPort.click();
            clickGuestPorts(dut2Name, guestports2);
            clickGuestPortsMode(dut2Name, "untag");
        }

        clickSave();
        waitReady();
        sleepsync();
    }

    // tejeshwini code

    public void CreateVLANToAddSSID() {

        editnetwork.click();
        waitReady();

        gotoNetworksetup.click();
        waitReady();

        GoToManagementVLAN.click();
        waitReady();
        MyCommonAPIs.sleepi(10);

        Next1.click();

        waitReady();
        MyCommonAPIs.sleepi(10);

        Next1.click();

        waitReady();

    }

    public void AddSSIDVLAN() {
        waitReady();
        btnNextOrViewNetworks.click();
        waitReady();
        btnNextOrViewNetworks.click();
        waitReady();
        confirm.click();
        waitReady();
        ViewNetwork.click();

    }

//     AddedByPratik
    public void CreateVLANforClientIsolation(String vlan) {
        MyCommonAPIs.sleepi(10);
        waitReady();
        editnetwork.click();
        waitReady();
        MyCommonAPIs.sleepi(10);
        gotoNetworksetup.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        addVlanPlusSym.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        networkName.setValue("office1");
        MyCommonAPIs.sleepi(5);
        vlanDesc.setValue("VLAN for Client Isolation Testing");
        MyCommonAPIs.sleepi(5);
        vlanName1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        vlanName1.click();
        MyCommonAPIs.sleepi(1);
        vlanName1.setValue("manvlan40707");
        MyCommonAPIs.sleepi(5);
        vlanId1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        vlanId1.click();
        MyCommonAPIs.sleepi(1);
        vlanId1.setValue("20");
        MyCommonAPIs.sleepi(5);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        Next1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        Next1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        Next1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        confirmBtn.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        confirmBtn.click();
        MyCommonAPIs.sleepi(10);
        if (successfulMessage.exists()) {
            logger.info("VLAN with ID 20 created successfully");
        }
        Next1.click();
        MyCommonAPIs.sleepi(10);
    }

    // AddedByPratik
    public void deleteVLAN(String vlan) {
        MyCommonAPIs.sleepi(10);
        waitReady();
        editnetwork.click();
        waitReady();
        MyCommonAPIs.sleepi(10);
        gotoNetworksetup.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        waitReady();
        MyCommonAPIs.sleepi(5);
        logger.info("Delete VLAN.");
        dataCustomVlan(vlan).hover();
        MyCommonAPIs.sleep(3000);
        executeJavaScript("arguments[0].removeAttribute('class')", editVLAN(vlan));
        MyCommonAPIs.sleep(3000);
        deleteCustomVlan(vlan).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(30);
        delVLANConfirmbtn.click();
        MyCommonAPIs.sleep(5 * 1000);
    }
    
    // AddedByPratik
    public void CreateVLANToTestPort(String vlan, String id) {
        MyCommonAPIs.sleepi(10);
        waitReady();
        editnetwork.click();
        waitReady();
        MyCommonAPIs.sleepi(10);
        gotoNetworksetup.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        addVlanPlusSym.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        networkName.setValue(vlan);
        MyCommonAPIs.sleepi(5);
        vlanDesc.setValue("VLAN for Port Testing");
        MyCommonAPIs.sleepi(5);
        vlanName1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        vlanName1.click();
        MyCommonAPIs.sleepi(1);
        vlanName1.setValue(vlan);
        MyCommonAPIs.sleepi(5);
        vlanId1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        vlanId1.click();
        MyCommonAPIs.sleepi(1);
        vlanId1.setValue(id);
        MyCommonAPIs.sleepi(5);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        Next1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        Next1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        Next1.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        Next1.click();
        MyCommonAPIs.sleepi(10);
        confirmBtn.scrollIntoView(true);
        MyCommonAPIs.sleepi(1);
        confirmBtn.click();
        MyCommonAPIs.sleepi(10);
        if (successfulMessage.exists()) {
            logger.info("VLAN with ID 20 created successfully");
        }
        Next1.click();
        MyCommonAPIs.sleepi(10);
    }
    
    // AddedByPratik
    public boolean editExistingVLANSelectportsAndSave(String vlan) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        editnetwork.click();
        waitReady();
        MyCommonAPIs.sleepi(10);
        gotoNetworksetup.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        waitReady();
        MyCommonAPIs.sleepi(5);
        logger.info("Delete VLAN.");
        dataCustomVlan(vlan).hover();
        MyCommonAPIs.sleep(3000);
        executeJavaScript("arguments[0].removeAttribute('class')", editVLAN(vlan));
        MyCommonAPIs.sleep(3000);
        editCustomVlan(vlan).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(30);
        Next1.click();
        MyCommonAPIs.sleepi(30);
        if (wiredSettingText.exists() && portMembersText.exists()) {
            selectPortIfSw(WebportalParam.sw1serialNo, "2").hover();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw1serialNo, "2").click();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw1serialNo, "3").hover();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw1serialNo, "3").click();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw1serialNo, "4").hover();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw1serialNo, "4").click();
            MyCommonAPIs.sleepi(1);
            selectPortIfSw(WebportalParam.sw2serialNo, "2").hover();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw2serialNo, "2").click();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw2serialNo, "3").hover();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw2serialNo, "3").click();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw2serialNo, "4").hover();
            MyCommonAPIs.sleep(30);
            selectPortIfSw(WebportalParam.sw2serialNo, "4").click();
            MyCommonAPIs.sleepi(1);
            Next1.scrollIntoView(true);
            MyCommonAPIs.sleepi(1);
            Next1.click();
            MyCommonAPIs.sleepi(5);
            if (portModeWarningMsg.exists()) {
                String temp = portModeWarningMsg.text();
                System.out.println("Warning Error Message: "+temp);
                logger.info("Getting warning error message");
                accessPortBtn.click();
                MyCommonAPIs.sleepi(1);
                Next1.scrollIntoView(true);
                MyCommonAPIs.sleepi(1);
                Next1.click();
                MyCommonAPIs.sleepi(10);
                Next1.scrollIntoView(true);
                MyCommonAPIs.sleepi(1);
                Next1.click();
                MyCommonAPIs.sleepi(10);
                Next1.scrollIntoView(true);
                MyCommonAPIs.sleepi(1);
                Next1.click();
                MyCommonAPIs.sleepi(10);
                confirmBtn.scrollIntoView(true);
                MyCommonAPIs.sleepi(1);
                confirmBtn.click();
                MyCommonAPIs.sleepi(10);
                if (successfulMessage.exists()) {
                    logger.info("VLAN with ID 20 created successfully");
                    result = true;
                }
                Next1.click();
                MyCommonAPIs.sleepi(10);
            }
            
        }
        return result;
    }
}
