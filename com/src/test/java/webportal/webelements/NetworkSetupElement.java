package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.apache.commons.lang.RandomStringUtils;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.CustomAccessManagment;
import webportal.param.CommonDataType.ManulAccessManagment;
import webportal.param.WebportalParam;

public class NetworkSetupElement extends MyCommonAPIs {
    Logger                       logger           = Logger.getLogger("NetworkSetupElement");
    public ManulAccessManagment  mamData;
    public CustomAccessManagment camData;
    boolean                      showAgainClicked = false;

    public SelenideElement btnAdd = $(".addIcon span");

    public void initTestData() {
        showAgainClicked = false;
        mamData = new CommonDataType().dataMAM;
        camData = new CommonDataType().dataCAM;
    }

    public void clickAdd() {
        btnAdd.click();
        waitReady();
        sleepi(4);
        if (!showAgainClicked) { // 此 VLAN 不会被发送到没有中继或访问端口成员的商用路由器。
            // for never show again box
            showAgainClicked = true;
            sleepi(10);
            if ($(sPopButtonCss).exists()) {
                clickBoxFirstButton();
            }
        }
    }

    public String          lsNetworks     = "tbody tr td";
    public String          editicon = "//td[text()='%s']/..//img[contains(@src,'edit.png')]";
    public SelenideElement txtCurrentstep = $(".StepFormBlock .currentStep span");
    public String          txtNextStep    = "//div[@class='StepFormBlock']//span[text()='%s']";

    public int getCurrentStep() {
        return Integer.parseInt(getText(txtCurrentstep));
    }
    
    public void clickEditIcon(String networkname) {
        waitElement(lsNetworks);
        for (SelenideElement se : $$(lsNetworks)) {
            if (getText(se).contains(networkname)) {
                System.out.print("check in if");
                se.hover();
                SelenideElement editicon_ele = $x(String.format(editicon, networkname));
                editicon_ele.click();
                break;
            }
        }
    }

    // configure network
    public SelenideElement txtNetName  = $("[name*=networkName]");
    public SelenideElement txtNetDesc  = $("[name*=vlanDesc]");
    public SelenideElement lbNetType   = $("[name*=networkType]");
    public SelenideElement txtvlanName = $("[name*=vlanName]");
    public SelenideElement txtvlanId   = $("[name*=vlanId]");

    /**
     * @param index
     *              0 - data, 1 - voice, 2 - video
     */
    public void selectNetType(int index) {
        for (int i = 0; i < 2; i++) { // try to fix sometimes it was not selected
            lbNetType.selectOption(index);
            sleep(1);
        }
    }

    public SelenideElement        btnSkip               = $(".cancelBtn");
    public SelenideElement        btnNextOrViewNetworks = $(".actionBtnRow button:last-child");
    public SelenideElement        txtSuccessful         = $("img[src*=success]");
    public static SelenideElement btnNext               = $x(
            String.format("(//button[text()='Next' or text()='%s'])[last()]", WebportalParam.getLocText("createVlanPageTopBtn", "next")));
    public static SelenideElement btnConfirm            = $x(
            String.format("//button[text()='Confirm' or text()='%s']", WebportalParam.getLocText("vlanOverhaul", "confirm")));
    public static SelenideElement btnViewNetworks       = $x(
            String.format("//button[text()='View Networks' or text()='%s']", WebportalParam.getLocText("View Networks")));

    public void finishAllStep() {
        int trytimes = 10;
        boolean bDone = false;
        while (trytimes > 0) {
            trytimes--;
            if (btnNext.exists()) {
                takess("check for network");
                waitReady();
                if ($(sPopButtonCss).exists()) {
                    clickBoxLastButton();
                    continue;
                }
                setLanIp();
                if (btnNext.isDisplayed()) {
                    try {
                        btnNext.click();
                    } catch (Throwable e) {
                        takess();
                        logger.info("some error");
                    }
                    continue;
                }
            }
            if (btnConfirm.exists()) {
                takess("check for confirm");
                btnConfirm.click();
                continue;
            }
            if (btnViewNetworks.exists()) {
                takess("check for view status");
                bDone = true;
                waitElement(txtSuccessful);
                btnViewNetworks.click();
                break;
            }
        }
        if (bDone) {
            waitReady();
            
        } else
            throw new RuntimeException("Not able to finish all steps");
        
        MyCommonAPIs.sleepi(30);
    }

