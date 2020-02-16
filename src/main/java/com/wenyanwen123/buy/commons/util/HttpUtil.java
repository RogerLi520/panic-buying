package com.wenyanwen123.buy.commons.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc http工具类
 * @Author liww
 * @Date 2019/6/17
 * @Version 1.0
 */
public class HttpUtil {

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 15000;
//    private static org.apache.http.client.CookieStore cookieStore;

    private static HttpClient defaultHttpClient = null;
    private static Object lock_defaultHttpClient = new Object();
    private static CloseableHttpClient defaultCloseableHttpClient = null;
    private static Object lock_defaultCloseableHttpClient = new Object();
    private static CloseableHttpClient SSLCloseableHttpClient = null;
    private static Object lock_SSLCloseableHttpClient = new Object();

    private static HttpClient getDefaultHttpClient() {
        if (defaultHttpClient == null) {
            synchronized (lock_defaultHttpClient) {
                if (defaultHttpClient == null) {
                    defaultHttpClient = new DefaultHttpClient();
                }
            }
        }
        return defaultHttpClient;
    }

    public static CloseableHttpClient getDefaultCloseableHttpClient() {
        if (defaultCloseableHttpClient == null) {
            synchronized (lock_defaultCloseableHttpClient) {
                if (defaultCloseableHttpClient == null) {
                    defaultCloseableHttpClient = HttpClients.createDefault();
                }
            }
        }
        return defaultCloseableHttpClient;
    }

