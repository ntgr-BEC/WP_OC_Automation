package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.CommonElement;
import webportal.webelements.DeviceBackupRestoreElement;

public class DeviceBackupRestorePage extends DeviceBackupRestoreElement {
    Logger        logger                 = Logger.getLogger("DeviceBackupRestorePage");
    public String backupDir              = null;
    public String backupDownloadName     = null;
    public String backupName             = "backupName";
    public String backupDesc             = "backupDesc";
    public Path   pathDownload           = null;
    public Path   pathbackupDownloadName = null;
    public String devSerialNo            = WebportalParam.br1serialNo;

    public String linkSuffix = "configBackup";

    public void initData() {
        String pathUser = System.getProperty("user.home");
        pathDownload = Paths.get(pathUser, "Downloads");
        backupDir = pathDownload.toString();
        if (!pathDownload.toFile().exists())
            throw new RuntimeException("Cannot find download path: " + backupDir);
        pathbackupDownloadName = Paths.get(backupDir, "backup.zip");
        backupDownloadName = pathbackupDownloadName.toString();
    }

    public DeviceBackupRestorePage() {
        initData();
        logger.info("init...");
    }

    public DeviceBackupRestorePage(String devNo) {
        devSerialNo = devNo;
        initData();
        logger.info("init...");
    }

    /**
     * build link like "#/devices/%s/configBackup"
     */
    public void gotoPage() {
        String sURL = getCurrentUrl();
        String[] devType = sURL.split("/");
        WebCheck.checkUrl(String.format("#/devices/%s/configBackup", devType[devType.length - 2]));
    }

    /**
     * @param devType
     *            like 'br', 'ap', 'sw'. Must be call after one of device opened
     */
    public void gotoPage(String devType) {
        WebCheck.checkUrl(String.format("#/devices/%s/configBackup", devType));
    }

    public List<String> getBackups() {
        if ($(txtRowName).exists())
            return getTexts(txtRowName.getSearchCriteria());
        else
            return new ArrayList<String>();
    }

    public boolean hasBackup() {
        if (txtRowName.exists())
            return true;
        else
            return false;
    }