    /**
     * @param isSkip
     *               true to skip false to next
     */
    public void clickNextOrSkip(boolean isSkip) {
        takess("check for network");
        if (isSkip) {
            btnSkip.click();
        } else {
            if (btnNextOrViewNetworks.isDisplayed()) {
                btnNextOrViewNetworks.hover();
            }
            btnNextOrViewNetworks.click();
        }
        waitReady();
    }

    // Wired Setting
    String                 sDeviceName         = sPortBlockCss + " li h5";
    String                 sDevicePorts        = sPortBlockCss + " [id*=divBoxScrlEditVlan]";
    String                 sDevicePortNo       = "ul li";
    String                 sDevicePortMode     = "#ulListEditVlan button";
    String                 sDevicePortRadius   = ".box-shadow .ButtonBlockPop button";
    String                 sBtnSelectAll       = "//H5[contains(text(), '%s')]/../../../..//button";
    String                 sBtnMemberSelectAll = ".SubsAccordianBlock >.networksetting > div:first-child #portBtn_";
    String                 sBtnRadiusSelectAll = ".SubsAccordianBlock >.networksetting > div:last-child #portBtn_";
    public SelenideElement cbIgmpSnooping      = $(".ChartAvalDetail > .row [name*=broadcast]");
    public SelenideElement lbTrafficPriority   = $(".fontSemiBold+select");
    public String          cbRadiusStatus      = ".m-b-20 [name*=broadcast]";
    public String          cbEnableRadius      = ".m-b-20 [name*=broadcast]+span";

    // mac/ip acl
    public SelenideElement cbMacAuth = $("#divBtnMacAuth input");
    public SelenideElement cbIpAuth  = $("#divDataToggle1Ip input");

    public SelenideElement txtIpManulDevName = $(".in #deviceName1");
    public SelenideElement txtIpManulDevIp   = $(".in #ipaddress1");
    public SelenideElement cbIpRange         = $(".in #range1");
    public SelenideElement txtIpMask         = $(".in #mask1");

    public SelenideElement txtIpCustomDevName1 = $(".in #deviceName2");
    public SelenideElement txtIpCustomDevIp1   = $(".in #ipaddressmannual2");
    public SelenideElement cbIpRange1          = $x("(//*[contains(@class,' in')]//*[contains(@id, 'subscription')])[1]");
    public SelenideElement txtIpMask1          = $(".in #mask2");
    public SelenideElement txtIpCustomDevName2 = $(".in #deviceName3");
    public SelenideElement txtIpCustomDevIp2   = $(".in #ipaddressmannual3");
    public SelenideElement cbIpRange2          = $x("(//*[contains(@class,' in')]//*[contains(@id, 'subscription')])[2]");
    public SelenideElement txtIpMask2          = $(".in #mask3");

    public SelenideElement txtMacManulDevName = $(".in #deviceName1");
    public SelenideElement txtMacManulDevMac  = $(".in #macaddress1");
    public SelenideElement cbAccessFrom       = $(".in #source1");
    public SelenideElement cbAccessTo         = $(".in #destination1");

    public SelenideElement lbFromDev            = $(".in #sourceclient");
    public SelenideElement lbToDev              = $(".in #destinationclient");
    public SelenideElement txtMacCustomDevName1 = $(".in #deviceName2");
    public SelenideElement txtMacCustomDevMac1  = $(".in #macaddressmannual2");
    public SelenideElement txtMacCustomDevName2 = $(".in #deviceName3");
    public SelenideElement txtMacCustomDevMac2  = $(".in #macaddressmannual3");

    String sManulIpMac  = "span[class*=ipspan]";
    String sCustomIpMac = "[id*=td2] span[class*=from-address]";

