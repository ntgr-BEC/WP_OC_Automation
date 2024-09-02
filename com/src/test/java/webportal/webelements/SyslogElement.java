package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class SyslogElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("SyslogElement");

    public SelenideElement cbEnable = $("#toggleSyslogConfig");
    public SelenideElement txtIp    = $("#ipPortConfig input[data-type*=syslogServerIp]");
    public SelenideElement txtPort  = $("#ipPortConfig input[data-type*=sysLogPort]");
    public SelenideElement btnSave  = $("#sysLogForm button:last-child");
}
