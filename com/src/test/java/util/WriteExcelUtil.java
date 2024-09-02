/**
 *
 */
package util;

import java.io.BufferedReader;
/**
 * @author zheli
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import webportal.param.WebportalParam;

public class WriteExcelUtil {
    private static final String EXCEL_XLS  = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {
        OutputStream out = null;
        testCreateExcel();
        XMLManagerForTest xmlManagerForTest = new XMLManagerForTest();

        File finalXlsxFile = new File(System.getProperty("user.dir") + "/target/JiraAPIReport.xlsx");

        Workbook wb = WorkbookFactory.create(finalXlsxFile);

        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("testRunKey");
        row.createCell(1).setCellValue("testCaseKey");
        row.createCell(2).setCellValue("status");
        row.createCell(3).setCellValue("environment");
        row.createCell(4).setCellValue("comment");
        row.createCell(5).setCellValue("userKey");
        row.createCell(6).setCellValue("executionTime");
        row.createCell(7).setCellValue("executionDate");
        out = new FileOutputStream(finalXlsxFile);
        workBook.write(out);
    }

    public static void testCreateExcel() {
        // 1:创建一个excel文档
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 2:创建一个sheet工作表，名为“学生成绩”
        XSSFSheet sheet = workbook.createSheet("学生成绩");
        // 3:创建首行
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("testRunKey");
        row.createCell(1).setCellValue("testCaseKey");
        row.createCell(2).setCellValue("status");
        row.createCell(3).setCellValue("environment");
        row.createCell(4).setCellValue("comment");
        row.createCell(5).setCellValue("userKey");
        row.createCell(6).setCellValue("executionTime");
        row.createCell(7).setCellValue("executionDate");

        // 7:创建输出流，讲excel文档存盘到d:/score.xls
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(System.getProperty("user.dir") + "/target/JiraAPIReport.xlsx");
            workbook.write(fos);
            fos.flush();
            System.out.println("存盘完成！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeExcel(List<Map> dataList, int cloumnCount, String finalXlsxPath) {
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum(); // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);
                String name = dataMap.get("BankName").toString();
                String address = dataMap.get("Addr").toString();
                String phone = dataMap.get("Phone").toString();
                for (int k = 0; k <= columnNumCount; k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(name);

                    Cell second = row.createCell(1);
                    second.setCellValue(address);

                    Cell third = row.createCell(2);
                    third.setCellValue(phone);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) { // Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    public HashMap<String, HashMap<String, String>> ntgrReadTranslateFile() throws IOException {
        File finalXlsxFile = new File(System.getProperty("user.dir") + "/src/test/resources/GUI_localization.xlsx");
        Workbook workBook = getWorkbok(finalXlsxFile);
        Sheet sheet = workBook.getSheetAt(0);
        HashMap<String, HashMap<String, String>> oRet = new HashMap<String, HashMap<String, String>>();
        int rowIdx = 5;
        int maxCol = 0;

        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            maxCol = row.getLastCellNum();
            String name = row.getCell(2).toString();
            System.out.println("name:" + name.toLowerCase());
            if (name.toLowerCase().equals("enu")) {
                rowIdx = i - 2;
                break;
            }
        }
        System.out.println(rowIdx);
        System.out.println(maxCol);

        for (int i = 3; i < maxCol; i++) {
            Row row = sheet.getRow(rowIdx);
            String cellValLan = row.getCell(i).toString().trim();
            System.out.println(cellValLan);
            HashMap<String, String> oLan = new HashMap<String, String>();

            for (int j = rowIdx + 1; j <= sheet.getLastRowNum(); j++) {
                Row rowLang = sheet.getRow(j);
                if (!rowLang.getCell(2).toString().trim().equals("")) {
                    oLan.put(rowLang.getCell(2).toString().trim(), rowLang.getCell(i).toString().trim());
                }
            }
            oRet.put(cellValLan, oLan);
        }
        /*
         * for (String key : oRet.keySet()){ System.out.println(key+"->"); for (String key2 : oRet.get(key).keySet()){
         * System.out.println(key2+":"+oRet.get(key).get(key2));
         *
         * } }
         */
        workBook.close();
        return oRet;
    }

    public String ReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public Map<String, Object> parseJSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            // System.out.println("k: "+k.toString()+",v:"+v.toString());
            if (v instanceof JSONArray) {
                // List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                // Iterator<JSONObject> it = ((JSONArray) v).iterator();
                // while (it.hasNext()) {
                // JSONObject json2 = it.next();
                // list.add(parseJSON2Map(json2.toString()));
                // }
                // map.put(k.toString(), list);

            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    public Map<String, Object> getJsonMap(String jsonFile) {
        String japanesetext = ReadFile(jsonFile);
        Map<String, Object> map3 = new HashMap<String, Object>();
        Map<String, Object> map4 = new HashMap<String, Object>();
        Map<String, Object> mapJapanese = new HashMap<String, Object>();
        map3 = parseJSON2Map(japanesetext);
        for (String key : map3.keySet()) {
            // System.out.println("key: "+key+",value:"+map.get(key));
            map4 = parseJSON2Map(map3.get(key).toString());
            for (String key2 : map4.keySet()) {
                mapJapanese.put(key2, map4.get(key2).toString());
            }
        }

        return mapJapanese;
    }

    public HashMap<String, String> getJsonStringMap(String jsonString) {
        Map<String, Object> map = new HashMap<String, Object>();
        HashMap<String, String> mapTo = new HashMap<String, String>();
        map = parseJSON2Map(jsonString);
        for (String key : map.keySet()) {
            // System.out.println("key: "+key+",value:"+map.get(key));
            mapTo.put(key, map.get(key).toString());

        }

        return mapTo;
    }

    public HashMap<String, Map<String, Object>> ntgrAnalysisJsonFile() {
        HashMap<String, Map<String, Object>> mapLang = new HashMap<String, Map<String, Object>>();
        String lanFile = System.getProperty("user.dir") + "/src/test/resources/english.json";
        String lanJsonContext = ReadFile(lanFile);
        Map<String, Object> mapLan = new HashMap<String, Object>();
        mapLan = parseJSON2Map(lanJsonContext);
        mapLang.put("en", mapLan);

        // System.out.println("japanese----------------");
        if (WebportalParam.BrowserLanguage.toLowerCase().equals("jp")) {
            lanFile = System.getProperty("user.dir") + "/src/test/resources/japanese.json";
            lanJsonContext = ReadFile(lanFile);
            mapLan = new HashMap<String, Object>();
            mapLan = parseJSON2Map(lanJsonContext);
            mapLang.put("jp", mapLan);
        }

        // System.out.println("german----------------");
        if (WebportalParam.BrowserLanguage.toLowerCase().equals("de")) {
            lanFile = System.getProperty("user.dir") + "/src/test/resources/german.json";
            lanJsonContext = ReadFile(lanFile);
            mapLan = new HashMap<String, Object>();
            mapLan = parseJSON2Map(lanJsonContext);
            mapLang.put("de", mapLan);
        }

        if (WebportalParam.BrowserLanguage.toLowerCase().equals("fe")) {
            lanFile = System.getProperty("user.dir") + "/src/test/resources/german.json";
            lanJsonContext = ReadFile(lanFile);
            mapLan = new HashMap<String, Object>();
            mapLan = parseJSON2Map(lanJsonContext);
            mapLang.put("fe", mapLan);
        }

        if (WebportalParam.BrowserLanguage.toLowerCase().equals("es")) {
            lanFile = System.getProperty("user.dir") + "/src/test/resources/german.json";
            lanJsonContext = ReadFile(lanFile);
            mapLan = new HashMap<String, Object>();
            mapLan = parseJSON2Map(lanJsonContext);
            mapLang.put("es", mapLan);
        }

        if (WebportalParam.BrowserLanguage.toLowerCase().equals("it")) {
            lanFile = System.getProperty("user.dir") + "/src/test/resources/german.json";
            lanJsonContext = ReadFile(lanFile);
            mapLan = new HashMap<String, Object>();
            mapLan = parseJSON2Map(lanJsonContext);
            mapLang.put("it", mapLan);
        }

        if (WebportalParam.BrowserLanguage.toLowerCase().equals("cn")) {
            lanFile = System.getProperty("user.dir") + "/src/test/resources/chinese.json";
            lanJsonContext = ReadFile(lanFile);
            mapLan = new HashMap<String, Object>();
            mapLan = parseJSON2Map(lanJsonContext);
            mapLang.put("cn", mapLan);
        }

        return mapLang;

        /*
         * for(String key : mapEng.keySet()) { System.out.println("----mapEng: "+key+",value:"+mapEng.get(key));
         *
         * } for(String key : mapJapanese.keySet()) {
         * System.out.println("----mapJapanese: "+key+",value:"+mapJapanese.get(key));
         *
         * } for(String key : mapGerman.keySet()) { System.out.println("----mapGerman: "+key+",value:"+mapGerman.get(key));
         *
         * }
         *
         * HashMap<String, String> mapTo = getJsonStringMap(mapGerman.get("common").toString()); for(String key :
         * mapTo.keySet()) { System.out.println("----mapGerman value: "+mapTo.get(key));
         *
         * }
         */
    }

    public void ntgrReadJsonFile() {
        String englishFile = System.getProperty("user.dir") + "/src/test/resources/english.json";
        String JsonContext = ReadFile(englishFile);
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> mapEng = new HashMap<String, Object>();
        map = parseJSON2Map(JsonContext);
        for (String key : map.keySet()) {
            // System.out.println("key: "+key+",value:"+map.get(key));
            map2 = parseJSON2Map(map.get(key).toString());
            for (String key2 : map2.keySet()) {
                mapEng.put(key2, map2.get(key2).toString());
            }
        }

        // System.out.println("japanese----------------");
        String japaneseFile = System.getProperty("user.dir") + "/src/test/resources/japanese.json";
        Map<String, Object> mapJapanese = getJsonMap(japaneseFile);

        // System.out.println("german----------------");
        String germanFile = System.getProperty("user.dir") + "/src/test/resources/german.json";
        Map<String, Object> mapGerman = getJsonMap(germanFile);

        Map<String, String> mapEngToJap = new HashMap<String, String>();
        Map<String, String> mapEngToGerman = new HashMap<String, String>();

        Map<String, String> mapJap = new HashMap<String, String>();
        Map<String, String> mapGer = new HashMap<String, String>();

        for (String key : mapEng.keySet()) {
            // System.out.println("mapEng----------------:"+key+",val---:"+mapEng.get(key));
            if (mapJapanese.containsKey(key)) {
                mapEngToJap.put(mapEng.get(key).toString(), mapJapanese.get(key).toString());
            }

            if (mapGerman.containsKey(key)) {
                mapEngToGerman.put(mapEng.get(key).toString(), mapGerman.get(key).toString());
            }
        }

        for (String key : mapJapanese.keySet()) {
            mapJap.put(key, mapJapanese.get(key).toString());
        }

        for (String key : mapGerman.keySet()) {
            mapGer.put(key, mapGerman.get(key).toString());
        }

        /*
         * for(String key3 : mapEngToJap.keySet()) {
         * System.out.println("mapEngToJap---:"+key3+",value3:"+mapEngToJap.get(key3));
         *
         * } for(String key3 : mapEngToGerman.keySet()) {
         * System.out.println("mapEngToGerman---:"+key3+",value3:"+mapEngToGerman.get(key3));
         *
         * }
         *
         * for(String key : mapJap.keySet()) { System.out.println("mapJap---:"+key+",value:"+mapJap.get(key));
         *
         * } for(String key3 : mapGer.keySet()) { System.out.println("mapGer---:"+key3+",value3:"+mapGer.get(key3));
         *
         * }
         */
    }

}
