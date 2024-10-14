/**
 *
 */
package webportal.param;

import static org.testng.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import util.MyCommonAPIs;
import util.RunCommand;
import util.SwitchCLIUtils;
import util.WriteExcelUtil;
import util.XMLManagerForTest;

/**
 * @author zheli
 */
public class WebportalParam {
    // some global configuration options
    public static boolean          skipIssueCheck   = false;
    public static boolean          enableDebug      = false;
    public static boolean          enableBatch      = false;
    public static boolean          enableHeadless   = false;
    public static boolean          enaReuseBrowser  = false;
    public static boolean          enaTwoSwitchMode = true;
    final static Logger            logger           = Logger.getLogger("WebportalParam");
    public static SimpleDateFormat sdfFomat         = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static WebDriver        curWebDriver     = null;
    public static boolean          browserIsFailed  = false;
    public static boolean          enableRemote     = false;
    public static boolean          enableProxy      = false;

    public static XMLManagerForTest                    xmlManager;
    public static String                               BrowserType;
    public static String                               BrowserPath;
    public static String                               ThemeStyle;
    public static String                               DUTType;
    public static String                               serverUrl;
    public static String                               serverUrlLogin;
    public static String                               sw1serialNo;
    public static String                               sw1deveiceName;
    public static String                               Organizations;
    public static String                               location1;
    public static String                               location2;
    public static String                               location3;
    public static String                               sw1IPaddress;
    public static String                               sw1ManagePort;
    public static String                               sw1LagPort1;
    public static String                               sw1LagPort2;
    public static String                               sw1Port1;
    public static String                               sw1Port6;
    public static String                               sw1LagPort1CLI;
    public static String                               sw1LagPort2CLI;
    public static String                               sw2serialNo;
    public static String                               sw2deveiceName;
    public static String                               sw2IPaddress;
    public static String                               sw2ManagePort;
    public static String                               sw2LagPort1;
    public static String                               sw2LagPort2;
    public static String                               sw2Port6;
    public static String                               sw2LagPort1CLI;
    public static String                               sw2LagPort2CLI;
    public static String                               defaultUser;
    public static String                               loginName;
    public static String                               loginPassword;
    public static String                               loginName1;
    public static String                               loginPassword1;
    public static String                               CountryOTP;
    public static String                               adminName;
    public static String                               adminPassword;
    public static String                               SecondadminName;
    public static String                               SecondadminPassword;
    public static String                               managerName;
    public static String                               managerPassword;
    public static String                               ownerName;
    public static String                               ownerPassword;
    public static String                               readManagerName;
    public static String                               readManagerPassword;
    public static String                               voucherManagerName;
    public static String                               voucherManagerPassword;
    public static String                               adminSupportUser;
    public static String                               l2SupportUser;
    public static String                               l3SupportUser;
    public static String                               loginDevicePassword;
    public static String                               sw1Model;
    public static String                               sw2Model;
    public static String                               sw1MacAddress;
    public static String                               sw2MacAddress;
    public static String                               sw1Firmware;
    public static String                               BrowserLanguage;
    public static HashMap<String, Map<String, Object>> languageDict;
    public static String                               TftpSvr;
    public static String                               ProxySvr;
    public static String                               SeleniumSvr;
    public static String                               macVPNClientIp;
    public static String                               winVPNClientUser;
    public static String                               longrunVpnGroupName = "VPN1";
    public static int                                  locationNumber      = 2;
    public static String                               mailDomain          = "@xmail.mynetgear.com";
    public static String                               OrbiDefaultSSID;
    public static String                               OrbiDefaultPassword;
    public static String                               Country;
    public static String                               ap1TargetFirmware;

    // add by bingke.xue for nas.
    public static String nas1SerialNo;
    public static String nas1DeviceName;
    public static String nas1UrlAddress;
    public static String nas1Username;
    public static String nas1Password;

    public static String br1IPaddress;
    public static String br1serialNo;
    public static String br1model;
    public static String br1deveiceName;
    public static String br1deveiceMac;
    public static String br2deveiceMac;
    public static String br1Firmware;
    public static String br2IPaddress;
    public static String br2serialNo;
    public static String br2deveiceName;
    public static String br3IPaddress;
    public static String br3serialNo;
    public static String br3deveiceName;

    public static String ob1IPaddress;
    public static String ob1IPsubnet;
    public static String ob1serialNo;
    public static String ob1Model;
    public static String ob1deveiceName;
    public static String ob1deveiceMac;
    public static String ob1Firmware;
    public static String ob1VPNNetworks;
    public static String ob1SSIDWANbssid;
    public static String ob1CPCNIC;

    public static String clientip;
    public static String clientport;
    public static String clientwlanmac;

    public static String client1name;
    public static String client1ip;
    public static String client1port;
    public static String client1mac;
    public static String client1wifi;
    public static String client1wifimac;
    public static String client2name;
    public static String client2ip;
    public static String client2port;
    public static String client2wifi;
    public static String client2eth;
    public static String client2wifimac;

    public static String ap1IPaddress;
    public static String ap1serialNo;
    public static String ap1deveiceName;
    public static String ap1macaddress;
    public static String ap2macaddress;
    public static String ap3macaddress;
    public static String ap1Firmware;
    public static String ap1Model;
    public static String ap5Model;
    public static String ap6Model;
    public static String ap7Model;
    public static String ap8Model;
    public static String ap5deveiceName;
    public static String ap6deveiceName;
    public static String ap7deveiceName;
    public static String ap8deveiceName;
    public static String ap9deveiceName;
    public static String ap10deveiceName;
    public static String ap5IPaddress;
    public static String ap6IPaddress;
    public static String ap7IPaddress;
    public static String ap8IPaddress;
    public static String ap4serialNo;
    public static String ap5serialNo;
    public static String ap6serialNo;
    public static String ap7serialNo;
    public static String ap8serialNo;
    public static String ap9serialNo;
    public static String ap10serialNo;
    public static String ap4macaddress;
    public static String ap5macaddress;
    public static String ap6macaddress;
    public static String ap7macaddress;
    public static String ap8macaddress;
    public static String ap9macaddress;
    public static String ap10macaddress;

    public static String ap2IPaddress;
    public static String ap2serialNo;
    public static String ap2deveiceName;
    public static String ap2Firmware;
    public static String ap2Model;

    public static String ap3IPaddress;
    public static String ap4IPaddress;
    public static String ap3serialNo;
    public static String ap3deveiceName;
    public static String ap3Firmware;
    public static String ap3Model;

    public static boolean isRltkSW1;
    public static boolean isRltkSW2;

