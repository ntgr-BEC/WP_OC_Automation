package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class BrSecurityServiceBlockingElements {
    public SelenideElement serviceblockingnever           = $("#service_serviceRadio_never");
    public SelenideElement serviceblockingpre             = $("#service_serviceRadio_per");
    public SelenideElement serviceblockingalways          = $("#service_serviceRadio_always");
    public SelenideElement serviceblockingaddbutton       = $("#BKS_service_addBut");
    public SelenideElement serviceblockingeditbutton      = $("#BKS_service_editBut");
    public SelenideElement serviceblockingdeletebutton    = $("#BKS_service_delBut");
    public SelenideElement serviceblockingservicetype     = $x(
            "//div[@class=\"addBks\"]/form/div[1]//div[@class=\"ant-select-selection-selected-value\"]");
    public SelenideElement serviceblockingprotocol        = $x(
            "//div[@class=\"addBks\"]/form/div[2]//div[@class=\"ant-select-selection__rendered\"]");
    public SelenideElement serviceblockingstartingport    = $("#service_drawer_portstart");
    public SelenideElement serviceblockingendingport      = $("#service_drawer_portend");
    public SelenideElement serviceblockingtypedefineduser = $("#service_drawer_userdefined");
    public SelenideElement serviceblockingonlyip          = $("#drawer_filterservices_onlyip");
    public SelenideElement serviceblockingonlyipaddress1  = $("#filterservices_onlyip_1");
    public SelenideElement serviceblockingonlyipaddress2  = $("#filterservices_onlyip_2");
    public SelenideElement serviceblockingonlyipaddress3  = $("#filterservices_onlyip_3");
    public SelenideElement serviceblockingonlyipaddress4  = $("#filterservices_onlyip_4");
    public SelenideElement serviceblockingiprange         = $("#drawer_filterservices_iprange");
    public SelenideElement serviceblockingstartip1        = $("#filterservices_iprange_startip1");
    public SelenideElement serviceblockingstartip2        = $("#filterservices_iprange_startip2");
    public SelenideElement serviceblockingstartip3        = $("#filterservices_iprange_startip3");
    public SelenideElement serviceblockingstartip4        = $("#filterservices_iprange_startip4");
    public SelenideElement serviceblockingendip1          = $("#filterservices_iprange_endip1");
    public SelenideElement serviceblockingendip2          = $("#filterservices_iprange_endip2");
    public SelenideElement serviceblockingendip3          = $("#filterservices_iprange_endip3");
    public SelenideElement serviceblockingendip4          = $("#filterservices_iprange_endip3");
    public SelenideElement serviceblockingallip           = $("#drawer_filterservices_allip");
    public SelenideElement serviceblockingrulecancel      = $("#service_drawer_cancelBut");
    public SelenideElement serviceblockingruleapply       = $("#service_drawer_applyBut");
    public SelenideElement serviceblockingcancel          = $("#BKS_service_cancelBut");
    public SelenideElement serviceblockingapply           = $("#BKS_service_applyBut");
    public SelenideElement serviceblockingselectone       = $x(
            "//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[5]/div[2]/div/div/div/div/div/table/tbody/tr[#ex#]/td[1]/span/label/span/input");
    public SelenideElement serviceblockingneverchecked    = $x("//*[@id=\"service_serviceRadio_never\"]/..");
    public SelenideElement serviceblockingprechecked      = $x("//*[@id=\"service_serviceRadio_per\"]/..");
    public SelenideElement serviceblockingalwayschecked   = $x("//*[@id=\"service_serviceRadio_always\"]/..");
    public SelenideElement serviceblockingtable1          = $x("//div[@class=\"ant-table-body\"]//td[1]//input");
    public SelenideElement serviceblockingtable1checked   = $x("//div[@class=\"ant-table-body\"]//td[1]//input/..");
    public SelenideElement serviceblockingdeleteokbutton  = $x("//button[@class=\"ant-btn ant-btn-primary ant-btn-sm\"]");
    public SelenideElement serviceblockingdialog          = $x("/html/body/div[2]/div/span/div/div/div/span");
    public SelenideElement serviceblockingdialog2         = $x("/html/body/div[4]/div/span/div/div/div/span");
    public SelenideElement serviceonlyipaddresschecked    = $x("//*[@id=\"drawer_filterservices_onlyip\"]/..");
    public SelenideElement serviceipaddressrange          = $x("//*[@id=\"drawer_filterservices_iprange\"]/..");
    public SelenideElement servicedelruleok   = $x("//button[@class = 'ant-btn ant-btn-primary ant-btn-sm']");
    public void serviceblockingprotocolselect(String protocol) {
        String xpath = "//ul[@class=\"ant-select-dropdown-menu  ant-select-dropdown-menu-root ant-select-dropdown-menu-vertical\"]/li";
        for (int i = 1; i < 4; i++) {
            SelenideElement serviceblockingprotocolselect = $x(xpath + "[" + String.valueOf(i) + "]");
            if (serviceblockingprotocolselect.getText().indexOf(protocol) != -1) {
                serviceblockingprotocolselect.click();
                break;
            }
        }
    }

    public void InputOnlyIpAddress(String[] ip) {
        String xpath = "#filterservices_onlyip_";
        for (int i = 1; i < 5; i++) {
            Selenide.$(xpath + String.valueOf(i)).clear();
            MyCommonAPIs.sleepi(3);
            Selenide.$(xpath + String.valueOf(i)).sendKeys(ip[i - 1]);
            MyCommonAPIs.sleepi(3);
        }
    }

    public void InputIpAddressRange(String[] ip1, String[] ip2) {
        String xpath1 = "#filterservices_iprange_startip";
        String xpath2 = "#filterservices_iprange_endip";
        for (int i = 1; i < 5; i++) {
            Selenide.$(xpath1 + String.valueOf(i)).clear();
            MyCommonAPIs.sleepi(3);
            Selenide.$(xpath1 + String.valueOf(i)).sendKeys(ip1[i - 1]);
            MyCommonAPIs.sleepi(3);
        }
        for (int i = 1; i < 5; i++) {
            Selenide.$(xpath2 + String.valueOf(i)).clear();
            MyCommonAPIs.sleepi(3);
            Selenide.$(xpath2 + String.valueOf(i)).sendKeys(ip2[i - 1]);
            MyCommonAPIs.sleepi(3);
        }
    }

    public void serviceblockingservicetypeselect(String typetext) {
        String xpath = "//ul[@class=\"ant-select-dropdown-menu  ant-select-dropdown-menu-root ant-select-dropdown-menu-vertical\"]/li";
        for (int i = 1; i < 45; i++) {
            SelenideElement serviceblockingservicetypeselect = $x(xpath + "[" + String.valueOf(i) + "]");
            if (serviceblockingservicetypeselect.getText().indexOf(typetext) != -1) {
                serviceblockingservicetypeselect.click();
                break;
            }
        }
    }
}