    /**
     * @param isIp
     * @param isAllow
     *                0 - allow, 1 - deny
     */
    public void setAclType(boolean isIp, int isAllow) {
        if (isIp) {
            $("#policyIp").selectOption(isAllow);
        } else {
            $("#policy").selectOption(isAllow);
        }
    }

    /**
     * @param isIp
     *                 true for ip
     * @param isEnable
     *                 true to enable
     */
    public void enableMacIpAcl(boolean isIp, boolean isEnable) {
        if (isIp) {
            setSelected(cbIpAuth, isEnable, true);
        } else {
            setSelected(cbMacAuth, isEnable, true);
        }
    }

    /**
     * @param isIp
     *              true to set ip, false to mac
     * @param index
     *              0-manual, 1-custom, 2-add device, 3-remove device
     */
    public void clickMacIpButton(boolean isIp, int index) {
        if (isIp) {
            $$("#divRow4IP button").get(index).click();
        } else {
            $$("#divStaticTextRghtMacAuth button").get(index).click();
        }
    }

    /**
     * @param isIp
     *                 true to set ip, false to mac
     * @param isCustom
     *                 true to set custom, false to manual
     */
    public void setIpMacACL(boolean isIp, boolean isCustom) {
        logger.info("isIp-" + isIp + ", isCustom-" + isCustom);
        waitReady();
        if (isCustom) {
            
            camData.Dump();
            if (!camData.from.toLowerCase().contains("mann")) {
                lbFromDev.selectOption(camData.from);
                System.out.println("1111111111111111");
            }
            
            if (!camData.to.toLowerCase().contains("mann")) {
                lbToDev.selectOption(camData.to);
                System.out.println("222222222222222");
            }
            MyCommonAPIs.sleepi(5);
            if (isIp) {
                System.out.println("77777777777777777777777777777777777777777777777777777777");
                txtIpCustomDevName1.setValue(camData.fromname);
                txtIpCustomDevIp1.setValue(camData.fromip);
                if (!camData.fromipmask.equals("")) {
                    setSelected(cbIpRange1, true, false);
                    txtIpMask1.setValue(camData.fromipmask);
                } else {
                    setSelected(cbIpRange1, false, false);
                }
                txtIpCustomDevName2.setValue(camData.toname);
                txtIpCustomDevIp2.setValue(camData.toip);
                if (!camData.toipmask.equals("")) {
                    setSelected(cbIpRange2, true, false);
                    txtIpMask2.setValue(camData.toipmask);
                } else {
                    setSelected(cbIpRange2, false, false);
                }
            } else {
                txtMacCustomDevName1.setValue(camData.fromname);
                txtMacCustomDevMac1.clear();
                txtMacCustomDevMac1.setValue(camData.frommac);
                txtMacCustomDevName2.setValue(camData.toname);
                txtMacCustomDevMac2.clear();
                txtMacCustomDevMac2.setValue(camData.tomac);
            }
        } else {
            mamData.Dump();
            if (isIp) {
                txtIpManulDevName.setValue(mamData.devName);
                txtIpManulDevIp.setValue(mamData.devIp);
                if (!mamData.devMask.equals("")) {
                    setSelected(cbIpRange, true, false);
                    txtIpMask.setValue(mamData.devMask);
                } else {
                    setSelected(cbIpRange, false, false);
                }
            } else {
                txtMacManulDevName.setValue(mamData.devName);
                txtMacManulDevMac.clear();
                txtMacManulDevMac.setValue(mamData.devMac);
            }
            setSelected(cbAccessFrom, mamData.enableFrom, false);
            setSelected(cbAccessTo, mamData.enableTo, false);
        }

        takess("check whether modified values are done to new one in edit page");
        clickBoxLastButton();
        takess("check whether modified values are done to new one in saved page");
    }