    // Add by sujuan.li at2018/9/10
    public static String brlanclientip;
    public static String brlanclient2ip;
    public static String brlanclientip2;
    public static String brwanclientip;
    public static String brlangateway;
    public static String brwangateway;
    public static String brlanconnectip;
    public static String brwanconnectip;
    public static String brlanconnectip2;
    public static String brcleintport1;
    public static String brcleintport2;
    public static String brdutwanip;
    public static String brpptpl2tpserver;
    public static String brpptpl2tpserversub;

    public static String bripsecoppositewanip;
    public static String bripsecoppositelangateway;
    public static String bripsecoppositelanclient;

    // Add by bingke.xue at 2019/5/28
    public static String ob2IPaddress;
    public static String ob2IPsubnet;
    public static String ob2serialNo;
    public static String ob2Model;
    public static String ob2deveiceName;
    public static String ob2SSIDWANbssid;
    public static String ob2sate1Model;
    public static String ob2sate1SSIDWANbssid;
    public static String ob2sate2Model;
    public static String ob2sate2SSIDWANbssid;
    public static String ob2CPCNIC;
    public static String ob2CPCNICSatellite;

    public static String ob2FirmwareVersion;
    public static String ob2Name;
    public static String ob2MAC_Address;
    public static String ob2GUI_URL;
    public static String ob2UserName;
    public static String ob2WifiNetworkName1;
    public static String ob2WifiNetworkPwd1;
    public static String ob2MAC_Address2;
    public static String promocode1;
    public static String promocode2;
    public static String promocode3;

    public static String ob3IPaddress;
    public static String ob3IPsubnet;
    public static String ob3serialNo;
    public static String ob3MAC_Address;
    public static String ob3Model;
    public static String ob3deveiceName;
    public static String ob4IPaddress;
    public static String ob4IPsubnet;   
    public static String ob4serialNo;
    public static String ob4MAC_Address;
    public static String ob4Model;
    public static String ob4deveiceName;

    public static String arch;

    // for firmware upgrade for orbi
    public static String serverimgver;
    public static String basebeforeimg;
    public static String baseafterimg;
    public static String satellitebeforeimg;
    public static String satelliteafterimg;
    
    public static String pr1IPaddress;
    public static String pr1serialNo;
    public static String pr1deveiceName;
    public static String pr1macaddress;
    public static String pr1Firmware;
    public static String pr1Model;
    
    public static String pr2IPaddress;
    public static String pr2serialNo;
    public static String pr2deveiceName;
    public static String pr2macaddress;
    public static String pr2Firmware;
    public static String pr2Model;
    
    public static String baseURI;
    public static String token;
    public static String apikey;
    public static String xsrfToken;
    public static String accountId;
    public static String networkId;
    public static String orgId;


