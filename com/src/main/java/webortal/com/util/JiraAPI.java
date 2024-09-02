/**
 * 
 */
package webortal.com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.net.HttpHeaders;

/**
 * @author zheli
 *
 */
public class JiraAPI {

    /**
     * @param args
     * @throws IOException
     * @throws ClientProtocolException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String username = "";
    public String password = "";
    public String server = "";
    public String useTestRun = "";
    public List<String> testrun = new ArrayList();
    public String RunCaseStatus = "";
    public String testPlan = "";
    public String configBy = "";

    public JiraAPI() {
        init();
        
    }

    public void init() {
        username = JiraParam.username;
        password = JiraParam.password;
        server = JiraParam.server;
        testPlan = JiraParam.testPlan;
        useTestRun = JiraParam.useTestRun;
        configBy =JiraParam.configBy;
        testrun=JiraParam.testrun;
        RunCaseStatus =JiraParam.RunCaseStatus;
    }

    public static void main(String[] args) {
//
//        JiraAPI jApi = new JiraAPI();
//        JSONObject json = new JSONObject();
//        json.put("projectKey", "PRJCBUGEN-P44");
//        json.put("testCaseKey", "PRJCBUGEN-T4751");
//        json.put("status", "Fail");
//        json.put("environment", "Firefox Browser");
//        json.put("comment", "The test has failed on some automation tool procedure.");
//        json.put("userKey", "bgu");
//        json.put("executionTime", "75496");
//        json.put("executionDate", "2017-11-29T10:36:15Z");
//        String url = jApi.server + "rest/atm/1.0/testresult";
//        jApi.jiraPostAPI(url, json);

    }

    /**
     * use to generatal testng package name
     * 
     * @return
     */
    public List<String> getTestNgPackageName() {
        List<String> packageString = new ArrayList();
        String APIurlurl = server + "rest/atm/1.0/testrun/" + testrun;
        JSONObject jsonObject = jiraGetAPI(APIurlurl);
        JSONArray items = jsonObject.getJSONArray("items");
        int testcaseSize = items.size();
        for (int i = 0; i < testcaseSize; i++) {
            String testCaseKey = JSONPath.eval(jsonObject, "$.items.testCaseKey[" + i + "]").toString();
            String testCaseStatus = JSONPath.eval(jsonObject, "$.items.status[" + i + "]").toString();
            // only run status in RunCaseStatus
            if (RunCaseStatus.contains(testCaseStatus)) {
                String testCaseurl = server + "rest/atm/1.0/testcase/" + testCaseKey;
                JSONObject jsonObject2 = jiraGetAPI(testCaseurl);
                String autoamtion = JSONPath.eval(jsonObject2, "$.customFields.Automation").toString();
                // only run automaiton case
                if (autoamtion.equals("Yes")) {
                    String folder = JSONPath.eval(jsonObject2, "$.folder").toString().replace("/", ".");
                    String packageName = testCaseKey.replace("-", "");
                    System.out.println(packageName);
                    packageString.add("webportal.testcase" + folder + "." + packageName);
                }
            }

        }
        System.out.println("get packageName done!");
        return packageString;
    }

    public void importTestNgResult(HashMap map) {
        XMLManager xmlManager = new XMLManager();

    }

    /**
     * 绕过验证
     * 
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public  SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    /**
     * 模拟请求
     * 
     * @param url
     *            资源地址
     * @param map
     *            参数列表
     * @param encoding
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public  JSONObject jiraGetAPI(String url) {
        String body = "";
        JSONObject jsonObject = null;
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = null;
        try {
            sslcontext = createIgnoreVerifySSL();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);
        // 创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        // 创建get方式请求对象
        HttpGet request = new HttpGet(url);

        // 设置参数到请求对象中
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            try {
                body = EntityUtils.toString(entity, "UTF-8");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jsonObject = JSONObject.parseObject(body);
        }
        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 释放链接
        try {
            response.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    public  JSONObject jiraPutAPI(String url, JSONObject jsonObject) {
        String body = "";
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = null;
        try {
            sslcontext = createIgnoreVerifySSL();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);
        // 创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        // 创建post方式请求对象
        HttpPut request = new HttpPut(url);
        // 设置参数到请求对象中
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        String jsonString = jsonObject.toJSONString();
        System.out.println(jsonString);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(jsonString);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        stringEntity.setContentType("application/json");
        request.setEntity(stringEntity);
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            try {
                body = EntityUtils.toString(entity, "UTF-8");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jsonObject = JSONObject.parseObject(body);
        }
        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 释放链接
        try {
            System.out.println("http put success :"+url);
            response.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
    public  JSONObject jiraPostAPI(String url,JSONObject jsonObject) {
        String body = "";
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = null;
        try {
            sslcontext = createIgnoreVerifySSL();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);
        // 创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        // 创建post方式请求对象
        HttpPost request = new HttpPost(url);
        // 设置参数到请求对象中
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        String jsonString=jsonObject.toJSONString();
        System.out.println(jsonString);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(jsonString);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        stringEntity.setContentType("application/json");
        request.setEntity(stringEntity);
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            try {
                body = EntityUtils.toString(entity, "UTF-8");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jsonObject = JSONObject.parseObject(body);
        }
        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 释放链接
        try {
            response.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}
