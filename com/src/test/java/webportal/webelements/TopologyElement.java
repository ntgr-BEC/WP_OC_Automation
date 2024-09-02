package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TopologyElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("TopologyElement");

    // svg elements
    public SelenideElement topology = $x("(//*[name()='svg'])[@style='cursor: all-scroll;']");
    public SelenideElement node = $x("((//*[name()='svg'])[@style='cursor: all-scroll;']//*[local-name()='g']/*[local-name()='g'])");
    public SelenideElement node1 = $x("((//*[name()='svg'])[@style='cursor: all-scroll;']//*[local-name()='g']/*[local-name()='g'])[1]");
    public SelenideElement node2 = $x("((//*[name()='svg'])[@style='cursor: all-scroll;']//*[local-name()='g']/*[local-name()='g'])[2]");
    
//    public SelenideElement arrow = $x("//div[contains(@class,'toplogyRectangle')]");
    public SelenideElement arrow = $x("//img[contains(@src, 'default-three-dot')]");
  
    public SelenideElement icontopology = $x("//img[contains(@src,'icon-topology')]");
    public SelenideElement treeview = $x("//p[text()='Tree View']");
    public SelenideElement abstractview = $x("//p[text()='Abstract View']");
    public SelenideElement ssidview = $x("//p[text()='SSID View']");
    public String nodestr = "((//*[name()='svg'])[@style='cursor: all-scroll;']//*[local-name()='g']/*[local-name()='g'])";
    public SelenideElement moredetail = $x("//button[text()='More Details']");
    public SelenideElement imgclose = $x("//img[contains(@src,'whiteCross')]");
    
    public SelenideElement shareicon = $x("//span[contains(@class,'icon-share')]");
    public SelenideElement rebooticon = $x("//span[contains(@class,'icon-reboot')]");
    public SelenideElement inputemail      = $("input[name*=emal]");
    public SelenideElement btnsend = $x("//div[contains(@style,'display: block')]//button[text()='Send']");
    public SelenideElement btncancel = $x("//div[contains(@style,'display: block')]//button[text()='Cancel']");
    public SelenideElement btncontinue = $x("//div[contains(@style,'display: block')]//button[text()='Continue']");
    
    public SelenideElement NodeImage(String DeviceName) {
        SelenideElement returnele = $x(nodestr);
        ElementsCollection ecs = $$x(nodestr);
        int index = 0;
        for (SelenideElement ele : ecs) {
            index ++;
            if(ele.getText().equals(DeviceName)) {
                returnele = ele;
                break;
            }
        }
        System.out.println(returnele);
        String nodeimgstr = nodestr + "[" + String.valueOf(index) + "]" + "/*[local-name()='image']";
        System.out.println(nodeimgstr);
        System.out.println($x(nodeimgstr));
        //return returnele;
        return $x(nodeimgstr);
    }
    
    //addedbypratikmovedevice
    public SelenideElement  noDeviceRelatedData  = $x("//p[text()='No Data Available']");
    public SelenideElement  generatingTopology   = $x("//p[text()='Generating Topology']");
    
}
