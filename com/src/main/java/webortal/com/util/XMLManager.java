package webortal.com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * only ues to src/main/java
 * 
 * @author zheli
 *
 */
public class XMLManager {
    /**
     * 读取XML文件，获取满足要求的第一个节点的属性值 参数：filePath（文件路径），attrXpath（需要获取属性值的属性的xpath）
     * 
     * @throws DocumentException
     * @auther:lizhenwei,2015-07-20
     */
    public String getAttributeValueFromXml(File filePath, String attrXpath) {
        String attrValue = "";
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(filePath);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List list = document.selectNodes(attrXpath);
        Iterator iter = list.iterator();
        if (list.size() < 1) {
            attrValue = "";
            System.out.println("not found xpath im xml file:" + attrXpath);
        } else {
            Element element = (Element) iter.next();
            attrValue = element.getStringValue();
        }
        return attrValue;
    }

    public String getValueFromWebPortalXml(String attrXpath) {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/config_webportal.xml");
        String attrValue = getAttributeValueFromXml(file, attrXpath);
        return attrValue;
    }
    public String getValueFromConfigPortXml(String attrXpath) {
        File file = new File("C:/AUTOMATION/CONFIG/config_port.xml");
        String attrValue = getAttributeValueFromXml(file, attrXpath);
        return attrValue;
    }
    /**
     * read config xml value form config_qc.xml
     * 
     * @param attrXpath
     * @return
     */
    public String getValueFromConfigJiraXml(String attrXpath) {
        File file = new File("C:/AUTOMATION/CONFIG/config_jira.xml");
        String attrValue = getAttributeValueFromXml(file, attrXpath);
        return attrValue;
    }
    public List<String> getTestRunFromConfigJiraXml(String attrXpath) {
        File file = new File("C:/AUTOMATION/CONFIG/config_jira.xml");
        List<String> list = getListFromXml(file, attrXpath);
        return list;
    }
    public  List<String>  getListFromXml(File filePath, String attrXpath){
        List<String> resultlist=new ArrayList<String>();
        String attrValue = "";
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(filePath);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List list = document.selectNodes(attrXpath);
        Iterator iter = list.iterator();
        for (int i=1;i<=list.size();i++) {
            Element element = (Element) iter.next();
            attrValue = element.getStringValue();
            resultlist.add(attrValue);
        }

        return resultlist;
    }
}
