package com.kakarot.skeleton.common.context;

import com.kakarot.skeleton.common.model.LoginUser;

public class UserContext {

    private static final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    private UserContext(){

    }

    public static void set(LoginUser loginUser){
        THREAD_LOCAL.set(loginUser);
    }

    public static LoginUser get(){
        return THREAD_LOCAL.get();
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }

}
