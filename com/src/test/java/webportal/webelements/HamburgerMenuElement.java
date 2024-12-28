package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class HamburgerMenuElement extends MyCommonAPIs {
    //
    public SelenideElement        closeLockedWindow = $x("//div[contains(@class,'borderRadius')]//button");
    public SelenideElement        hamburgermenu     = $x("//*[@id='notificationDrop']/../../ul/li/a");
    public SelenideElement        hamburgermenunew  = $x("//*[@id='notificationDrop']/../../ul/li/div");
    public SelenideElement        notificationicon  = $x("//div[contains(@data-tooltip,'Notification')]");
    public SelenideElement        lochead           = $x("//*[@id=\"headerLocName\"]");
    public SelenideElement        locClick          = $x("//*[@id=\"header\"]/div[2]/ul/li/ul/li/div/div[3]/a[1]");
    public static SelenideElement errorokbutton     = $x("//h4[text()='Error']/../../..//button[text()='" + WebportalParam.getLocText("OK") + "']");

    public static SelenideElement addInsightDevices = $x("//button[text()='" + WebportalParam.getLocText("Add Insight Included Device") + "']");
    

    public SelenideElement activationDate(String text) {
        SelenideElement activationDate = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[4]/p[2]");
        SelenideElement activationDate1 = $x("//span[contains(text(), '" + text + "')]/../../../../../td[3]/p[2]");
        SelenideElement activationDate2 = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[4]/p[1]");
        if(activationDate.isDisplayed()) {
           System.out.println("old one");
        }else if(activationDate1.exists()){
             activationDate = $x("//span[contains(text(), '" + text + "')]/../../../../../td[3]/p[2]");
         }else {
             activationDate = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[4]/p[1]");
         }
        return activationDate;
    }

    public SelenideElement ExpiryDate(String text) {
        SelenideElement ExpiryDate = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[5]/p");
        SelenideElement ExpiryDate2 = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[5]/p[2]");
        if(ExpiryDate.isDisplayed()) {
            System.out.println("old one");
         }else {
              ExpiryDate = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[5]/p[2]");
          }
        return ExpiryDate;
    }

    public SelenideElement Done = $x("//span[@class = 'ng-scope'][text() = 'Done']");

//    public static SelenideElement accountmanager             = $x("//*[@id='notificationDrop']/../../ul//a[text()='" + WebportalParam.getLocText("Account Management") + "']" );
    public static SelenideElement accountmanager             = $x("(//*[@id='notificationDrop']/../../ul//a)[1]");
    public static SelenideElement accountmanager1            = $x("//*[text()='Account Management'])[1]");
    public SelenideElement        accountemail               = $("#hEmailsubscriptions");
    public static SelenideElement vpnservices                = $x("//div/a[text()='" + WebportalParam.getLocText("VPN Services") + "']");
    public SelenideElement        InstantVPN                 = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div[1]/div[1]/ul/li[2]");
    public SelenideElement        addvpnkey                  = $x("//button[@data-target='.AddVpnLicenseKey']");
    public SelenideElement        enterkey                   = $x(
            "//div[@class='modal fade AddVpnLicenseKey in']//input[@id='vpnLicenseKeyValue']");
    public SelenideElement        closeenterkey              = $x("//div[@class='modal fade AddVpnLicenseKey in']//button[@class='close']");
    public SelenideElement        vpnwarning                 = $x("//div[@aria-label='close']/..//p");
    public static SelenideElement totalvpncredits            = $x(
            "//p[text()='" + WebportalParam.getLocText("Total VPN Group Credits") + "']/../h2");
    public static SelenideElement vpntotalgroup              = $x("//p[text()='" + WebportalParam.getLocText("Total Groups") + "']/../h2");
    public static SelenideElement vpntotalgroupnew           = $x(
            "//p[text()='" + WebportalParam.getLocText("Total Allocated Credits") + "']/../h2");;
    public static SelenideElement vpnusercredits             = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div[2]/div/div[1]/div/div[4]/div/h2");
    public static String          availablecredits           = "//p[text()='" + WebportalParam.getLocText("Available Credits")
            + "']/../h2[text()='%s']";
    public static String          deletecurrentdevicecredit  = "//button[text()='" + WebportalParam.getLocText("Buy Device Credits Pack")+ "']/../../..//div[contains(@id,'DataTables_Table')]//tr/td[1]";
    public String                 deletecurrentservicesmany  = "//div[@id='proCurrLicenseTable_wrapper']//tr/td[1]";
    public SelenideElement        deletecurrentservices      = $x(deletecurrentservicesmany);
    public static SelenideElement cancelservices             = $x("//button[text()='" + WebportalParam.getLocText("Cancel Service") + "']");
    public static SelenideElement cancelAutorenewal          = $x("//button[text()='Cancel autorenewal']");
    public static SelenideElement yesokayBtn                 = $x("//button[text()='Yes, cancel']");
    public SelenideElement        cancelvpnpopuptext         = $x("//div[contains(@class,'cancelVpnService in')]//p");
    public static SelenideElement cancelservicesyes          = $x("(//button[text()='Cancel'])[4]");
    public static SelenideElement cancelservicesyesnew       = $x("//button[text()='" + WebportalParam.getLocText("Yes, cancel") + "']");
    public static SelenideElement creditsAllocation          = $x("(//div/a[text()='" + WebportalParam.getLocText("Credit Allocation") + "'])[2]");
    public String                 allocatedCredits           = "//span[text()='%s' and @class='ManagerClTxt nameOverFlow']/../../../td[%s]";
    public static SelenideElement totalDevicesCredits        = $x("//p[text()='" + WebportalParam.getLocText("Total Device Credits") + "']/../h2");
    public static SelenideElement totalIcpCredits            = $x(
            "//p[text()='" + WebportalParam.getLocText("Total Instant Captive Portal Credits") + "']/../h2");
    public SelenideElement        searchOrgName              = $(".icon-search");
    public SelenideElement        inputOrgName               = $x("//*[@placeholder=\"Organization Name\"]");
    public static SelenideElement deallocate                 = $x("//a[text()='" + WebportalParam.getLocText("Deallocate") + "']");
    public static String          deallcateDev               = "//span[text()='" + WebportalParam.getLocText("Device Credits") + "']/../..";
    public SelenideElement        deallocateDevCredits       = $x(deallcateDev + "//div[contains(@class,'firstLast')]");
    public SelenideElement        selectDeallocateDevCredits = $x(deallcateDev + "/..//i");
    public static String          deallcateIcp               = "//span[text()='" + WebportalParam.getLocText("Instant Captive Portal Credits")
            + "']/../..";
    public SelenideElement        deallocateIcpCredits       = $x(deallcateIcp + "//div[contains(@class,'firstLast')]");
    public SelenideElement        selectDeallocateIcpCredits = $x(deallcateIcp + "/..//i");
    public String                 selectOrgDeallocate        = "//span[text()='%s']/..//i";
    public String                 inputDeallocateCredits     = "(//span[text()='%s']/../../..//input)[2]";
    public static SelenideElement searchBtn                  = $x("//button[text()='" + WebportalParam.getLocText("Search") + "']");
    public String                 allocateBtn                = "//span[text()='%s']/../..//span[contains(@class,'pointerCursor')]";
    public String                 showallocate                = "//span[text()='%s']//..//..//../td/p";
    public String                 clickallocate                = "//span[text()='%s']//..//..//../td/p/i/img";

    public String                 creditsCount               = "(//span[contains(@class,'signInText')])[%s]";
    public String                 creditsPlusBtn             = "(//img[contains(@src,'plus-circle')])[%s]";
    public String                 totalCredits               = "(//div[contains(@class,'myDevicesTable')])[%s]//td[1]";
    public String                 usedCredits                = "(//div[contains(@class,'myDevicesTable')])[%s]//td[2]";
    public String                 unusedCredits              = "(//div[contains(@class,'myDevicesTable')])[%s]//td[3]";
    public SelenideElement        devCreditsAllocate         = $x("(//i[contains(@class,'icon-icon-collapse')])[1]");
    public SelenideElement        icpCreditsAllocate         = $x("(//i[contains(@class,'icon-icon-collapse')])[2]");
    public SelenideElement        vpnCreditsAllocate         = $x("(//i[contains(@class,'icon-icon-collapse')])[3]");
    public SelenideElement        successMsg                 = $x("//*[@id=\"AllocateCreditErr\"]/div/a");
    public SelenideElement        errorMsg                   = $x("//div[@id='AllocateCreditErr1']/div");
    public SelenideElement        allocateButton             = $x("//button[contains(@class,'saveBtn')]");
    public static SelenideElement subscriptions              = $x("//div/a[text()='" + WebportalParam.getLocText("Subscriptions") + "']");
    public static SelenideElement purchaseorderhistory       = $x("//div/a[text()='" + WebportalParam.getLocText("Purchase Order History") + "']");
    public static SelenideElement upgradebutton              = $x("//button[contains(text(),'OK, Got it')]");
    // public static SelenideElement nopurchasemessage = $x("//p[contains(text(),'No purchases to display')]");
    public static String          directPurchaseHistoryHead       = "//span[text()='"
            + WebportalParam.getLocText("Insight Premium Subscription - Direct Purchase") + "']/../";
    public SelenideElement        dropDownPremiunDirectPurchase   = $x("(" + directPurchaseHistoryHead + "/i)[2]");
    public SelenideElement        directPurchaseHistoryTdOne      = $x(directPurchaseHistoryHead + "..//td[1]");
    public SelenideElement        directPurchaseHistoryTdTwo      = $x(directPurchaseHistoryHead + "..//td[2]");
    public SelenideElement        directPurchaseHistoryTdThree    = $x(directPurchaseHistoryHead + "..//td[3]");
    public SelenideElement        directPurchaseHistoryTdFour     = $x(directPurchaseHistoryHead + "..//td[4]");
    public static String          directPurchaseHistoryProHead    = "//span[contains(text(),'"
            + WebportalParam.getLocText("Insight Pro Subscription - Direct Purchase") + "')]/../";
    public SelenideElement        dropDownProDirectPurchase       = $x("(" + directPurchaseHistoryProHead + "/i)[2]");
    public SelenideElement        directPurchaseProHistoryTdOne   = $x(directPurchaseHistoryProHead + "..//td[1]");
    public SelenideElement        directPurchaseProHistoryTdTwo   = $x(directPurchaseHistoryProHead + "..//td[2]");
    public SelenideElement        directPurchaseProHistoryTdThree = $x(directPurchaseHistoryProHead + "..//td[3]");
    public SelenideElement        directPurchaseProHistoryTdFour  = $x(directPurchaseHistoryProHead + "..//td[4]");
    public SelenideElement        directPurchaseProHistoryTdFive  = $x(directPurchaseHistoryProHead + "..//td[5]");
    public static SelenideElement proCurrentSubscription          = $x("//h3[text()='" + WebportalParam.getLocText("Insight Pro") + "']");
    public static SelenideElement proSubCancelBanner              = $x(
            "//p[contains(text(),'" + WebportalParam.getLocText("The selected subscription has been cancelled successfully.") + "')]");
    public String                 proSubscriptionTable            = "//span[text()='Active Subscription Keys']/../..//td[text()='N/A']";
    public SelenideElement        proSubscriptionTdOne            = $x(proSubscriptionTable + "/../td[1]");
    public SelenideElement        proSubscriptionTdTwo            = $x(proSubscriptionTable + "/../td[2]");
    public SelenideElement        proSubscriptionTdThree          = $x(proSubscriptionTable + "/../td[3]");
    public SelenideElement        proSubscriptionTdFour           = $x(proSubscriptionTable + "/../td[4]");

    public SelenideElement currentsubscriptionpremium = $x(
            "//*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[2]/div[1]/div[2]/div/div/ul/li[1]/p");

    public SelenideElement CancelSubscriptionformpremiumanually = $x(
            "//*[@id=\"premiumSubscriptionPackDiv\"]/div/div[2]/div[1]/div[2]/div/div/ul/li[1]/p");
    public SelenideElement Unlimited                            = $x("//*[text() = 'Unlimited']");
    //public SelenideElement        Unlimited              = $x("//*[@id=\"premiumSubscriptionPackDiv\"]/div/div[1]/div/div/div[1]/p[1]");
    //public SelenideElement        Unlimited              = $x("//p[text() = 'Unlimited']");
    public SelenideElement        trytrialbutton              = $("#btnDefaultTrialsubscriptions");
    public SelenideElement        currentsubscription         = $("#hplanNamesubscriptions");
    public static SelenideElement currentSubscriptionNew      = $x("//h3[text()='" + WebportalParam.getLocText("Type") + "']/../p");
    public static SelenideElement subscriptionTableActivation = $x("//h3[text()='" + WebportalParam.getLocText("Activation") + "']/../p");
    public static SelenideElement subscriptionTableExpiration = $x("//h3[text()='" + WebportalParam.getLocText("Expiration") + "']/../p");
    public static SelenideElement subscriptionTableCanceled   = $x("//h3[text()='" + WebportalParam.getLocText("Expiration") + "']/../p[2]");
    public SelenideElement        nopurchasemessage           = $x("//span[contains(text(),'Insight Premium Trial Subscription')]");
    public SelenideElement        nopurchasemessage1          = $x("//p[contains(text(),'Sorry, we could not find any relevant results.')]");
    public SelenideElement        iconsearch                  = $x("//span[@class='icon-search']");
    public SelenideElement        canceliconsearch            = $x("//i[@class='icon icon-group-2x']");
    public SelenideElement        inputtextiniconsearch       = $x("//input[@id='purchaseHistorySearch']");
    public SelenideElement        availableprovpncredits      = $x("//p[contains(text(),'Available Credits')]");
    public SelenideElement        availableinsightprocredit   = $x("//p[contains(text(),'Available Credits')]");
    public SelenideElement        subscriptionkeytext         = $x("(//span[text()='Instant Captive Portal']/../..//p[contains(text(),'Subscription key')])[1]");
    public SelenideElement        subscriptionkeytext1        = $x("//p[contains(text(),'Subscription key')]");
    public SelenideElement        prouserinsightlicensecount  = $x("//div[@class='networksetting AddDeviceAcc']//div[1]//h3[1]//span[1]");
    public SelenideElement        prouservpnlicensecount      = $x("//div[@class='SubsAccordianBlock']//div[2]//h3[1]//span[1]");
    public SelenideElement        instantcaptiveportalcount   = $x("(//span[contains(text(),'Instant Captive Portal')])[2]");
    public SelenideElement        defaultfilter               = $x("//option[contains(text(),'Last Year')]");
    public SelenideElement        lastyearfilter              = $x("//option[contains(text(),'Last 30')]");
    public SelenideElement        lastquarterfilter           = $x("//option[contains(text(),'Last Quarter')]");
    public SelenideElement        defaultcategoryfilter       = $x("//select[@name='prodCategory']");
    public SelenideElement        allcategoryfilter           = $x("(//option[contains(text(),'All')])[2]");
    public SelenideElement        insightsubscription         = $x("//option[contains(text(),'Insight Subscriptions')]");
    public SelenideElement        insightvpnfilter            = $x("//option[contains(text(),'Insight VPN')]");
    public SelenideElement        insightcaptivefilter        = $x("//option[contains(text(),'Insight Captive Portal')]");
    public SelenideElement        insightpremiumsubscription  = $x("//option[contains(text(),'Insight Premium Subscriptions')]");
    public SelenideElement        instantcaptivefilter        = $x("//option[contains(text(),'Instant Captive Portal')]");
    public SelenideElement        insightprofilter            = $x("//option[contains(text(),'Insight Pro Subscription - Direct Purchase')]");
    public SelenideElement        devicesupportcontracts      = $x("//option[contains(text(),'Device Support and Contracts')]");
    public SelenideElement        provpnsubscription          = $x("//option[contains(text(),'Insight Pro VPN')]");
    public SelenideElement        insightprosubscription      = $x("//option[contains(text(),'Pro user Insight Licenses')]");
    public SelenideElement        insightprosubscription1     = $x("//option[contains(text(),'Insight Pro Subscriptions')]");
    public SelenideElement        collapse                    = $x("//i[@class='icon icon-icon-collapse']");
    public SelenideElement        collapsecaptiveportal       = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div[2]/div/div[3]/h3/span[2]/i[1]");
    public SelenideElement        collapseprovpn              = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div[2]/div/div[2]/h3/span[2]/i[2]");
    public SelenideElement        VPNcurrency                 = $x("//span[text() ='Insight Instant VPN - 1 Yr-ZAR']");
    public SelenideElement        Multipackcurrency           = $x("//span[text() ='Insight Premium 5 pack Sub,1Yr HKD']");
    public SelenideElement        collapseinsigtdivcredits    = $x("//div[@class='networksetting AddDeviceAcc']//div[1]//h3[1]//span[2]//i[1]");
    public SelenideElement        expandcaptiveportal         = $x("//span[contains(text(),'Instant Captive Portal')]/../..//i[@class='icon icon-icon-collapse']");
    public SelenideElement        expandprovpn                = $x("//div[@class='SubsAccordianBlock']//div[2]//h3[1]//span[2]//i[2]");
    public SelenideElement        expandinsigtdivcredits      = $x("//div[@class='networksetting AddDeviceAcc']//div[1]//h3[1]//span[2]//i[2]");
    public SelenideElement        icpcollapse                 = $x("//span[contains(text(),'Instant Captive Portal')]/../..//i[2]");
    public SelenideElement        icpcurrency                 = $x("(//*[contains(@class, 'fontSemiBold colorBlack fontSize14')])[3]");
    public SelenideElement        icppurchase                 = $x("//table[@id='DataTables_Table_14']//tr[@class='odd']");
    public SelenideElement        prouservpnlicense           = $x("//p/span[text()='Insight Pro VPN']");
    public SelenideElement        instantcaptiveportal        = $x("//p/span[text()='Instant Captive Portal']");
    public SelenideElement        prouserinsightlicense       = $x("//p/span[text()='Insight Pro Subscriptions']");
    public SelenideElement        premiumuserinsightlicense   = $x("//h3/span[text()='Insight Subscriptions']");
    public SelenideElement        creditoption                = $x("//span[@class='colorPurple ManagerClTxt fontSize14 pointerCursor']");
    public SelenideElement        activatedate                = $x("//th[contains(text(),'Activated On')]");
    public SelenideElement        expiredate                  = $x("//th[contains(text(),'Expires On')]");
    public SelenideElement        endon                       = $(
            "#_divConSecsubscriptions > div:nth-child(2) > div > div.premiumSubscriptionBlock > div:nth-child(1) > div.swichPortPad25.m-b-30 > div > div > ul > li:nth-child(3) > p");
    public SelenideElement        moreneeded                  = $("#liAfterFreeDeviceSubscription");
    public SelenideElement                 subButton                   = $x("//*[@id=\"DataTables_Table_4\"]/tbody/tr/td[1]/div/div/button"); //pri-qa
    public SelenideElement                 cancelSubscriptionButton    = $x("//button[text()='Cancel Subscription']");
    public SelenideElement        subButton1                 = $x("(//div[@class=\"ButtonBlockPop small-buttons\"]/button)[2]");;  //maint-beta
    public static SelenideElement deviceCreditsNum            = $x("//button[text()='" + WebportalParam.getLocText("Buy Device Credits Pack")
            + "']/../../..//div[contains(@id,'DataTables_Table')]//td[4]/span");
    public static SelenideElement deviceCreditsStatus         = $x("//button[text()='" + WebportalParam.getLocText("Buy Device Credits Pack")
            + "']/../../..//div[contains(@id,'DataTables_Table')]//td[5]/span");

    public static SelenideElement managenotifications        = $x("//div/a[text()='" + WebportalParam.getLocText("Manage Notifications") + "']");  ////div/a[text()='Manage Notification Preferences']
    public static SelenideElement managenotificationsnew     = $x(
            "//div/a[text()='" + WebportalParam.getLocText("Manage Notification Preferences") + "']");
    public SelenideElement        accounttab                 = $x("//li[@class='accountTabBlock']");
    public SelenideElement        emailnotifications         = $("#iconHideFaPlusMngNoti");
    public String                 emailnotificationsnew      = "//input[@name='emailNotify']";
    public String                 pushalertsnotificationsnew = "//input[@name='pushNotify']";
    public static SelenideElement enableemailnotifications   = $x(
            "//h5[text()='" + WebportalParam.getLocText("Email Notifications") + "']/..//span");
    public SelenideElement        emailaddress               = $("#divEmlOnOfSetMngNoti");
    public SelenideElement        locationname               = $x("//h5[@id='hEmailNameMngNoti0']/../..");
    public SelenideElement        savenoticications          = $("#btnadUsrMngNoti");
    public SelenideElement        savenoticicationsnew       = $x("//button[@class='btn saveBtn']");

    public static SelenideElement updateprofile     = $x(
            "//*[@id='notificationDrop']/../../ul//a[text()='" + WebportalParam.getLocText("Update Profile") + "']");
    public SelenideElement        emailverifydialog = $x("//*[@aria-label='Email Verification Reminder']");
    public SelenideElement        cancelbutton      = $x("//*[normalize-space(text())='Cancel']");
    public SelenideElement        editprofile       = $x("//span[text()='Edit Profile']");
    public SelenideElement        firstname         = $x("//label[text()='First Name*']/../input");
    public SelenideElement        lastname          = $x("//label[text()='Last Name*']/../input");
    public SelenideElement        choosecountry     = $x("//label[text()='Choose Country']/..//select");
    public SelenideElement        state             = $x("//label[text()='State']/../input");
    public SelenideElement        city              = $x("//label[text()='City']/../input");
    public SelenideElement        streetaddress     = $x("//label[text()='Street Address']/../input");
    public SelenideElement        apartmentorsuite  = $x("//label[text()='Apartment or Suite']/../input");
    public SelenideElement        postalzipcode     = $x("//label[text()='Postal/ZIP Code']/../input");
    public SelenideElement        savebutton        = $x("//span[text()='Save']");

    public SelenideElement loginsettings       = $x("//span[text()='Login Settings']");
    public SelenideElement twostepverification = $x("//span[text()='Two-Step Verification']");
    public SelenideElement deleteprimarynum    = $x("//span[text()='Primary']/..//div");
    public SelenideElement savenum             = $x("//*[@id=\"sumit_count\"]");
    public SelenideElement inputDevicenum      = $x("//*[@id=\"billing_input_text\"]");

    public SelenideElement removenumbutton    = $x("(//p[text()='Remove Verification Device']/../..//button)[2]");
    public SelenideElement addsmsverification = $x("//button[text()='Add SMS Verification']");
    public SelenideElement changeemail        = $x("//span[text()='Change Email']");
    public SelenideElement newemail           = $x("//input[@name='email']");
    public SelenideElement confirmmail        = $x("//input[@name='cnfemail']");
    public SelenideElement submitchangeemail  = $x("//input[@name='password']/ancestor::div[4]//button");
    public SelenideElement currentpassword    = $x("//input[@name='password']");
    public SelenideElement changepassword     = $x("//span[text()='Change Password']");
    public SelenideElement oldpassword        = $x("//label[text()='Old Password']/../input");
    public SelenideElement newpassword        = $x("//label[text()='New Password']/../input");
    public SelenideElement confirmnewpassword = $x("//label[text()='Confirm New Password']/../input");
    public SelenideElement submitbutton       = $x("//button[@name='changePasswordButton']/span[text()='Submit']");
    public SelenideElement okconformation     = $x(
            "//p[contains(text(), 'Insight unable to find your subscription plan details.')]//following::button[@class = 'btn btn-danger']");
    public SelenideElement Invalidsession     = $x("//p[contains(text(), 'Invalid Session')]//following::button[contains(text(), 'OK')]");
    // added by tejeshwini

    public SelenideElement Billingdropdown = $x("//h2[text()='Billing Information ']/../..//span");
    public SelenideElement Carddropdown    = $("#PaymentHeader span");
    // public SelenideElement Termsandcondition = $x("//*[@id=\"styled-checkbox-1\"]");
    public SelenideElement Termsandcondition = $x("//input[@id='styled-checkbox-1']/../label");
    public SelenideElement Policy            = $x("//span[text()='By checking this box, I accept NETGEAR’s  ']");

    public SelenideElement about            = $x("//*[@id='notificationDrop']/../../ul//a[text()='About']");
    public SelenideElement webportalversion = $x("//div[@class='InsightVirsion']/p[1]");
    public SelenideElement cloudversion     = $x("//div[@class='InsightVirsion']/p[2]");
    public SelenideElement closebutton      = $x("//div[@id='divModConsuccessModlWiredPoeSch']//button[text()='Close']");

    public SelenideElement logout = $x("//*[@id='notificationDrop']/../../ul//a[text()='Log Out']");

    public SelenideElement closedevicecredits = $x("(//div[contains(@class,'modal fade Devicecredit in')]//button)[1]");
    public SelenideElement upgrade            = $x("//button[text()='Upgrade']");
    public SelenideElement Changebutton       = $x("//*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[2]/div[1]/div[1]/div/button");
    public SelenideElement ChangebuttonMoToYr = $x("//*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[2]/div[1]/div[2]/div/p/span[2]/span[1]");

    // public SelenideElement currentsubscriptionold =
    // $x("//*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[2]/div[1]/div[2]/div/div/ul/li[1]/p");
    public SelenideElement        currentsubscriptionold      = $("#hplanNamesubscriptions");
    public SelenideElement        subscriptionupgrade         = $("#btnModlDefaultsubscriptions");
    public SelenideElement        currencydevsubscription     = $("#hCurrencyDeviceSubscription");
    public SelenideElement        currencydevsubscriptionNew  = $x("//div[contains(@class,'primiumSubsBilling')]//li[5]/p");
    public SelenideElement        currencysubscription        = $("#hCurrencysubscriptions");
    public SelenideElement        currencysubscriptionNew     = $x("//div[contains(@class,'primiumSubsBilling')]//li[4]/p");
    public SelenideElement        deviceredsubscription       = $("#hColorRedSubscription");
    public SelenideElement        deviceredsubscriptioncancel = $x("//*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[1]/div/div/div[1]/h2");
    public SelenideElement        subscriptioncancel          = $x(
            " //*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[2]/div[1]/div[2]/div/div/ul/li[3]/p[2]");
    public SelenideElement        deviceredsubscriptionNew    = $x("//div[@class='SubsDeviceslist']/div[1]/h2");
    public SelenideElement        changesubscription          = $("#btnModslLearnMoreSubscription");
    public static SelenideElement changeSubNew                = $x("//span[text()='" + WebportalParam.getLocText("Change Subscription") + "']");
    public SelenideElement        buydevicecredits            = $("#btnBuyDeviceSubscription");
    public SelenideElement        billingandpayment           = $("#btnDefDeviceSubscription");
    public static SelenideElement billingandpaymentNew        = $x("//span[text()='" + WebportalParam.getLocText("Subscription Billing") + "']");
    public SelenideElement        editsubscription            = $x("(//div[@id='BilingHeader'])[1]//span");
    public SelenideElement        disableautorenewal          = $x("//h5[text()='Auto Renewal']/..//label[2]/input");
    public SelenideElement        enableautorenewal           = $x("//h5[text()='Auto Renewal']/..//label[1]/input");
    public SelenideElement        editbillinginfomation       = $x("(//div[@id='BilingHeader'])[2]//span");
    public SelenideElement        editcreditcardinformation   = $x("//h2[text()='Credit Card Information']/..//span");
    public SelenideElement        okbutton                    = $("#countryRedirectError");
    public SelenideElement        yearlyUS                    = $x("//h2[contains(text(),'/Yr')]/../input");
    public SelenideElement        monthlyUS                   = $x("//h2[contains(text(),'/Mo')]/../input");
    public SelenideElement        yearly                      = $x("//h2[contains(text(),'/Yr')]/../input");
    public SelenideElement        monthly                     = $x("//h2[contains(text(),'/Mo')]/../input");

    public SelenideElement checkoutbutton = $("#btnModlCheckoutSubscription");
    public SelenideElement devicesnum     = $x("//h2[text()='1. Number of Device Credits']/../..//input");
    public SelenideElement devicenumplus  = $x("//*[@id=\"icon_plus\"]");
    // public SelenideElement billingfirstname = $x("//h5[text()='First Name']/../input");
    public SelenideElement billingfirstname = $("#firstname");
    // public SelenideElement billinglastname = $x("//h5[text()='Last Name']/../input");
    public SelenideElement billinglastname = $("#lastname");
    public SelenideElement billingemail    = $x("//h5[text()='Email']/../input");
    // public SelenideElement billingstreetaddress = $x("//h5[text()='Street Address']/../input");
    public SelenideElement billingstreetaddress = $("#address");
    // public SelenideElement billingcity = $x("//h5[text()='City']/../input");
    public SelenideElement billingcity = $("#city");
    // public SelenideElement billingzip = $x("//h5[text()='Zip']/../input");
    public SelenideElement billingzip          = $("#zipcode");
    public SelenideElement billingzipErrorPath = $x("//div[@id='invalid-zip']");
    // public SelenideElement billingcountry = $x("//select[@name='country']");
    public SelenideElement billingcountry = $("#country");
    public SelenideElement billingstate1  = $x("//select[@name='state']");
    public SelenideElement billingstate2  = $x("//input[@name='state']");
    public SelenideElement billingicon    = $x("//*[@id=\"billing_icon_checked\"]");
    public SelenideElement billingstate3  = $("#state-text");
    // public SelenideElement billingstate2 = $x("//input[@name='state']");
    public SelenideElement billingvatnum = $x("//input[@id='orderSumary-vatregis-input']");
    // public SelenideElement paymentcardnumber = $x("//h5[text()='Card Number']/../input");
    public SelenideElement paymentcardnumber = $x("//*[@id=\"cc_no\"]");
    // public SelenideElement paymentcvvnumber = $x("//h5[text()='CVV Number']/../input");
    public SelenideElement paymentcvvnumber = $("#cvv");
    // public SelenideElement paymentexpirationmonth = $x("//select[@name='cc_exp_mm']");
    public SelenideElement paymentexpirationmonth = $("#cc_exp_mm");
    // public SelenideElement paymentexpirationyear = $x("//select[@name='cc_exp_yyyy']");
    public SelenideElement paymentexpirationyear  = $("#cc_exp_yyyy");
    public String          paymentautorenewstatus = "//input[@name='AutoRenew']";
    public SelenideElement paymentsumbit          = $("#submitPayment");
    public SelenideElement addpromocode           = $(".PromoCodeBlock:nth-child(2)");
    public SelenideElement conformpromocode       = $("#add-promo");

    public SelenideElement        enterpromocode      = $("#enter-promo");
    public static SelenideElement totalprice          = $x("//td[text()=' " + WebportalParam.getLocText("Total") + " ']/../td[2]");
    public SelenideElement        donebutton          = $x("//button[text()='Done']");
    public SelenideElement        checkbox            = $x("//*[@id=\"styled-checkbox-1\"]");
    public static SelenideElement placeyourorder      = $x("//button[text()='" + WebportalParam.getLocText("Place Your order") + "']");
    public static SelenideElement placeyourordernew   = $x("//*[@id=\"checkout-btn\"]");
    public static SelenideElement placeYourOrder      = $x("//button[text()='" + WebportalParam.getLocText("Place Your Order") + "']");
    public SelenideElement        gotosubscription    = $x("//a[text()='Go to Subscriptions']");
    public SelenideElement        gotosubscriptionnow = $x("//a[text()='Go to dashboard']");
    public SelenideElement        continuenow         = $x("//a[text()='Continue']");
    public SelenideElement        Submitpayment       = $x("//input[@type='submit']");

    public SelenideElement        cancelsubscription                   = $x(
            "//*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[2]/div[1]/div[2]/div/p/span[1]/span[1]");
    public SelenideElement        cancelBasicSub                       = $("#btncancelPlanModl");
    public SelenideElement        cancelSubscriptionNew                = $x(
            "//*[@id=\"_divConSecsubscriptions\"]/div[2]/div/div[2]/div[1]/div[2]/div/p/span[1]/span[1]");
    public static SelenideElement captiveportalservices                = $x(
            "//div/a[text()='" + WebportalParam.getLocText("Captive Portal Services") + "']");
    public static SelenideElement vpnservicespage                = $x(
            "//div/a[text()='" + WebportalParam.getLocText("VPN Services") + "']");
    public static SelenideElement insightcaptiveportalhistory          = $x(
            "//span[text()='" + WebportalParam.getLocText("Insight Captive Portal") + "']");
    public static SelenideElement captiveportalavailablecredits        = $x(
            "//p[text()='" + WebportalParam.getLocText("Available Credits") + "']/../h2");
    public SelenideElement        captiveportalavailablecreditsmanager = $x("//div[@class='listViewAllocation']//td[4]");
    public static SelenideElement captiveportalavailablecreditsnew     = $x(
            "//p[text()='" + WebportalParam.getLocText("Available Credits to Allocate") + "']/../h2");
    public String                 icpSubscriptionTable                 = "//span[text()='" + WebportalParam.getLocText("Current Services")
            + "']/../..//";
    public SelenideElement        icpSubscriptionTdTwo                 = $x(icpSubscriptionTable + "td[2]");
    public SelenideElement        icpSubscriptionTdThree               = $x(icpSubscriptionTable + "td[3]");
    public SelenideElement        icpSubscriptionTdFour                = $x(icpSubscriptionTable + "td[4]");
    public String                 delicpservicesmany                   = "(//div[contains(@id,'DataTables_Table')])[1]//tbody//td[1]";
    public SelenideElement        deleteicpservices                    = $x(delicpservicesmany);
    public SelenideElement        subDiv                               = $("#_divConSecsubscriptions");
    public SelenideElement        deleteIcpServiceWarningIcon          = $x("//span[@class='WarnIcon']/..");
    public static SelenideElement icpCreditsNum                        = $x("//*[@id=\"content\"]/div[5]/div/div[2]/div[1]/div/div[1]/div/h2");
    public SelenideElement        gracePeriodWarning                   = $("div.GridSelectsecond");
    public SelenideElement        gracePeriodWarningBuyBtn             = $("div.backWarnig button");
    public String                 expiredIcpCreditsTable               = "//span[text()='" + WebportalParam.getLocText("Previous Services")
            + "']/../..//";
    public SelenideElement        expiredIcpTdOne                      = $x(expiredIcpCreditsTable + "td[1]");
    public SelenideElement        expiredIcpTdFour                     = $x(expiredIcpCreditsTable + "td[4]");
    public SelenideElement        inputIcpKeyBtn                       = $("div.ButtonBlockPop button");

    public static SelenideElement orderhisactsort     = $x(
            "//span[text()='" + WebportalParam.getLocText("Insight VPN") + "']/../..//th[contains(@aria-label,'Activated On')]");
    public static SelenideElement ordersubsort        = $x(
            "//span[text()='" + WebportalParam.getLocText("Insight VPN") + "']/../..//th[contains(@aria-label,'Subscriptions:')]");
    public static SelenideElement openedorderhis      = $x(
            "//h3[contains(@class,'open')]/span[contains(text(),'" + WebportalParam.getLocText("Insight VPN") + "')]/..//i[2]");
    public static SelenideElement openorderhis        = $x("//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN") + "')]/..//i[2]");
    public static String          orderhistoryyr      = "//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[1]//p[contains(text(),'%s Yr')]";
    public static String          someorderhistory    = "//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[%s]//p[contains(text(),'%s Year')]";
    public static SelenideElement orderhistoryqty     = $x("//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[1]//td[2]/span");
    public static String          someorderhistoryqty = "//span[text()='" + WebportalParam.getLocText("Insight VPN")
            + "']/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[%s]//td[2]/span";
    public static SelenideElement orderstatus         = $x("//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[1]//td[3]/p[1]");
    public static String          someorderstatus     = "//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[%s]//td[3]/p[1]";
    public static SelenideElement orderActivatedOn    = $x("//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[1]//td[3]/p[2]");
    public static SelenideElement orderExpriesOn      = $x("//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr[1]//td[4]");
    public static String          vpnOrderTable       = "//span[contains(text(),'" + WebportalParam.getLocText("Insight VPN")
            + "')]/../..//table[contains(@id,'DataTables_Table')]//tbody/tr";

    public static SelenideElement createaccount      = $x("(//u[text()='" + WebportalParam.getLocText("Create an Account") + "'])[1]");
    public static SelenideElement createaccountold   = $x("(//u[text()='" + WebportalParam.getLocText("Create account") + "'])[1]");
    public static SelenideElement createaccountcognito      = $x("//*[text()=' Create an Account ']");
    public SelenideElement        createfirstname    = $x("//label[text()='First Name']/../input");
    public SelenideElement        createfirstname1   = $x("//*[@id=\"ip_firstName\"]");
    public SelenideElement        createlastname     = $x("//label[text()='Last Name']/../input");
    public SelenideElement        createlastname1     = $x("//*[@id=\"ip_lastName\"]");
    public SelenideElement        createemailaddress = $x("//label[text()='Email Address']/../input");
    public SelenideElement        createemailaddress1 = $x("//*[@id=\"mat-input-0\"]");
    public SelenideElement        confirmemail       = $x("//label[text()='Confirm Email Address']/../input");
    public SelenideElement        confirmemail1       = $x("//*[@id=\"cnfEmail\"]");
    public SelenideElement        createpassword     = $x("//label[text()='Password']/..//input");
    public SelenideElement        createpassword1     = $x("//*[@id=\"ip_pwdSignup\"]");
    public SelenideElement        confirmpassword    = $x("//label[text()='Confirm Password']/..//input");
    public SelenideElement        confirmpassword1    = $x("//*[@id=\"ip_cnfPwd\"]");
    public SelenideElement        selectcountry      = $x("//label[text()='Choose Country']/..//select");
    public SelenideElement        selectcountry1      = $x("//*[@id=\"mat-input-6\"]");
    public String                 acceptPolicy       = ".boxOnCheckBox .md-container";
