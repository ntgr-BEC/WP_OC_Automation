package webportal.ApiTest.Location.Pro.NegativeTestcases;
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


public class AddDuplicateLocationPro_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    
    Random random = new Random();
    int randomNumber = random.nextInt(1000);
    String Loc    = "Loc" + String.valueOf(randomNumber);
    String OrgID;

    
    @Feature("AddDuplicateLocationPro_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add duplicate network to pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2(OrgID);
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
  
    @Step("Create Organization")
    public void step1()
    { 
              
        Response response = new Api_AddOrganization().step1();
        OrgID = response.jsonPath().getString("orgInfo.orgId");   
                         
        }
    
    @Step("Send get request to {url}")
    public void step2(String OrgID)
    { 
       
        endPointUrl = new ApiRequest().ENDPOINT_URL;     
        
        headers.put("apikey",WebportalParam.apikey);
        headers.put("token",WebportalParam.token);
               
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("accountId",WebportalParam.accountId);
        pathParams.put("orgId",OrgID);
        
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("orgId",OrgID);
        
        String requestBody="{\"networkInfo\":[{\"name\":\""+Loc+"\",\"adminPassword\":\"Netgear1@\",\"timeSettings\":{\"timeZone\":\"262\"},\"street\":\"\",\"city\":\"\",\"state\":\"\",\"postCode\":\"\",\"isoCountry\":\"US\"}]}\r\n" + 
                "";       
        
        Response getResponse1 = ApiRequest.sendPostRequest(endPointUrl.get("Add_Network_Pro"), requestBody, headers, pathParams, queryParams); 
        getResponse1.then().body("response.status", equalTo(true));
        
        Response getResponse2 = ApiRequest.sendPostRequest(endPointUrl.get("Add_Network_Pro"), requestBody, headers, pathParams, queryParams); 
        getResponse2.then().body("response.status", equalTo(false))
        .body("response.message", equalTo("Network already exists with the same name"));
        
        
        
                         
        }
                
    }



