package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ContentFilteringElement;
import webportal.webelements.GlobalNotificationElement;

public class ContentFilteringPage extends ContentFilteringElement {
    Logger logger = Logger.getLogger("ContentFilteringElement");
    boolean result = false;
    public ContentFilteringPage() {
        logger.info("init...");
    }
    
    public boolean isenableCF() {
        boolean result = false;
        MyCommonAPIs.sleepi(30);
        
        System.out.println(getCFStatus());
        
        if (!getCFStatus()) {
            logger.info("CF is not enabled");
            result = true;
        }
        
       return result;
   }
    
    public boolean DisableCF() {
        boolean result = false;
        MyCommonAPIs.sleep(20);
        CFstatus.click();
        MyCommonAPIs.sleep(30);
        if (CFDisable.exists()) {
            CFconfirm.click();
            MyCommonAPIs.sleep(20);
            Save.click();     
            MyCommonAPIs.sleep(30);
            result = true;
        }
        return result;
    }
    
    public void EnableOrDisableCF(String Num) {
        MyCommonAPIs.sleepi(30);
        
        System.out.println(getCFStatus());
     
        MyCommonAPIs.sleepi(5);
       if (Num.equals("0")) {           
           if(getCFStatus()) {           
               CFstatus1.click();
               MyCommonAPIs.sleep(20);
               YesCF.click();
           }else {
               System.out.println("alredy CF is disabled");
           }
           
       }else {           
           if(!getCFStatus()) {  
               MyCommonAPIs.sleep(10);
               CFstatus1.click();
           }else {
               System.out.println("alredy CF is enabled");
           }
       }
       
   }
    
    public void EnableSafeSearch(String Num) {
        MyCommonAPIs.sleep(10);
        SafeSearch.click();
        MyCommonAPIs.sleep(10);
        Save.click();     
        MyCommonAPIs.sleep(10);
        refresh();
        MyCommonAPIs.sleep(30);
   }
    
    public boolean EnableNewAccount() {
        boolean result = false;
        MyCommonAPIs.sleepi(20);
        CFstatus1.click();
        MyCommonAPIs.sleepi(30);
        if(CFTrail.isDisplayed()) {
            CFTrail.click();
            MyCommonAPIs.sleepi(10);
            result =true;
        }
        
        return result;        
    }
    
    public boolean Report(String ReportName) {
        boolean result = false;
        
        if(ReportName.equals("Weekly")) {
            logger.info("reportgenarated for Weekly");
            click(WeeklyReport);
            Save.click();     
            MyCommonAPIs.sleepi(30);
            System.out.println(WeeklyReport.isSelected());
            if(MonthlyReport.isSelected()) {
                logger.info("Mailsent sucessfully");
                result= true;            
            }
            
        }else if(ReportName.equals("Monthly")) {
            logger.info("reportgenarated for Monthly");
            click(MonthlyReport);
            Save.click();     
            MyCommonAPIs.sleepi(30);
            System.out.println(MonthlyReport.isSelected());
            if(MonthlyReport.isSelected()) {
                logger.info("Mailsent sucessfully");
                result= true;
            }
        }else {
            logger.info("reportgenarated for Daily");
            click(DailyReport);
            Save.click();     
            MyCommonAPIs.sleepi(30);
            System.out.println(DailyReport.isSelected());
            if(DailyReport.isSelected()) {
                logger.info("Mailsent sucessfully");
                result= true;
            }
                    
        }       
        return result;
        
    }
    
    public void SendtoDeny(String URL) { 
        MyCommonAPIs.sleepi(3);
        AllowCategories.click();
        MyCommonAPIs.sleepi(20);
        AllowSearch.click();
        MyCommonAPIs.sleepi(20);
        AllowSearch.sendKeys(URL);
        MyCommonAPIs.sleepi(20);
        AllowSelect.click();
        MyCommonAPIs.sleepi(20);
        ArrowRight.click();
        MyCommonAPIs.sleepi(20);
        Save.click();     
        MyCommonAPIs.sleepi(20);
        refresh();
        MyCommonAPIs.sleepi(30);
    }
    
    public void AddAllowlist(String Domain, String Description) {
        MyCommonAPIs.sleep(10);
        AddAllow.click();
        MyCommonAPIs.sleep(20);
        DomainString.sendKeys(Domain);
        DomainDiscription.sendKeys(Description);
        AllowButton.click();
        MyCommonAPIs.sleep(30);
        Save.click();     
        MyCommonAPIs.sleep(10);
        refresh();
        MyCommonAPIs.sleep(30);
        
    }
    
    public void AddBlocklist(String Domain, String Description) {
        MyCommonAPIs.sleepi(10);
        AddBlock.click();
        MyCommonAPIs.sleepi(20);
        DomainString.sendKeys(Domain);
        DomainDiscription.sendKeys(Description);
        BlockButton.click();
        MyCommonAPIs.sleepi(30);
        Save.click();     
        MyCommonAPIs.sleepi(10);
        refresh();
        MyCommonAPIs.sleepi(30);
        
    }
    
    public boolean AddBlocklistError(String Domain, String Description) {       
        boolean result = false;
        MyCommonAPIs.sleep(10);
        AddBlock.click();
        MyCommonAPIs.sleep(20);
        DomainString.sendKeys(Domain);
        DomainDiscription.sendKeys(Description);
        BlockButton.click();
        MyCommonAPIs.sleep(30);
        if(blocklisterror.exists()) {            
            Closeblocklisterror.click();
            logger.info("Error exits");
            result = true;
        }
        return result;
    }
    
