package com.wallpaper.bing.presenter.impl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.wallpaper.bing.activity.MainActivity;
import com.wallpaper.bing.network.RetrofitHelper;
import com.wallpaper.bing.network.bean.BaseBean;
import com.wallpaper.bing.network.bean.WallpaperInfoBean;
import com.wallpaper.bing.presenter.contract.IMainContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author Gao
 * date 2017/12/11
 * description
 */

public class MainPresenterImpl implements IMainContract.IMainPresenter {

    private CompositeDisposable disposable;
    private IMainContract.IMainView mainView;

    public MainPresenterImpl(IMainContract.IMainView mainView) {
        this.mainView = mainView;
        disposable = new CompositeDisposable();
    }

    @Override
    public void getWallpaper(String date) {
        disposable.add(RetrofitHelper.getBingApi().queryWallpaperInfo(date)
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
        disposable.add(RetrofitHelper.getBingApi().getWallpaper(imageUrl)
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
                .insertImage(mainView.getContext().getContentResolver(), bitmap, "设置壁纸", "a"));
        intent.setData(uri);
        (mainView.getContext()).startActivity(intent);
    }

    @Override
    public void downloadWallpaper(InputStream inputStream, String picName) {
        String savePathDir = Environment.getExternalStorageDirectory() + "/Bing壁纸/";
        File dirFile = new File(savePathDir);
        if (!dirFile.exists()) {
            boolean create = dirFile.mkdirs();
            if (!create) {
                Toast.makeText(mainView.getContext(), "创建文件夹失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        File picFile = new File(savePathDir + "" + picName);
        try {
            boolean newFile = picFile.createNewFile();
            if (!newFile) {
                Toast.makeText(mainView.getContext(), "创建图片失败", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(mainView.getContext(), "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mainView.getContext(), "保存失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void unSubscribe() {
        disposable.clear();
    }
}
