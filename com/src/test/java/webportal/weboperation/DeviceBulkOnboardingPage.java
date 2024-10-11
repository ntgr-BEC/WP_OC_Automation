package webportal.weboperation;
import java.awt.Robot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBulkOnboardingElement;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;


public class DeviceBulkOnboardingPage extends DeviceBulkOnboardingElement {

    Logger logger;
    public void GoToSummaryPage(String locationName) {
        // TODO Auto-generated constructor stub
        String hrefSummary = "#/dashboard/location";
        hrefSummary = hrefSummary + "/"+ locationName;
        System.out.println(hrefSummary);
        WebCheck.checkHrefIcon(hrefSummary);
        String pageName = this.getClass().getSimpleName();
        //logger = Logger.getLogger(pageName);
        //logger.info("init...");
    }
    public String ImportWrongformatFile(String FilePath) {
        Robot robot;
        boolean result;
        String warningContent = "";
        addDeviceButton.click();
        MyCommonAPIs.sleepi(2);
        addMultiDevice.click();
        MyCommonAPIs.sleepi(5);
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
//        click(selectAll, true);
//        MyCommonAPIs.sleepi(10);        
       if(updatelocationButton.isDisplayed()) {
        updatelocationButton.click();
        MyCommonAPIs.sleepi(50);
    }
//        Viewdevice.click();
        if (warningOfWrongformat.exists()) {
            warningContent = warningOfWrongformat.getText();
            System.out.println(warningContent);
            if (closeWarningBulkOnboarding.exists()) {
                closeWarningBulkOnboarding.click();
            } else if(clocewarning.exists()) {
                clocewarning.click();  
            } 
        }
        return warningContent;
    }
    
    public void Deviceimage(String serialNo, String locationName, String MAC) {
      
        String warningContent = "";
        Locationdevice.click();
        addSinglelocation.click();
        MyCommonAPIs.sleepi(5);
        sendserial.sendKeys(serialNo);
        MyCommonAPIs.sleepi(5);
        selctlocation.selectOption(locationName);
        MyCommonAPIs.sleepi(3);
        goDeviceBtn.click();
        MyCommonAPIs.sleepi(3);
        macAddress.sendKeys(MAC);      ;      
        MyCommonAPIs.sleepi(5);
//        Go.click();
        MyCommonAPIs.sleepi(5);
        Next.click();
        MyCommonAPIs.sleepi(5);
        System.out.print("view checking device");
//        if (viewDevices1.exists()) {
//            viewDevices1.click();
//            logger.info("Clicked on View device");
//        }
//        System.out.print("view after checking device");
        MyCommonAPIs.sleepi(15);
    }
    
    public void bannerAdding(String serialNo, String locationName, String Mac) {
        
        String warningContent = "";
        BannerSingle.click();
        MyCommonAPIs.sleepi(5);
        sendserial.sendKeys(serialNo);
        MyCommonAPIs.sleepi(3);
        goDeviceBtn.click();
        MyCommonAPIs.sleepi(3);
        macAddress.sendKeys(Mac);      ;
        MyCommonAPIs.sleepi(5);
//        Go.click();
        MyCommonAPIs.sleepi(5);
        Next.click();
        MyCommonAPIs.sleepi(5);     
        viewDevices1.click();
        MyCommonAPIs.sleepi(5);  
        
    }
    
    public void wronglocationName(String FilePath) {
               
        String warningContent = "";
        changeaddDeviceButton.click();
        addMultiDevicechange.click();
        MyCommonAPIs.sleepi(5);
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(5);
        click(SelectALL);
        MyCommonAPIs.sleepi(5);
        upload.click();
  
    }
    
    
    public void addMultipleDeviceViaBanner(String FilePath) {
        MyCommonAPIs.sleepi(5);
        AddMultipleDevicebanner.click();
        MyCommonAPIs.sleepi(5);
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(5);
        click(SelectALL);
        MyCommonAPIs.sleepi(5);
        upload.click();
        MyCommonAPIs.sleepi(10);
    }
    
