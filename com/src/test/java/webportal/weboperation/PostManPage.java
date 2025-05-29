package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;
import org.apache.http.HttpResponse;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;

import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.SSIDData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessQuickViewElement;
import java.util.Random;
import org.openqa.selenium.interactions.Actions;
import util.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class PostManPage extends MyCommonAPIs {

    public PostManPage() {
        logger.info("into Postman...");
    }

    public void Deregisterold(String SLNo) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the HTTP POST request to deregister AP
            //String accessToken = getRefreshToken(accessToken, accessToken);
            System.out.println("accessToken: ");
            HttpPost postRequest = new HttpPost("https://ocapi-qa.netgear.com/api/v2/ocProductRegisterDeactivate/?accessToken=eyJraWQiOiJieWRpUGM2ZFdZSVRKQm1KMyt2YzJFNVNqVjFRVTNMQ3hcL2htTndldGlSaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMjE1NDQzNC1hMGIxLTcwNzAtZDY2OS1kMzg3ZWM2Zjg0MzciLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtd2VzdC0xLmFtYXpvbmF3cy5jb21cL2V1LXdlc3QtMV9hVG1RNzJhVXYiLCJjbGllbnRfaWQiOiI0NTRmOWxmZWtkMjQwcHUxa2RmcHF0aDlmZyIsIm9yaWdpbl9qdGkiOiI3NWMyODMyOC05NzZlLTQyODUtYjA2MS1mY2FjM2MzYmQ4MmYiLCJldmVudF9pZCI6IjA3ZTczYWJiLTQyYzUtNDE2Ny1hNWM4LTBjYjZmYmE3ZjI1MiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3NDU5MDMwMTMsImV4cCI6MTc0NTkwMzk3OCwiaWF0IjoxNzQ1OTAzMDc4LCJqdGkiOiJhZDM3OWE5MS02YTA4LTQ0NTEtYjliOC1hMmViN2NjYzJhYmMiLCJ1c2VybmFtZSI6ImEyMTU0NDM0LWEwYjEtNzA3MC1kNjY5LWQzODdlYzZmODQzNyJ9.fK7QGBxqBrHEsmNLKkf88OQcqf8S2L8hDzOn1jnGa7cyYDsRbQFbCKu4RcAs_mE1D6zgv-CCpaznJycmMHlL_trfM-VXQyr_WqqiTICGNIC5nhqXcHYlFLtSt4bRRN8v6b-1cD3khkLbYa9rnwOjs5CdA2YeBACfzZysgGNvhW2USZj9zHwWEpUlsDtYso2fAxYRPEYuSHEkT0-AMNNPgUlAZKUVdHSbsaVgoe99CSWOKYiLAw_nxbk_FHFOGIEyo5VmN6ETauOKyCjdPaXhqFARfkTmovA3Fi6RcBP71yURtnt0JusejcOTantcvwrji7y7-piussxhumKqwDgOEA");

            // Add headers
            postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            postRequest.setHeader("x-dreamfactory-api-key", "175208f843081dc7d9addad1e372c51c04c4c3dd54834a500d85955f71742a34");

            // Create form parameters
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("serialNumber", SLNo));

            // Set the request entity
            postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

            // Execute the request
            try (org.apache.hc.client5.http.impl.classic.CloseableHttpResponse response = httpClient.execute(postRequest)) {
                // Print the response status
                System.out.println("Response Status: " + response.getCode());

                // Print the response body
                String responseBody = "";
                try {
                    responseBody = EntityUtils.toString(response.getEntity());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Response Body: " + responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private static final String CLIENT_ID = "3329e9jbjhg6ecv365afgp709b";
    private static final String USERNAME = "sumanta.jena@netgear.com";
    private static final String PASSWORD = "!@Automation@1234"; // Replace with actual
    private static final String API_KEY = "175208f843081dc7d9addad1e372c51c04c4c3dd54834a500d85955f71742a34";

    public static void Deregister(String serialNumber) {
        try {
            String refreshToken = getRefreshToken();
            String accessToken = getAccessToken(refreshToken);
            performDeregister(serialNumber, accessToken);
        } catch (Exception e) {
            System.err.println("Failed to deregister device " + serialNumber + ": " + e.getMessage());
        }
    }

    private static String getRefreshToken() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String json = "{\n" +
                "  \"AuthParameters\": {\n" +
                "    \"USERNAME\": \"" + USERNAME + "\",\n" +
                "    \"PASSWORD\": \"" + PASSWORD + "\"\n" +
                "  },\n" +
                "  \"AuthFlow\": \"USER_PASSWORD_AUTH\",\n" +
                "  \"ClientId\": \"" + CLIENT_ID + "\"\n" +
                "}";

        RequestBody body = RequestBody.create(MediaType.parse("application/x-amz-json-1.1"), json);
        Request request = new Request.Builder()
                .url("https://cognito-idp.us-east-1.amazonaws.com")
                .post(body)
                .addHeader("Content-Type", "application/x-amz-json-1.1")
                .addHeader("X-Amz-Target", "AWSCognitoIdentityProviderService.InitiateAuth")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            return new JSONObject(res).getJSONObject("AuthenticationResult").getString("RefreshToken");
        }
    }

    private static String getAccessToken(String refreshToken) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://accounts-qa.netgear.com/api/getAccessToken")
                .get()
                .addHeader("X-Amz-Target", "AWSCognitoIdentityProviderService.InitiateAuth")
                .addHeader("Content-Type", "application/json")
                .addHeader("appkey", CLIENT_ID)
                .addHeader("Authorization", "Bearer " + refreshToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            return new JSONObject(res).getJSONObject("data").getString("accessToken");
        }
    }

    private static void performDeregister(String serialNumber, String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String bodyJson = "{ \"serialNumber\": \"" + serialNumber + "\" }";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), bodyJson);

        Request request = new Request.Builder()
                .url("https://ocapi-qa.netgear.com/api/v2/ocProductRegisterDeactivate/?accessToken=" + accessToken)
                .post(body)
                .addHeader("x-dreamfactory-api-key", API_KEY)
                .addHeader("Content-Type", "text/plain")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Deregister response: " + response.code());
            System.out.println(response.body().string());
        }
    } 

    }
    
