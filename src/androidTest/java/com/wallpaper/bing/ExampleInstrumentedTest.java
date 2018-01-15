package com.wallpaper.bing;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;
import android.view.Display;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.wallpaper.bing", appContext.getPackageName());
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) appContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels;// 得到宽度
        float height = dm.heightPixels;// 得到高度


    }
}
