package com.huangxj.common.core.security;

/**
 * @ClassName UserContext
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-03 20:38
 * @Version V1.0
 **/
public class LoginTypeContext {

    private static final ThreadLocal<String> context = new ThreadLocal<>();


    public static void set(String LoginTyp) {
        context.set(LoginTyp);
    }

    public static String get() {
        return context.get();
    }

    public static void remove() {
        context.remove();
    }
}
