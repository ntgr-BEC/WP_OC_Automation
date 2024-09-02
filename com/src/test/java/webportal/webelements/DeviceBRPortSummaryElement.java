package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceBRPortSummaryElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRPortSummaryElement");

    public SelenideElement portType      = $("#divDevMoniDevSumm");
    public SelenideElement vlanID        = $("#divDlanDevSumm");
    public SelenideElement PVID          = $("#divDefault2VlanDevSumm");
    public SelenideElement BytesSent     = $("#divByte2SentDevSumm");
    public SelenideElement BytesReceived = $("#divByt2RecDevSumm");
}
