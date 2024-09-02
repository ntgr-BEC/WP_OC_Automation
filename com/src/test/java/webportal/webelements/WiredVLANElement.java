package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.collections4.ListUtils;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.CustomAccessManagment;
import webportal.param.WebportalParam;

public class WiredVLANElement extends MyCommonAPIs {
    final static Logger          logger         = Logger.getLogger("WiredVLANElement");
    public CustomAccessManagment camData;
    public static String         sw1DeviceXpath = "";
    public static String         sw2DeviceXpath = "";
    public static String         br1DeviceXpath = "";
    public static String         ap1DeviceXpath = "";

    public void initTestData() {
        ipFilterIpMask = "0.0.0.255";
        ipFilterMacOpt = 0;
        enaIpFilterIpMask = false;
        enaIpFilterCustom = false;
        camData = new CommonDataType().dataCAM;
    }
    
    public String vlanlistTableId        = "#tbdywiredVlan";
    public String vlanlistTable          = "#tbdywiredVlan tr";
    public String vlanlistTableCol       = "#tbdywiredVlan td:nth-of-type(1) span";
    public String vlanlistTableVlanIdCol = "#tbdywiredVlan td:nth-of-type(2)";
    public String vlanItemEdit           = "img[src*=\"edit\"]";
    public String vlanItemDel            = "img[src*=\"del\"]";

    // warning msg before creating vlan
    public SelenideElement cbNotShowAgain    = $("#myModal[style*=block] label input");
    public SelenideElement btnNotShowAgainOK = $("#myModal[style*=block] button");

    // new/edit vlan
    public SelenideElement vlanName       = $x("//div[@id=\"divOnOfSetVlanEditVlan\"]//input[@id=\"vlanName\"]");
    public SelenideElement vlanDesc       = $x("//span[text()='Description']/../following-sibling::input");
    public SelenideElement vlanId         = $x("//div[@id=\"divOnOffSettVlanIdEditVlan\"]//input[@id=\"vlanId\"]");
    public SelenideElement vlanAdd        = $x("//a[@id=\"ancOpenwiredVlan\"]");
    public SelenideElement btnContinue    = $x("//button[@class='loginBtnSection' and text()='Continue']");
    public SelenideElement vlanNext       = $("#addNewVlanModal[style*=block] #buPrimaryOnClickBtn");
    public SelenideElement showPortMember = $x("//i[@id='iconPlusEditVlan']");//$("#iconMinFaEditVlan+i");
    
    public SelenideElement btnsaveVlan    = $("#saveEditVlan");
    public SelenideElement btncancelVlan    = $("#cancelEditVlan");
    public String          sVlanType      = "#divRowThreeBtn div:nth-child(%s) div[id*=divCommon]";

    // private vlan -- from WiredVLANForVLANElement
    public SelenideElement showPublicPort   = $("#iconPulPlusEditVlan");
    public SelenideElement showEmployeePort = $("#iconPlusArspnMinColEditVlan");
    public SelenideElement showGuestPort    = $("#iconPlusTrEditVlan");
    
    // network vlan
    public SelenideElement networkVlan    = $(".in .cstmModFooter a button");
    public SelenideElement PrivateVlan    = $(".in .cstmModFooter a+button");
    public SelenideElement txtNetName     = $("#networkConfigForm input[name*=networkName]");
    public SelenideElement txtNetDesc     = $("#networkConfigForm input[name*=vlanDesc]");
    public SelenideElement txtNetVlanName = $("#networkConfigForm input[name*=vlanName]");
    public SelenideElement txtNetVlanID   = $("#networkConfigForm input[name*=vlanId]");
    public SelenideElement lbNetType      = $("#networkConfigForm [name*=networkType]");
    public SelenideElement btnNext        = $(".configureNetwork .saveBtn");
    public SelenideElement btnSkip        = $(".configureNetwork .cancelBtn");
    public SelenideElement btnConfirm     = $(".networkDeployment button:last-child");

    public void clickSave() {
        takess("check vlan conf");
        click(btnsaveVlan);
    }

    // create vlan/net
    public SelenideElement btnClickHere = $(".CustomRadioBlock .colorPurple");

    /**
     * @param type
     *             0 -- data network, 1-voice vlan, 2-video vlan
     */
    public void selectNetType(int type) {
        lbNetType.selectOption(type);
    }

    public void setVlanId(String vlanid) {
        // check for voice/video vlan
        if (!vlanid.contains("408")) {
            vlanId.clear();
            vlanId.setValue(vlanid);
        }
    }

