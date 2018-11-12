package com.example.huyhoang8398.emailclient.interfaces;

import com.google.api.services.gmail.model.Message;

public class MessageSingleton {
    // static variable single_instance of type Singleton
    private static MessageSingleton single_instance = null;

    // variable of type String
    public Message message;

    // private constructor restricted to this class itself
    private MessageSingleton() {

    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    // static method to create instance of Singleton class
    public static MessageSingleton getInstance() {
        if (single_instance == null)
            single_instance = new MessageSingleton();
        return single_instance;
    }
}