package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class RoutersBusinessVPNElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("RoutersBusinessVPNElement");

    //public SelenideElement btnQuickView          = $x("//a[text()='Quick View']");
    public SelenideElement btnQuickView          = $x("//a[@id='brSettingsView']");
    public SelenideElement cirbtnAdd             = $x("//span[contains(@class,'icon-add')]");
    public SelenideElement cirbtnGraphView       = $x("//span[contains(@class,'icon-pool')]");
    public SelenideElement cirbtnListView        = $x("//span[contains(@class,'icon-page-setting')]");
    public SelenideElement cirbtnDelete          = $x("//span[contains(@class,'icon-delete')]");
    public SelenideElement btnCreateNext         = $x("//div[contains(@style,'display: block')]//button[text()='Next']");
    
    public SelenideElement inputGroupName        = $("input[name*=name]");
    public SelenideElement inputDescription      = $("input[name*=description]");
    public SelenideElement inputMTU              = $("input[name*=mtu]");
    public SelenideElement DomainNamesthroughVPN = $("input[name*=isVpnDomain]");
    public SelenideElement inputDomainName       = $("input[name*=domainNames]");
    public SelenideElement btnBack               = $x("//button[contains(@class,'cancelBtn')]");
    public SelenideElement btnNext               = $x("//button[contains(@class,'saveBtn')]");
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
    public SelenideElement duplicateName         = $x("//*[@id='businessFormError']/div");
    public SelenideElement faqbutton             = $x("//*[@id='divConSecConnNeigh']/div[1]/div[1]/div[2]/a");
    
    public SelenideElement dataTable             = $x("//table[contains(@class,'dataTable')]");
    public SelenideElement btnAddRemoteRouter    = $x("//button[text()='Add New Remote Router']");
    public String          tableTbody            = "table tbody";
    public SelenideElement bvpntable             = $x("//table[@id='vpnBusiness']");
    public SelenideElement selectVPNCredits      = $("select[name*=skuId]");
    public SelenideElement selectVLAN            = $("select[name*=vlanProfile]");
    public String          EmployeeHomeSite_1_5  = "Employee Home Site (1 User / 5 Clients)";
    public String          MicroOffice_9_45      = "Micro Office (9 User / 45 Clients)";
    public String          MicroOffice_15_75     = "Micro Office (15 User / 75 Clients)";
    public String          SmallOffice_25_125    = "Small Office (25 User / 125 Clients)";
    public String          SmallOffice_50_250    = "Small Office (50 User / 250 Clients)";
    public SelenideElement textEmployeeHome      = $x("//div[contains(@style,'display: block')]//p[text()='Employee Home']");
    public SelenideElement textBranchOffice      = $x("//div[contains(@style,'display: block')]//p[text()='Branch Office']");
//    public SelenideElement radioEmployeeHome     = $x("//div[contains(@class,'CustomRadioBlock')]//div[1]//input");
//    public SelenideElement radioBranchOffice     = $x("//div[contains(@class,'CustomRadioBlock')]//div[2]//input");
    public SelenideElement radioEmployeeHome     = $x("//div[contains(@style,'display: block')]//p[text()='Employee Home']/../input");
    public SelenideElement radioBranchOffice     = $x("//div[contains(@style,'display: block')]//p[text()='Branch Office']/../input");
    
    public SelenideElement inputVLANProfile      = $x("//div[contains(@style,'display: block')]//input[@name='vlanEnable']");
    public SelenideElement VLANProfile           = $x("//h5[text()='VLAN Profile']/../label/span");
    public SelenideElement inputRouterIsolation  = $x("//div[contains(@style,'display: block')]//input[@name='routerIsoSt']");
    public SelenideElement RouterIsolation       = $x("//div[contains(@style,'display: block')]//input[@name='routerIsoSt']/../span");
    public SelenideElement inputAggrKeepAlive    = $x("//div[contains(@style,'display: block')]//input[@name='isAggrKeepAlive']");
    public SelenideElement AggrKeepAlive         = $x("//div[contains(@style,'display: block')]//input[@name='isAggrKeepAlive']/../span");
    
    public SelenideElement ModalWarning          = $x("//div[contains(@style,'display: block')]//h4[text()='Warning']");
    public SelenideElement DomainNamesHelpText   = $x("//p[contains(text(), 'This defines')]");
    
    public SelenideElement VPNWirelessNetwork    = $("input[name*=enableOfcVpn]");
    public SelenideElement inputWirelessSSID     = $x("//h5[text()='Wireless SSID']/../input");
    public SelenideElement selectSecurity        = $x("//h5[text()='Security']/../select");
    public SelenideElement inputPassword         = $("input[name*=password]");
    public SelenideElement radiusip         = $x("//*[@id='secondaryAdd']");
    public SelenideElement radiuskey         = $x("//*[@id='secondarySecret']");
    
    
    public SelenideElement inputDeviceName       = $x("//span[text()='Device Name']/../input");
    public SelenideElement inputMACAddress       = $x("//span[text()='MAC Address']/../input");
    public SelenideElement selectRules           = $("select[name*=policy]");
    public SelenideElement selectDevice          = $("select[name*=deviceId]");
    
    public SelenideElement btnViewVPNGroup       = $x("//button[text()='View My Business VPN Group']");
    public SelenideElement successicon           = $x("//img[contains(@src,'success-icon')]");
    
    public SelenideElement graphLink             = $x("((//*[name()='svg'])[@height=500]//*[local-name()='g']/*[local-name()='g'])[1]/*[local-name()='path']");
    public SelenideElement graphCentralCir       = $x("((//*[name()='svg'])[@height=500]//*[local-name()='g']/*[local-name()='g'])[2]/*[local-name()='circle']");
    public SelenideElement graphRemote1Cir       = $x("((//*[name()='svg'])[@height=500]//*[local-name()='g']/*[local-name()='g'])[3]/*[local-name()='circle']");
    
    public SelenideElement graphRemote1Img       = $x("((//*[name()='svg'])[@height=500]//*[local-name()='g']/*[local-name()='g'])[3]/*[local-name()='image']");
    public SelenideElement graphRemote2Img       = $x("((//*[name()='svg'])[@height=500]//*[local-name()='g']/*[local-name()='g'])[4]/*[local-name()='image']");
    
    public SelenideElement getVPNGroupElement(String groupname) {
        return $(By.xpath(String.format("//td[text()='%s']", groupname)));
    }

}