    /**
     * @param  devType
     *                 0-sw1, 1-sw2, 2-br, 3-ap
     * @return
     */
    public String getDeviceXpathX(int devType) {
        String result = "";
        if ((devType == 0) || (devType == 1)) {
            if (sw1DeviceXpath.isEmpty()) {
                sw1DeviceXpath = getDeviceXpath(WebportalParam.sw1deveiceName);
                if (WebportalParam.enaTwoSwitchMode) {
                    sw2DeviceXpath = getDeviceXpath(WebportalParam.sw2deveiceName);
                }
            }
            result = sw1DeviceXpath;
            if (devType == 1) {
                result = sw2DeviceXpath;
            }
        }
        if (devType == 2) {
            if (br1DeviceXpath.isEmpty()) {
                br1DeviceXpath = getDeviceXpath(WebportalParam.br1deveiceName);
            }
            result = br1DeviceXpath;
        }
        if (devType == 3) {
            if (ap1DeviceXpath.isEmpty()) {
                ap1DeviceXpath = getDeviceXpath(WebportalParam.ap1deveiceName);
            }
            result = ap1DeviceXpath;
        }
        return result;
    }

    /**
     * click button under port member
     *
     * @param devType
     *                 from 0-sw1, 1-sw2, 2-br1, 3-ap
     * @param btnIndex
     *                 from 0 to 4, "select all", ap, tp, port auth, delete
     *                 FIXME: for br, no port auth button
     */
    public void clickDeviceButton(int devType, int btnIndex) {
        String sPath = String.format("%s//button", getDeviceXpathX(devType));
        $$x(sPath).get(btnIndex).click();
    }

    public void removeAllPort(boolean is2ndSW) {
        logger.info(String.format("removeAllPort"));
        if (is2ndSW) {
            clickDeviceButton(1, 0);
        } else {
            clickDeviceButton(0, 0);
        }
        sleepi(4);
        if (is2ndSW) {
            clickDeviceButton(1, 4);
        } else {
            clickDeviceButton(0, 4);
        }
        takess("check whether all ports are removed");
    }

    /**
     * @param portIndex
     *                  from 1 to n
     * @param istrunk
     *                  whether access(false) for trunk(true)
     * @param devType
     *                  0-sw1, 1-sw2, 2-br, 3-ap
     */
    public void selectPort(int portIndex, boolean istrunk, int devType) {
        logger.info(String.format(": %s", portIndex));
        String portXpath = getDeviceXpathX(devType) + portIdXpath;
        $$x(portXpath).get(getPortIndex(portIndex, getPortNo(devType))).click();
        sleepi(2);
        if (istrunk) {
            clickDeviceButton(devType, 2);
        } else {
            clickDeviceButton(devType, 1);
        }

        takess("check which ports are selected for vlan");
    }

    // port align
    public String secPorts = ".box-scroller li:nth-child(%s)";
    //
    // public ElementsCollection getPorts(int portIndex) {
    // return $$(String.format(secPorts, portIndex));
    // }

    /**
     * @param  portIndex
     * @return           whether all ports are lag
     */
    public boolean isPortsLag(int portIndex) {
        for (SelenideElement se : getPorts(portIndex)) {
            if (!se.$("#one #two #three").exists()) {
                logger.info(String.format(": check <%s>", se));
                return false;
            }
        }
        return true;
    }

    /**
     * @param  portIndex
     * @return           whether these ports are selected
     */
    public boolean isPortsSelected(int portIndex) {
        for (SelenideElement se : getPorts(portIndex)) {
            String sAttr = se.getAttribute("class").toLowerCase();
            logger.info(String.format(": check <%s>-<%s>", se, sAttr));
            if (sAttr.contains("active") || sAttr.contains("portselected"))
                return true;
        }
        return false;
    }

    /**
     * @param  portIndex
     * @return           whether these ports are trunk port
     */
    public boolean isPortsTrunk(int portIndex) {
        for (SelenideElement se : getPorts(portIndex)) {
            if (!se.getAttribute("class").toLowerCase().contains("trunkport")) {
                logger.info(String.format(": check <%s>", se));
                return false;
            }
        }
        return true;
    }

    /**
     * @param  portIndex
     * @return           whether these ports are access port
     */
    public boolean isPortsAccess(int portIndex) {
        for (SelenideElement se : getPorts(portIndex)) {
            if (!se.getAttribute("class").toLowerCase().contains("accessport")) {
                logger.info(String.format(": check <%s>", se));
                return false;
            }
        }
        return true;
    }