    public boolean hasBackup(String name) {
        boolean result = false;
        ElementsCollection eles = $$(sBackupName);
        for (SelenideElement ele : eles) {
            if (getText(ele).contains(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * when there was no backup in table, you cannot upload. so we need to create a fresh default one
     */
    public void createBackup() {
        if (!hasBackup()) {
            createBackup(backupName, backupDesc);
        }
    }

    public void createBackup(String backupName, String backupDesc) {
        if (!hasBackup(backupName)) {
            clickCreate();
            assert (!btnDeviceOffline.exists());
            setText(txtCreateBackupName, backupName);
            setText(txtCreateBackupDesc, backupDesc);
            clickBoxLastButton();
            waitReady();
            waitBackupRestore();
        }
    }

    /**
     * do modify for an existed backup to default one
     */
    public void modifyBackName() {
        createBackup();
        if (!hasBackup(backupName)) {
            modifyBackName(backupName, backupDesc);
        }
    }

    public void modifyBackName(String backupName, String backupDesc) {
        editLine(sTableDevices, 1, 2);
        setText(txtBackupName, backupName);
        setText(txtBackupDesc, backupDesc);
        clickBoxLastButton();
        waitReady();
    }

    /**
     * @param bkFilename
     *            download default backup to local
     */
    public void downloadBackup(String bkFilename) {
        String fileName = backupDir + bkFilename;
        File fp = new File(fileName);

        editLineNew(sTableDevices, 1, backupName, 0);
        waitReady();
        CommonElement.closeDownloadBar();
        new File(backupDownloadName).renameTo(fp);
    }

    /**
     * download backup to local with default file name
     */
    public void downloadBackup() {
        editLine(sTableDevices, 1, backupName, 0);
        sleepi(10);
        CommonElement.closeDownloadBar();
        takess("check download file name");
    }

    public void deleteBackup() {
        deleteBackupNew(backupName);
    }

    public void deleteBackup(String backupName) {
        if (hasBackup()) {
            editLine(sTableDevices, 1, backupName, 3);
            clickBoxLastButton();
            MyCommonAPIs.sleepi(5);
        }
    }

    public void deleteBackups() {
        for (String name : getBackups()) {
            deleteBackup(name);
        }
    }

    public void deleteBackupNew(String backupName) {
        if (hasBackup()) {
            editLineNew(sTableDevices, 1, backupName, 3);
            clickBoxLastButton();
            MyCommonAPIs.sleepi(5);
        }
    }

    public void deleteAllBackup() {
        takess("delete All data");
        for (int i = 1; i <= 3; i++) { 
            if (hasBackup()) { 
                
                String sRow = String.format("%s tr:nth-of-type(%d)", sTableDevices, i);  
                String sCell = String.format("%s td:last-child", sRow);

                int imageCount = $$(sRow).get(0).$$("img").size();
                System.out.println("imageCount: "+imageCount);
                if (imageCount > 3) {
                    $(sCell).hover();
                    $$(sRow).get(0).$$("img").get(3).click();
                    sleepi(4);
                    waitReady();
                    clickBoxLastButton();
                    MyCommonAPIs.sleepi(5);
                    break;
                }
                waitReady();
            } else {
                break;
            }
        }
    }


    public void uploadBackup() {
        btnUpload.click();
        setText(txtUploadName, backupName);
        setText(txtUploadDesc, backupDesc);
        CommonElement.ChooseFile(getDownloadBackupFile());
        takess("check uploaded file name");
        clickBoxLastButton();
        waitReady();
    }

    /**
     * restore default backup to device
     */
    public void restoreBackup() {
        restoreBackup(backupName);
    }

    public void restoreBackupNew() {
        restoreBackupNew(backupName);
    }

    public void restoreBackup(String backupName) {
        editLine(sTableDevices, 1, backupName, 1);
        takess("take a restore to device");
        clickBoxLastButton();
        waitReady();
        waitBackupRestore();
        sleep(300, "wait device is reonline");
    }

    public void restoreBackupNew(String backupName1) {
        editLineNew(sTableDevices, 1, backupName1, 1);
        clickBoxLastButton();
        waitReady();
        waitBackupRestore();
    }

    public void DeviceBackup(String mode) {
        createBackup();
        String fileName = backupDir + mode;
        File fp = new File(fileName);
        if (!hasBackup(backupName)) {
            if (!fp.exists()) {
                modifyBackName();
                downloadBackup(mode);
            } else {
                uploadBackup();
            }
        } else {
            if (!fp.exists()) {
                downloadBackup(mode);
            }
        }
    }

    public void DeviceRestore(String mode) {
        createBackup();
        if (!hasBackup(backupName)) {
            uploadBackup();
        }
        restoreBackupNew();
    }

    public void editBackup(String oldName, String BackupName, String backupDesc) {
        if (hasBackup()) {
            editLineNew(sTableDevices, 1, oldName, 2);
            setText(txtBackupName, BackupName);
            setText(txtBackupDesc, backupDesc);
            clickBoxLastButton();
            waitReady();
        }
    }

    public void uploadBackup(String backupDownloadName) {
        btnUpload.click();
        setText(txtUploadName, backupName);
        setText(txtUploadDesc, backupDesc);
        CommonElement.ChooseFile(backupDir.replaceAll("/", "\\\\") + "\\" + backupDownloadName);
        clickBoxLastButton();
        waitReady();
    }

    public String getDownloadBackupFile() {
        return Paths.get(backupDir, getDownloadBackupFile(devSerialNo)).toString();
    }

    public String getDownloadBackupFile(String serialNo) {
        String fileName = "";
        List<File> fileList = new ArrayList<File>();
        File file = new File(backupDir);
        File[] files = file.listFiles();
        if (files.length == 0)
            return null;

        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            }
        }
        for (File f1 : fileList) {
            if (f1.getName().contains(serialNo)) {
                fileName = f1.getName();
                logger.info(f1.getName() + " is existed.");
                break;
            }
        }
        return fileName;
    }

    /**
     * delete all downloaded files with device SerialNo
     */
    public void deleteDownloadFiles() {
        deleteDownloadFile(devSerialNo);
    }

    public void deleteDownloadFile(String serialNo) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(backupDir);
        File[] files = file.listFiles();
        if (files == null)
            return;

        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            }
        }
        for (File f1 : fileList) {
            if (f1.getName().contains(serialNo)) {
                f1.delete();
                logger.info("Delete " + f1.getName() + " success.");
            }
        }
    }

    public void editLineNew(String sTable, int iColumn, String sMatch, int iImg) {
        editLine(sTable, iColumn, sMatch, iImg);
    }

    public boolean checkBackupTableInfo() {
        boolean result = false;
        if (hasBackup(backupName) && $x("//td[text()='" + backupDesc + "']").exists() && !txtRowStatus.exists()) {
            result = true;
            logger.info("Backup table information is correct.");
        }
        return result;
    }

    public boolean checkBackupLimit() {
        boolean result = false;
        clickCreate();
        MyCommonAPIs.sleepi(5);
        if (getPageErrorMsg().contains("The maximum number of backup files has been reached".toLowerCase())) {
            clickBoxLastButton();
            result = true;
            logger.info("Backup limit message displayed.");
        }
        return result;
    }
    
    
    public String getfilename(String fil) {
        
          String fileName = "";       
           String backupDir = "D:\\downTeju";
           
           System.out.println(backupDir);
           List<File> fileList = new ArrayList<File>();
           File file = new File(backupDir);
           File[] files = file.listFiles();
           if (files.length == 0)
               return null;

           for (File f : files) {
               if (f.isFile()) {
                   fileList.add(f);
               }
           }
           for (File f1 : fileList) {
               if (f1.getName().contains(fil)) {
                   fileName = f1.getName();
                   logger.info(f1.getName() + " is existed.");
                   break;
               }
           }
           
           return fileName;
        
        
    }
    
    
    public void deleteFile(String fileName, String savePath) {
        try {
            logger.info("Delete file start.");
            Process p = Runtime.getRuntime().exec("cmd /c del /f" + savePath + '\\' + fileName);
            logger.info("Delete file end.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
