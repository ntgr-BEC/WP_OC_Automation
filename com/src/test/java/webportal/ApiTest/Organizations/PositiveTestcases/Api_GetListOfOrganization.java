package webportal.ApiTest.Organizations.PositiveTestcases;
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


public class Api_GetListOfOrganization extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    ArrayList<String> OrgID = new ArrayList<String>();
    ArrayList<String> OrgIDgetList = new ArrayList<String>();
    String orgId1;
    String orgId2;
    String orgId3;
    
    @Feature("Api_GetListOfOrganization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Get list of organization(s) based on the account identifier.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        
        System.out.println("Start to tare down");
        Map<String, String> pathParams = new HashMap<String, String>();
        
        pathParams.put("accountId",WebportalParam.accountIdPro);  
        
        for(int i=0; i<OrgID.size()-1; i++) {
            pathParams.put("orgId",OrgID.get(i));
            Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
            getResponse1.then().body("response.status", equalTo(true));
        }
        
    }   
    
  
    @Step("Send get request to {url}")
    public void step1()
    { 
            Response response1 = new Api_AddOrganization().step1();
            Response response2 = new Api_AddOrganization().step1();
            Response response3 = new Api_AddOrganization().step1();
            
            OrgID.add(response1.jsonPath().getString("orgInfo.orgId"));
            OrgID.add(response2.jsonPath().getString("orgInfo.orgId"));
            OrgID.add(response3.jsonPath().getString("orgInfo.orgId"));
            OrgID.add(WebportalParam.orgId);
            
            endPointUrl = new ApiRequest().ENDPOINT_URL;          
            headers.put("token",WebportalParam.tokenPro);
            headers.put("apikey",WebportalParam.apikey);
            headers.put("accountId",WebportalParam.accountIdPro);
                                   
            Map<String, String> pathParams = new HashMap<String, String>();
            pathParams.put("accountId",WebportalParam.accountIdPro);
                       
            Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Get_Organization_List"), headers, pathParams, null); 
            getResponse.then().body("response.status", equalTo(true));
            OrgIDgetList.add(getResponse.jsonPath().getString("details[0].orgId"));
            OrgIDgetList.add(getResponse.jsonPath().getString("details[1].orgId"));
            OrgIDgetList.add(getResponse.jsonPath().getString("details[2].orgId"));
            OrgIDgetList.add(getResponse.jsonPath().getString("details[3].orgId"));
        
            boolean areEqual = OrgIDgetList.containsAll(OrgID);
            assertTrue(areEqual, "Get List is Not sucessfull");

                         
        }
                
    }



