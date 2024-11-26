/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.ElementsCollection;

import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.RadiusConfigurationElement;
import webportal.webelements.WiredVLANElement;

/**
 * @author Lavi
 */
public class WiredVLANPage extends WiredVLANElement {
    /**
     *
     */

    final static Logger logger = Logger.getLogger("WiredVLANPage");

    public WiredVLANPage() {
        // TODO Auto-generated constructor stub
        logger.info("init...");
    }

    public WiredVLANPage(boolean noPage) {
        WebCheck.checkHrefIcon(URLParam.hrefWiredSetting);
        logger.info("initex...");
    }

    public void gotoPage() {
        logger.info(String.format("gotoVlanPage ..."));
        open(URLParam.hrefWiredSetting, true);
        // TODO: sometime vlan list does not ready for sorting, how to make sure list is finallized?
        MyCommonAPIs.waitReady();
        sleep(5, "list is rendering...");
    }

    public void gotoVlanIpFilter() {
        logger.info(String.format("gotoVlanIpFilter ..."));
        open(URLParam.hrefWiredSettingIpFiltering, true);
    }

    public List<String> getVlans() {
        logger.info(String.format("getVlans..."));
        MyCommonAPIs.waitReady();
        if (!$(vlanlistTableVlanIdCol).exists())
            return new ArrayList<String>();
        MyCommonAPIs.getTexts(vlanlistTableVlanIdCol);
        return MyCommonAPIs.getTexts(vlanlistTableCol);
    }

    public List<String> getVlanIDs() {
        if (!$(vlanlistTableVlanIdCol).exists())
            return new ArrayList<String>();
        return MyCommonAPIs.getTexts(vlanlistTableVlanIdCol);
    }

    public boolean checkVlan(String vlanName, String vlanId) {
        List<String> lsVlans = getVlans();
        List<String> lsVlanIds = getVlanIDs();
        if (lsVlans.contains(vlanName) && lsVlanIds.contains(vlanId))
            return true;
        else
            return false;
    }

