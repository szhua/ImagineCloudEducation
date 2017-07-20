package com.runer.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.runer.net.interf.INetResult;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Exchanger;
import java.util.logging.Logger;

import cz.msebera.android.httpclient.Header;

/**
 * 公用的网络请求辅助类
 * 网路请求的第一层过滤（统一处理错误结果）
 * Create by szhua 2016/3/14
 */
public abstract class IDao {
    public final static String TAG = "httplog";
    protected INetResult mResult;
    protected Context mContext;

    public IDao(Context context, INetResult iNetResult) {
        mContext = context;
        mResult = iNetResult;
    }

    /**
     * 得到结果后，对结果处理逻辑
     *
     * @param result      网络请求返回的结果
     * @param requestCode 区别请求号码
     * @throws IOException
     */
    public abstract void onRequestSuccess(JsonNode result, int requestCode) throws IOException;




    /**
     * get请求网络，本方法提供结果的分发
     *
     * @param params
     */
    protected void getRequest(String url, Map<String, String> params) {
        _getRequest(url, params, 0);
    }

    /**
     * get请求网络，带有请求编号
     *
     * @param params
     */
    protected void getRequestForCode(String url, Map<String, String> params, int requestCode) {
        _getRequest(url, params, requestCode);
    }

    private void _getRequest(String url, Map<String, String> params, final int requestCode) {

        RequestParams ajaxParams = new RequestParams(params);
        Log.d(TAG, AsyncHttpClient.getUrlWithQueryString(true, url, ajaxParams));

        Http.getInstance().get(mContext, url, ajaxParams, new TextHttpResponseHandler() {

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.d(TAG, responseBody);
                String ss=responseBody;
                String errorInfo = "";
                int error_code = 0;
                try {
                    JsonNode node = ResultUtil.handleResult(responseBody);
                    boolean responseCode = node.findValue("success").asBoolean();
                    //  String responseCode = node.findValue("succeed").asText();
                    if (node.findValue("msg") != null) {
                        errorInfo = node.findValue("msg").asText();
                    }

                    if (node.findValue("error_code") != null) {
                        error_code = node.findValue("error_code").asInt();
                    }
                    if (responseCode) {
                        onRequestSuccess(node, requestCode);
                        mResult.onRequestSuccess(requestCode);
                    } else {
                        mResult.onRequestError(requestCode, errorInfo, error_code);
                    }
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Log.d(TAG, "statusCode:" + statusCode + " Body:" + responseBody);
                if (statusCode == 0)
                    mResult.onNoConnect();
                else
                    mResult.onRequestFaild(requestCode,"" + statusCode, responseBody);
            }
        });

    }

    public void originalGetRequest(String url ,RequestParams params ,final int requestCode){

        Log.i(TAG, "POST: " + AsyncHttpClient.getUrlWithQueryString(true, url, params));

        Http.getInstance().get(mContext, url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.i(TAG, responseBody);

                try {
                    JsonNode node = ResultUtil.handleResult(responseBody);
                    int  code = 0;
                    String  message ="" ;
                    if (node.findValue("code")!=null){
                        code =node.findValue("code").asInt();
                    }
                    if(node.findValue("message")!=null){
                        message =node.findValue("message").asText() ;
                    }
                    if(code==RequestCode.CODE_SUCCESS){
                        onRequestSuccess(node,requestCode);
                        mResult.onRequestSuccess(requestCode);
                    }else{
                        if(!TextUtils.isEmpty(message))
                            mResult.onRequestError(requestCode,message,code);
                        else
                            mResult.onRequestError(requestCode,"error",code);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    mResult.onRequestFaild(requestCode,"error",e.toString());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Log.d(TAG, "statusCode:" + statusCode + " Body:" + responseBody);
                if (statusCode == 0){
                    mResult.onNoConnect();}
                else{
                    mResult.onRequestFaild(requestCode,"" + statusCode, responseBody);
                }
            }
        });


    }


    public void originalPostRequest(String url ,RequestParams params ,final int requestCode){

        Log.i(TAG, "POST: " + AsyncHttpClient.getUrlWithQueryString(true, url, params));
        Http.getInstance().post(mContext, url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.i(TAG, responseBody);
                try {
                    JsonNode node = ResultUtil.handleResult(responseBody);
                     int  code = 0;
                     String  message ="" ;
                    if (node.findValue("error")!=null){
                       code =node.findValue("error").asInt();
                    }
                    if(node.findValue("msg")!=null){
                       message =node.findValue("msg").asText() ;
                    }
                    if(code==RequestCode.CODE_SUCCESS){
                        //返回result_____Node
                        onRequestSuccess(node.findValue("result"),requestCode);
                        mResult.onRequestSuccess(requestCode);
                    }else{
                        if(!TextUtils.isEmpty(message))
                        mResult.onRequestError(requestCode,message,code);
                        else
                        mResult.onRequestError(requestCode,"error",code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("szhua",e.toString());
                    mResult.onRequestFaild(requestCode,"error",e.toString());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Log.d(TAG, "statusCode:" + statusCode + " Body:" + responseBody);
                if (statusCode == 0){
                    mResult.onNoConnect();
                }else{
                    mResult.onRequestFaild(requestCode,""+ statusCode, responseBody);
            }
            }
        });


    }


    /**
     * POST 请求 MainInUse===Post
     *
     * @param requestCode
     */
    public void postRequest(String url, RequestParams params, final int requestCode) {

        Log.i(TAG, "POST: " + AsyncHttpClient.getUrlWithQueryString(true, url, params));

        Http.getInstance().post(mContext, url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Log.d(TAG, "statusCode:" + statusCode + " Body:" + responseBody);
                if (statusCode == 0)
                    mResult.onNoConnect();
                else
                    mResult.onRequestFaild(requestCode,"" + statusCode, responseBody);
            }
        });

    }

    /**
     * Post json 请求
     *
     * @param url         接口地址
     * @param jsonParams  请求参数 json字符串
     * @param requestCode 请求编号，区分返回的结果
     */
    public void postRequest(String url, String jsonParams, final int requestCode) {
        Log.d(TAG, "POST: " + url + " JSONParams:" + jsonParams);

        Http.getInstance().post(mContext, url, jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable throwable) {
                Log.d(TAG, "statusCode:" + statusCode + " Body:" + responseBody);

                if (statusCode == 0)
                    mResult.onNoConnect();
                else
                    mResult.onRequestFaild(requestCode,"" + statusCode, responseBody);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.d(TAG, responseBody);
                String errorInfo = "";
                int error_code = 0;
                try {
                    JsonNode node = ResultUtil.handleResult(responseBody);
                    boolean responseCode = node.findValue("success").asBoolean();
                    //  String responseCode = node.findValue("succeed").asText();
                    if (node.findValue("msg") != null) {
                        errorInfo = node.findValue("msg").asText();
                    }
                    if (node.findValue("error_code") != null) {
                        error_code = node.findValue("error_code").asInt();
                    }
                    if (responseCode) {
                        onRequestSuccess(node, requestCode);
                        mResult.onRequestSuccess(requestCode);
                    } else {
                        mResult.onRequestError(requestCode, errorInfo, error_code);
                    }
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}