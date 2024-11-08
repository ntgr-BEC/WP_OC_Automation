package webportal.ApiTest.Wired.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import webportal.ApiTest.Devices.PositiveTestcases.Api_GetDevices;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_GetVlanMembers extends TestCaseBaseApi{

    String networkId;
    String deviceId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    

    
    @Feature("Api_GetVlanMembers") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test Sets VLAN Mac Acl") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();

    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    {  

       Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("Vlan_Info"), headers, pathParams, null); 
       getResponse.then().body("response.status", equalTo(true));
    }
    

   @Step("Create a Vlan in the existing network and setting Vlan Members")
   public void step1() throws Exception
   {
        endPointUrl = new ApiRequest().ENDPOINT_URL;
       
        new Api_SetVlanMembers().test();

       

       pathParams.put("networkId",WebportalParam.networkId);
       pathParams.put("vlanId","250");
       
       
       headers.put("token",WebportalParam.token);
       headers.put("apikey",WebportalParam.apikey);
       headers.put("accountId",WebportalParam.accountId);     
      

       Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Get_VlanMembers"), headers, pathParams, null); 
       getResponse.then().body("response.status", equalTo(true));
   }
    
    
    
  
}
