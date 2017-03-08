package com.lotte.android_notepad.support;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lotte.android_notepad.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by wubin on 2017/3/8.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(new File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
//                .resize(width, height)//
//                .centerInside()//
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}
