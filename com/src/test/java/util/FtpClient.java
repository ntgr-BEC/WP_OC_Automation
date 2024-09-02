package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import io.netty.util.CharsetUtil;

public class FtpClient {
    Logger           logger               = Logger.getLogger("FtpClient");
    public FTPClient handle               = null;
    String           remotePath           = "longrun4wp";
    String           localFileName        = "bigfile.dat";
    String           localFileNameTmp     = "bigfile_tmp.dat";
    String           localFilePath        = "d:/lavi/";
    String           localFileFullName    = localFilePath + localFileName;
    String           localFileFullNameTmp = localFilePath + localFileNameTmp;

    public FtpClient(String user, String passwd, String ip) {
        Init(user, passwd, ip, 21);
    }

    public FtpClient(String user, String passwd, String ip, int port) {
        Init(user, passwd, ip, port);
    }

    public void Init(String user, String passwd, String ip, int port) {
        logger.info(user + "/" + passwd + "/" + ip);
        handle = new FTPClient();
        handle.setDefaultTimeout(10 * 1000);
        handle.setConnectTimeout(15 * 1000);
        handle.setDataTimeout(15 * 60 * 1000);
        handle.setAutodetectUTF8(true);
        handle.setCharset(CharsetUtil.UTF_8);
        handle.setControlEncoding(CharsetUtil.UTF_8.name());

        try {
            handle.connect(ip, port);
            if (!handle.login(user, passwd)) {
                logger.warning("failed to login");
                return;
            }
            handle.enterLocalPassiveMode();
            handle.setFileType(FTP.BINARY_FILE_TYPE);
            handle.changeWorkingDirectory(remotePath);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void UploadFile(String localfile, String remotefile) {
        logger.info(localfile + "/" + remotefile);
        try {
            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new FileInputStream(localfile));
            handle.dele(remotefile);
            if (!handle.storeFile(remotefile, buffIn)) {
                logger.warning("failed to upload file");
            }
            buffIn.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void UploadFile() {
        UploadFile(localFileFullName, localFileName);
    }

    public void DownloadFile(String remotefile, String localfile) {
        logger.info(remotefile + "/" + localfile);
        try {
            if (!handle.retrieveFile(remotefile, new FileOutputStream(localfile))) {
                logger.warning("failed to download file");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void DownloadFile() {
        DownloadFile(localFileName, localFileFullNameTmp);
    }

    public long getFile(String fileName) {
        logger.info(fileName);
        try {
            FTPFile[] lsFile = handle.listFiles(fileName);
            return lsFile[0].getSize();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public long getRemoteFileSize() {
        return getFile(localFileName);
    }

    /**
     * @param isNew
     *            true to locate new download file, false to locate original file
     * @return
     */
    public long getLocalFileSize(boolean isNew) {
        if (isNew)
            return new File(localFileFullNameTmp).length();
        else
            return new File(localFileFullName).length();
    }
}
