package com.udacity.android.bakingapp.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.udacity.android.bakingapp.R;

public class BaseFragment extends Fragment {
    private Toast mToast;

    public Context getContext() {
        return getActivity();
    }


    protected void toast(String text){
        if(mToast!=null){
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(),text,Toast.LENGTH_LONG);
        mToast.show();
    }

    protected boolean isLandscapeMode(){
        return getResources().getBoolean(R.bool.landscape);
    }
}
