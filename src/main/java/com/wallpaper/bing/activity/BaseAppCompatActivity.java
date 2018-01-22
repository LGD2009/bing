package com.wallpaper.bing.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.wallpaper.bing.R;
import com.wallpaper.bing.presenter.IBasePresenter;
import com.wallpaper.bing.util.SystemBarTintManager;

/**
 * author Gao
 * date 2017/9/13 0013
 * description
 */

public abstract class BaseAppCompatActivity<T extends IBasePresenter> extends AppCompatActivity {
    protected SystemBarTintManager tintManager;

    protected ProgressDialog progressDialog;

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        if (statusBarTintColor()==0) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);//获得当前主题的属性
            tintManager.setStatusBarTintColor(typedValue.data);
        }else {
            tintManager.setStatusBarTintColor(statusBarTintColor());
        }


        presenter=createPresenter();

    }

    protected abstract T createPresenter();

    protected int statusBarTintColor(){
        return 0;
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void showProgressDialog(String title ,String message){
        progressDialog = ProgressDialog.show(this,title,message);
    }

    protected void showProgressDialog(){
        showProgressDialog("","请稍候...");
    }

    protected void dismissProgressDialog(){
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter!=null){
            presenter.unSubscribe();
        }
        super.onDestroy();
    }
}
