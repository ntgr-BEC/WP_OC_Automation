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
        public Map<String, String> ENDPOINT_URL = new HashMap<String, String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            {
                put("Network_Sanity", "insightappcom/api/network/v1/{networkId}");
                put("Add_Network", "insightappcom/api/network/v1/premium/{accountId}");
                put("Vlan_Sanity","insightappcom/api/network/v1/vlans/{networkId}");
                put("Add_Ssid", "insightappcom/api/wireless/v1/ssid/{networkId}");
                put("Network_Settings", "insightappcom/api/wireless/v1/{networkId}/broadcasttounicast");
                put("Lag_Group", "insightappcom/api/network/v1/{networkId}/lagGroup");
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
            }
        };
        
    }


     