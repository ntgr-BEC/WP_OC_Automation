package webportal.ApiTest.BulkDeployment.NegativeTestcases;
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
import webportal.ApiTest.Organizations.PositiveTestcases.Api_AddOrganization;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GetMeshInfoWithEmptyBody_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String,String>();
    Map<String, String> headers = new HashMap<String, String>();

    
    @Feature("GetMeshInfoWithEmptyBody_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T002") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Retrieves all the details related to Mesh with empty body") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T002") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }

    @Step("Send get request to {url}")
    public void step1()
    {        

        endPointUrl = new ApiRequest().ENDPOINT_URL;
        
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountIdPro);        
        headers.put("networkId",WebportalParam.networkIdPro); 
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",WebportalParam.orgId);
        
        String requestBody="{}";

        Response getResponse2 = ApiRequest.sendPostRequest(endPointUrl.get("Get_MeshInfo"),requestBody, headers, pathParams, null); 
        getResponse2.then().body("response.status", equalTo(false))
                           .body("response.message", equalTo("Error occured while fetching device mesh info"));

        
    }
                  
    }

