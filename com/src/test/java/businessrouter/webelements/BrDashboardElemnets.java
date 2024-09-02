package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrDashboardElemnets {
    public SelenideElement rebootbutton          = $x("(//button[@type = 'button'])[2]");
    public SelenideElement rebootokbutton        = $x("(//button[@class ='ant-btn ant-btn-primary'])[3]");
    public SelenideElement wanconnectstatus      = $x("(//div[@class =' ant-tabs-tab'])[1]");
    public SelenideElement wanreleasebutton      = $("#basic_conn_release");
    public SelenideElement wanrenewbutton        = $("#basic_conn_renew");
    public SelenideElement wandisconnectebutton  = $("#basic_conn_release");
    public SelenideElement wanconnectbutton      = $("#basic_conn_renew");
    public SelenideElement waninterconncetstatus = $x("(//tr[@class ='ant-table-row  ant-table-row-level-0']/td[2])[1]");
    public SelenideElement waninterip            = $x("(//tr[@class ='ant-table-row  ant-table-row-level-0']/td[2])[2]");
    public SelenideElement wanintermask          = $x("(//tr[@class ='ant-table-row  ant-table-row-level-0']/td[2])[3]");
    public SelenideElement devicestablename      = $x(
            "//div[@class=\"ant-table-body\"]//tr[@class=\"ant-table-row  ant-table-row-level-0\"]/td[3]");
    public SelenideElement devicestableip        = $x(
            "//div[@class=\"ant-table-body\"]//tr[@class=\"ant-table-row  ant-table-row-level-0\"]/td[4]");
    
    public SelenideElement rebootokbuttonbr100        = $x("(//button[@class ='ant-btn ant-btn-primary'])[1]");
}
