package webportal.ApiTest.Organizations.PositiveTestcases.Organization;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.uwyn.jhighlight.fastutil.Arrays;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_GetCreditAllocationAccount extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String OrgID; 
    String OrgID1; 
    int DC= 3;
    int ICPC= 1;

    
    @Feature("API_Organizations_PositiveTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Get credit allocation details for an account.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
//        
//        Map<String, String> pathParams = new HashMap<String, String>();
//        pathParams.put("orgId",OrgID);
//        pathParams.put("accountId",WebportalParam.accountId);
//        
//        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
//        getResponse1.then().body("response.status", equalTo(true));
//        
//        
//        pathParams.put("orgId",OrgID1);
//        pathParams.put("accountId",WebportalParam.accountId);
//        
//        
//        pathParams.put("orgId",OrgID1);
//        pathParams.put("accountId",WebportalParam.accountId);
//        
//        Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
//        getResponse1.then().body("response.status", equalTo(true));
//        
//        
        
        
    }  
    
  
    @Step("Send get request to {url}")
    public void step1()
    { 
//        Response response = new Api_AddOrganization().step1();
//        Response response1 = new Api_AddOrganization().step1();
//        OrgID = response.jsonPath().getString("orgInfo.orgId");
//        OrgID1 = response.jsonPath().getString("orgInfo.orgId");
//        
//        Response response2 = new Api_AllocateDeviceCredits().step2(DC, ICPC, OrgID );
//        Response response3 = new Api_AllocateDeviceCredits().step2(DC, ICPC, OrgID1 );
        
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("startFrom", "1");
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("searchText", "Org453");
        
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("GetAllocateCredits"), headers, pathParams, queryParams); 
        getResponse.then().body("response.status", equalTo(true));
                         
        }
                
    }



