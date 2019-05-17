package com.tcg.mis.common.http;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.exception.TimeoutException;
import com.tcg.mis.common.log.TcgLogFactory;
import com.tcg.mis.common.util.JsonUtils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

/**
 * Description: http client <br/>
 *
 * @author Eddie
 */
public class HttpClientTool {

    private final static Logger LOGGER = TcgLogFactory.getLogger(HttpClientTool.class);

    public static final String CONTENT_TYPE_DEFAULT = "application/x-www-form-urlencoded";

    @Autowired
    private CloseableHttpClient httpClient;

    public <T> T doGet(String url, Class<T> clazz) {
    	Map<String, String> headers = Maps.newHashMap();
        return doGet(url, clazz, headers);
    }
    
    public <T> T doGet(String url, Class<T> clazz, List<NameValuePair> nvps) {
        try {
        	NameValuePair[] nvpsArr = new NameValuePair[nvps.size()];
        	nvpsArr = nvps.toArray(nvpsArr);
            String responseString = doGet(url, nvpsArr);
            return JsonUtils.fromJson(responseString, clazz);
        } catch (Exception e) {
            LOGGER.warn("[HTTP] json parse failed. get: " + url, e);
        }
        return null;
    }

    public <T> T doGet(String url, Class<T> clazz, NameValuePair... nvps) {
        try {
            String responseString = doGet(url, nvps);
            return JsonUtils.fromJson(responseString, clazz);
        } catch (Exception e) {
            LOGGER.warn("[HTTP] json parse failed. get: " + url, e);
        }
        return null;
    }
    