//    public static SelenideElement  acceptPolicy1       = $x("//*[@id=\"mat-mdc-checkbox-1\"]/div/div");
    public static SelenideElement  acceptPolicy1       = $x("//span[text()='By checking this box, clicking Next, I accept ']/../../mat-checkbox/div/div");
    public static SelenideElement policyText         = $x("//span[text()='By checking this box, clicking Next, I accept']");
    public static SelenideElement policyText1        = $x("//input[@name='mailProgram']/..//i[@id='_iPromoReg']");
    public static SelenideElement policyText2        = $x("//input[@name='termsAndCondition']/..//i[@id='_iPromoReg']");
    public static SelenideElement proaccountContinue = $x("//*[@id=\"_ancSignUpReg\"]");
    public SelenideElement        continuebutton     = $("#signupBtn_i");
    public SelenideElement        continuebutton1     = $("#signupForm > div.buttonBlock.mb20.ml30 > button");
    public SelenideElement loginPwdNewcognito    = $("#mat-input-8");
    public SelenideElement SigninbuttonCognito    = $x("//*[@id=\"Login-btn\"]");
    public static SelenideElement emailerror         = $x(
            "//label[@for='email']/..//div[text()='" + WebportalParam.getLocText("Please enter a valid email address.") + "']");
    public static SelenideElement confirmemailerror  = $x(
            "//label[@for='input_0']/..//div[text()='" + WebportalParam.getLocText("Email and Confirm Email must be same.") + "']");
    public static SelenideElement firstnameerror     = $x(
            "//label[@for='input_1']/..//div[text()='" + WebportalParam.getLocText("First Name cannot be blank.") + "']");
    public static SelenideElement pwderror           = $x(
            "//label[@for='input_3']/..//div[contains(text(),'" + WebportalParam.getLocText("Allowed symbols are") + " ! @ # $ % ^ & * ( )')]");
    public static SelenideElement confirmpwderror    = $x(
            "//label[@for='input_4']/..//div[text()='" + WebportalParam.getLocText("Password and Confirm Password must be same.") + "']");
  
    public static SelenideElement NoThankYou         = $x("//u[text()='No thank you']");
    
    public static SelenideElement yesenbale          = $x("(//button[text()='" + WebportalParam.getLocText("Yes, Enable") + "'])[1]");
    public static SelenideElement yesenbalenew       = $x("(//button[text()='" + WebportalParam.getLocText("Yes enable") + "'])[1]");
    public static SelenideElement yesenbale2FA       = $x(
            "//button[@class='md-raised md-primary submitbtn md-button ng-scope md-ink-ripple'][contains(text(),'Yes, Enable')]");
    public SelenideElement        inputphone         = $x("//input[@id='phone']");
    public static SelenideElement addphone           = $x("//button/span[text()='" + WebportalParam.getLocText("Add Phone Number") + "']");
    public static SelenideElement Next               = $x("//span[contains(text(),'Next')]");

    public SelenideElement        verifybutton = $x("//span[text()='Verify']");
    public SelenideElement        dontTrust    = $x("//span[contains(@translate,'dont_trust')]/..");
    public static String          finishPage   = "//div[@ng-show='logging' and @aria-hidden='false']";
    public static SelenideElement finishMsg    = $x("//h3[text()='" + WebportalParam.getLocText("Account Created") + "']");
    public static SelenideElement finishbutton = $x(finishPage + "//button/span[text()='" + WebportalParam.getLocText("Finish") + "']");
    public SelenideElement        finishCreate = $x("//button[@ng-click='goToLogin()']");

    public SelenideElement        checkemailtitle       = $x("(//*[@id=\"email_list\"]/tr)[1]");
    public SelenideElement        loginPwd              = $x("//input[@id='searchinput']");
    public SelenideElement        loginButton           = $("#Login-btn");
    public SelenideElement        owneremail            = $x("//a[text()='Invite owner email']");
    public SelenideElement        trytrialemail         = $x("//a[text()='NETGEAR Insight Premium Free Trial']");
    public SelenideElement        invitemanager         = $x("//a[text()='Invite manager email']");
    public SelenideElement        getowneraccounturl    = $x("//a[text()=' click here to accept the invitation']");
    public SelenideElement        getowneraccounturlnew = $x("//a[text()=' click here']");
    public SelenideElement        ownerconfirmemail     = $x("//input[@name='confirmSignupEmail']");
    public SelenideElement        ownerpassword         = $x("//input[@name='password']");
    public SelenideElement        ownerconfirmpwd       = $x("//input[@name='confirmPwd']");
    public SelenideElement        ownercountrycode      = $x("//select[@id='countryCode']");
    public SelenideElement        ownerphonenum         = $x("//input[@name='phoneNo']");
    public SelenideElement        ownerterms            = $x("(//*[@id='_iPromoReg'])[2]");
    public static SelenideElement ownersignup           = $x("//button[text()='" + WebportalParam.getLocText("Sign Up") + "']");

    public static SelenideElement createproemailinput     = $x(
            "//h4[text()='" + WebportalParam.getLocText("Please enter your email address") + "']/..//input");
    public SelenideElement        ClickOnnext             = $x("//button[@type= 'submit'][text() = 'Next']");
    public static SelenideElement createpronextbutton     = $x("//button[text()='" + WebportalParam.getLocText("Next") + "']");
    public static SelenideElement proaccountsignuptitle   = $x("//p[text()='" + WebportalParam.getLocText("Account Sign-Up") + "']");
    public SelenideElement        inputproaccountkey      = $x("//input[@name='key']");
    public SelenideElement        StartTrialNow           = $x("//button[contains(text(), 'Start Trial Now')]");
    public SelenideElement        HardBundleStartTrialNow = $x("//button[contains(text(), 'Add Insight Included Device')]");
    public static SelenideElement inputproaccountkeynext  = $x("//button[text()='" + WebportalParam.getLocText("Next") + "']");
    public static SelenideElement selectmanagerservice    = $x("//p[text()='Value Added Reseller']");  //$x("//p[text()='" + WebportalParam.getLocText("Managed Service Provider") + "']/..");
    public static SelenideElement selectvalueservice      = $x("//*[@id=\"divInlineRadioSubscription\"]/label[1]");
    public SelenideElement        businessname            = $("#businessName");
    public static SelenideElement businessprimaryaddress  = $x(
            "//label[text()='" + WebportalParam.getLocText("Primary Address of Business") + "']/..//input");
    public SelenideElement        businesscity            = $x("//input[@name='city']");
    public SelenideElement        ProTrailStart           = $x("//button[contains(text(), 'Activate Free Trial')]");
    public SelenideElement        ProTrailActive          = $x("//*[@id=\"rebootModal\"]/div/div/div[3]/button[2]");
    public SelenideElement        businessstate           = $x("//input[@name='state']");
    public SelenideElement        businesszip             = $x("//input[@name='zipCode']");
    public SelenideElement        businesscountry         = $x("//select[@name='businessCountry']");
    public SelenideElement        businessphone           = $x("//input[@name='businessPhoneNo']");
    public SelenideElement        agreeterm               = $x("//p[text()='By signing up, I agree to the']/../i");
    public SelenideElement        agreetermDirectPurchase = $x("(//*[@id=\"_iPromoReg\"])[2]");
    public static SelenideElement businesssignup          = $x("//button[text()='" + WebportalParam.getLocText("Sign Up") + "']");
    public SelenideElement        businessDone            = $(".loginBtnSection");
    public static SelenideElement businessupgrade         = $x("//button[text()='" + WebportalParam.getLocText("Upgrade") + "']");
    public SelenideElement        businessInfoError       = $(".alert-danger");

    public static SelenideElement grantAccessToSupport = $x(
            "//a[contains(@class,'colorGray') and text()='" + WebportalParam.getLocText("Grant Access to Support") + "']");

    public static SelenideElement Logon                    = $x("(//*[@id=\"divModConsuccessModlWiredPoeSch\"]/div[3]/button[2])[4]");
    public String                 supportRequestTableEmail = "//td[contains(text(),'%s')]";
    public String                 supportRequestLogInUser  = "" + supportRequestTableEmail + "/..//td[4]/div//button";
    public String                 supportRequestEndAccess  = "" + supportRequestTableEmail + "/../td[4]/div/span/button";
    public static SelenideElement gotoMyAccount            = $x("//a[text()='" + WebportalParam.getLocText("Go to my Insight Account") + "']");
    public static SelenideElement endSupportAccess         = $x(
            "//button[text()='" + WebportalParam.getLocText("End Technical Support Access") + "']");
    public static SelenideElement startacess               = $x("//button[text()='" + WebportalParam.getLocText("Yes, Grant Access") + "']");

    public SelenideElement closeSignUp       = $x("//a[@class='close close-modal']");
    public String          loginAndSubscribe = "//a[@class='btn btn-lg btn-white']";

    public static SelenideElement subscriptionPlanTab      = $x(
            "//h2[contains(text(),'" + WebportalParam.getLocText("Your Subscription Plan Order") + "')]");
    public static SelenideElement billingInfoTab           = $x(
            "//h2[contains(text(),'" + WebportalParam.getLocText("Billing Information") + "')]");
    public static SelenideElement paymentInfoTab           = $x(
            "//h2[contains(text(),'" + WebportalParam.getLocText("Payment Information") + "')]");
    public static SelenideElement orderSummaryTab          = $x("//h2[contains(text(),'" + WebportalParam.getLocText("Order Summary") + "')]");
    public static String          directPurchaseTableBasic = "//span[contains(text(),'" + WebportalParam.getLocText("Insight Premium Subscription")
            + "')]/../../..";
    public static SelenideElement directPurchaseTable      = $x(
            directPurchaseTableBasic + "//td/span[text()='" + WebportalParam.getLocText("Insight Direct Premium - US") + "']");
    public SelenideElement        directPurchaseTdOne      = $x(directPurchaseTableBasic + "//td[1]");
    public SelenideElement        directPurchaseTdTwo      = $x(directPurchaseTableBasic + "//td[2]");
    public SelenideElement        directPurchaseTdThree    = $x(directPurchaseTableBasic + "//td[3]");
    public SelenideElement        directPurchaseTdFour     = $x(directPurchaseTableBasic + "//td[4]");
    public static SelenideElement cancelSubscription       = $x("//button[text()='" + WebportalParam.getLocText("Cancel Subscription") + "']");

    public SelenideElement MUB                            = $x("//div[contains(@class,'leftMenuHave')]//a[text() = 'Monthly Usage Billing']");
    public SelenideElement MUBcancelation                 = $x("//u[text() = 'Cancellation Policy']");
    public SelenideElement cancelationKB                  = $x("//*[@id=\"articlePage:frm:articleStats\"]/h1");
    public SelenideElement ManagePaymentMethods           = $x("//span[text() = 'Manage Payment Methods']");
    public SelenideElement EditBilliningINfo              = $x("//*[@id=\"billingBlock-savedBilling-editIcon\"]");
    public SelenideElement EditPaymentINfo                = $x("//*[@id=\"paymentBlock-editIcon\"]");
    public SelenideElement Submit                         = $x("//*[@id=\"save-autorenew-button\"]");
    public SelenideElement cancel                         = $x("//*[@id=\"cancel-button-link\"]");
    public SelenideElement OtherthanNorthAmericacountries = $x(
            "//p[text() = 'We’re sorry! Monthly Usage Billing isn’t available in your country or region.']");
    public SelenideElement MUBhistorytext                 = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div[2]/div/p");
    public SelenideElement MUBhistory                     = $x("//li[text()= 'Usage History']");
    public SelenideElement Termaandcondition              = $x(
            "//*[@id=\"usageBasedBillingcontent\"]/div[3]/div/div/div[2]/div/div/div[2]/div/label/p");
    public SelenideElement TermaandconditionCheckbox      = $x(
            "//*[@id=\"usageBasedBillingcontent\"]/div[3]/div/div/div[2]/div/div/div[2]/div/label/p");
    public SelenideElement TermaandconditionAccept        = $x("//*[@id=\"usageBasedBillingcontent\"]/div[3]/div/div/div[3]/button[2]");
    public SelenideElement BillingInfo                    = $x("//*[@id=\"billingBlock-savedBillingInfo-div\"]");
    public SelenideElement GoToBillingInfo                = $x("//*[@id=\"billingBlock-savedInfo-plusicon\"]");
    public SelenideElement GoToPaymentinfo                = $x("//*[@id=\"paymentBlock-plusIcon\"]");
    public SelenideElement MUBEnableling                  = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div/div[2]/div/div[2]/div/label/span");
    public SelenideElement MUBdisablemessage              = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div/div[2]/div/div[2]/div[3]");
    public SelenideElement CreditAllocationerror          = $x("//*[@id=\"creditAllocationErr\"]/div");
    public SelenideElement AddpurchaseKey                 = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div[1]/div/div/div[2]/div/button");
    public SelenideElement WriteLMSKey                    = $x("//*[@id=\"licenseKeyValue\"]");
    public SelenideElement clickonAddLMSKey               = $x("//button[@data-testid='addButton']");
    public SelenideElement LMSActivation                  = $x("//*[@id=\"proCurrLicenseTable\"]/tbody/tr/td[3]");
    public SelenideElement LMSExpiration                  = $x("//*[@id=\"proCurrLicenseTable\"]/tbody/tr/td[4]");
    public SelenideElement otherCountries                 = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div/div/div/p");

    public SelenideElement DevieCreditsCount      = $x("//div[@class=\"SubsDeviceslist\"][1]/div[1]/h2[1]");
    public SelenideElement AvailableCreditsCount  = $x("//div[@class=\"SubsDeviceslist\"][1]/div[3]/h2[1]");
    public SelenideElement ActivationTime         = $x("//div[@class=\"SubsBillingCol primiumSubsBilling m-b-20\"][1]/ul[1]/li[2]/p[1]");
    public SelenideElement ExpiryTime             = $x("//div[@class=\"SubsBillingCol primiumSubsBilling m-b-20\"][1]/ul[1]/li[3]/p[1]");
    public SelenideElement BuyDeviceCreditsButton = $x("//div[@class=\"premiumSubscriptionBlock\"][1]/div[1]/div[1]/div[1]/button[1]");
    public SelenideElement PremiumTrialTextField  = $x("//div[@class=\"SubsBillingCol primiumSubsBilling m-b-20\"][1]/ul[1]/li[1]/p[1]");

    public static SelenideElement openbusinessorderhis    = $x(
            "//span[contains(text(),'" + WebportalParam.getLocText("Business VPN") + "')]/..//i[2]");
    public static String          businessvpnOrderTable   = "//span[contains(text(),'" + WebportalParam.getLocText("Insight Business VPN")
            + "')]/../..//..//..//..//..//..//table[contains(@id,'DataTables_Table')]//tbody/tr";
    public static SelenideElement businessorderhistoryqty = $x("//span[contains(text(),'" + WebportalParam.getLocText("Insight Business VPN")
            + "')]/../..//..//..//..//..//..//table[contains(@id,'DataTables_Table')]//tbody/tr[1]//td[2]/span");

    public static SelenideElement Totalremotesite        = $x("//p[text()='" + WebportalParam.getLocText("Total Remote Site Credits") + "']/../h2");
    public static SelenideElement BusinessAvilablecredit = $x("//p[text()='" + WebportalParam.getLocText("Available Credits") + "']/../h2");
    public static SelenideElement MaxxonnectedClients    = $x("//*[@id=\"proCurrLicenseTable\"]/tbody/tr/td[5]");
    public static SelenideElement PurchaseOrderHistory   = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]/div[2]/a");

    public SelenideElement ProTrailPurchasehistoryDropDown = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div[2]/div/div/h3/span[2]/i[2]");

    public static SelenideElement AddProKey                            = $x("//button[contains(text(), 'Add Purchase Confirmation Key')]");
    public static SelenideElement AddICPKey                            = $x("//button[contains(text(), 'Add Instant Captive Portal Key')]");
    public static SelenideElement AddVPNKey                            = $x("//button[contains(text(), 'Add VPN Service Key')]");
    public static SelenideElement AddProLicense                        = $x("//*[@id=\"licenseKeyValue\"]");
    public static SelenideElement AddICPLicenseKey                     = $x("//*[@id=\"smart-form-license\"]/div/input");
    public static SelenideElement ClickAdd                             = $x("(//*[text()= 'Add'])[2]");
    public static SelenideElement ClickAddICP                          = $x("(//button[contains(text(), 'Add')])[2]");
    public static SelenideElement ClickAddok                           = $x("//*[@id=\"main\"]/div/div[2]/div[2]/div/div/div[6]/button[2]");
    public static SelenideElement addemailprohardbundle                = $x("//*[@id=\"smart-form-register1\"]/fieldset/section/label/input");
    public static SelenideElement nextBtnPro                           = $x("//*[@id=\"smart-form-register1\"]/fieldset/div/button");
    public static SelenideElement cnfmeMailProhard                     = $x("//*[@id=\"smart-form-register2\"]/fieldset/section[2]/label[2]/input");
    public static SelenideElement passwordProhard                      = $x("//*[@id=\"authPwd\"]");
    public static SelenideElement cnfmPasswordPro                      = $x("//*[@id=\"confirmPassField\"]");
    public static SelenideElement selectCountryPro                     = $x("//*[@id=\"countryCode\"]");
    public static SelenideElement countryAus                           = $x("//*[@id=\"countryCode\"]/option[8]");
    public static SelenideElement phoneNumber                          = $x("//*[@id=\"smart-form-register2\"]/fieldset/section[8]/label[2]/input");
    public static SelenideElement checkbox12                           = $x("(//*[@id=\"_iPromoReg\"])[2]");
    public static SelenideElement nextBtnProacc                        = $x("//*[@id=\"_ancSignUpReg\"]");
    public static SelenideElement addinsightDevice                     = $x(
            "//*[@id=\"_divContReg\"]/div[2]/div[2]/div/div/div[2]/div/div/div[1]/div/div[3]/button");
    public static SelenideElement accountType                          = $x("//*[@id=\"divInlineRadioSubscription\"]/label[2]/p");
    public static SelenideElement businessName                         = $x("//*[@id=\"businessName\"]");
    public static SelenideElement primaryAddress                       = $x("//*[@id=\"_fldReg\"]/section[2]/label[2]/input");
    public static SelenideElement citypro                              = $x("//*[@id=\"_fldReg\"]/section[3]/label[2]/input");
    public static SelenideElement statepro                             = $x("//*[@id=\"_fldReg\"]/div[2]/div[1]/section/label[2]/input");
    public static SelenideElement zipCodepro                           = $x("//*[@id=\"_fldReg\"]/div[2]/div[2]/section/label[2]/input");
    public static SelenideElement chooseCountry1                       = $x("//*[@id=\"_fldReg\"]/section[4]/label[2]/select");
    public static SelenideElement countryAustrailia                    = $x("//*[@id=\"_fldReg\"]/section[4]/label[2]/select/option[8]");
    public static SelenideElement businessPhonenumber                  = $x("//*[@id=\"_fldReg\"]/section[5]/label[2]/input");
    public static SelenideElement sigInbtn                             = $x("//*[@id=\"_fldReg\"]/div[3]/div/button");
    public static SelenideElement organizationBtn                      = $x("//*[@id=\"NoOrgHeadDiv\"]");
    public static SelenideElement nameofOrganization                   = $x("//*[@id=\"orgName\"]");
    public static SelenideElement selectaOwner                         = $x("//*[@id=\"existingUser\"]");
    public static SelenideElement oneYearInsight                       = $x(
            "//*[@id=\"_divContReg\"]/div[2]/div[2]/div/div/div[2]/div/div/div[1]/div/div[1]/h3");
    public static SelenideElement textcheckpro                         = $x("//*[@id=\"successMessage\"]/div[2]/div[1]/p");
    public static SelenideElement actDate                              = $x("//*[@id=\"DataTables_Table_0\"]/tbody/tr/td[3]/p");
    public static SelenideElement expDate                              = $x("//*[@id=\"DataTables_Table_0\"]/tbody/tr/td[4]/p[2]");
    public SelenideElement        accountManagement                    = $x("(//*[@id=\"header\"]/div[2]/div/ul/li/ul/li)[1]");
    public SelenideElement        humbergerMenuforPremtoPro            = $x("//*[@id=\"header\"]/div[2]/div/ul/li/div/div/div[2]/p[2]");
    public SelenideElement        upgradeToPro                         = $x("//a[text()='Upgrade to Pro Subscription']");
    public SelenideElement        upgradeproText                       = $x("//*[@id=\"main\"]/div/div[4]/div[2]/div/div[2]/p");
    public SelenideElement        upgradeToProBtn                      = $x("//button[text()='Upgrade to Pro']");
    public SelenideElement        dropdownHumbergerMenu                = $x("//*[@id=\"header\"]/div[2]/div/ul/li/ul/li");
    public SelenideElement        upgradetoProText                     = $x("//p[text()='Upgrade to Pro']");
    public SelenideElement        managedServiceProvider               = $x("//*[@id=\"divInlineRadioSubscription\"]/label[1]/p");
    public SelenideElement        businessAddressName                  = $x("//*[@id=\"_fldReg\"]/section[2]/label[2]/input");
    public SelenideElement        businessCity                         = $x("//*[@id=\"_fldReg\"]/section[3]/label[2]/input");
    public SelenideElement        businessState                        = $x("//*[@id=\"_fldReg\"]/div[2]/div[1]/section/label[2]/input");
    public SelenideElement        businesscityZipCode                  = $x("//*[@id=\"_fldReg\"]/div[2]/div[2]/section/label[2]/input");
    public SelenideElement        businessCountry                      = $x("//*[@id=\"_fldReg\"]/section[4]/label[2]/select");
    public SelenideElement        businessPhoneNumber                  = $x("//*[@id=\"_fldReg\"]/section[5]/label[2]/input");
    public SelenideElement        businessUpgradeBtn                   = $x("//*[@id=\"_fldReg\"]/div[3]/div/button");
    public SelenideElement        businessAccSuccessMsg                = $x("//p[@id='NoOrgHeadOneDiv']");
    public static SelenideElement Acccountlicense                      = $x("//*[@id=\"smart-form-license\"]/div[2]/div[1]/label/input");
    public static SelenideElement orglicense                           = $x("//p[text()='Organization']");
    public static SelenideElement orglicense1                          = $x("//*[@id=\"smart-form-license\"]/div[2]/div[2]/label/i");
    public static SelenideElement accountLevelActiveSubscription       = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div[2]/div/div[1]/h3/span[1]");
    public static SelenideElement accountLevelExpiredSubscription      = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div[2]/div/div[2]/h3/span[1]");
    public static SelenideElement organizationLevelActiveSubscription  = $x(
            "//*[@id=\"content\"]/div[4]/div/div[2]/div[2]/div/div[3]/h3/span[1]  ");
    public static SelenideElement organizationLevelExpiredSubscription = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div[2]/div/div[4]/h3/span[1]");
    public static SelenideElement Dropdownorg                          = $x(
            "//*[@id=\"content\"]/div[4]/div/div[2]/div[2]/div/div[4]/h3/span[2]/i[2]");

    public static SelenideElement PrchaseconformationkeyAccout = $x("//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[1]");
    public static SelenideElement SKUAccout                    = $x("//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[2]");
    public static SelenideElement ActivationAccount            = $x("//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[3]");
    public static SelenideElement ExpirationAccount            = $x("//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[4]");
    public static SelenideElement CreditAccount                = $x("//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[5]");

    public static SelenideElement PrchaseconformationkeyOrg = $x("(//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[1])[1]");
    public static SelenideElement SKUOrg                    = $x("(//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[2])[1]");
    public static SelenideElement ActivationOrg             = $x("(//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[3])[1]");
    public static SelenideElement ExpirationOrg             = $x("(//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[4])[1]");
    public static SelenideElement CreditOrg                 = $x("(//*[@id=\"proCurrLicenseTable\"]/thead/tr/th[5])[1]");

    public static SelenideElement SelectOrg               = $x("//*[@id=\"smart-form-license\"]/div[1]/div/div[4]/div/select");
    public static SelenideElement accountManagement1      = $x("//*[@id=\"header\"]/div[2]/div/ul/li/ul/li[1]/a");
    public static SelenideElement mailinatorSearchBoxText = $x("(//input[@placeholder='Enter Public Mailinator Inbox'])[1]");
    public static SelenideElement accountMailId           = $x("(//li/h3)[2]");
    public static SelenideElement maillinatorEnterEmail   = $x("//*[@id=\"search\"]");
    public static SelenideElement mailinatorGoButton      = $x("//button[text()='GO']");
    public static SelenideElement clickonMail             = $x("(//td)[5]");
    public static SelenideElement clickonMail1            = $x("(//td)[6]");
    public static SelenideElement mailMemoCnfm            = $x("/html/body/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[1]/td/text()[2]");
    public static SelenideElement openLinkClkHere         = $x("/html/body/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[1]/td/a");

    public static SelenideElement MonthlyPaymentMethod          = $x("//*[@class=\"monthlyUsageBillingDetail\"]//span[@class=\"cstmSlider cstmRound\"]");
    public static SelenideElement AccountType                   = $x("//*[@id=\"header\"]/div[2]/div/ul/li/div/div/div[2]/p[2]");
    public static SelenideElement closeTheWindow                = $x("//*[@id=\"mainHeader\"]/div[16]/div/div/div/span/img");
    public static SelenideElement freetrailText                 = $x(
            "//*[@id=\"_divContReg\"]/div[2]/div[2]/div/div/div[2]/div/div/div[3]/div/div[1]/h3/text()");
    public static SelenideElement btnFreeTrail                  = $x("//button[text()='Start Trial Now']");
    public static SelenideElement btnAddingOneYearInsightDevice = $x("//button[text()='Add Insight Included Device']");

    public SelenideElement orgexits(String text) {
        SelenideElement Ssid = $x("//span[text()='" + text + "']//..//..//../td[6]/p");
        return Ssid;
    }
    
    public SelenideElement devicecount(String text) {
        SelenideElement count = $x("//*[text()= '" + text + "']/../../p[2]/span/span[2]");
        return count;
    }
    
    public SelenideElement devicecount1(String text) {
        SelenideElement count1 = $x("//*[text()='" + text + "']/../div[1]/ul/li[3]");
        return count1;
    }

    public static SelenideElement proAccNotificationIcon      = $x("//div[@data-tooltip='Notification']");
    public static SelenideElement proAccNoOfNotification      = $x("//div[@data-tooltip='Notification']/span");
    public static SelenideElement premiumAccNotificationIcon  = $x("//div[@data-tooltip='Notifications']");
    public static SelenideElement premiumAccNoOfNotification  = $x("//div[@data-tooltip='Notifications']/span");
    public static SelenideElement seeAllBtn                   = $x("//button[text()='See All']");
    public static SelenideElement deleteNotificationBtn       = $x("(//img[@class='deleteDeviceIcon'])[1]");
    public static SelenideElement firstNotification           = $x("//*[@id=\"alarmsTable\"]/tbody/tr[1]/td[1]/div/div[2]/p[1]");
    public static SelenideElement msgNotificationDeleted      = $x("//*[@id=\"notificationMsg\"]/div");
    public static SelenideElement msgDontHaveAnyNotification  = $x("//p[text()='No notification based on current Notification Filter setting.']");
    public static SelenideElement dashBoardPageProAcc         = $x("(//a[@href='/#/organization/dashboard'])[1]");
    public static SelenideElement locationDashboardPremiumAcc = $x("(//a[@href='/#/dashboard/account'])[1]");
    public static SelenideElement proAccLogout                = $x("//li//a[text()='Log Out']");
    public static SelenideElement CreditAllocation            = $x("//div[@class='leftMenuItems']//div/a[text()='" + WebportalParam.getLocText("Credit Allocation") + "']");
    public static SelenideElement AddPurchaseConfirmationKey  = $x("//button[@data-testid='gotIt' and text()='Add Purchase Confirmation Key']");
    public static SelenideElement LicenseOkButton             = $x("//div[@class='modal-footer cstmModFooter']//button[text()='OK']");
    public static SelenideElement CreditAllocateIcon          = $x("//img[@id='AccCreditAllocate']");
    public static SelenideElement AccountSugnUpEmail          = $x("//input[@name='signupEmail']");
    public SelenideElement        loginPwdNew                 = $("#searchinput");
    public SelenideElement        locationSelectCheckBox      = $x("//ul//*[@class='checkbox ipacl']");
    public SelenideElement        AssigntoOrganization        = $x("//button[text()='Assign to Organization']");
    public SelenideElement        newOrgName                  = $x("//input[@data-type='orgName' and @name='newOrgName']");
    public SelenideElement        SubmitByText                = $x("//button[text()='Submit']");
    public SelenideElement        OKassignToOrg               = $x("//div[@class='modal fade successSaveModal in']//button[normalize-space(text())='OK']");
    public SelenideElement        inputlicenseKey             = $x("//input[@data-type='licenseKey' and @name='key' ]");
    public SelenideElement        NextBtnByText               = $x("//button[text()='Next']");
    public SelenideElement        accountOpt                  = $x("//p[text()='Account']");
    //pratik
    public SelenideElement        verifyLocafterMigration                  = $x("//span[text()='OnBoardingTest']");
    public SelenideElement        addlocationtoNewOrgCheckbox              = $x("//label[@class='checkbox ipacl']");
    public SelenideElement        btnAssigntoOrg                           = $x("//button[text()='Assign to Organization']");
    public SelenideElement        inputNewOrgName                          = $x("//input[@name='newOrgName']");
    public SelenideElement        submitButton                             = $x("//button[text()='Submit']");
    public SelenideElement        locationAddedSuccessMsg                  = $x("//p[text()='Locations added successfully.']");
    public SelenideElement        okayButton                               = $x("(//button[text()='OK'])[3]");
    public SelenideElement        insightpremiumSubTitle                   = $x("//span[text()='Insight Subscriptions']");
    public SelenideElement        pluIcon1PurchaseHistory                  = $x("(//i[@class='icon icon-icon-collapse'])[1]");
    public SelenideElement        insightPremiumAnnualSubscription         = $x("//p[contains(text(),'Insight Premium Monthly/Annual Subscription')]");
    public SelenideElement        cancelledSubscription1                   = $x("(//p[text()='Cancelled'])[1]");
    public SelenideElement        pluIcon2PurchaseHistory                  = $x("(//i[@class='icon icon-icon-collapse'])[2]");
    public SelenideElement        insightPremiumMultipackSub               = $x("//span[contains(text(),'Insight Premium 5 pack')]");
    public SelenideElement        cancelledSubscription2                   = $x("(//p[text()='Cancelled'])[2]");
    public SelenideElement        gotoDashboard                            = $x("//button[text()='Go To Dashboard']");
    public SelenideElement        insightVPNSubscription                   = $x("//span[text()='Insight VPN']");
    public SelenideElement        insightVPNName                           = $x("//span[text()='Insight Instant VPN - 1 Year']");
    public SelenideElement        activatedDate                            = $x("(//p[text()='Aug 24, 2023'])[1]");
    public SelenideElement        startDateVPNSub                          = $x("(//tr//td)[2]");
    public SelenideElement        endDateVPNSub                            = $x("(//tr//td)[3]");
    public SelenideElement        upgradeToProMessgae1                     = $x("//p[text()='Do you want to upgrade to Pro subscription?']");
    public SelenideElement        upgradeToProMessgae                      = $x("//p[contains(text(),'Premium to Pro subscription upgrade enables')]");
    public SelenideElement        licensePageLink                          = $x("//a[text()='Where can I buy a Pro key?']");
    public SelenideElement        whereToBuYHeaderonnewURL                 = $x("//h2[text()='Where To Buy']");
    public SelenideElement        verifyParagraphonlandedURL               = $x("//h4[starts-with(text(),'Find NETGEAR Business')]");
    public SelenideElement        cookiesClose                             = $x("//span[@id='cookie-close']");
    //    added by tejeshwini KV
    public SelenideElement               downlaodinvoice                  = $x("(//*[@id=\"premiumSubscriptionPackDiv\"]//*[text()= 'Download Invoice'])[1]");
    public SelenideElement               downlaodinvoiceok                = $x("(//*[@id=\"premiumSubscriptionPackDiv\"]//*[text()= 'Download Invoice'])[3]");
    public static SelenideElement        SelectYear                       = $x("(//*[@id=\"selectInvoice\"])[1]");
    public static SelenideElement        Selectinvoice                    = $x("(//*[@id=\"selectInvoice\"])[2]");      
    