    public void openACL(String ipMac, boolean isCustom) {
        logger.info("ipMac-" + ipMac + ", isCustom-" + isCustom);
        ElementsCollection lsAcl;
        if (isCustom) {
            lsAcl = $$(sCustomIpMac);
        } else {
            lsAcl = $$(sManulIpMac);
        }
        for (SelenideElement s : lsAcl) {
            if (getText(s).equals(ipMac)) {
//                SelenideElement editicon   = $x("//span[@class='from-address' and text()='" + ipMac +"']/../../td[4]//img[contains(@src,'commonIcons/edit.png')]");
                SelenideElement editicon   = $x("//span[text()='" + ipMac +"']/ancestor::tr/td[4]//img[contains(@src,'commonIcons/edit.png')]");
                s.scrollIntoView(true);
                System.out.println("After scrollintoview");
                System.out.println(editicon.exists());
                System.out.println(editicon.isDisplayed());
                s.hover();
                System.out.println("After hover");
                System.out.println(editicon.exists());
                System.out.println(editicon.isDisplayed());
                //"//span[text()='" + ipMac +"']/../../../td[4]//img[contains(@src,'commonIcons/edit.png')]"
                
                System.out.println("Click edit button");
                editicon.click();
//                for (SelenideElement t : $$("img[src*=edit]")) {
//                    if (t.isDisplayed()) {
//                        System.out.println("Click edit button");
//                        t.click();
//                        break;
//                    }
//                }
                break;
            }
        }
    }

    public void setIgmpSnooping(boolean enable) {
        setSelected(cbIgmpSnooping, enable, true);
    }

    public void setTrafficPriority(String level) {
        lbTrafficPriority.selectOptionContainingText(level);
    }

    public String getSwitchSelectAllButton(String devName) {
        return String.format(sBtnSelectAll, devName);
    }

    public String getDeviceString(String devName) {
        return String.format("//h5[contains(text(),'%s')]/../../../../../..", devName);
    }
    
    public SelenideElement getPort(String devName, String portId) {
        return getDevicePort(getDeviceString(devName), portId);
    }

    public static String sw1DeviceXpath = "";
    public static String sw2DeviceXpath = "";
    public static String br1DeviceXpath = "";
    public static String ap1DeviceXpath = "";

    /**
     * @param  devType
     *                 0-sw1, 1-sw2, 2-br, 3-ap
     * @return
     */
    public String getDeviceXpathX(int devType) {
        String result = "fixme";
        if ((devType == 0) || (devType == 1)) {
            if (sw1DeviceXpath.isEmpty()) {
                sw1DeviceXpath = getDeviceXpath(0);
                sw2DeviceXpath = getDeviceXpath(1);
            }
            if (devType == 0) {
                result = sw1DeviceXpath;
            } else {
                result = sw2DeviceXpath;
            }
        }
        if ((devType == 2)) {
            if (br1DeviceXpath.isEmpty()) {
                br1DeviceXpath = getDeviceXpath(devType);
            }
            result = br1DeviceXpath;
        }
        if ((devType == 3)) {
            if (ap1DeviceXpath.isEmpty()) {
                ap1DeviceXpath = getDeviceXpath(devType);
            }
            result = ap1DeviceXpath;
        }
        return result;
    }

    public String getDevicePortIdXpathX(int devType) {
        return getDeviceXpathX(devType) + portIdXpath;
    }

    /**
     * @param portMemberOrRadius
     *                           true to select port under port member(first block), false is to select port under radius(2nd block)
     * @param devName
     * @param portIndex
     *                           index of device from 1~n
     */
    public void clickPort(boolean portMemberOrRadius, String devName, int portIndex) {
        logger.info(portMemberOrRadius + "/" + devName + "/" + portIndex);
        String sPortBlock;
        if (portMemberOrRadius) {
            sPortBlock = String.format(sPortBlockXpath, 1);
        } else {
            sPortBlock = String.format(sPortBlockXpath, 2);
        }

        int iIndex = getPortIndex(portIndex, getPortNo(devName));
        System.out.println("!!!!!!!!!!!!!!!!!!Check port xpath");
        System.out.println(sPortBlock + getDevicePortIdXpathX(getDeviceType(devName)));
        System.out.println(iIndex);
        
        
        
        $$x(sPortBlock + getDevicePortIdXpathX(getDeviceType(devName))).get(iIndex).click();
        waitReady();
    }

