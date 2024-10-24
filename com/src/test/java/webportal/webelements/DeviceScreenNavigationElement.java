package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.openqa.selenium.By;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;

public class DeviceScreenNavigationElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceScreenNavigationElement");
    
    

  public SelenideElement     WirelessClient                   =  $x("//*[@id=\"hSubHeadClientChrt\"]");
  public SelenideElement     WirelessTraffic                  =  $x("//*[@id=\"hUsgTrfChrt\"]");
  public SelenideElement     WirelessConnectedClients         =  $x("//*[@id=\"_hPurpleStrge\"]");
  public SelenideElement     WirelessDevices                  =  $x("//*[@id=\"hDevDevList\"]");
  public SelenideElement     WirelessClientList               =  $x("//*[@id=\"hClientList\"]");
  
  
  public SelenideElement     SummaryProperties                =  $x("//*[@id=\"_hColorPurpProp\"]");
  public SelenideElement     SummaryHealth                    =  $x("//*[@id=\"_hSystmHlthLoc\"]");
  public SelenideElement     SummaryWirelessClients           =   $x("//*[@id=\"_hGhWirTrf\"]");
  public SelenideElement     SummaryPortUtilization           =  $x("//*[@id=\"_hPnlPrtUtil\"]");
  public SelenideElement     SummaryNotification              =  $x("//*[@id=\"_hColorPurNotify\"]");
  public SelenideElement     SummarycaptivePortal             =  $x("//*[@id=\"hSubHeadClientChrt\"]");
  public SelenideElement     SummaryCF                        =  $x("//*[@id=\"_hColorPurpProp\"]");
  
  
  public SelenideElement     RoutersBVN                        =  $("#brSettingsView");
  public SelenideElement     Routers                           =  $x("//*[@id=\"_devices\"]");

  
  public SelenideElement    WiredUsage                         =  $x("//*[@id=\"hUsgeWiredQuickView\"]");
  public SelenideElement    WiredPOEUsage                      =  $x("//*[@id=\"_hSystmHlthLoc\"]");
  public SelenideElement    WiredTraffic                       =  $x("//*[@id=\"_hSystmHlthLoc\"]");
  public SelenideElement    WiredDevices                       =  $x("//*[@id=\"hDeviceswiredQuickView\"]");
  
  public SelenideElement    Storageerror                       =  $x("//*[@id=\"showErrorDiv\"]/div");
  public SelenideElement    StorageUsage                       =  $x("//*[@id=\"usage\"]");
  public SelenideElement    StorageDevices                     =  $x("//*[@id=\"_devices\"]");
 
  public SelenideElement    FirmwareScheduleUpdate              =  $x("//h3[text()='Scheduled Update']");
  
  public SelenideElement    clientConnected                    =  $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[1]/div/div[1]/ul/li[1]");
  public SelenideElement    clientDisconnected                   =  $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[1]/div/div[1]/ul/li[2]");
  public SelenideElement    clientcaptiveportal                 =  $x("//*[@id=\"_hSystmHlthLoc\"]");
  
  public SelenideElement    troubleshootPingTest                =  $x("//*[@id=\"DNSTable\"]/thead/tr/th[2]"); 
  public SelenideElement    troubleshootDNSLookup               =  $x("//*[@id=\"DNSTable\"]/thead/tr/th[3]");
  public SelenideElement    troubleshootOokla                   =  $x("//*[@id=\"DNSTable\"]/thead/tr/th[4]");
  public SelenideElement    troubleshoottracerouteTest          =  $x("//*[@id=\"DNSTable\"]/thead/tr/th[5]");
  
  public SelenideElement    SummaryTab                          =  $x("(//a[text()='Summary'])[2]");
  
  public SelenideElement    TopologyTab                         =  $x("//a[text()='Topology']");
  
  public SelenideElement    DevicesTab                          =  $x("//a[@href='/#/devices/dash']");
  
  public SelenideElement    WirelessTab                         =  $x("//a[@href='#/wireless/wirelessQuickView']");
  
  public SelenideElement    WirelessSettingButton               =  $(".view-button");
  public SelenideElement    WifiandCapitivePortalTab            =  $x("//div[@class=\"leftMenuItems\"]/div[2]/a");
  public SelenideElement    SSIDLabel                           =  $x("//th[@id='ssdiWirSett']");
  public SelenideElement    StatusLabel                         =  $x("//th[@id='ststuWirSett']");
  public SelenideElement    BandLabel                           =  $x("//th[@id='bandWirSett']");
  public SelenideElement    SecurityLabel                       =  $x("//th[@id='secWirSett']");
  public SelenideElement    PasswordLabel                       =  $x("//th[@id='PwdWirSett']");
  public SelenideElement    MacACLLabel                         =  $x("//th[@id='macalWirSett']");
  public SelenideElement    CaptivePortal                       =  $x("//th[@id='capPrtWirSett']");
  public SelenideElement    AddSSIDButton                       =  $(".addIcon.icon-add");
  public SelenideElement    Searchoption                        =  $("#divSearchPurWirSett");
  
  public SelenideElement    WifiSchedulesTab                    = $x("//div[@class=\"leftMenuItems\"][1]/div[3]/a");
  public SelenideElement    WifiSchedulesLabel                  = $x("//span[contains(text(),'WiFi Schedules')]");
  public SelenideElement    WifiScheduleAddButton               = $x("//a[@id='quickView']");
  public SelenideElement    WifiScheduleQuickViewButton         = $(".addIcon");
  
  public SelenideElement    URLfilteringTab                     = $x("//div[@class=\"leftMenuItems\"][1]/div[4]/a");
  public SelenideElement    URLBlackListToggleSwitch            = $x("//div[@id='divOnOfSeturlFiltering']");
  
  public SelenideElement    InstantWifiTab                      = $x("//div[@class=\"leftMenuItems\"][1]/div[5]/a");
  public SelenideElement    InstantWifiQuickkview               = $x("//a[@id='quickView']");
  
  public SelenideElement    FastRoamingTab                      = $x("//div[@class=\"leftMenuItems\"][1]/div[6]/a");
  public SelenideElement    FastRomingEnableToggleSwitch        = $x("(//span[@class='cstmSlider cstmRound'])[4]");
  
  public SelenideElement    AirBridegGroupsTab                  =  $x("//div[@class=\"leftMenuItems\"][1]/div[7]/a");
  public SelenideElement    AirBridgeNameLabel                  = $x("//th[contains(text(),'Name')]");
  public SelenideElement    AirBridgeMasteLabel                 = $x("//th[contains(text(),'Master')]");
  public SelenideElement    AirBridgeSatelliteLabel             = $x("//th[contains(text(),'Satellites')]");
  public SelenideElement    AirBridgeStatusLabel                = $x("//th[contains(text(),'Status')]");
  public SelenideElement    CreateAirBirdgeGroupButton          = $("#divssidWirSett");
  public SelenideElement    AirBridgeQuickView                  = $x("//a[@id='quickView']");
  public SelenideElement    AirBridgeSearchBox                  = $(".searchIcon.icon-search");
  
  public SelenideElement    LoadBalancingTab                    =  $x("//div[@class=\"leftMenuItems\"][1]/div[8]/a");
  public SelenideElement    LoadBalancingToggleLabel            = $x("//h5[contains(text(),'Load Balancing')]");
  public SelenideElement    DisStickyClientsToggleLabel         = $x("//h5[contains(text(),'Disassociate sticky clients')]");
  public SelenideElement    LoadBalancingQuickView              = $x("//a[@id='quickView']");
  public SelenideElement    LoadBalancingSaveButton             = $(".btn.saveBtn");
  public SelenideElement    LoadBalancingCancelButton           = $x("//button[@class=\"btn cancelBtn\"][1]");
  
  public SelenideElement    AdvancedWirelessSettingTab          = $x("//div[@class=\"leftMenuItems\"][1]/div[11]/a");
  public SelenideElement    AWS_Quickview                       = $x("//a[@id='quickView']");
  public SelenideElement    GHLZabel_24                         = $x("//span[contains(text(),'2.4GHz')]");
  public SelenideElement    EnableRadioLabel_24                 = $x("(//*[@class=\"cstmSlider cstmRound\"])[4]");
  public SelenideElement    RadioModeLabel_24                   = $x("(//*[text()=\"Radio Mode\"])[1]");
  public SelenideElement    RadioModeDropdown_24                = $x("(//*[@class=\"form-control formValidate inputTextField\"])[5]");
  public SelenideElement    ChannelWidthLabel_24                = $x("(//*[text()=\"Channel Width\"])[1]");
  public SelenideElement    ChannelWidthDropdown_24             = $x("(//*[@id=\"dropdown-width\"])[1]");
  public SelenideElement    GhzExpandIcon_24                    = $x("//form[@class=\"client-form\"]/div[1]/div[1]/div[1]/div[1]/h3[1]/span[2]/i[2]");
  public SelenideElement    GhzExpandIcon5Low                   = $x("//form[@class=\"client-form\"]/div[2]/div[1]/div[1]/div[1]/h3[1]/span[2]/i[2]");
  public SelenideElement    GhzExpandIcon5High                  = $x("//form[@class=\"client-form\"]/div[3]/div[1]/div[1]/div[1]/h3[1]/span[2]/i[2]");
  public SelenideElement    Ghz5LowLabel                        = $x("//span[contains(text(),'5 GHz / 5 GHz Low')]");
  public SelenideElement    EnableRadioLabel_5Low               = $x("//form[@class=\"client-form\"]/div[2]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[1]/h5[1]");
  public SelenideElement    EnableRadioButton_5Low              = $x("//form[@class=\"client-form\"]/div[2]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[1]/label[1]");
  public SelenideElement    EnableRadioButton_24                = $x("//form[@class=\"client-form\"]/div[1]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[1]/label[1]");
  public SelenideElement    RadioModeLabel_5Low                 = $x("//form[@class=\"client-form\"]/div[2]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[2]/div[1]/h5[1]");
  public SelenideElement    RadioModeDropdown_5Low              = $x("//form[@class=\"client-form\"]/div[2]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[2]/select[1]");
  public SelenideElement    ChannelWidthDropdown_5Low           = $x("//form[@class=\"client-form\"]/div[2]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[3]/select[1]");
  public SelenideElement    ChannelWidthLabel_5Low              = $x("//form[@class=\"client-form\"]/div[2]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[3]/h5[1]");
  public SelenideElement    RadioModeLabel_5High                = $x("//form[@class=\"client-form\"]/div[3]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[2]/div[1]/h5[1]");
  public SelenideElement    RadioModeDropdown_5High             = $x("//form[@class=\"client-form\"]/div[3]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[2]/select[1]");
  public SelenideElement    ChannelWidthDropdown_5High          = $x("//form[@class=\"client-form\"]/div[3]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[3]/select[1]");
  public SelenideElement    ChannelWidthLabel_5High             = $x("//form[@class=\"client-form\"]/div[3]/div[1]/div[@class=\"networksetting NotificationsAcor\"]/div[@class=\"overflow\"]/div[@class=\"nsaccord-detail show\"]/div[1]/div[1]/div[3]/h5[1]");
  public SelenideElement    GhzHighLabel_5High                  = $x("//span[contains(text(),'5GHz High')]");
  public SelenideElement    CancelButton                        = $x("//button[@id='btnCancelUpdateRadiChnl']");
  public SelenideElement    SaveButton                          = $x("//button[@id='btnSaveUpdteRadiChnl']");
  
  public SelenideElement    AdvancedNetworkSettingTab           =  $x("//div[@class=\"leftMenuItems\"][1]/div[11]/a");
  public SelenideElement    ANS_QuichViewButton                 = $x("//a[@id='quickView']");
  public SelenideElement    ANS_BroadCastLabel                  = $x("//h5[contains(text(),'Broadcast-to-Unicast')]");
  public SelenideElement    ANS_IGMPSnooping                    = $x("//h5[contains(text(),'IGMP Snooping')]");
  public SelenideElement    ANS_BroadcastToggleButton           = $x("(//*[@class=\"cstmSlider cstmRound\"])[4]");
  public SelenideElement    ANS_IGMPToggleButton                = $x("(//*[@class=\"cstmSlider cstmRound\"])[5]");
  public SelenideElement    ANS_CancelButton                    = $x("//button[@class=\"btn cancelBtn\"]");
  public SelenideElement    ANS_SaveButton                      = $x("//button[@class=\"btn saveBtn\"]");
  
  public SelenideElement    MeshSettingTab                      =  $x("//div[@class=\"leftMenuItems\"][1]/div[10]/a");
  public SelenideElement    MS_QuicktabButton                   = $x("//a[@id='quickView']");
  public SelenideElement    MeshSettingLabel                    = $x("//p[contains(text(),'Mesh Settings')]");
  public SelenideElement    MeshmodeLabel                       = $x("//h5[contains(text(),'Mesh Mode')]");
  public SelenideElement    MeshModeAutoRadioButton             = $x("//div[@class=\"CustomRadioBlock Inline-Radio\"]/label[1]/input[1]");
  public SelenideElement    MeshModeDisableMeshRadioButton      = $x("//div[@class=\"CustomRadioBlock Inline-Radio\"]/label[2]/input[1]");
  public SelenideElement    MS_Cancel_Button                    = $x("//button[@class=\"btn cancelBtn\"]");
  public SelenideElement    MS_Save_Button                      = $x("//button[@class=\"btn saveBtn\"]");
  
  public SelenideElement    ManagementLanSetting                = $x("//div[@class=\"leftMenuItems\"][1]/div[9]/a[1]");
  public SelenideElement    MgVlanSettingQuickView              = $x("//a[@id='quickView']");
  public SelenideElement    MgVlanSettingLabel                  = $x("//h3[@id='hAdvanceSetteditWirNet']");
  public SelenideElement    MgVlanDropdown                      = $x("//div[@class=\"statidetails statidetailsVisible\"]/div[1]/div[1]/div[1]/select[1]");
  public SelenideElement    AddCustom_VlanButton                = $x("//div[@class=\"statidetails statidetailsVisible\"]/div[1]/div[1]/div[2]/a[1]");
  public SelenideElement    UntaggVlanLabel                     = $x("//h5[@id='HvlaneditWirNet ']");
  public SelenideElement    UntagAddCustom_VlanButton           = $x("//div[@class=\"statidetails statidetailsVisible\"]/div[2]/div[1]/div[2]/a[1]");
  public SelenideElement    UntagVlanDropdown                   = $x("//div[@class=\"statidetails statidetailsVisible\"]/div[2]/div[1]/div[1]/select[1]");
  public SelenideElement    MgvlanCancelButton                  = $x("//button[@class=\"btn cancelBtn\"]");
  public SelenideElement    MgvlanSaveButton                    = $x("//button[@class=\"btn saveBtn\"]");

  public SelenideElement    WiredSettingButton                  = $("#settingsView");
  public SelenideElement    VLANNetworkSetupTab                 = $x("//div[@class=\"leftMenuItems\"]/div[2]/a[1]");
  public SelenideElement    VLANNameLabel                       = $x("//th[@id='tdwiredVlan']");
  public SelenideElement    VLANIDLabel                         = $x("//th[@id='td2wiredVlan']");
  public SelenideElement    NetworkTypeLabel                    = $x("//th[@id='td3wiredVlan']");
  public SelenideElement    SecondaryVLANTypeLabel              = $x("//th[@id='td4wiredVlan']");
  public SelenideElement    quickViewButton                     = $x("//a[@id='quickView']");
  public SelenideElement    AddVLANButton                       = $x("//a[@id='ancOpenwiredVlan']/span[1]");
  
  public SelenideElement    GroupPortSettingsTab                = $x("//div[@class=\"leftMenuItems\"]/div[3]/a[1]"); 
  public SelenideElement    LAGTab                              = $x("//div[@class=\"leftMenuItems\"]/div[4]/a[1]");
  public SelenideElement    AddLAGButton                        = $x("//div[@class=\"col-md-6 col-md-offset-6 locationBarIcons\"]");
  
  public SelenideElement    SpanningTreeTab                     = $x("//div[@class=\"leftMenuItems\"]/div[5]/a[1]");
  public SelenideElement    PoEDowntimeSchedulesTab             = $x("//div[@class=\"leftMenuItems\"]/div[6]/a[1]");
  public SelenideElement    AddPoppupwiredPoeSchButton          = $x("//a[@id='ancAddPoppupwiredPoeSch']/span[1]");
  public SelenideElement    CreatePOEDowntimeScheduleButton     = $x("//div[@class=\"PoeShedulesBlock\"]/div[1]/div[1]/p[3]/button[1]");
  public SelenideElement    RADIUSConfigurationTab              = $x("//div[@class=\"leftMenuItems\"]/div[7]/a[1]");
  
  public SelenideElement    LocationHamburgerButton             = $x("//div[@id=\"locationDivsContainer\"][1]/div[1]/div[2]/div[1]/div[1]/ul[1]/li[1]/a[1]");
  public SelenideElement    EditLocationButton                  = $x("//div[@id=\"locationDivsContainer\"]/div[1]/div[2]/div[1]/div[1]/ul[1]/li[1]/ul[1]/li[1]/a[1]/b[1]");
  
  public SelenideElement    LocationSettingsTab                 = $x("//div[@class=\"leftMenuItems\"][1]/div[1]/a[1]");
  public SelenideElement    LocationNameLabel                   = $x("//h5[@id='_hLocationNameOne']");
  public SelenideElement    LocationNameField                   = $x("//input[@id='editName']");
  public SelenideElement    DeviceAdminPasswordLabel            = $x("//h5[@id='hAdminPasstext']");
  public SelenideElement    DeviceAdminPasswordField            = $x("//input[@id='editAdminPass']");
  public SelenideElement    StreetLabel                         = $x("//h5[@id='hStreetText']");
  public SelenideElement    StreetField                         = $x("//input[@id='editStreet']");
  public SelenideElement    CityLabel                           = $x("//div[@id=\"divOnOffSettingFour\"][1]/h5[1]");
  public SelenideElement    CityField                           = $x("//input[@id='editCity']");
  public SelenideElement    StateLabel                          = $x("//h5[@id='hStateText']");
  public SelenideElement    StateField                          = $x("//input[@id='editState']");
  public SelenideElement    ZipcodeLabel                        = $x("//h5[@id='hZipCodeText']");
  public SelenideElement    ZipCodeField                        = $x("//input[@id='editZipCode']");
  public SelenideElement    CountryLabel                        = $x("//h5[@id='hCountryText']");
  public SelenideElement    CountryDropdown                     = $x("//select[@id='editCountry']");
  public SelenideElement    TimeLabel                           = $x("//h5[@id='hTimeZoneText']");
  public SelenideElement    TimeDropdown                        = $x("//select[@id='editTimeZone']");
  public SelenideElement    WirelessRegionLabel                 = $x("//h5[@id='hRegionText']");
  public SelenideElement    WirelessRegionDropdown              = $x("//select[@id='editWirelessRegion']");
  public SelenideElement    LocationImageLabel                  = $x("//h5[@id='hLocImg']");
  public SelenideElement    LocationChooseButton                = $("#spanChooseBtn");
  public SelenideElement    LocEditCancelButton                 = $x("//a[@id='aCancelBtn']");
  public SelenideElement    LocEditSaveButton                   = $x("//button[@id='buSaveBtn']");
  
  public SelenideElement    RADIUSTab                           = $x("//div[@class=\"leftMenuItems\"][1]/div[2]/a[1]");
  public SelenideElement    AccessAuthenticationLabel           = $x("//h5[@id='_hOnOfSetRaLiBlColMdRowStRad']");
  public SelenideElement    AccessAuthenticationToggleSwitch    = $x("//div[@id=\"_divClMdSixRaLiBlColMdRowStRad\"]/div[1]/label[1]");
  public SelenideElement    PrimaryServerLabel                  = $x("//h3[@id='_hPrimaryServer']");
  public SelenideElement    AddressLabel                        = $x("//h5[@id='_hAddress']");
  public SelenideElement    AddressField                        = $x("//input[@id='primaryAdd']");
  public SelenideElement    PortNumberLabel                     = $x("//h5[@id='_divPortNumber']");
  public SelenideElement    PortNumberField                     = $x("//input[@id='primaryPort']");
  public SelenideElement    SecretKeyLabel                      = $x("//h5[@id='_hSecKey']");
  public SelenideElement    SecretKeyField                      = $x("//input[@id='primarySecret']");
  public SelenideElement    SecondaryServer                     = $x("//h3[contains(text(),'Secondary Server')]");
  public SelenideElement    SecAddressLabel                     = $x("//h5[@id='_hAdressTwo']");
  public SelenideElement    SecAddressField                     = $x("//input[@id='secondaryAdd']");
  public SelenideElement    SecPortNumberLabel                  = $x("//h5[@id='_hPortNumberOne']");
  public SelenideElement    SecPortNumberField                  = $x("//input[@id='secondaryPort']");
  public SelenideElement    SecSecretKeyLabel                   = $x("//h5[@id='_hSecKeyTwo']");
  public SelenideElement    SecSecretKeyField                   = $x("//input[@id='secondarySecret']");
  public SelenideElement    ReauthenticationTimeLabel           = $x("//h5[@id='huploadrateLimit']");
  public SelenideElement    AccountingLabel                     = $x("//h5[@id='_hAccountingOne']");
  public SelenideElement    RadiusCancelButton                  = $x("//a[@id='_aCancelBtnTwo']");
  public SelenideElement    RadiusSaveButton                    = $x("//button[@id='_buSaveBtnTwo']");

  
  public SelenideElement    NetworkSetupTab                     = $x("//div[@class=\"leftMenuItems\"][1]/div[3]/a[1]");
  public SelenideElement    NS_VLANNameLAbel                    = $x("//*[@id=\"trnetworkVlan\"]/th[1]");
  public SelenideElement    NS_VLANId                           = $x("//*[@id=\"trnetworkVlan\"]/th[2]");
  public SelenideElement    NS_Desc                             = $x("//*[@id=\"trnetworkVlan\"]/th[3]");
  public SelenideElement    NS_NetworkType                      = $x("//*[@id=\"trnetworkVlan\"]/th[4]");
  
  public SelenideElement    SyslogConfigurationTab              = $x("//div[@class=\"leftMenuItems\"][1]/div[4]/a[1]");
  public SelenideElement    EnableSyslogLabel                   = $x("//h5[contains(text(),'Enable Syslog')]");
  public SelenideElement    EnableSyslogtoggleswitch            = $x("(//label[@class=\"cmnSwitch\"])[3]");
  public SelenideElement    SLC_CancelButton                    = $x("//button[@class=\"btn ipbtn cancelBtn\"]");
  public SelenideElement    SLC_SaveButton                      = $x("//button[@class=\"btn saveBtn\"]");
  
  public SelenideElement    LEDSettingsTab                      = $x("//div[@class=\"leftMenuItems\"][1]/div[5]/a[1]");
  public SelenideElement    LEDSettingLabel                     = $x("//h5[contains(text(),'LED Settings')]");
  public SelenideElement    LEDSettingDropdown                  = $x("//*[@class=\"Dropdown-placeholder is-selected\"]");
  public SelenideElement    LEDSettingCancelButton              = $x("//button[@class=\"btn cancelBtn firmwareUpdate\"]");
  public SelenideElement    LEDSettingSaveButton                = $x("//button[@class=\"btn saveBtn\"]");
  
  public SelenideElement    RoutingTab                          = $x("//div[@class=\"leftMenuItems\"][1]/div[6]/a[1]");
  public SelenideElement    RoutingVlanLabel                    = $x("//span[contains(text(),'Routing VLANs')]");
  public SelenideElement    VlanNameLabel                       = $x("//th[contains(text(),'VLAN Name')]");
  public SelenideElement    VlanIDLabel                         = $x("//th[contains(text(),'VLAN ID')]");
  public SelenideElement    RoutingNetworkTypeLabel             = $x("//th[contains(text(),'Network Type')]");
  public SelenideElement    WirelessNetworkLabel                = $x("//th[contains(text(),'Wireless Network (SSID)')]");
  public SelenideElement    IPAddressLabel                      = $x("//th[contains(text(),'IP Address')]");
  public SelenideElement    GatewayLabel                        = $x("//span[contains(text(),'Gateway')]");
  
  public SelenideElement    SNMPv12Tab                          = $x("//div[@class=\"leftMenuItems\"][1]/div[7]/a[1]");
  public SelenideElement    PageTitle                           = $x("//div[@class=\"OnOffSetting InlineHeading m-b-20\"]/h6[1]");
  public SelenideElement    EnableSNMPTrap                      = $x("//h5[contains(text(),'Enable SNMP Trap')]");
  public SelenideElement    EnableSNMPTrapToggleSwitch          = $x("//div[@class=\"d-flex align-items-center m-b-20\"][1]/label[1]");
  public SelenideElement    SNMPIPaddressLabel                  = $x("//form[@id=\"snmpForm\"]/div[1]/div[3]/span[1]");
  public SelenideElement    SNMPIPaddressField                  = $x("//form[@id=\"snmpForm\"]/div[1]/div[3]/input[1]");
  public SelenideElement    CommunityStringLabel                = $x("//form[@id=\"snmpForm\"]/div[1]/div[3]/span[1]");
  public SelenideElement    CommunityStringField                = $x("//form[@id=\"snmpForm\"]/div[1]/div[4]/input[1]");
  public SelenideElement    SNMPCancelButton                    = $x("//button[@class=\"btn ipbtn cancelBtn\"][1]");
  public SelenideElement    SNMPClearButton                     = $x("//button[@class=\"btn ipbtn cancelBtn\"][2]");
  public SelenideElement    SNMPSaveButton                      = $x("//button[@class=\"btn saveBtn\"]");
  
  public SelenideElement    ConfigurationBackupRestoreTab       = $x("//div[@class=\"leftMenuItems\"][1]/div[8]/a[1]");
  public SelenideElement    LocationBackupLabel                 = $x("//h3[contains(text(),'Location Backups')]");
  public SelenideElement    LocationBackupButton                = $x("//button[@class=\"loginBtnSection fontSize16\"]");
  public SelenideElement    LocationBackupPlusButton            = $x("//div[@class=\"locationBarIcons\"]/div[1]/span[1]");
  
  public SelenideElement     MobileDevices                       =  $("//*[@id=\"content\"]/div[4]/div[1]/div/div[1]/h2");
  public SelenideElement     MobileMUB                           =  $x("//*[@id=\"content\"]/div[4]/div[2]/div/div[1]/h2");