    public void DeleteAllowlist(String Domain) {
        if (DomaininAllowlist(Domain)) {
            logger.info("Delete Domain.");
            executeJavaScript("arguments[0].removeAttribute('class')", editdomain(Domain));
            MyCommonAPIs.sleep(3000);
            deleteDomain(Domain).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(6000);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
            Save.click();     
            MyCommonAPIs.sleep(10);
            refresh();
            MyCommonAPIs.sleep(30);
        }
        
    }
    
    public boolean DomaininAllowlist(String Domain) {
        boolean result = false;
        String sElement = String.format("//span[text()='%s']", Domain);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Domain:" + Domain + " is existed.");
        }
        
        return result;
    }
    
  public void SendtoAllow(String URL) { 
        refresh();
        MyCommonAPIs.sleep(30);
        MyCommonAPIs.sleep(3);
        BlockedCategories.click();
        MyCommonAPIs.sleep(10);
        BlockedSearch.click();
        MyCommonAPIs.sleep(10);
        BlockedSearch.sendKeys(URL);
        MyCommonAPIs.sleep(10);
        DenySelect.click();
        MyCommonAPIs.sleep(10);
        ArrowLeft.click();
        MyCommonAPIs.sleep(10);
        Save.click();       
        MyCommonAPIs.sleep(30);
    }
    
    public void gotoCF() {
        MyCommonAPIs.sleep(5);
        CF.click();
    }
    
    public void gotoCFResult() {
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        waitReady();
        if (openorderhis.exists()) {
            openorderhis.click();
        }
        MyCommonAPIs.sleepi(10);
    }
    
    public boolean checkBuyCFResult(String year, String number) {
        boolean result = false;
        gotoCFResult();
        ElementsCollection eles = $$x(CFOrderTable);
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        if (orderhistoryqty.exists()) {
            for (SelenideElement ele : eles) {
                System.out.println(ele);
                if (!ele.findElement(By.xpath("td[3]/p[1]")).getText().contains("Cancelled")) {
                    String actOnDateText = ele.findElement(By.xpath("td[3]/p[2]")).getText();
                    String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
                    actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
                    expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
                    System.out.println(actOnDate);
                    System.out.println(expOnDate);
                    orderQty = ele.findElement(By.xpath("td[2]/span")).getText();
                    System.out.println(orderQty);
                    break;
                }
            }
            if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == Integer.valueOf(year)) && orderQty.equals(number)) {
                result = true;
                logger.info("Order history display correct.");
            }
        }
        return result;
    }
    
    public boolean checkBuyCFTopUpResult(String year, String number) {
        boolean result = false;
        gotoCFResult();
        ElementsCollection eles = $$x(CFOrderTabletop);
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        if (orderhistoryqtytop.exists()) {
            for (SelenideElement ele : eles) {
                System.out.println(ele);
                if (!ele.findElement(By.xpath("td[3]/p[1]")).getText().contains("Cancelled")) {
                    String actOnDateText = ele.findElement(By.xpath("td[3]/p[2]")).getText();
                    String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
                    actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
                    expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
                    System.out.println(actOnDate);
                    System.out.println(expOnDate);
                    orderQty = ele.findElement(By.xpath("td[2]/span")).getText();
                    System.out.println(orderQty);
                    break;
                }
            }
            if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == Integer.valueOf(year)) && orderQty.equals(number)) {
                result = true;
                logger.info("Order history display correct.");
            }
        }
        return result;
    }

    public boolean checkCFServicesSubscription(String GroupNum, String UserCredits) {
        boolean result = false;
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        waitReady();
        if (CFservices.exists()) {
            CFservices.click();
            waitReady();
            MyCommonAPIs.sleepi(10);
            if (CFtotalgroup.exists()) {
                System.out.println("enterd CF group");
                String Totalgroup = CFtotalgroup.getText();
                System.out.println(Totalgroup);
                System.out.println(GroupNum);
                if (CFtotalgroup.getText().equals(String.valueOf(Integer.parseInt(GroupNum))) && $x(String.format(availablecredits,
                        String.valueOf((Integer.parseInt(GroupNum)) - Integer.parseInt(getText(CFtotalgroup))))).exists()) {
                    result = true;
                    logger.info("Vpn services display correct.");
                }
            } 
        }
        return result;
    }
    
    public boolean checkCFTopUpSubscription(String GroupNum, String UserCredits) {
        boolean result = false;
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        waitReady();
        if (CFservices.exists()) {
            CFservices.click();
            waitReady();
            MyCommonAPIs.sleepi(10);
            if (CFTopUptotalgroup.exists()) {
                System.out.println("enterd CF group");
                String Totalgroup = CFtotalgroup.getText();
                System.out.println(Totalgroup);
                System.out.println(GroupNum);
                if (CFTopUptotalgroup.getText().equals(String.valueOf(Integer.parseInt(GroupNum))) && $x(String.format(availablecreditsTopUp,
                        String.valueOf((Integer.parseInt(GroupNum)) - Integer.parseInt(getText(CFTopUptotalgroup))))).exists()) {
                    result = true;
                    logger.info("Vpn services display correct.");
                }
            } 
        }
        return result;
    }
    
    public void inputLicenceAndFinishSignin(Map<String, String> map) {
        boolean result = false;
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        waitReady();
        if (CFservices.exists()) {
            CFservices.click();
        }
        AddCFKey.click();
        MyCommonAPIs.sleepi(10);
        SendCFKey.setValue(map.get("Licence Key"));
        logger.info("Input licence:" + map.get("Licence Key"));
        AddsucessFully.click();
        MyCommonAPIs.sleepi(10);
    }
    
}
