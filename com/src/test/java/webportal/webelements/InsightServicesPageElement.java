package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;

import static com.codeborne.selenide.Selenide.$$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class InsightServicesPageElement extends MyCommonAPIs {

    public SelenideElement        insightservicesbutton = $x("//img[contains(@src,'icon-insight-services.png')]");
    public static SelenideElement vpnproducttitle       = $x("//h4[text()='" + WebportalParam.getLocText("VPN Products") + "']");
    public static SelenideElement devicesupporttitle    = $x("//h4[text()='" + WebportalParam.getLocText("Device Support") + "']");
    public static SelenideElement captiveportaltitle    = $x("//h4[text()='" + WebportalParam.getLocText("Captive Portal Products") + "']");
    public static SelenideElement devicesviewbutton     = $x("//h4[text()='" + WebportalParam.getLocText("Device Support") + "']/..//..//..//button");
    public static SelenideElement vpnviewbutton         = $x("//h4[text()='VPN Products']/..//..//..//button"); //("//h4[text()='" + WebportalParam.getLocText("VPN Products") + "']/..//..//..//button");
    public static SelenideElement Businessvpnviewbutton = $x("//h4[text()='" + WebportalParam.getLocText("Business VPN") + "']/..//..//..//button");
    public static SelenideElement CFviewbutton         = $x("//h4[text()='" + WebportalParam.getLocText("Content Filtering") + "']/..//..//..//button");
    public static SelenideElement captiveportalview     = $x(
            "//h4[text()='" + WebportalParam.getLocText("Captive Portal Products") + "']/..//..//..//button");
    public static SelenideElement MultipackView     = $x(
            "//h4[text()='" + WebportalParam.getLocText("Insight Premium Subscriptions") + "']/..//..//..//button");

    public String          captiveprotalquantity = "(//div[@class='card-ap']/span)";
    public SelenideElement captiveportalthreeyr    = $x("//label[contains(text(),' 3 Years ')]");
    public SelenideElement captiveportaloneyr  = $x("//label[contains(text(),' 1 Year ')]");

    public String basicele = "//h4[contains(text(),'%s Year')]/..";
    public String buynum   = "(" + basicele + "//button)[2]";
    public String buynow   = basicele + "/div[2]/button";

    public String devicecredits = "div.box-ap span.circle";
    public String deviceyears   = "//label[contains(text(),'%s')]";
    public static SelenideElement dropbilling   = $("#BilingHeader > h2 > span");
    public static SelenideElement Savecount     = $x("//*[@id=\"sumit_count\"]");
    public SelenideElement Billingdropdown         = $x("(//h2[text()='Billing Information ']/../..//span)");

    public static SelenideElement butnumnext   = $x("(//span[text()='" + WebportalParam.getLocText("Qty") + "']/..//button)[2]");
    public static SelenideElement buynownext   = $x("//button[text()=' Buy Now ']");  //$x("//*[@id=\"captivePortal-feature-ByuNw\"]");
    public static SelenideElement buynownext1   = $x("//button[text()='Buy Now']");
    public static SelenideElement checkoutnext = $x("//button[text()='" + WebportalParam.getLocText("Next") + "']");
    public static SelenideElement butnumnextCF   = $x("//*[@id=\"increment_Button\"]/img");

//    public static SelenideElement billingfirstname     = $x("//h5[text()='" + WebportalParam.getLocText("First Name") + "']/../input");
//    public static SelenideElement billinglastname      = $x("//h5[text()='" + WebportalParam.getLocText("Last Name") + "']/../input");
    public SelenideElement        billingemail         = $x("//input[@type='email']");
//    public SelenideElement        billingstreetaddress = $x("//input[@name='address']");
//    public SelenideElement        billingcity          = $x("//input[@name='city']");
//    public SelenideElement        billingzip           = $x("//input[@name='postalCode']");
//    public SelenideElement        billingcountry       = $x("//select[@name='country']");
//    public SelenideElement        billingstate1        = $x("//select[@name='state']");
//    public SelenideElement        billingstate2        = $x("//input[@name='state']");
    
    public SelenideElement        billingfirstname                     = $("#firstname");
    public SelenideElement        billinglastname                      = $("#lastname");
    public SelenideElement        billingstreetaddress                 = $("#address");
    public SelenideElement        billingcity                          = $("#city");
    public SelenideElement        billingzip                           = $("#zipcode");
    public SelenideElement        billingstate1                        = $x("//select[@name='state']");
    public SelenideElement        billingstate2                        = $x("//input[@name='state']");
    public SelenideElement        billingstate3                        = $("#state-text");
    public SelenideElement        billingcountry                       = $("#country");
    public SelenideElement billingicon  = $x("//*[@id=\"cartMain-BilingHeader-span\"]/i");
    public SelenideElement billingicon1  = $x("//*[@id=\"billing_icon_checked\"]");
    public SelenideElement        billingvatnum          = $x("//h5[text()='VAT Registration Number']/../input");
    public static SelenideElement savebutton             = $x("//button[text()='" + WebportalParam.getLocText("Save") + "']");
    public SelenideElement        paymentcardnumber      = $x("//input[@id='cc_no']"); //$("#cc_no");
    public SelenideElement        Termsandcondition      = $x("//input[@id='styled-checkbox-1']/../label");
    public SelenideElement        Carddropdown           = $("#PaymentHeader span");
    public SelenideElement        paymentcvvnumber       = $("#cvv");
    public SelenideElement        paymentexpirationmonth = $("#cc_exp_mm");
    public SelenideElement        paymentexpirationyear  = $("#cc_exp_yyyy");
    public String                 paymentautorenewstatus = "//input[@name='AutoRenew']";
    public String                 paymentautorenew       = paymentautorenewstatus + "/../label/span";
    public static SelenideElement cardNumberError        = $x("//*[@id=\"invalid-card\"]");
    public static SelenideElement expiryMonthError       = $x("//*[@id=\"invalid-cvv\"]");
    public static SelenideElement expiryYearError        = $x("//p[contains(text(),'Please select a year.')]");
    public static SelenideElement cvvCodeError           = $x("//p[contains(text(),'Please check cvv number.')]");

    public String                 subTotalAndTax    = "//*[@id=\"orderSumary-block-text\"]";
    public String                 amounttax    = "//*[@id=\"orderSumary-block-strong\"]";
    public SelenideElement        totalPrice        = $x("//*[@id=\"orderSumary-Total-curncy\"]");
    public static SelenideElement placeyourorder    = $x("//button[text()='" + WebportalParam.getLocText("Place Your order") + "']");
    public static SelenideElement placeyourordernew = $x("//button[text()='" + WebportalParam.getLocText("Place Your Order") + "']");
    public static SelenideElement placeYourOrder    = $x("//button[text()='" + WebportalParam.getLocText("Place your order") + "']");
    public static SelenideElement gotomainpage      = $x("//a[text()='Go to dashboard']");
    public static SelenideElement cardError         = $x("//div[text()='" + WebportalParam.getLocText("Invalid Credit Card Details") + "']");
    
    
    public static SelenideElement ServiceSubscription        = $x("(//*[@id=\"captivePortal-ProductName-span\"])[1]");
    public static SelenideElement TopupInspection            = $x("(//*[@id=\"captivePortal-ProductName-span\"])[2]");
    
//    public static SelenideElement EmployeeHomeSite           = $x("//*[@id=\"bussinessvpn-ProductNme-span-0\"]");  //$x("(//*[@id=\"bussinessvpn-ProductNme-span\"])[1]");
//    public static SelenideElement MicroOffice9User           = $x("//*[@id=\"bussinessvpn-ProductNme-span-1\"]");  //$x("(//*[@id=\"bussinessvpn-ProductNme-span\"])[2]");
//    public static SelenideElement MicroOffice15User          = $x("//*[@id=\"bussinessvpn-ProductNme-span-2\"]");  //$x("(//*[@id=\"bussinessvpn-ProductNme-span\"])[3]");
//    public static SelenideElement SmallOffice25User          = $x("//*[@id=\"bussinessvpn-ProductNme-span-3\"]");  //$x("(//*[@id=\"bussinessvpn-ProductNme-span\"])[4]");
//    public static SelenideElement SmallOffice50User          = $x("//*[@id=\"bussinessvpn-ProductNme-span-4\"]");  //$x("(//*[@id=\"bussinessvpn-ProductNme-span\"])[5]");
    
    public static SelenideElement EmployeeHomeSite           = $x("//h2[contains(text(), 'Home Office, Package 1')]/../span");
    public static SelenideElement MicroOffice9User           = $x("//h2[contains(text(), 'Micro Office, Package 1')]/../span");
    public static SelenideElement MicroOffice15User          = $x("//h2[contains(text(), 'Micro Office, Package 2')]/../span");
    public static SelenideElement SmallOffice25User          = $x("//h2[contains(text(), 'Small Office, Package 1')]/../span");
    public static SelenideElement SmallOffice50User          = $x("//h2[contains(text(), 'Small Office, Package 2')]/../span");
    
    
    public static SelenideElement Quantity   = $x("(//*[@id=\"increment_Button\"]/img)[2]");
    public static SelenideElement Quantity1   = $x("//body/app-root[1]/app-captiveportal[1]/div[2]/div[1]/div[7]/div[2]/div[1]/div[1]/div[1]/span[2]/button[1]/img[1]");
    public static SelenideElement Buynum   = $x("//*[@id=\"captivePortal-feature-ByuNw\"]");
    
    
//    device contract elements by Tejeshwini
    public static SelenideElement continueButton   = $x("//*[@id=\"succesPage-ContentWrapper\"]/div/div[2]/a");
    public static SelenideElement submit   = $x("//*[@id=\"MainContent_btnPurchaseContractSubmit\"]");
    public static SelenideElement Pro1Device1Year   = $x("//*[@id=\"a9F800000004LmREAU\"]");
    public static SelenideElement Pro1Device3Year   = $x("//*[@id=\"a9F800000004LmIEAU\"]");
    public static SelenideElement Pro1Device1Year1   = $x("//*[@id=\"a9F800000004LnDEAU\"]");
    public static SelenideElement Pro1Device3Year1   = $x("//*[@id=\"a9F800000004LnSEAU\"]");
    public SelenideElement DeviceSupport(String text) {
        SelenideElement Ssid = $x(" //p[contains(text(), '"+ text +"')]/../../div[2]/a");
        return Ssid;
    }
    
    public SelenideElement Devicecontract(String text) {
        SelenideElement Ssid = $x("(//*[contains(text(), '"+ text +"')])[last()]");
        return Ssid;
    }
    
    public SelenideElement ContractName(String text) {
        SelenideElement Ssid = $x("(//*[contains(text(), '"+ text +"')])[last()]/..//td[2]");
        return Ssid;
    }
    public SelenideElement PurchaeContract(String text) {
        SelenideElement Ssid = $x("(//*[contains(text(), '"+ text +"')])[last()]/..//td[3]");
        return Ssid;
    }
    public SelenideElement ExpireContract(String text) {
        SelenideElement Ssid = $x("(//*[contains(text(), '"+ text +"')])[last()]/..//td[4]");
        return Ssid;
    }
    
    public SelenideElement        continuenow         = $x("//a[text()='Continue']");
    public SelenideElement        clkonCaptivePortals   = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]/div[5]/a");
                                                         
    //marketplacepayment
    
    public SelenideElement        marketPlaceIcon                       = $x("//img[@src='assets/img/Header/icon-insight-services.png']");
    public SelenideElement        captivePortalServicesHeading          = $x("//h4[text()='Captive Portal Products']");
    public SelenideElement        viewProductCaptiveBtn                 = $x("(//button[text()='View Products'])[2]");
    public SelenideElement        businessVPNHeading                    = $x("//h4[text()='Business VPN']");
    public SelenideElement        viewProductBVPNBtn                    = $x("(//button[text()='View Products'])[1]");
    public SelenideElement        deviceSupportHeading                  = $x("//h4[text()='Device Support']");
    public SelenideElement        viewProductDeviceSupportBtn           = $x("(//button[text()='View Products'])[3]");
    public SelenideElement        insightPremiumServicesHeading         = $x("//h4[text()='Insight Premium Subscriptions']");
    public SelenideElement        viewProductinsightPremiumServicesBtn  = $x("(//button[text()='View Products'])[4]");
    public SelenideElement        vpnProductsHeading                    = $x("//h4[text()='VPN Products']");
    public SelenideElement        viewProductvpnProductsBtn             = $x("(//button[text()='View Products'])[5]");
    public SelenideElement        openInsightIncludedwithHardware      = $x("//h3/span[contains(text(), 'Insight Included with Hardware')]/following-sibling::span/i[2]");
    public ElementsCollection     InsightIncludedwithHardwareDetails    = $$(Selectors.byXpath("//table[@id=\"DataTables_Table_5\"]//td//div"));

    
    public SelenideElement OrbiProAXExit(String text) {
        SelenideElement targetText = $x("//span[2]/span[2][contains(text(), '" + text + "')]/../../../p[2]");
        return targetText;
    }
    
    public SelenideElement OrbiProexpairy(String text) {
        SelenideElement targetText = $x("(//span[2]/span[2][contains(text(), '" + text + "')]/../../../../../td[4]/p)[2]");
        return targetText;
    }
    
    //vpn1year
    
    public SelenideElement vpnBuyNowButton =$x("//div[@id=\"productDetail-contentBlock\"]//button[@id=\"productDetail-CatCustom-Button\"]");
    public SelenideElement buyIcp =$x("//*[contains(text(),'Buy Instant Captive Portal Key')]");
 
    public SelenideElement humberger =$x("//*[@id=\"header\"]/div[2]/div/ul/li/div/div/div[2]/p[1]");
    public SelenideElement account   =$x("//*[@id=\"header\"]/div[2]/div/ul/li/ul/li[1]/a");
    public SelenideElement captivePortalServices   =$x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]/div[4]/a");
    public SelenideElement vpnServices   =$x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]/div[3]/a");
    public SelenideElement buyVpn =$x("//*[@id=\"content\"]/div[4]/div/div[2]/div[1]/div[2]/div/button");
    
}
