package webportal.weboperation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;  
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import io.qameta.allure.Step;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.webelements.HamburgerMenuElement;

//import org.apache.tika.parser.pdf.PDFParser;  
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;  
import java.util.Scanner;



/**
 * @author Tejeshwini K V
 */

public class FileHandling extends HamburgerMenuElement {
    
    
   
  
    
//    public String file(String fileName) throws IOException, SAXException, TikaException {   
//
//        FileInputStream fs = null;
//        BodyContentHandler ch = new BodyContentHandler();  
//        File fl = new File("C:/Users/Lenovo/Downloads/"+fileName+"");         
//        try {
//            fs = new FileInputStream(fl);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }  
//        Metadata md = new Metadata();  
//        ParseContext pc = new ParseContext();   
//        PDFParser pp = new PDFParser();  
//        pp.parse(fs, ch, md, pc);   
//        String str = ch.toString();  
//     
//        System.out.println("Extracting the contents from the file: \n" + ch.toString());  
//        
//        try{
////            File f1=new File("C:\\Users\\Lenovo\\Downloads\\156107251_.pdf");   
//            //File f1=new File("C:\\\\Users\\\\Lenovo\\\\Downloads\\\\156107251_ (1).pdf");  
//            Files.deleteIfExists(Paths.get("C:\\Users\\Lenovo\\Downloads\\156107251_ (1).pdf"));
//            /*Files.deleteIfExists(path)
//            System.out.println(f1.getAbsolutePath());
//            System.out.println("Here::"+f1.canWrite());
//            System.out.println("Done"+f1.renameTo(new File("C:\\\\Users\\\\Lenovo\\\\Downloads\\\\156107251_ (2).pdf")));*/
//            //System.out.println("Hre we are ::"+new File ("C:\\Users\\Lenovo\\Downloads\\156107251_ (1).pdf").exists());
//           /* System.out.println(f1.getAbsoluteFile().getName());
//            System.out.println(f1.getPath());
//            System.out.println(f1.exists());
//
//          
//            if(f1.delete()){
//                System.out.println(f1.getName() + " is deleted!");
//            }else{
//                System.out.println("Delete operation is failed.");
//            }*/
//
//        }
//            catch (Exception e) {
//                System.out.println(e.getStackTrace());
//                // TODO: handle exception
//            }
//      
//        
//        return  str;
//     }
//   
    