    public String ImportWrongformatFile85(String FilePath) {
        Robot robot;
        boolean result;
        String warningContent = "";
        SummaryAdd.click();
        SummaryMultiple.click();
        MyCommonAPIs.sleepi(5);
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(5);
//        click(SelectALL);
//        MyCommonAPIs.sleepi(5);
//        upload.click();
        MyCommonAPIs.sleepi(10);
        if (warningOfWrongformat.exists()) {
            warningContent = warningOfWrongformat.getText();
            System.out.println(warningContent);
        }
        closewarning.click();
        return warningContent;
    }
    
    
    public String GetcurrentPath() {
       
       File f = new File(this.getClass().getResource("").getPath());
       String filePath  = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\BulkOnboarding\\");
       
      System.out.println(filePath);
      return filePath;
        
    }
    
    public String GetcurrentPathWebPoratlPerformanceImprovement() {
        
        File f = new File(this.getClass().getResource("").getPath());
        String filePath  = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\WebPoratlPerformanceImprovement\\");
        
       System.out.println(filePath);
       return filePath;
         
     }
    
    public String GetcurrentPathIMPro() {
        
        File f = new File(this.getClass().getResource("").getPath());
        String filePath  = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\IMPro25CreditTrail\\");
        
       System.out.println(filePath);
       return filePath;
         
     }
    public boolean addMultiDeviceButtonExistOrNOT() {
       
        boolean result = false;
        
        addDeviceButton.click();
        if (addMultiDevice.exists()) {
            result = true;
            System.out.print(result);
        }
        return result;
    }
   
