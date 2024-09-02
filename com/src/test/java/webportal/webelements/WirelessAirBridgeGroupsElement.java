package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class WirelessAirBridgeGroupsElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("WirelessAirBridgeGroupsElement");

    public String sGroupTable = "#tbdyWirSett";
    
    public class ABGroupData {
        public String  name          = "abName";
        public String  ssid          = "abSSID";
        public String  key           = WebportalParam.loginDevicePassword;
        public boolean abMode        = true;
        public boolean rfMode        = true;
        public String  channelWidth  = "40";
        public String  channelNumber = "100";
        public String  MasterDevice  = null;
    }
    
    // summary page
    public SelenideElement btnAirBridgeGroupLink = $("#spnRghtNsaSumm .ManagerClTxt");
    public String          sCpuInfo              = "#liveLineChart";
    public String          sMemoryInfo           = ".height100 .dragIcon+div .progress-bar";
    
    // one airbridge group summary
    public SelenideElement btnAddDevice  = $(".btn-outline");
    public SelenideElement txtAPSerialNo = $("#serialNo");
    
    //
    public SelenideElement btnAdd = $("a[href*=add] span");
    // create group
    public SelenideElement btnWarningCountryRegion = $(".in .btn-danger");
    public SelenideElement txtGroupName            = $("input[name*=grpName]");
    public SelenideElement txtGroupSSID            = $("input[name*=ssid]");
    public SelenideElement txtPassword             = $("#ssidPassword");
    public SelenideElement txtSecurityKey          = $("input[name*=password]");
    public SelenideElement cbABMode                = $("input[name*=airBridgeMode]");
    public SelenideElement cbRFMode                = $("input[name*=greenMode]");
    public SelenideElement lbChannelWidth          = $x("(//div[@class='statidetails']//select)[1]");
    public SelenideElement lbChannelNumber         = $x("(//div[@class='statidetails']//select)[2]");
    public SelenideElement lbChannelNumberEdit     = $("select[name=channel]");
    public SelenideElement btnCancel               = $(".actionBtnRow button[class*=cancel]");
    public SelenideElement btnSave                 = $(".actionBtnRow button[class*=save]");
    public SelenideElement btnNextOrCreateNew      = $(".renameDeviceBtn"); // from dashboard
    
    public boolean getAirBridgeMode() {
        return cbABMode.is(Condition.checked);
    }

    public SelenideElement txtWirelessRegion() {
        return $$("#addAirBridgeGroupForm h5+input").last();
    }

    public void setNewChannelWidth() {
        String n = null;
        String s = lbChannelWidth.getSelectedText();
        for (int i = 0; i < 3; i++) {
            lbChannelWidth.selectOption(i);
            n = lbChannelWidth.getSelectedText();
            if (!n.equals(s)) {
                break;
            }
        }
        logger.info(String.format("update %s to %s", s, n));
    }

    public void setChannelWidth(String item) {
        if (item == null) {
            setNewChannelWidth();
        } else {
            lbChannelWidth.selectOptionContainingText(item);
        }
    }

    public void setNewChannelNumber() {
        String n = null;
        String s = lbChannelNumberEdit.getSelectedText();
        for (int i = 0; i < 3; i++) {
            lbChannelNumberEdit.selectOption(i);
            n = lbChannelNumberEdit.getSelectedText();
            if (!n.equals(s)) {
                break;
            }
        }
        logger.info(String.format("update %s to %s", s, n));
    }
    
    public void setChannelNumber(String item) {
        if (item == null) {
            setNewChannelNumber();
        } else {
            lbChannelNumberEdit.selectOption(item);
        }
    }

    // group config
    String sMasterDevice     = "input[name*=selectMaster]+i+p";
    String sMasterDeviceName = ".radio-inline p";
    
    public void selectMaster(String devName) {
        if (devName == null)
            return;
        int devIndex = 0;
        for (SelenideElement se : $$(sMasterDeviceName)) {
            if (getText(se).equals(devName)) {
                $$(sMasterDevice).get(devIndex).click();
            }
            devIndex++;
        }
    }
    
    // add ap to group
    public SelenideElement txtDeviceserialNo           = $("#serialNo");
    public SelenideElement btnDeviceSelectGroup        = $(".HomeBanner .dropdown-toggle");
    public SelenideElement txtDevicedeviceName         = $("#deviceName");
    public SelenideElement btnDeviceNext               = $(".in .cancelBtn");
    public SelenideElement btnDeviceViewAirBridgeGroup = $("#btnCanCapPOrt");

    public void selectGroup(String groupName) {
        btnDeviceSelectGroup.click();
        boolean bSelected = false;
        for (SelenideElement se : $$("li h5")) {
            if (getText(se).equals(groupName)) {
                bSelected = true;
                se.click();
                break;
            }
        }
        assertTrue(bSelected, "Airbridge group does not exitsed");
    }

    // airbridge group members
    public String          sSwitchView         = "div.addIcon>span";
    public SelenideElement txtLineGreat        = $(".p2pLegendsLine.backGreen");
    public SelenideElement txtLineGood         = $(".p2pLegendsLine.backBlue");
    public SelenideElement txtLineBad          = $(".p2pLegendsLine.backRed");
    public SelenideElement txtLineConnected    = $(".table-responsive .backGreen");
    public SelenideElement txtLineDisconnected = $(".table-responsive .backRed");

    // device for in pool view (last)
    public SelenideElement txtMasterNameOne      = $(".table-responsive .text-center p:first-child");
    public SelenideElement txtMasterStatusOne    = $(".table-responsive .text-center p:last-child");
    public SelenideElement txtMasterNameMore     = $(".table-responsive  .p2pDeviceStatusBottom .fontSemiBold");
    public SelenideElement txtMasterStatusMore   = $(".table-responsive  .p2pDeviceStatusBottom .fontSemiBold+p");
    public SelenideElement txtSatelliteName      = $(".table-responsive h6");
    public SelenideElement txtSatelliteStatus    = $(".table-responsive h6+p");
    public SelenideElement txtSatelliteNetStatus = $(".table-responsive .p2pIconBox span");

    // device for in list view (first)
    public String sDeviceStatus      = "tbody img";
    public String sDeviceStatusTwoAP = ".p2pIconBox span";
    public String sDeviceName        = "tbody tr td:first-child p:first-child";
    public String sDeviceLocation    = "tbody tr td .OrbiClientElipsis";
    public String sDeviceUpTime      = "#divRowNsaSumm tbody tr td:last-child";

    public int getAPCount() {
        return $$(sDeviceName).size();
    }

    /**
     * @param view
     *             0 - list, 1 - map, 2 - topo
     */
    public void selectSubViewInABGDevice(int view) {
        click($$(sSwitchView).get(view));
    }
    
    public boolean isDeviceConnected(String devName) {
        if ($(sDeviceStatusTwoAP).exists()) {
            if (!$(sDeviceStatusTwoAP).getAttribute("class").contains("backRed"))
                return true;
        } else {
            int index = 0;
            for (SelenideElement se : $$(sDeviceName)) {
                if (getText(se).equals(devName)) {
                    if (!$$(sDeviceStatus).get(index).getAttribute("src").contains("poor"))
                        return true;
                }
                index++;
            }
        }
        return false;
    }

    // configuration for a device
    public SelenideElement txtConfDeviceName      = $("input[name=deviceInfo_deviceName]");
    public SelenideElement txtConfDeviceLatitude  = $("input[name=locationSettings_latitude]");
    public SelenideElement txtConfDeviceLongitude = $("input[name=locationSettings_longitude]");
    public SelenideElement btnConfSave            = $("#btnSavCapPort");

    SelenideElement cbConfSTPStatus                = $("input[name=spanningTree_enabled]");
    SelenideElement cbAssignIPAddressAutomatically = $("input[name=deviceLANIPSettings_dhcpEnabled]");
    SelenideElement cbManagementSSID               = $("input[name=mgmtSsidInfo_enabled]");
    String          sConfRadioStatus               = "input[type=radio]";
    String          sConfRadioOption               = sConfRadioStatus + "+i+p";

    public void setSpanningTreeProtocol(boolean opt) {
        setSelected(cbConfSTPStatus, opt, true);
        btnConfSave.click();
        waitReady();
    }

    public boolean getSpanningTreeProtocol() {
        return cbConfSTPStatus.is(Condition.checked);
    }

    public boolean getManagementSSIDStatus() {
        return cbManagementSSID.is(Condition.checked);
    }

    public void setManagementSSIDStatus(boolean opt) {
        setSelected(cbManagementSSID, opt, true);
    }

    /**
     * @param mode
     */
    public void setManagementSSID(boolean mode) {
        SelenideElement seAlways = $$(sConfRadioStatus).get(0);
        SelenideElement seIdle = $$(sConfRadioStatus).get(1);
        if (mode && !seAlways.isSelected()) {
            logger.info("set to always on");
            $$(sConfRadioOption).get(0).click();
        } else if (!mode && !seIdle.isSelected()) {
            logger.info("set to idle then off");
            $$(sConfRadioOption).get(1).click();
        } else {
            logger.info("no change");
        }
    }
    
    /**
     * @return
     */
    public boolean getManagementSSIDMode() {
        if ($$(sConfRadioStatus).get(0).isSelected())
            return true;
        return false;
    }
    
    // dns & speedtest
    public SelenideElement txtDNSDomain    = $("#dnsLookUpTest input");
    public SelenideElement btnDNSTestRun   = $("button.saveBtn");
    public SelenideElement btnSpeedTestRun = $("#divRowNsaSumm button");

    public String sDNSSummaryResultTable = ".TableHeadBlock .row h6";
    public String sDNSSpeedResultTable   = ".dataTable tbody td";

    /**
     * @param mode
     *             0 - dns, 1 - speed
     */
    public void RunDNSLookupSpeedTest(int mode) {
        if (mode == 0) {
            txtDNSDomain.setValue("cn.bing.com");
            btnDNSTestRun.click();
            sleepsync();
            waitReady();
            waitElement(sDNSSummaryResultTable);
        } else {
            btnSpeedTestRun.click();
            sleepsync();
            waitReady();
            waitElement(btnSpeedTestRun);
        }
    }
    
    public SelenideElement txtDNSResultHost() {
        return $$(sDNSSummaryResultTable).first();
    }
    
    public SelenideElement txtDNSResultTime() {
        return $$(sDNSSummaryResultTable).last();
    }
    
    public List<String> getDNSResultIp() {
        return getTexts(sDNSSpeedResultTable);
    }

    /**
     * @param  val
     *             0 - upload, 1 - download, 2 - delay
     * @return
     */
    public String getSpeedResult(int val) {
        return getText($$(sDNSSpeedResultTable).get(val));
    }
}
