package com.dongnao.ioclib.event;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class EventsInvocationhandler implements InvocationHandler {

    private Activity activity;
    private Map<String, Method> methodMap;

    public EventsInvocationhandler(Activity activity, Map<String, Method> methodMap) {
        this.activity = activity;
        this.methodMap = methodMap;

    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method mtd = methodMap.get(method.getName());
        if (mtd == null) {
            return null;
        }
        return mtd.invoke(activity, args);
    }
}