//  Added by vivek for Notification preferences for ALL organizations 
  public SelenideElement        OrganizationSettings        = $x("//ul[@class='d-flex no-padding colorBlack']/li[@class='locationTabBlock']");
  public SelenideElement        LocationSettings            = $x("//ul[@class='d-flex no-padding colorBlack']/li[@class='locationTabBlock' and text()='Location Settings']");
  public ElementsCollection     listOfOrg                   = $$(Selectors.byXpath("//h3/span[@class='d-flex inputaclfield justify-content-between']/span"));
  public ElementsCollection     listOfntwrk                 = $$(Selectors.byXpath("//tr/td/span[@class='colorBlack action-delete']"));
  public ElementsCollection     listOfplusIcon              = $$(Selectors.byXpath("//div[@class='overflow']//div[@class='nsaccord-detail']/../h3/span[2]"));
  public ElementsCollection     listOfcheckbox              = $$(Selectors.byXpath("//tr[@class='divactiveLeftMenu']//span"));
  public SelenideElement        SaveOrgSetting              = $x("//div[@class='stati-footer text-right actionBtnRow']//button[text()='Save']");
  public ElementsCollection     listOfcheckboxValue         = $$(Selectors.byXpath("//tr[@class='divactiveLeftMenu']//span/label/input[@type='checkbox']"));
  public SelenideElement        firstcheckBox               = $x("(//tr[@class='divactiveLeftMenu']//span)[2]");
  public SelenideElement        firstcheckBoxValue          = $x("(//tr[@class='divactiveLeftMenu']//span/label/input[@type='checkbox'])[1]");
  public SelenideElement        locationText                = $x("(//td/div[text()='Locations'])[1]");
  
  
  public static SelenideElement schedule                    = $x("//p[text()='Schedule']");
  public static SelenideElement selectDate                  = $x("//*[@placeholder=\"Select Date\"]");
  public static SelenideElement selectTime                  = $x("//p[text()='Select Time']");
  public static SelenideElement increseTime                 = $x("//p[text()='Select Time']/../../div/div[2]/div/ul/li[2]/div[2]/i");
  public static SelenideElement increseTimeok               = $x("//*[@id=\"smart-form-license\"]/div[2]/div[3]/div/div[2]/div/div[2]/div[2]/button");
  public static SelenideElement addSchedule                  = $x("//button[text()='Add']");
  public static SelenideElement Orgimmediet                  = $x("//span[contains(text(), 'Organization Level Active Subscription')]/../span[2]/i[2]");
  public static SelenideElement ICPpropage                     = $x("//a[text()='Captive Portal Services']");

  public SelenideElement loginNowButton   = $x("//*[@id=\"button_premium\"]"); 
  public SelenideElement selcetDate(String Date) {
      SelenideElement selectdate = $x("//*[text()='" + Date + "'][contains(@class, 'react-datepicker__day--today')]");
      return selectdate;
  }
  