    /**
     * return port is access/trunk/delete status
     *
     * @param  portIndex
     * @param  devIndex
     * @return
     */
    public int getPortMode(int portIndex, int devIndex) {
        // TODO
        return 0;
    }

    // left panel
    public SelenideElement clickIpFilter    = $("[href=\"#/wired/IPFiltering/network\"]");
    public SelenideElement clickMacAuth     = $("[href=\"#/wired/MacAuthentication/network\"]");
    public SelenideElement clickVlanSetting = $("[href=\"#/wired/editVlan/network\"]");

    // ip filter & mac auth
    public SelenideElement clickIpFilterOn = $("[id=\"enableCP\"]");

    public SelenideElement clickIpFilterOff           = $("[id=\"disableCP\"]");
    public SelenideElement clickIpFilterOnOff         = $("input[id*='InputOnOff']");
    public SelenideElement clickMacAuthOnOff          = $("input[id*='MacAuth']");
    public SelenideElement clickIpFilterMacAuthPolicy = $("[id=\"policy\"]");

    public boolean enaIpFilterIpMask = false;
    public String  ipFilterIpMask;
    /**
     * 0 - allow both, 1 - allow to(dst), 2 - allow from(src), 3 - disable both
     */
    public int     ipFilterMacOpt    = 0;

    public void enableACL(boolean isEnable) {
        logger.info(String.format(": %s", isEnable));
        String sAtt;
        if (clickIpFilterOff.exists()) {
            if (isEnable) {
                sAtt = clickIpFilterOn.getAttribute("class");
                if (!sAtt.contains("active")) {
                    click(clickIpFilterOn);
                }
            } else {
                sAtt = clickIpFilterOff.getAttribute("class");
                if (!sAtt.contains("active")) {
                    click(clickIpFilterOff);
                }
            }
        } else {
            if (clickIpFilterOnOff.exists()) {
                setSelected(clickIpFilterOnOff, isEnable, true);
            } else {
                setSelected(clickMacAuthOnOff, isEnable, true);
            }
        }
        sleep(4, "enableACL: done");
    }

    /**
     * @return true for allow, false for deny
     */
    public boolean getPolicy() {
        String sGet = clickIpFilterMacAuthPolicy.getSelectedText();
        logger.info(String.format(": %s", sGet));
        if (sGet.toUpperCase().contains("ALL"))
            return true;
        else
            return false;
    }

    public void setPolicy(boolean isAllow) {
        logger.info(String.format(": %s", isAllow));
        if (isAllow) {
            clickIpFilterMacAuthPolicy.selectOption(WebportalParam.getLocText("Allow"));
        } else {
            clickIpFilterMacAuthPolicy.selectOption(WebportalParam.getLocText("Deny"));
        }
        waitReady();
    }

    // action buttons
    public SelenideElement clickIpFilterManual = $(".actionBtnRow button:nth-of-type(1)");
    public SelenideElement clickIpFilterCustom = $(".actionBtnRow button:nth-of-type(2)");
    public SelenideElement clickIpFilterAdd    = $(".actionBtnRow button:nth-of-type(3)");
    public SelenideElement clickIpFilterRemove = $(".actionBtnRow button:nth-of-type(4)");

    public void clickIpFilterRemoveDo() {
        if (cbAllManual.exists()) {
            click(cbAllManual);
        }
        if (cbAllCustom.exists()) {
            click(cbAllCustom);
        }
        if (clickIpFilterRemove.isEnabled()) {
            click(clickIpFilterRemove);
        }
        MyCommonAPIs.waitReady();

        setPolicy(!getPolicy());
        if (cbAllManual.exists()) {
            cbAllManual.click();
        }
        if (cbAllCustom.exists()) {
            cbAllCustom.click();
        }
        if (clickIpFilterRemove.isEnabled()) {
            clickIpFilterRemove.click();
        }
        MyCommonAPIs.waitReady();
    }

    public String          ipAclTable             = "#tbodyMap1 tr";
    public String          ipmacAclList           = ".sequenceNo span";
    public String          ipmacAclNameList       = ".ipaclNo";
    public String          ipAclCustomTable       = "#tablecustom tbody tr";
    public String          ipmacAclCustomList     = "#tablecustom tr td:nth-child(2) span:first-child";
    public String          ipmacAclCustomNameList = "#tablecustom tr td:nth-child(2) span:last-child";
    public String          ipMacAuthTable         = "#tbdyidMacAuth tr";
    public String          sIpMacEdit             = "[data-target*=\"dit\"]";
    public String          sIpMacDel              = "[data-target*=\".deleteModal\"]";
    public SelenideElement cbAllManual            = $("#selectAllMannual+i");
    public SelenideElement cbAllCustom            = $("#selectAllCustom+i");
    public String          ipMacAcl            = "//span[text()='%s']";

