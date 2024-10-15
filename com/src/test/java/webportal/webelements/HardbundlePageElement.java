/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author Netgear
 *
 */
public class HardbundlePageElement extends MyCommonAPIs{
    
  
    public SelenideElement deleteDevice(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[16]/p//i[2]");
        return Ssid;
    }
    
    public SelenideElement editModule(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[16]/p");
        return Ssid;
    }
    
    
    public SelenideElement deviceExit(String text) {
        SelenideElement deviceExit = $x("//span[2]/span[2][contains(text(), '" + text + "')]");
        return deviceExit;
    }
   

    
    public static SelenideElement accountmanager             = $x(
            "//*[@id='notificationDrop']/../../ul//a[text()='" + WebportalParam.getLocText("Account Management") + "']");
    public SelenideElement        closeLockedWindow         = $x("//div[contains(@class,'borderRadius')]//button");
    public SelenideElement        deleteapyes              = $x("//button[@class = 'btn btn-danger'][text() = 'Delete']");
    public SelenideElement        settingsorquickview      = $x("//a[contains(@href,'wirelessSettings')]");
    public SelenideElement        OneYearInsightIncludedwithHardware   = $x("//span[contains(text(),'One Year Insight Included with Hardware')]//following::i[@class = 'icon icon-icon-collapse']");
    public SelenideElement        OneYearInsightIncludedwithHardware1   = $x("//span[contains(text(),'Insight Included with Hardware')]//following::i[@class = 'icon icon-icon-collapse']");
    public String                 showallocate                = "//span[text()='%s']//..//..//../td[5]/p";
    public String                 clickallocate                = "//span[text()='%s']//..//..//../td[5]/p/i/img";
    public SelenideElement        hamburgermenu     = $x("//*[@id='notificationDrop']/../../ul/li/a");
    public SelenideElement        hamburgermenunew  = $x("//*[@id='notificationDrop']/../../ul/li/div");
    public SelenideElement        BulkOnboarding  = $x("//button[@class = 'btn btn-primary'][text() = 'Update Location']");
    public SelenideElement        SelectAllDevice  = $x("//*[@id=\"content\"]/div[4]/div/div[1]/div/div/div[1]/div/table/thead/tr/th[1]/div/span/label/i");
    public SelenideElement        UpdateDeviceList  = $x("//button[@class = 'btn saveBtn'][text() = 'Update Devices List']");
    public SelenideElement        ViewList  = $x("//button[@class = 'btn saveBtn'][text() = 'View Devices']");
    public SelenideElement        notificationicon  = $x("//div[contains(@data-tooltip,'Notification')]");
    public SelenideElement        AddHardBundle  = $x("//button[contains(text(), 'Add Insight Included Device')]");
    public SelenideElement        ClickOnnext  = $x(" //button[@type= 'submit'][text() = 'Next']");
    public SelenideElement loginButtonNew = $("form[name*=loginForm] button");
    public SelenideElement loginPwdNew1   = $x("//*[@id='input_1']");
    public SelenideElement loginPwdNew    = $("#searchinput");
    public static String          loader           = "[class='loaderContainer']";
    public static int             timeout_element  = 130 * 1000;
    public static SelenideElement createpronextbutton    = $x("//button[text()='" + WebportalParam.getLocText("Next") + "']");
    public SelenideElement ProemailID   = $x("//input[@name = 'signupEmail']");
    public SelenideElement SelectLocation   = $x("//*[@id=\"gridView\"]/div/ul/li[1]/div/label/i");
    public SelenideElement AssignToOrganization   = $x("//button[text() = 'Assign to Organization']");
    public SelenideElement OrganizationName   = $x("//input[@type = 'text'][@name = 'newOrgName']");
    public SelenideElement Submit   = $x("//button[text() = 'Submit']");
    public SelenideElement        successMsg                 = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div[1]/div[2]/div[2]/div/ul/li/h3");
    public SelenideElement        ActivatedOn      = $x("//p[contains(text(),'Feb 23, 2022')]");
    public SelenideElement        ExpiredOn      = $x("//p[contains(text(),'Feb 23, 2027')]");
    
    public SelenideElement activationDate(String text) {
        SelenideElement activationDate = $x("//span[2]/span[2][contains(text(), '"+ text +"')]//..//..//..//..//../td[3]/p[2]");
        return activationDate;
    }

    public SelenideElement ExpiryDate(String text) {
        SelenideElement ExpiryDate = $x("//span[2]/span[2][contains(text(), '"+ text +"')]//..//..//..//..//../td[4]/p");
        return ExpiryDate;
    }
  
  
 
    public SelenideElement        logout      = $x("//*[@id=\"header\"]/div[2]/div/ul/li/div/div/div[2]");
    public SelenideElement        PurchaseHistory      = $x("//*[contains(text(), 'Purchase Order History')]");
    
    public SelenideElement getyear1(String text) {
        SelenideElement Ssid = $x("//*[text()='" + text + "']/../../../../../td[3]");
        return Ssid;
    }
    
    public SelenideElement getyear2(String text) {
        SelenideElement Ssid = $x("//*[text()='" + text + "']/../../../../../td[4]");
        return Ssid;
    }
    //AddedByPratik
    public SelenideElement srNounderOneYearInsightIncludedwithHardwarePRO(String text) {
        SelenideElement activationDate = $x("//span[contains(text(), '"+ text +"')]");
        return activationDate;
    }
}
