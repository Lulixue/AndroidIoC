package com.dongnao.ioc;

import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

import androidx.annotation.NonNull;

public class Proxy0 extends Proxy implements View.OnClickListener {


    //方法静态域
    private static Method m1;   //代理的方法对象 onclick


    /**
     * 构造器传递InvocationHandler
     */
    protected Proxy0(@NonNull InvocationHandler h) {
        super(h);
    }

    /**
     * 实际代理的地方
     * @param v
     */
    @Override
    public void onClick(View v) {
        Object[] args = new Object[] {v};
        try {
            //最终还是找到InvocationHandler来做处理
            h.invoke(this, m1, args);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }


    //初始化
    static {
        try {
            Class c1 = Class.forName(Object.class.getName());
            m1 = c1.getMethod("onClick", new Class[]{View.class});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
