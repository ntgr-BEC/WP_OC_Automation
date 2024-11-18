package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.SSIDData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessQuickViewElement;
import java.util.Random;
import org.openqa.selenium.interactions.Actions;
import util.*;

    public class ApiRequest {
        
        static {
            RestAssured.baseURI=WebportalParam.baseURI;
            RestAssured.defaultParser=Parser.JSON;
        }

        // Generic GET request method
        public static Response sendGetRequest(String endpoint, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            if(pathParams == null)
            {
                pathParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .when()
                    .get(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
            if(responseBody.contains("Invalid session")) {
                throw new RuntimeException("Token has expired. Please refresh your token");
            }
            return response;
        }

        // Generic POST request method
        public static Response sendPostRequest(String endpoint, String requestBody, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
        
        if(pathParams == null)
        {
            pathParams=new HashMap<>();
        }
     Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
     response.prettyPrint();
     String responseBody=response.getBody().asString();
     if(responseBody.contains("Invalid session")) {
         throw new RuntimeException("Token has expired. Please refresh your token");
     }   

     return response;
            
            
            
        }

        // Generic PUT request method
        public static Response sendPutRequest(String endpoint, String requestBody, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            if(pathParams == null)
            {
                pathParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .put(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
            if(responseBody.contains("Invalid session")) {
                throw new RuntimeException("Token has expired. Please refresh your token");
            }
            
            
            return response;
            
        }
            

        // Generic DELETE request method
        public static Response sendDeleteRequest(String endpoint, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
           
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .when()
                    .delete(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
            if(responseBody.contains("Invalid session")) {
                throw new RuntimeException("Token has expired. Please refresh your token");
            }
            return response;
            
        }
        
        public static Response sendGetRequest(String endpoint, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams ,int expectedcode) {
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .when()
                    .get(endpoint)
                    .then()
                    .statusCode(expectedcode)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
            if(responseBody.contains("Invalid session")) {
                throw new RuntimeException("Token has expired. Please refresh your token");
            }
            return response;
        }

        // Generic POST request method
        public static Response sendPostRequest(String endpoint, String requestBody, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams , int expectedcode) {
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
     Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post(endpoint)
                    .then()
                    .statusCode(expectedcode)
                    .extract()
                    .response();
     response.prettyPrint();     
     return response;
            
            
            
        }

        // Generic PUT request method
        public static Response sendPutRequest(String endpoint, String requestBody, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams , int expectedcode) {
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .put(endpoint)
                    .then()
                    .statusCode(expectedcode)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
            if(responseBody.contains("Invalid session")) {
                throw new RuntimeException("Token has expired. Please refresh your token");
            }
            
            
            return response;
            
        }
            

        // Generic DELETE request method
        public static Response sendDeleteRequest(String endpoint, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams , int expectedcode) {
           
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .when()
                    .delete(endpoint)
                    .then()
                    .statusCode(expectedcode)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
//            if(responseBody.contains("Invalid session")) {
//                throw new RuntimeException("Token has expired. Please refresh your token");
//            }
            return response;
            
        }
        
        // Generic DELETE request method with delete body
        public static Response sendDeleteRequest(String endpoint, String requestBody, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
           
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .delete(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
            return response;
            
        }
        
        // Generic PUT request without bodu method
        public static Response sendPutRequest(String endpoint, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
            if(endpoint == null)
            {
                throw new IllegalArgumentException("Endpoint URL NOT FOUND");
            }
            if(queryParams == null)
            {
                queryParams=new HashMap<>();
            }
            if(pathParams == null)
            {
                pathParams=new HashMap<>();
            }
            Response response= RestAssured.given()
                    .headers(headers)
                    .pathParams(pathParams)
                    .queryParams(queryParams)
                    .log().all()
                    .when()
                    .put(endpoint)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            response.prettyPrint();
            String responseBody=response.getBody().asString();
            if(responseBody.contains("Invalid session")) {
                throw new RuntimeException("Token has expired. Please refresh your token");
            }
            
            
            return response;
            
        }
        
        public boolean connectClient(Map<String, String> map) {
            MyCommonAPIs.sleepi(30);

            int sum = 0;
            while (true) {
                MyCommonAPIs.sleepi(10);
                if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID " + map.get("SSID"))
                        .indexOf("true") != -1) {
                    break;
                } else if (sum > 30) {
                    assertTrue(false, "Client cannot connected.");
                    break;
                }
                sum += 1;
            }

            boolean result1 = true;
            if (map.get("Security").contains("Open")) {

                System.out.println("enter open");
                if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect  " + map.get("SSID"))
                        .equals("true")) {
                    result1 = false;
                    if (new Javasocket()
                            .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect  " + map.get("SSID"))
                            .equals("true")) {
                        result1 = true;
                    }
                }
            }

            else if (map.get("Security").contains("WPA3")) {
                System.out.println("enter WPA3");
                if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA3SAE aes").equals("true")) {

                    result1 = false;
                    if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                            "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA3SAE aes").equals("true")) {
                        result1 = true;
                    }
                }
            } else {
                System.out.println("enter WPA2");
                if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA2PSK aes").equals("true")) {
                    System.out.println("check this");
                    result1 = false;
                    if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                            "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA2PSK aes").equals("true")) {
                        System.out.println("check this inside");
                        result1 = true;
                    }
                }

            }
            return result1;

        }

        public Map<String, String> ENDPOINT_URL = new HashMap<String, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            {
                put("Network_Sanity", "insightappcom/api/network/v1/{networkId}");
                put("Add_Network", "insightappcom/api/network/v1/premium/{accountId}");
                put("Vlan_Sanity","insightappcom/api/wired/v1/vlans/{networkId}");
                put("Add_Ssid", "insightappcom/api/wireless/v1/ssid/{networkId}");
                put("Network_Settings", "insightappcom/api/wireless/v1/{networkId}/broadcasttounicast");
                put("LagGroup_Sanity", "insightappcom/api/wired/v1/{networkId}/lagGroups");
                put("Ssid_Sanity", "insightappcom/api/wireless/v1/{networkId}/{id}");
                put("Url_Filter", "insightappcom/api/wireless/v1/{networkId}/urlFilter");
                put("Wireless_Settings", "insightappcom/api/wireless/v1/{networkId}/radioConfig");
                put("Ars_Sanity", "insightappcom/api/network/v1/arsConfig/{networkId}");
                put("InstantWifi_Sanity", "insightappcom/api/wireless/v1/networkRFSettings/{networkId}");
                put("Get_FastRoaming", "insightappcom/api/wireless/v1/fastRoaming/{networkId}");
                put("Modify_FastRoaming", "insightappcom/api/wireless/v1/fastRoaming/{networkId}/{status}");
                put("DefaultNatSsids_Sanity", "insightappcom/api/wireless/v1/natSsid");
                put("RadiusServerConfig_Sanity", "insightappcom/api/network/v1/radiusServerConfig");
                put("ScheduleWifi_Sanity", "insightappcom/api/wireless/v1/scheduleSsid/{networkId}");
                put("SNMP_Sanity", "insightappcom/api/network/v1/snmp/{accountId}/{networkId}");
                put("Syslog_Settings", "insightappcom/api/network/v1/sysLog/{networkId}");
                put("Set_SNMP_Settings", "insightappcom/api/network/v1/snmp/{accountId}/{networkId}/{commandType}");
                put("SystemHealthDetails_Sanity", "insightappcom/api/network/v1/systemHealthDetails/{accountId}/{networkId}");
                put("TrafficPolices_Sanity", "insightappcom/api/wireless/v1/wirelessNetwork/trafficPolicies/{networkId}/{id}");
                put("Country_List", "insightappcom/api/public/v1/countryList");
                put("Insight_Models", "insightappcom/api/public/v1/models");
                put("TimeZone_List", "insightappcom/api/public/v1/timeZoneList");
                put("Update_Location_Address", "insightappcom/api/network/v1/Address/{networkId}");
                put("Clone_Network", "insightappcom/api/network/v1/cloneNetwork");
                put("Firmware_Upgrade","insightappcom/api/network/v1/firmwareUpgrade/{deviceCount}/{networkId}");
                put("Update_Location_Password", "insightappcom/api/network/v1/locationPassword/{networkId}");
                put("Firmware_Upgrade_Details","insightappcom/api/network/v1/firmwareUpgrade/devices/{networkId}");
				put("Add_Organization", "insightappcom/api/organization/v1/Organization/{accountId}");
                put("Delete_Organization", "insightappcom/api/organization/v1/{accountId}/Organization/{orgId}");
                put("Update_Organization", "insightappcom/api/organization/v1/Organization/{accountId}/{orgId}");
                put("Get_Organization_List", "insightappcom/api/organization/v1/{accountId}");       
                put("Mesh_Info", "insightappcom/api/wireless/v1/meshInfo/{networkId}"); 
                put("Optimize_Now", "insightappcom/api/wireless/v1/networkRFSettings/{networkId}/optimizeNow/{checkSchedule}");
                put("List_Wireless_Networks", "insightappcom/api/wireless/v1/ssidList/{networkId}");
                put("Modify_InstantWIFI", "insightappcom/api/wireless/v1/networkRFSettings/{networkId}/{requestType}");
                put("API_Headers", "insightappcom/api/v1/apiHeaders");
                put("Modify_Wireless_MacAcl", "insightappcom/api/wireless/v1/macAcl/{networkId}/{wirelessNetworkId}");
                put("Delete_Wireless_MacAcl", "insightappcom/api/wireless/v1/macAclDevice/{networkId}/{wirelessNetworkId}");
                put("Vlan", "insightappcom/api/wired/v1/vlan/{networkId}");
                put("Get_Acl","insightappcom/api/wired/v1/{networkId}/vlan/{vlanId}/aclSettings");
                put("Get_iPAcl","insightappcom/api/wired/v1/{networkId}/vlan/{vlanId}/ipAclSettings");
                put("Enable_MacAcl","insightappcom/api/wired/v1/aclSetting/macAuth/{networkId}/vlan/{vlanId}");
                put("VlanMacAcl_Sanity","insightappcom/api/wired/v1/aclSettings/{networkId}/vlan/{vlanId}");
                put("AllocateCredits","insightappcom/api/organization/v1/allocateCredit/{orgId}");
                put("GetAllocateDeviceCredits","insightappcom/api/organization/v1/creditAllocation/{orgId}");
                put("GetAllocateCredits","insightappcom/api/organization/v1/creditAllocationDetails/{startFrom}");
                put("Organization_Identifier","insightappcom/api/organization/v1/details/{accountId}/{orgId}");
                put("Vlan_Info","insightappcom/api/wired/v1/vlan/{networkId}/{vlanId}");
                put("SDM_Status","insightappcom/api/device/v1/sdmStatus/{serialNo}");
                put("Get_Device","insightappcom/api/device/v1/deviceList/{networkId}/{page}");
                put("Device_RFChannel","insightappcom/api/device/v1/rfChannel/{networkId}/{serialNo}");
                put("Device_MeshInfo","insightappcom/api/device/v1/meshInfoDetails/{serialNo}/{networkId}");
                put("LED_Settings","insightappcom/api/device/v1/ledSettings/{deviceType}/{serialNo}");
                put("Known_UnknownAPs","insightappcom/api/device/v1/knownUnknownAPs/{networkId}/{serialNo}");
                put("Device_Diagnostics","insightappcom/api/device/v1/diagnostic/{serailNo}");
                put("DeviceDetails_ByDeviceIdentifier","insightappcom/api/device/v1/deviceInfo/{deviceId}/{deviceType}/{commandType}");
                put("DeviceFactoryReset_DeviceReboot","/insightappcom/api/device/v1/deviceFactoryReset/{serialNo}/{deviceType}");
                put("Device_IPSettings","insightappcom/api/device/v1/device/{deviceType}/{serialNo}/{commandType}");
                put("ConnectedNeighborsList","insightappcom/api/device/v1/connectedNeighborsList/{serialNo}/{deviceType}/{portId}");
                put("GetBlueLED_Settings","insightappcom/api/device/v1/blueLEDSettings/{serialNo}");
                put("BlueLED_Settings","insightappcom/api/device/v1/blueLEDSettings/{serialNo}/{commandType}");
                put("AP_Statistics","insightappcom/api/device/v1/apStatistics/{serialNo}");
                put("IP_Statistics","insightappcom/api/device/v1/{serialNo}/{deviceType}/{commandType}");
                put("Delete_Device","insightappcom/api/device/v1/{serialNo}/{networkId}");
                put("Add_Device","insightappcom/api/device/v1/{networkId}");
                put("LagGroupId_Sanity", "insightappcom/api/wired/v1/{networkId}/{lagGroupId}/lagGroups");
                put("Add_Network_Pro","insightappcom/api/network/v1/{accountId}/{orgId}");
                put("Get_Network_Pro","insightappcom/api/network/v1/networkList/{accountId}/{orgId}");
                put("Set_SNMP_Configuration","insightappcom/api/network/v1/snmp/{accountId}/{orgId}/{networkId}/{commandType}");
                put("Fetch_Credits","insightappcom/api/license/v1/creditsInfo");
                put("Get_Purchase_Confirmation","insightappcom/api/license/v1/licenseInfo");
                put("Get_Licensekey_Information","insightappcom/api/license/v1/licenseKeyInfo/{type}");
                put("Add_Purchase_Confirmation","insightappcom/api/license/v1/addLicense");
                put("DNSLookUp","insightappcom/api/troubleshoot/v1/dnsLookup/{networkId}");
                put("PingTest","insightappcom/api/troubleshoot/v1/pingTest/{networkId}");
                put("SpeedTest","insightappcom/api/troubleshoot/v1/speedTest/{networkId}");
                put("Traceroute","insightappcom/api/troubleshoot/v1/traceRoute/{networkId}");
                put("DNSLookUp_Results","insightappcom/api/troubleshoot/v1/dnslookupResults/{networkId}/{serialNo}");
                put("PingTest_Results","insightappcom/api/troubleshoot/v1/pingtestResults/{networkId}/{serialNo}");
                put("SpeedTest_results","insightappcom/api/troubleshoot/v1/speedtestResults/{networkId}/{serialNo}");
                put("Traceroute_results","insightappcom/api/troubleshoot/v1/tracerouteTestResults/{networkId}/{serialNo}");
                put("Set_VlanMembers","insightappcom/api/wired/v1/{networkId}/vlan/{vlanId}/vlanMembers");
                put("Switch_CableTest","insightappcom/api/device/v1/switchCableTest/{serialNo}");
                put("Set_WiredRadius","insightappcom/api/wired/v1/{networkId}/wiredRadiusAuth");
                put("IpAcl_Sanity","insightappcom/api/wired/v1/ipAclSettings/{networkId}/vlan/{vlanId}");
                put("Delete_VlanMacAcl","insightappcom/api/wired/v1/macAclSettings/{networkId}/vlan/{vlanId}");
                put("SDM_Status2","insightappcom/api/device/v1/sdmstatus/{serialNo}");
                put("RadiusServer_Sanity","insightappcom/api/wired/v1/radiusServerConfig");
                put("Set_StaticRoute","insightappcom/api/wired/v1/staticRouteInfo/{networkId}");
                put("StaticRoute_Sanity","insightappcom/api/wired/v1/staticRoute/{networkId}");
                put("Set_SpanningTree","insightappcom/api/wired/v1/spanningTree/{networkId}");
                put("Get_SpanningTree","insightappcom/api/wired/v1/spanningTreeInfo/{networkId}");
                put("Get_VlanMembers","insightappcom/api/wired/v1/vlan/{networkId}/vlanmembers/{vlanId}");
                put("Add_Manager","insightappcom/api/msp/v1/manager");
                put("Manager_Sanity","insightappcom/api/msp/v1/manager/{managerId}");
                put("Get_Manager","insightappcom/api/msp/v1/managersList/{orgId}/{startFrom}");
                put("Get_OwnersList","insightappcom/api/msp/v1/ownerList");
                put("Invite_SecAdmin","insightappcom/api/user/v1/secondaryAdmin");
                put("Get_SecAdmin","insightappcom/api/user/v1/secondaryAdmins/{startFrom}");
                put("Delete_SecAdmin","insightappcom/api/user/v1/secondaryAdmin/{secadminId}");
                put("Retrieve_PurchaseOrderHistory","insightappcom/api/user/v1/purchaseHistory/{categoryId}/{timePeriod}");
                put("Usage_History","insightappcom/api/mub/v1/usageBillingHistory");
                put("Usage_Billing_Status","insightappcom/api/mub/v1/usageBillingStatus");
                put("Configure_UsageBilling","insightappcom/api/mub/v1/configureUsageBilling");
                put("Reboot_Device","insightappcom/api/reboot/v1/reboot");
                put("Clients_Info","insightappcom/api/clients/v1/list/{accountId}/{orgId}/{networkId}/{serialNo}/{type}/{isConnected}/{page}");
                put("Reboot_Device1","insightappcom/api/device/v1/deviceReboot/{serialNo}/{deviceType}");
                put("Connected_Clients","insightappcom/api/device/v1/{networkId}/wifiClients/{serialNo}");
                put("Add_Device_Pro","insightappcom/api/device/v1/{orgId}/{networkId}");
                put("Add_Ssid_Pro","insightappcom/api/wireless/v1/{orgId}");
                put("Delete_Ssid_Pro","insightappcom/api/wireless/v1/organization/{orgId}/{wirelessOrgId}");
                put("Add_Wireless_Network","insightappcom/api/wired/v1/vlan/wirelessnetwork/{networkId}");
                put("Modify_VlanMembers","insightappcom/api/wired/v1/vlanMembers/{networkId}/vlan/{vlanId}");
                put("Get_BulkDeplDetails","insightappcom/api/bulk/v1/{orgId}");
                put("Add_BulkDevices","insightappcom/api/bulk/v1/addBulkDevices/{orgId}/{isBulkAdd}");
                put("Get_MeshInfo","insightappcom/api/bulk/v1/getMeshInfo/{orgId}");
                put("Move_Device","insightappcom/api/device/v1/moveDevice/{deviceId}/{networkId}/{orgId}");
                put("OrgSsid_MacAuth","insightappcom/api/wireless/v1/macAuth/{orgId}/{wirelessOrgId}");
                put("OrgSsidDetails_ByOrgIdentifier","insightappcom/api/wireless/v1/organization/ssidList/{orgId}");
                put("Modify_SsidPro","insightappcom/api/wireless/v1/organization/{orgId}/{wirelessOrgId}");
                put("OrgSsid_RadiusServerConfig","insightappcom/api/wireless/v1/radiusServerConfig/{orgId}/{wirelessOrgId}");
                put("OrgSsidDetails_BySsidIdentifier","insightappcom/api/wireless/v1/ssid/{orgId}/{wirelessOrgId}");
                put("LocSsidDetails_BySsidIdentifier","insightappcom/api/wireless/v1/ssid/{networkId}/{id}/{orgId}");
                put("Set_TrafficPolicies","insightappcom/api/wireless/v1/updateTrafficPolicies/{orgId}/{wirelessOrgId}");
                put("Get_TrafficPolicies","insightappcom/api/wireless/v1/trafficPolicies/{orgId}/{wirelessOrgId}");
                put("Set_TrafficPolicies_ForLocSsid","insightappcom/api/wireless/v1/trafficPolicies/{orgId}/{networkId}/{id}");
            }
        };
         
    }


     