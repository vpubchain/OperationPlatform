package com.jiuling.commonbusiness.util;


import android.util.Base64;

import java.io.UnsupportedEncodingException;


/**
 * Created by HP on 2018/5/3.
 */

public class Base64Utils {

    //Base64编码

    public static String encoderBase64(String data){
        try {
            return Base64.encodeToString(data.getBytes("utf-8"),1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Base64解码

    public static String decoderBase64(String data){
        byte[] asBytes = Base64.decode(data,1);

        try {
            return new String(asBytes,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }



}
