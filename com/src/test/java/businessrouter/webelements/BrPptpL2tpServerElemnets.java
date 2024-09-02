package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrPptpL2tpServerElemnets {

    public SelenideElement username   = $("#txtUserName");
    public SelenideElement password   = $("#txtPwd");
    public SelenideElement loginbutton   = $x("//input[@title ='Login']");
    public SelenideElement continuebutton = $x("//input[@name ='button.continue.router_status']");
    public SelenideElement vpnlink  =  $("#mm3");
    public SelenideElement pptplink  =  $("#sm5");
    public SelenideElement pptpenable  =  $("#chkEnable");
    public SelenideElement pptpstartip1  =  $x("//input[@name ='pptpServerIPRange.StartIPAddress1']");
    public SelenideElement pptpstartip2  =  $x("//input[@name ='pptpServerIPRange.StartIPAddress2']");
    public SelenideElement pptpstartip3  =  $x("//input[@name ='pptpServerIPRange.StartIPAddress3']");
    public SelenideElement pptpstartip4  =  $x("//input[@name ='pptpServerIPRange.StartIPAddress4']");
    
    public SelenideElement pptpendip1  =  $x("//input[@name ='pptpServerIPRange.EndIPAddress1']");
    public SelenideElement pptpendip2  =  $x("//input[@name ='pptpServerIPRange.EndIPAddress2']");
    public SelenideElement pptpendip3  =  $x("//input[@name ='pptpServerIPRange.EndIPAddress3']");
    public SelenideElement pptpendip4  =  $x("//input[@name ='pptpServerIPRange.EndIPAddress4']");
    
    public SelenideElement pptpauthpap  =  $("#chkPap");
    public SelenideElement pptpauthchap  =  $("#chkChap");
    public SelenideElement pptpauthmschap  =  $("#chkMschap");
    public SelenideElement pptpauthmschap2  =  $("#chkMschapv2");
    public SelenideElement pptpencmppe  =  $("#chkMppe");
    public SelenideElement pptpencmppe128  =  $("#chkMppe128");
    public SelenideElement pptpencmppestateful  =  $("#chkMppeStateful");
    public SelenideElement pptpapplybutton = $x("//input[@name ='button.config.pptpServerIPRange.pptp_server']");
        
    public SelenideElement l2tplink  =  $("#sm3");
    public SelenideElement l2tpenable  =  $("#chkEnable");
    public SelenideElement l2tpstartip1  =  $x("//input[@name ='Xl2tpServerIPRange.StartIPAddress1']");
    public SelenideElement l2tpstartip2  =  $x("//input[@name ='Xl2tpServerIPRange.StartIPAddress2']");
    public SelenideElement l2tpstartip3  =  $x("//input[@name ='Xl2tpServerIPRange.StartIPAddress3']");
    public SelenideElement l2tpstartip4  =  $x("//input[@name ='Xl2tpServerIPRange.StartIPAddress4']");
    
    public SelenideElement l2tpendip1  =  $x("//input[@name ='Xl2tpServerIPRange.EndIPAddress1']");
    public SelenideElement l2tpendip2  =  $x("//input[@name ='Xl2tpServerIPRange.EndIPAddress2']");
    public SelenideElement l2tpendip3  =  $x("//input[@name ='Xl2tpServerIPRange.EndIPAddress3']");
    public SelenideElement l2tpendip4  =  $x("//input[@name ='Xl2tpServerIPRange.EndIPAddress4']");
    
    public SelenideElement l2tpauthpap  =  $("#chkPap");
    public SelenideElement l2tpauthchap  =  $("#chkChap");
    public SelenideElement l2tpauthmschap  =  $("#chkMschap");
    public SelenideElement l2tpauthmschap2  =  $("#chkMschapv2");
    public SelenideElement l2tpapplybutton = $x("//input[@name ='button.config.Xl2tpServerIPRange.l2tp_server']");
    
    public SelenideElement userlink = $("#mm4");
    public SelenideElement usersecondlink = $("#sm1");
    public SelenideElement useraddbutton = $x("//input[@name ='button.add.USERDBUsers.usersConfig.-1']");
    public SelenideElement pptpl2tpusername =$("#txtUserName");
    public SelenideElement pptpl2tpusertype = $("#selUserType");
    public SelenideElement pptpl2tppassword = $("#txtPwd");
    public SelenideElement pptpl2tpconfirmpassword = $("#txtCfmPwd");
    public SelenideElement userapplybutton = $x("//input[@name ='button.config.USERDBUsers.users.-1']");
    public SelenideElement newusernameoftable = $x("//*[@id=\"tblUsers\"]/tbody/tr/td[2]");
    public SelenideElement newusercheckbox = $x("//*[@id=\"tblUsers\"]/tbody/tr/td[1]");
    public SelenideElement userselectallbutton = $x("//input[@class ='submit submit3 bSelectall']");
    public SelenideElement userdeletebutton = $x("//input[@name ='button.delete.USERDBUsers.users']");
    
}
