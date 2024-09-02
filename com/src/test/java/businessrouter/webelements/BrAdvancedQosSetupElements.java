package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrAdvancedQosSetupElements {
    public SelenideElement dynaminqosenable                = $("#dynamic_qos_enable");
    public SelenideElement detectinternetbandwith          = $("#qos_quality_recommended");
    public SelenideElement qosspeedtestbutton              = $("#qos_quality_speedtest");
    public SelenideElement defineinternetbandwith          = $("#qos_quality_bandwidth");
    public SelenideElement downloadspeedvalue              = $("#downlink_value");
    public SelenideElement uploadspeedvalue                = $("#uplink_value");
    public SelenideElement autotoupdateferformance         = $("#AutoUpdateEnable");
    public SelenideElement updatenowbutton                 = $("#qos_quality_updateNow");
    public SelenideElement qoscancelbutton                 = $("#qos_dynamic_cancel");
    public SelenideElement qosdynamicapplybutton           = $("#qos_dynamic_apply");
    public SelenideElement dynaminqosenabledialogok        = $x("//div[@class=\"ant-confirm-btns\"]/button[2]");
    public SelenideElement defineinternetbandwidthdialogok = $x("//div[@class=\"ant-confirm-btns\"]/button");
    public SelenideElement speedvalueerrordialogtext       = $x("//div[@class=\"ant-message\"]/span");
    public SelenideElement dynaminqosdialogtest            = $x("//span[@class=\"ant-confirm-title\"]");
}