    // manual access management ip filter/mac auth
    public SelenideElement ipMAMDeviceName = $("#myModal[style*=\"block\"] #deviceName1");
    public SelenideElement ipMAMDeviceIp   = $x("//*[contains(@style, \"block\")]//input[@id=\"ipaddress1\"]");
    public SelenideElement ipMAMDeviceMac  = $x("//*[contains(@style, \"block\")]//input[@id=\"macaddress1\"]");
    public SelenideElement ipcbRange       = $("#myModal[style*=\"block\"] #range1+i");
    public SelenideElement ipRangeMask     = $("#myModal[style*=\"block\"] #mask1");

    public void showIpRangeMask() {
        if (!ipRangeMask.isDisplayed()) {
            ipcbRange.click();
        }
    }

    public SelenideElement ipAllowSrc        = $("#myModal[style*=\"block\"] #source1");
    public SelenideElement ipAllowDst        = $("#myModal[style*=\"block\"] #destination1");
    public SelenideElement ipMAMDeviceAdd    = $("#myModal[style*=\"block\"] .modal-footer button:nth-of-type(2)");
    public SelenideElement ipMAMDeviceCancel = $("#myModal[style*=\"block\"] .modal-footer button:nth-of-type(1)");

    /**
     * @return whether entry is opened for edit
     */
    public boolean isIpFilterMacAuthOpen() {
        if (ipMAMDeviceName.exists() || camlbFrom.exists()) {
            logger.info(String.format(": true"));
            return true;
        } else {
            logger.info(String.format(": false"));
            return false;
        }
    }

    public void addIpFilterMAM(String name, String ip) {
        logger.info(String.format(": %s-%s-%s", name, ip, ipFilterMacOpt));
        if (!isIpFilterMacAuthOpen()) {
            logger.info("click manual to add/edit");
            click(clickIpFilterManual);
        }

        ipMAMDeviceName.setValue(name);
        ipMAMDeviceIp.setValue(ip);
        if (ipFilterMacOpt == 1) {
            setSelected(ipAllowSrc, false, false);
            setSelected(ipAllowDst, true, false);
        } else if (ipFilterMacOpt == 2) {
            setSelected(ipAllowDst, false, false);
            setSelected(ipAllowSrc, true, false);
        } else if (ipFilterMacOpt == 3) {
            // error
            setSelected(ipAllowDst, false, false);
            setSelected(ipAllowSrc, false, false);
        } else {
            setSelected(ipAllowDst, true, false);
            setSelected(ipAllowSrc, true, false);
        }

        if (enaIpFilterIpMask) {
            showIpRangeMask();
            ipRangeMask.setValue(ipFilterIpMask);
        }

        takess("check all values");
        click(ipMAMDeviceAdd);
    }

    public void addMacAuthMAM(String name, String mac) {
        logger.info(String.format(": %s-%s-%s", name, mac, ipFilterMacOpt));
        if (!isIpFilterMacAuthOpen()) {
            logger.info("click manual to add/edit");
            click(clickIpFilterManual);
        }

        ipMAMDeviceName.setValue(name);
        ipMAMDeviceMac.setValue(mac);
        if (ipFilterMacOpt == 1) {
            setSelected(ipAllowSrc, false, false);
            setSelected(ipAllowDst, true, false);
        } else if (ipFilterMacOpt == 2) {
            setSelected(ipAllowDst, false, false);
            setSelected(ipAllowSrc, true, false);
        } else if (ipFilterMacOpt == 3) {
            // error
            setSelected(ipAllowDst, false, false);
            setSelected(ipAllowSrc, false, false);
        } else {
            setSelected(ipAllowDst, true, false);
            setSelected(ipAllowSrc, true, false);
        }

        takess("check all values");
        click(ipMAMDeviceAdd);
    }

    public List<String> getIpMacAcls() {
        sleep(4, "make sure page is updated");
        List<String> lsRet = new ArrayList<String>();
        if ($(ipmacAclList).exists()) {
            lsRet = MyCommonAPIs.getTexts(ipmacAclList);
        }
        if ($(ipmacAclCustomList).exists()) {
            lsRet = ListUtils.union(lsRet, MyCommonAPIs.getTexts(ipmacAclCustomList));
        }
        return lsRet;
    }

