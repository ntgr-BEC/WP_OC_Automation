/**
 * 
 */
package webortal.com.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

/**
 * @author zheli
 *
 */
public class JiraParam {
    public static String username = "";
    public static String password = "";
    public static String server = "";
    public static String useTestRun = "";
    public static List<String> testrun = new ArrayList();
    public static String RunCaseStatus = "";
    public static String testPlan = "";
    public static String configBy = "";
    public static String BrowserType = "";
    public static String ImportResult = "";
    public static String ipAddress="C:";
    public static String useIP="";

    public JiraParam() {
        XMLManager xmlManager = new XMLManager();
        username = xmlManager.getValueFromConfigJiraXml("//User_Name");
        password = xmlManager.getValueFromConfigJiraXml("//Password");
        server = xmlManager.getValueFromConfigJiraXml("//JIRA_Info/JIRA_Server");
        testPlan = xmlManager.getValueFromConfigJiraXml("//JIRA_TestPlan");
        useTestRun = xmlManager.getValueFromConfigJiraXml("//useTestRun");
        configBy = xmlManager.getValueFromConfigPortXml("//ConfigBy");
        BrowserType = xmlManager.getValueFromConfigPortXml("//BrowserType");
        useIP= xmlManager.getValueFromConfigJiraXml("//RunningPC");
        if (useIP.contains(".")) {
            ipAddress="\\\\"+useIP;
        }
        if (useTestRun.toLowerCase().equals("true")) {
            testrun = xmlManager.getTestRunFromConfigJiraXml("//JIRA_TestRun");
        } else {
            String APIurl = server + "rest/atm/1.0/testplan/" + testPlan;
            JiraAPI jiraAPI = new JiraAPI();
            JSONObject jsonObject = jiraAPI.jiraGetAPI(APIurl);
            JSONArray testRuns = jsonObject.getJSONArray("testRuns");
            for (int index = 0; index < testRuns.size(); index++) {
                String testCaseKey = JSONPath.eval(jsonObject, "$.testRuns.key[" + index + "]").toString();
                testrun.add(testCaseKey);
            }
        }
        RunCaseStatus = xmlManager.getValueFromConfigJiraXml("//Case_Status");
        ImportResult= xmlManager.getValueFromConfigJiraXml("//Import_Result");
        System.out.println("Read all  parameters from the config_jira.xml file");
    }
}
