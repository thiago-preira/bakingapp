package com.udacity.android.bakingapp.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.udacity.android.bakingapp.R;

public class BaseActivity extends AppCompatActivity {

    private Toast mToast;

    protected void toast(String text){
        if(mToast!=null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this,text,Toast.LENGTH_LONG);
        mToast.show();
    }

    protected Context getContext(){
        return this;
    }

    protected boolean isLandscapeMode(){
        return getResources().getBoolean(R.bool.landscape);
    }
}
