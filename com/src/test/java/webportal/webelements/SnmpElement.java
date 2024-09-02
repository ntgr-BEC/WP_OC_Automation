package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class SnmpElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("SnmpElement");

    public SelenideElement cbEnable           = $("#snmpForm .cmnSwitch input");
    public SelenideElement txtIpAddress       = $("#snmpForm [data-type='snmpIpAddress']");
    public SelenideElement txtCommunityString = $("#snmpForm [data-type='snmpCommunityString']");
    public SelenideElement btnSave            = $("#divOvrFlwwiredSNMP button:last-child");


    public SelenideElement btnCancel          = $("#divOvrFlwwiredSNMP button:first-child");
    public SelenideElement btnClear           = $("#divOvrFlwwiredSNMP button[data-target*=clearSnmpModal]");
    
    //addedbypratikforMovedevice
    public SelenideElement nodevicesAvailable  = $x("(//p[text()='No device available'])[1]");
    public SelenideElement nodevicesAvailable1  = $x("(//p[text()='No device available'])[2]");
}
