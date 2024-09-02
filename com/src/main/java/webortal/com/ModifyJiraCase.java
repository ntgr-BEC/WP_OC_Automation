/**
 * 
 */
package webortal.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author zheli
 *
 */
public class ModifyJiraCase {

    /**
     * @throws IOException
     * 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated constructor stub
        // JiraParam jiraParam = new JiraParam();
//        addAutomation("[customer issue]Handle some lldp packet with vlan id 0","Yes");
      
        modifyAutomation();
          System.out.println("end");
    }

    public static void modifyAutomation() throws IOException {
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
//                System.out.println(fs.getName() + " [目录]");
            } else {
                System.out.println(fs.getName());
                String excelPath = "D:/学习/Managed Test Cases Summary/" + fs.getName();
                File finalXlsxFile = new File(excelPath);
                ExportJiraCaeForQTP ex = new ExportJiraCaeForQTP();
                Workbook workBook = ex.getWorkbok(finalXlsxFile);
                Sheet sheet = workBook.getSheetAt(0);
                for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {
                    String caseName;
                    String isAutomation;
                    Row row = sheet.getRow(rownum);
                    if (row.getCell(0) != null) {
                        caseName = row.getCell(0).toString();
//                        System.out.println(caseName);
                        isAutomation = row.getCell(6).toString();
//                        System.out.println(isAutomation);
                        addAutomation(caseName, isAutomation);
                    } else {
                    }
                }
            }
        }
    }

    public static void addAutomation(String caseName, String isAutomation) throws IOException {
        OutputStream out = null;
        String excelPath = "D:/ffte/allCase.xls";
        File finalXlsxFile = new File(excelPath);
        ExportJiraCaeForQTP ex = new ExportJiraCaeForQTP();
        Workbook workBook = ex.getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        for (int rownum = 1; rownum <= sheet.getLastRowNum(); rownum++) {

            Row row = sheet.getRow(rownum);
            if (row.getCell(1) != null) {
                String acName = row.getCell(1).toString();
                if (acName.equals(caseName)) {
                    row.createCell(2).setCellValue(isAutomation);
                }
            }
        }
        out = new FileOutputStream(finalXlsxFile);
        workBook.write(out);
        out.flush();
        out.close();
    }

}
