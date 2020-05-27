package com.dongnao.ioclib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    //设置事件监听的方法名称
    String listenerSetter();

    //监听传入类型
    Class<?> listenerType();

    //实际回调的方法
    String callbackMethod();
}