    public WebportalParam() {
        logger.info("init start");
        XMLManagerForTest xmlManager = new XMLManagerForTest();
        WriteExcelUtil excelUnit = new WriteExcelUtil();
        defaultUser = xmlManager.getValueFromWebPortalXml("//defaultUser");
        loginName = xmlManager.getValueFromWebPortalXml("//loginName");
        loginPassword = xmlManager.getValueFromWebPortalXml("//loginPassword");
        loginName1 = xmlManager.getValueFromWebPortalXml("//loginName1");
        loginPassword1 = xmlManager.getValueFromWebPortalXml("//loginPassword1");
        adminName = xmlManager.getValueFromWebPortalXml("//adminName");
        CountryOTP = xmlManager.getValueFromWebPortalXml("//CountryOTP");
        adminPassword = xmlManager.getValueFromWebPortalXml("//adminPassword");
        SecondadminName = xmlManager.getValueFromWebPortalXml("//SecondAdmin");
        SecondadminPassword = xmlManager.getValueFromWebPortalXml("//SecondAdminPassword");        
        managerName = xmlManager.getValueFromWebPortalXml("//managerName");
        managerPassword = xmlManager.getValueFromWebPortalXml("//managerPassword");
        ownerName = xmlManager.getValueFromWebPortalXml("//ownerName");
        ownerPassword = xmlManager.getValueFromWebPortalXml("//ownerPassword");
        readManagerName = xmlManager.getValueFromWebPortalXml("//readManagerName");
        readManagerPassword = xmlManager.getValueFromWebPortalXml("//readManagerPassword");
        voucherManagerName = xmlManager.getValueFromWebPortalXml("//voucherManagerName");
        voucherManagerPassword = xmlManager.getValueFromWebPortalXml("//voucherManagerPassword");
        adminSupportUser = xmlManager.getValueFromWebPortalXml("//adminSupportUser");
        l2SupportUser = xmlManager.getValueFromWebPortalXml("//l2SupportUser");
        l3SupportUser = xmlManager.getValueFromWebPortalXml("//l3SupportUser");
        loginDevicePassword = xmlManager.getValueFromWebPortalXml("//locationPassword");
        BrowserType = xmlManager.getValueFromWebPortalXml("//BrowserType");
        BrowserPath = xmlManager.getValueFromWebPortalXml("//BrowserPath");
        ThemeStyle = xmlManager.getValueFromWebPortalXml("//ThemeStyle");
        DUTType = xmlManager.getValueFromWebPortalXml("//DUTType");
        BrowserLanguage = xmlManager.getValueFromWebPortalXml("//BrowserLanguage");
        serverUrl = xmlManager.getValueFromWebPortalXml("//serverUrl");
        serverUrlLogin = serverUrl;
        Organizations = xmlManager.getValueFromWebPortalXml("//Organizations");
        location1 = xmlManager.getValueFromWebPortalXml("//location1");
        location2 = xmlManager.getValueFromWebPortalXml("//location2");
        location3 = xmlManager.getValueFromWebPortalXml("//location3");
        TftpSvr = xmlManager.getValueFromWebPortalXml("//TftpSvr");
        sw1serialNo = xmlManager.getValueFromWebPortAndDut("SW1", "SerialNo");
        sw1Model = xmlManager.getValueFromWebPortAndDut("SW1", "Model");
        sw1MacAddress = xmlManager.getValueFromWebPortAndDut("SW1", "Mac_Address");
        sw1Firmware = xmlManager.getValueFromWebPortAndDut("SW1", "Firmware");
        sw1deveiceName = xmlManager.getValueFromWebPortAndDut("SW1", "DeviceName");
        sw1IPaddress = xmlManager.getValueFromWebPortAndDut("SW1", "Ip_Address");
        sw1ManagePort = xmlManager.getValueFromWebPortAndDut("SW1", "Mangeport").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g",
                "").replace("1/0/", "").replace("0/", "");
        sw1LagPort1CLI = xmlManager.getValueFromWebPortAndDut("SW1", "LagPort1");
        sw1LagPort2CLI = xmlManager.getValueFromWebPortAndDut("SW1", "LagPort2");
        sw1LagPort1 = xmlManager.getValueFromWebPortAndDut("SW1", "LagPort1").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g",
                "").replace("1/0/", "").replace("0/", "");
        sw1LagPort2 = xmlManager.getValueFromWebPortAndDut("SW1", "LagPort2").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g",
                "").replace("1/0/", "").replace("0/", "");
        sw1Port1 = xmlManager.getValueFromWebPortAndDut("SW1", "Port1").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g", "").replace("1/0/", "").replace("0/", "");
        sw1Port6 = xmlManager.getValueFromWebPortAndDut("SW1", "Port6").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g", "").replace("1/0/", "").replace("0/", "");
        sw2deveiceName = xmlManager.getValueFromWebPortAndDut("SW2", "DeviceName");
        sw2serialNo = xmlManager.getValueFromWebPortAndDut("SW2", "SerialNo");
        sw2Model = xmlManager.getValueFromWebPortAndDut("SW2", "Model");
        sw2MacAddress = xmlManager.getValueFromWebPortAndDut("SW2", "Mac_Address");
        sw2deveiceName = xmlManager.getValueFromWebPortAndDut("SW2", "DeviceName");
        sw2IPaddress = xmlManager.getValueFromWebPortAndDut("SW2", "Ip_Address");
        sw2ManagePort = xmlManager.getValueFromWebPortAndDut("SW2", "Mangeport").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g",
                "").replace("1/0/", "").replace("0/", "");
        sw2LagPort1CLI = xmlManager.getValueFromWebPortAndDut("SW2", "LagPort1");
        sw2LagPort2CLI = xmlManager.getValueFromWebPortAndDut("SW2", "LagPort2");
        sw2LagPort1 = xmlManager.getValueFromWebPortAndDut("SW2", "LagPort1").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g",
                "").replace("1/0/", "").replace("0/", "");
        sw2LagPort2 = xmlManager.getValueFromWebPortAndDut("SW2", "LagPort2").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g",
                "").replace("1/0/", "").replace("0/", "");
        sw2Port6 = xmlManager.getValueFromWebPortAndDut("SW2", "Port6").replace("xmg", "").replace("mg", "").replace("xg", "").replace("g", "").replace("1/0/", "").replace("0/", "");
        macVPNClientIp = xmlManager.getValueFromWebPortalXml("//VPNClientIp");
        winVPNClientUser = xmlManager.getValueFromWebPortalXml("//VPNClientUser");

        // add by bingke.xue for nas.
        nas1SerialNo = xmlManager.getValueFromWebPortAndDut("NAS1", "SerialNo");
        nas1DeviceName = xmlManager.getValueFromWebPortAndDut("NAS1", "DeviceName");
        nas1UrlAddress = xmlManager.getValueFromWebPortAndDut("NAS1", "UrlAddress");
        nas1Username = xmlManager.getValueFromWebPortAndDut("NAS1", "Username");
        nas1Password = xmlManager.getValueFromWebPortAndDut("NAS1", "Password");

        OrbiDefaultSSID = xmlManager.getValueFromWebPortalXml("//OrbiDefaultSSID");
        OrbiDefaultPassword = xmlManager.getValueFromWebPortalXml("//OrbiDefaultPassword");

        // webportal is not accessible in lab segment, lets use config file
        ProxySvr = xmlManager.getValueFromWebPortalXml("//ProxySvr");
        SeleniumSvr = xmlManager.getValueFromWebPortalXml("//SeleniumSvr");
        if (!xmlManager.getValueFromWebPortalXml("//SeleniumSvrEnable").equals("0")) {
            enableRemote = true;
        }

        if (!xmlManager.getValueFromWebPortalXml("//ProxySvrEnable").equals("0")) {
            enableProxy = true;
        }

        if (xmlManager.getValueFromWebPortalXml("//TwoSwitchMode").equals("0")) {
            enaTwoSwitchMode = false;
        }

        // for br device
        br1IPaddress = xmlManager.getValueFromWebPortAndDut("BR1", "Ip_Address");
        br2IPaddress = xmlManager.getValueFromWebPortAndDut("BR2", "Ip_Address");
        br3IPaddress = xmlManager.getValueFromWebPortAndDut("BR3", "Ip_Address");
        br1serialNo = xmlManager.getValueFromWebPortAndDut("BR1", "SerialNo");
        br2serialNo = xmlManager.getValueFromWebPortAndDut("BR2", "SerialNo");
        br3serialNo = xmlManager.getValueFromWebPortAndDut("BR3", "SerialNo");
        br1model = xmlManager.getValueFromWebPortAndDut("BR1", "Model");
        br1deveiceName = xmlManager.getValueFromWebPortAndDut("BR1", "DeviceName");
        br1deveiceMac = xmlManager.getValueFromWebPortAndDut("BR1", "Mac_Address");
        br2deveiceMac = xmlManager.getValueFromWebPortAndDut("BR2", "Mac_Address");
        br1Firmware = xmlManager.getValueFromWebPortAndDut("BR1", "Firmware");
        br2deveiceName = xmlManager.getValueFromWebPortAndDut("BR2", "DeviceName");
        br3deveiceName = xmlManager.getValueFromWebPortAndDut("BR3", "DeviceName");

        // for orbi device
        ob1IPaddress = xmlManager.getValueFromWebPortAndDut("ORBI1", "Ip_Address");
        ob1IPsubnet = xmlManager.getValueFromWebPortAndDut("ORBI1", "Ip_Subnet");
        ob1serialNo = xmlManager.getValueFromWebPortAndDut("ORBI1", "SerialNo");
        ob1Model = xmlManager.getValueFromWebPortAndDut("ORBI1", "Model");
        ob1deveiceMac = xmlManager.getValueFromWebPortAndDut("ORBI1", "Mac_Address");
        ob1deveiceName = xmlManager.getValueFromWebPortAndDut("ORBI1", "DeviceName");
        ob1Firmware = xmlManager.getValueFromWebPortAndDut("ORBI1", "Firmware");
        ob1VPNNetworks = xmlManager.getValueFromWebPortAndDut("ORBI1", "VPNNetworks");
        ob1SSIDWANbssid = xmlManager.getValueFromWebPortAndDut("ORBI1", "SSIDWAN_BSSID");
        ob1CPCNIC = xmlManager.getValueFromWebPortAndDut("ORBI1", "CPC_NIC_to_ORBI_LAN");

        // add by bingke.xue at 2019/5/28
        ob2IPaddress = xmlManager.getValueFromWebPortAndDut("ORBI2", "Ip_Address");
        ob2IPsubnet = xmlManager.getValueFromWebPortAndDut("ORBI2", "Ip_Subnet");
        ob2serialNo = xmlManager.getValueFromWebPortAndDut("ORBI2", "SerialNo");
        ob2Model = xmlManager.getValueFromWebPortAndDut("ORBI2", "Model");
        ob2deveiceName = xmlManager.getValueFromWebPortAndDut("ORBI2", "DeviceName");
        ob2sate1Model = xmlManager.getValueFromWebPortAndDut("ORBI2", "Satellite1_Model");
        ob2sate1SSIDWANbssid = xmlManager.getValueFromWebPortAndDut("ORBI2", "Satellite1_SSIDWAN_BSSID");
        ob2sate2Model = xmlManager.getValueFromWebPortAndDut("ORBI2", "Satellite2_Model");
        ob2sate2SSIDWANbssid = xmlManager.getValueFromWebPortAndDut("ORBI2", "Satellite2_SSIDWAN_BSSID");
        ob2CPCNIC = xmlManager.getValueFromWebPortAndDut("ORBI2", "CPC_NIC_to_ORBI_LAN");
        ob2CPCNICSatellite = xmlManager.getValueFromWebPortAndDut("ORBI2", "CPC_NIC_to_Satellite");

        ob2FirmwareVersion = xmlManager.getValueFromWebPortAndDut("ORBI2", "FirmwareVersion");
        ob2Name = xmlManager.getValueFromWebPortAndDut("ORBI2", "Name");
        ob2MAC_Address = xmlManager.getValueFromWebPortAndDut("ORBI2", "MAC_Address");
        ob2GUI_URL = xmlManager.getValueFromWebPortAndDut("ORBI2", "GUI_URL");
        ob2UserName = xmlManager.getValueFromWebPortAndDut("ORBI2", "UserName");
        ob2WifiNetworkName1 = xmlManager.getValueFromWebPortAndDut("ORBI2", "WifiNetworkName1");
        ob2WifiNetworkPwd1 = xmlManager.getValueFromWebPortAndDut("ORBI2", "WifiNetworkPwd1");
        
        ob2MAC_Address2 = xmlManager.getValueFromWebPortAndDut("ORBI2", "MAC_Address2");
        ob2SSIDWANbssid = xmlManager.getValueFromWebPortAndDut("ORBI2", "SSIDWAN_BSSID");

        ob3IPaddress = xmlManager.getValueFromWebPortAndDut("ORBI3", "Ip_Address");
        ob3IPsubnet = xmlManager.getValueFromWebPortAndDut("ORBI3", "Ip_Subnet");
        ob3serialNo = xmlManager.getValueFromWebPortAndDut("ORBI3", "SerialNo");
        ob3Model = xmlManager.getValueFromWebPortAndDut("ORBI3", "Model");
        ob3deveiceName = xmlManager.getValueFromWebPortAndDut("ORBI3", "DeviceName");
        ob4IPaddress = xmlManager.getValueFromWebPortAndDut("ORBI4", "Ip_Address");
        ob4IPsubnet = xmlManager.getValueFromWebPortAndDut("ORBI4", "Ip_Subnet");
        ob4serialNo = xmlManager.getValueFromWebPortAndDut("ORBI4", "SerialNo");
        ob4Model = xmlManager.getValueFromWebPortAndDut("ORBI4", "Model");
        ob4deveiceName = xmlManager.getValueFromWebPortAndDut("ORBI4", "DeviceName");

        arch = xmlManager.getValueFromWebPortalXml("//Arch");

        // for win client
        clientip = xmlManager.getValueFromWebPortAndDut("CLIENT", "Windows_ip_address");
        clientport = xmlManager.getValueFromWebPortAndDut("CLIENT", "Windows_Socket_Port");
        clientwlanmac = xmlManager.getValueFromWebPortAndDut("CLIENT", "Windows_WLAN_MAC");

        client1name = xmlManager.getValueFromWebPortAndDut("CLIENT1", "Name");
        client1ip = xmlManager.getValueFromWebPortAndDut("CLIENT1", "Windows_ip_address");
        client1port = xmlManager.getValueFromWebPortAndDut("CLIENT1", "Windows_Socket_Port");
        client1wifi = xmlManager.getValueFromWebPortAndDut("CLIENT1", "Windows_Wifi_Adapter");
        client1wifimac = xmlManager.getValueFromWebPortAndDut("CLIENT1", "Windows_Wifi_MAC");
        client1mac=xmlManager.getValueFromWebPortAndDut("CLIENT1","Client_MAC");
        client2name = xmlManager.getValueFromWebPortAndDut("CLIENT2", "Name");
        client2ip = xmlManager.getValueFromWebPortAndDut("CLIENT2", "Windows_ip_address");
        client2port = xmlManager.getValueFromWebPortAndDut("CLIENT2", "Windows_Socket_Port");
        client2wifi = xmlManager.getValueFromWebPortAndDut("CLIENT2", "Windows_Wifi_Adapter");
        client2eth = xmlManager.getValueFromWebPortAndDut("CLIENT2", "Windows_Ethernet_Adapter");
        client2wifimac = xmlManager.getValueFromWebPortAndDut("CLIENT2", "Windows_Wifi_MAC");

        // for ap device
        ap1IPaddress = xmlManager.getValueFromWebPortAndDut("AP1", "Ip_Address");
        ap1serialNo = xmlManager.getValueFromWebPortAndDut("AP1", "SerialNo");
        ap1deveiceName = xmlManager.getValueFromWebPortAndDut("AP1", "DeviceName");
        ap1macaddress = xmlManager.getValueFromWebPortAndDut("AP1", "Mac_Address");
        ap2macaddress = xmlManager.getValueFromWebPortAndDut("AP2", "Mac_Address");
        ap3macaddress = xmlManager.getValueFromWebPortAndDut("AP3", "Mac_Address");
        ap1Firmware = xmlManager.getValueFromWebPortAndDut("AP1", "Firmware");
        ap1Model = xmlManager.getValueFromWebPortAndDut("AP1", "Model");
        ap1TargetFirmware = xmlManager.getValueFromWebPortAndDut("AP1", "TargetFirmware");

        ap5deveiceName = xmlManager.getValueFromWebPortAndDut("AP5", "DeviceName");
        ap6deveiceName = xmlManager.getValueFromWebPortAndDut("AP6", "DeviceName");
        ap7deveiceName = xmlManager.getValueFromWebPortAndDut("AP7", "DeviceName");
        ap8deveiceName = xmlManager.getValueFromWebPortAndDut("AP8", "DeviceName");
        ap9deveiceName = xmlManager.getValueFromWebPortAndDut("AP9", "DeviceName");
        ap10deveiceName = xmlManager.getValueFromWebPortAndDut("AP10", "DeviceName");
        ap5IPaddress = xmlManager.getValueFromWebPortAndDut("AP5", "Ip_Address");
        ap6IPaddress = xmlManager.getValueFromWebPortAndDut("AP6", "Ip_Address");
        ap7IPaddress = xmlManager.getValueFromWebPortAndDut("AP7", "Ip_Address");
        ap8IPaddress = xmlManager.getValueFromWebPortAndDut("AP8", "Ip_Address");
        ap5Model = xmlManager.getValueFromWebPortAndDut("AP5", "Model");
        ap6Model = xmlManager.getValueFromWebPortAndDut("AP6", "Model");
        ap7Model = xmlManager.getValueFromWebPortAndDut("AP7", "Model");
        ap8Model = xmlManager.getValueFromWebPortAndDut("AP8", "Model");

        ap4serialNo = xmlManager.getValueFromWebPortAndDut("AP4", "SerialNo");
        ap5serialNo = xmlManager.getValueFromWebPortAndDut("AP5", "SerialNo");
        ap6serialNo = xmlManager.getValueFromWebPortAndDut("AP6", "SerialNo");
        ap7serialNo = xmlManager.getValueFromWebPortAndDut("AP7", "SerialNo");
        ap8serialNo = xmlManager.getValueFromWebPortAndDut("AP8", "SerialNo");
        ap9serialNo = xmlManager.getValueFromWebPortAndDut("AP9", "SerialNo");
        ap10serialNo = xmlManager.getValueFromWebPortAndDut("AP10", "SerialNo");
        ap4macaddress = xmlManager.getValueFromWebPortAndDut("AP4", "Mac_Address");
        ap5macaddress = xmlManager.getValueFromWebPortAndDut("AP5", "Mac_Address");
        ap6macaddress = xmlManager.getValueFromWebPortAndDut("AP6", "Mac_Address");
        ap7macaddress = xmlManager.getValueFromWebPortAndDut("AP7", "Mac_Address");
        ap8macaddress = xmlManager.getValueFromWebPortAndDut("AP8", "Mac_Address");
        ap9macaddress = xmlManager.getValueFromWebPortAndDut("AP9", "Mac_Address");
        ap10macaddress = xmlManager.getValueFromWebPortAndDut("AP10", "Mac_Address");

        ap2IPaddress = xmlManager.getValueFromWebPortAndDut("AP2", "Ip_Address");
        ap2serialNo = xmlManager.getValueFromWebPortAndDut("AP2", "SerialNo");
        ap2deveiceName = xmlManager.getValueFromWebPortAndDut("AP2", "DeviceName");
        ap2Firmware = xmlManager.getValueFromWebPortAndDut("AP2", "Firmware");
        ap2Model = xmlManager.getValueFromWebPortAndDut("AP2", "Model");

        ap3IPaddress = xmlManager.getValueFromWebPortAndDut("AP3", "Ip_Address");
        ap4IPaddress = xmlManager.getValueFromWebPortAndDut("AP4", "Ip_Address");
        ap3serialNo = xmlManager.getValueFromWebPortAndDut("AP3", "SerialNo");
        ap3deveiceName = xmlManager.getValueFromWebPortAndDut("AP3", "DeviceName");
        ap3Firmware = xmlManager.getValueFromWebPortAndDut("AP3", "Firmware");
        ap3Model = xmlManager.getValueFromWebPortAndDut("AP3", "Model");
        pr1IPaddress = xmlManager.getValueFromWebPortAndDut("PR1", "Ip_Address");
        pr1serialNo = xmlManager.getValueFromWebPortAndDut("PR1", "SerialNo");
        pr1deveiceName = xmlManager.getValueFromWebPortAndDut("PR1", "DeviceName");
        pr1macaddress = xmlManager.getValueFromWebPortAndDut("PR1", "Mac_Address");

        // Add by sujuan.li at2018/9/10
        brlanclientip = xmlManager.getValueFromPortXml("//BR_LANClientIP");
        brlanclient2ip = xmlManager.getValueFromPortXml("//BR_LANClient2IP");
        brlanclientip2 = xmlManager.getValueFromPortXml("//BR_LANClientIP2");
        brwanclientip = xmlManager.getValueFromPortXml("//BR_WANClientIP");
        brlangateway = xmlManager.getValueFromPortXml("//BR_LANGateway");
        brwangateway = xmlManager.getValueFromPortXml("//BR_WANGateway");
        brlanconnectip = xmlManager.getValueFromPortXml("//BR_LANConnectIP");
        brwanconnectip = xmlManager.getValueFromPortXml("//BR_WANConnectIP");
        brlanconnectip2 = xmlManager.getValueFromPortXml("//BR_LANConnectIP2");
        brcleintport1 = xmlManager.getValueFromPortXml("//BR_LANClientPort1");
        brcleintport2 = xmlManager.getValueFromPortXml("//BR_LANClientPort2");
        brdutwanip = xmlManager.getValueFromPortXml("//BR_DUTWAN");
        brpptpl2tpserver = xmlManager.getValueFromPortXml("//BR_PPTPL2TPServer");
        brpptpl2tpserversub = xmlManager.getValueFromPortXml("//BR_PPTPL2TPServerSub");
        bripsecoppositewanip = xmlManager.getValueFromPortXml("//BR_IPSEC2GATEWAY");
        bripsecoppositelangateway = xmlManager.getValueFromPortXml("//BR_IPSEC2CLIENTGATEWAY");
        bripsecoppositelanclient = xmlManager.getValueFromPortXml("//BR_IPSEC2CLIENT");
        promocode1 = xmlManager.getValueFromWebPortalXml("//Promocode1");
        promocode2 = xmlManager.getValueFromWebPortalXml("//Promocode2");
        promocode3 = xmlManager.getValueFromWebPortalXml("//Promocode3");
        // Add by sujuan.li at2018/9/10

        // for firmware upgrade test for orbi
        serverimgver = xmlManager.getValueFromPortXml("//ServerImageVersion");
        basebeforeimg = xmlManager.getValueFromPortXml("//beforeimage");
        baseafterimg = xmlManager.getValueFromPortXml("//afterimage");
        satellitebeforeimg = xmlManager.getValueFromPortXml("//satellitebeforeimage");
        satelliteafterimg = xmlManager.getValueFromPortXml("//satelliteafterimage");
        
        Country = xmlManager.getValueFromWebPortalXml("//Country");
        
        //forPRDevice
        pr1IPaddress = xmlManager.getValueFromWebPortAndDut("PR1", "Ip_Address");
        pr1serialNo = xmlManager.getValueFromWebPortAndDut("PR1", "SerialNo");
        pr1deveiceName = xmlManager.getValueFromWebPortAndDut("PR1", "DeviceName");
        pr1macaddress = xmlManager.getValueFromWebPortAndDut("PR1", "Mac_Address");
        pr1Firmware = xmlManager.getValueFromWebPortAndDut("PR1", "Firmware");
        pr1Model = xmlManager.getValueFromWebPortAndDut("PR1", "Model");

        pr2IPaddress = xmlManager.getValueFromWebPortAndDut("PR2", "Ip_Address");
        pr2serialNo = xmlManager.getValueFromWebPortAndDut("PR2", "SerialNo");
        pr2deveiceName = xmlManager.getValueFromWebPortAndDut("PR2", "DeviceName");
        pr2macaddress = xmlManager.getValueFromWebPortAndDut("PR2", "Mac_Address");
        pr2Firmware = xmlManager.getValueFromWebPortAndDut("PR2", "Firmware");
        pr2Model = xmlManager.getValueFromWebPortAndDut("PR2", "Model");
        
        baseURI = xmlManager.getValueFromWebPortAndDut("Api_Main", "baseURI");
        apikey = xmlManager.getValueFromWebPortAndDut("Api_Main", "apikey");
        xsrfToken = xmlManager.getValueFromWebPortAndDut("Api_Main", "xsrfToken");
        accountId = xmlManager.getValueFromWebPortAndDut("Api_Main", "accountId"); 
        networkId = xmlManager.getValueFromWebPortAndDut("Api_Main", "networkId"); 
        orgId = xmlManager.getValueFromWebPortAndDut("Api_Main", "orgId"); 
        token = xmlManager.getValueFromWebPortAndDut("Api_Main", "token");
        
        // init for multiple language suports
        languageDict = excelUnit.ntgrAnalysisJsonFile();
        logger.info("init end");

        isRltkSW1 = isRltkSW(sw1Model) ? true : false;
        isRltkSW2 = isRltkSW(sw2Model) ? true : false;
    }

    public String getLocalNetIp(boolean isBR) {
        String ips = new RunCommand().getCmdOutput("ipconfig.exe", "ipconfig.exe", 5);
        String ip = sw1IPaddress;
        if (isBR) {
            ip = br1IPaddress;
        }

        logger.info(ips);
        String ipprefix = ip.substring(0, ip.lastIndexOf("."));
        for (String line : ips.split("\n")) {
            logger.info(line);
            if (line.contains("IPv4") && line.contains(ipprefix))
                return line.split(":")[1].trim();
        }

        return ip;
    }

    public static int getSwitchPortNumber(String model) {
        if (model.contains("110") || model.contains("510") || model.contains("710"))
            return 10;
        if (model.contains("724") || model.contains("324"))
            return 24;
        if (model.contains("728"))
            return 28;
        if (model.contains("752"))
            return 52;
        if ((model.contains("716") || model.contains("516")) || model.contains("M4350")|| model.contains("M4250"))
            return 16;
        if (model.contains("108"))
            return 8;
        throw new RuntimeException("unknow type");
    }

    public static boolean isSwitcMS510(String model) {
        return model.contains("MS510");
    }

    public static boolean isSwitcMS324(String model) {
        return model.contains("MS324");
    }

    public static boolean isSwitcMS108(String model) {
        return model.contains("MS108");
    }

    public static boolean isSwitcXS516(String model) {
        return model.contains("XS516");
    }

    public static boolean isSwitcMS510() {
        return sw1Model.contains("MS510");
    }

    public static boolean isSwitcMS324() {
        return sw1Model.contains("MS324");
    }

    public static boolean isSwitcMS108() {
        return sw1Model.contains("MS108");
    }

    public static boolean isSwitcXS516() {
        return sw1Model.contains("XS516");
    }
    public static boolean isSwitcM4250(String model) {
        return model.contains("M4250");
    }
    public static boolean isSwitcM4350(String model) {
        return model.contains("M4350");
    }

    /**
     * @param model
     * @param port
     * @return return g3, mg4, xmg5
     */
    public static String getSwitchPort(String model, int port) {
        String name = "g";
        if (isSwitcMS510(model)) {
            if (port > 8) {
                name = "xg";
            } else if (port > 4) {
                name = "xmg";
            } else {
                name = "mg";
            }
        } else if (isSwitcMS324(model)) {
            if (port > 24) {
                name = "xg";
            } else {
                name = "mg";
            }
        } else if (isSwitcMS108(model)) {
            name = "mg";
        } else if (isSwitcXS516(model)) {
            name = "xg";
        } else if(isSwitcM4350(model)) {
            name = "1/0/";
        }else if(isSwitcM4250(model)) {
            name = "0/";
        }
        String toreturn = String.format("%s%s", name, port);
        logger.info(String.format("model/port/return: %s/%s/%s", model, port, toreturn));
        return toreturn;
    }

    /**
     * @param model
     * @param port
     *            can be 1, g1
     * @return return g3, mg4, xmg5
     */
    public static String getSwitchPort(String model, String port) {
        if (!port.contains("lag"))  {
            port = port.replace("xmg", "").replace("mg", "").replace("xg", "").replace("g", "").replace("1/0/", "").replace("0/", "");
            System.out.println(port);
            return getSwitchPort(model, Integer.parseInt(port));
        } else {
            return port;
        }

    }

    /**
     * @param is2ndSw
     *            true to 2nd switch
     * @param is2ndPort
     *            true to 2nd lag port
     * @return g3 or g4
     */
    public static String getSwitchLag(boolean is2ndSw, boolean is2ndPort) {
        String model = sw1Model;
        String port = sw1LagPort1;
        if (is2ndSw) {
            model = sw2Model;
            port = sw2LagPort1;
            if (is2ndPort) {
                port = sw2LagPort2;
            }
        } else {
            if (is2ndPort) {
                port = sw1LagPort2;
            }
        }
        return getSwitchPort(model, Integer.parseInt(port));
    }

    /**
     * @param is2ndSw
     *            true for sw2
     * @param is2ndPort
     *            true for lag port 2
     * @return
     */
    public static int getSwitchLagIndex(boolean is2ndSw, boolean is2ndPort) {
        String port = null;
        if (is2ndSw) {
            port = sw2LagPort1;
            if (is2ndPort) {
                port = sw2LagPort2;
            }
        } else {
            port = sw1LagPort1;
            if (is2ndPort) {
                port = sw1LagPort2;
            }
        }
        logger.info(String.format("is2ndSw/is2ndPort/port: %s/%s/%s", is2ndSw, is2ndPort, port));
        return Integer.parseInt(port);
    }

    public static int getSwitchPortNumber() {
        return getSwitchPortNumber(sw1Model);
    }

    public static String getSwitchUplinkPort(String model) {
        if (model.contains("110") || model.contains("510") || model.contains("710") || model.contains("108"))
            return "8";
        if (model.contains("724") || model.contains("728") || model.contains("752") || model.contains("716") || model.contains("516")
                || model.contains("324"))
            return "16";
        throw new RuntimeException("unknow type for model: " + model);
    }

    /**
     * @param text
     * @return
     *         return true for rltk false for brcm
     */
    public static boolean checkSwitchType(String text) {
        System.out.println("checkSwitchType:" + text);
        // if (text.startsWith("GS") || text.startsWith("GC108P") || text.startsWith("MS"))
        // return true;
        // return false;
        if (text.startsWith("GC728X") || text.startsWith("GC728XP") || text.startsWith("GC752X") || text.startsWith("GC752XP")
                || text.startsWith("GC110P") || text.startsWith("GS724Tv6") || text.startsWith("GS748Tv6") || text.startsWith("XS508TM")
                || text.startsWith("XS516TM") || text.startsWith("XS724TM") || text.startsWith("GS510TPP") || text.startsWith("M4350") || text.startsWith("M4250"))
            return false;
        return true;
    }

    /**
     * @param text
     *            check model or ip
     * @return true is rt switch, false is bc
     */
    public static boolean isRltkSW(String text) {
        assertTrue(text.length() > 1);
        assertTrue(sw1IPaddress.length() > 1);
        assertTrue(sw2IPaddress.length() > 1);
        boolean isRltk = false;
        if (bk_sw1IPaddress == null) {
            bk_sw1IPaddress = sw1IPaddress;
            bk_sw2IPaddress = sw2IPaddress;
        }
        if (!text.contains(".")) {
            if (checkSwitchType(text))
                isRltk = true;
        } else {
            if ((text.equals(sw1IPaddress) && isRltkSW1) || (text.equals(sw2IPaddress) && isRltkSW2)) {
                isRltk = true;
            } else {
                isRltk = false;
            }
        }
        logger.info("check switch type for: " + text + ", is rltk: " + isRltk);
        return isRltk;
    }

    public static boolean isRltkSW() {
        if (checkSwitchType(sw1Model) || checkSwitchType(sw2Model))
            return true;
        return false;
    }

    // to backup all sw1 options
    public static boolean isOptionSwitched  = false;
    public static String  bk_sw1Model       = null;
    public static String  bk_sw1serialNo    = null;
    public static String  bk_sw1deveiceName = null;
    public static boolean bk_isRltkSW1;
    public static String  bk_sw1IPaddress   = null;
    public static String  bk_sw2IPaddress   = null;

    /**
     * @param isSet
     *            true to update sw1 options to new one
     * @param ip
     */
    public static void updateSwitchOneOption(boolean isSet, String ip) {
        logger.info(String.format("isSet:%s - ip:%s", isSet, ip));
        if (bk_sw1Model == null) {
            bk_sw1Model = sw1Model;
            bk_isRltkSW1 = isRltkSW1;
            bk_sw1serialNo = sw1serialNo;
            bk_sw1deveiceName = sw1deveiceName;
        }

        if (isSet) {
            if (bk_sw1IPaddress.equals(ip)) {
                isOptionSwitched = false;
            } else {
                isOptionSwitched = true;
            }

            if (isOptionSwitched) {
                isRltkSW1 = isRltkSW2;
                sw1IPaddress = sw2IPaddress;
                sw1Model = sw2Model;
                sw1serialNo = sw2serialNo;
                sw1deveiceName = sw2deveiceName;
            }
        } else {
            isRltkSW1 = bk_isRltkSW1;
            sw1IPaddress = bk_sw1IPaddress;
            sw1Model = bk_sw1Model;
            sw1serialNo = bk_sw1serialNo;
            sw1deveiceName = bk_sw1deveiceName;
        }

        SwitchCLIUtils.switchIp = sw1IPaddress;
        logger.info(String.format("sw1Model/IPaddress:%s/%s - sw1IsRltk:%s", sw1Model, sw1IPaddress, isRltkSW1));
    }

    static int currentSwitchIpMode = 0;

    /**
     * @param ipMode
     *            0 - dhcp, 1 - static, 2 - none
     *            Note1: 1 & 2 are using same ip
     *            Note2: call 0 to restore on tearDown
     */
    public static void updateSwitchIp(int ipMode) {
        logger.info("update ip mode to: " + ipMode);
        if (currentSwitchIpMode == ipMode) {
            logger.info("no changes");
            return;
        }
        currentSwitchIpMode = ipMode;
        if ((ipMode != 0) && (currentSwitchIpMode == 0)) {
            logger.info("it's static or none");
            sw1IPaddress = MyCommonAPIs.nextIP(sw1IPaddress, 50);
            sw2IPaddress = MyCommonAPIs.nextIP(sw2IPaddress, 50);
        }
        if ((ipMode == 0) && (currentSwitchIpMode != 0)) {
            logger.info("it's dhcp");
            sw1IPaddress = MyCommonAPIs.nextIP(sw1IPaddress, -50);
            sw2IPaddress = MyCommonAPIs.nextIP(sw2IPaddress, -50);
        }
    }

    /**
     * @param lines
     *            from show running-config
     * @param match
     *            text to contains
     * @param isRe
     *            true to use regx
     * @return
     */
    public static String includeLines(String lines, String match, boolean isRe) {
        String toRet = "";
        lines = lines.replaceAll("\r", "");
        for (String line : lines.split("\n")) {
            line = line.trim();
            if (line.length() > 1) {
                if ((!isRe && line.contains(match)) || (isRe && MyCommonAPIs.matches(line, match))) {
                    logger.info(line);
                    toRet += line + "\n";
                }
            }
        }
        if (toRet.equals("")) {
            logger.info("no line matched");
        }
        return toRet;
    }

    /**
     * @param lines
     *            lines contains many lines
     * @param matchs
     *            list of string to match
     * @return a string contains all matched lines
     */
    public static String includeLines(String lines, List<String> matchs) {
        String toRet = "";
        lines = lines.replaceAll("\r", "");
        for (String line : lines.split("\n")) {
            line = line.trim();

            boolean bMatched = false;
            for (String match : matchs) {
                if ((match != null) && line.contains(match)) {
                    bMatched = true;
                    break;
                }
            }
            if (bMatched) {
                logger.info(line);
                toRet += line + "\n";
            }
        }
        if (toRet.equals("")) {
            logger.info("no line matched");
        }
        return toRet;
    }

    public static void print(String msg) {
        System.out.println(sdfFomat.format(new Date()) + " - " + msg);
    }

    public static String _ntgrLanguageTranslate(String key1, String key2, String tarLan) {
        WriteExcelUtil excelUnit = new WriteExcelUtil();
        String tarStr = key2;
        if (languageDict.containsKey(tarLan.toLowerCase())) {
            if (languageDict.get(tarLan.toLowerCase()).containsKey(key1)) {
                HashMap<String, String> mapTo = excelUnit.getJsonStringMap(languageDict.get(tarLan.toLowerCase()).get(key1).toString());
                if (mapTo.containsKey(key2)) {
                    tarStr = mapTo.get(key2);
                }
            }
        }
        return tarStr;
    }

    /**
     * return English text if not found
     *
     * @param key1
     * @param key2
     * @param tarLan
     *            current browser language text
     * @return
     */
    public static String ntgrLanguageTranslate(String key1, String key2, String tarLan) {
        String tarStr = _ntgrLanguageTranslate(key1, key2, tarLan);
        if (tarStr.equals(key2)) {
            tarStr = _ntgrLanguageTranslate(key1, key2, defaultLang);
            logger.warning(String.format("%s-%s: %s", key1, key2, tarStr));
        } else {
            logger.info(String.format("%s-%s: %s", key1, key2, tarStr));
        }

        return tarStr;
    }

    public static String ntgrTranslateToLanguage(String sourceString, String fromLan, String tarLan) {
        WriteExcelUtil excelUnit = new WriteExcelUtil();
        String key1 = "";
        String key2 = "";
        int flag = 0;
        String tarStr = sourceString;
        if (languageDict.containsKey(fromLan.toLowerCase())) {
            mark: for (String key3 : languageDict.get(fromLan.toLowerCase()).keySet()) {
                // System.out.println("key: "+key+",value:"+map.get(key));
                HashMap<String, String> mapTo = excelUnit.getJsonStringMap(languageDict.get(fromLan.toLowerCase()).get(key3).toString());
                for (String key4 : mapTo.keySet()) {
                    if (mapTo.get(key4).equals(sourceString.trim())) {
                        flag = 1;
                        key1 = key3;
                        key2 = key4;
                        break mark;
                    }
                }

            }
        }
        if (flag == 1) {
            tarStr = ntgrLanguageTranslate(key1, key2, tarLan);
        }
        logger.info(String.format("%s: %s", sourceString, tarStr));

        return tarStr;
    }

    // covert to default
    public static String defaultLang = "en";

    /**
     * @param key1
     * @param key2
     * @return The text according to current browser's language
     */
    public static String getLocText(String key1, String key2) {
        return ntgrLanguageTranslate(key1, key2, BrowserLanguage);
    }

    /**
     * @param sourceString
     * @return The text according to current browser's language
     */
    public static String getLocText(String sourceString) {
        return ntgrTranslateToLanguage(sourceString, defaultLang, BrowserLanguage);
    }

    /**
     * @param sourceString
     * @return The English language text from current browser's language
     */
    public static String getNLocText(String sourceString) {
        return ntgrTranslateToLanguage(sourceString, BrowserLanguage, defaultLang);
    }

    /**
     * @param key1
     * @param key2
     * @return The English text on other language which was not translated correctly
     */
    public static String getNLocText(String key1, String key2) {
        return ntgrLanguageTranslate(key1, key2, defaultLang);
    }

    // add by dallas.zhao at 2019/6/12
    public static String getApXmlConfig(String Model, String key) {
        String result = "";
        if (ap1deveiceName.indexOf(Model) != -1) {
            switch (key) {
            case "Serial Number":
                result = ap1serialNo;
                break;
            case "Device Name":
                result = ap1deveiceName;
                break;
            case "Address":
                result = ap1IPaddress;
                break;
            }
        } else if (ap2deveiceName.indexOf(Model) != -1) {
            switch (key) {
            case "Serial Number":
                result = ap2serialNo;
                break;
            case "Device Name":
                result = ap2deveiceName;
                break;
            case "Address":
                result = ap2IPaddress;
                break;
            }
        } else if (ap3deveiceName.indexOf(Model) != -1) {
            switch (key) {
            case "Serial Number":
                result = ap3serialNo;
                break;
            case "Device Name":
                result = ap3deveiceName;
                break;
            case "Address":
                result = ap3IPaddress;
                break;
            }
        }
        return result;
    }

    public static String resultPass = "SANITYPASS";
    public static String resultFail = "SANITYFAIL";
    public static int    noStepPass = 0;
    public static int    noStepFail = 0;

    public static void printResult(boolean result, String switchIp, String switchMode, String checkPort) {
        if (result) {
            noStepPass++;
        } else {
            noStepFail++;
        }
        System.out.println(String.format("==========> %s(%s-%s: Pass-%s/Fail-%s/Total-%s) -> (%s) <==========", result ? resultPass : resultFail,
                switchMode, switchIp, noStepPass, noStepFail, noStepPass + noStepFail, checkPort));
    }

    public static byte[] takeScreenShot() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (WebportalParam.browserIsFailed || (WebportalParam.curWebDriver == null))
            return baos.toByteArray();
        try {
            Screenshot ss = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(WebportalParam.curWebDriver);
            BufferedImage bi = ss.getImage();
            ImageIO.write(bi, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return ((TakesScreenshot) WebportalParam.curWebDriver).getScreenshotAs(OutputType.BYTES);
        }
    }

    public static String getCSVData(String csvFileName, String headerMatch, String headerReturn, String rowMatch) {
        String toReturn = "";
        System.out.println("111111111111111111111");
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFileName));
            System.out.println("2222222222222222");
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            System.out.println("333333333333333333");
            System.out.println(csvParser);
            for (CSVRecord csvRecord : csvParser) {
                System.out.println("4444444444444444444");
                if (csvRecord.get(headerMatch).equalsIgnoreCase(rowMatch)) {
                    toReturn = csvRecord.get(headerReturn);
                    break;
                }
            }
            csvParser.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        logger.info(String.format("Got <%s-%s> on <%s-%s> in: %s", rowMatch, toReturn, headerMatch, headerReturn, csvFileName));
        return toReturn;
    }

    public static String getDeviceSerialNoCSV(String devName) {
        System.out.println("++++++++++++++++++++");
        String csvFile = System.getProperty("user.dir") + "/src/test/resources/HardBundleDevice.csv";
        return getCSVData(csvFile, "DEVICE NAME", "DEVICE SERIAL", devName);
    }

    public static String getDeviceMacCSV(String devName) {
        String csvFile = System.getProperty("user.dir") + "/src/test/resources/HardBundleDevice.csv";
        return getCSVData(csvFile, "DEVICE NAME", "MAC", devName);
    }

    public static String getDeviceMacCSVSKU(String devName) {
        String csvFile = System.getProperty("user.dir") + "/src/test/resources/SKUSerialNo.csv";
        return getCSVData(csvFile, "DEVICE NAME", "MAC", devName);
    }

    public static String getDeviceSerialNoSKU(String devName) {
        String csvFile = System.getProperty("user.dir") + "/src/test/resources/SKUSerialNo.csv";
        return getCSVData(csvFile, "DEVICE NAME", "DEVICE SERIAL", devName);
    }
    
    public static String getDeviceSerialNoMAC(String devName) {
        String csvFile = System.getProperty("user.dir") + "/src/test/resources/ACL_List.xlsx";
        return getCSVData(csvFile, "DEVICE NAME", "Actuallist", devName);
    }
        
    public static boolean ManagedorSMart(String text) {
        System.out.println("checkSwitchType:" + text);
        // if (text.startsWith("GS") || text.startsWith("GC108P") || text.startsWith("MS"))
        // return true;
        // return false;
        if (text.startsWith("M4"))
            return true;
        return true;
    }

}
