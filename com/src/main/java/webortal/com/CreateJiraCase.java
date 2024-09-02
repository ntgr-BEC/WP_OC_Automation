/**
 * 
 */
package webortal.com;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;

import webortal.com.util.JiraParam;

/**
 * @author zheli
 *
 */
public class CreateJiraCase {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 
     */
    public CreateJiraCase() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) throws IOException {
        JiraParam jiraParam = new JiraParam();
        setComponet();

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

    public static void setComponet() throws IOException {
         Configuration.browser = "chrome";
         System.setProperty("webdriver.chrome.driver",
         System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
         open("https://jira.netgear.com/login.jsp");
         $("#login-form-username").setValue("zheli");
         Selenide.sleep(3000);
         $("#login-form-password").setValue("1qaz@WSX");
         Selenide.sleep(3000);
         $("#login-form-submit").click();
         Selenide.sleep(3000);
         open("https://jira.netgear.com/secure/Tests.jspa#/configuration/fileImport?projectId=13475");
        String path = "D:\\学习\\Managed Test Cases Summary"; // 路径
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]");
            } else {
                System.out.println(fs.getName());
                String[] ff = fs.getName().split("-");
                String feature = ff[ff.length - 1].replaceAll(".xlsx", "");
                System.out.println(feature);
                String folder = "/" + fs.getName().replaceAll(".xlsx", "").replaceAll("-", "/");
                System.out.println(folder);
                $(Selectors.byText("Microsoft® Excel®")).click();
                $(Selectors.byId("select-box-1-button")).setValue(folder);
                $(Selectors.byText("Next")).click();
                Selenide.sleep(1200);
                $(Selectors.byText("Next")).click();
//                String excelPath = "D:/学习/Managed Test Cases Summary/" + fs.getName();
//                File finalXlsxFile = new File(excelPath);
//                ExportJiraCaeForQTP ex = new ExportJiraCaeForQTP();
//                Workbook workBook = ex.getWorkbok(finalXlsxFile);
//                Sheet sheet = workBook.getSheetAt(0);
//                for (int rownum = 1; rownum < sheet.getLastRowNum(); rownum++) {
//                    String excName = "";
//                    String priority = "";
//                    Row row = sheet.getRow(rownum);
//                    if (row.getCell(0) != null) {
//                        excName = row.getCell(0).toString();
//                        System.out.println(excName);
//                        System.out.println(rownum);
//                        priority = row.getCell(3).toString();
//                        priority=modifyPriority(priority);
//                    } else {
//
//                    }
//                    JiraAPI jApi = new JiraAPI();
//                    JSONObject json = new JSONObject();
//                    json.put("projectKey", "MS");
//                    json.put("name", excName);
//                    json.put("objective", feature);
//                    json.put("folder", folder);
//                    json.put("status", "Approved");
//                    json.put("priority", "High");
//                    json.put("component", feature);
//                    json.put("owner", "bgu");
//                    son.put("testScript", "bgu");
//                    String url = "https://jira.netgear.com/rest/atm/1.0/folder";
//                    jApi.jiraPostAPI(url, json);
//                }
                // String[] ff= fs.getName().split("-");
                // String feature=ff[ff.length-1].replaceAll(".xlsx", "");
                // System.out.println(feature);
                // $("[name=\"name\"]").setValue(feature);
                // $("#assigneeType-field").click();
                // $(Selectors.byXpath("//button[text()='Add']")).click();
                //

                // String filename="/"+ fs.getName().replaceAll(".xlsx", "").replaceAll("-",
                // "/");
                // System.out.println(filename);
            }
        }
    }

    public static String modifyPriority(String pString) {
        if (pString.contains("P3")) {
            pString = "Low";
        } else if (pString.contains("P2")) {
            pString = "Normal";
        } else if (pString.contains("P1")) {
            pString = "High";
        }
        return pString;
    }
}
