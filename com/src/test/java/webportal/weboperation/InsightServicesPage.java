package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;

import java.time.Year;
import com.codeborne.selenide.Selenide;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.InsightServicesPageElement;
import io.qameta.allure.Step;
import webportal.weboperation.*;

/**
 * @author Netgear
 */

public class InsightServicesPage extends InsightServicesPageElement {
    /**
    *
    */
    Logger logger;

    public InsightServicesPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefInsightServices);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public InsightServicesPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public boolean checkGotoMarketPlace() {
        boolean result = false;
        if (insightservicesbutton.exists()) {
            insightservicesbutton.click();
            waitReady();
            String url = MyCommonAPIs.getCurrentUrl();
            if (url.contains("/#/insightServices")) {
                result = true;
                logger.info("Its in market place page.");
            }
        }
        return result;
    }

    public boolean checkMarketPlacePage(String accountType) {
        boolean result = false;
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("/#/insightServices")) {
            if (accountType.equals("admin") && vpnproducttitle.exists() && devicesupporttitle.exists() && devicesviewbutton.exists()
                    && captiveportaltitle.exists()) {
                result = true;
                logger.info("Market place page displayed.");
            } else if (accountType.equals("owner") && vpnproducttitle.exists() && devicesupporttitle.exists() && !devicesviewbutton.exists()
                    && captiveportaltitle.exists()) {
                result = true;
                logger.info("Market place page displayed.");
            } else if (accountType.equals("normal") && vpnproducttitle.exists() && vpnviewbutton.exists() && devicesupporttitle.exists()
                    && devicesviewbutton.exists() && captiveportaltitle.exists() && captiveportalview.exists()) {
                result = true;
                logger.info("Market place page displayed.");
            }
        }
        return result;
    }

    public void buyVpnProducts(Map<String, String> map) {
        humberger.click();
        MyCommonAPIs.sleepi(10);
        account.click();
        MyCommonAPIs.sleepi(10);
        vpnServices.click();
        MyCommonAPIs.sleepi(30);
        buyVpn.click();
        MyCommonAPIs.sleepi(30);
        
        
        
        
//        waitReady();
//        if (vpnviewbutton.exists()) {
//            vpnviewbutton.click();
//        }
        String buyNum = String.format(buynow, map.get("YearNum"));
        waitElement(buyNum);
        $x(buyNum).click();
        MyCommonAPIs.sleepi(5);
        for (int i = 0; i < (Integer.valueOf(map.get("BuyNum")) - 1); i++) {
            butnumnext.click();
            MyCommonAPIs.sleepi(2);
        }
        inputPaymentPage(map);
    }

    public void BusinessVpnProducts(Map<String, String> map) {
        waitReady();
        if (vpnviewbutton.exists()) {
            Businessvpnviewbutton.click();
        }
        // String buyNum = String.format(buynow, map.get("YearNum"));
        // waitElement(buyNum);
        // $x(buyNum).click();

        if (map.get("Subscription Type").equals("Employee Home Site")) {
            EmployeeHomeSite.click();
        } else if (map.get("Subscription Type").equals("Micro Office - 9 User")) {
            MicroOffice9User.click();
        } else if (map.get("Subscription Type").equals("Micro Office - 15 User")) {
            MicroOffice15User.click();
        } else if (map.get("Subscription Type").equals("Small Office - 25 User")) {
            SmallOffice25User.click();
        } else if (map.get("Subscription Type").equals("Small Office - 50 User")) {
            SmallOffice50User.click();
        } else if (map.get("Subscription Type").equals("")) {
            logger.info("subscription tye is not proper");
        }

        MyCommonAPIs.sleepi(5);
        for (int i = 0; i < (Integer.valueOf(map.get("BuyNum")) - 1); i++) {
            if (Quantity.isDisplayed()) {
                Quantity.click();
            }
            if (Quantity1.isDisplayed()) {
                Quantity1.click();
            }
            MyCommonAPIs.sleepi(10);
            Buynum.click();
        }
        inputPaymentPage(map);
    }

    public void buyCFProducts(Map<String, String> map) {
        waitReady();
        WebCheck.checkHrefIcon(URLParam.hrefInsightServices);
        if (CFviewbutton.exists()) {
            CFviewbutton.click();
        }

        if (map.get("Subscription Type").equals("Content Filtering Service Subscription")) {
            ServiceSubscription.click();
        } else {

            TopupInspection.click();
        }

        MyCommonAPIs.sleepi(10);

        for (int i = 0; i < (Integer.valueOf(map.get("Quantity")) - 1); i++) {
            butnumnextCF.click();
            MyCommonAPIs.sleepi(2);
        }
        inputPaymentPage(map);
    }

    public void buyCFTopUp(Map<String, String> map) {
        waitReady();
        WebCheck.checkHrefIcon(URLParam.hrefInsightServices);
        if (CFviewbutton.exists()) {
            CFviewbutton.click();
        }

        if (map.get("Subscription Type").equals("Content Filtering Service Subscription")) {
            ServiceSubscription.click();
        } else {

            TopupInspection.click();
        }

        MyCommonAPIs.sleepi(10);

        for (int i = 0; i < (Integer.valueOf(map.get("Quantity")) - 1); i++) {
            butnumnextCF.click();
            MyCommonAPIs.sleepi(2);
        }
        inputPaymentPage(map);
    }

    public void inputBillingInfo(Map<String, String> map) {
        MyCommonAPIs.sleepi(5);
        if (Savecount.isDisplayed()) {
            Savecount.click();
        }
        MyCommonAPIs.sleepi(5);
        dropbilling.click();
        MyCommonAPIs.sleepi(3);
        billingfirstname.setValue(map.get("First Name"));
        billinglastname.setValue(map.get("Last Name"));
        // billingemail.setValue(map.get("Email"));
        // if(map.containsKey("Company Name")) {
        // billingcompanyname.sendKeys(map.get("Company Name"));
        // }
        billingstreetaddress.setValue(map.get("Street Address"));
        billingcity.setValue(map.get("City"));
        billingzip.setValue(map.get("Zip"));
        billingcountry.selectOption(map.get("Country"));
        MyCommonAPIs.sleepi(5);
        // if ($x("//div[@hidden]/input[@name='state']").exists()) {
        // billingstate1.selectOption(map.get("State"));
        // } else {
        // billingstate2.setValue(map.get("State"));
        // }
        if (billingstate1.isDisplayed()) {
            billingstate1.selectOption(map.get("State"));
        } else {
            billingstate2.sendKeys(map.get("State"));
        }
        if (map.containsKey("VAT Registration Number")) {
            billingvatnum.setValue(map.get("VAT Registration Number"));
        }
        MyCommonAPIs.sleepi(5);
        if (billingicon.isDisplayed()) {
            billingicon.click();
        } else {
            billingicon1.click();
        }
        MyCommonAPIs.sleepi(3);
    }

    public void clickSubscriptionPlanNext() {
        MyCommonAPIs.sleepi(10);
        if (buynownext.exists()) {
            buynownext.click();
        } else if (buynownext1.exists()) {
            buynownext1.click();
        }
        // waitElement(checkoutnext);
        // checkoutnext.click();
        MyCommonAPIs.sleepi(10);
    }

    public void clickdropdown() {

        Billingdropdown.click();
    }

    public void billingdrop() {
        dropbilling.click();
        MyCommonAPIs.sleepi(5);
    }

    public void inputPaymentInfo(Map<String, String> map) {

        if (paymentcardnumber.isDisplayed()) {
            System.out.println("paymentcardnumber is existed");
        } else {
            Carddropdown.click();
        }

        paymentcardnumber.setValue(map.get("Card Number"));
        MyCommonAPIs.sleepi(2);
        paymentcvvnumber.setValue(map.get("CVV Number"));
        MyCommonAPIs.sleepi(2);
        if (!map.get("Expiration Month").equals("")) {
            paymentexpirationmonth.selectOption(map.get("Expiration Month"));
        }
        if (!map.get("Expiration Year").equals("")) {
            paymentexpirationyear.selectOption(map.get("Expiration Year"));
        }
        MyCommonAPIs.sleepi(20);
    }

    public void terms() {
        click(Termsandcondition, true);
    }

    public void clickSaveButton() {
        savebutton.click();
        MyCommonAPIs.sleepi(30);
    }

    public void clickPlaceOrderButton() {
        if (!placeyourorder.isDisplayed() && !placeyourordernew.isDisplayed()) {
            if ($x(paymentautorenew).exists()) {
                $x(paymentautorenew).click();
            }
        }
        MyCommonAPIs.sleepi(10);
        if (placeyourordernew.exists()) {
            placeyourordernew.click();
        } else if (placeyourorder.exists()) {
            placeyourorder.click();
        }
        // waitElement(gotomainpage);
    }

    public void inputPaymentPage(Map<String, String> map) {
        waitReady();
        clickSubscriptionPlanNext();

        inputBillingInfo(map);
        // clickSaveButton();
        inputPaymentInfo(map);
        // clickSaveButton();
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(3);
        new HamburgerMenuPage(false).clickPlaceOrder();
        // gotomainpage.click();
        waitReady();
    }

    public void inputPaymentPageforDeviceSupport(Map<String, String> map) {
        waitReady();
        inputBillingInfo(map);
        inputPaymentInfo(map);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(10);
        new HamburgerMenuPage(false).clickPlaceOrderSupportDevice();
        waitReady();
    }

    public void buyCaptivePortalProducts(Map<String, String> map) {
        
//       System.out.print("entered into buy" );
        humberger.click();
        MyCommonAPIs.sleepi(10);
        account.click();
        MyCommonAPIs.sleepi(10);
        captivePortalServices.click();

        MyCommonAPIs.sleepi(30);
//        int i=10;
//        while(true) {
//            System.out.print("enterted while");
//            
//            if(buyIcp.exists()) {
//                System.out.print("found icp");
//                buyIcp.click();
//              break;
//            }
//            i--;
//            if(i==0)
//            {
//                break;
//            }
           // MyCommonAPIs.sleepi(10);
//        MyCommonAPIs.sleepi(30);
//        buyIcp.click();
//        MyCommonAPIs.sleepi(30);
//        System.out.print("buyicp clicked" );

//        MyCommonAPIs.sleepi(30);
//        buyIcp.click();
//        MyCommonAPIs.sleepi(30);
//        System.out.print("buyicp clicked" );

        
            
       // MyCommonAPIs.sleepi(30);
      //  System.out.print("buyicp clicked" );
        
        
        
//        if (captiveportalview.exists()) {
//            captiveportalview.click();
//        }
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        js.executeScript("window.scrollBy(0, -250)");
        MyCommonAPIs.sleepi(10);
        waitElement(buyIcp);
        buyIcp.click();
        MyCommonAPIs.sleepi(20);
//        Selenide.refresh();
        if (!map.get("Quantity").equals("1")) {
            switch (map.get("Quantity")) {
            case "3":
                $x(captiveprotalquantity + "[2]").click();
                break;
            case "10":
                $x(captiveprotalquantity + "[3]").click();
                break;
            case "40":
                $x(captiveprotalquantity + "[4]").click();
                break;
            }
        }
        if (map.get("Duration").equals("1")) {
            captiveportaloneyr.click();
        } else if (map.get("Duration").equals("3")) {
            captiveportalthreeyr.click();
        }
        inputPaymentPage(map);
    }

    public void buyInsightPremiumSubscriptions(Map<String, String> map) {
        // waitElement($(devicecredits));
        if (MultipackView.exists()) {
            MultipackView.click();
        }
        if (map.get("Device Credits Pack").equals("5")) {
            $$(devicecredits).first().click();
        } else if (map.get("Device Credits Pack").equals("10")) {
            $$(devicecredits).last().click();
        }
        MyCommonAPIs.sleepi(5);
        $x(String.format(deviceyears, map.get("Buy Year"))).click();
        inputPaymentPage(map);
    }

    // public boolean checkBillingPage(String price, String countryCode, String plan) {
    // boolean result = false;
    // if (countryCode.equals("")) {
    // if ($x(String.format("//span[text()='%s']", price)).exists() && $x(String.format("//p[text()='%s']", plan)).exists())
    // {
    // result = true;
    // logger.info("Billing page display correct.");
    // }
    // } else {
    // if ($x(String.format("//span[text()='%s']", price)).exists() && $x(String.format("//p[contains(text(),'%s')]",
    // countryCode)).exists()
    // && $x(String.format("//p[text()='%s']", plan)).exists()) {
    // result = true;
    // logger.info("Billing page display correct.");
    // }
    // }
    // return result;
    // }
    public boolean checkBillingPage(String price, String countryCode, String plan) {
        boolean result = false;

        String Price = getText("#orderSumary-block-text");
        String CountryCode = getText("#cartMain-Biling-p");
        String Plan = getText("#cartMain-Biling-p");
        if (countryCode.equals("")) {
            if (Price.contains(price) && Plan.contains(plan)) {
                result = true;
                logger.info("Billing page display correct.");
            }
        } else {
            if (Price.contains(price) && CountryCode.contains(countryCode) && Plan.contains(plan)) {
                result = true;
                logger.info("Billing page display correct.");
            }
        }
        return result;
    }

    public boolean checkBillingDevicesNum(String number) {
        boolean result = false;
        if ($x(String.format("//p[contains(text(),'%s')]", number + " pack")).exists()) {
            result = true;
            logger.info("Billing devices display correct.");
        }
        return result;
    }

    // public boolean checkPaymentInfoError() {
    // boolean result = false;
    // if (cardNumberError.exists() && expiryMonthError.exists() && expiryYearError.exists() && cvvCodeError.exists()) {
    // result = true;
    // logger.info("Payment information display correct.");
    // }
    // return result;
    // }

    public boolean checkPaymentInfoError() {
        boolean result = false;
        if (cardNumberError.exists() && cvvCodeError.exists()) {
            result = true;
            logger.info("Payment information display correct.");
        }
        return result;
    }

    public boolean checkTotalPrice() {
        boolean result = false;
        String subTotal = getText($$x(subTotalAndTax).first());
        String tax = getText($$x(subTotalAndTax).last());
        Double plusPrice = Double.valueOf(subTotal.substring(1, subTotal.indexOf(" "))) + Double.valueOf(tax.substring(3, tax.lastIndexOf(" ")));
        System.out.println(plusPrice);
        if (getText(totalPrice).contains(String.valueOf(plusPrice))) {
            result = true;

        }
        return result;
    }

    public boolean DeviceContractVerification(String SerialNo) {
        // boolean result1 = false;
        // boolean result2 = false;
        boolean result = false;

        int count = 0;
        while (count < 10) {
            MyCommonAPIs.sleepi(10);
            if (continuenow.exists()) {
                continuenow.click();
                result = true;
                break;
            }
            count += 1;
        }
        // String contract = getText(ContractName(SerialNo));
        // String purchaseDate = getText(PurchaeContract(SerialNo));
        // String ExpiryDate = getText(ExpireContract(SerialNo));
        // System.out.print(contract);
        // System.out.print(purchaseDate);
        // System.out.print(ExpiryDate);
        // String Purchase = purchaseDate.substring(0,3);
        // String Expiry = ExpiryDate.substring(0,3);
        // System.out.print(Expiry);
        // System.out.print(ExpiryDate);
        // int Pur = Integer.parseInt(Purchase);
        // int Exp = Integer.parseInt(Expiry);
        // System.out.print(Pur);
        // System.out.print(Exp);
        // if(Devicecontract(SerialNo).exists() && contract.contains("Category 1, 1-Year")){
        // System.out.print("category information is right");
        // result1 = true;
        // }
        //
        // if(Pur-Exp == 1){
        // logger.info("category information is right");
        // result2 = true;
        // }
        //
        // if (result1==true && result2 == true) {
        // result = true;
        // }

        return result;

    }

    public void buyDeviceContract(Map<String, String> map, String SerialNumber) {
        waitReady();
        devicesviewbutton.click();
        DeviceSupport(SerialNumber).click();
        waitReady();
        MyCommonAPIs.sleepi(20);

        if (map.get("Device Category").equals("Category 1, 1-Year")) {
            Selenide.switchTo().frame("netgearIFrame");
            if (Pro1Device1Year.exists()) {
                click(Pro1Device1Year);
            } else {
                click(Pro1Device1Year1);
            }

        } else if (map.get("Device Category").equals("Category 1, 3-Year")) {

            if (Pro1Device3Year.exists()) {
                click(Pro1Device3Year);
            } else {
                click(Pro1Device3Year1);
            }
        }
        MyCommonAPIs.sleepi(20);
        submit.click();
        inputPaymentPageforDeviceSupport(map);
    }

    public void clickOnCaptiveportal() {
        MyCommonAPIs.sleepi(2);
        clkonCaptivePortals.click();
        MyCommonAPIs.sleepi(5);
    }

    // Written by Vivek
    @Step("Opening the Insight Included with Hardware Section")
    public void openInsightIncludedwithHardwareSection() {
        logger.info(" --- Opening open Insight Included with Hardware ");
        MyCommonAPIs.sleepi(5);
        openInsightIncludedwithHardware.click();
    }

    // Written by Vivek
    @Step("Getting the Actual Text for 5 year varification")
    public boolean verifyInsightPremiumFiveYearsubscription(String SerialNo) {
        MyCommonAPIs.sleepi(6);
        boolean result = false;
        logger.info("----------  Getting the text --------------------");
        if (OrbiProAXExit(SerialNo).exists()) {
            String check = OrbiProAXExit(SerialNo).getText();
            logger.info(check);
            if (check.contains("Premium 5-year subscription")) {
                result = true;
        }
        
        }

        return result;
    }

    // Written by Vivek
    @Step("Getting the Actual Text for 1 year varification")
    public boolean verifyInsightPremiumOneYearsubscriptionNotPresent(String SerialNo) {
        MyCommonAPIs.sleepi(6);
        boolean result = true;
        logger.info("----------  Getting the text --------------------");
        String check = OrbiProAXExit(SerialNo).getText();
        logger.info(check);
        if (check.contains("Insight Premium 1-year subscription")) {
            result = false;
        }

        return result;
    }

    // Written by Vivek
    @Step("verify the Actual Text should be  5 year from today date")
    public boolean verifyExpairyDateIsExpected(String SerialNo) {
        MyCommonAPIs.sleepi(6);
        boolean result = false;
        logger.info("----------  Getting the text --------------------");
        String actualText = OrbiProexpairy(SerialNo).getText();
        logger.info("ExpairyDate=" + actualText);
        Year currentYear = Year.now();
        int yearValue = currentYear.getValue();
        int expiryYear = yearValue + 5;
        String expiryYearStr = String.valueOf(expiryYear);
        if (actualText.contains(expiryYearStr)) {
            result = true;
        }

        return result;
    }
    
    public void buyInsightPremiumSubscriptions1(Map<String, String> map) {
        // waitElement($(devicecredits));
        if (MultipackView.exists()) {
            MultipackView.click();
        }
        if (map.get("Device Credits Pack").equals("5")) {
            $$(devicecredits).first().click();
        } else if (map.get("Device Credits Pack").equals("10")) {
            $$(devicecredits).last().click();
        }
        MyCommonAPIs.sleepi(5);
        $x(String.format(deviceyears, map.get("Buy Year"))).click();
        inputPaymentPage1(map);
    }

    public void inputPaymentPage1(Map<String, String> map) {
        waitReady();
        clickSubscriptionPlanNext();

        inputBillingInfo(map);
        // clickSaveButton();
        inputPaymentInfo(map);
        // clickSaveButton();
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(3);
        new HamburgerMenuPage(false).clickPlaceOrder1();
        // gotomainpage.click();
        waitReady();
    }

}
