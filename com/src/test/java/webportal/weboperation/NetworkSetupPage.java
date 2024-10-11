package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.SelenideElement;

import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.NetworkSetupElement;

public class NetworkSetupPage extends NetworkSetupElement {
    Logger          logger         = Logger.getLogger("NetworkSetupPage");
    static int      deleteImagePos = 99;
    SelenideElement btnEditLine    = $("img[src*=edit]");
    SelenideElement btnDelLine     = $("img[src*=del]");

    public NetworkSetupPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefNetworksSetup);
    }

    public List<String> getNetworks() {
        if ($(lsNetworks).exists())
            return getTexts(lsNetworks);
        else
            return new ArrayList<String>();
    }

    /**
     * @param netName
     *                (use contains) 1 to open "Management Network"
     */
    public void openNetwork(String netName) {
        if (netName.equals("1")) {
            netName = "Management VLAN";
        }
        logger.info(netName);
        waitElement(lsNetworks);
        for (SelenideElement se : $$(lsNetworks)) {
            if (getText(se).contains(netName)) {
                System.out.print("check in if");
                se.hover();
                SelenideElement editicon_ele = $x(String.format(editicon, netName));
                editicon_ele.click();
                break;
            }
        }
//        clickEditIcon(netName);
        waitReady();
        sleep(5, "wait date to be loaded");
    }

    /**
     * @param step
     *             1 - n
     */
    public void gotoStep(int step) {
        for (int i = 0; i < 30; i++) {
            int curPos = getCurrentStep();
            if (curPos == step) {
                break;
            }
            String sNext;
            if ((curPos - step) > 0) {
                sNext = String.format(txtNextStep, curPos - 1);
            } else {
                sNext = String.format(txtNextStep, curPos + 1);
            }
            // to skip br
            if (step == 5) {
                setLanIp();
            }
            $x(sNext).click();
            if (step > 4) {
                clickBoxLastButton();
            }
            waitReady();
        }
    }

    public void initDeleteImagePos() {
        if (deleteImagePos == 99) {
            if (btnDelLine.exists()) {
                if (btnEditLine.exists()) {
                    deleteImagePos = 1;
                } else {
                    deleteImagePos = 0;
                }
            }
        }
        logger.info("to delete a vlan with pos: " + deleteImagePos);
    }

    public void deleteNetwork(String netName) {
        initDeleteImagePos();
        logger.info(netName);
        for (String vn : getNetworks()) {
            if (vn.contains(netName)) {
                try {
                    editLine("tbody", 1, vn, deleteImagePos);
                    clickBoxLastButton();
                } catch (Throwable e) {
                    e.printStackTrace();
                    refresh();
                }
            }
        }
    }

    public void deleteAllNetwork() {
        takess("delete All data");
        initDeleteImagePos();
        for (String vn : getNetworks()) {
            if (vn.contains("Management VLAN") || vn.contains("vlan2 Network") || vn.contains("Management")) {
            } else {
                deleteNetwork(vn);
            }
        }
    }

    /**
     * @param netName
     * @param desc
     * @param vlanType
     *                 0 - data, 1 - voice, 2 - video
     * @param vlanName
     * @param vlanId
     */
    public void setNetwork1(String netName, String desc, int vlanType, String vlanName, String vlanId) {
        logger.info(netName + "/" + desc + "/" + vlanType + "/" + vlanName + "/" + vlanId);
        waitReady();
        setText(txtNetName, netName);
        if (desc != null) {
            setText(txtNetDesc, desc);
        }

        if (vlanType != 99) { // if vlan type == 99, do not need to set vlan type
            selectNetType(vlanType);
        }
        
        if (vlanType == 0) {
            setText(txtvlanName, vlanName);
            if (getValue(txtvlanId).length() == 0) {
                setText(txtvlanId, Integer.parseInt(vlanId));
            }
        } else if (vlanType == 99) {
            if (txtvlanName != null) {
                txtvlanName.clear();
                setText(txtvlanName, vlanName);
            }
            if (txtvlanId != null) {
//                txtvlanId.clear();
                setText(txtvlanId, Integer.parseInt(vlanId));
                txtvlanId.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                txtvlanId.sendKeys(Keys.BACK_SPACE);
                txtvlanId.sendKeys(vlanId);
            }
        }
        clickNextOrSkip(false);
        if (txtvlanId.exists() && txtvlanId.isDisplayed()) {
            clickNextOrSkip(false);
        }
    }

    /**
     * @param  portMember
     *                    split by , (index of device from 1~n)
     * @param  portMode
     *                    0 - access, 1 - trunk, 2 - delete
     * @param  radiusPort
     *                    split by , (index of device from 1~n)
     * @param  radiusMode
     *                    0 - auto, 1 - auth, 2 - unauth
     * @return            Note: will do on two switch
     */
    public void setNetwork2(String portMember, int portMode, String radiusPort, int radiusMode) {
        logger.info(portMember + "/" + portMode + "/" + radiusPort + "/" + radiusMode);
        setPortMembers(WebportalParam.sw1deveiceName, portMember, portMode);
        if (WebportalParam.enaTwoSwitchMode) {
            setPortMembers(WebportalParam.sw2deveiceName, portMember, portMode);
        }
        setPortRadius(WebportalParam.sw1deveiceName, radiusPort, radiusMode);
        if (WebportalParam.enaTwoSwitchMode) {
            setPortRadius(WebportalParam.sw2deveiceName, radiusPort, radiusMode);
        }
        clickNextOrSkip(false);
    }

    public void setNetwork3(String ssid) {
        btnAddNewWiFi.click();
        waitReady();
        setText(txtSsidName, ssid);
        txtSsidPass.sendKeys(WebportalParam.loginDevicePassword);
        btnAddSsid.click();
        waitReady();
    }

    /**
     * Set all switches which has empty value with an ip
     *
     * @param ip
     */
    public void setOtherIpAddress(String ip) {
        String nextip = "";
        int countSw = $$(allInput).size();
        logger.info("found switch number: " + countSw);
        for (int i = 0; i < countSw; i++) {
            nextip = nextIP(ip, 100 + i);
            SelenideElement se = $$(allInput).get(i);
            if (se.getValue().length() == 0) {
                se.setValue(nextip);
            }
        }
    }

    /**
     * @param isdhcp
     *               true to enable dhcp, false to static for non-manager vlan
     * @param ip1
     *               switch 1
     * @param ip2
     *               switch 2
     */
    public void setNetwork4(boolean isdhcp, String ip1, String ip2) {
        setDhcpMode(isdhcp);
        if (!isdhcp) {
            if (txtGateway.exists()) {
                txtGateway.setValue(nextIP(ip2, 10));
            }
            setText(txtMask, "255.255.255.0");
            setStaticIP(WebportalParam.sw1deveiceName, ip1);
            if (WebportalParam.enaTwoSwitchMode) {
                setStaticIP(WebportalParam.sw2deveiceName, ip2);
            }
            setOtherIpAddress(ip2);
        }
        clickNextOrSkip(false);
    }

    /**
     * set with static ip with new way for manager vlan
     */
    public void setNetwork4() {
        setDhcpMode(false);
        if (txtGateway.exists()) {
            txtGateway.setValue(getIpGateway(WebportalParam.sw1IPaddress));
        }
        setText(txtMask, "255.255.255.0");
        setStaticIP(WebportalParam.sw1deveiceName, getVlan1StaticIp(false));
        String lastip = getVlan1StaticIp(true);
        if (WebportalParam.enaTwoSwitchMode) {
            setStaticIP(WebportalParam.sw2deveiceName, lastip);
        }
        setOtherIpAddress(lastip);
        clickNextOrSkip(false);
    }
    
    /**
     * Set fake ip for all switches
     *
     * @param ipStart
     */
    public void setNetwork4Sanity(String ipStart) {
        setDhcpMode(false);
        if (txtMask.exists()) {
            setText(txtMask, "255.255.255.0");
            setOtherIpAddress(ipStart);
        } else {
            logger.info("current switch does not suport or no switch?");
        }
        clickNextOrSkip(false);
    }
    
    /**
     * @param netName
     * @param vlanType
     *                 0 - data, 1 - voice, 2 - video
     * @param vlanName
     * @param vlanId
     */
    public void createNetwork(String netName, int vlanType, String vlanName, String vlanId) {
        logger.info(netName + "/" + vlanType + "/" + vlanName + "/" + vlanId);
        if (!getNetworks().contains(netName)) {
            clickAdd();
            setNetwork1(netName, null, vlanType, vlanName, vlanId);
            finishAllStep();
        }
    }

    /**
     * Create a network with full port selected
     *
     * @param netName
     * @param vlanId
     */
    public void createNetwork(String netName, String vlanId) {
        logger.info(netName + "/" + vlanId);
        if (getNetworks().contains(netName))
            return;
        clickAdd();
        setNetwork1(netName, null, 0, netName, vlanId);
        setAllPorts(true, 1, false);
        finishAllStep();
    }

    /**
     * config auth mode to 1,2,m on sw1, 2,3,m on sw2
     *
     * @param netName
     * @param vlanId
     * @param isMultiPort
     *                    true to enable 3 ports, false to 2 ports
     */
    public void createNetwork(String netName, String vlanId, boolean isMultiPort) {
        logger.info(netName + "/" + vlanId + "/" + isMultiPort);
        if (getNetworks().contains(netName))
            return;
        clickAdd();
        setNetwork1(netName, null, 0, netName, vlanId);
        if (isMultiPort) {
            setPortRadius(WebportalParam.sw1deveiceName, "1,2," + WebportalParam.sw1ManagePort, 1);
            if (WebportalParam.enaTwoSwitchMode) {
                setPortRadius(WebportalParam.sw2deveiceName, "2,3," + WebportalParam.sw2ManagePort, 1);
            }
        } else {
            setPortRadius(WebportalParam.sw1deveiceName, "1," + WebportalParam.sw1ManagePort, 1);
            if (WebportalParam.enaTwoSwitchMode) {
                setPortRadius(WebportalParam.sw2deveiceName, "1," + WebportalParam.sw2ManagePort, 1);
            }
        }
        enableRadius(WebportalParam.sw1deveiceName, true);
        if (WebportalParam.enaTwoSwitchMode) {
            enableRadius(WebportalParam.sw2deveiceName, true);
        }
        takess("screen to radius on vlan network");
        finishAllStep();
    }

    /**
     * @param netName
     * @param vlanId
     * @param isIp
     *                true is ip
     * @param isAllow
     *                0-allow, 1-deny
     * @param aclType
     *                see {@code clickMacIpButton}, 0 - manual, 1 - custom
     */
    public void createNetwork(String netName, String vlanId, boolean isIp, int isAllow, int aclType) {
        logger.info(netName + "/" + vlanId + "/" + isIp + "/" + isAllow + "/" + aclType);
        if (getNetworks().contains(netName))
            return;
        clickAdd();
        setNetwork1(netName, null, 0, netName, vlanId);
        setAclType(isIp, isAllow);
        clickMacIpButton(isIp, aclType);
        if (aclType == 0) {
            setIpMacACL(isIp, false);
        } else {
            setIpMacACL(isIp, true);
        }
        enableMacIpAcl(isIp, true);
        finishAllStep();
    }

    /**
     * @param netName
     * @param vlanId
     * @param aclType
     *                see {@code clickMacIpButton}, 0 - manual, 1 - custom
     */
    public void createSanityACLNetwork(String netName, String vlanId, int aclType) {
        logger.info(netName + "/" + vlanId + "/" + aclType);
        if (getNetworks().contains(netName))
            return;
        clickAdd();
        setNetwork1(netName, null, 0, netName, vlanId);
        // allow manual MAC ACL
        setAclType(false, 0);
        clickMacIpButton(false, aclType);
        if (aclType == 0) {
            setIpMacACL(false, false);
        } else {
            setIpMacACL(false, true);
        }

        enableMacIpAcl(false, true);
        
        // deny manual IP ACL
        setAclType(true, 1);
        clickMacIpButton(true, aclType);
        if (aclType == 0) {
            setIpMacACL(true, false);
        } else {
            setIpMacACL(true, true);
        }
        enableMacIpAcl(true, true);

        finishAllStep();
    }
    
    /**
     * @param netName
     * @param vlanId
     * @param vlanIpMode
     *                   0 - dhcp, 1 - static, 2 - none.
     *                   for static, it will set like 192.168.x.x network
     */
    public void createNetwork(String netName, String vlanId, int vlanIpMode) {
        logger.info(netName + "/" + vlanId + "/" + vlanIpMode);
        if (getNetworks().contains(netName))
            return;
        clickAdd();
        setNetwork1(netName, null, 0, netName, vlanId);
        gotoStep(4);
        if (vlanIpMode == 0) {
            setDhcpMode(true);
        } else if (vlanIpMode == 1) {
            String ipnet = String.format("192.168.%s.1", (int) (Math.random() * 255));
            setNetwork4(false, nextIP(ipnet, 10), nextIP(ipnet, 20));
            System.out.println(nextIP(ipnet, 10));
            System.out.println(nextIP(ipnet, 20));
        }
        finishAllStep();
    }

    /**
     * for ap564, select ports for tag or untag in vlan
     *
     * @param netName
     * @param vlanId
     * @param devName
     * @param ports
     *                 split by , (index of device from 1~n)
     * @param portMode
     */
    public void createNetwork(String netName, String vlanId, String devName, String ports, int portMode) {
        logger.info(netName + "/" + vlanId + "/" + devName + "/" + ports + "/" + portMode);
        clickAdd();
        setNetwork1(netName, null, 0, netName, vlanId);
        setPortMembers(devName, ports, portMode);
        finishAllStep();
    }

    /**
     * for ap564, select 2 port for untag and port 3 tag in vlan
     *
     * @param netName
     * @param vlanId
     * @param devName
     * @param accessPort
     *                   split by , (index of device from 1~n)
     * @param trunkPort
     *                   split by , (index of device from 1~n)
     */
    public void createNetwork(String netName, String vlanId, String devName, String accessPort, String trunkPort) {
        logger.info(netName + "/" + vlanId + "/" + devName + "/" + accessPort + "/" + trunkPort);
        clickAdd();
        setNetwork1(netName, null, 0, netName, vlanId);
        setPortMembers(devName, accessPort, 0);
        setPortMembers(devName, trunkPort, 1);
        finishAllStep();
    }
    
    public void restoreVlan1toDHCP() {
        openNetwork("1");
        gotoStep(4);
        setNetwork4(true, null, null);
        finishAllStep();
    }
    
}
