package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author Netgear
 */

public class DevicesApPortConfiqSummaryPageElement extends MyCommonAPIs {
    public SelenideElement txtSendByte = $("#divByt2RecDevSumm");
    public SelenideElement txtRecvByte = $("#divBytRecDevSumm");
    public SelenideElement txtVlanID   = $("#divDlanDevSumm");
    public SelenideElement txtPVID     = $("#divDefault2VlanDevSumm span");
}
