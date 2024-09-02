/**
 * 
 */
package webortal.com;

import java.io.File;

import com.alibaba.fastjson.JSONObject;

import webortal.com.util.JiraAPI;
import webortal.com.util.JiraParam;

/**
 * @author zheli
 *
 */
public class CreateJiraFolder {

    /**
     * 
     */
    public CreateJiraFolder() {
        // TODO Auto-generated constructor stub
    }
    public static void main(String[] args) {
        JiraParam jiraParam = new JiraParam();
        getFileName();
      
        
//        for (int i = 0; i < args.length; i++) {
         
//        }
     
    }
    public static void getFileName() {
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
               String filename="/"+ fs.getName().replaceAll(".xlsx", "").replaceAll("-", "/");
               JiraAPI jApi = new JiraAPI();
               JSONObject json = new JSONObject();
               json.put("projectKey", "MS");
               json.put("name",filename);
               json.put("type", "TEST_CASE");
               String url ="https://jira.netgear.com/rest/atm/1.0/folder";
               jApi.jiraPostAPI(url, json);
                System.out.println(filename);
            }
        }
    }
}
