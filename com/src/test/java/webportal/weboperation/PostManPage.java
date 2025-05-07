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

public class PostManPage extends MyCommonAPIs {

    public PostManPage() {
        logger.info("into Postman...");
    }

    public void Deregister(String SLNo) {

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
    
    public static String getRefreshToken() throws Exception {
        String url = "https://cognito-idp.eu-west-1.amazonaws.com/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-amz-json-1.1");
        con.setRequestProperty("X-Amz-Target", "AWSCognitoIdentityProviderService.InitiateAuth");

        // Request body
        String jsonInputString = "{\n" +
                "   \"AuthParameters\" : {\n" +
                "      \"USERNAME\" : \"sumanta.jena@netgear.com\",\n" +
                "      \"PASSWORD\" : \"!@Automation@1234\"\n" +
                "   },\n" +
                "   \"AuthFlow\" : \"USER_PASSWORD_AUTH\",\n" +
                "   \"ClientId\" : \"454f9lfekd240pu1kdfpqth9fg\"\n" +
                "}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonInputString);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse JSON
        JSONObject jsonResponse = new JSONObject(response.toString());
        String refreshToken = jsonResponse.getJSONObject("AuthenticationResult").getString("RefreshToken");

        return refreshToken;
    }
    

    public static String getAccessToken() {
        String accessToken = "";
        HttpURLConnection con = null;
        try {
            String url = "https://accounts2-stg.netgear.com/api/getAccessToken";
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            // Set headers
            con.setRequestProperty("X-Amz-Target", "AWSCognitoIdentityProviderService.InitiateAuth");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("appkey", "454f9lfekd240pu1kdfpqth9fg");

            // Use latest refresh token or valid one
            String bearerToken = getRefreshToken();
            con.setRequestProperty("Authorization", "Bearer " + bearerToken);

            // Enable output for POST
            con.setDoOutput(true);

            // Send empty JSON body if expected
            String jsonInputString = "{}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    JSONObject jsonResponse = new JSONObject(response.toString());

                    // Ensure key name matches actual response format
                    if (jsonResponse.has("accessToken")) {
                        accessToken = jsonResponse.getString("accessToken");
                    } else {
                        System.err.println("Access token key not found in response.");
                    }
                }
            } else {
                System.err.println("Failed to get access token. HTTP error code: " + responseCode);
                try (BufferedReader err = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String inputLine;
                    while ((inputLine = err.readLine()) != null) {
                        errorResponse.append(inputLine);
                    }
                    System.err.println("Error response body: " + errorResponse.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return accessToken;
    }

    }
    
