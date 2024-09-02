package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRVPNGroupsElement;

public class DeviceBRVPNGroupsPage extends DeviceBRVPNGroupsElement {
    Logger logger = Logger.getLogger("DeviceBRVPNUsersPage");

    public DeviceBRVPNGroupsPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRvpnGroups);
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

    public List<String> getDevices(String groupName) {
        List<String> devList = new ArrayList<String>();
        String toClick = String.format(txtDeviceList, groupName);
        if ($x(toClick).exists())
            return getTexts(toClick);
        else {
            logger.warning(groupName + " has no device");
            return devList;
        }
    }
    
   public boolean checkdevicecount() {
       boolean result = false;
       String count =  getText(devicecount);
       System.out.println("This is the device Count----> "+ count);
       String devices = count.substring(1, 2);
       System.out.println("Number of Exact Devices Added --->"+ devices);
       return result;
   }

    public void deleteAllGroups() {
        takess("delete All data");
        List<String> groupList = getGroups();
        for (String groupName : groupList) {
            deleteGroup(groupName);
        }
    }

    public void deleteGroup(String groupName) {
        String toClick = String.format(btnDeleteGroup, groupName);
        for (int i = 0; i < 3; i++) {
            deleteOneDevice(groupName);
        }

        $x(toClick).click();
        clickYesNo(true);
    }

    /**
     * @param groupName
     *                  vpn name
     */
    public void deleteOneDevice(String groupName) {
        int iGroup = getGroups().indexOf(groupName);
        if (iGroup == -1) {
            logger.warning(groupName + " not found");
            return;
        }
        if (getDevices(groupName).size() < 1) {
            logger.warning(groupName + " has no device");
            return;
        }
        MyCommonAPIs.sleepi(10);
        String toClick = String.format(btnDeviceIcon, groupName);
        MyCommonAPIs.sleepi(10);
        $$x(toClick).last().click();
        MyCommonAPIs.sleepi(10);
        selectGroupDeviceMenu(groupName, 1);
        MyCommonAPIs.sleepi(10);
        clickYesNo(true);
    }

    public void deleteDevice(String groupName, String devName) {
        int iGroup = getGroups().indexOf(groupName);
        if (iGroup == -1) {
            logger.warning(groupName + " not found");
            return;
        }
        int iDevice = getDevices(groupName).indexOf(devName);
        if (iDevice == -1) {
            logger.warning(devName + " not found");
            return;
        }
        String toClick = String.format(btnDeviceIcon, groupName);
        $$x(toClick).get(iDevice).click();
        selectGroupDeviceMenu(groupName, 1);
        clickYesNo(true);
    }

    public void deleteDevices(String groupName) {
        int iGroup = getGroups().indexOf(groupName);
        if (iGroup == -1) {
            logger.warning(groupName + " not found");
            return;
        }
        for (int i = 0; i < 3; i++) {
            int iCount = getDevices(groupName).size();
            if (iCount == 0) {
                logger.warning(groupName + " has no device");
                break;
            }
            String toClick = String.format(btnDeviceIcon, groupName);
            $$x(toClick).get(0).click();
            selectGroupDeviceMenu(groupName, 1);
            clickYesNo(true);
        }
    }

    /**
     * @param groupName
     *                  vpn group name
     * @param iNo
     *                  how many devices to be added, 1~n
     */
    public void addDeviceGroup(String groupName, int iNo) {
        logger.info(String.format("add %d device to group: %s", iNo, groupName));
        int iGroup = getGroups().indexOf(groupName);
        if (iGroup == -1) {
            logger.warning(groupName + " not found");
            return;
        }

        String toClick = String.format(btnAddDevice, groupName);
        if ($x(toClick).exists()) {
            $x(toClick).click();
            waitReady();

            assertTrue($$(cbDeviceList).size() >= iNo, "we need device list more than: " + iNo);
            while (iNo > 0) {
                iNo--;
                logger.info(groupName + " add device");
                $$(cbDeviceList).get(iNo).click();
            }

            btnSaveDevice.click();
            waitReady();
        }
        takess("check vpn group status");
    }
    
    /**
     * Add 3 br which configured in XML into group
     *
     * @param groupName
     *                  vpn group name
     */
    public void addDeviceGroup(String groupName) {
        logger.info(String.format("add all xml device to group: %s", groupName));
        int iGroup = getGroups().indexOf(groupName);
        if (iGroup == -1) {
            logger.warning(groupName + " not found");
            return;
        }
        
        String toClick = String.format(btnAddDevice, groupName);
        if ($x(toClick).exists()) {
            $x(toClick).click();
            waitReady();
            
            if ($(txtDeviceListName).exists()) {
                List<String> devList = getTexts(txtDeviceListName);
                List<String> devListXML = Arrays
                        .asList(new String[] { WebportalParam.br1deveiceName, WebportalParam.br2deveiceName, WebportalParam.br3deveiceName });
                int devPos = 0;
                for (String devName : devList) {
                    if (devListXML.contains(devName)) {
                        logger.info("add to group: " + devName);
                        $$(cbDeviceList).get(devPos).click();
                    }
                    devPos++;
                }
            }
            
            btnSaveDevice.click();
            sleepi(10);
            waitReady();
            getPageErrorMsg();
            waitReady();
        }
        takess("check vpn group status");
    }

    /**
     * @param groupName
     *                  Note: only up to 3 devices
     */
    public void addAllDevices(String groupName) {
        int iGroup = getGroups().indexOf(groupName);
        logger.info("check name: " + groupName + " with index: " + iGroup);
        if (iGroup == -1) {
            logger.warning(groupName + " not found");
            return;
        }

        // you must pay additional vpn license for element below
        String toClick = String.format(btnAddDevice, groupName);
        System.out.println(toClick);
        if ($x(toClick).exists()) {
            $x(toClick).click();
            waitReady();
            takess("check device status");
            //by Shoib
            int iAdd = 1;
            for (SelenideElement se : $$(cbDeviceList)) {
                if (iAdd > 0) {
                    logger.info("add a device to group: " + groupName);
                    se.click();
                }
                iAdd--;
            }
            btnSaveDevice.click();
            waitReady();
        } else {
            logger.info("no space for device adding");
        }
    }

    public void createVPNGroup(String groupName) {
        logger.info("check to add group: " + groupName);
        if (getGroups().contains(groupName)) {
            logger.warning(groupName + " is already existed");
            return;
        }

        clickAdd();
        if (btnOK.exists()) {
            btnOK.click();
        }

        txtName.setValue(groupName);
        MyCommonAPIs.sleepi(10);
        btnSave.click();
        MyCommonAPIs.sleepi(10);
        waitReady();
    }

    public void createVpnGroupWithDevices(String groupName) {
        logger.info("create vpn with devices for: " + groupName);
        createVPNGroup(groupName);
        addAllDevices(groupName);
    }

    public void openGroupLink(String groupName) {
        takess("openGroupLink");
        logger.info("open " + groupName);
        String toClick = String.format(staHealthy, groupName);
        if ($x(toClick).exists()) {
            System.out.println(toClick);
            click($x(toClick));
        } else {
            toClick = String.format(staBroken, groupName);
            if ($x(toClick).exists()) {
                System.out.println(toClick);
                click($x(toClick));
            } else {
                toClick = String.format(staInPorcess, groupName);
                if ($x(toClick).exists()) {
                    System.out.println(toClick);
                    click($x(toClick));
                }
            }
        }
    }

    /**
     * https://insight.netgear.com/#/routers/tunnelDetails/DBA4F3A5FA054EC0AAFC415C7121C946/5JR1865NF0008/vpncheck
     *
     * @return
     */
    public boolean checkLinkStatus() {
        takess("checkLinkStatus");
        if ($(sLinkGroupName).exists() && (getText(sLinkGroupName).length() == 0))
            return false;
        if ($(sLinkGroupName).exists() && (getText(sLinkVPNName).length() == 0))
            return false;
        if ($(sLinkDeviceName).exists() && (getTexts(sLinkDeviceName).size() == 0))
            return false;
        if ($(sLinkStatus).exists() && (getTexts(sLinkStatus).size() == 0))
            return false;
        return true;
    }

    public void editTunnel(String name, String desc) {
        btnTunnelName.click();
        waitReady();
        txtTunnelName.setValue(name);
        txtTunnelDesc.setValue(desc);
        clickBoxLastButton();
        waitReady();
    }

    /**
     * [title*=VPN1]+div+div [class*=Process]
     *
     * @return check a link, return false if this link is not health
     */
    public boolean isLinkHealthy(String groupName) {
        String sState = String.format(staHealthy, groupName);
        String sState1 = String.format(staBroken, groupName);
        String sState2 = String.format(staInPorcess, groupName);
        takess("isLinkHealthy");
        if ($x(sState1).exists() || $x(sState2).exists())
            return false;
        if ($x(sState).exists())
            return true;
        return false;
    }

    public void waitLinkHealthy(String groupName) {
        boolean timeout = true;
        MyCommonAPIs.timerStart(15 * 60);
        while (MyCommonAPIs.timerRunning()) {
            try {
                if (isLinkHealthy(groupName)) {
                    timeout = false;
                    break;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            sleep(10, "go next.");
            refresh();
        }

        assertTrue(!timeout, "VPN link should be connected");
    }

    /**
     * @return check all link, return false if any link is not health
     */
    public boolean isAllLinkHealthy() {
        takess("isAllLinkHealthy");
        for (String groupName : getGroups()) {
            boolean sStatus = isLinkHealthy(groupName);
            if (!sStatus)
                return false;
        }
        return true;
    }
}
