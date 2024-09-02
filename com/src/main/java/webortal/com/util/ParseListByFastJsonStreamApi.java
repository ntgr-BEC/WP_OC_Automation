package webortal.com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONReader;

/**
 * 用来解析json文件
 * 
 * @author zheli
 *
 */
public class ParseListByFastJsonStreamApi {

    private static final String FILE_PATH = "D:\\workspace\\com\\src\\test\\resources\\testcase.json";

    public static void main(String[] args) throws FileNotFoundException {
        parseData();
    }

    public static List<String> parseData() throws FileNotFoundException {
        List<String> testName = new ArrayList<String>();
        JSONReader jsonReader = new JSONReader(new FileReader(new File(FILE_PATH)));

        jsonReader.startObject();// 将整个json文件当作 Map<String,Object> 对象来解析 {,}
        while (jsonReader.hasNext()) {
            String key = jsonReader.readString();
            if (key.equals("totalCount"))// "key"对应的Object只有一个
            {
                Object obj = jsonReader.readObject();//
                String val = obj.toString();
                System.out.println("obj: " + obj + ", value: " + val);
            } else if (key.equals("testCases")) {// "anotherKey"对应的是一个List对象
                jsonReader.startArray();// ---> [ 开启读List对象
                while (jsonReader.hasNext()) {

                    jsonReader.startObject();
                    while (jsonReader.hasNext()) {
                        String objKey = jsonReader.readString();
                        String objVal = jsonReader.readObject().toString();
                        System.out.println("objKey: " + objKey + ", objVal: " + objVal);
                        testName.add(objVal);
                    }
                    jsonReader.endObject();
                }
                jsonReader.endArray();// ---> ]
            }
        }
        jsonReader.endObject();
        jsonReader.close();
        return testName;
    }
}