//Added by Vivek
  public SelenideElement loginButtonLandingPage              = $x("//button[@id='button_premium']");
  public SelenideElement SelectNumber                        = $x("//*[@aria-label='********3275']");
  public SelenideElement SaveNumber                          = $x("//button[text()='Save']");
  public SelenideElement creditCountpro                      = $x("(//td/span[@class='ManagerClTxt colorPurple pointerCursor'])[1]");
  public SelenideElement creditDetailsText                   = $x("//div[text()='License Detail']");
  public SelenideElement CrditTextunderPopUp                 = $x("//div[@class='clientDataDetailSection']//li[text()='Credits']");
  public SelenideElement ManagerTextCredit                   = $x("//div[@class='clientDataDetailSection']//li[contains(text(),'Managers')]");
  public SelenideElement CrditAllocationText                 = $x("//div[text()='Credit Allocation']");
  public SelenideElement OrgTextCredit                       = $x("//div[@class='clientDataDetailSection']//li[contains(text(),'Organizations')]");
  public static SelenideElement SignUpPro                    = $x("//*[@id=\"insight_signup\"]/span/a");
  public static SelenideElement SignUpProCognito             = $x("//*[@id=\"insight_signup\"]");
  public static SelenideElement ErrorTextForenterprise       = $x("//div[@id='ssidModalError']");
  
  
