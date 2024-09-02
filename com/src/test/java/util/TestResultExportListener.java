/**
 * 
 */
package util;

/**
 * @author zheli
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import switches.param.GlobalParam;
import webortal.com.util.JiraAPI;

/**
 * use to listener testng report and send post http request to jira api
 * 
 * @author zheli
 *
 */
public class TestResultExportListener implements IReporter {
    private static final String EXCEL_XLS  = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        List<ITestResult> list = new ArrayList<ITestResult>();
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext testContext = suiteResult.getTestContext();
                IResultMap passedTests = testContext.getPassedTests();
                IResultMap failedTests = testContext.getFailedTests();
                IResultMap skippedTests = testContext.getSkippedTests();
                IResultMap failedConfig = testContext.getFailedConfigurations();
                list.addAll(this.listTestResult(passedTests));
                list.addAll(this.listTestResult(failedTests));
                list.addAll(this.listTestResult(skippedTests));
                list.addAll(this.listTestResult(failedConfig));
            }
        }
        this.sort(list);
        try {
            this.outputResult(list, System.getProperty("user.dir") + "/target/JiraAPIReport.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private ArrayList<ITestResult> listTestResult(IResultMap resultMap) {
        Set<ITestResult> results = resultMap.getAllResults();
        return new ArrayList<ITestResult>(results);
    }

    private void sort(List<ITestResult> list) {
        Collections.sort(list, new Comparator<ITestResult>() {
            public int compare(ITestResult r1, ITestResult r2) {
                if (r1.getStartMillis() > r2.getStartMillis()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    private void outputResult2(List<ITestResult> list, String path) {
        try {
            XMLManagerForTest xmlManagerForTest = new XMLManagerForTest();
            BufferedWriter output = new BufferedWriter(new FileWriter(new File(path)));
            StringBuffer sb = new StringBuffer();
            for (ITestResult result : list) {
                if (sb.length() != 0) {
                    sb.append("\r\n");
                }
                sb.append(result.getTestClass().getRealClass().getPackage().getName()).append(" ").append(GlobalParam.BrowserType).append(" ")
                        .append("comment: The test has failed on some automation tool procedure.").append(" ")
                        .append(xmlManagerForTest.getValueFromConfigJiraXml("//User_Name")).append(" ")
                        .append(this.formatDate(result.getStartMillis())).append(" ").append(result.getEndMillis() - result.getStartMillis())
                        .append("毫秒 ").append(this.getStatus(result.getStatus()));
            }
            output.write(sb.toString());
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void outputResult(List<ITestResult> list, String path) throws IOException {
        XMLManagerForTest xmlManagerForTest = new XMLManagerForTest();
        OutputStream out = null;
        JiraAPI jiraApi = new JiraAPI();
        String excelPath = System.getProperty("user.dir") + "/target/" + jiraApi.testrun + ".xlsx";
        CreateExcel(excelPath);
        File finalXlsxFile = new File(excelPath);

        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        int i = 1;
        for (ITestResult result : list) {
            Row row = sheet.createRow(i);
            String packageName = result.getTestClass().getRealClass().getPackage().getName();
            String[] packageArray = packageName.split("testcase.");
            String packageArray2 = packageArray[1];
            System.err.println(packageArray2);
            String[] foldAndTestcase = packageArray2.split("[.]");
            row.createCell(0).setCellValue("jiraAPI.testrun");
            row.createCell(1).setCellValue(foldAndTestcase[0]);
            row.createCell(2).setCellValue(foldAndTestcase[1]);
            row.createCell(3).setCellValue(this.getStatus(result.getStatus()));
            row.createCell(4).setCellValue(GlobalParam.BrowserType);
            row.createCell(5).setCellValue("The test has failed on some automation tool procedure.");
            row.createCell(6).setCellValue(xmlManagerForTest.getValueFromConfigJiraXml("//User_Name"));
            row.createCell(7).setCellValue(result.getEndMillis() - result.getStartMillis());
            row.createCell(8).setCellValue(this.formatDate(result.getStartMillis()));
            i++;
        }
        out = new FileOutputStream(finalXlsxFile);
        workBook.write(out);
        System.out.println("export testNG success");
    }

    public static void CreateExcel(String excelFile) {
        // 1:创建一个excel文档
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("report");
        // 3:创建首行
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("testRunKey");
        row.createCell(1).setCellValue("folder");
        row.createCell(2).setCellValue("testCaseKey");
        row.createCell(3).setCellValue("status");
        row.createCell(4).setCellValue("environment");
        row.createCell(5).setCellValue("comment");
        row.createCell(6).setCellValue("userKey");
        row.createCell(7).setCellValue("executionTime");
        row.createCell(8).setCellValue("executionDate");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(excelFile);
            workbook.write(fos);
            fos.flush();
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

    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) { // Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    private String getStatus(int status) {
        String statusString = null;
        switch (status) {
        case 1:
            statusString = "Pass";
            break;
        case 2:
            statusString = "Fail";
            break;
        case 3:
            statusString = "Not Executed";
            break;
        default:
            break;
        }
        return statusString;
    }

    private String formatDate(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return formatter.format(date);
    }

}