    public List<String> getIpMacNameAcls() {
        List<String> lsRet = new ArrayList<String>();
        if ($(ipmacAclNameList).exists()) {
            lsRet = MyCommonAPIs.getTexts(ipmacAclNameList);
        }
        if ($(ipmacAclCustomNameList).exists()) {
            lsRet = ListUtils.union(lsRet, MyCommonAPIs.getTexts(ipmacAclCustomNameList));
        }
        return lsRet;
    }

    // custom ip acl
    public SelenideElement camlbFrom         = $("#myModal[style*=block] [id*=sourceclient]");
    public SelenideElement camlbTo           = $("#myModal[style*=block] [id*=destinationclient]");
    public SelenideElement camtfFromDevName  = $("#myModal[style*=block] #deviceName2");
    public SelenideElement camtfToDevName    = $("#myModal[style*=block] #deviceName3");
    public SelenideElement camtfFromIp       = $("#myModal[style*=block] #ipaddressmannual2");
    public SelenideElement camtfToIp         = $("#myModal[style*=block] #ipaddressmannual3");
    public SelenideElement camtfFromMac      = $("#myModal[style*=block] #macaddressmannual2");
    public SelenideElement camtfToMac        = $("#myModal[style*=block] #macaddressmannual3");
    public SelenideElement camtfFromIpMask   = $("#myModal[style*=block] #mask2");
    public SelenideElement camtfToIpMask     = $("#myModal[style*=block] #mask3");
    public SelenideElement camtfFromIpMac    = $("#myModal[style*=block] #macaddressmannual2");
    public SelenideElement camtfToIpMac      = $("#myModal[style*=block] #macaddressmannual3");
    public SelenideElement camcbFromAddRange = $x("(//div[@id=\"myModal\" and contains(@style, \"block\")]//input[@type=\"checkbox\"])[1]");
    public SelenideElement camcbToAddRange   = $x("(//div[@id=\"myModal\" and contains(@style, \"block\")]//input[@type=\"checkbox\"])[2]");

    public boolean enaIpFilterCustom = false;

    public boolean isIpFilterCustomOpen() {
        if (camlbFrom.exists()) {
            logger.info(String.format(": true"));
            return true;
        } else {
            logger.info(String.format(": false"));
            return false;
        }
    }

    public void setCAM(CustomAccessManagment cam) {
        logger.info(String.format(": %s", cam.toString()));
        cam.Dump();
        if (!isIpFilterMacAuthOpen()) {
            logger.info("click custom to add/edit");
            click(clickIpFilterCustom);
        }

        if (!cam.from.toLowerCase().contains("mann")) {
            camlbFrom.selectOption(cam.from);
        }
        if (!cam.to.toLowerCase().contains("mann")) {
            camlbTo.selectOption(cam.to);
        }

        camtfFromDevName.setValue(cam.fromname);
        camtfToDevName.setValue(cam.toname);
        if (camtfFromIpMac.exists()) {
            camtfFromIpMac.setValue(cam.frommac);
            camtfToIpMac.setValue(cam.tomac);
        } else {
            camtfFromIp.setValue(cam.fromip);
            camtfToIp.setValue(cam.toip);
            if (cam.enableAdd1) {
                setSelected(camcbFromAddRange, true, false);
                camtfFromIpMask.setValue(cam.fromipmask);
            }

            if (cam.enableAdd2) {
                setSelected(camcbToAddRange, true, false);
                camtfToIpMask.setValue(cam.toipmask);
            }
        }

        takess("check all values");
        click(ipMAMDeviceAdd);
        MyCommonAPIs.waitElementNot(camtfFromDevName.getSearchCriteria());
    }

    public String          portMode       = "button[data-target*=PortConfigurationMode]";
    public String          cbRadiusConfig = "#divDetShwEditVlan input";
    public SelenideElement btnSave        = $(".in button:last-child");

    public SelenideElement cbSwitchRadiusOption(String dutName) {
        return $x(String.format("//h5/span[text()='%s']/../../../..//input", dutName));
    }

    /**
     * @param isEnable
     *                 true to enable radius on both sw, false to disable
     */
    public void setRadiusConfig(boolean isEnable) {
        setSelected(cbSwitchRadiusOption(WebportalParam.sw1deveiceName), isEnable, true);
        waitReady();
        if (isEnable && btnSave.isDisplayed()) {
            btnSave.click();
        }
        if (WebportalParam.enaTwoSwitchMode) {
            setSelected(cbSwitchRadiusOption(WebportalParam.sw2deveiceName), isEnable, true);
            if (isEnable && btnSave.isDisplayed()) {
                btnSave.click();
            }
        }
        waitReady();
        sleepi(4); // Suck this sleep, if click too fast, it will search save button in current page
    }

}