// Added by Vivek for Advanced Purchase History
  public static SelenideElement DivCreditSubsText               = $x("//th[text()='Subscriptions']/../../..//tbody//td[@style='white-space: pre-line;']");
  public static SelenideElement DivCreditSubsQuantity           = $x("//span[text()='Insight Subscriptions']/../..//th[text()='Quantity']/../../..//td[2]");
  public static SelenideElement DivCreditSubsActivationDate     = $x("//span[text()='Insight Subscriptions']/../..//th[text()='Activated On']/../../..//td[3]");
  public static SelenideElement DivCreditSubsExpiryDate         = $x("//span[text()='Insight Subscriptions']/../..//th[text()='Expires On']/../../..//td[4]");
  public static SelenideElement ExpaindHardbundal               = $x("//span[text()='Insight Included with Hardware']/../span[2]/i[2]");
  public static SelenideElement HardBdlDivCreditSubsText        = $x("(//th[text()='Subscriptions']/../../..//tbody//td[@style='white-space: pre-line;'])[2]");
  public static SelenideElement HbbLocInfo                      = $x("(//th[text()='Location']/../../..//tbody//td[2]/span)[1]");
  public static SelenideElement HBBDivCreditActivationDate      = $x("(//th[text()='Activated On']/../../..//tbody//td[3]/p)[2]");
  public static SelenideElement HBBDivCreditExpiryDate          = $x("(//th[text()='Expires On']/../../..//tbody//td[4]/p)[2]");
  public static SelenideElement HardBdlSecDivCreditSubsText     = $x("(//th[text()='Subscriptions']/../../..//tbody//td[@style='white-space: pre-line;'])[3]");
  public static SelenideElement HbbSecondLocInfo                = $x("(//th[text()='Location']/../../..//tbody//td[2]/span)[2]");
  public static SelenideElement HBBSecDivCreditActivationDate   = $x("(//th[text()='Activated On']/../../..//tbody//td[3]/p)[3]");
  public static SelenideElement HBBSecDivCreditExpiryDate       = $x("(//th[text()='Expires On']/../../..//tbody//td[4]/p)[3]");
  public static SelenideElement HardBdlThirdDivCreditSubsText   = $x("(//th[text()='Subscriptions']/../../..//tbody//td[@style='white-space: pre-line;'])[4]");
  public static SelenideElement HbbThirdLocInfo                 = $x("(//th[text()='Location']/../../..//tbody//td[2]/span)[3]");
  public static SelenideElement HBBThirdDivCreditActivationDate = $x("(//th[text()='Activated On']/../../..//tbody//td[3]/p)[4]");
  public static SelenideElement HBBThirdDivCreditExpiryDate     = $x("(//th[text()='Expires On']/../../..//tbody//td[4]/p)[4]");
  public static SelenideElement ExpaindICPCreditSection         = $x("//span[text()='Insight Captive Portal']/../span[2]/i[2]");
  public static SelenideElement ICPDivCreditSubsText            = $x("//span[text()='Insight Captive Portal']/../..//th[text()='Subscriptions']/../../..//td[@style='white-space: pre-line;']");
  public static SelenideElement ICPDivCreditSubsQuantity        = $x("//span[text()='Insight Captive Portal']/../..//th[text()='Quantity']/../../..//td[2]");
  public static SelenideElement ICPDivCreditSubsActivationDate  = $x("//span[text()='Insight Captive Portal']/../..//th[text()='Activated On']/../../..//td[3]");
  public static SelenideElement ICPDivCreditSubsExpiryDate      = $x("//span[text()='Insight Captive Portal']/../..//th[text()='Expires On']/../../..//td[4]");
  public static SelenideElement ExpaindVPNCreditSection         = $x("//span[text()='Insight VPN']/../span[2]/i[2]");
  public static SelenideElement VPNDivCreditSubsText            = $x("//span[text()='Insight VPN']/../..//th[text()='Subscriptions']/../../..//td[@style='white-space: pre-line;']");
  public static SelenideElement VPNDivCreditSubsQuantity        = $x("//span[text()='Insight VPN']/../..//th[text()='Quantity']/../../..//td[2]");
  public static SelenideElement VPNDivCreditSubsActivationDate  = $x("//span[text()='Insight VPN']/../..//th[text()='Activated On']/../../..//td[3]");
  public static SelenideElement VPNDivCreditSubsExpiryDate      = $x("//span[text()='Insight VPN']/../..//th[text()='Expires On']/../../..//td[4]");
  public static SelenideElement PreMonthlyDivCreditSubsText     = $x("(//th[text()='Subscriptions']/../../..//tbody//td[@style='white-space: pre-line;'])[2]");
  public static SelenideElement PreMonthlyDivCreditSubsQuantity = $x("(//span[text()='Insight Subscriptions']/../..//th[text()='Quantity']/../../..//td[2])[2]");
  public static SelenideElement PreMonthlyDivCreditSubsActiDate = $x("(//span[text()='Insight Subscriptions']/../..//th[text()='Activated On']/../../..//td[3])[2]");
  public static SelenideElement PreMonthlyDivCreditSubsExpDate  = $x("(//span[text()='Insight Subscriptions']/../..//th[text()='Expires On']/../../..//td[4])[2]");
  public static SelenideElement ExpaindinsightPreSubsSection    = $x("//span[text()='Insight Premium Subscriptions']/../span[2]/i[2]");
  public static SelenideElement insightPreSubsCreditText        = $x("//span[text()='Insight Premium Subscriptions']/../..//th[text()='Subscriptions']/../../..//td[@style='white-space: pre-line;']");
  public static SelenideElement insightPreSubsCreditQuantity    = $x("//span[text()='Insight Premium Subscriptions']/../..//th[text()='Quantity']/../../..//td[2]");
  public static SelenideElement insightPreSubsCreditActivationDate  = $x("//span[text()='Insight Premium Subscriptions']/../..//th[text()='Activated On']/../../..//td[3]");
  public static SelenideElement insightPreSubsCreditExpiryDate      = $x("//span[text()='Insight Premium Subscriptions']/../..//th[text()='Expires On']/../../..//td[4]");
  
  //added by Pratik for HumbergerMenu
  public SelenideElement  emailSwitch                           = $x("(//span[@class='cstmSlider cstmRound'])[4]");
  public SelenideElement  pushSwitch                            = $x("(//span[@class='cstmSlider cstmRound'])[5]");
  public SelenideElement  verifyLocafterMigration1              = $x("//span[text()='office1']");
  public SelenideElement  csvFileDownloadButton                 = $x("//div[@data-tooltip='Download CSV']");
  public SelenideElement  allocatedDeviceCredits                = $x("//span[text()='2']");
  public SelenideElement  allocatedCaptiveCredits               = $x("//td[text()='2']");
  public SelenideElement  sideTrayofDeviceCredits               = $x("//div[@class='clientDetailBlock deviceCreditSideBar dataDetailShow']");
  public SelenideElement  netgearHeader                         = $x("//p[text()='Netgear']");
  public SelenideElement  sideTrayDeviceAllocatedCredits        = $x("//span[text()='Allocated Device Credits']");
  public SelenideElement  sideTraNoOfyDeviceAllocatedCredits    = $x("(//span[text()='2'])[2]");
  public SelenideElement  sideTrayCloseIcon                     = $x("//div[@class='closeIconBlock']");
  public SelenideElement  deviceCreditTrayLicenseKey            = $x("//p[@class='ClientHistoryEllipses no-margin']");
  public SelenideElement  deviceCreditTrayLicenseKeyLong        = $x("//div[@class='AccountTooltip']");
  public SelenideElement  deviceCreditTrayLicenseKeyCloneText   = $x("//small[text()='Clone']");
  public SelenideElement  deviceCreditsonTray                   = $x("(//td[text()='2'])[2]");
  public SelenideElement  usedCreditsShwononTray                = $x("(//td[text()='0'])[2]");
  public SelenideElement  expriryDateShownOnTray                = $x("//tr[@class='inputTextField']/td[4]");
  public SelenideElement  copiedLicenseKeyonDeviceCreditTray    = $x("//small[text()='Copied']");
  public SelenideElement  checkboxCloneLicenseKeyTray           = $x("//img[@src='assets/img/copy-icon.png']");
  public SelenideElement  sideTraNoOfyDeviceAllocatedCredits1   = $x("(//span[text()='1'])[4]");
  public SelenideElement  deviceCreditsonTray1                  = $x("//td[text()='1']");
  public SelenideElement  allocatedDeviceCreditsAfterDeallocate = $x("(//span[text()='1'])[3]");
  public SelenideElement  allocatedDeviceCredits2               = $x("//span[text()='500']");
  public SelenideElement  sideTraNoOfyDeviceAllocatedCredits2   = $x("(//span[text()='500'])[2]");
  public SelenideElement  multipleLicensekeys1                  = $x("//div[contains(@class,'myDevicesTable OrgNotifaicationBlock')]/div/div/table/tbody/tr[1]");
  public SelenideElement  multipleLicensekeys2                  = $x("//div[contains(@class,'myDevicesTable OrgNotifaicationBlock')]/div/div/table/tbody/tr[2]");
  public SelenideElement  multipleLicensekeys3                  = $x("//div[contains(@class,'myDevicesTable OrgNotifaicationBlock')]/div/div/table/tbody/tr[3]");
  public SelenideElement  multipleLicensekeys4                  = $x("//div[contains(@class,'myDevicesTable OrgNotifaicationBlock')]/div/div/table/tbody/tr[4]");
  public SelenideElement  backButtonFromDeallocate              = $x("//span[@class='icon icon-icon-arrow-previous']");
  
  
  public SelenideElement  search              = $x("//*[@id=\"ancSearchIconOrgDash\"]/span");
  public SelenideElement  sendemail           = $x("//*[@id=\"content\"]/div[3]/div[1]/div/ul/li[1]/div/div/input");
  public SelenideElement  searchbutton              = $x("//*[@id=\"content\"]/div[3]/div[1]/div/ul/li[2]/button[2]");
  public SelenideElement  clickdown              = $x("//*[@id=\"smartadmin-root\"]/div/div[3]/div/div/div[2]/div");
  public SelenideElement  activateFreeTrial              = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/button");
  
  //Added by Anusha H
  public SelenideElement  ReportInMail              = $x("(//div[text()=\"Organization Report: Netgear\"])[1]");
  public SelenideElement  errorMSG                  = $x("//*[@id=\"formError1\"]");
  
  public SelenideElement orgcount(String text) {
      SelenideElement orgcount = $x("//span[contains(text(), '" + text + "')]");
      return orgcount;
  }
  
  public SelenideElement license(String text) {
      SelenideElement license = $x("//span[contains(text(), '" + text + "')]");
      return license;
  }
  
  public SelenideElement  AcctSubsActiveRowElemnets      = $x("(//*[@id=\"proCurrLicenseTable\"]/thead/tr)[1]");
  public SelenideElement  AcctSubsActiveRow              = $x("//span[text()=\"Account Level Active Subscription\"]");
  public SelenideElement  OrgSubsActiveRow               = $x("//span[text()=\"Organization Level Active Subscription\"]");
  public SelenideElement  ORGSubsActiveRowElemnets       = $x("(//*[@id=\"proCurrLicenseTable\"]/thead/tr)[4]");
  public SelenideElement  OrgSubsExpiryRow               = $x("//span[text()=\"Organization Level Expired Subscription\"]");
  public SelenideElement  AcctSubsExpiryRow              = $x("//span[text()=\"Account Level Expired Subscription\"]");
  
  public SelenideElement SelectOrgInSubspage(String text) {
      SelenideElement SelectOrg = $x("//*[text()=\"Select Organization\"]/../select/option[text()='" + text + "']");
      return SelectOrg;
  }
  
  public SelenideElement  selectSubsKey              = $x("//*[@id=\"creditKey\"]"); 
  public SelenideElement  SelectAll                  = $x("//*[text()=\"Select All\"]");
  public SelenideElement  selectSubsAcct             = $x("//div[@class=\"d-flex align-items-center disableAccordian\"]");
  public SelenideElement  keysDropdown               = $x("//li[@class=\"no-margin OrgNotifaicationBlock\"]"); 
  
  public SelenideElement  editORG                    = $x("//*[@id=\"_ancliOrgDiv0\"]"); 
  public SelenideElement  AllocateCredits            = $x("//*[@id=\"_ulliOrgDiv0\"]/li[1]/a/b");
  public SelenideElement  Allocatebutton2             = $x("//button[@class=\"btn btn-primary addDeviceBtn\" and text()=\"Allocate\"]");
  //AddedByPratik
  public SelenideElement  clickOnAdvanced                        = $x("//button[contains(text(),'Advanced')]");
  public SelenideElement  clickOnLinktoOpenLocalGUI              = $x("//a[@id='proceed-link']");
  public SelenideElement  apUsername                             = $x("//input[@id='userName']"); 
  public SelenideElement  apPassword                             = $x("//input[@id='userPwd']"); 
  public SelenideElement  loginButtonLocalGUIAP                  = $x("//button[text()='Login']"); 
  public SelenideElement  verifyLoggedInSuccesswithAPModel       = $x("//span[contains(text(),'Access Point (W')]");
  public SelenideElement  switchGUIPassword                      = $x("//input[@type='PASSWORD']");
  public SelenideElement  loginButtonSwitch                      = $x("//a[@id='login_button']");
  public SelenideElement  okPopupBtn                             = $x("//a[text()='OK']");
  public SelenideElement  switchModelverifyText                  = $x("//label[contains(text(),'Switch')]");
  public SelenideElement  selectAllNoti               = $x("//span[@class=\"icon-bulk-edit-2\"]"); 
  public SelenideElement  selectCheckBox               = $x("//*[@id=\"selectAllCheck\"]/../i"); 
  public SelenideElement  deleteNoti                   = $x("//*[@id=\"btnDelete1\"]"); 
  public SelenideElement  firstOrgCreditAllocation     = $x("//span[text()='Netgear']/../../..//img[@id='AccCreditAllocate']");
  public SelenideElement  secOrgCreditAllocation       = $x("//span[text()='organization']/../../..//img[@id='AccCreditAllocate']");
  
  public SelenideElement  enable2FA                    = $x("(//span[text()='Enable']/..//div)[4]");
  public SelenideElement  email2FA                     = $x("//*[text()=\"Email (default)\"]"); 
  public SelenideElement  Continue                    = $x("//*[text() ='Continue']");
  //AddedByPratikForCognito
  public SelenideElement        cancelbuttonCognito         = $x("//div[contains(@class,'buttonBlock')]//button[contains(@class,'cancel')]");
  public SelenideElement        newemailCognito             = $x("//input[@id='mat-input-0']");
  public SelenideElement        confirmmailCognito          = $x("//input[@id='mat-input-1']");
  public SelenideElement        currentpasswordCognito      = $x("//input[@id='mat-input-2']");
  public SelenideElement        submitchangeemailCognito    = $x("//span[text()='Submit']");
  public SelenideElement        verifyOtpScreenCognito      = $x("//p[contains(text(),'sent a Verification code')]");
  public SelenideElement        confirmEmailOtpYopmail      = $x("//td[text()='Verification Code: ']/b");
  public SelenideElement        enterChangeemailOTP         = $x("//label[@for='ip_otp' or @aria-owns='ip_otp']");
  public SelenideElement        enterOTP                    = $x("//input[@id='ip_otp']");
  public SelenideElement        firstNameCognito            = $x("//input[@formcontrolname='firstName']");
  public SelenideElement        lastNameCognito             = $x("//input[@formcontrolname='lastName']");
  public SelenideElement        stateCognito                = $x("//input[@formcontrolname='state']");
  public SelenideElement        cityCognito                 = $x("//input[@formcontrolname='city']");
  public SelenideElement        streetAddCognito            = $x("//input[@formcontrolname='address']");
  public SelenideElement        apartmentCognito            = $x("//input[@formcontrolname='address2']");
  public SelenideElement        zipcodeCognito              = $x("//input[@formcontrolname='zipcode']"); 
    
  //AddedByPratikForPurchaseOrderHistoryenhancement
  public SelenideElement  insightIncludedHardBundleCSV      = $x("//span[text()='Insight Included with Hardware']/..//span[@class='icon-export-csv']");
  public SelenideElement  insightLicenseFileCSV             = $x("//span[text()='Pro user Insight Licenses']/..//span[@class='icon-export-csv']");
  public SelenideElement  insightSubscriptionFileCSV        = $x("//span[text()='Insight Subscriptions']/..//span[@class='icon-export-csv']");
  public SelenideElement  deviceCreditsPremacc              = $x("//p[text()='Device Credits']/..//h2");
  public SelenideElement  insightDevicesPremacc             = $x("//p[text()='Insight Devices']/..//h2");
  public SelenideElement  availableCreditsPremacc           = $x("//p[text()='Available Credits']/..//h2");
  public SelenideElement  submitBtn                         = $x("(//span[text()='Submit'])[2]");
  public SelenideElement  inviteEmailLinkAndText            = $x("//a[contains(text(),'click here to accept the invitation')]");
  public SelenideElement  signupbuttonForManagerandOwner    = $x("//button[text()='Sign Up']");
  public SelenideElement  mubBillingTab                     = $x("//div[@class='leftMenuItems']//a[text()='Monthly Usage Billing']");
  
  //AddedByPratik
  public SelenideElement  mubPaymentsaveBtn                 = $x("//button[@type='submit' and text()=' Save ']");
  public SelenideElement  enableMUB                         = $x("//h5[text()='Monthly Usage Billing']/..//span[@class='cstmSlider cstmRound']");
  public SelenideElement  successmsgResult                  = $x("//h2[text()=' Your profile updated successfully! ']");
  public SelenideElement  gotoDashboardlink                 = $x("//a[text()='Go to dashboard']");
  public SelenideElement  savedbillingInfoEdit              = $x("//span[@id='billingBlock-savedBilling-editIcon']");
  public SelenideElement  savedPaymentinfoedit              = $x("//span[@id='paymentBlock-editIcon']");
  public SelenideElement  usageHistoryTab                   = $x("//li[text()='Usage History']");
  public SelenideElement  usageHistoryTextVerify            = $x("//p[contains(text(),'available on the first day of the next month.')]");
  public SelenideElement  mubtextAboutDisableMUB            = $x("//div[contains(text(),'be disabled on the first day of the next month')]");
  public SelenideElement activationDatePremiumAcc(String text) {
      SelenideElement activationDate = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[3]/p[1]");
      if(activationDate.isDisplayed()) {
         System.out.println("old one");
      }
      return activationDate;
  }

  public SelenideElement ExpiryDatePremiumAcc(String text) {
      SelenideElement ExpiryDate = $x("//span[contains(text(), '" + text + "')]//..//..//../../../td[4]/p[2]");
      if(ExpiryDate.isDisplayed()) {
          System.out.println("old one");
       }
      return ExpiryDate;
  }
  public SelenideElement  verifyAdminEmailMessage           = $x("//td[contains(text(),'Please confirm your email address')]");
  public SelenideElement  verifyVPNCreditsavilable          = $x("//p[text()='Total VPN Group Credits']");
  public SelenideElement  verifyCaptivePortalCount1         = $x("(//span[text()='Instant Captive Portal']/../..//span[text()='3'])[1]");
  public SelenideElement  netgearBusinessPageAcceptcookies  = $x("//button[contains(@class,'primary acceptcookies')]");
  public SelenideElement  netgearBusinessPagePremiumSub     = $x("//h2[contains(text(),'Login and Subscribe')]/..//a[text()='NETGEAR Insight Premium']");
  public SelenideElement  netgearBusinessPageProSub         = $x("//h2[contains(text(),'Login and Subscribe')]/..//a[text()='NETGEAR Insight Pro']");
  public SelenideElement  cancelSubscriptionBtn             = $x("//a[@id='cancel_link']");
  public SelenideElement  warningOkButtonProAcc             = $x("//p[contains(text(),'An error occurred while retrieving the status')]/../..//button[text()='OK' and @class='btn btn-danger']");
  public SelenideElement  warningOkButtonProAcc1            = $x("//p[contains(text(),'Insight Pro account')]/../..//button[text()='OK']");
  public SelenideElement  checkBox1                         = $x("//p[contains(text(),'Yes! Please keep me updated')]");
  public SelenideElement  checkBox2                         = $x("//input[@name='termsAndCondition']/..//i");
  public SelenideElement  proaccCreatedNotificationCognito  = $x("//p[contains(text(),'Your account has been created.')]");
  public SelenideElement  proaccCreatedOKBtnCognito         = $x("//p[contains(text(),'Your account has been created.')]/../..//button[text()='OK']");
}

