package com.jiuling.commonbusiness.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * desc:
 *
 * @author Rayman
 * @date 2017/10/30
 */

public class PaletteUtil {

    private static final String TAG = "PaletteUtil";

    public static Observable createPaletteColor(final BitmapDrawable drawable) {
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                Bitmap bitmap = drawable.getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int color = palette.getDarkMutedColor(Color.TRANSPARENT);
                        Log.e(TAG, "palette color:" + color);
                        e.onNext(color);
                    }
                });

            }
        });
        observable.observeOn(Schedulers.newThread());
        observable.subscribeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    public static Observable createPaletteColor(final Context context, final int resId, final int defaultColor) {
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int color = palette.getDarkMutedColor(defaultColor);
                        Log.e(TAG, "palette color:" + color);
                        e.onNext(color);
                    }
                });

            }
        });
        observable.observeOn(Schedulers.newThread());
        observable.subscribeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
