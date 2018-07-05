package com.haru.mybatis.util;

import com.haru.mybatis.exception.CustomException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author HARU
 * @since 2018/7/5
 */
public class HttpUtils {
    static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    /**
     * 使用get方式利用HttpClient请求数据
     *
     * @param url
     * @param params
     * @param respCharset
     * @return
     */
    public static String get(String url, Map<String, String> params, String respCharset) {
        HttpClient client = null;
        GetMethod method = null;
        InputStream resStream = null;
        BufferedReader br = null;
        try {
            client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            method = new GetMethod(url);
            method.setRequestHeader("Connection", "close");
            if (params != null) {
                List<NameValuePair> nvList = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    NameValuePair nvp = new NameValuePair();
                    nvp.setName(key);
                    nvp.setValue(params.get(key));
                    nvList.add(nvp);
                }
                method.setQueryString(nvList.toArray(new NameValuePair[]{}));
            }
            int code = client.executeMethod(method);
            resStream = method.getResponseBodyAsStream();
            br = new BufferedReader(new InputStreamReader(resStream, respCharset));
            StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            String response = resBuffer.toString();
            if (code != 200) {
                throw new CustomException("错误状态码:" + code + response);
            }
            return response;
        } catch (Exception e) {
            logger.error("get请求出错：", e);
            throw new CustomException("远程连接到" + url + "，发生错误:" + e.getMessage());
        } finally {
            if (resStream != null) {
                try {
                    resStream.close();
                } catch (IOException e) {
                    logger.error("get请求出错：", e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("get请求出错：", e);
                }
            }
            if (method != null) {
                try {
                    method.releaseConnection();
                    ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
                } catch (Exception e) {
                    logger.error("get请求出错：", e);
                }
            }
            if (client != null)
                client.getHttpConnectionManager().closeIdleConnections(0);

        }
    }

    /**
     * 使用get方式利用HttpClient请求数据
     *
     * @param url
     * @param headers HashUtils.getMap("Content-type", "application/json;charset=UTF-8")
     * @param params
     * @param respCharset
     * @param body
     * @return
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params, String respCharset, String body) {
        if (url.startsWith("https")) {
            Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
            Protocol.registerProtocol("https", https);
        }
        HttpClient client = null;
        PostMethod method = null;
        InputStream resStream = null;
        BufferedReader br = null;
        try {
            client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            method = new PostMethod(url);
            method.setRequestHeader("Connection", "close");
            // 增加头信息
            if (null != headers) {
                for (String key : headers.keySet()) {
                    method.setRequestHeader(key, headers.get(key));
                }
            }
            if (params != null) {
                MultiValueMap<String, String> paramsMulti = new LinkedMultiValueMap<String, String>();
                ConcurrentSkipListMap cbody = new ConcurrentSkipListMap();
                for (String key : params.keySet()) {
                    paramsMulti.add(key, HashUtils.getStringValue(params, key));
                }
                cbody.putAll(paramsMulti);
                String sourceStr = SortCollection.sort(null, cbody);    //排序
                String sign = MD5.sign(sourceStr, "", "");  //加密
                url = UriComponentsBuilder.fromHttpUrl(url).queryParams(paramsMulti).build().toUriString(); //生成地址
            }
            if (StringUtils.isNotBlank(body)) {
                method.setRequestBody(body);    //设置body会清空method.addParameter(nvp)的设置
            }
            int code = client.executeMethod(method);
            resStream = method.getResponseBodyAsStream();
            br = new BufferedReader(new InputStreamReader(resStream, respCharset));
            StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            String response = resBuffer.toString();

            if (code != 200) {
                throw new CustomException("错误状态码:" + code + response);
            }
            return response;
        } catch (Exception e) {
            logger.error("post请求错误：", e);
            throw new CustomException("远程连接到" + url + "，发生错误:" + e.getMessage());
        } finally {
            if (url.startsWith("https")) {
                Protocol.unregisterProtocol("https");
            }
            if (resStream != null) {
                try {
                    resStream.close();
                } catch (IOException e) {
                    logger.error("post请求错误：", e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("post请求错误：", e);
                }
            }
            if (method != null) {
                try {
                    method.releaseConnection();
                    ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
                } catch (Exception e) {
                    logger.error("post请求错误：", e);
                }
            }
            if (client != null)
                client.getHttpConnectionManager().closeIdleConnections(0);
        }
    }
}