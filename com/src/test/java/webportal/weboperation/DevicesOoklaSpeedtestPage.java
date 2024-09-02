package webportal.weboperation;

import java.util.Calendar;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceOoklaSpeedtestElement;

public class DevicesOoklaSpeedtestPage extends DeviceOoklaSpeedtestElement {
    Logger logger;
    public void GoToTroubleshootPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefNetworkTroubleShoot);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    public void NetworkTroubleshootPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    public void gotoAPOoklaSpeedtestpage() {
        System.out.println("d222222222222223333333333333333330");
        WebCheck.checkHrefIcon(URLParam.hrefAPOoklaSpeedtest);
        System.out.println("d222222222222223333333333333333331");
        MyCommonAPIs.sleep(5 * 1000);       
        //logger.info("init... go to speed page");
       // System.out.println("d22222222222222333333333333333333");
    } 
    public void ClickRuntestButton() {
        btnRuntest.click();
        MyCommonAPIs.sleepi(60);
       // logger.info("click run test button...");
    }
    public String GetUploadInfo() {
        System.out.println("d22222222222222333333333333333333112");
        String UploadInfo = elementUpload.getText();
        System.out.println(UploadInfo);
        return UploadInfo;
    }
    public String GetDownloadInfo() {
        String DownloadInfo = elementDownload.getText();
        return DownloadInfo;
    }
    public String GetLatencyInfo() {
        String LatencyInfo = elementLatency.getText();
       
        System.out.println(LatencyInfo);
        return LatencyInfo;
    }
    public String getSystemDate() {
        //logger.info("Get system Date");
        Calendar cal = Calendar.getInstance();
         System.out.println("12222222222222222222222222");
        // cal.set(2018, 07, 31);
         String hour = String.valueOf(cal.get(Calendar.AM_PM));
         System.out.println(hour);
        String min = String.valueOf(cal.get(Calendar.MINUTE));
        System.out.println(min);
        String Date = String.valueOf(cal.get(Calendar.DATE));
        System.out.println(Date);
        String Month = String.valueOf(cal.get(Calendar.MONDAY));
        System.out.println(Month);
        String Year = String.valueOf(cal.get(Calendar.YEAR));
        System.out.println(Year);
        String systemTime = Date+", " + Year + " | " ;
        System.out.println(systemTime);
        return systemTime;
    }
    public String getWebpotalTime() {
        String webportaltime = elementwebportaltime.getText();
        return webportaltime;
    }
}
    
