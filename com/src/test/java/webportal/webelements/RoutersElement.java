package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class RoutersElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("RoutersElement");

    public String          sTableDevices   = "#NASDeviceListTable tbody";
    public SelenideElement btnAddGroup     = $("#routerVPNGraphContainer #_addDeviceMod");
    public SelenideElement btnAddOK        = $(".createVPNGroupWarningModal.in button");
    public SelenideElement txtVPNGroupName = $("#smart-form-license input");

    public SelenideElement btnCreate         = $(".AddVpnBlock button");
    public SelenideElement btnAddIcon        = $("#_addDeviceMod");
    public SelenideElement btnOK             = $(".createVPNGroupWarningModal.in button");
    public SelenideElement txtName           = $("#smart-form-license input");
    public SelenideElement btnSave           = $("#routerVPNGraphContainer .CreateVpnGroup.in button:last-child");
    public String          warningOk         = "//button[@id='vpnGroupWarning']";
    public SelenideElement closecreategroup  = $x("//div[contains(@class,'CreateVpnGroup in')]//button[1]");
    public SelenideElement closewarning      = $x(
            "//button[text()='" + WebportalParam.getLocText("BUY NOW") + "' and @id='vpnGroupWarning']/../../div[1]/button");
    public SelenideElement vpnwarning        = $x("//div[@aria-label='close']/..//p");
    public SelenideElement vpnwarningclose   = $x("//div[@aria-label='close']/img");
    public SelenideElement vpnexpiredwarning = $("#pWarnIconsubscriptions");
    public SelenideElement settings          = $("#brSettingsView");
    
    ////////////// below elements move to RoutersBusinessVPNElement.java  ////////////
    ////////////////////// new elements for SSID-WAN start //////////////////////////
    /*
    public SelenideElement cirbtnAdd             = $x("//span[contains(@class,'icon-add')]");
    public SelenideElement cirbtnGraphView       = $x("//span[contains(@class,'icon-pool')]");
    public SelenideElement cirbtnListView        = $x("//span[contains(@class,'icon-page-setting')]");
    public SelenideElement cirbtnDelete          = $x("//span[contains(@class,'icon-delete')]");
    
    public SelenideElement inputGroupName        = $("input[name*=name]");
    public SelenideElement inputDescription      = $("input[name*=description]");
    public SelenideElement inputMTU              = $("input[name*=mtu]");
    public SelenideElement DomainNamesthroughVPN = $("input[name*=isVpnDomain]");
    public SelenideElement inputDomainName       = $("input[name*=domainNames]");
    public SelenideElement btnBack               = $(".actionBtnRow button:first-child");
    public SelenideElement btnNext               = $(".actionBtnRow button:last-child");
    public SelenideElement btnAddCentralRouter   = $x("//button[text()='Add Central Router']");
    public SelenideElement selectNetwork         = $("select[name*=networkId]");
    public SelenideElement selectRouter          = $("select[name*=deviceId]");
    public SelenideElement inputRouterIp         = $("input[name*=ipAddress]");
    public SelenideElement inputRouterIpSubnet   = $("input[name*=localNetwork]");
    public SelenideElement inputVPNNetworks      = $("input[name*=vpnNetworks]");
    public SelenideElement btnModalCancel        = $x("//div[contains(@style,'display: block')]//button[text()='Cancel']");
    public SelenideElement btnModalSave          = $x("//div[contains(@style,'display: block')]//button[text()='Save']");
    public SelenideElement btnModalDelete        = $x("//div[contains(@style,'display: block')]//button[text()='Delete']");
    public SelenideElement btnModalOK            = $x("//div[contains(@style,'display: block')]//button[text()='OK']");
    public SelenideElement ModalCloseImg         = $x("//div[contains(@style,'display: block')]//button[contains(@class,'close')]");
    
    public SelenideElement dataTable             = $x("//table[contains(@class,'dataTable')]");
    public SelenideElement btnAddRemoteRouter    = $x("//button[text()='Add New Remote Router']");
    public String          tableTbody            = "table tbody";
    public SelenideElement selectVPNCredits      = $("select[name*=skuId]");
    public SelenideElement selectVLAN            = $("select[name*=vlanProfile]");
    public String          EmployeeHomeSite_1_5  = "Employee Home Site (1 User / 5 Clients)";
    public String          MicroOffice_9_45      = "Micro Office (9 User / 45 Clients)";
    public String          MicroOffice_15_75     = "Micro Office (15 User / 75 Clients)";
    public String          SmallOffice_25_125    = "Small Office (25 User / 125 Clients)";
    public String          SmallOffice_50_250    = "Small Office (50 User / 250 Clients)";
    public SelenideElement textEmployeeHome      = $x("//div[contains(@style,'display: block')]//p[text()='Employee Home']");
    public SelenideElement textBranchOffice      = $x("//div[contains(@style,'display: block')]//p[text()='Branch Office']");
    public SelenideElement radioEmployeeHome     = $x("//div[contains(@class,'CustomRadioBlock')]//div[1]//input");
    public SelenideElement radioBranchOffice     = $x("//div[contains(@class,'CustomRadioBlock')]//div[2]//input");
    
    public SelenideElement inputVLANProfile      = $x("//div[contains(@style,'display: block')]//input[@name='vlanEnable']");
    public SelenideElement VLANProfile           = $x("//h5[text()='VLAN Profile']/../label/span");
    public SelenideElement inputRouterIsolation  = $x("//div[contains(@style,'display: block')]//input[@name='routerIsoSt']");
    public SelenideElement RouterIsolation       = $x("//span[text()='Router Isolation']/../label/span");
    public SelenideElement inputAggrKeepAlive    = $x("//div[contains(@style,'display: block')]//input[@name='isAggrKeepAlive']");
    public SelenideElement AggrKeepAlive         = $x("//span[text()='Aggressive keepalive']/../label/span");
    
    public SelenideElement ModalWarning          = $x("//div[contains(@style,'display: block')]//h4[text()='Warning']");
    public SelenideElement DomainNamesHelpText   = $x("//p[contains(text(), 'This defines')]");
    
    public SelenideElement VPNWirelessNetwork    = $("input[name*=enableOfcVpn]");
    public SelenideElement inputWirelessSSID     = $x("//h5[text()='Wireless SSID']/../input");
    public SelenideElement selectSecurity        = $x("//h5[text()='Security']/../select");
    public SelenideElement inputPassword         = $("input[name*=password]");
    
    public SelenideElement inputDeviceName       = $x("//span[text()='Device Name']/../input");
    public SelenideElement inputMACAddress       = $x("//span[text()='MAC Address']/../input");
    public SelenideElement selectRules           = $("select[name*=policy]");
    public SelenideElement selectDevice          = $("select[name*=deviceId]");
    
    public SelenideElement btnViewVPNGroup       = $x("//button[text()='View My Business VPN Group']");
    */
    ////////////////////// new elements for SSID-WAN end //////////////////////////
    
    public String          vpngrouptitle     = "//div[@title='%s']";

    public String txtGroupCircel   = "//div[text()='%s']/..";
    public String txtGroupName     = ".CircleGroupName";
    public String txtDeviceList    = txtGroupCircel + "/../..//li//p";
    public String btnAddDevice     = txtGroupCircel + "/../..//img[contains(@src, 'plus')]";
    public String btnDeleteGroup   = txtGroupCircel + "/../..//img[contains(@src, 'delete')]";
    public String btnDeviceIcon    = txtGroupCircel + "/../..//img[contains(@src, 'router')]";
    public String menuDeviceDetail = txtGroupCircel + "/../..//a[contains(@class, 'colorGray')]";
    public String menuDeviceDelete = txtGroupCircel + "/../..//a[contains(@class, 'colorRed')]";
    public String vpngroupstatus   = txtGroupCircel + "/../..//label[contains(@class, 'backBlue')]";

    public String btnAddDeviceExpired     = txtGroupCircel + "/..//img[contains(@src, 'plus')]";
    public String btnDeleteGroupExpired   = txtGroupCircel + "/..//img[contains(@src, 'delete')]";
    public String btnDeviceIconExpired    = txtGroupCircel + "/..//img[contains(@src, 'router')]";
    public String menuDeviceDetailExpired = txtGroupCircel + "/..//a[contains(@class, 'colorGray')]";
    public String menuDeviceDeleteExpired = txtGroupCircel + "/..//a[contains(@class, 'colorRed')]";

    public String btnAddDeviceNew     = txtGroupCircel + "//img[contains(@src, 'plus')]";
    public String btnDeleteGroupNew   = txtGroupCircel + "//img[contains(@src, 'delete')]";
    public String btnDeviceIconNew    = txtGroupCircel + "//img[contains(@src, 'router')]";
    public String menuDeviceDetailNew = txtGroupCircel + "//a[contains(@class, 'colorGray')]";
    public String menuDeviceDeleteNew = txtGroupCircel + "//a[contains(@class, 'colorRed')]";

    public String          expiredvpnstatus = "//div[contains(@class,'borderColorBroken')]/div[text()='%s']";
    public SelenideElement expiredwarning   = $("#pWarnIconsubscriptions");

    public void selectGroupDeviceMenu(String groupName, int menu) {
        String sElement;
        if (menu == 0) {
            sElement = String.format(menuDeviceDetail, groupName);
        } else {
            sElement = String.format(menuDeviceDelete, groupName);
        }
        for (SelenideElement se : $$x(sElement)) {
            if (se.isDisplayed()) {
                se.click();
                break;
            }
        }
    }
    
    public SelenideElement lastKnownInfo              = $x("//p[text()='Last known information']");
    public SelenideElement lastKnownInfostarsym       = $x("//span[text()='*' and @class='colorRed']");
    public SelenideElement upTimedeviceDashVerify     = $x("//td[@id='tdUpTimedevicesDash0']");
    public SelenideElement upTimedeviceDashVerify1    = $x("//p[contains(text(),'Days')]");
    
    //AddedByPratik
    public SelenideElement vpnSetting                 = $x("//a[text()='VPN Settings']");
    public SelenideElement ipSecVPNTab                = $x("(//a[text()='IPSec VPN' and @href = '#/routers/settings/IPSecVPN'] )[1]");
    public SelenideElement headerIpSecWizard          = $x("//p[text()='IPSec VPN']");
    public SelenideElement connectedTextVerify        = $x("//p[text()='Connected']");
    public SelenideElement greenColorSymbol           = $x("//div[contains(@class,'colorGreen')]");
    public SelenideElement addVPNGroup                = $x("//span[@class='icon-add']");
    public SelenideElement verifYTexTConfigureIPSec   = $x("//h3[text()='Configure IPSec VPN Group']");
    public SelenideElement inputGroupName             = $x("//input[@name='groupName']");
    public SelenideElement inputDescription           = $x("//input[@name='desc']");
    public SelenideElement selectDeviceName           = $x("//select[@name='deviceName']");
    public SelenideElement defaultIPSecProfile        = $x("//select[@disabled and @name ='ipSecProfile']");
    public SelenideElement defaultOptionIPSecProfile  = $x("//select[@name='ipSecProfile']/option[@value='Default_Client_to_Site_IKEv2']");
    public SelenideElement hubRouterText              = $x("//span[text()='Hub Router' or text()='Main Router']");
    public SelenideElement nextButton                 = $x("//button[text()='Next' and @class='btn saveBtn']");
    public SelenideElement inputLANIPAddress          = $x("//input[@name='lanIpAddress']");
    public SelenideElement inputLANSubnet             = $x("//input[@name='lanSubnet']");
    public SelenideElement textLanNetworkSetting      = $x("//h5[text()='LAN Network Setting']");
    public SelenideElement spokeRouterDesc            = $x("//p[contains(text(),'Router(s)')]");
    public SelenideElement headerSpokeRouter          = $x("//h3[text()='Spoke Router' or text()='Branch Router']");
    public SelenideElement buttonAddSpokeRouter       = $x("//button[text()='Add Spoke Router' or text()='Add Branch Router']");
    public SelenideElement noDataAvailable            = $x("//td[text()='No Data Available']");
    public SelenideElement buttonAddNewSpokeRouter    = $x("//button[text()='Add new spoke router' or text()='Add new branch router']");
    public SelenideElement selectNetwork              = $x("//select[@name='networkName']");
    public SelenideElement networkNameListed          = $x("//option[text()='"+WebportalParam.location1+"']");
    public SelenideElement selectRouter               = $x("//select[@name='deviceName']");
    public SelenideElement optionRouterSelectPR       = $x("//option[@name='"+WebportalParam.pr2serialNo+"']");
    public SelenideElement selectRouterText           = $x("//span[text()='Select Router']");
    public SelenideElement a4              = $x("");
    
}
