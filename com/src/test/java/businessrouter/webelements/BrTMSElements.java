package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrTMSElements {
    public SelenideElement username   = $("#txtUserName");
    public SelenideElement password   = $("#txtPwd");
    public SelenideElement loginbutton   = $x("//input[@title ='Login']");
    public SelenideElement commoninterface  =  $x("//a[text()='Common Interface']");
    public SelenideElement command   = $("#command");
    public SelenideElement interfacetype  =  $("#interface_type");
    public SelenideElement dutip  =  $x("//*[@id=\"1.1\"]/table[1]/tbody/tr/td[2]/input");
    public SelenideElement dutport  =  $x("//*[@id=\"1.1\"]/table[3]/tbody/tr/td[2]/input");
    public SelenideElement hostip  =  $x("//*[@id=\"1.2\"]/table/tbody/tr/td[2]/input");
    public SelenideElement  parameter  =  $("#parameter_input");
    public SelenideElement  applybutton  =  $x("//input[@name='submit']");
    public SelenideElement result= $("#result");
    public SelenideElement backbutton = $x("//input[@name='back']");
    public SelenideElement resetbutton = $x("//input[@name='reset']");
    public SelenideElement cmdresult = $("#ret_show");
}
