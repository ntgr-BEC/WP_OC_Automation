package webportal.ApiTest.Organizations.PositiveTestcases.Location;
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
import webportal.ApiTest.Organizations.PositiveTestcases.Organization.Api_AddOrganization;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_GetListOfNetwork extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    ArrayList<String> LocID = new ArrayList<String>();
    ArrayList<String> LocIDgetList = new ArrayList<String>();
    
    String OrgID;

    
    @Feature("API_Organizations_PositiveTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Get list of network(s) by organization identifier.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",  OrgID);
        pathParams.put("accountId",WebportalParam.accountId);
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }  
  
    @Step("Send get request to {url}")
    public void step1()
    { 
       
        Response response = new Api_AddOrganization().step1();
        OrgID = response.jsonPath().getString("orgInfo.orgId");   
                         
    }
    
    
    @Step("Send get request to {url}")
    public void step2()
    { 
       
        Response response = new Api_AddLocationPro().step2(OrgID);
        Response response1 = new Api_AddLocationPro().step2(OrgID);
        
        
        LocID.add(response.jsonPath().getString("networkInfo[0].networkId"));
        LocID.add(response1.jsonPath().getString("networkInfo[0].networkId"));
        
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        
        headers.put("apikey",WebportalParam.apikey);
        headers.put("token",WebportalParam.token);
        
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("accountId",WebportalParam.accountId);
        pathParams.put("orgId",OrgID);
        
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Get_Network_Pro"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        
        LocIDgetList.add(getResponse.jsonPath().getString("details[0].networkId"));
        LocIDgetList.add(getResponse.jsonPath().getString("details[1].networkId"));
        
        System.out.println(LocIDgetList);
        System.out.println(LocID);
        System.out.println("output");
        boolean areEqual = LocIDgetList.equals(LocID);
        assertTrue(areEqual, "Get List is Not sucessfull");
     }
                
    }



