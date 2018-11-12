package com.example.huyhoang8398.emailclient.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * Created by nghia on 6/30/2017.
 */

public abstract class BaseAPI {
    private APIListener listener;
    private Context context;

    public BaseAPI(Context context) {
        this.context = context;
    }

    public void requestGet(String urlRequest) {
        listener.onPreExecute();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            listener.onRequestSuccess(parseObject(jsonObject));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onRequestFailure(error.getMessage(), 123);
            }
        });

        ConnectionManagement.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void requestPost(String urlRequest, Object jsonBody) {
        listener.onPreExecute();
        final String mRequestBody = jsonBody.toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    listener.onRequestSuccess(parseObject(object));
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onRequestFailure("", 12);
                }
            }
        };

        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onRequestFailure(error.getMessage(), 12);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRequest, responseListener, responseError) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String jsonString = null;
                try {
                    jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));

            }
        };

        int socketTimeout = 5000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        ConnectionManagement.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void setAPIListener(APIListener listener) {
        this.listener = listener;
    }

    public abstract Object parseObject(JSONObject jsonObject);

    public abstract void execute(Object... data);


}
