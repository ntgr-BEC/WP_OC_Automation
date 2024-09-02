/**
 * 
 */
package webortal.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import webortal.com.util.JiraAPI;
import webortal.com.util.JiraParam;

/**
 * 用来执行导出jira上的用例到QTP里
 * 
 * @author zheli
 *
 */
public class ExportJiraAllCase {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    public static void main(String[] args) throws Exception {
        JiraParam jiraParam = new JiraParam();
        ExportJiraAllCase qtp = new ExportJiraAllCase();
        qtp.createAllTestCaseExcel();

    }
    public void ss() throws IOException {
        String excelPath = "C:\\AUTOMATION\\Functionality\\MS-P3-all.xlsx";
        File finalXlsxFile = new File(excelPath);
        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        HashMap<String, String > ha= new HashMap<String, String>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String name = row.getCell(0).toString();
            ha.put(name, "1");
        }
        System.out.println(ha.size());
    }
    public void createAllTestCaseExcel() throws IOException {
        // Create excel and Workbook
        OutputStream out = null;
        JiraAPI jiraApi = new JiraAPI();
        String excelPath = "C:\\AUTOMATION\\Functionality\\" + jiraApi.testPlan + "-all.xlsx";
        CreateExcel(excelPath);
        File finalXlsxFile = new File(excelPath);
        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        JiraAPI jiraAPI = new JiraAPI();
        int excelrow = 1;
        for (String testRunKey : jiraAPI.testrun) {
            String APIurlurl = jiraAPI.server + "rest/atm/1.0/testrun/" + testRunKey;
            JSONObject jsonObject = jiraAPI.jiraGetAPI(APIurlurl);
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i < items.size(); i++) {
                String testCaseKey = JSONPath.eval(jsonObject, "$.items.testCaseKey[" + i + "]").toString();
                String testCaseStatus = JSONPath.eval(jsonObject, "$.items.status[" + i + "]").toString();
                if (jiraAPI.RunCaseStatus.contains(testCaseStatus)) {
                    String testCaseurl = jiraAPI.server + "rest/atm/1.0/testcase/" + testCaseKey;
                    JSONObject jsonObject2 = jiraAPI.jiraGetAPI(testCaseurl);
                    String autoamtion = JSONPath.eval(jsonObject2, "$.customFields.Automation").toString();
                    // only run automaiton case
                    String folder = JSONPath.eval(jsonObject2, "$.folder").toString();
                    String[] folderName = folder.split("/");
                    String objective = folderName[folderName.length - 1];
                    String caseName = JSONPath.eval(jsonObject2, "$.name").toString();
                    // 看case的name里是不是没有CLI或者GUI，没有就导出，如果是有CLI或者GUI的其中一个，就只导出符合的这个
                    Row row = sheet.createRow(excelrow);
                    System.out.println(testRunKey + ":" + testCaseKey + ":" + objective + ":" + caseName);
                    row.createCell(0).setCellValue(testRunKey);
                    row.createCell(1).setCellValue(testCaseKey);
                    row.createCell(2).setCellValue(objective);
                    row.createCell(3).setCellValue(caseName);
                    row.createCell(4).setCellValue(autoamtion);
                    excelrow++;
                }
            }
        }
        out = new FileOutputStream(finalXlsxFile);
        workBook.write(out);
        out.flush();
        out.close();
        System.out.println("export jira testCase success");
    }

    public Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) { // Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * // 在指定目录下创建一个excel文档，用来存放testplan下的所有测试用例
     */
    public void CreateExcel(String excelFile) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("report");
        // 3:创建首行
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("testRunKey");
        row.createCell(1).setCellValue("testCaseKey");
        row.createCell(2).setCellValue("folder");
        row.createCell(3).setCellValue("name");
        row.createCell(4).setCellValue("Automation");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(excelFile);
            workbook.write(fos);
            fos.flush();
            fos.close();
            System.out.println("create excel done!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 把objective目录对应的excel的caseName对应的Executed变成Y
     * 
     * @param objective
     * @param caseName
     * @throws IOException
     */

    public void markExecuteCaseList(String objective, String caseID) throws IOException {
        OutputStream out = null;
        JiraAPI jiraApi = new JiraAPI();
        String excelPath = "C:\\AUTOMATION\\Functionality\\" + objective + "\\ExecuteCaseList.xls";
        File finalXlsxFile = new File(excelPath);
        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
            Row row = sheet.getRow(rownum);
            if (row.getCell(0) != null) {
                String cellString = row.getCell(0).toString();
                if (cellString.contains(caseID)) {
                    row.getCell(1).setCellValue("Y");
                }
            }

        }
        out = new FileOutputStream(finalXlsxFile);
        workBook.write(out);
        out.flush();
        out.close();
    }

    /**
     * 把所有Functionality下的每一个ExecuteCaseList 下的case标记好
     * 
     * @throws IOException
     */
    public void createFunctionalityTestCaseExcel() throws IOException {
        List<String> ExcelList = new ArrayList();
        JiraAPI jiraApi = new JiraAPI();
        String excelPath = "C:\\AUTOMATION\\Functionality\\" + jiraApi.testPlan + ".xlsx";
        File finalXlsxFile = new File(excelPath);
        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        // 取出所有不重名的目录字段
        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
            Row row = sheet.getRow(rownum);
            String cellString = row.getCell(2).toString();
            ExcelList.add(cellString);
        }
        Set set = new HashSet();
        List<String> objectiveList = new ArrayList();
        for (String object : ExcelList) {
            if (set.add(object)) {
                objectiveList.add(object);
            }
        }
        for (String excelName : objectiveList) {
            initExecuteCaseList(excelName);
        }
        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
            Row row = sheet.getRow(rownum);
            String folderName = row.getCell(2).toString();
            String caseName = row.getCell(3).toString().substring(0, 4);
            markExecuteCaseList(folderName, caseName);
        }
    }

    /**
     * 把指定的excel里的Executed 变成N
     * 
     * @param objective
     * @throws IOException
     */
    public void initExecuteCaseList(String objective) throws IOException {
        OutputStream out = null;
        JiraAPI jiraApi = new JiraAPI();
        String excelPath = "C:\\AUTOMATION\\Functionality\\" + objective + "\\ExecuteCaseList.xls";
        File finalXlsxFile = new File(excelPath);
        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
            Row row = sheet.getRow(rownum);
            if (row.getCell(0) != null) {
                row.getCell(1).setCellValue("N");
            }
        }
        out = new FileOutputStream(finalXlsxFile);
        workBook.write(out);
        out.flush();
        out.close();
    }

    public void createRunVBS() throws IOException {
        List<String> ExcelList = new ArrayList();
        JiraAPI jiraApi = new JiraAPI();
        String excelPath = "C:\\AUTOMATION\\Functionality\\" + jiraApi.testPlan + ".xlsx";
        File finalXlsxFile = new File(excelPath);
        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
            Row row = sheet.getRow(rownum);
            String cellString = row.getCell(2).toString();
            ExcelList.add(cellString);
        }
        Set set = new HashSet();
        List<String> vbsList = new ArrayList();
        for (String object : ExcelList) {
            if (set.add(object)) {
                vbsList.add(object);
            }
        }
        String vbsPath = "C:\\AUTOMATION\\TESTSUITE\\0RUN-TEST-SET.vbs";
        File vbs = new File(vbsPath);
        // clear code
        write(vbsPath, false, "");
        // start write code
        writeInvbs("''#########################################################################################");
        writeInvbs("''#                                                                                       #");
        writeInvbs("''#                    THIS FILE WAS GENERATED BY VBSCRIPT DYNAMICALLY                    #");
        writeInvbs("''#                                                                                       #");
        writeInvbs("''#                        Create By Tool Automated Run Test Plan                         #");
        writeInvbs("''#                                                                                       #");
        writeInvbs("''#                                  CRATED BY Zhenwei.Li                                 #");
        writeInvbs("''#                                                                                       #");
        writeInvbs("''#########################################################################################");
        writeInvbs("ReDim TestSet (" + vbsList.size() + ")");
        writeInvbs("For idx = 1 To UBound(TestSet)");
        writeInvbs("  TestSet(idx) = \"\"");
        writeInvbs("Next");
        writeInvbs("");
        writeInvbs("");
        int index = 1;
        for (String feature : vbsList) {
            writeInvbs("TestSet(" + index + ") = \"TS-FUNC-" + feature + ".vbs\"");
            index++;
        }
        writeInvbs("'###################################################################");
        writeInvbs("'####      !!!!!!!!!       DON'T MODIFY            !!!!!!!!!      ##");
        writeInvbs("'###################################################################");
        writeInvbs("");
        writeInvbs("set wshShell=WScript.CreateObject(\"WScript.shell\")");
        writeInvbs("");
        writeInvbs("For idx = 1 To UBound(TestSet)");
        writeInvbs("    If TestSet(idx) <> \"\" Then");
        writeInvbs("        wshShell.Run(\"wscript.exe \"\"\" & TestSet(idx)) & \"\"\"\", 1, TRUE");
        writeInvbs("        wscript.Sleep 10000");
        writeInvbs("    End If");
        writeInvbs("Next");
        writeInvbs("'' following scripts for send mail");
        writeInvbs("Auto_Send_Email_Flag = \"yes\"");
        writeInvbs("");
        writeInvbs("If Auto_Send_Email_Flag = \"yes\" Then");
        writeInvbs("    wscript.Sleep 60000");
        writeInvbs("Set qtpApp = CreateObject(\"QuickTest.Application\")");
        writeInvbs(
                "LogSystem_Switch_Type = qtpApp.Test.LastRunResults.DataTable.GlobalSheet.GetParameter(\"LogSystem_Switch_Type\")");
        writeInvbs("qtpApp.TDConnection.Disconnect");
        writeInvbs("Set qtpApp = Nothing");
        writeInvbs("''LogSystem_Switch_Type = 1");
        writeInvbs("    Set oFSO = CreateObject(\"Scripting.FileSystemObject\")");
        writeInvbs("    Set f = oFSO.OpenTextFile(\"AUTO-SEND-MAIL.vbs\")");
        writeInvbs("    s = f.ReadAll");
        writeInvbs("    f.Close");
        writeInvbs("    ExecuteGlobal s");
        writeInvbs("    Send_Report");
        writeInvbs("    Set oFSO = Nothing");
        writeInvbs("End If");
        System.out.println("CreateRunVBS done! : " + vbsList);

    }

    public void writeInvbs(String text) {
        String vbsPath = "C:\\AUTOMATION\\TESTSUITE\\0RUN-TEST-SET.vbs";
        File finalXlsxFile = new File(vbsPath);
        write(vbsPath, true, text + "\r\n");
    }

    public void write(String filePath, boolean append, String text) {
        if (text == null) {
            return;
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append));
            try {
                out.write(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
