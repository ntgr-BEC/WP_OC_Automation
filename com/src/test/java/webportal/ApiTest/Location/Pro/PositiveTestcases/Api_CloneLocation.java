package webportal.ApiTest.Location.Pro.PositiveTestcases;
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
import webportal.ApiTest.Organizations.PositiveTestcases.Api_AddOrganization;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_CloneLocation extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String OrgID;
    String LocID;
    String OrgIDcopy;
    String LocIDcopy;


    
    @Feature("Api_CloneLocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Cloning the location in a pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();

    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",  OrgID);
        pathParams.put("accountId",WebportalParam.accountId);
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
        pathParams.put("orgId",  OrgIDcopy);
        Response getResponse2 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
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
        
        headers.put("apikey",WebportalParam.apikey);
        headers.put("token",WebportalParam.token);
        headers.put("accountId",WebportalParam.accountId);
        
        Response response2 = new Api_AddOrganization().step1();
         OrgIDcopy = response2.jsonPath().getString("orgInfo.orgId");
        
        Response response3 = new Api_AddLocationPro().step2(OrgID);
        LocIDcopy = response3.jsonPath().getString("networkInfo[0].networkId");
        
        
        String requestBody1="{\"srcNetworkId\":\""+LocID+"\",\"srcOrgId\":\""+OrgID+"\",\"targetOrganizations\":[{\"networkList\":[\""+LocIDcopy+"\"],\"orgId\":\""+OrgIDcopy+"\"}]}"; 
        
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Clone_Network"), requestBody1, headers, null, null); 
        getResponse.then().body("response.status", equalTo(true));
        getResponse.then().body("response.message", equalTo("Success in cloning network details."));
                         
    }
    
    
    
                
    }



