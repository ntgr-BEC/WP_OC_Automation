/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.HashMap;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author Tejeshwini K V
 */
 public class PRDashPageElements extends MyCommonAPIs {
     
   
     public String basicList = "//td[text()='%s']/..";
     public String uptime    = basicList + "/td[11]";
     public SelenideElement troubleshoot    = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[14]");
     public static SelenideElement                    domainName             = $("#pingAndTraceTest > div.row.m-b-20.d-sm-flex.align-items-center > div.col-sm-8 > div > input");
     public static SelenideElement                    testNow                = $x("//*[@id=\"pingRunTestButton\"]");
     public static SelenideElement                    erroralert             = $("//*[@id=\"divDPingTraceRoute\"]/div[4]/div/div[3]/div/div[2]/div/p[3]");
     public SelenideElement share              = $x("//*[@id=\"aModalFourSwitchBtn\"]");
     public SelenideElement shareemail         = $x("//*[@id=\"share_email_id\"]");
     public SelenideElement send               = $x("//button[text()='Send']");
     public SelenideElement alerttext          = $x("//div[@class='alert alert-success alert-dismissable']");
     public SelenideElement connectedclient              = $x("//*[@id=\"connectedHeader\"]");
     public SelenideElement disconnectedclient           = $x("//li[text()=\"Disconnected\"]");
     public SelenideElement staticclient                 = $x("//*[@id=\"staticHeader\"]");
     public SelenideElement clientparameter              = $x("//*[@id=\"connectedTab\"]/div/div/div[1]/table/thead/tr/th[1]/div/span[2]");
     public SelenideElement portparameter                = $x("//*[@id=\"connectedTab\"]/div/div/div[1]/table/thead/tr/th[2]");
     public SelenideElement MACparameter                 = $x("//*[@id=\"connectedTab\"]/div/div/div[1]/table/thead/tr/th[3]");
     public SelenideElement IPparameter                  = $x("//*[@id=\"connectedTab\"]/div/div/div[1]/table/thead/tr/th[4]");
     public SelenideElement VLANiDparameter              = $x("//*[@id=\"connectedTab\"]/div/div/div[1]/table/thead/tr/th[5]");
     public static SelenideElement editnetwork           = $x("//*[@id=\"headerLocImg\"]");
     public static SelenideElement gotoNetworksetup      = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[3]/a");
     public SelenideElement GoToManagementVLAN           = $("#tdtbdyFooterurl0");
     public SelenideElement Next                         = $x("(//button[text()='Next'])[8]");
     public SelenideElement Next1                        = $("button.saveBtn");
     public SelenideElement warning                      = $x("//*[@id=\"myModal\"]/div/div/div[1]/h4)[3]");
     public SelenideElement ok                           = $x("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
     
     
     
     
     public SelenideElement WANToglleenable                    = $x("//*[@id=\"wanIpForm\"]/div[1]/div[2]/div/div[1]/div/label/span");
     public SelenideElement WANTogllestatus                    = $x("//*[@id=\"wanIpForm\"]/div[1]/div[2]/div/div[1]/div/label/input");
     public SelenideElement WANSvae                            = $x("//*[@id=\"deviceLevelFirmware\"]/div[4]/button[2]");
     public SelenideElement PrimaryWLAN                        = $x("//*[@id=\"wanIpForm\"]/div[1]/div[2]/div/div[3]/div[1]/h5");
     public SelenideElement conectionType                      = $x("//*[text()=\"Connection Type\"]/../select");
     public SelenideElement DHCP                               = $x("//*[@class=\"dhcpBlock disableAccordian\"]");
     
     public SelenideElement enableQos                         = $x("//*[@id=\"deviceLevelFirmware\"]/div/div[3]/div[2]/div/div/div/div/div[2]/label/span");
     public SelenideElement Qosdownload                       = $x("//*[@name='download']");
     public SelenideElement Qosupload                         = $x("//*[@name='upload']");
     public SelenideElement QosdownloadUnit                   = $x("//*[@name='downloadUnit']");
     public SelenideElement QosuploadUnit                     = $x("//*[@name='uploadUnit']");
     public SelenideElement QosSave                           = $x("//*[@id=\"Qos_save\"]");
     
     public SelenideElement selectGateway              = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div[2]/div[1]/div[2]/div/div[1]/select");
     public SelenideElement warrningText               = $x("//*[@id=\"mdnsFirmwareInfo\"]/div/div/div[2]/p");
     public SelenideElement OkGotIt                    =  $x("//*[@id=\"mdnsFirmwareInfo\"]/div/div/div[3]/button");
     public SelenideElement Dropdown           = $x("//*[@id=\"divColMdAptn\"]/div[1]/span");
     public SelenideElement reboot             = $("#divColMdAptn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(2)");
     public SelenideElement rebootconfirm      = $x("//button[text()='Continue']");
     
     
     public SelenideElement editModule(String text) {
         //SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[15]/p");
         SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p");
         return Ssid;
     }
     
     public SelenideElement enterDevice(String text) {
         //SelenideElement device = $x("//td[text()='" + text + "']/../td[15]/p//i[1]");
         SelenideElement device = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");
         return device;
   }
   
    
    
}