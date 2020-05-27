package com.dongnao.ioclib;

import android.app.Activity;
import android.os.Bundle;

import com.dongnao.ioclib.utils.InjectUtil;

import androidx.annotation.Nullable;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtil.inject(this);
    }
}
