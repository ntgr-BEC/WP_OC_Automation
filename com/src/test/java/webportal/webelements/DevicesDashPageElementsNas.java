/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

/**
 * @author zheli
 *
 */
public class DevicesDashPageElementsNas {
    public SelenideElement addDevice       = $("[data-tooltip=\"Add Device\"]");
    public SelenideElement search          = $("[data-tooltip=\"Search\"]");
    public SelenideElement MoreOptions     = $(".moreIcon backPurple");
    public SelenideElement serialNo        = $("#serialNo");
    public SelenideElement addDeviceBtn    = $(".addDeviceBtn");
    public SelenideElement deviceName      = $("#deviceName");
    public SelenideElement errorInfo       = $x("(//div[@id=\"myModal\"]//p)[1]");
    public SelenideElement errorOK         = $x("(//div[@id=\"myModal\"]//button[text()='OK'])[1]");
    public SelenideElement noDontDelete    = $(Selectors.byText("No, don't delete"));
    public SelenideElement deleteConfirm   = $(Selectors.byText("Delete"));
    public SelenideElement showErrorMsg    = $("#showErrorMsg");
    public SelenideElement addDeviceCancel = $x("//*[@id=\"divMaindevicesDash\"]/div[3]/div/div/div[3]/button[1]");
    public SelenideElement deviceStatus    = $("#spnCirDevIddevicesDash0");
    public SelenideElement addagain        = $(".alert.alert-danger.alert-dismissable");
    public SelenideElement nodevice        = $("td.dataTables_empty");
    public SelenideElement nasCloudButton  = $("#cloud-tab");
	
    public SelenideElement enterDeviceSummary(String serialNumber) {
        return $(Selectors.byText(serialNumber));
    }

    public SelenideElement editDevice(String text) {
        SelenideElement Device = $x("//i[1]/img[@data-deviceserial='" + text + "']/..");
        return Device;
    }

    public SelenideElement editModule(String text) {
        SelenideElement Device = $x("//img[@data-deviceserial='" + text + "']/ancestor::p");
        return Device;
    }

    public SelenideElement deleteDevice(String text) {
        SelenideElement Device = $x("(//img[@data-deviceserial='" + text + "']/..)[1]");
        return Device;
    }

    public SelenideElement editDeviceNameModule(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr/td[1]");
    }

    public SelenideElement editDeiveNameIcon(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr/td[1]/p//i/img");
    }

    public SelenideElement editDeviceNameText(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//input");
    }

    public SelenideElement editDeviceNameConfirm(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//span[1]/button");
    }

    public SelenideElement devicesName(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//p[1]/span");
    }

    public SelenideElement devicesStatus(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr/td[2]/span");
    }

    public String sDeviceModel = "//td[contains(@id, 'tdDevModlIddevicesDash')]";
    public String sDeviceIp    = "//td[contains(@id, 'tdNASDevIddevicesDash')]";
}
