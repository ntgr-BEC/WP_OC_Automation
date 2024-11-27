package webportal.ApiTest.Organizations.PositiveTestcases;
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
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_AllocateDeviceCredits extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String OrgID;
    int DC= 2;
    int ICPC= 2;
    Response response1;
    

    
    @Feature("Api_AddOrganization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Allocate device credits to organization") // It's a testcase title from Jira Test Case.
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
    public Response step2(int DC, int ICPC, String OrgID)
    {     
        
        headers.put("token",WebportalParam.token);
        endPointUrl = new ApiRequest().ENDPOINT_URL;        
        System.out.println(OrgID);
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountIdPro);
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId", OrgID);
        
        String requestBody1="{\r\n" + 
                "  \"bsVpnCredInfo\": {\r\n" + 
                "    \"creditCount\": 0,\r\n" + 
                "    \"unusedLicKeys\": [\r\n" + 
                "      {\r\n" + 
                "        \"credits\": 0,\r\n" + 
                "        \"key\": \"string\"\r\n" + 
                "      }\r\n" + 
                "    ]\r\n" + 
                "  },\r\n" + 
                "  \"cfCredInfo\": {\r\n" + 
                "    \"creditCount\": 0,\r\n" + 
                "    \"unusedLicKeys\": [\r\n" + 
                "      {\r\n" + 
                "        \"credits\": 0,\r\n" + 
                "        \"key\": \"string\"\r\n" + 
                "      }\r\n" + 
                "    ]\r\n" + 
                "  },\r\n" + 
                "  \"cfTopUpCredInfo\": {\r\n" + 
                "    \"creditCount\": 0,\r\n" + 
                "    \"unusedLicKeys\": [\r\n" + 
                "      {\r\n" + 
                "        \"credits\": 0,\r\n" + 
                "        \"key\": \"string\"\r\n" + 
                "      }\r\n" + 
                "    ]\r\n" + 
                "  },\r\n" + 
                "  \"devCredInfo\": {\r\n" + 
                "    \"creditCount\": "+DC+",\r\n" + 
                "    \"unusedLicKeys\": [\r\n" + 
                "      {\r\n" + 
                "        \"credits\": 0,\r\n" + 
                "        \"key\": \"string\"\r\n" + 
                "      }\r\n" + 
                "    ]\r\n" + 
                "  },\r\n" + 
                "  \"instantCpCredInfo\": {\r\n" + 
                "    \"creditCount\": "+ICPC+",\r\n" + 
                "    \"unusedLicKeys\": [\r\n" + 
                "      {\r\n" + 
                "        \"credits\": 0,\r\n" + 
                "        \"key\": \"string\"\r\n" + 
                "      }\r\n" + 
                "    ]\r\n" + 
                "  },\r\n" + 
                "  \"vpnCredInfo\": {\r\n" + 
                "    \"creditCount\": 0,\r\n" + 
                "    \"unusedLicKeys\": [\r\n" + 
                "      {\r\n" + 
                "        \"credits\": 0,\r\n" + 
                "        \"key\": \"string\"\r\n" + 
                "      }\r\n" + 
                "    ]\r\n" + 
                "  }\r\n" + 
                "}";       
        
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("AllocateCredits"), requestBody1, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        getResponse.then().body("response.message", equalTo("Success in setting organization allocation details."));
        getResponse.then().body("devAllocResp.message", equalTo("Allocation of device credit success in organization."));
        getResponse.then().body("instantCpAllocResp.message", equalTo("Allocation of instant captive portal credit success in organization."));     
        return getResponse;
    }



}