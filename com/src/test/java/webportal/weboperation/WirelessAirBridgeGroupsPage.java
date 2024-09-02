package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;

import java.util.List;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessAirBridgeGroupsElement;

public class WirelessAirBridgeGroupsPage extends WirelessAirBridgeGroupsElement {
    Logger             logger                 = Logger.getLogger("WirelessAirBridgeGroupsPage");
    public ABGroupData abGroupData            = new ABGroupData();
    public String      sDefaultAirBridgeGroup = "defaultabg"; // for mesh

    public WirelessAirBridgeGroupsPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefWirelessairBridgeGroups);
        if (!$(sGroupTable).exists()) {
            open(URLParam.hrefWirelessairBridgeGroups, true);
        }
        waitReady();
    }

    public List<String> getGroups() {
        return getTextsTable(sGroupTable, 1);
    }
    
    public void removeAllGroup() {
        takess("delete All data");
        for (String group : getGroups()) {
            if (!group.toLowerCase().contains(sDefaultAirBridgeGroup)) {
                deleteGroup(group);
            }
        }
    }

    public int getNumberSatellite(String groupName) {
        try {
            return Integer.parseInt(getTextTable(sGroupTable, groupName, 3));
        } catch (Throwable e) {
            return 0;
        }
    }

    /**
     * Only create empty group
     * add support for from dashboard
     *
     * @param groupName
     * @param addAP
     *                  true to add an AP in abg
     */
    public void createGroup(String groupName, boolean addAP) {
        logger.info("add group: " + groupName);
        boolean addDevice = false;
        if (!txtGroupName.exists()) {
            click(btnAdd);
        }

        waitReady();
        sleepi(4);
        if (btnWarningCountryRegion.exists())
            throw new RuntimeException("wrong location or region is setted.");
        setText(txtGroupName, groupName);
        setText(txtGroupSSID, abGroupData.ssid);
        if (txtSecurityKey.exists()) {
            txtSecurityKey.sendKeys(abGroupData.key);
        } else {
            txtPassword.sendKeys(abGroupData.key);
        }

        setSelected(cbABMode, abGroupData.abMode, true);
        setSelected(cbRFMode, abGroupData.rfMode, true);
        lbChannelWidth.selectOptionContainingText(abGroupData.channelWidth);
        lbChannelNumber.selectOption(abGroupData.channelNumber);
        takess("set all options");
        if (!btnNextOrCreateNew.exists()) {
            click(btnSave);
        } else {
            addDevice = true;
            click(btnNextOrCreateNew);
        }
        if (!addAP) {
            if (!addDevice) {
                clickBoxFirstButton();
            }
        } else {
            clickBoxLastButton();
            setText(txtDeviceserialNo, getFakeDeviceNo("WAC502"));
            clickBoxLastButton();
            waitReady();
            waitElement(btnDeviceSelectGroup);
            selectGroup(groupName);
            clickBoxLastButton();
            waitReady();
            waitElement(txtDevicedeviceName);
            clickBoxLastButton();
            waitReady();
            btnDeviceNext.click();
            gotoPage();
        }
        waitReady();
    }

    public void deleteGroup(String groupName) {
        logger.info("delete group: " + groupName);
        if (!getGroups().contains(groupName))
            return;
        editLine(sGroupTable, 1, groupName, 1);
        clickBoxLastButton();
        waitReady();
    }
    
    public void AddAPToGroup(String groupName, String apNo) {
        btnAddDevice.click();
        txtAPSerialNo.setValue(apNo);
        clickBoxLastButton();
        waitReady();
        clickBoxLastButton();
        waitReady();
        clickBoxFirstButton();
        waitReady();
        refresh();
    }
    
    /**
     * @param i
     *          0 - summary, 1 - config, 2 - devices
     */
    public void selectSubGroupPage(int i) {
        logger.info("open view: " + i);
        if (i == 0) {
            WebCheck.checkUrl(URLParam.hrefWirelessairBridgeGroupsSummary);
        } else if (i == 1) {
            WebCheck.checkUrl(URLParam.hrefWirelessairBridgeGroupsConfig);
        } else {
            WebCheck.checkUrl(URLParam.hrefWirelessairBridgeGroupsDevices);
        }
    }

    /**
     * @param i
     *          0 - summay, 1 - config, 2 - group, 3 - antenna align, 4 - dns, 5 - speedtest
     */
    public void selectSubDevicePage(int i) {
        logger.info("open view: " + i);
        if (i == 0) {
            WebCheck.checkUrl(URLParam.hrefDeviceAirBridgeSummary);
        } else if (i == 1) {
            WebCheck.checkUrl(URLParam.hrefDeviceAirBridgeConfig);
        } else if (i == 2) {
            WebCheck.checkUrl(URLParam.hrefDeviceAirBridgeGroup);
        } else if (i == 3) {
            WebCheck.checkUrl(URLParam.hrefDeviceAirBridgeAntennaAlign);
        } else if (i == 4) {
            WebCheck.checkUrl(URLParam.hrefDeviceAirBridgednsLookup);
        } else {
            WebCheck.checkUrl(URLParam.hrefDeviceAirBridgespeedTest);
        }
    }
    
    public void openGroup(String groupName) {
        logger.info("open group: " + groupName);
        editLine(sGroupTable, 1, groupName, 0);
    }

    public void editGroupConfig(String groupName) {
        logger.info("edit group: " + groupName);
        openGroup(groupName);
        selectSubGroupPage(1);
        waitElement(txtGroupName);
        
        if (abGroupData.name != null) {
            setText(txtGroupName, abGroupData.name);
        } else {
            setText(txtGroupName, groupName);
        }
        selectMaster(abGroupData.MasterDevice);
        setText(txtGroupSSID, abGroupData.ssid);
        txtPassword.clear();
        txtPassword.sendKeys(abGroupData.key);
        setSelected(cbABMode, abGroupData.abMode, true);
        setSelected(cbRFMode, abGroupData.rfMode, true);
        setChannelWidth(abGroupData.channelWidth);
        setChannelNumber(abGroupData.channelNumber);
        takess("set all new options");
        btnSave.click();
        waitReady();
        clickBoxLastButton();
        gotoPage();
    }
    
    public void editDeviceConfiguration(String lati, String longi) {
        txtConfDeviceLatitude.setValue(lati);
        txtConfDeviceLongitude.setValue(longi);
        btnConfSave.click();
        waitReady();
    }

    /**
     * @param mode
     *             true for "always on", false for "off if idle in 15m"
     */
    public void editDeviceConfiguration(boolean mode) {
        setManagementSSID(mode);
        btnConfSave.click();
        waitReady();
    }
}
