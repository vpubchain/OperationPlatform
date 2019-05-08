package com.jiuling.commonbusiness.util;

/**
 * Created by HP on 2018/5/1.
 */

public class StringUtils {

    public static String deleteZero(String value){

        int jie=0;

        for (int i=0;i<value.length();i++){
            if (value.charAt(value.length()-1-i)=='0'){
                jie++;
            }else{
                break;
            }
        }

        return value.substring(0,value.length()-jie);

    }
}
