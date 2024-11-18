
package webportal.ApiTest.Devices.PositiveTestcases;

import static org.hamcrest.CoreMatchers.equalTo;
import testbase.TestCaseBaseApi;
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

//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;


public class Api_RebootDevice extends TestCaseBaseApi{
    WebportalParam webportalParam = new WebportalParam();
    
    @Feature("RebootDevice") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This API REBOOTS THE DEVICE") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
        Map<String, String> endPointUrl = new HashMap<String, String>();
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("deviceType","AP");    
        pathParams.put("serialNo",WebportalParam.ap1deveiceName);
      
        String requestBody="{\"deviceInfo\":{\"command\":{\"type\":0,\"reboot\":1}}} or {\"deviceInfo\":{\"command\":{\"type\":1,\"hardFactoryReset\":1}}}";
  
        //TO PERFORM ANY REQUEST

        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Reboot_Device1"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
                          .body("response.message", equalTo("\"Your configuration has been applied. It may take some time to reflect."));

    }
                  
    }

