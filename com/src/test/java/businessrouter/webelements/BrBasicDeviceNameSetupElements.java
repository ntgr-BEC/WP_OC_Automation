package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrBasicDeviceNameSetupElements {
    public SelenideElement devicename   = $("#editDeviceName_input1");
    public SelenideElement devicenamecancelbutton  = $("#editDeviceName_but1");
    public SelenideElement devicenameapplybutton   = $("#editDeviceName_but2");
    public SelenideElement devicenameeditwarning   = $x("//div[@class ='ant-form-explain']");

}
