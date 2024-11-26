package webportal.ApiTest.BulkDeployment.PostiveTestcase;
import static org.hamcrest.CoreMatchers.equalTo;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Api_AddBulkDevices extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String,String>();
    Map<String, String> headers = new HashMap<String, String>();
    String  OrgID;
    String  LocID;
  
    
    
    @Feature("Api_GetBulkAddDevices") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T002") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Retrieves all the details related to Bulk deployment") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T002") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",OrgID);
        pathParams.put("accountId",WebportalParam.accountIdPro);
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }  
  
    @Step("Send get request to {url}")
    public void step1()
    {        
        Response response = new Api_AddOrganization().step1();
        OrgID = response.jsonPath().getString("orgInfo.orgId");
        
        Response response1 = new Api_AddLocationPro().step2(OrgID);
        LocID = response1.jsonPath().getString("networkInfo[0].networkId");
        
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountIdPro);        
        headers.put("networkId",WebportalParam.networkIdPro); 
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",OrgID);
        pathParams.put("isBulkAdd","true");
        
        String requestBody="{\"deviceList\":[{\"deviceName\":\"AP1\",\"devicePlatform\":\"WEB\",\"deviceType\":\"AP\",\"imeiNo\":\"string\",\"macAddress\":\"aa:bb:cc:dd:ee:ff\",\"model\":\"WAC505\",\"networkId\":\""+LocID+"\",\"networkName\":\"string\",\"profileId\":\"string\",\"profileName\":\"string\",\"serialNo\":\"4XT2424344234\"},{\"deviceName\":\"AP2\",\"devicePlatform\":\"WEB\",\"deviceType\":\"AP\",\"imeiNo\":\"string\",\"macAddress\":\"aa:bb:cc:dd:ee:ff\",\"model\":\"WAC505\",\"networkId\":\""+LocID+"\",\"networkName\":\"string\",\"profileId\":\"string\",\"profileName\":\"string\",\"serialNo\":\"4XT3545354345\"}]}";

        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_BulkDevices"),requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
                           .body("response.message", equalTo("success"));

        
    }
                  
    }