    public String SetExcelFile(List<Map<String, String>> ListDevInfo,String FileName) throws IOException {
      
        File file = new File(FileName);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("创建成功");
            } else {
                System.out.println("创建失败");
            }
        }
        //定义文件名格式并创建
        File csvFile = File.createTempFile("Device", ".csv", new File(FileName));
        FileName = FileName + "\\"+csvFile.getName();
        System.out.println(FileName);
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(FileName));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("DEVICE NAME", "DEVICE SERIAL", "LOCATION NAME"));
        System.out.print(ListDevInfo.size());
         for(int i = 0; i< ListDevInfo.size(); i++) {
             System.out.print(i);
             System.out.println("ttt111111111111111111111111111");
             System.out.println(ListDevInfo.get(i).get("DEVICE NAME"));
             System.out.println(ListDevInfo.get(i).get("DEVICE SERIAL"));
             System.out.println(ListDevInfo.get(i).get("LOCATION NAME"));
             csvPrinter.printRecord(ListDevInfo.get(i).get("DEVICE NAME"), ListDevInfo.get(i).get("DEVICE SERIAL"),ListDevInfo.get(i).get("LOCATION NAME"));
         }
        csvPrinter.flush();
        csvPrinter.close();
        return FileName;
     
        

         
     
    }
 public static void RemoveInfoOfCsv(String FileName)  {
      
        
     File file = new File(FileName);
      boolean result = file.delete();
       
        

         
     
    }
    public void ImportCvsFile(String FilePath) {
        boolean result= true;
        String warningContent = "";
        addDeviceButton.click();
        addMultiDevice.click();        
        MyCommonAPIs.sleepi(5);        
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        updatelocationButton.click();
        System.out.println("sadf322222222222222222222222222222114");
       
    }
    public void ImportCvsFileAdd(String FilePath) {
        boolean result= true;
        String warningContent = "";
        addDeviceButton.click();
        addMultiDevice.click();        
        MyCommonAPIs.sleepi(5);        
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
    }
    public String ImportExcessMaxNumCvsFile(String FilePath) {
        Robot robot;
        boolean result;
        String warningContent = "";
        addDeviceButton.click();
        addMultiDevice.click();
        System.out.println("sadf322222222222222222222222222222111");
        MyCommonAPIs.sleepi(5);
        System.out.println("sadf322222222222222222222222222222112");
        browseButton.sendKeys(FilePath);
        System.out.println("sadf322222222222222222222222222222113");
        MyCommonAPIs.sleepi(10);
        continueButton.click();
        MyCommonAPIs.sleepi(5);
        if (errorMassege.exists()) {
            warningContent = errorMassege.getText();
            System.out.println(warningContent);
        }
        return warningContent;
    }
    public void DownloadCvsFile() {
        Robot robot;
        //boolean result;
        String warningContent = "";
        SummaryAdd.click();
        SummaryMultiple.click();
        System.out.println("sadf322222222222222222222222222222111");
        MyCommonAPIs.sleepi(5);
        System.out.println("sadf322222222222222222222222222222112");      
        clickhereButton.click();
        System.out.println("sadf322222222222222222222222222222113");
        MyCommonAPIs.sleepi(10);
    }
    public void ImportCvsFileOfWrongLocationName(String FilePath) {
        Robot robot;
        boolean result;
        String warningContent = "";
        SummaryAdd.click();
        SummaryMultiple.click();
        System.out.println("sadf322222222222222222222222222222111");
        MyCommonAPIs.sleepi(5);
        System.out.println("sadf322222222222222222222222222222112");
        browseButton.sendKeys(FilePath);
        System.out.println("sadf322222222222222222222222222222113");
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(10);        
        updatelocationButton.click();
        MyCommonAPIs.sleepi(5);
//        System.out.println("sadf322222222222222222222222222222114");
        Viewdevice.click();
        //selectAll.selectRadio("on");
        //selectAll.click();
        System.out.println("sadf322222222222222222222222222222115");
//        Viewdevice.click();
        
    }
    public String ImportCvsFileWithWrongname(String FilePath) {
        Robot robot;
        boolean result;
        String warningContent = "";
        SummaryAdd.click();
        SummaryMultiple.click();
        System.out.println("sadf322222222222222222222222222222111");
        MyCommonAPIs.sleepi(5);
        System.out.println("sadf322222222222222222222222222222112");
        browseButton.sendKeys(FilePath);
        System.out.println("sadf322222222222222222222222222222113");
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        updatelocationButton.click();
        MyCommonAPIs.sleepi(10);
        if (errorMassage.exists()) {
            System.out.println("sadf322222222222222222222222222222115");
            warningContent = errorMassage.getText();
            System.out.println(warningContent);
        }
        return warningContent;
    }
    
    public String ImportCvsFileWithWrongname85(String FilePath) {
        Robot robot;
        boolean result;
        String warningContent = "";
        addDeviceButton.click();
        addMultiDevice.click();
        System.out.println("sadf322222222222222222222222222222111");
        MyCommonAPIs.sleepi(5);
        System.out.println("sadf322222222222222222222222222222112");
        browseButton.sendKeys(FilePath);
        System.out.println("sadf322222222222222222222222222222113");
        MyCommonAPIs.sleepi(10);
        MyCommonAPIs.sleepi(5);
//        updatelocationButton.click();
        MyCommonAPIs.sleepi(10);
        if (warningoferrormessage.exists()) {
            System.out.println("sadf322222222222222222222222222222115");
            warningContent = warningoferrormessage.getText();
            System.out.println(warningContent);
        }
        return warningContent;
    }
    
    public String ImportCvsFileWithmultierrors(String FilePath) {
        Robot robot;
        boolean result;
        String warningContent = "";
        SummaryAdd.click();
        SummaryMultiple.click();
        System.out.println("sadf322222222222222222222222222222111");
        MyCommonAPIs.sleepi(5);
        System.out.println("sadf322222222222222222222222222222112");
        browseButton.sendKeys(FilePath);
        System.out.println("sadf322222222222222222222222222222113");
        MyCommonAPIs.sleepi(10);
//        continueButton.click();
        MyCommonAPIs.sleepi(5);
        if (warningoferrormessage.exists()) {
            System.out.println("sadf322222222222222222222222222222115");
            warningContent = warningoferrormessage.getText();
            System.out.println(warningContent);
        }
        return warningContent;
    }
    
    public void ImportCvsFileOrg(String FilePath) {
        boolean result= true;
        OrgSettings.click();
        MyCommonAPIs.sleepi(5); 
        Orgdevice.click();  
        MyCommonAPIs.sleepi(5); 
        if (orgSettingsAddDevicesButton.exists()) {
            orgSettingsAddDevicesButton.click();
        } else if(OrgAddButton.exists()) {
            OrgAddButton.click();
        }
        MyCommonAPIs.sleepi(5);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(20);
//        click(selectAll, true);
//        MyCommonAPIs.sleepi(5);
//        updateDevicelist.click();
//        MyCommonAPIs.sleepi(5);
        Viewdevice.click();
        MyCommonAPIs.sleepi(10);
    }
    public void ImportCvsFilesummaryscreen(String FilePath) {
        boolean result= true;
        SummaryAdd.click();
        SummaryMultiple.click();        
        MyCommonAPIs.sleepi(10);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(20);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        updatelocationButton.click();
        MyCommonAPIs.sleepi(5);
        Viewdevice.click();
        MyCommonAPIs.sleepi(10);
    }
    public void ImportCvsFileRouterscreen(String FilePath) {
        WebCheck.checkHrefIcon(URLParam.hrefRouters);
        boolean result= true;
//        RouterClick.click();
        MyCommonAPIs.sleepi(5); 
        RouterAdd.click();
        MyCommonAPIs.sleepi(5);
        RouterMultiple.click();        
        MyCommonAPIs.sleepi(10);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        if (updatelocationButton.exists()) {
            updatelocationButton.click();
        }
        MyCommonAPIs.sleepi(5);
        Viewdevice.click();
        MyCommonAPIs.sleepi(10);
    }
    
    public void ImportCvsFileMobilescreen(String FilePath) {
        WebCheck.checkHrefIcon(URLParam.hrefMobile);
        boolean result= true;
        MobileAdd.click();
        MobileMultiple.click();        
        MyCommonAPIs.sleepi(5);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        if (updatelocationButton.exists()) {
            updatelocationButton.click();
        }
        MyCommonAPIs.sleepi(10);
        Viewdevice.click();
        MyCommonAPIs.sleepi(10);
    }
    
    public void ImportCvsFileWirelessscreen(String FilePath) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        boolean result= true;
        if (WirelessAdd.exists() && wirelessAddButton.exists()) {
            wirelessAddButton.click();
        } else {
        WirelessAdd.click();
        }
        if (wirelessPageaddMultipleDevices.exists()) {
            wirelessPageaddMultipleDevices.click();
        } else if(WirelessMultiple.exists()) {
            WirelessMultiple.click(); 
        }       
        MyCommonAPIs.sleepi(5);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        if (updatelocationButton.exists()) {
            updatelocationButton.click();
        }
        System.out.println("sadf322222222222222222222222222222114");
        Viewdevice.click();
        MyCommonAPIs.sleepi(10);
    }
    
    public void ImportCvsFileWiredscreen(String FilePath) {
        WebCheck.checkHrefIcon(URLParam.hrefWiredQuickView);
        boolean result= true;
        WiredAdd.click();
        WiredMultiple.click();        
        MyCommonAPIs.sleepi(5);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        if (updatelocationButton.exists()) {
            updatelocationButton.click();
        }
        MyCommonAPIs.sleepi(10);
        Viewdevice.click();
        MyCommonAPIs.sleepi(10);
    }
    
    public void ImportCvsFilestoragescreen(String FilePath) {
        WebCheck.checkHrefIcon(URLParam.hrefStorage);
        boolean result= true;
        StorageAdd.click();
        StorageMultiple.click();        
        MyCommonAPIs.sleepi(5);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        if (updatelocationButton.exists()) {
            updatelocationButton.click();
        }
        System.out.println("sadf322222222222222222222222222222114");
//        Viewdevice.click();
//        MyCommonAPIs.sleepi(10);
    }
    public void RenameDevice() {
        DeviceName.click();
        MyCommonAPIs.sleepi(3); 
        DeviceName.clear();
        MyCommonAPIs.sleepi(3); 
        DeviceName.sendKeys("Ap1");
        MyCommonAPIs.sleepi(3); 
        DeviceName2.click();
        MyCommonAPIs.sleepi(3); 
        DeviceName2.clear();
        MyCommonAPIs.sleepi(3); 
        DeviceName2.sendKeys("AP2");
        MyCommonAPIs.sleepi(10); 
        click(selectAll, true);
        MyCommonAPIs.sleepi(10); 
        Uploaddevice.click();
    }
    
