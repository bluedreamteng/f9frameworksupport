package com.tengfei.f9framework.common;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestInfo {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方法
     */
    private String methodName;

    /**
     * 请求头
     */
    private Map<String,String> headMap = new HashMap<>();

    /**
     * 请求体
     */
    private String requestBody;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, String> getHeadMap() {
        return headMap;
    }

    public void addRequestHead(String headFiled,String headValue) {
        headMap.put(headFiled,headValue);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
