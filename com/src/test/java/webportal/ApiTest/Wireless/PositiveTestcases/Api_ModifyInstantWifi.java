package webportal.ApiTest.Wireless.PositiveTestcases;
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
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_ModifyInstantWifi extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
    @Feature("Api_GetInstantWifi") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test modify Instat WIFI config from the particular network ID") // It's a testcase title from Jira Test Case.
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
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Response add = new Api_AddNetwork().step1();
        networkId=add.jsonPath().getString("networkInfo[0].networkId");    
           
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
       
        pathParams.put("networkId",networkId);
        pathParams.put("requestType","instantWifiLogs");    //requestType can be selectchannels/autoChannel/autoPower/instantWifiLogs
        String requestBody = "{\"nwRfSettings\":{\"isEnable\":\"1\",\"isExcludeDFS\":\"0\",\"defaultTxPower\":\"1\",\"isInsWifiLogsEnabled\":\"1\"}} or {\"nwRfSettings\":{\"selectedChannelsWlan0\":[\"1\",\"4\",\"8\",\"11\"],\"selectedChannelsWlan1\":[\"36\",\"40\",\"44\",\"48\",\"56\",\"60\",\"64\"],\"selectedChannelsWlan2\":[\"100\",\"104\",\"108\",\"112\",\"116\",\"120\",\"124\",\"128\",\"132\",\"136\",\"140\",\"149\",\"153\",\"157\",\"161\",\"165\"],\"selectedChannelsWlan2_6\":[\"5\",\"21\",\"25\",\"37\",\"53\",\"69\",\"85\",\"101\",\"113\",\"117\",\"133\",\"149\",\"165\",\"181\",\"197\",\"213\",\"229\"]}}";
        
        //TO PERFORM ANY REQUEST 
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Modify_InstantWIFI"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        
    }
                  
    }

