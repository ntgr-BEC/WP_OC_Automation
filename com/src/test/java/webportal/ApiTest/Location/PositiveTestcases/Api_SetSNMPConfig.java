package webportal.ApiTest.Location.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

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
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_SetSNMPConfig extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
  
    String networkId;
    
    
    @Feature("Api_SetSNMPConfig") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test set SNMP configuration from the particular API") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @Step("Send get request to {url}")
    public void step1()
    { 
        
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
      
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkId);
        pathParams.put("commandType", "1");    //The enumerations are 1 (SET_SNMP), 2(UPDATE_SNMP), 3(DELETE_SNMP)
        pathParams.put("accountId",WebportalParam.accountId);
        
        String requestBody = "{\r\n" + 
                "  \"communityId\": \"ABC@1234\",\r\n" + 
                "  \"ipAddress\": \"14.14.14.15\",\r\n" + 
                "  \"status\": \"1\",\r\n" +  
                "  \"ipMask\": \"255.255.255.255\",\r\n" + 
                "  \"accessType\": \"1\",\r\n" + 
                "  \"version\": \"3\"\r\n" + 
                "}";
        
        //TO PERFORM ANY REQUEST 
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Set_SNMP_Settings"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
        .body("response.message", equalTo("Success in setting SNMP Configuration."))
        .body("details[0].message", equalTo("Applying your configuration settings. This can take up to 3 minutes to display"))
        .body("details[0].serialNo", equalTo(WebportalParam.ap1deveiceName));
       
    }
                  
    }

