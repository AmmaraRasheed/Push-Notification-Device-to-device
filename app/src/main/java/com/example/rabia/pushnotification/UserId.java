package com.example.rabia.pushnotification;

import android.support.annotation.NonNull;

public class UserId {
    public String user_id;
    public <T extends UserId> T withId(@NonNull final String id){
        this.user_id=id;
        return(T) this;
    }
}
