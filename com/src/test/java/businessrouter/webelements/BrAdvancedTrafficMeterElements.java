package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class BrAdvancedTrafficMeterElements {
    public SelenideElement trafficmeterenableordisable = $("#checkTraffic");
    public SelenideElement trafficvolumecontroltype    = $("#traffic_internet_tvolume");
    public SelenideElement trafficvolumecontrolvalue   = $x("//*[@id=\"tm_type\"]/div[1]/div/div");
    public SelenideElement trafficmonthlylimit         = $("#traffic_internet_tvolumeMon");
    public SelenideElement tafficrounddatavoumevalue   = $("#traffic_internet_roundUp");
    public SelenideElement tafficconnectiontimecontrol = $("#traffic_internet_conntime");
    public SelenideElement tafficconnectiontimemonthly = $("#traffic_internet_conntimeMon");

    public SelenideElement tafficrestarttimehour = $x(
            "//form[@class=\"ant-form ant-form-horizontal qos-form qos-form2 left-item\"]/div[@class=\"ant-row ant-form-item\"][3]//span[@class=\"ant-time-picker m-b-8\"]/input");

    public SelenideElement tafficrestarttimeday = $x(
            "//div[@id=\"root\"]//div[@class=\"ant-row ant-form-item\"][3]//div[@class=\"ant-select-selection-selected-value\"]");

    public SelenideElement tafficrestrartnowbutton           = $("#traffic_internet_restartBut");
    public SelenideElement tafficpopwarningmessage           = $("#traffic_internet_warningMessage");
    public SelenideElement tafficturnledflash                = $("#checkLed");
    public SelenideElement tafficblockinternet               = $("#checkBlock");
    public SelenideElement tafficmeterrefreshbutton          = $("#traffic_statistics_Refresh");
    public SelenideElement tafficmetertafficstatusbutton     = $("#traffic_statistics_Traffic");
    public SelenideElement tafficmeterpollinterval           = $("#interval");
    public SelenideElement tafficmetersetinterval            = $("#show_traffic_interval");
    public SelenideElement trafficmetertrafficvolumeupload   = $x("//div[@class=\"ant-table-body\"]//tbody/tr[1]/td[3]");
    public SelenideElement trafficmetertrafficvolumedownload = $x("//div[@class=\"ant-table-body\"]//tbody/tr[1]/td[4]");
    public SelenideElement trafficmetertrafficvolumetotal    = $x("//div[@class=\"ant-table-body\"]//tbody/tr[1]/td[5]");
    public SelenideElement tafficmetercancelbutton           = $("#TW_traffic_Cancel");
    public SelenideElement tafficmeterapplybutton            = $("#TW_traffic_Apply");
    public SelenideElement trafficrestartdialog              = $x("//span[@class=\"ant-confirm-title\"]");
    public SelenideElement trafficrestartdialogokbutton      = $x("//button[@class=\"ant-btn ant-btn-primary\"]");
    public SelenideElement trafficrestartdialogcancelbutton  = $x("//div[@class=\"ant-confirm-btns\"]/button[1]");
    public SelenideElement trafficmeterchecked               = $x("//input[@id=\"checkTraffic\"]/..");
    public SelenideElement trafficmeterhourselected          = $x(
            "//div[@class=\"ant-time-picker-panel-combobox\"]/div[1]//li[@class=\"ant-time-picker-panel-select-option-selected\"]");
    public SelenideElement trafficmeterminuteselected        = $x(
            "//div[@class=\"ant-time-picker-panel-combobox\"]/div[2]//li[@class=\"ant-time-picker-panel-select-option-selected\"]");
    public SelenideElement trafficmeterstarttimetext         = $x("//div[@class=\"bak-block\"]/p[1]");
    public SelenideElement trafficmetercurrenttimetext       = $x("//div[@class=\"bak-block\"]/p[2]");
    public SelenideElement trafficmeterwarningmessage        = $x("//div[@class=\"ant-confirm-body\"]/span");
    public SelenideElement trafficmeterwarningmessageok      = $x("//div[@class=\"ant-confirm-btns\"]/button");
    public SelenideElement trafficmetertrafficstatusexit     = $("#PanelHeader_close_btn");
    public SelenideElement trafficmeterdialogtext            = $x("//div[@class=\"ant-message\"]/span");
    public SelenideElement trafficmeterwarningpage           = $("#message");

    public void trafficcounterdayselect(String date) {
        String xpath = "//ul[@class=\"ant-select-dropdown-menu  ant-select-dropdown-menu-root ant-select-dropdown-menu-vertical\"]/li";
        for (int i = 1; i < 30; i++) {
            SelenideElement trafficcounterday = $x(xpath + "[" + String.valueOf(i) + "]");
            if (tafficrestarttimeday.getText().indexOf(date) != -1) {
                // trafficcounterday.scrollTo();
                tafficrestarttimeday.click();
                MyCommonAPIs.sleepi(3);
                break;

            }
            if (trafficcounterday.getText().indexOf(date) != -1) {
                trafficcounterday.click();
                MyCommonAPIs.sleepi(3);
                break;
            }
            if (i % 5 == 0) {
                trafficcounterday.click();
                MyCommonAPIs.sleepi(3);
                tafficrestarttimeday.click();
            }
        }
    }

    public void trafficcounterdaydefault() {
        String xpath = "//ul[@class=\"ant-select-dropdown-menu  ant-select-dropdown-menu-root ant-select-dropdown-menu-vertical\"]/li";
        for (int i = 1; i < 30; i++) {
            if (tafficrestarttimeday.getText().indexOf("1st") == -1) {
                String selectedtext = tafficrestarttimeday.getText().replaceAll("[a-zA-Z]", "");
                if (tafficrestarttimeday.getText().equals("Last")) {
                    Selenide.$x(xpath + "[" + String.valueOf(29 - 5) + "]").click();
                    MyCommonAPIs.sleepi(3);
                    tafficrestarttimeday.click();
                } else if (Integer.valueOf(selectedtext) < 6) {
                    Selenide.$x(xpath + "[1]").click();
                    MyCommonAPIs.sleepi(3);
                    tafficrestarttimeday.click();
                } else {
                    Selenide.$x(xpath + "[" + String.valueOf(Integer.valueOf(selectedtext) - 5) + "]").click();
                    MyCommonAPIs.sleepi(3);
                    tafficrestarttimeday.click();
                }
            } else {
                break;
            }
        }
    }

    public void trafficvolumecontrolconfig(String option) {
        String xpath = "//ul[@class=\"ant-select-dropdown-menu  ant-select-dropdown-menu-root ant-select-dropdown-menu-vertical\"]/li[text()=\"";
        // System.out.println(Selenide.$x(xpath + option + "\"]").getText());
        Selenide.$x(xpath + option + "\"]").click();
        MyCommonAPIs.sleepi(3);
    }
}
