package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrAdvancedLogsElements {
    public SelenideElement logcontents = $x("//div[@class=\"log-message m-b-16\"]");
    //public SelenideElement logcontents                = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/form/div[1]");
    public SelenideElement logrefreshbutton           = $("#logs_but1");
    public SelenideElement logclearbutton             = $("#logs_but2");
    public SelenideElement logapplybutton             = $("#logs_but3");
    public SelenideElement logallowsites              = $("#log_site");
    public SelenideElement logblocksites              = $("#log_block");
    public SelenideElement logconnectiontoweb         = $("#log_conn");
    public SelenideElement logdosattackportscan       = $("#log_dosport");
    public SelenideElement logPortfowardingtriggering = $("#log_port");
    public SelenideElement logvpnservice              = $("#log_vpn_head");
    public SelenideElement logdialogtext              = $x("//div[@class=\"ant-message\"]/span");

    public void logcheckboxnotcheck(int index) {
        String xpath = "//div[@class=\"ant-row ant-form-item m-b-16\"][" + String.valueOf(index)
                + "]//span[@class=\"ant-form-item-children\"]//input/..";
        String xpathoption = "//div[@class=\"ant-row ant-form-item m-b-16\"][" + String.valueOf(index)
                + "]//span[@class=\"ant-form-item-children\"]//input";
        SelenideElement checkboxchecked = $x(xpath);
        SelenideElement checkboxselect = $x(xpathoption);
        if (checkboxchecked.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")) {
            checkboxselect.selectRadio("on");
        }
    }

    public void logcheckboxallcheck() {
        for (int i = 1; i <= 7; i++) {
            String xpath = "//div[@class=\"ant-row ant-form-item m-b-16\"][" + String.valueOf(i)
                    + "]//span[@class=\"ant-form-item-children\"]//input/..";
            String xpathoption = "//div[@class=\"ant-row ant-form-item m-b-16\"][" + String.valueOf(i)
                    + "]//span[@class=\"ant-form-item-children\"]//input";
            SelenideElement checkboxchecked = $x(xpath);
            SelenideElement checkboxselect = $x(xpathoption);
            if (!checkboxchecked.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")) {
                checkboxselect.selectRadio("on");
            }
        }
    }
}
