package com.aeolou.digital.media.android.tmediapicke.manager;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class TMediaInvocationHandler implements InvocationHandler {
    private TMediaPicker tMediaPicker;


    private TMediaInvocationHandler(TMediaPicker tMediaPicker) {
        this.tMediaPicker = tMediaPicker;
    }

    public static Object bind(TMediaPicker tMediaPicker) {
        return Proxy.newProxyInstance(tMediaPicker.getClass().getClassLoader(), tMediaPicker.getClass().getInterfaces(), new TMediaInvocationHandler(tMediaPicker));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(tMediaPicker, args);
    }
}