    /**
     * @param portMemberOrRadius
     *                           true to select port under port member, false is to set radius
     * @param mode
     *                           0 - access, 1 - trunk, 2 - delete
     *                           0 - auto, 1 - auth, 2 - unauth
     * @param doubleSelect
     *                           true to click twice for deselect
     */
    public void setAllPorts(boolean portMemberOrRadius, int mode, boolean doubleSelect) {
        logger.info(portMemberOrRadius + "/" + mode + "/" + doubleSelect);
        String sPortBlock;
        if (portMemberOrRadius) {
            sPortBlock = String.format(sPortBlockXpath, 1);
        } else {
            sPortBlock = String.format(sPortBlockXpath, 2);
        }

        String xpath = getSwitchSelectAllButton(WebportalParam.sw1deveiceName);
        $x(sPortBlock + xpath).click();
        if (doubleSelect) {
            $x(sPortBlock + xpath).click();
        }
        sleepi(2);

        xpath = getSwitchSelectAllButton(WebportalParam.sw2deveiceName);
        $x(sPortBlock + xpath).click();
        if (doubleSelect) {
            $x(sPortBlock + xpath).click();
        }
        sleepi(4);

        waitReady();
        if (portMemberOrRadius) {
            clickPortMode(mode);
        } else {
            clickPortRadius(mode);
        }
    }

    /**
     * @param mode
     *             0 - access, 1 - trunk, 2 - delete
     */
    public void clickPortMode(int mode) {
        $$(sDevicePortMode).get(mode).click();
        sleepi(1); // sometimes button is not clicked, twice is nonharmful :)
        click($$(sDevicePortMode).get(mode), true);
    }

    /**
     * @param devName
     * @param portList
     *                 split by , (index of device from 1~n)
     * @param portMode
     *                 0 - access, 1 - trunk, 2 - delete
     */
    public void setPortMembers(String devName, String portList, int portMode) {
        logger.info(devName + "/" + portList + "/" + portMode);
        if (portList.equals(""))
            return;
        for (String s : portList.split(",")) {
            System.out.println("the s is " + s);
            clickPort(true, devName, Integer.parseInt(s));
            waitReady();
            clickPortMode(portMode);
            waitReady();
        }
    }

