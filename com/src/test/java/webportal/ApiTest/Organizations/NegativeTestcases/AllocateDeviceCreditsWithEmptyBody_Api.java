package webportal.ApiTest.Organizations.NegativeTestcases;
import static org.hamcrest.CoreMatchers.equalTo;

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


public class AllocateDeviceCreditsWithEmptyBody_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String OrgID;
    int DC= 2;
    int ICPC= 2;
    Response response1;
    

    
    @Feature("AllocateDeviceCreditsWithEmptyBody_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Allocate device credits to organization with empty body") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2(DC, ICPC, OrgID);
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
        response1 = new Api_AddOrganization().step1();
        OrgID = response1.jsonPath().getString("orgInfo.orgId");
    }
  
    @Step("Send get request to {url}")
    public void step2(int DC, int ICPC, String OrgID)
    {     
        
        headers.put("token",WebportalParam.token);
        endPointUrl = new ApiRequest().ENDPOINT_URL;        
        System.out.println(OrgID);
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountIdPro);
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId", OrgID);
        
        String requestBody1="{}";       
        
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("AllocateCredits"), requestBody1, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(false))
          .body("response.message", equalTo(""));
        
    }



}