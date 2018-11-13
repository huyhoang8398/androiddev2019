package com.example.huyhoang8398.emailclient.interfaces;


/**
 * Created by nghia on 6/29/2017.
 */

public interface APIListener {
    void onPreExecute();
    void onRequestSuccess(Object object);
    void onRequestFailure(String message, int errorCode);
}
