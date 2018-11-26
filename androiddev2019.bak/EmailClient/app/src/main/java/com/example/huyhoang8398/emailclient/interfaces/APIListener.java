package com.example.huyhoang8398.emailclient.interfaces;



public interface APIListener {
    void onPreExecute();
    void onRequestSuccess(Object object);
    void onRequestFailure(String message, int errorCode);
}