    /**
     * @param isData
     *               tag port 1, and true to untag port 2 for data vlan, false to all port to auto for radius
     */
    public void setSanityPortMembers(boolean isData) {
        logger.info(isData ? "tag port 1 and untag 2" : "tag port 1");
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            click(getPort(DevicesDashPageElements.mapDeviceList.get(sNo).get("Name"), "1"));
        }
        clickPortMode(1);
        waitReady();
        if (isData) {
            for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
                click(getPort(DevicesDashPageElements.mapDeviceList.get(sNo).get("Name"), "2"));
            }
            clickPortMode(0);
            waitReady();
        } else {
            for (SelenideElement seBtn : $$(sBtnRadiusSelectAll)) {
                click(seBtn);
            }
            clickPortRadius(0);
            waitReady();
        }
    }

    /**
     * @param mode
     *             0 - auto, 1 - auth, 2 - unauth (current page only has one group)
     */
    public void clickPortRadius(int mode) {
        $$(sDevicePortRadius).get(mode).click();
    }

    /**
     * @param devName
     * @param portList
     *                   split by , (index of device from 1~n)
     * @param radiusMode
     *                   0 - auto, 1 - auth, 2 - unauth
     */
    public void setPortRadius(String devName, String portList, int radiusMode) {
        if (portList.equals(""))
            return;
        logger.info(devName + "/" + portList + "/" + radiusMode);
        for (String s : portList.split(",")) {
            clickPort(false, devName, Integer.parseInt(s));
            clickPortRadius(radiusMode);
        }
    }

    /**
     * @param devName
     * @param checked
     *                true to enable, false to disable
     */
    public void enableRadius(String devName, boolean checked) {
        String cbXpath = String.format("%s//input", getDeviceXpathX(getDeviceType(devName)));
        setSelected($x(cbXpath), checked, true);
        if (checked) {
            clickBoxLastButton();
        }
    }

    // Vlan ip conf
    public String          sVlanIPMode  = ".radio-inline";
    public SelenideElement txtGateway   = $("input[name*=gatewayAddress]");
    public SelenideElement txtMask      = $("input[name*=subnetMask]");
    public String          stxtSwitchIp = "//h5[text()='%s']/../div/input";
    public String          allInput     = "input[data-type*=ipAddress]";
    public SelenideElement btnVlanWarn  = $x("//*[text()=\"DHCP or static routing mode is enabled on other VLANs. In order to disable routing on the Management VLAN, first disable routing on the other VLANs.\"]");

    /**
     * @param isdhcp
     *               true to dhcp, false to static
     */
    public void setDhcpMode(boolean isdhcp) {
        if (isdhcp) {
            $$(sVlanIPMode).get(0).click();
        } else {
            $$(sVlanIPMode).get(1).click();
        }
    }

    /**
     * @param ipMode
     *               0 - dhcp, 1 - static, 2 - none
     */
    public void setVlanIpMode(int ipMode) {
        if (WebportalParam.isRltkSW1 || WebportalParam.isRltkSW2)
            throw new RuntimeException("Only support BRCM switch");
        $$(sVlanIPMode).get(ipMode).click();
    }

    public void setStaticIP(String devName, String ip) {
        String swElement = String.format(stxtSwitchIp, devName);
        if ($x(swElement).exists()) {
            setText(swElement, ip);
        } else {
            logger.info("Skip for device: " + devName);
        }
    }

    // wireless ssid setting
    public SelenideElement btnAddNewWiFi = $(".PoeShedulesBlock button");
    public SelenideElement txtSsidName   = $("input[name*=ssidName]");
    public SelenideElement txtSsidPass   = $("input[name*=password]");
    public SelenideElement btnAddSsid    = $(".actionBtnRow .saveBtn");
    
    // DHCP Server
    public SelenideElement lbSelectBRDevices = $("#DHCPServerVLANInput");
    public SelenideElement txtBRVlanId       = $(".DhcpServerSection [value*=ID]");
    public SelenideElement txtVlanIp         = $(".DhcpServerSection [name*=lanIP]");
    public SelenideElement txtVlanIpMask     = $(".DhcpServerSection [name*=lanSubnet]");
    public String          cbDhcpServer      = ".DhcpServerSection [name*=broadcast]";
    public SelenideElement txtStartIp        = $(".DhcpServerSection [name*=startIP]");
    public SelenideElement txtEndIp          = $(".DhcpServerSection [name*=endIP]");

    public void setLanIp() {
        if (txtVlanIp.exists()) {
            txtVlanIp.setValue("192.168." + RandomStringUtils.randomNumeric(2) + ".1");
        }
    }

    // Inter-Network Sharing
    public SelenideElement cbManagermentNetwork = $(".networkSharingSection [name*=broadcast]");
    public SelenideElement txtNetVlanId         = $(".networkSharingSection .statidetails > h5");
    public SelenideElement warning              = $x("//p[contains(text(),'This VLAN will not be sent to routers with no trunk or access port member.')]");
    public SelenideElement okWarning            = $x("//button[text()=\"Don't show again\"]");
    public SelenideElement warning1             = $x("//p[contains(text(),'Please ensure VLAN IP address subnet does not overlap with WAN IP address.')]");
    public SelenideElement okWarning1           = $x("//p[contains(text(),'Please ensure VLAN IP address subnet does not overlap with WAN IP address.')]/../../div[3]/button");
    public SelenideElement warning2             = $x("//p[contains(text(),'Make sure that the port on your router that supports the DHCP Server function is configured as a trunk port.')]");
    public SelenideElement skipWarning          = $x("//button[@class=\"btn btn-danger\" and text()=\"Skip\"]");
}