//    from tejeshwini
    
    
    public void ImportCvsFilesummaryscreenBySelectingALL(String FilePath) {
        boolean result= true;
        SummaryAdd.click();
        SummaryMultiple.click();        
        MyCommonAPIs.sleepi(5);        
        OrgUpload.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(5);
        if (updatelocationButton.exists()) {
            updatelocationButton.click();
        }
        System.out.println("sadf322222222222222222222222222222114");
    }
    
    public void ImportmultipledevicesformatFile(String FilePath) {
        Robot robot;
        changeaddDeviceButton.click();
        MyCommonAPIs.sleepi(2);
        addMultiDevicechange.click();
        MyCommonAPIs.sleepi(5);
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(15);
        click(selectAll, true);
        MyCommonAPIs.sleepi(20);        
        if(updatelocationButton.isDisplayed()){
            updatelocationButton.click();
            System.out.println("Update location details is clicked");
        }
        MyCommonAPIs.sleepi(10);
        Viewdevice.click();

    }
    public String GetcurrentPath1() {
        
        File f = new File(this.getClass().getResource("").getPath());
        String filePath  = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\ProBulkOnboardingEnhancement");
        
       System.out.println(filePath);
       return filePath;
         
     }
    
    public void ImportmultipledevicesformatFile1(String FilePath) {
        Robot robot;
        browseButtonOrg.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll, true);
        MyCommonAPIs.sleepi(10);        
        if (updatelocationButton.exists()) {
            updatelocationButton.click();
        }
        MyCommonAPIs.sleepi(5);
        Viewdevice.click();
    } 
    
    public boolean verifysummaryPagemultipleDevicesoption() {
        boolean result = false;
        changeaddDeviceButton.click();
        MyCommonAPIs.sleepi(5);
        if (addMultiDevicechange.exists()) {
            result = true;
            System.out.println("Summary page bulk onboarding verified.");
        }
        return result;
    }
    
    public void GoToRouterVPNPage() {
        MyCommonAPIs.sleepi(5);
        routerPageTab.click();
        MyCommonAPIs.sleepi(5);
    }
    
    public boolean verifyRoutersPagemultipleDevicesoption() {
        boolean result = false;
        routersAddDevicesIcon.click();
        MyCommonAPIs.sleepi(5);
        if (addMultipleDevices.exists()) {
            result = true;
            System.out.println("Routers page bulk onboarding verified.");
        }
        return result;
    }
    
    public void GoToWirelessPage() {
        MyCommonAPIs.sleepi(5);
        wirelessPageTab.click();
        MyCommonAPIs.sleepi(5);
    }
    
    public boolean verifyWirelessPagemultipleDevicesoption() {
        boolean result = false;
        wirelessAdddevicesIcon.click();
        MyCommonAPIs.sleepi(5);
        if (addMultipleDevices.exists()) {
            result = true;
            System.out.println("Wireless page bulk onboarding verified.");
        }
        return result;
    }
    
    public void GoToWiredPage() {
        MyCommonAPIs.sleepi(5);
        wiredPageTab.click();
        MyCommonAPIs.sleepi(5);
    }
    
    public boolean verifyWiredPagemultipleDevicesoption() {
        boolean result = false;
        wiredAdddevicesIcon.click();
        MyCommonAPIs.sleepi(5);
        if (addMultipleDevices.exists()) {
            result = true;
            System.out.println("Wired page bulk onboarding verified.");
        }
        return result;
    }
    
    public void GoToDevicesPage() {
        MyCommonAPIs.sleepi(5);
        devicesPageTab.click();
        MyCommonAPIs.sleepi(5);
    }
    
    public boolean verifyDevicesPagemultipleDevicesoption() {
        boolean result = false;
        devicesAdddevicesIcon.click();
        MyCommonAPIs.sleepi(5);
        if (addMultipleDevices.exists()) {
            result = true;
            System.out.println("Devices page bulk onboarding verified.");
        }
        return result;
    }
    
    public void orgLevelBulkOnboarding() {
        orgLvlAddDevicesIcon.click();
        MyCommonAPIs.sleepi(2);
        if (addMultipleDevices.exists()) {
            System.out.println("Devices page bulk onboarding verified.");
            addMultipleDevices.click();
        }
        MyCommonAPIs.sleepi(2);
    }
    
    public boolean ImportmultipledevicesformatFileWithWrongLocations(String FilePath) {
        boolean result= false;
        browseButtonOrg1.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
//        click(selectAll, true);
//        MyCommonAPIs.sleepi(10);        
//        updatelocationButton.click();
//        MyCommonAPIs.sleepi(5);
//        Viewdevice.click();
//        MyCommonAPIs.sleepi(10);
        if (locatioNotmatcherror.exists() && locatioNotmatcherror1.exists() && locatioEmptyError.exists()) {
            result = true;
            System.out.println("Errors are occured for wrong/empty locations.");
            MyCommonAPIs.sleepi(2);
        }
        return result;
    } 
    
    public String GetcurrentPath12() {
        
        File f = new File(this.getClass().getResource("").getPath());
        String filePath  = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\MHSBulkOnboarding\\");
        
       System.out.println(filePath);
       return filePath;
         
     }
    
    public void ImportCvsFileviaOrgScreen(String FilePath) {
        boolean result= true;
        OrgAdd.click();
        AddMultiple.click();        
        MyCommonAPIs.sleepi(15);        
        OrgUpload1.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        click(selectAll1, true);
        MyCommonAPIs.sleepi(30);
        if(updatelocationButton.isDisplayed()) {
            updatelocationButton.click();
            MyCommonAPIs.sleepi(50);
        }
        Viewdevice.click();
        MyCommonAPIs.sleepi(10);
    }
    
    //AddedByPratik
    public boolean bulkOnboardingErrorMessagesVerify(String FilePath) {
        boolean result = false;
        Robot robot;
        changeaddDeviceButton.click();
        MyCommonAPIs.sleepi(2);
        addMultiDevicechange.click();
        MyCommonAPIs.sleepi(5);
        browseButton.sendKeys(FilePath);
        MyCommonAPIs.sleepi(10);
        SelenideElement messageElement = $(".d-flex.align-items-center.justify-content-between p");
        String expectedMessage = "12 errors found. The following devices were not added due to errors. If you want to add these devices, check the name, serial number, or location of each device, and then try again, or you can continue without these devices at this time.";
        messageElement.shouldHave(Condition.text(expectedMessage));
        Assert.assertEquals(messageElement.text().trim(), expectedMessage, "Message verification failed.");
        result = true;
        return result;
    }
    
}