    public String fetchFileName (String Path, String Model) {
        String Filename = null;
        String folderPath = Path;
        // Create a File object for the folder
        File folder = new File(folderPath);

        // Check if the folder exists and is a directory
        if (folder.exists() && folder.isDirectory()) {
            // Get all files in the folder
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                // Iterate through the files and add their names to the list
                for (File file : listOfFiles) {                 
                     if (file.isFile()) {
//                          Filename = file.getName();
                       if(file.getName().contains(Model) && !file.getName().contains("jfrog_fw_download.py")) {
                        return Filename = file.getName();// Add file name to the list
                       }
                    }
                }
            }
        }
     return Filename;
    }
    
    public String file(String fileName) throws IOException, SAXException, TikaException {   

//      FileInputStream fs = null;
     

      BodyContentHandler ch = new BodyContentHandler();  
      File fl = new File("D:\\downTeju\\"+fileName+"");      
      System.out.println("check1");
//      try {
//          fs = new FileInputStream(fl);
//      } catch (FileNotFoundException e) {
//          e.printStackTrace();
//      }  
      FileInputStream fs = new FileInputStream(fl);  
      Metadata md = new Metadata();  
      ParseContext pc = new ParseContext();   
      PDFParser pp = new PDFParser();          
      pp.parse(fs, ch, md, pc);   
      String str = ch.toString();   
   
      System.out.println("Extracting the contents from the file: \n" + ch.toString());  
      fs.close();
      fl.delete();
//      try{
////          File f1=new File("C:\\Users\\Lenovo\\Downloads\\156107251_.pdf");   
//          //File f1=new File("C:\\\\Users\\\\Lenovo\\\\Downloads\\\\156107251_ (1).pdf");  
//          Files.deleteIfExists(Paths.get("C:\\Users\\Lenovo\\Downloads\\156107251_ (1).pdf"));
          /*Files.deleteIfExists(path)
          System.out.println(f1.getAbsolutePath());
          System.out.println("Here::"+f1.canWrite());
          System.out.println("Done"+f1.renameTo(new File("C:\\\\Users\\\\Lenovo\\\\Downloads\\\\156107251_ (2).pdf")));*/
          //System.out.println("Hre we are ::"+new File ("C:\\Users\\Lenovo\\Downloads\\156107251_ (1).pdf").exists());
         /* System.out.println(f1.getAbsoluteFile().getName());
          System.out.println(f1.getPath());
          System.out.println(f1.exists());

        
          if(f1.delete()){
              System.out.println(f1.getName() + " is deleted!");
          }else{
              System.out.println("Delete operation is failed.");
          }*/

//      }
//          catch (Exception e) {
//              System.out.println(e.getStackTrace());
//              // TODO: handle exception
//          }
    
      
      return  str;
   }
    public void createFile(String path) {
        File myObj = new File(path);  
        try {       
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
            } else {
            System.out.println("File already exists.");
            }
         } catch (IOException e) {
           System.out.println("An error occurred.");
           e.printStackTrace();
         }
            
    }
    
    public void writeFile(String path,String Vap) {
        FileWriter myWriter;
        try {              
            File output = new File("C:\\Auto\\filename.txt");
            FileWriter writer = new FileWriter(output);

            writer.write(Vap);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   


   public String ssidBroadcast(String path, String Ssid, String band) throws IOException{
       int linecount = 0;            
       String line="";
       String  wantedline = "";
       String  wantedline1 = "";
          
       try {
           String stringSearch = Ssid;
           BufferedReader bf = new BufferedReader(new FileReader(path));

          
           MyCommonAPIs.sleepi(4);
           System.out.println("Searching for " + stringSearch + " in file...");
                      
           line = bf.readLine();

           while (( line = bf.readLine()) != null)
           {
           int indexfound = line.indexOf(stringSearch);
           linecount++;
           
            if (indexfound > -1) {
                
                
                if(band == "2.4") {
                    if(line.contains("wifi0")){
                        wantedline = line;
                        System.out.println(wantedline); 
                    } 
                } else if(band == "5.0") {
                    if(line.contains("wifi1")){
                        wantedline = line;
                        System.out.println(wantedline); 
                    } 
                } else if (band == "6.0") { 
                    if(line.contains("wifi2")){
                        wantedline = line;
                        System.out.println(wantedline); 
                    }     
                } else {
                    System.out.println("Band push is fail");
              }
              }
              }
       } catch (IOException e) {
        System.out.println("IO Error Occurred: " + e.toString());
       }
       
       return wantedline;      
       }  
   
   public String fetchFileName () {
       String Filename = "";
       String folderPath = "C:\\auto";
       // Create a File object for the folder
       File folder = new File(folderPath);

       // Check if the folder exists and is a directory
       if (folder.exists() && folder.isDirectory()) {
           // Get all files in the folder
           File[] listOfFiles = folder.listFiles();

           if (listOfFiles != null) {
               // Iterate through the files and add their names to the list
               for (File file : listOfFiles) {                 
                    if (file.isFile()) {
                         Filename = file.getName();
                      if(!file.getName().equals("tftpd32.exe") && !file.getName().equals("tftpd32.ini")) {
                       return Filename = file.getName();// Add file name to the list
                      }
                   }
               }
           }
       }
    return Filename;
   }

    public static void deleteAllExcept(String fileToKeep) {
        File folder1 = new File("C:\\Auto");

        // Check if the folder exists and is a directory
        if (folder1.exists() && folder1.isDirectory()) {
            // Get the list of files in the folder
            File[] listOfFiles = folder1.listFiles();

            if (listOfFiles != null) {
                // Iterate through the files
                for (File file : listOfFiles) {
                    // Delete the file if its name doesn't match the file to keep
                    if (file.isFile() && !file.getName().equals(fileToKeep) && !file.getName().equals("tftpd32.ini")) {
                        boolean deleted = file.delete(); // Delete the file
                        if (deleted) {
                            System.out.println("Deleted: " + file.getName());
                        } else {
                            System.out.println("Failed to delete: " + file.getName());
                        }
                    }
                }
            }
        } else {
            System.out.println("The folder does not exist or is not a directory.");
        }
        
    }
       
    public static void deleteAllExcept(String fileToKeep, String path) {
        File folder1 = new File(path);

        // Check if the folder exists and is a directory
        if (folder1.exists() && folder1.isDirectory()) {
            // Get the list of files in the folder
            File[] listOfFiles = folder1.listFiles();
             System.out.println("list of file is "+listOfFiles);
            if (listOfFiles != null) {
                // Iterate through the files
                for (File file : listOfFiles) {
                    // Delete the file if its name doesn't match the file to keep
                    if (file.isFile() && !file.getName().equals(fileToKeep)) {
                        boolean deleted = file.delete(); // Delete the file
                        if (deleted) {
                            System.out.println("Deleted: " + file.getName());
                        } else {
                            System.out.println("Failed to delete: " + file.getName());
                        }
                    }
                }
            }
        } else {
            System.out.println("The folder does not exist or is not a directory.");
        }
        
    }
	
	
	public boolean assertLogAfterTime(String logFilePath,  String ssid) throws IOException  {
      
        boolean found = false;
        BufferedReader br = new BufferedReader(new FileReader(logFilePath));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains("connected to " + ssid)) {
                    System.out.println("✅ Found log: " + line);
                    found = true;
                    break;
                }
            }

        br.close();
        return found;
        }
 }
       
    
   
    
   
       
