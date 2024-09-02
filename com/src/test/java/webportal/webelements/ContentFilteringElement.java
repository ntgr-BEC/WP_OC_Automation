/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import org.apache.log4j.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.WiredPoESchedulesPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author Tejeshwini K V
 */
public class ContentFilteringElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("ContentFilteringElement");


    
    public boolean getCFStatus() {
        return CFstatus.is(Condition.checked);
    }
    
    
    
    public SelenideElement        CF                        = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[4]/a");
    public SelenideElement        CFcheck                   = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[1]/div/div/div[2]/label/input");
    public SelenideElement        CFstatus1                  = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div/div/div/div[2]/label/span");
    public SelenideElement        CFstatus                  = $x("//*[@id=\"CF_checkbox\"]");
    public SelenideElement        AllowCategories           = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div/a/span");
    public SelenideElement        BlockedCategories         = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[3]/div/div[2]/div[1]/div/a/span");
    public SelenideElement        AllowSearch               = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div/ul/li/div/div/input");
    public SelenideElement        BlockedSearch             = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[3]/div/div[2]/div[1]/div/ul/li/div/div/input");
    public SelenideElement        AllowSelect               = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[1]/div/div[2]/div[2]/div[1]/table/tbody/tr/td[1]/div/span[1]/label/i");
    public SelenideElement        DenySelect                = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[3]/div/div[2]/div[2]/div[1]/table/tbody/tr/td[1]/div/span[1]/label/i");
    public SelenideElement        ArrowRight                = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[2]/div[1]/button/img");
    public SelenideElement        ArrowLeft                 = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[2]/div[2]/div[2]/button/img");
    public SelenideElement        Save                      = $x("//*[@id=\"divCOnSecCf\"]/div[3]/button[2]");
    public SelenideElement        Cancel                    = $x("//*[@id=\"btnCancelCf\"]");
    public SelenideElement        CFTrail                   = $x("//*[@id=\"divMainOrbiCf\"]/div[5]/div/div/div[3]/button[1]");
    public SelenideElement        CFDisable                 = $x("//p[contains(text(), \"Are you sure you want to disable Content Filtering?\")]");
    public SelenideElement        CFconfirm                 = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button[1])[5]");
    public SelenideElement        SafeSearch                = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[1]/div/div/label/span");
    public SelenideElement        AddAllow                  = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[3]/div/div/div[1]/div/div[1]/span");
    public SelenideElement        AddBlock                  = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[4]/div/div/div[1]/div/div[1]/span");
    public SelenideElement        DomainString              = $x("//*[@id=\"cfForm\"]/div/div[2]/input");
    public SelenideElement        DomainDiscription         = $x("//*[@id=\"cfForm\"]/div/div[3]/input");
    public SelenideElement        AllowButton               = $x("//button[contains(text(), 'Allow')]");
    public SelenideElement        BlockButton               = $x("//button[contains(text(), 'Block')]");
    public SelenideElement        deletessidyes             = $x("//button[text() = 'Yes, Delete']");
    public SelenideElement        blocklisterror            = $x("//*[@id=\"showNotification2\"]/div");
    public SelenideElement        Closeblocklisterror       = $x("//*[@id=\"divMainOrbiCf\"]/div[4]/div/div/div[1]/button/img");
    public SelenideElement        WeeklyReport              = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[5]/div[2]/div/div[2]/label/input");
    public SelenideElement        MonthlyReport             = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[5]/div[2]/div/div[3]/label/input");
    public SelenideElement        DailyReport               = $x("//*[@id=\"divCOnSecCf\"]/div[2]/div/div[2]/div[5]/div[2]/div/div[1]/label/input");
    public SelenideElement        Sucessfullmessage         = $x("//*[@id=\"showNotification\"]/div");
    
    public SelenideElement        AddCFKey                  = $x("//*[@id=\"content\"]/div[2]/div/div/ul[2]/li/div/button");
    public SelenideElement        SendCFKey                 = $x("//*[@id=\"add-filtering-key\"]/div/input");
    public SelenideElement        AddsucessFully            = $x("//*[@id=\"main\"]/div/div[2]/div/div/div[3]/button");
    public SelenideElement        YesCF                     = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button[1])[5]");
    
    
    
    
    public SelenideElement editdomain(String domain) {
        SelenideElement Domain = $x("//span[text() = '" + domain + "']/../../td[5]/p/i/img/ancestor::p");
        return Domain;
    }
    
    public SelenideElement deleteDomain(String domain) {
        SelenideElement Domain = $x("//span[text() = '" + domain + "']/../../td[5]/p/i[3]");
        if (!Domain.exists()) {
            Domain = $x("//span[text() = '" + domain + "']/../../td[5]/p/i");
        }
        return Domain;
    }
    
    
    
    public SelenideElement        hamburgermenunew           = $x("//*[@id='notificationDrop']/../../ul/li/div");
    public static SelenideElement CFusercredits             = $x("//p[text()='" + WebportalParam.getLocText("Allocated Credits") + "']/../h2");
    public static String          availablecredits           = "//p[text()='" + WebportalParam.getLocText("Available Credits")+ "']/../h2";
    public static SelenideElement CFtotalgroup              = $x("//p[text()='" + WebportalParam.getLocText("Total Content Filtering Subscription Credits") + "']/../h2");
    public static SelenideElement accountmanager      = $x("//*[@id='notificationDrop']/../../ul//a[text()='" + WebportalParam.getLocText("Account Management") + "']");
    public static String          CFOrderTable       = "//span[contains(text(),'" + WebportalParam.getLocText("Content Filtering")+ "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr";
    public static SelenideElement openorderhis        = $x("//span[contains(text(),'" + WebportalParam.getLocText("Content Filtering") + "')]/..//i[2]");
    public static SelenideElement orderhistoryqty     = $x("//span[contains(text(),'" + WebportalParam.getLocText("Content Filtering")
    + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[1]//td[2]/span");
    public static SelenideElement CFservices                = $x("//div/a[text()='" + WebportalParam.getLocText("Content Filtering Services") + "']");


    public static SelenideElement orderhistoryqtytop     = $x("//span[contains(text(),'" + WebportalParam.getLocText("Content Filtering")
    + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[2]//td[2]/span");
    public static String          CFOrderTabletop       = "//span[contains(text(),'" + WebportalParam.getLocText("Content Filtering")+ "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[2]";
    public static SelenideElement CFTopUptotalgroup              = $x("//p[text()='" + WebportalParam.getLocText("Total Top-up Inspection Limit Pack Credits") + "']/../h2");
    public static String          availablecreditsTopUp           = "(//p[text()='" + WebportalParam.getLocText("Available Credits")+ "']/../h2)[2]";

}
