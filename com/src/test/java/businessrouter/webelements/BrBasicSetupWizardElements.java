package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrBasicSetupWizardElements {
    public SelenideElement detectconnectionauto   = $x("//*[@id=\"WANDetc_radio\"]/label[1]/span[1]/input");
    public SelenideElement detectconnectionmanual  = $x("//*[@id=\"WANDetc_radio\"]/label[2]/span[1]/input");
    public SelenideElement qosspeedtestbutton   = $("#qos_quality_speedtest");
    public SelenideElement defineinternetbandwith   = $("#qos_quality_bandwidth");
    public SelenideElement autotoupdateferformance   = $("#AutoUpdateEnable");
    public SelenideElement updatenowbutton   = $("#qos_quality_updateNow");
    public SelenideElement newpassword   = $("#sysNewPasswd");
    public SelenideElement confirmnewpassword   = $("#sysConfirmPasswd");
    public SelenideElement securityquestion1   = $x("#sysConfirmPasswd//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[6]/div[2]/div/div/div");
    public SelenideElement answer1   = $("#answer1");
    public SelenideElement securityquestion2   = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[8]/div[2]/div/div/div");
    public SelenideElement answer2   = $("#answer2");
    public SelenideElement nextbutton   = $("#next_btn");
    
    public SelenideElement nowizardbutton = $("#WANDetc_radio_MyDetc");
    public SelenideElement wanpageflag = $("#WANAssign_radio_dhcp");

}