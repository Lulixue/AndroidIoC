package com.dongnao.ioclib.utils;

import android.app.Activity;
import android.view.View;

import com.dongnao.ioclib.BaseActivity;
import com.dongnao.ioclib.annotations.BindView;
import com.dongnao.ioclib.annotations.ContentView;
import com.dongnao.ioclib.annotations.EventBase;
import com.dongnao.ioclib.annotations.OnClick;
import com.dongnao.ioclib.event.EventsInvocationhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class InjectUtil {


    public static void inject(Activity activity) {
        injectLayout(activity);
        injectView(activity);
        injectEvents(activity);
    }


    /**
     * 注入事件
     * @param activity
     */
    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] mtds = clazz.getDeclaredMethods();
        for (Method mtd : mtds) {
            OnClick onClick = mtd.getAnnotation(OnClick.class);
            if (onClick == null) {
                continue;
            }
            int[] ids = onClick.value();
            Class<? extends Annotation> annotationType = onClick.annotationType();
            EventBase eventBase = annotationType.getAnnotation(EventBase.class);
            if (eventBase == null) {
                continue;
            }
            String listenerSetter = eventBase.listenerSetter();
            Class<?> listenerType = eventBase.listenerType();
            String callBackMethod = eventBase.callbackMethod();

            Map<String, Method> methodMap = new HashMap<>();
            methodMap.put(callBackMethod, mtd);
            EventsInvocationhandler invocationhandler
                    = new EventsInvocationhandler(activity, methodMap);


            for (int id : ids) {
                View viewById = activity.findViewById(id);
                try {
                    Method method =
                            viewById.getClass().getMethod(listenerSetter, listenerType);
                    Object proxyInstance = Proxy.newProxyInstance(listenerType.getClassLoader()
                            , new Class[]{listenerType}, invocationhandler);

                    method.invoke(viewById, proxyInstance);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    /**
     * 创建view，并注入到对应activity
     * @param activity
     */
    private static void injectView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            BindView bindView = declaredField.getAnnotation(BindView.class);
            if (bindView == null) {
                continue;
            }
            int id = bindView.value();
            View viewById = activity.findViewById(id);
            //当注入成员是私有的，需要先设置setAccessible
            declaredField.setAccessible(true);
            try {
                declaredField.set(activity, viewById);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }


    }


    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView con = clazz.getAnnotation(ContentView.class);
        if (con == null) {
            return;
        }
        int layoutId = con.value();
        activity.setContentView(layoutId);
    }
}
