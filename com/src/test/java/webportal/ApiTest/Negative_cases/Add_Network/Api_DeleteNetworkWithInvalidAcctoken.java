package webportal.ApiTest.Negative_cases.Add_Network;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

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


public class Api_DeleteNetworkWithInvalidAcctoken extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
    @Feature("VLAN Listing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test retrieves VLAN details feom the Netgear APIs based on specific Network ID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",networkId);
        
        Response getResponse3 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), headers, pathParams, null); 
        getResponse3.then().body("response.status", equalTo(true));
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
       
            endPointUrl = new ApiRequest().ENDPOINT_URL;          
            headers.put("token",WebportalParam.token);
            headers.put("apikey",WebportalParam.apikey);
            headers.put("accountId",WebportalParam.accountId);        
            headers.put("networkId",networkId);
            
            Map<String, String> pathParamsadd = new HashMap<String, String>();
            pathParamsadd.put("accountId",WebportalParam.accountId);
            String requestBody1="{\"networkInfo\":[{\"name\":\"San Jose\",\"adminPassword\":\"Test@1234\",\"timeSettings\":{\"timeZone\":\"262\"},\"street\":\"\",\"city\":\"\",\"state\":\"\",\"postCode\":\"\",\"isoCountry\":\"US\"}]}";       
            
            
            //TO ADD NETWORK AND RETRIEVE NETWORK ID
            Response getResponse1 = ApiRequest.sendPostRequest(endPointUrl.get("Add_Network"), requestBody1, headers, pathParamsadd, null); 
            getResponse1.then().body("response.status", equalTo(true));
            networkId=getResponse1.jsonPath().getString("networkInfo[0].networkId");
           
            Map<String, String> pathParams = new HashMap<String, String>();
            pathParams.put("networkId",networkId);
            
            Map<String, String> wrongHeaders = new HashMap<String, String>();
            wrongHeaders.put("token","eyJraWQiOiJvc2FqOVNyemR1SWgrUTFRa3NqcTZpZU9EU25PYXFyQnY5ekFVV3pkT2lNPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJkNGM4OTQwOC1iMDgxLTcwMTAtNWUzMy01MDljNzE5YmY4MjgiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9KYUdVeVB1Y3MiLCJjbGllbnRfaWQiOiI3amI5MmNuZm9iMTFpOTVvdDQ4bmdqZXNlOCIsIm9yaWdpbl9qdGkiOiJkOGQ5NTMxZS00NzNmLTQ3NmYtODRmNS1iYzUxYmQ2N2E3YTAiLCJldmVudF9pZCI6IjA1MmU2MGZkLTEzZTEtNDFiZi1hMjFkLWM1ODNjODIyYWViOCIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3MjgyODAyMDMsImV4cCI6MTcyODI4MjY2NSwiaWF0IjoxNzI4MjgxNzY1LCJqdGkiOiI4MzhmNGIwMC1iZTQ1LTQxNmYtYTBiYi0yODVhOTEwMzQ4MjgiLCJ1c2VybmFtZSI6ImQ0Yzg5NDA4LWIwODEtNzAxMC01ZTMzLTUwOWM3MTliZjgyOCJ9.PCXgcKgjeQtGR8ky_E4YGzBnC8paA4RcNozPJOdaHfCitTgLBCk1Wl75Z2Qccs_TROM9Rmv2Y7YSsbrXIZSkDb9kF3bxFMCB0nWlKULAQuLGZE2nFcnlVX-BVLgonYVr5uIzZBlYhpCr74zECrMbYhnzou_YpfqgGbkwNjy0tPR_tEFVae-nXZGXeZ7aTotlWxBeQvi6QRri1eh4xyz_Uvjdy1WzLxf3p-byZsjsvHjLPM_Rfg7NhqnJVmIDPnrZ9BhqsCfwcZ5tmU_ynE0Fn1-WVsei13Sgobv1Dd28YTGf17kxdsoe_maB1_cq8Kp3MTl5nnmtkVzGHbIN2ILFaQ");
            wrongHeaders.put("apikey",WebportalParam.apikey);
            wrongHeaders.put("accountId",WebportalParam.accountId);        
            wrongHeaders.put("networkId",networkId);
       
            
            //TO PERFORM ANY REQUEST
            Response getResponse2 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), wrongHeaders, pathParams, null, 200); 
                                     getResponse2.then().body("response.status", equalTo(false))
                                     .body("response.message",equalTo("Invalid session"));
                               
        }
        

        
        
    }



