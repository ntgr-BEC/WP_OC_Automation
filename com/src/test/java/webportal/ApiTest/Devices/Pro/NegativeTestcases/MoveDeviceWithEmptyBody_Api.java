package webportal.ApiTest.Devices.Pro.NegativeTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import util.MyCommonAPIs;
import webportal.ApiTest.Location.Pro.PositiveTestcases.Api_AddLocationPro;
import webportal.ApiTest.Organizations.PositiveTestcases.Api_AddOrganization;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class MoveDeviceWithEmptyBody_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    
    String OrgID;
    String LocID;
    String deviceId;
    String LocIDcopy;


    
    @Feature("MoveDeviceWithEmptyBody_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Api_MoveDevice with empty body") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2();

    }

    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams1 = new HashMap<String, String>();
        pathParams1.put("orgId",  OrgID);
        pathParams1.put("accountId",WebportalParam.accountIdPro);
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams1, null); 
        getResponse1.then().body("response.status", equalTo(true));
//        
//        
//        Map<String, String> pathParams2 = new HashMap<String, String>();
//        pathParams2.put("networkId",WebportalParam.networkIdPro);
//        pathParams2.put("orgId",WebportalParam.orgId);
//        
//        String requestBody= "{\"deviceInfo\":[{\"abDeviceMode\":\"string\",\"abGroupId\":\"string\",\"deviceName\":\""+WebportalParam.ap2deveiceName+"\",\"devicePlateform\":\"WEB\",\"deviceType\":\"AP\",\"imeiNo\":\"string\",\"macAddress\":\""+WebportalParam.ap2macaddress+"\",\"model\":\""+WebportalParam.ap2Model+"\",\"profileId\":\"string\",\"profileSsid\":\"string\",\"profileSsidPwd\":\"string\",\"serialNo\":\""+WebportalParam.ap2serialNo+"\"}]}";
//        
//        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_Device_Pro"), requestBody, headers, pathParams2, null); 
//        getResponse.then().body("response.status", equalTo(true));
//        MyCommonAPIs.sleepi(1000);
//       
        
    }  
  
    @Step("Send get request to {url}")
    public void step1()
    { 
        headers.put("apikey",WebportalParam.apikey);
        headers.put("token",WebportalParam.tokenPro);
        headers.put("accountId",WebportalParam.accountIdPro);
        
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        
        Response response = new Api_AddOrganization().step1();
        OrgID = response.jsonPath().getString("orgInfo.orgId");
        
        Response response1 = new Api_AddLocationPro().step2(OrgID);
        LocID = response1.jsonPath().getString("networkInfo[0].networkId");
        
        String requestBody1="{\"srcNetworkId\":\""+WebportalParam.networkId+"\",\"srcOrgId\":\""+WebportalParam.orgId+"\",\"targetOrganizations\":[{\"networkList\":[\""+LocID+"\"],\"orgId\":\""+OrgID+"\"}]}"; 
        
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Clone_Network"), requestBody1, headers, null, null);         
               
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkIdPro);
        pathParams.put("page","0");        
        
        
        Response getResponse1 = ApiRequest.sendGetRequest(endPointUrl.get("Get_Device"), headers, pathParams, null);
        getResponse1.then().body("response.status", equalTo(true));
        
        deviceId=getResponse1.jsonPath().getString("deviceInfo[0].deviceId");
        System.out.print(deviceId);
    }
        
    @Step("Send get request to {url}")
    public void step2()
    { 
        Map<String, String> pathParams1 = new HashMap<String, String>();
        pathParams1.put("networkId",WebportalParam.networkIdPro);
        pathParams1.put("deviceId",deviceId);
        pathParams1.put("orgId",WebportalParam.orgId);
        
        String requestBody="{}";
        
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Move_Device"), requestBody, headers, pathParams1, null); 
        getResponse.then().body("response.status", equalTo(false))
                          .body("response.message", equalTo("Error in moving device"));

                         
    }
              
    }



