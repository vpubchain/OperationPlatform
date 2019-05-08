package com.vpubchain.wallet.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Chain-Cloud on 2018/5/7.
 */

public class StringFormat {

    //保留postion位小数 然后去0
    public static <T> String stringToFormat(T data,int positon){

        String wei="";
        if (positon>0){
            for (int i=0;i<positon;i++){
                wei+="0";
            }
        }
        wei="0."+wei;



        DecimalFormat format = new DecimalFormat(wei);
        String a = format.format(new BigDecimal(String.valueOf(data)));
        return deleteZero(a) ;


    }


    //去掉0  如果是整数就去掉小数点
    public static String deleteZero(String value){

        int jie=0;

        for (int i=0;i<value.length();i++){
            if (value.charAt(value.length()-1-i)=='0'){
                jie++;
            }else{
                break;
            }
        }

        String result=value.substring(0,value.length()-jie);

        if (result.lastIndexOf(".")==result.length()-1){
            return result.substring(0,result.length()-1);
        }else{
            return result;
        }


    }



}
