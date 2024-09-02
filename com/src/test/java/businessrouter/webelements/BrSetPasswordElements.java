package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrSetPasswordElements {
    public SelenideElement oldpassword                  = $("#sysOldPasswd");
    public SelenideElement newpassword                  = $("#setpassword_2");
    public SelenideElement confirmpassword              = $("#sysConfirmPasswd");
    public SelenideElement passwordrecoveryenable       = $("#enable_recovery");
    public SelenideElement passwordcancelbutton         = $("#setpassword_9");
    public SelenideElement passwordapplybutton          = $("#setpassword_10");
    public SelenideElement changepassworderror          = $x("//*[@id=\"setpassword_2\"]/../../div");
    public SelenideElement changepassworddialogtitle    = $x("//*[@class=\"ant-confirm-body-wrapper\"]//span[@class=\"ant-confirm-title\"]");
    public SelenideElement changepassworddialogcontent  = $x("//*[@class=\"ant-confirm-body-wrapper\"]//div[@class=\"ant-confirm-content\"]");
    public SelenideElement changepassworddialogokbutton = $x("//*[@class=\"ant-confirm-btns\"]/button[@class=\"ant-btn ant-btn-primary\"]");

}