//  added by Anusha
  public SelenideElement    ChannelUtilizationLabel             = $x("//*[@id=\"spnTitDevSwitchChnlUit\"]");
  public SelenideElement    ClientOSLabel                       = $x("//*[@id=\"spnTitDevApClientOS\"]");
  public SelenideElement    DeviceDetailsLabel                  = $x("//*[@id=\"hDevDevSumm\"]/h3");
  public SelenideElement    Label24Ghz                          = $x("//*[@id=\"selChrtInpTrfChrt\"]");
  public SelenideElement    Label1Week                          = $x("//*[@id=\"selChrt2InpTrfChrt\"]");
  public SelenideElement    Label24Ghz1                         = $x("//*[@id=\"liCstmDevApClientOS\"]");
  public SelenideElement    Name                                = $x("//*[@id=\"devName\"]");
  public SelenideElement    SerialNo                            = $x("//*[text()='Serial Number']/../div[2]");
  public SelenideElement    Model                               = $x("//*[text()='Model']/../div[2]");
  public SelenideElement    MacAddress                          = $x("//*[text()='MAC Address']/../div[2]");
  public SelenideElement    CountryRegion                       = $x("//*[@id=\"divCouSumm\"]");
  public SelenideElement    UpTime                              = $x("//*[@id=\"divUpTSumm\"]");
  public SelenideElement    IPAddress                           = $x("//*[@id=\"divIPAPSumm\"]");
  public SelenideElement    Clients24                           = $x("//*[@id=\"divClintSumm\"]");
  public SelenideElement    Clients5                            = $x("//*[@id=\"divWlanOneClnSumm\"]");
  public SelenideElement    Clients6                            = $x("//*[text()='6 GHz Clients']/../div");
  public SelenideElement    FirmwareVersion                     = $x("//*[@id=\"divFirmSumm\"]");

  public SelenideElement    TopologyPage                        = $x("(//*[text()=\"Topology\"])[2]");
  public SelenideElement    LabelISP                            = $x("//*[text()='ISP']");
  public SelenideElement    Label3dots                          = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[1]/img");
  public SelenideElement    Search                              = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[1]/div[1]/img");
  public SelenideElement    Filter                              = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[2]/div[1]/img");
  public SelenideElement    Legends                             = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[3]/div[1]/img");
  public SelenideElement    Toplogyhelp                         = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[4]/div[1]/img");
  public SelenideElement    Dowmloadimg                         = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[5]/div[1]/img");
  public SelenideElement    Searchinside                        = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[1]/div[2]/div[1]/div[1]/p");
  public SelenideElement    ShowClientsLabel                    = $x("//*[text()='Show Clients']");
  public SelenideElement    CollapsedState                      = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[3]/div[2]/div[2]/div[1]/div/p");
  public SelenideElement    ExpandededState                     = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[3]/div[2]/div[2]/div[2]/div/p");
  public SelenideElement    DeviceStatus                        = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[3]/div[2]/div[2]/div[3]/div/p");
  public SelenideElement    ConnectionType                      = $x("//*[@id=\"topologyContainer\"]/div[2]/div/div[2]/ul/li[3]/div[2]/div[2]/div[4]/p");
  public SelenideElement    LearnMoreButton                     = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div/div[3]/button[1]");
  public SelenideElement    OKgotitButton                       = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div/div[3]/button[2]");
  public SelenideElement    CloseButton                         = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div/div[1]/button/img");
  public SelenideElement    DownloadimgButton                   = $x("//*[@id=\"saveButton\"]");
  
  public SelenideElement    ClientsPage                        = $x("(//*[text()=\"Clients\"])[3]"); 
  public SelenideElement    AllButton                           = $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[2]/div[1]/div[1]/div[1]/div/div[1]/label/p");
  public SelenideElement    WiredButton                         = $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[2]/div/div[1]/div[1]/div/div[2]/label/p");
  public SelenideElement    WirelessButton                      = $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[2]/div[1]/div[1]/div[1]/div/div[3]/label/p");
  
  public SelenideElement    Defaultinput                        = $x("//*[@id=\"divCOnSecLed\"]/div[2]/div[1]/div/div/div/select");
  public SelenideElement    TypeLabel                           = $x("//*[@id=\"notificationTabl\"]/thead/tr/th[1]");
  public SelenideElement    DetailsLabel                        = $x("//*[@id=\"notificationTabl\"]/thead/tr/th[2]");
  public SelenideElement    DateAndTimeLabel                    = $x("//*[@id=\"notificationTabl\"]/thead/tr/th[3]");
  
  public SelenideElement    NotificationBellIcon                = $x("//*[@id=\"notificationDrop\"]/div/img");
  public SelenideElement    NotificationNo                      = $x("//*[@id=\"notificationDrop\"]/div/span");
  public SelenideElement    SeeAllLabel                         = $x("//*[text()='See All']");
  public SelenideElement    MarkAsAllRead                       = $x("//*[text()='Mark All as Read']");
  
  public SelenideElement    PlusIcon                            = $x("//i[@class=\"icon icon-icon-collapse\"]");
  public SelenideElement    QuestionMarkIcon                    = $x("//div[@class=\"smart-form\"]");
  public SelenideElement    Searchicon                          = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div[1]/div/div[3]/a/span");
  public SelenideElement    SerialNoInPurchase                  = $x("//span[@class=\"fontSize14 whiteNoWrap\"]/span[2]");
  public SelenideElement    AutoAllocationLabel                 = $x("//h6[@class=\"fontSemiBold plusIcon display-inline\"]");
  public SelenideElement    AutoAllocationToggle                = $x("(//span[@class=\"cstmSlider cstmRound\"])[4]"); 
  public SelenideElement    OrganizationName                    = $x("//span[@class=\"ManagerClTxt nameOverFlow\"]");
  public SelenideElement    CreditAllocationButton              = $x("//*[@id=\"AccCreditAllocate\"]");
  public SelenideElement    DeallocateButton                    = $x("//*[text()=\"Deallocate\"]");
  public SelenideElement    AddPurchaseConfKeyButton            = $x("//*[text()=\"Add Purchase Confirmation Key\"]");
  public SelenideElement    ActivateFreeTrialButton             = $x("//*[text()=\"Activate Free Trial\"]");
  public SelenideElement    CurrentSubscriptionLabel            = $x("//*[text()=\"Current Subscription\"]");
  public SelenideElement    AddVPNButton                        = $x("//button[text()=\"Add VPN Service Key\"]");
  public SelenideElement    OneIncludedFreeLabel                = $x("//p[text()=\"1 Included Free\"]");
  public SelenideElement    NoOfTotalVPNCredits                 = $x("//p[text()=\"Total VPN Group Credits\"] /../h2");
  public SelenideElement    NoOfTotalAllocatedCredits           = $x("//p[text()=\"Total Allocated Credits\"] /../h2");
  public SelenideElement    NoOfAvailableCreditstoAllocate      = $x("//p[text()=\"Available Credits to Allocate\"] /../h2");
  public SelenideElement    AddICPButton                        = $x("//button[text()=\"Add Instant Captive Portal Key\"]");
  public SelenideElement    NoOfTotalICPCredits                 = $x("//p[text()=\"Total Instant Captive Portal Credits\"] /../h2");
  public SelenideElement    BillingLabel                        = $x("//li[text()=\"Billing\"]");
  public SelenideElement    UsageHistoryLabel                   = $x("//li[text()=\"Billing\"]/../li[2]");
  public SelenideElement    MonthlyUsageBillingLabel            = $x("//h5[text()=\"Monthly Usage Billing\"]");
  public SelenideElement    CancellationPolicyPage              = $x("//u[text()=\"Cancellation Policy\"]");
  public SelenideElement    CancellationPolicyImg               = $x("//u[text()=\"Cancellation Policy\"]/../img");
  public SelenideElement    GlobalSettingsLabel                 = $x("//li[text()=\"Global Settings\"]");
  public SelenideElement    OrganizationSettingsLabel           = $x("//li[text()=\"Organization Settings\"]");
  public SelenideElement    EventNotificationsLabel             = $x("//li[text()=\"Event Notifications\"]");
  public SelenideElement    EnableNotificationsLabel            = $x("// h6[@class=\"LevelTitle font-size-16 m-b-20 padding-bottom-10\"]");
  public SelenideElement    EmailInputField                     = $x("//input[@class=\"form-control inputTextField plr-0 m-b-5\"]");

  public SelenideElement    crosseye                            = $x("(//*[@id=\"iEyeIcon\"])[2]");
  public SelenideElement    password1                           = $x("(//*[@id=\"iEyeIcon\"]//../input)[2]");
  public SelenideElement    Locatedevice                        = $x("//*[@id=\"spnStDetSumm\"]/a[2]/span");
  public SelenideElement    Okpopup                             = $x("(//*[@id=\"btnclModCapPOrt\"])[2]");
  public SelenideElement    Networkconf                         = $x("//*[@id=\"spnStDetSumm\"]/a[1]");
  public SelenideElement    Locatedev                           = $x("//*[@id=\"spnStDetSumm\"]/a[2]");
  public SelenideElement    Connectedlabel                      = $x("//*[@id=\"pFOntRegSumm\"]");
  public SelenideElement    LastRefreshed                       = $x("//*[@id=\"pRefFOntRegSumm\"]");
  
  public SelenideElement    ShareDiagnostics                    = $x("//*[@id=\"aOpenShareDiagApBtn\"]/span");
  public SelenideElement    Paragraph                           = $x("//*[@id=\"divRowApBtn\"]/div[3]/div/div/div[2]/div/div/div/p");
  public SelenideElement    EmailSend                           = $x("//*[@id=\"share_email_id\"]");
  public SelenideElement    CancelButtonInSD                    = $x("//*[@id=\"divRowApBtn\"]/div[3]/div/div/div[3]/button[1]");
  public SelenideElement    SendButtonInSD                      = $x("//*[@id=\"divRowApBtn\"]/div[3]/div/div/div[3]/button[2]");
  public SelenideElement    CrossButtonInSD                     = $x("//*[@id=\"divRowApBtn\"]/div[3]/div/div/div[1]/button/img");
  
  public SelenideElement    TotaDeviceCredits                   = $x("//p[text()=\"Total Device Credits\"]");
  public SelenideElement    InsightBundledDevices               = $x("//p[text()=\"Insight Bundled Devices\"]");
  public SelenideElement    TotalICP                            = $x("//p[text()=\"Total Instant Captive Portal Credits\"]");
  public SelenideElement    TotalVPN                            = $x("//p[text()=\"Total VPN Group Credits\"]");
  
  public SelenideElement    NAPPage                             = $x("//*[text()=\"Neighboring Access Point\"]");
  public SelenideElement    KnownAccessPoints                   = $x("//span[text()=\"Known Access Points\"]");
  public SelenideElement    UnknownAccessPoints                 = $x("//span[text()=\"Unknown Access Points\"]");
  public SelenideElement    plus                                = $x("(//span[text()=\"Known Access Points\"]/../span/i)[2]");
  public SelenideElement    RadioLabel                          = $x("//th[text()=\"Radio\"]");
  public SelenideElement    DeviceLabel                         = $x("//th[text()=\"Device Name\"]");
  public SelenideElement    MACAddressLabel                     = $x("//th[text()=\"MAC Address\"]");
  public SelenideElement    SsidLabel                           = $x("//th[text()=\"SSID\"]");
  public SelenideElement    ChannelLabel                        = $x("//th[text()=\"Channel\"]");
  public SelenideElement    RssiLabel                           = $x("//th[text()=\"RSSI\"]");
  public SelenideElement    TimestampLabel                      = $x("//th[text()=\"Timestamp\"]");
  
  public SelenideElement   TroubleShootPage                     = $x("(//a[text()=\"Troubleshoot\"])[2]");
  public SelenideElement   PingTest                             = $x("//*[text()=\"Ping Test\"]");
  public SelenideElement   DNSLookup                            = $x("//*[text()=\"DNS Lookup\"]");
  public SelenideElement   OoklaSpeedtest                       = $x("//*[text()=\"Ookla Speedtest\"]");
  public SelenideElement   Traceroot                            = $x("//*[text()=\"Traceroute\"]");
  
  public SelenideElement   LedSettings                          = $x("(//*[text()=\"LED Settings\"])[1]");
  public SelenideElement   DefaultOption                        = $x("//*[@id=\"divCOnSecLed\"]/div[2]/div[2]/div/div/div/select");
  public SelenideElement   OffOption                            = $x("//*[@id=\"divCOnSecLed\"]/div[2]/div[2]/div/div/div/select/option[2]");
  public SelenideElement   OffExceptPowerLed                    = $x("//*[@id=\"divCOnSecLed\"]/div[2]/div[2]/div/div/div/select/option[3]");  

  public SelenideElement   IPSettings                           = $x("(//*[text()=\"IP Settings\"])[1]");
  public SelenideElement   AssignIPLabel                        = $x("//*[@id=\"hAssignIpSettng\"]");
  public SelenideElement   Toggle                               = $x("//*[@id=\"divOnOffSettIpSettng\"]/label/span");
  public SelenideElement   IPaddressLabel                       = $x("//*[@id=\"hIpAddrIpSettng\"]");
  public SelenideElement   IPAddr                               = $x("//*[@id=\"ipAddress\"]");
  public SelenideElement   SubnetMaskLabel                      = $x("//*[@id=\"hSubIpSettng\"]");
  public SelenideElement   SubnetMask                           = $x("//*[@id=\"subnetMask\"]");
  public SelenideElement   GatewayAddressLabel                  = $x("//*[@id=\"hGateIpSettng\"]");
  public SelenideElement   GatewayAddress                       = $x("//*[@id=\"gatewayAddress\"]");
  public SelenideElement   PrimaryDNSLabel                      = $x("(//*[@id=\"hDnsIpSettng\"])[1]");
  public SelenideElement   PrimaryDNS                           = $x("//*[@id=\"dnsServer\"]");
  public SelenideElement   SecondaryDNSLabel                    = $x("(//*[@id=\"hDnsIpSettng\"])[1]");
  public SelenideElement   SecondaryDNS                         = $x("//*[@id=\"secondaryDnsServer\"]");
  
  public SelenideElement   RadioandChannel                      = $x("(//*[text()=\"Radio and Channels\"])[1]");
  public SelenideElement   Label24ghz                           = $x("(//*[@id=\"hServRadiChnl\"])[1]");
  public SelenideElement   Label5ghz                            = $x("(//*[@id=\"hServRadiChnl\"])[2]");
  public SelenideElement   EnableRadio                          = $x("(//*[text()=\"Enable Radio\"])[1]");
  public SelenideElement   RadioMode                            = $x("(//*[text()=\"Radio Mode\"])[1]");                   
  public SelenideElement   Channel                              = $x("(//*[text()=\"Channel\"])[1]");
  public SelenideElement   ChannelWidth                         = $x("(//*[text()=\"Channel Width\"])[1]");
  public SelenideElement   OutputPower                          = $x("(//*[text()=\"Output Power\"])[1]");
  public SelenideElement   R11b                                 = $x("(//*[@id=\"divColMdRadiChnl\"]/div[2]/select/option[1])[1]");
  public SelenideElement   R11ng                                = $x("(//*[@id=\"divColMdRadiChnl\"]/div[2]/select)[1]");
  public SelenideElement   R11bg                                = $x("(//*[@id=\"divColMdRadiChnl\"]/div[2]/select/option[2])[1]");
  public SelenideElement   C20M                                 = $x("(//*[@id=\"divOnOdSetRadiChnl\"]/select)[1]");
  public SelenideElement   C40M                                 = $x("(//*[@id=\"divOnOdSetRadiChnl\"]/select/option[2])[1]");
  public SelenideElement   D20_40                               = $x("(//*[@id=\"divOnOdSetRadiChnl\"]/select/option[3])[1]");
  public SelenideElement   Auto                                 = $x("(//*[@id=\"divSettRadiChnl\"]/select)[1]");
  public SelenideElement   Full                                 = $x("(//*[@id=\"divSettRadiChnl\"]/select/option[2])[1]");
  public SelenideElement   Half                                 = $x("(//*[@id=\"divSettRadiChnl\"]/select/option[3])[1]");
  public SelenideElement   Quarter                              = $x("(//*[@id=\"divSettRadiChnl\"]/select/option[5])[1]");
  public SelenideElement   Eighth                               = $x("(//*[@id=\"divSettRadiChnl\"]/select/option[4])[1]");
  public SelenideElement   Minimum                              = $x("(//*[@id=\"divSettRadiChnl\"]/select/option[6])[1]");
  
  public SelenideElement   DiagnosticMode                       = $x("(//*[text()=\"Diagnostic Mode\"])[1]");
  public SelenideElement   DiagnosticModeLabel                  = $x("(//*[text()=\"Diagnostic Mode\"])[4]");
  public SelenideElement   Para1                                = $x("//*[@id=\"pEnbSecDiagMod\"]");
  public SelenideElement   Para2                               = $x("//*[@id=\"pByenbDiagMod\"]");
  public SelenideElement   SecureDiagnosticsModeLabel           = $x("//*[@id=\"divOvrFlwDiagMod\"]/div");
  public SelenideElement   ToggleInDM                           = $x("//*[@id=\"spSliderDiagMod\"]");
  public SelenideElement   OkInDMpopup                          = $x("(//*[text()=\"OK\"])[7]");
  public SelenideElement   SaveInDM                             = $x("//*[@id=\"btnSaveDiagMod\"]");
  public SelenideElement   PortNoLabel                          = $x("//*[@id=\"hPrtNoDiagMod\"]");
  public SelenideElement   PortNo                               = $x("//*[@id=\"pPrtDiagMod\"]");
  
  public SelenideElement   IconFilter                           = $x("//span[@class=\"icon-filter\"]");
  public SelenideElement   HideandShowpara                      = $x("//*[@id=\"liOneApBtn\"]/span[1]");
  public SelenideElement   ChannelUti                           = $x("//*[@id=\"channelUtil\"]");
  public SelenideElement   ClientOS                             = $x("//*[@id=\"clientOS\"]");
  public SelenideElement   SaveInIconFilter                     = $x("//*[@id=\"buUpdateSummaryOneApBtn\"]");
  
  public SelenideElement   StasticsPage                         = $x("(//*[text()=\"Statistics\"])[1]");
  public SelenideElement   SSIDLabelInSP                        = $x("//th[text()=\"SSID\"]");
  public SelenideElement   ClientsLabelInSP                     = $x("//th[text()=\"Clients\"]");
  public SelenideElement   BroadcastandMulticast                = $x("//p[text()=\"Broadcast and Multicast\"]");
  public SelenideElement   Unicast                              = $x("//p[text()=\"Unicast\"]");
  public SelenideElement   BandLabelInSP                        = $x("//th[text()=\"Band\"]");
  public SelenideElement   TxData                               = $x("//span[text()=\"Tx Data\"]");
  public SelenideElement   RxData                               = $x("//span[text()=\"Rx Data\"]");
  public SelenideElement   Clearstats                           = $x("//*[@id=\"divShowStatics\"]/div[3]/button"); 

  public SelenideElement   Settings                             = $x("//*[@class=\"settingMenu AdminDropDown dropdown-toggle\"]");
  public SelenideElement   Fullscreen                           = $x("//*[@class=\"AdminDropDown viewFullSreenIcon verticleAlignTop zIndex3\"]");
  public SelenideElement   UserName                             = $x("//div[@class=\"userNameBlock\"]");
  public SelenideElement   NotificationBell                     = $x("//*[@id=\"notificationDrop\"]/div/img");
  public SelenideElement   Userimage                            = $x("//*[@class=\"userProfilePicBlock\"]");
  public SelenideElement   Customize                            = $x("//*[@id=\"darkThemeSideBarButton\"]/img");
  public SelenideElement   CustomizeLabel                       = $x("//div[@class=\"fontSize18\"]");
  public SelenideElement   NavigationLabel                      = $x("//p[@class=\"m-b-5 fontSize16 fontSemiBold\"]");
  public SelenideElement   CloseInC                             = $x("//*[@class=\"cursor-pointer sideBarCloseIcon\"]");

  public SelenideElement   Notifications                        = $x("(//*[text()=\"Notifications\"])[3]"); 
  public SelenideElement   TypeLabelInN                         = $x("//*[@id=\"notificationTabl\"]/thead/tr/th[1]");     
  public SelenideElement   DetailsInN                           = $x("//*[@id=\"notificationTabl\"]/thead/tr/th[2]");     
  public SelenideElement   DateandTimeInN                       = $x("//*[@id=\"notificationTabl\"]/thead/tr/th[3]");     
  
  public SelenideElement   MeshTopology                         = $x("(//*[text()=\"Mesh Topology\"])[1]"); 
  public SelenideElement   MeshSettingsLabel                    = $x("//*[text()=\"Mesh Settings\"]");     
  public SelenideElement   FAQ                                  = $x("//*[@class=\"ButtonBlockPop small-buttons text-left smart-form\"]");     
  public SelenideElement   QuestionMarkInMT                     = $x("(//*[@class=\"QuestionCol\"])[2]");   
  public SelenideElement   para1                                = $x("//div[@class=\"smart-form OnOffSetting\"]/p[1]"); 
  public SelenideElement   para2                                = $x("//div[@class=\"smart-form OnOffSetting\"]/p[2]");     
  public SelenideElement   para3                                = $x("//div[@class=\"smart-form OnOffSetting\"]/p[3]");     
  public SelenideElement   para4                                = $x("//div[@class=\"smart-form OnOffSetting\"]/p[4]");   
  public SelenideElement   CloseInMT                            = $x("//h4[text()=\"Mesh Topology\"]/../button");  
  public SelenideElement   imageInMT                            = $x("//*[@id=\"divCOnSecLed\"]/div[3]/div[2]/div/img");     
  public SelenideElement   AdvannceSettingsInM                  = $x("//*[@class=\"configurationIcon\"]");     
  public SelenideElement   AutoLabel                            = $x("(//label[@class=\"radio-inline\"])[1]");   
  public SelenideElement   ExtenderOnlyLabel                    = $x("(//label[@class=\"radio-inline\"])[2]");
  public SelenideElement   RootOnlyLabel                        = $x("(//label[@class=\"radio-inline\"])[3]"); 
  public SelenideElement   DisableMeshlabel                     = $x("(//label[@class=\"radio-inline\"])[4]");  
  public SelenideElement   BackInMT                             = $x("//*[@id=\"divCOnSecLed\"]/div[3]/div/div[1]/div/div[3]/div[1]/div/span"); 
  public SelenideElement   ListView                             = $x("//*[@id=\"divCOnSecLed\"]/div[3]/div[1]/div/div[2]/div[2]/span"); 
  
  public SelenideElement    ArrowOnLeft                         = $x("//*[@class=\"verticalToggleMenu toplogyRectangle d-flex align-items-center justify-content-center\"]");
  public SelenideElement    ArrowOnLeft1                         = $x("(//*[@class=\"verticalToggleMenu toplogyRectangle d-flex align-items-center justify-content-center\"])[2]");
  public SelenideElement    RouterTab                           =  $x("(//span[text()=\"Router/VPN\"])[1]"); 
  public SelenideElement    MobileTab                           =  $x("(//span[text()=\"Mobile\"])[1]"); 
  public SelenideElement    WirlessTab                          =  $x("//a[text()=\"Wireless\"]"); 
  public SelenideElement    WiredTab                            =  $x("//a[text()=\"Wired\"]"); 
  public SelenideElement    DeviceTab                           =  $x("(//span[text()=\"Devices\"])[1]"); 
  public SelenideElement    FirmwareTab                         =  $x("//a[text()=\"Firmware\"]"); 
  public SelenideElement    ClientsTab                          =  $x("(//a[text()=\"Clients\"])[2]"); 
  public SelenideElement    Troubleshoot                        =  $x("//a[text()=\"Troubleshoot\"]"); 
  public SelenideElement    SettingsTab                         =  $x("(//a[text()=\"Settings\"])[5]"); 
  
  public SelenideElement    SummartTabHor                       =  $x("//a[contains(@class,\"active\")]"); 
  public SelenideElement    TopologyTabHor                      =  $x("//a[@href='#/topology/view']"); 
  public SelenideElement    RouterTabHor                        =  $x("//a[contains(@class,\"hideWhenHorizontal\")]//*[text()='Router/VPN']"); 
  public SelenideElement    MobileTabHor                        =  $x("//a[contains(@class,\"hideWhenHorizontal\")]//*[text()=\"Mobile\"]"); 
  public SelenideElement    WirlessTabHor                       =  $x("//a[contains(@class,\"hideWhenHorizontal\")]//*[text()=\"Wireless\"]"); 
  public SelenideElement    WiredTabHor                         =  $x("//a[contains(@class,\"hideWhenHorizontal\")]//*[text()=\"Wired\"]"); 
  public SelenideElement    DeviceTabHor                        =  $x("//span[contains(@class,\"d-flex justify-content-between hideWhenHorizontal\")]//*[text()=\"Devices\"]"); 
  public SelenideElement    FirmwareTabHor                      =  $x("//a[@href=\"/#/firmware/available\"]"); 
  public SelenideElement    ClientsTabHor                       =  $x("//a[@href=\"/#/Clients/Clients\"]"); 
  public SelenideElement    TroubleshootHor                     =  $x("//a[@href=\"/#/Troubleshoot/Troubleshoot\"]"); 
  public SelenideElement    SettingsTarHor                      =  $x("//a[@href=\"#/Network/EditNetwork\"]");
  
  public SelenideElement    SummaryInR                          =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"#/routers/quickview\"]");
  public SelenideElement    SettingsInR                         =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"#/routers/settings/VPNGroups\"]");
  public SelenideElement    SummaryInM                          =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"#/mobility/mobilityQuickView\"]");
  public SelenideElement    SettingsInM                         =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"#/mobility/mobilitySettings\"]");
  public SelenideElement    SummaryInWL                         =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"#/wireless/wirelessQuickView\"]");
  public SelenideElement    SettingsInWL                        =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"#/wireless/wirelessSettings\"]");
  public SelenideElement    SummaryInW                          =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"/#/wired/quickView\"]");
  public SelenideElement    SettingsInW                         =  $x("//ul[@class=\"hideWhenHorizontal menuBarSecondLevel\"]//a[@href=\"/#/wired/VLAN\"]");
 
  public SelenideElement    SummaryQuickview                    =  $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[1]/a");
  public SelenideElement    SettingsQuickview                   =  $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[2]/a");
  public SelenideElement    All                                 =  $x("(//a[text()=\"All\"])[2]");
 
  public SelenideElement    AllRouter                           =  $x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]/ul");
  public SelenideElement    AllEditmobile                     =  $x("//*[text()=\"Default\"]");
  public SelenideElement    AllWirelessWifi                     =  $x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]");
  
  //AddedByPratik
  public SelenideElement    clickonOffice1LocationOnHomePage    =  $x("(//img[@src='https://qa-network-icon.s3.amazonaws.com/network_default.png'])[2]");
  public SelenideElement    routerVPNTab                        =  $x("(//span[text()='Router/VPN'])[1]");
  public SelenideElement    firmwarePageTab                     =  $x("(//a[text()='Firmware'])[2]");
  public SelenideElement    advancedWirelessSettingMenuSelect   =  $x("//a[text()='Wireless Settings']");
  

}
