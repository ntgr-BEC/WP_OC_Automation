package webportal.NightlyBuild.PRJCBUGEN_T14270;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.google.inject.matcher.Matcher;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.FileHandling;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String Path = "C:\\jfrog";

    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14270") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("load firmware") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14270") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Load firmware and Login IM WP success;")
    public void step1()  {
        new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        new APUtils(WebportalParam.ap1IPaddress).changeAPtoLocal();
        MyCommonAPIs.sleepi(50);      
        new FileHandling().deleteAllExcept("tftpd32.exe", Path);

//         Define the command to run the Python script using Python interpreter
        String pythonExe = "C:\\Program Files\\Python38\\python.exe";  // or the full path to Python like "C:\\Python39\\python.exe"
        String command = "C:\\jfrog\\jfrog_fw_download.py";
        
        String downlaodstatement ="";
        String filename ="";
        // ProcessBuilder to run the Python script
        ProcessBuilder processBuilder = new ProcessBuilder(pythonExe, command);
        
        try {
            // Start the process
            Process process = processBuilder.start();
            
            // Read the output from the script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line="";
            while (( line = reader.readLine()) != null) {
                System.out.println(line); // Print output from the Python script
                downlaodstatement = line;
            }
            
            // Wait for the process to complete and capture exit code
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("line is "+downlaodstatement);
        Pattern pattern = Pattern.compile("Downloaded successfully: (.+)");
        java.util.regex.Matcher matcher = pattern.matcher(downlaodstatement);
        System.out.println("Matcher is "+matcher);
        if (matcher.find()) {
             filename = matcher.group(1);
        }
        
        System.out.println(filename);
        new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        // Upgrade firmware with fetched file
        new APUtils(WebportalParam.ap1IPaddress).upgrageFirmware(filename);
        MyCommonAPIs.sleepi(500);
        new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        new APUtils(WebportalParam.ap1IPaddress).changeAPtoNetgear();
        MyCommonAPIs.sleepi(500);
        new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        // Log into the web portal
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();  // Whatever this is doing
    }
        
    
    
    @Step("Test Step 2: Onboard AP;")
    public void step2() throws IOException {
        
            new APUtils(WebportalParam.ap1IPaddress).Setserver( WebportalParam.ap1IPaddress);
    
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ap1serialNo);
            devInfo.put("Device Name", WebportalParam.ap1deveiceName);
            devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
            new DevicesDashPage().addNewDevice(devInfo);
        
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        
            assertTrue(new WirelessQuickViewPage().checkApIsOnline(WebportalParam.ap1serialNo));

    }
}