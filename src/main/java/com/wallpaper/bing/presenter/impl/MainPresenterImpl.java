package com.wallpaper.bing.presenter.impl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.wallpaper.bing.network.RetrofitHelper;
import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.presenter.contract.IMainContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public class MainPresenterImpl extends BasePresenterImpl implements IMainContract.IMainPresenter {

    private IMainContract.IMainView mainView;

    public MainPresenterImpl(IMainContract.IMainView mainView) {
        super(mainView);
        this.mainView = mainView;
    }

    @Override
    public void getWallpaper(String date) {
        compositeDisposable.add(RetrofitHelper.getBingApi().queryWallpaperInfo(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<WallpaperInfoBean>>() {
                    @Override
                    public void accept(@NonNull BaseBean<WallpaperInfoBean> infoBean) throws Exception {
                        mainView.onSuccess(infoBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mainView.onFailed(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void getWallpaper(String imageUrl, final int option) {
        mainView.showDialog();
        compositeDisposable.add(RetrofitHelper.getBingApi().getWallpaper(imageUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        mainView.dismissDialog();
                        mainView.onSuccess(responseBody, option);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mainView.dismissDialog();
                        mainView.onFailed(throwable.getMessage());
                    }
                })
        );
    }

    @Override
    public void setDesktopWallpaper(Bitmap bitmap) {
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("mimeType", "image*//*");
        Uri uri = Uri.parse(MediaStore.Images.Media
                .insertImage(mainView.getAppCompatContext().getContentResolver(), bitmap, "设置壁纸", "a"));
        intent.setData(uri);
        PackageManager pm = mainView.getAppCompatContext().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if (activities.size() > 0) {
            mainView.getAppCompatContext().startActivity(intent);
        }
    }

    @Override
    public void downloadWallpaper(InputStream inputStream, String picName) {
        String savePathDir = Environment.getExternalStorageDirectory() + "/Bing壁纸/";
        File dirFile = new File(savePathDir);
        if (!dirFile.exists()) {
            boolean create = dirFile.mkdirs();
            if (!create) {
                Toast.makeText(mainView.getAppCompatContext(), "创建文件夹失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        File picFile = new File(savePathDir + "" + picName);
        try {
            boolean newFile = picFile.createNewFile();
            if (!newFile) {
                Toast.makeText(mainView.getAppCompatContext(), "创建图片失败", Toast.LENGTH_SHORT).show();
                return;
            }
            FileOutputStream out = new FileOutputStream(picFile);
            byte[] data = new byte[1024];
            int len;
            while ((len = inputStream.read(data)) != -1) {
                out.write(data, 0, len);
            }
            out.flush();
            out.close();
            Toast.makeText(mainView.getAppCompatContext(), "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mainView.getAppCompatContext(), "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

}
