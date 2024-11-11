package webportal.ApiTest.Organizations.PositiveTestcases.Organization;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.List;
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
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_UpdateOrganization extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String orgId;
    
    Random random = new Random();
    int randomNumber = random.nextInt(1000);
    String orgupdate    = "Orgupdate" + String.valueOf(randomNumber);
    String updateowner    = "ownerupdate" + String.valueOf(randomNumber);
    
    @Feature("Api_UpdateOrganization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T002") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Update organization") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T002") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    
    public void teardown()
    { 
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",orgId);
        pathParams.put("accountId",WebportalParam.accountId);
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }  
    
  
    @Step("Send get request to {url}")
    public Response step1()
    {     
            Response response = new Api_AddOrganization().step1();
            endPointUrl = new ApiRequest().ENDPOINT_URL;          
            headers.put("apikey",WebportalParam.apikey);
            headers.put("token",WebportalParam.token);
            orgId = response.jsonPath().getString("orgInfo.orgId");
                                   
            Map<String, String> pathParams = new HashMap<String, String>();
            pathParams.put("accountId",WebportalParam.accountId);
            pathParams.put("orgId",orgId);
           
            String requestBody1="{\"orgInfo\":{\"orgName\":\""+orgupdate+"\",\"ownerName\":\"Netgear1 Devices\",\"ownerEmail\":\""+updateowner+"@yopmail.com\",\"persPhnNo\":\"\",\"busiPhnNo\":\"\",\"emailRecipient\":[\"1\",\"2\"],\"pushRecipient\":[\"1\",\"2\"],\"deviceOwnership\":\"1\",\"repRecipient\":[\"1\",\"2\"],\"isSchedule\":\"1\",\"frequency\":\"1\",\"applyToAllOrg\":\"0\"}}";       
            
            Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Update_Organization"), requestBody1, headers, pathParams, null); 
            getResponse.then().body("response.status", equalTo(true));
            orgId=getResponse.jsonPath().getString("orgInfo.orgId");
            System.out.println("Org ID under response"+ orgId);
            return getResponse;
                         
        }
                
    }



