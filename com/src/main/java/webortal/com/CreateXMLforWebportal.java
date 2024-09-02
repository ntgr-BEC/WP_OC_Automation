package webortal.com;

import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import webortal.com.util.JiraAPI;

/**
 * 用DOM4J创建xml文档
 * 
 *
 */

public class CreateXMLforWebportal {

    public static void main(String[] args) throws Exception {
        
        //1.第一种 创建文档及设置根元素节点的方式
        
        
        //2.第二种 创建文档及设置根元素节点的方式
        Element root = DocumentHelper.createElement("suite");
        Document document = DocumentHelper.createDocument(root);
        //add suite
        root.addAttribute("name", "Suite");
        
        //add test
        Element testElement = root.addElement("test");
        testElement.addAttribute("name", "Test");
        //add packages
       Element packagesElement=  testElement.addElement("packages");
        JiraAPI jApi=new JiraAPI();
        List<String> testname=jApi.getTestNgPackageName();
        for(String attribute : testname) {
            packagesElement.addElement("package").addAttribute("name", attribute);
          }                
        //把生成的xml文档存放在硬盘上  true代表是否换行
        OutputFormat format = new OutputFormat("    ",true);
        format.setEncoding("UTF-8");//设置编码格式
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream( System.getProperty("user.dir")+"/testng.xml"),format);
    
        xmlWriter.write(document);
        xmlWriter.close();
    }

}
