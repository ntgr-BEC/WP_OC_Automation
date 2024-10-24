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
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;


public class Api_SetWirelessSettings extends TestCaseBaseApi{
    
    Map<String, String> endPointUrl = new HashMap<String,String>();
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
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
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }
    @Step("Send get request to {url}")
    public void step1()
    {
        Response add = new Api_AddNetwork().step1();
        networkId=add.jsonPath().getString("networkInfo[0].networkId");
        
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);        
        
        pathParams.put("networkId",networkId);
        String requestBody = "{\"rfSettings\":{\"bandWlan0\":{\"autoChannelStatus\":null,\"autoPowerStatus\":null,\"beaconInterval\":\"100\",\"channelName\":null,\"channelWidth\":\"0\",\"dtimInterval\":\"3\",\"guardInterval\":null,\"isChannelAuto\":null,\"isTxPwrAuto\":null,\"maxRateLimit\":\"64\",\"mcsRate\":null,\"operateMode\":\"11be\",\"radioStatus\":\"1\",\"rateLimitStatus\":\"1\",\"rrmCh1\":null,\"rrmCh2\":null,\"rrmCh3\":null,\"selectedChannels\":null,\"txPower\":null},\"bandWlan1\":{\"autoChannelStatus\":null,\"autoPowerStatus\":null,\"beaconInterval\":\"100\",\"channelName\":null,\"channelWidth\":\"1\",\"dtimInterval\":\"3\",\"guardInterval\":null,\"isChannelAuto\":null,\"isTxPwrAuto\":null,\"maxRateLimit\":\"64\",\"mcsRate\":null,\"operateMode\":\"11be\",\"radioStatus\":\"1\",\"rateLimitStatus\":\"1\",\"rrmCh1\":null,\"rrmCh2\":null,\"rrmCh3\":null,\"selectedChannels\":null,\"txPower\":null},\"bandWlan2\":{\"autoChannelStatus\":null,\"autoPowerStatus\":null,\"beaconInterval\":\"100\",\"channelName\":null,\"channelWidth\":\"2\",\"dtimInterval\":\"3\",\"guardInterval\":null,\"isChannelAuto\":null,\"isTxPwrAuto\":null,\"maxRateLimit\":\"64\",\"mcsRate\":null,\"operateMode\":\"11be\",\"radioStatus\":\"1\",\"rateLimitStatus\":\"1\",\"rrmCh1\":null,\"rrmCh2\":null,\"rrmCh3\":null,\"selectedChannels\":null,\"txPower\":null}}}";

        
        //TO PERFORM ANY REQUEST
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Wireless_Settings"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        
                
    }

}
