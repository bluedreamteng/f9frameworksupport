package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ws.http.request.HttpRequestVariableSubstitutor;
import com.intellij.ws.http.request.psi.HttpHeaderField;
import com.intellij.ws.http.request.psi.HttpRequest;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAction extends AnAction {
    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement data = e.getData(CommonDataKeys.PSI_ELEMENT);
        HttpRequest httpRequest = PsiTreeUtil.getParentOfType(data, HttpRequest.class);
        if (httpRequest == null) {
            throw new RuntimeException("Request not received, please try again");
        }
        HttpRequestInfo requestInfo = new HttpRequestInfo();
        System.out.println("请求方法：" + httpRequest.getHttpMethod());
        requestInfo.setMethodName(httpRequest.getHttpMethod());
        Project project = e.getProject();
        assert project != null;
        System.out.println("请求url：" + httpRequest.getHttpUrl(HttpRequestVariableSubstitutor.getDefault(project)));
        requestInfo.setUrl(httpRequest.getHttpUrl(HttpRequestVariableSubstitutor.getDefault(project)));
        List<HttpHeaderField> headerFieldList = httpRequest.getHeaderFieldList();
        for (HttpHeaderField headerField : headerFieldList) {
            String headKey = headerField.getHeaderFieldName().getText();
            String headValue = null;
            if (headerField.getHeaderFieldValue() != null) {
                headValue = headerField.getHeaderFieldValue().getText();
            }
            requestInfo.addRequestHead(headKey, headValue);
        }
        if (httpRequest.getRequestBody() != null) {
            requestInfo.setRequestBody(httpRequest.getRequestBody().getText());
        }
        String requestMethod = generateRequestMethod(requestInfo);

        CopyPasteManager.getInstance().setContents(new StringSelection(requestMethod));

    }


    private String generateRequestMethod(HttpRequestInfo requestInfo) {
        if (!HTTP_POST.equals(requestInfo.getMethodName()) && !HTTP_GET.equals(requestInfo.getMethodName())) {
            throw new RuntimeException("unsupported request type");
        }
        StringBuilder result = new StringBuilder();
        result.append("\n");
        //拼接head
        appendRequestHead(requestInfo, result);
        if (isEmpty(requestInfo.getRequestBody())) {
            appendNoRequestBodyResult(requestInfo, result);
        } else if ("application/json".equals(requestInfo.getHeadMap().get("Content-Type"))) {
            appendJsonRequestBodyResult(requestInfo, result);
        } else {
            appendOtherRequestBodyResult(requestInfo, result);
        }
        return result.toString();
    }

    private void appendOtherRequestBodyResult(HttpRequestInfo requestInfo, StringBuilder result) {
        Map<String, String> paramMap = parseRequestParam(requestInfo.getRequestBody());
        if (paramMap.isEmpty()) {
            result.append("String resp = HttpUtil.doHttp(\"" + requestInfo.getUrl() + "\", headerMap, null, HttpUtil.POST_METHOD, HttpUtil.RTN_TYPE_STRING);");
        } else {
            result.append("Map<String,String> paramMap = new HashMap<>();");
            for (Map.Entry<String, String> params : paramMap.entrySet()) {
                result.append("paramMap.put(\"" + params.getKey() + "\",\"" + params.getValue() + "\");");
                result.append("\n");
            }
            result.append("String resp = HttpUtil.doHttp(\"" + requestInfo.getUrl() + "\", headerMap, paramMap, HttpUtil.POST_METHOD, HttpUtil.RTN_TYPE_STRING);");
        }

    }

    private void appendJsonRequestBodyResult(HttpRequestInfo requestInfo, StringBuilder result) {
        JsonObject jsonObject = new JsonParser().parse(requestInfo.getRequestBody()).getAsJsonObject();
        result.append("JSONObject params = new JSONObject();");
        result.append("\n");
        for (Map.Entry<String, JsonElement> item : jsonObject.entrySet()) {
            result.append("params.put(\"" + item.getKey() + "\", " + item.getValue() + ");");
            result.append("\n");
        }
        result.append("String resp = HttpUtil.doHttp(\"" + requestInfo.getUrl() + "\", headerMap, JSON.toJSONString(params), HttpUtil.POST_METHOD, HttpUtil.RTN_TYPE_STRING);");
    }

    private void appendNoRequestBodyResult(HttpRequestInfo requestInfo, StringBuilder result) {
        result.append("String resp = HttpUtil.doHttp(\"" + requestInfo.getUrl() + "\", headerMap, null, HttpUtil.POST_METHOD, HttpUtil.RTN_TYPE_STRING);");
        result.append("\n");
    }

    private void appendRequestHead(HttpRequestInfo requestInfo, StringBuilder result) {
        result.append("Map<String,String> headerMap = new HashMap<>();");
        result.append("\n");
        for (Map.Entry<String, String> heads : requestInfo.getHeadMap().entrySet()) {
            result.append("headerMap.put(\"" + heads.getKey() + "\",\"" + heads.getValue() + "\");");
            result.append("\n");
        }
    }


    private Map<String, String> parseRequestParam(String requestParam) {
        Map<String, String> result = new HashMap<>();
        if (isEmpty(requestParam)) {
            return result;
        }
        String[] params = requestParam.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }

    private boolean isEmpty(String requestParam) {
        return requestParam == null || "".equals(requestParam.trim());
    }
}
