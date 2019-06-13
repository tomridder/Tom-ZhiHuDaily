package com.bignerdranch.myrxmeizi.bean;

import java.util.Date;

public class MessageEvent
{
    private Date message;

    public  MessageEvent(Date message){
        this.message=message;
    }


    public Date getMessage() {
        return message;
    }

    public void setMessage(Date message) {
        this.message = message;
    }
}
