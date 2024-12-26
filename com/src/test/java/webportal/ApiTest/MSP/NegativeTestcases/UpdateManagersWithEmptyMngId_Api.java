package webportal.ApiTest.MSP.NegativeTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
import webportal.ApiTest.MSP.PositiveTestcases.Api_GetManagers;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class UpdateManagersWithEmptyMngId_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String managerId;
    String orgId;
    
    
    @Feature("UpdateManagersWithEmptyMngId_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test updates the manager account with empty manager id details to a pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod
    public void teardown()
    {
        Map<String, String> pathParams1 = new HashMap<String, String>(); 
        pathParams1.put("managerId",managerId);
       
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Manager_Sanity"), headers, pathParams1, null); 
        getResponse1.then().body("response.status", equalTo(true));
        
        Map<String, String> pathParams2 = new HashMap<String, String>();
        pathParams2.put("accountId",WebportalParam.accountIdPro);
        pathParams2.put("orgId",orgId);

         Response getResponse2 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams2, null); 
         getResponse2.then().body("response.status", equalTo(true));
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
        List<Response> response = new Api_GetManagers().step1();
        managerId=response.get(1).jsonPath().getString("details[0]._id");
        orgId=response.get(0).jsonPath().getString("orgInfo.orgId");
        
        endPointUrl = new ApiRequest().ENDPOINT_URL;   
        
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountIdPro);
        
        pathParams.put("managerId"," ");
        
        String requestBody="{\"accessType\":\"1\",\"applyToAll\":false,\"managerOrgInfoList\":[{\"orgId\":\""+orgId+"\"}]}";
        
        //TO PERFORM ANY REQUEST 
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Manager_Sanity"),requestBody,headers, pathParams, null,400); 
        getResponse.then().body("response.status", equalTo(false))
        .body("response.message", equalTo("Some error occured while updating manager info"));
        
      
    }
                  
    }

