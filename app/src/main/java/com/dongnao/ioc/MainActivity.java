package com.dongnao.ioc;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dongnao.ioclib.BaseActivity;
import com.dongnao.ioclib.annotations.BindView;
import com.dongnao.ioclib.annotations.ContentView;
import com.dongnao.ioclib.annotations.OnClick;

import androidx.annotation.Nullable;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final String TAG = "david";

    @BindView(R.id.tv_text)
    private TextView mTvText;
    @BindView(R.id.btn_study)
    private Button mBtnStudy;
    @BindView(R.id.btn_study_two)
    private Button mBtnStudyTwo;



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, mTvText.getText().toString());
    }

    @OnClick({R.id.btn_study, R.id.btn_study_two})
    public void click(View view) {
        Log.e(TAG, ((Button)view).getText().toString());

    }


}