    /**
     * Delete all custom vlans by Ids
     */
    public void deleteAllVlan() {
        takess("delete All data");
        try {
            gotoPage();
            List<String> lsVlans = getVlanIDs();
            for (String vID : lsVlans) {
                logger.info(String.format(": %s", vID));
                if (!(vID.equals("1"))) {
                    editLine(vlanlistTable, vlanlistTableVlanIdCol, vID, vlanItemDel);
                    clickButton(2);
                } else {
                    continue;
                }

                // wait table to refresh if it's too many
                sleep(4, "vlan list to update");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name
     * @param byID
     *             true to select vlan by id, false by name
     */
    public void deleteVlan(String name, boolean byID) {
        List<String> lsVlans;
        String colName = vlanlistTableCol;
        if (byID) {
            lsVlans = getVlanIDs();
            colName = vlanlistTableVlanIdCol;
        } else {
            lsVlans = getVlans();
        }
        logger.info(String.format(": %s", name));
        if (lsVlans.contains(name)) {
            editLine(vlanlistTable, colName, name, vlanItemDel);
            clickButton(2);
        } else {
            logger.info("no vlan was deleted");
        }
    }

    public void checkWarning() {
        if (cbNotShowAgain.exists()) {
            btnNotShowAgainOK.click();
        }
    }

    /**
     * @param name
     *                 since vlanName can be same, just create new one or ignore if vlanId was existed.
     * @param vlanid
     *                 the new id to set
     * @param vlanType
     *                 0 - data, 1 - voice, 2 - video/1-6 for all type of vlan
     */
    public void newVlan(String name, String vlanid, int vlanType) {
        logger.info(String.format(": %s-%s-%s", name, vlanid, vlanType));
        List<String> lsVlans = getVlanIDs();
        if (lsVlans.contains(vlanid))
            return;

        waitElement(vlanAdd);
        click(vlanAdd);
        MyCommonAPIs.waitReady();
        boolean needSet = false;
        if (btnClickHere.exists()) {
            if ((vlanType >= 0) && (vlanType < 3)) {
                NetworkSetupPage nsp = new NetworkSetupPage();
                nsp.gotoPage();
                nsp.createNetwork("testnet" + vlanid, vlanType, name, vlanid);
                gotoPage();
            } else {
                btnClickHere.click();
                needSet = true;
            }
        } else {
            needSet = true;
            clickBoxLastButton(); // new after 5.5
            MyCommonAPIs.waitReady();
            if (vlanType > 1) {
                $(String.format(sVlanType, vlanType)).click();
            }
            click(vlanNext);
            MyCommonAPIs.waitReady();
            checkWarning();
        }
        if (needSet) {
            vlanName.setValue(name);
            setVlanId(vlanid);
            clickSave();
            MyCommonAPIs.waitReady();
        }
    }
    
    public void newPrivateVlan(String Name, String vlanId, boolean withLag) {
        logger.info(String.format(": %s-%s-%s", Name, vlanId, withLag));
        List<String> lsVlans = getVlanIDs();
        if (lsVlans.contains(vlanId))
            return;
        waitElement(vlanAdd);
        click(vlanAdd);
        MyCommonAPIs.waitReady();
        click(btnClickHere);
        vlanName.setValue(Name);
        setVlanId(vlanId);
        if (withLag) {
            showPublicPort.click();
            $("#divBoxShdwEDitVlan0 #three .icon-L1").click(); // select first lag port
            waitReady();
            $("#ulListEditVlan0 #publicBtn2_0").click(); // select first access port button
            waitReady();
        }
        clickSave();
    }

    /**
     * @param name
     *                 open {@code vlanid} no matter how {@code vlanName} is
     * @param vlanid
     *                 the new id to set in case of non-exist, if it's "", then open existed
     * @param vlanType
     *                 0 - data, 1 - voice, 2 - video/1-6 for all type of vlan
     */
    public void openVlan(String name, String vlanid, int vlanType) {
        logger.info(String.format(": %s-%s-%s", name, vlanid, vlanType));
        if (vlanid.equals("")) {
            editLine(vlanlistTable, vlanlistTableCol, name, vlanItemEdit);
        } else {
            newVlan(name, vlanid, vlanType);
            openVlan(vlanid);
        }
    }

    /**
     * make sure vlanId must be existed
     *
     * @param vlanId
     */
    public void openVlan(String vlanId) {
        editLine(vlanlistTable, vlanlistTableVlanIdCol, vlanId, vlanItemEdit);
    }

    /**
     * @param ipmac
     *              String to ip or mac for selection
     * @param isIp
     *              true is to lookup ip, false to mac
     */
    public void openIpFilterMacAuth(String ipmac, boolean isIp) {
        logger.info(String.format(": %s-%s", ipmac, isIp));
        if ($(ipAclCustomTable).exists()) {
            editLine(ipAclCustomTable, ipmacAclCustomList, ipmac, sIpMacEdit);
        } else {
            if (isIp) {
                System.out.println("In editline");
                editLine(ipAclTable, ipmacAclList, ipmac, sIpMacEdit);
            } else {
                editLine(ipMacAuthTable, ipmacAclList, ipmac, sIpMacEdit);
            }
        }
    }

    /**
     * @param name
     *                vlan name
     * @param devName
     * @param devIp
     * @param allow
     */
    public void editVlanIpFiltering(String name, String devName, String devIp, boolean allow) {
        logger.info(String.format(": %s-%s-%s-%s", name, devName, devIp, allow));
        boolean editmode = isIpFilterMacAuthOpen();
        if (!editmode) {
            logger.info("edit page is not opened");
            if (!clickIpFilter.exists()) {
                logger.info("select vlan and edit vlan again");
                gotoPage();
                openVlan(name, "", 0);
                MyCommonAPIs.waitElement(clickIpFilter.getSearchCriteria());
            }

            click(clickIpFilter);

            if (allow) {
                setPolicy(true);
            } else {
                setPolicy(false);
            }
            waitReady();
        }

        if (enaIpFilterCustom) {
            setCAM(camData);
        } else {
            addIpFilterMAM(devName, devIp);
        }

        MyCommonAPIs.waitReady();
        if (!editmode) {
            enableAcl(true);
        }
        getPageErrorMsg();
        MyCommonAPIs.sleepi(1);
    }

    public void editVlanIpFilteringAllow(String name, String devName, String devIp) {
        editVlanIpFiltering(name, devName, devIp, true);
    }

    public void editVlanIpFilteringDeny(String name, String devName, String devIp) {
        editVlanIpFiltering(name, devName, devIp, false);
    }

    /**
     * @param name
     * @param devName
     * @param devMac
     * @param allow
     */
    public void editVlanMacAuth(String name, String devName, String devMac, boolean allow) {
        logger.info(String.format(": %s-%s-%s-%s", name, devName, devMac, allow));
        boolean editmode = isIpFilterMacAuthOpen();
        if (!editmode) {
            if (!clickMacAuth.exists()) {
                gotoPage();
                openVlan(name, "", 0);
            }
            click(clickMacAuth);

            if (allow) {
                setPolicy(true);
            } else {
                setPolicy(false);
            }
            waitReady();
        }

        if (enaIpFilterCustom) {
            setCAM(camData);
        } else {
            addMacAuthMAM(devName, devMac);
        }

        MyCommonAPIs.waitReady();
        if (!editmode) {
            enableAcl(true);
        }

        getPageErrorMsg();
    }

    /**
     * @param name:
     *                enaIpFilterCustom is true will fw to new func
     * @param devName
     * @param devMac
     */
    public void editVlanMacAuthAllow(String name, String devName, String devMac) {
        editVlanMacAuth(name, devName, devMac, true);
    }

    /**
     * @param name
     *                enaIpFilterCustom is true will fw to new func
     * @param devName
     * @param devMac
     */
    public void editVlanMacAuthDeny(String name, String devName, String devMac) {
        editVlanMacAuth(name, devName, devMac, false);
    }

    public void enableAcl(boolean isEnable) {
        enableACL(isEnable);
    }

    public void showPortMember() {
        //scrollTo(false);
        if (showPortMember.exists()) {
            scrollTo(false);
            System.out.println("Scroll to see plus icon");
            if(showPortMember.isDisplayed()) {
                System.out.println("Click plus icon");
                click(showPortMember);
            }
        }
        scrollTo(false);
    }

    /**
     * @param vlanId
     * @param devName
     * @param accessPorts
     *                    like "1,2"
     * @param trunkPorts
     *                    like "1,2"
     * @param delPorts
     *                    like "1,2"
     * @param isBR
     *                    true is set port for BR
     */
    public void editVlanPorts(String vlanId, String devName, String accessPorts, String trunkPorts, String delPorts, boolean isBR) {
        editLine(vlanlistTableId, 2, vlanId, 0);
        showPortMember();
        editPorts(devName, accessPorts, trunkPorts, delPorts, isBR);
        clickButton(0);
    }

    /**
     * @param switchIndex
     *                    from 0-sw1, 1-sw2
     * @param portIndex
     *                    from 1-n
     * @param mode
     *                    from 0 - 2
     */
    public void setPortAuthMode(int switchIndex, String portIndex, int mode) {
        RadiusConfigurationElement rce = new RadiusConfigurationElement();

        clickPort(portIndex, switchIndex);
        clickDeviceButton(switchIndex, 3);

        rce.selectMode(mode);
        rce.btnSave.click();
        waitReady();
    }

    public void setManagePort2Auth() {
        setPortAuthMode(0, WebportalParam.sw1ManagePort, 1);
        if (WebportalParam.enaTwoSwitchMode) {
            setPortAuthMode(1, WebportalParam.sw2ManagePort, 1);
        }
    }

    /**
     * select sw1 port4 to auth, sw2 port4 to unauth (set manage to force-auth first)
     *
     * @param vlanId
     *                      vlan id to select
     * @param isEnable
     *                      true to enable radius
     * @param isLAG
     *                      true just select port one time, false to select 2 lag ports
     * @param firstSWtoAuto
     *                      true to set first sw1 port to auto and sw2 to force-auth
     *                      false to set sw1 to force-auth, sw2 to force-unauth
     */
    public void editPortMode(String vlanId, boolean isEnable, boolean isLAG, boolean firstSWtoAuto) {
        logger.info(vlanId + "/" + isEnable + "/" + isLAG + "/" + firstSWtoAuto);
        editLine(vlanlistTableId, 2, vlanId, 0);
        showPortMember();

        // make sure manager port is auth
        setManagePort2Auth();
        clickPort(WebportalParam.sw1LagPort1, 0);
        if (WebportalParam.enaTwoSwitchMode && !isLAG) {
            clickPort(WebportalParam.sw2LagPort1, 1);
        }

        waitReady();
        RadiusConfigurationElement rce = new RadiusConfigurationElement();
        ElementsCollection ecs = $$(portMode);
        int i = 0;
        if (!firstSWtoAuto) {
            i = 1;
        }

        clickDeviceButton(0, 3);
        waitReady();
        rce.selectMode(i++);
        rce.btnSave.click();
        waitReady();

        clickDeviceButton(1, 3);
        waitReady();
        rce.selectMode(i);
        rce.btnSave.click();
        waitReady();

        setRadiusConfig(isEnable);
        clickButton(0);
    }

    /**
     * Direct set port mode to access/trunk or delete
     *
     * @param devName
     * @param accessPorts
     *                    like "1,2"
     * @param trunkPorts
     *                    like "1,2"
     * @param delPorts
     *                    like "1,2"
     * @param isBR
     *                    true is set port for BR
     */
    public void editPorts(String devName, String accessPorts, String trunkPorts, String delPorts, boolean isBR) {
        logger.info(devName + "/a:" + accessPorts + "/t:" + trunkPorts + "/d:" + delPorts + "/br:" + isBR);
        int devType = getDeviceType(devName);
        int offset = 0;
        if (isBR) {
            offset = 1;
        }
        for (String p : accessPorts.split(",")) {
            if (p.equals("")) {
                break;
            }
            logger.info("set acess port to: " + p);
            int port = Integer.parseInt(p) + offset;
            clickPort(port, devName);
            clickDeviceButton(devType, 1);
        }
        for (String p : trunkPorts.split(",")) {
            if (p.equals("")) {
                break;
            }
            logger.info("set trunk port to: " + p);
            int port = Integer.parseInt(p) + offset;
            clickPort(port, devName);
            clickDeviceButton(devType, 2);
        }
        for (String p : delPorts.split(",")) {
            if (p.equals("")) {
                break;
            }
            logger.info("delete port to: " + p);
            int port = Integer.parseInt(p) + offset;
            clickPort(port, devName);
            if (isBR) {
                clickDeviceButton(devType, 3);
            } else {
                clickDeviceButton(devType, 4);
            }
        }
    }

    /**
     * @param is2ports
     *                 true to add 2 ports
     * @param istrunk
     *                 true to add port with trunk mode
     * @param is2ndSW
     *                 true to select port on 2nd switch
     */
    public void addPortToVlan(boolean is2ports, boolean istrunk, boolean is2ndSW) {
        logger.info(is2ports + "/" + istrunk + "/" + is2ndSW);
        click(clickVlanSetting);
        MyCommonAPIs.waitReady();
        showPortMember();
        removeAllPort(false);
        if (is2ndSW) {
            removeAllPort(true);
        }
        selectPort(4, istrunk, 0);
        if (is2ndSW) {
            selectPort(4, istrunk, 1);
        }
        if (is2ports) {
            selectPort(5, istrunk, 0);
            if (is2ndSW) {
                selectPort(5, istrunk, 1);
            }
        }

        clickSave();
        MyCommonAPIs.sleepi(2);
        MyCommonAPIs.waitReady();
    }

    /**
     * @param istrunk
     *                true to set Trunk mode (port must be in lag already)
     */
    public void addLagPortToVlan(boolean istrunk) {
        click(clickVlanSetting);
        MyCommonAPIs.waitReady();
        showPortMember();
        selectPort(WebportalParam.getSwitchLagIndex(false, false), istrunk, 0);
        clickSave();
        MyCommonAPIs.waitReady();
        sleepsync();
    }

    /**
     * @param is2ndport
     *                  true to select port 5, false to select port 4
     * @param istrunk
     *                  true to trunk mode, false to access mode
     */
    public void addLagPortToVlan(boolean is2ndport, boolean istrunk) {
        click(clickVlanSetting);
        MyCommonAPIs.waitReady();
        showPortMember();

        if (is2ndport) {
            selectPort(WebportalParam.getSwitchLagIndex(false, true), istrunk, 0);
        } else {
            selectPort(WebportalParam.getSwitchLagIndex(false, false), istrunk, 0);
        }

        clickSave();
        MyCommonAPIs.waitReady();
    }

    public List<String> getAllowDevices() {
        return getIpMacAcls();
    }

    public List<String> getAllowDevicesName() {
        return getIpMacNameAcls();
    }

    public void removeAllAclCli() {
        SwitchCLIUtils.cleanIpACL();
        SwitchCLIUtils.cleanMacACL();
    }

    public void deleteAllVlanCli() {
        SwitchCLIUtils.cleanVlan();
    }

    public void removeAllAcl() {
        logger.info(String.format("removeAllAcl"));
        try {
            gotoVlanIpFilter();
            click(clickIpFilter);
            clickIpFilterRemoveDo();
            MyCommonAPIs.waitReady();
            click(clickMacAuth);
            clickIpFilterRemoveDo();
            MyCommonAPIs.waitReady();

            sleep(10, "wait cloud to clear via cli");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * @param  vlanId
     * @param  lagId
     *                TODO
     * @param  isTag
     * @return
     */
    public boolean checkVlanLag(String vlanId, String lagId, boolean isTag) {
        String m;
        logger.info(String.format(": %s-%s-%s", vlanId, lagId, isTag));
        if (!WebportalParam.isRltkSW1) {
            String s = getCmdOutput(String.format("show vlan %s", vlanId), false);
            lagId = "\\d";
            if (isTag) {
                m = String.format(".*lag +%s +Include +Include +Tagged.*", lagId);
            } else {
                m = String.format(".*lag +%s +Include +Include +Untagged.*", lagId);
            }
            if (MyCommonAPIs.matches(s, m))
                return true;
            else
                return false;
        } else {
            String s = getCmdOutput(String.format("show running-config interfaces LAG %s", lagId), false);
            if (isTag) {
                if (s.contains(" tagg") && !s.contains(" untagg")) {
                    return true;
                }
            } else {
                if (s.contains(" untagg")) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public void clickAddVlanBtn() {
        waitElement(vlanAdd);
        click(vlanAdd);
        MyCommonAPIs.waitReady();
        if(btnContinue.exists()) {
            click(btnContinue);
        }
    }
    
    public void editVlanNameDescID(String vName, String vdesc, String vvlanid) {
        // change vlanName
        if (vName != null) {
            vlanName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            vlanName.sendKeys(Keys.BACK_SPACE);
            vlanName.sendKeys(vName);
        }
        // change desc
        if (vdesc != null) {
            vlanDesc.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            vlanDesc.sendKeys(Keys.BACK_SPACE);
            vlanDesc.sendKeys(vdesc);
        }
        // change vlanid
        if (vvlanid != null) {
            vlanId.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            vlanId.sendKeys(Keys.BACK_SPACE);
            vlanId.sendKeys(vvlanid);
        }
        clickButton(0);
    }

}
