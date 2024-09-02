/**
 * 
 */
package webortal.com;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author zheli
 *
 */
public class ImportAllResult {

    /**
     * 
     */
    public ImportAllResult() {
        // TODO Auto-generated constructor stub
        
    }
    public static void main(String[] args) throws IOException, ParseException {
        getFileName();
        System.out.println("export all case");
    }
    public static void getFileName() throws IOException, ParseException {
        String path = "C:\\AUTOMATION\\TESTSUITE\\TestReport"; // 路径
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            String folder=fs.getName();
            System.out.println(folder);
            String[] str=new String[1];
            str[0] = folder;
            ImportJiraResultForQTP importJiraResultForQTP=new ImportJiraResultForQTP();
                    importJiraResultForQTP.main(str);
        }
    }
}
