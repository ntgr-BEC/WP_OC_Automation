/**
 * 
 */
package webortal.com;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;

import webortal.com.util.JiraAPI;
import webortal.com.util.JiraParam;

/**
 * @author zheli
 *
 */
public class ImportJiraResultForQTP {
    public static void main(String[] args) throws IOException, ParseException {

        JiraParam jiraParam = new JiraParam();
        if (jiraParam.ImportResult.equals("True")) {
            String environment = jiraParam.BrowserType;
            if (environment.equals("iexplore")) {
                environment = "IE 11";
            } else if (environment.equals("chrome")) {
                environment = "Chrome Browser";
            } else {
                environment = "Firefox Browser";
            }
            // String excelString = "GC728XP v8.1.20.28-TS-GUI-FUNC-ARP-20171204.xlsx";
            String excelString = args[0];
            File excelFile = null;
            if (excelString.contains("C:") || excelString.contains("c:")) {
                excelFile = new File(excelString);
            } else {
                excelFile = new File("C:\\AUTOMATION\\TESTSUITE\\TestReport\\" + excelString);
            }
            String[] folder1 = excelString.split("-FUNC-");
            String[] folder2 = folder1[1].split("-201");
            String folderName = folder2[0];
            System.out.println(folderName);
            ExportJiraCaeForQTP exportJiraCaeForQTP = new ExportJiraCaeForQTP();
            Workbook workBook = exportJiraCaeForQTP.getWorkbok(excelFile);
            Sheet sheet = workBook.getSheetAt(0);
            for (int rownum = 2; rownum <= sheet.getLastRowNum(); rownum++) {
                if (sheet.getRow(rownum) != null) {
                    Row row = sheet.getRow(rownum);
                    if (row.getCell(1) != null) {
                        String executionDate = row.getCell(1).toString();
                        double executionTime = row.getCell(2).getNumericCellValue();
                        String testcaseName = row.getCell(3).toString().substring(0, 3);
                        String passOrfail = row.getCell(5).toString();
                        Date date = ImportJiraResultForQTP.stringToDate(executionDate, "yyyy/MM/dd HH:mm:ss");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        executionDate = formatter.format(date);
                        if (passOrfail.equals("OK")) {
                            String status = "Pass";
                            File allExcelFile = new File(
                                    "C:\\AUTOMATION\\Functionality\\" + JiraParam.testPlan + ".xlsx");
                            Workbook allExcelWorkBook = exportJiraCaeForQTP.getWorkbok(allExcelFile);
                            Sheet allExcelSheet = allExcelWorkBook.getSheetAt(0);
                            for (int i = 1; i <= allExcelSheet.getLastRowNum(); i++) {
                                Row allExcelRow = allExcelSheet.getRow(i);
                                String allExcelFolder = allExcelRow.getCell(2).toString();
                                String allExcelName = allExcelRow.getCell(3).toString();
                                if (allExcelFolder.equals(folderName) && allExcelName.contains(testcaseName)) {
                                    String testRunKey = allExcelRow.getCell(0).toString();
                                    String testCaseKey = allExcelRow.getCell(1).toString();
                                    // System.out.println(testRunKey+">>>"+testCaseKey+">>>"+executionDate + ">>>" +
                                    // executionTime + ">>>" + testcaseName + ">>>" + passOrfail);
                                    JiraAPI jApi = new JiraAPI();
                                    JSONObject json = new JSONObject();
                                    json.put("status", status);
                                    json.put("comment", "The test has pass on automation");
                                    json.put("userKey", jiraParam.username);
                                    json.put("executionTime", executionTime);
                                    json.put("executionDate", executionDate);
                                    String url = jApi.server + "rest/atm/1.0/testrun/" + testRunKey + "/testcase/"
                                            + testCaseKey + "/testresult";
                                    jApi.jiraPutAPI(url, json);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("config_jira.xml set <Import_Result> is False ,so don't import result in jira");
        }
    }

    public static Date stringToDate(String string, String pattern) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(string);
        return date;
    }

}
