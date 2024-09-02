package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLManagerForTest {
    /**
     * 读取XML文件，获取满足要求的第一个节点的属性值 参数：filePath（文件路径），attrXpath（需要获取属性值的属性的xpath）
     *
     * @throws DocumentException
     * @auther:lizhenwei,2015-07-20
     */
    public String       xml_config_port      = System.getProperty("user.dir") + "/src/test/resources/config_port.xml";
    public String       xml_config_dut       = System.getProperty("user.dir") + "/src/test/resources/config_dut.xml";
    public String       xml_config_webportal = System.getProperty("user.dir") + "/src/test/resources/config_webportal.xml";
    static List<String> toprint              = Arrays.asList("//loginName", "//serverUrl", "//WebPortalVersion", "//location1", "Ip_Address",
            "SerialNo", "DeviceName", "Windows_ip_address");

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
        List<?> list = document.selectNodes(attrXpath);
        Iterator<?> iter = list.iterator();
        if (list.size() < 1) {
            System.out.println("Not found: " + attrXpath);
        } else {
            Element element = (Element) iter.next();
            attrValue = element.getStringValue();
            if (toprint.contains(attrXpath)) {
                System.out.println(String.format("getAttributeValueFromXml: <%s> --> <%s>", attrXpath, attrValue));
            }
        }
        return attrValue;
    }

    public ArrayList<String> getAttributeValuesFromXml(File filePath, String sFatherXpath, String sChildMatch) {
        ArrayList<String> lsValues = new ArrayList<String>();
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(filePath);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return lsValues;
        }

        List<?> list = document.selectNodes(sFatherXpath);
        if (list.isEmpty()) {
            System.out.println("getAttributeValuesFromXml -> Not found: " + sFatherXpath);
        } else {
            Iterator<?> iter = list.iterator();
            while (iter.hasNext()) {
                Element element = (Element) iter.next();
                List<?> le = element.elements();
                for (Object e : le) {
                    if (((Element) e).getName().toLowerCase().contains(sChildMatch.toLowerCase())) {
                        String toAdd = ((Element) e).getStringValue();
                        System.out.println(String.format("getAttributeValuesFromXml -> Add <%s> from <%s>/<%s>", toAdd, sFatherXpath, sChildMatch));
                        lsValues.add(toAdd);
                    }
                }
            }
        }
        return lsValues;
    }

    /**
     * 读取config_port.xml 参数：attrXpath（需要获取属性值的属性的xpath）
     *
     * @return 返回string对象
     * @throws DocumentException
     * @auther:lizhenwei,2015-07-20
     */
    public String getValueFromPortXml(String attrXpath) {
        try {
            File file = new File(System.getProperty("user.dir") + "/src/test/resources/config_port.xml");
            String attrValue = getAttributeValueFromXml(file, attrXpath);
            return attrValue;
        } catch (Throwable e) {
            System.out.println(String.format("<%s> not configured", attrXpath));
            return "";   
        }
    }

    /**
     * 读取config_dut.xml 参数：attrXpath（需要获取属性值的属性的xpath）
     *
     * @return 返回string对象
     * @throws DocumentException
     * @auther:lizhenwei,2015-07-20
     */
    public String getValueFromDutXml(String attrXpath) {
        try {
            File file = new File(System.getProperty("user.dir") + "/src/test/resources/config_dut.xml");
            String attrValue = getAttributeValueFromXml(file, attrXpath);
            return attrValue;
        } catch(Throwable e) {
            return "";
        }
    }

    public ArrayList<String> getValuesFromDutXml(String attrXpath, String toMatch) {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/config_dut.xml");
        return getAttributeValuesFromXml(file, attrXpath, toMatch);
    }

    /**
     * read config_port.xml and config_dut.xml first read config_port ,second read config_dut
     *
     * @param portParam
     * @param dutParam
     * @return
     */
    public String getValueFromWebPortAndDut(String portParam, String dutParam) {
        
        try {
            String useInDut = getValueFromWebPortalXml("//" + portParam);
            String dut = getValueFromDutXml("//" + useInDut + "/" + dutParam);
            if (toprint.contains(dutParam)) {
                System.out.println(String.format("getValueFromWebPortAndDut: <%s> --> <%s>", useInDut, dut));
            }
            return dut;
        } catch(Throwable e) {
            System.out.println(String.format("dut <%s> attribute <%s> not configured", portParam, dutParam));
            return "";   
        }
        
    }

    public ArrayList<String> getValuesFromWebPortAndDut(String portParam, String toMatch) {
        String useInDut = getValueFromWebPortalXml("//" + portParam);
        return getValuesFromDutXml("//" + useInDut, toMatch);
    }

    /**
     * 读取config_webportal.xml 参数：attrXpath（需要获取属性值的属性的xpath）
     *
     * @return 返回string对象
     * @throws DocumentException
     * @auther:lizhenwei,2015-07-20
     */
    public String getValueFromWebPortalXml(String attrXpath) {
        try {
            File file = new File(System.getProperty("user.dir") + "/src/test/resources/config_webportal.xml");
            String attrValue = getAttributeValueFromXml(file, attrXpath);
            return attrValue;
        } catch(Throwable e) {
            System.out.println(String.format("<%s> is not configured", attrXpath));
            return "";
        }
        
    }

    /**
     * read config xml value form config_qc.xml
     *
     * @param attrXpath
     * @return
     */
    public String getValueFromConfigJiraXml(String attrXpath) {
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/config_jira.xml");
        String attrValue = getAttributeValueFromXml(file, attrXpath);
        return attrValue;
    }
}
