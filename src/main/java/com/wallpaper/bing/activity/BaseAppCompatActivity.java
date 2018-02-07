package com.wallpaper.bing.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.wallpaper.bing.R;
import com.wallpaper.bing.fragment.GeneralPreferenceFragment;
import com.wallpaper.bing.presenter.IBaseView;
import com.wallpaper.bing.presenter.impl.BasePresenterImpl;
import com.wallpaper.bing.util.SystemBarTintManager;

/**
 * author Gao
 * date 2017/9/13 0013
 * description
 */

public abstract class BaseAppCompatActivity<P extends BasePresenterImpl,T> extends AppCompatActivity implements IBaseView<T>{

    protected SystemBarTintManager tintManager;

    protected ProgressDialog progressDialog;

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //设置状态栏颜色
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        if (statusBarTintColor() == 0) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);//获得当前主题的属性
            tintManager.setStatusBarTintColor(typedValue.data);
        } else {
            tintManager.setStatusBarTintColor(statusBarTintColor());
        }

        //设置夜间模式
        boolean isNight = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(GeneralPreferenceFragment.NIGHT_SWITCH, false);
        getDelegate().setLocalNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        //初始化presenter
        presenter = createPresenter();
        if (presenter != null) {
            //切换夜间模式
            presenter.setOnChangeNightListener(savedInstanceState);
        }

    }

    protected abstract P createPresenter();

    /**
     * statusBar 颜色
     * @return 0-默认主题色，或者返回其他int类型的颜色
     */
    protected @ColorInt int statusBarTintColor() {
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

    protected void showProgressDialog(String title, String message) {
        progressDialog = ProgressDialog.show(this, title, message);
    }

    protected void showProgressDialog() {
        showProgressDialog("", "请稍候...");
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public AppCompatActivity getAppCompatContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.unSubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void onSuccess(T bean) {

    }

    @Override
    public void onFailed(String s) {

    }

}