    public static CloseableHttpClient getSSLCloseableHttpClient() {
        if (SSLCloseableHttpClient == null) {
            synchronized (lock_SSLCloseableHttpClient) {
                if (SSLCloseableHttpClient == null) {
                    SSLCloseableHttpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
//                            .setConnectionManager(connMgr)
                            .setDefaultRequestConfig(requestConfig)
                            .build();
                }
            }
        }
        return SSLCloseableHttpClient;
    }

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();

//        cookieStore = new BasicCookieStore();
    }

    /**
     * 设置超时时间
     *
     * @param timeOut
     */
    public void setTimeOut(int timeOut) {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(timeOut);
        // 设置读取超时
        configBuilder.setSocketTimeout(timeOut);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(timeOut);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static String doGet(String url) throws ConnectTimeoutException, SocketTimeoutException {
        return doGet(url, new HashMap<String, String>(), MAX_TIMEOUT);
    }


    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, String> params) throws ConnectTimeoutException, SocketTimeoutException {
        return doGet(url, params, MAX_TIMEOUT);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @param timeOut
     * @return
     */
    public static String doGet(String url, Map<String, String> params, int timeOut) throws ConnectTimeoutException, SocketTimeoutException {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        HttpClient httpclient = getDefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(apiUrl);

            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);

            httpGet.setConfig(configBuilder.build());
            HttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            //System.out.println("执行状态码 : " + statusCode);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取二进制数
     *
     * @param apiUrl
     * @return
     */
    public static byte[] getBytes(String apiUrl) throws ConnectTimeoutException, SocketTimeoutException {
        int timeOut = 10 * 1000;
        byte[] result = null;
        HttpClient httpclient = getDefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(apiUrl);

            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);

            httpGet.setConfig(configBuilder.build());
            HttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = input2byte(instream);
            }
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) throws ConnectTimeoutException, SocketTimeoutException {
        return doPost(apiUrl, new HashMap<String, String>());
    }


    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, String> params) throws ConnectTimeoutException, SocketTimeoutException {
        return doPost(apiUrl, params, MAX_TIMEOUT);
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl  API接口URL
     * @param params  参数map
     * @param timeOut 超时时间
     * @param cookies 传入和传出cookies
     * @return
     */
    public static String doPostWithCookie(String apiUrl, Map<String, String> params, int timeOut, List<Cookie> cookies) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getDefaultCloseableHttpClient();

        HttpClientContext context = new HttpClientContext();
        //设置传入cookie
        if (cookies != null && cookies.size() > 0) {
            context.setCookieStore(new BasicCookieStore());
            for (Cookie c : cookies) {
                context.getCookieStore().addCookie(c);
            }
        }

        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost, context);
            //System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
            //获取cookie
            if (context.getCookieStore() != null) {
                List<Cookie> cs = context.getCookieStore().getCookies();
                if (cs != null && cs.size() > 0) {
                    if (cookies == null) cookies = new ArrayList<>();
                    cookies.clear();
                    cookies.addAll(cs);
                }
            }
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl  API接口URL
     * @param params  参数map
     * @param timeOut 超时时间
     * @return
     */
    public static String doPost(String apiUrl, Map<String, String> params, int timeOut) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getDefaultCloseableHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            //System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json   json字符串
     * @return
     */
    public static String doPost(String apiUrl, String json) throws ConnectTimeoutException, SocketTimeoutException {
        return doPost(apiUrl, json, MAX_TIMEOUT);
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json    json字符串
     * @return
     */
    public static byte[] doPostBytes(String apiUrl, String json) throws ConnectTimeoutException, SocketTimeoutException {
        int timeOut=MAX_TIMEOUT;
        CloseableHttpClient httpClient = getDefaultCloseableHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        byte[] result=null;
        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            StringEntity stringEntity = new StringEntity(json, "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = input2byte(instream);
            }
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doPost(String apiUrl, String json, int timeOut) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getDefaultCloseableHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            StringEntity stringEntity = new StringEntity(json, "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            //System.out.println(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }
    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json    json字符串
     * @param timeOut 超时时间
     * @param cookies 设置或获取cookies
     * @return
     */
    public static String doPostWithCookie(String apiUrl, String json, int timeOut, List<Cookie> cookies) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getDefaultCloseableHttpClient();
        HttpClientContext context = new HttpClientContext();
        //设置传入cookie
        if (cookies != null && cookies.size() > 0) {
            context.setCookieStore(new BasicCookieStore());
            for (Cookie c : cookies) {
                context.getCookieStore().addCookie(c);
            }
        }


        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            StringEntity stringEntity = new StringEntity(json, "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost, context);
            HttpEntity entity = response.getEntity();
            //System.out.println(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity, "UTF-8");
            //获取cookie
            if (context.getCookieStore() != null) {
                List<Cookie> cs = context.getCookieStore().getCookies();
                if (cs != null && cs.size() > 0) {
                    if (cookies == null) cookies = new ArrayList<>();
                    cookies.clear();
                    cookies.addAll(cs);
                }
            }
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），soap形式
     *
     * @param apiUrl    API接口URL
     * @param params    参数map
     * @param method    请求方法名
     * @param namespace 命名空间
     * @param timeOut   超时时间
     * @return
     */
    public static String doPostSoap(String apiUrl, Map<String, String> params, String method, String namespace, int timeOut) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getDefaultCloseableHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            //拼接SOAP
            StringBuilder soapRequestData = new StringBuilder();
            soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>");
            soapRequestData.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
            soapRequestData.append("<soap:Body>");
            soapRequestData.append("<" + method + " xmlns=\"" + namespace + "\">");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                soapRequestData.append("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
            }
            soapRequestData.append("</" + method + ">");
            soapRequestData.append("</soap:Body>" + "</soap:Envelope>");

            //soap设置到post中
            StringEntity stringEntity = new StringEntity(soapRequestData.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("text/xml");
            httpPost.setHeader("Content-type", "text/xml; charset=utf-8");
            httpPost.setHeader("SOAPAction", namespace + method);
            httpPost.setEntity(stringEntity);

            response = httpClient.execute(httpPost);
            //System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, String> params) throws ConnectTimeoutException, SocketTimeoutException {
        return doPost(apiUrl, params, MAX_TIMEOUT);
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     *
     * @param apiUrl  API接口URL
     * @param params  参数map
     * @param timeOut 超时时间
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, String> params, int timeOut) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getSSLCloseableHttpClient();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     *
     * @param apiUrl  API接口URL
     * @param params  参数map
     * @param timeOut 超时时间
     * @param cookies 传入和传出cookies
     * @return
     */
    public static String doPostSSLWithCookie(String apiUrl, Map<String, String> params, int timeOut, List<Cookie> cookies) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getSSLCloseableHttpClient();
        HttpClientContext context = new HttpClientContext();
        //设置传入cookie
        if (cookies != null && cookies.size() > 0) {
            context.setCookieStore(new BasicCookieStore());
            for (Cookie c : cookies) {
                context.getCookieStore().addCookie(c);
            }
        }

        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            //获取cookie
            if (context.getCookieStore() != null) {
                List<Cookie> cs = context.getCookieStore().getCookies();
                if (cs != null && cs.size() > 0) {
                    if (cookies == null) cookies = new ArrayList<>();
                    cookies.clear();
                    cookies.addAll(cs);
                }
            }
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl API接口URL
     * @param json   JSON对象
     * @return
     */
    public static String doPostSSL(String apiUrl, Object json) throws ConnectTimeoutException, SocketTimeoutException {
        return doPostSSL(apiUrl, json, MAX_TIMEOUT);
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl  API接口URL
     * @param json    JSON对象
     * @param timeOut 超时时间
     * @return
     */
    public static String doPostSSL(String apiUrl, Object json, int timeOut) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getSSLCloseableHttpClient();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl  API接口URL
     * @param json    JSON对象
     * @param timeOut 超时时间
     * @param cookies 设置和获取cookies
     * @return
     */
    public static String doPostSSLWithCookie(String apiUrl, Object json, int timeOut, List<Cookie> cookies) throws ConnectTimeoutException, SocketTimeoutException {
        CloseableHttpClient httpClient = getSSLCloseableHttpClient();
        HttpClientContext context = new HttpClientContext();
        //设置传入cookie
        if (cookies != null && cookies.size() > 0) {
            context.setCookieStore(new BasicCookieStore());
            for (Cookie c : cookies) {
                context.getCookieStore().addCookie(c);
            }
        }

        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(timeOut);
            // 设置读取超时
            configBuilder.setSocketTimeout(timeOut);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(timeOut);
            // 在提交请求之前 测试连接是否可用
            configBuilder.setStaleConnectionCheckEnabled(true);
            httpPost.setConfig(configBuilder.build());
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            //获取cookie
            if (context.getCookieStore() != null) {
                List<Cookie> cs = context.getCookieStore().getCookies();
                if (cs != null && cs.size() > 0) {
                    if (cookies == null) cookies = new ArrayList<>();
                    cookies.clear();
                    cookies.addAll(cs);
                }
            }
        } catch (ConnectTimeoutException cte) {
            throw cte;
        } catch (SocketTimeoutException ste) {
            throw ste;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
//        try {
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//
//                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    return true;
//                }
//            }).build();
//            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
//
//                public boolean verify(String arg0, SSLSession arg1) {
//                    return true;
//                }
//
//                public void verify(String host, SSLSocket ssl) throws IOException {
//                }
//
//                public void verify(String host, X509Certificate cert) throws SSLException {
//                }
//
//                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
//                }
//            });
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            sslsf = new SSLConnectionSocketFactory(
                    ctx, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslsf;
    }

}