    public <T> T doGet(String url, Class<T> clazz, Map<String, String> headers) {
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        if(headers != null) {
        	for(Entry<String, String> header: headers.entrySet()) {
        		httpget.setHeader(header.getKey(), header.getValue());
            }
        }
        LOGGER.info("[HTTP] start get: {}", url);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (CloseableHttpResponse response = httpClient.execute(httpget)) {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            LOGGER.info("[HTTP] end get: {}, elapsed: {}, response: {}", url, stopwatch.elapsed(TimeUnit.MILLISECONDS), responseString);
            EntityUtils.consume(entity);
            return JsonUtils.fromJson(responseString, clazz);

        } catch (Exception e) {
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            LOGGER.warn("[HTTP] end get: " + url + ", elapsed: " + elapsed + " failed", e);
            // ConnectTimeoutException: Took too long to connect to remote host
            // SocketTimeoutException: Remote host didn't respond in time
            if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
                throw new TimeoutException("Connect " + url + " timeout in " + elapsed + " ms.", e);
            }
        }
        return null;
    }

    
    public <T> T doDelete(String url, Class<T> clazz, Map<String, String> headers, NameValuePair... nvps) {
        try {
            String responseString = doDelete(url, headers ,nvps);
            return JsonUtils.fromJson(responseString, clazz);
        } catch (Exception e) {
            LOGGER.warn("[HTTP] json parse failed. get: " + url, e);
        }
        return null;
    }
    
    private String doDelete(String url, Map<String, String> headers, NameValuePair... nvps) {
        
        URI uri;
        try {
            if(nvps != null){
                uri = new URIBuilder(url).addParameters(Arrays.asList(nvps)).build();
            }else {
                uri = new URIBuilder(url).build();
            }

        } catch (URISyntaxException e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }
        
        HttpDelete httpDelete = new HttpDelete(uri);
        httpDelete.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        if(headers != null) {
            for(Entry<String, String> header: headers.entrySet()) {
                httpDelete.setHeader(header.getKey(), header.getValue());
            }
        }
        LOGGER.info("[HTTP] start delete: {}", httpDelete.getURI());
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            LOGGER.info("[HTTP] end delete: {}, elapsed: {}, response: {}", httpDelete.getURI(), stopwatch.elapsed(TimeUnit.MILLISECONDS), responseString);
            EntityUtils.consume(entity);
            return responseString;
        } catch (Exception e) {
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            LOGGER.warn("[HTTP] end get: " + url + ", elapsed: " + elapsed + " failed", e);
            // ConnectTimeoutException: Took too long to connect to remote host
            // SocketTimeoutException: Remote host didn't respond in time
            if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
                throw new TimeoutException("Connect " + url + " timeout in " + elapsed + " ms.", e);
            }
        }
        return null;
    }


    private String doGet(String url, NameValuePair... nvps) {
        URI builder;
        try {
            builder = new URIBuilder(url).addParameters(Arrays.asList(nvps)).build();
        } catch (URISyntaxException e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }

        HttpGet httpget = new HttpGet(builder);
        httpget.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        LOGGER.info("[HTTP] start get: {}", httpget.getURI());
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (CloseableHttpResponse response = httpClient.execute(httpget)) {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            LOGGER.info("[HTTP] end get: {}, elapsed: {}, response: {}", httpget.getURI(), stopwatch.elapsed(TimeUnit.MILLISECONDS), responseString);
            EntityUtils.consume(entity);
            return responseString;
        } catch (Exception e) {
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            LOGGER.warn("[HTTP] end get: " + url + ", elapsed: " + elapsed + " failed", e);
            // ConnectTimeoutException: Took too long to connect to remote host
            // SocketTimeoutException: Remote host didn't respond in time
            if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
                throw new TimeoutException("Connect " + url + " timeout in " + elapsed + " ms.", e);
            }
        }
        return null;
    }

    public <T> T doFilePost(String url, Class<T> clazz, MultipartFile file, String fileParameter) throws IOException {
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        try {
        	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	   		builder.addBinaryBody(fileParameter, file.getInputStream());
	   		HttpEntity entity = builder.build();
            httppost.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }

        return doExecute(httppost, clazz);
    }
    
    public <T> T doPost(String url, Class<T> clazz, NameValuePair... nvps) {
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        try {
            httppost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(nvps), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }

        return doExecute(httppost, clazz);
    }

    public <T> T doPost(String url, Class<T> clazz, Object requestBody) {
        return doPost(url, clazz, requestBody, null);
    }
    
    public <T> T doPost(String url, Class<T> clazz, Object requestBody, Map<String, String> headers) {
        HttpPost httppost = new HttpPost(url);
        
        httppost.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        httppost.setHeader("Content-Type", MediaType.APPLICATION_JSON);
        if(headers != null) {
        	for(Entry<String, String> header: headers.entrySet()) {
            	httppost.setHeader(header.getKey(), header.getValue());
            }
        }
        
        try {
            if (requestBody != null) {
            	String requestBodyStr = JsonUtils.toJson(requestBody);
            	LOGGER.info("post url {} request body is: {}", url, requestBodyStr);
                httppost.setEntity(new StringEntity(Objects.requireNonNull(requestBodyStr), "UTF-8"));
            }
        }  catch (UnsupportedCharsetException e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }

        return doExecute(httppost, clazz);
    }

    public <T> T doPostKeyValue(String url, Class<T> clazz, Map<String,String> parameters, Map<String, String> headers) {
        HttpPost httppost = new HttpPost(url);

        httppost.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        httppost.setHeader("Content-Type", MediaType.APPLICATION_JSON);
        if(headers != null) {
            for(Entry<String, String> header: headers.entrySet()) {
                httppost.setHeader(header.getKey(), header.getValue());
            }
        }
        try {
            String params = "";
            StringBuilder sb = new StringBuilder();
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            httppost.setEntity(new StringEntity(params, CONTENT_TYPE_DEFAULT, "UTF-8"));
        }  catch (Exception e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }

        return doExecute(httppost, clazz);
    }
    
    public <T> T doPut(String url, Class<T> clazz, Object requestBody, Map<String, String> headers) {
        HttpPut httpput = new HttpPut(url);
        httpput.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        httpput.setHeader("Content-Type", MediaType.APPLICATION_JSON);
        if(headers != null) {
            for(Entry<String, String> header: headers.entrySet()) {
                httpput.setHeader(header.getKey(), header.getValue());
            }
        }
        try {
            if (requestBody != null) {
                httpput.setEntity(new StringEntity(Objects.requireNonNull(JsonUtils.toJson(requestBody)), "UTF-8"));
            }
        } catch (UnsupportedCharsetException e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }

        return doExecute(httpput, clazz);
    }

    public <T> T doPutKeyValue(String url, Class<T> clazz, Map<String,String> parameters, Map<String, String> headers) {
        HttpPut httpput = new HttpPut(url);
        httpput.setHeader("Accept-Charset", StandardCharsets.UTF_8.name());
        httpput.setHeader("Content-Type", MediaType.APPLICATION_JSON);
        if(headers != null) {
            for(Entry<String, String> header: headers.entrySet()) {
                httpput.setHeader(header.getKey(), header.getValue());
            }
        }
        try {
            String params = "";
            StringBuilder sb = new StringBuilder();
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            httpput.setEntity(new StringEntity(params, CONTENT_TYPE_DEFAULT, "UTF-8"));
        } catch (Exception e) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "Connect failed: " + url, e);
        }

        return doExecute(httpput, clazz);
    }

    
    /**
     * HTTP main process
     */
    private <T> T doExecute(HttpEntityEnclosingRequestBase httpEntity, Class<T> clazz) {
        LOGGER.info("[HTTP] start {}: {}", httpEntity.getMethod(), httpEntity.getURI());
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (CloseableHttpResponse response = httpClient.execute(httpEntity)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            LOGGER.info("[HTTP] end: {}, elapsed: {}, response: {}", httpEntity.getURI(), stopwatch.elapsed(TimeUnit.MILLISECONDS), content);
            EntityUtils.consume(entity);
            return JsonUtils.fromJson(content, clazz);
        } catch (Exception e) {
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            LOGGER.warn("[HTTP] end: " + httpEntity.getURI() + ", elapsed: " + elapsed + " failed", e);
            // ConnectTimeoutException: Took too long to connect to remote host
            // SocketTimeoutException: Remote host didn't respond in time
            if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
                throw new TimeoutException("Connect " + httpEntity.getURI() + " timeout in " + elapsed + " ms.", e);
            }
        }
        return null;
    }
}
