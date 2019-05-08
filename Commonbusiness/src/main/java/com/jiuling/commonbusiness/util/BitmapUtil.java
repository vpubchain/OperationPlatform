package com.jiuling.commonbusiness.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.jiuling.commonbusiness.AppConstants;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by Rayman on 2016/11/16.
 */
public class BitmapUtil {

    private static String TAG = "BitmapUtil";

  /*stream = new FileOutputStream("/sdcard/" + filename);
    获取要保存到的文件的文件流
    bmp.compress(format, quality, stream);
    把指定的bitmp压缩到文件中 就是保存在指定文件中 format是文件格式（Bitmap.CompressFormat.JPEG jpeg） quality 是品质（100 就是原质量）
    看名字 saveBitmap2file
    你要上传的话 就去指定位置取这个file就行 路径的问题 可能有写真机找不到/sdcard/
    建议 Environment类取地址 保存和读取时 都用Environment.getXXXX*/

    public static boolean saveBitmap2file(Bitmap bmp, String filename) {
        boolean isSuccess=false;
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        String basePath = AppConstants.EXTERNALDIRECTORY + "pic";
        File base = new File(basePath);
        if (!base.exists()) {
            base.mkdirs();
        }
        File file = new File(basePath + File.separator + filename);
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(file.getAbsolutePath());
            isSuccess=bmp.compress(format, quality, stream);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isSuccess;

    }


    public static void test(Bitmap btImage){
        File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
        FileOutputStream out=null;
        try {
            out = new FileOutputStream(file);
            btImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static String saveRoundBitmap(String url) {
        String path = "";
        try {
            Bitmap bitmap = getImage(url);
            Bitmap round = toRoundBitmap(bitmap);
            if (saveBitmap2file(round, "user_head_round")) {
                path = AppConstants.EXTERNALDIRECTORY + "user_head_round";
            } else {
                Log.i("yeayea", "存储图片失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String saveRoundBitmap(Bitmap bmp) {
        String path = null;
        try {
            Bitmap round = toRoundBitmap(bmp);
            if (saveBitmap2file(round, "user_head_round")) {
                path = AppConstants.PACKAGE + "user_head_round";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String saveRoundRectangleBitmap(String url, int pixel) {
        String path = null;
        try {
            Bitmap bitmap = getImage(url);
            Bitmap round = toRoundCorner(bitmap, pixel);
            if (saveBitmap2file(round, "user_head_rectangle")) {
                path = AppConstants.EXTERNALDIRECTORY + "user_head_rectangle";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 直接调用BitmapFactory的方法，将原文件加载到Bitmap中
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromFile(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     * @return
     */
    public static Bitmap readBitmapFromFile(String filePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     * @return
     */
    public static Bitmap readBitmapFromFileDescriptor(String filePath, int width, int height) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
            float srcWidth = options.outWidth;
            float srcHeight = options.outHeight;
            int inSampleSize = 1;

            if (srcHeight > height || srcWidth > width) {
                if (srcWidth > srcHeight) {
                    inSampleSize = Math.round(srcHeight / height);
                } else {
                    inSampleSize = Math.round(srcWidth / width);
                }
            }

            options.inJustDecodeBounds = false;
            options.inSampleSize = inSampleSize;
            return BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
        } catch (Exception ex) {
        }
        return null;
    }

    public static byte[] getByteFromBitmap(Bitmap bitmap) {
        byte[] buffer = new byte[]{};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
            buffer = out.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encode(buffer, Base64.DEFAULT);
    }

    public static Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap
     * @param :指的是圆的半径，取宽和高之间的最小值，除2。圆就越小，你可以设置圆的半径小于除2后的数字 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;

            left = 0;
            top = 0;
            right = width;
            bottom = width;

            height = width;

            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;

            float clip = (width - height) / 2;

            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;

            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);

        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

        // 以下有两种方法画圆,drawRounRect和drawCircle
//		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    /**
     * 将图片变为圆角
     *
     * @param bitmap 原Bitmap图片
     * @param pixels 图片圆角的弧度(单位:像素(px))
     * @return 带有圆角的图片(Bitmap 类型)
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);           //压缩成JPEG
        int options = 50;
        while (baos.toByteArray().length / 1024 > 100) {                //假若图片还是偏大，则用webp来压缩
            baos.reset();
            image.compress(Bitmap.CompressFormat.WEBP, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     * 将对应的File文件通过压缩之后加载到Bitmap上面
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;

        int originalWidth = newOpts.outWidth;           //原图宽度
        int originalHeight = newOpts.outHeight;         //原图高度
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float standardHeight = 800f;// 这里设置高度为800f
        float standardWidth = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int zoomRadio = 1;// 压缩比例，默认为1
        if (originalWidth > originalHeight && originalWidth > standardWidth) {      //图片的宽度比高度大，而且比屏幕的宽度要大，则用宽度来压缩
            zoomRadio = (int) (originalWidth / standardWidth);
        } else if (originalHeight > originalWidth && originalHeight > standardHeight) {      //图片的高度比宽度大，而且比屏幕的高度要大，则用高度来压缩
            zoomRadio = (int) (originalHeight / standardHeight);
        }
        if (zoomRadio <= 0) {
            zoomRadio = 1;
        }
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = zoomRadio;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 将压缩的bitmap保存到SDCard卡临时文件夹，用于上传
     *
     * @param filename
     * @param bit
     * @return
     */
    public static String saveCompressBitmap(String filename, Bitmap bit) {
        String basePath = AppConstants.PACKAGE_PATH + "pic";
        File base = new File(basePath);
        if (!base.exists()) {
            base.mkdirs();
            Logger.i(TAG, "pic目录不存在");
        }
        File f = new File(basePath + File.separator + filename);
        try {
            Logger.i(TAG, "创建文件：" + basePath + File.separator + filename);
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            if (bit.compress(Bitmap.CompressFormat.JPEG, 50, fOut)) {
                fOut.flush();
                fOut.close();
            }
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    /**
     * 清除缓存文件
     */
    public static void deleteCacheFile() {
        File file = new File(AppConstants.PACKAGE + "pic/");
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    /**
     * 递归删除
     */
    private static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}
