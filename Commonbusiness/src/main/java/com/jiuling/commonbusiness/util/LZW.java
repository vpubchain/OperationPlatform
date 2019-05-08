package com.jiuling.commonbusiness.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/6/26.
 */
public class LZW {

    /**
     * LZW压缩加密
     * 
     * @param str   json字符串
     * @return
     */
    public static String encrypt(String str) {
        int dictSize = 256;
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) i, i);
        }

        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : str.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }

        // Output the code for w.
        if (!w.equals("")) {
            result.add(dictionary.get(w));
        }

        return LZW.codes_to_string(result);
    }


    /**
     * LZW压缩解密
     *
     * @param str   json字符串
     * @return
     */
    public static String decrypt(String str) {
        List<Integer> compressed = LZW.string_to_codes(str);

        int dictSize = 256;
        Map<Integer, String> dictionary = new HashMap<Integer, String>();
        for (int i = 0; i < 256; i++)
            dictionary.put(i, "" + (char) i);

        String w = "" + (char) (int) compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);

            result.append(entry);

            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));

            w = entry;
        }
        return result.toString();
    }

    protected static String codes_to_string(List<Integer> codes) {
        
        int dictionary_count = 256,
                bits = 8,
                rest = 0,
                rest_length = 0;
        String ret = "";
        for (int code : codes) {
            rest = (rest << bits) + code;
            rest_length += bits;
            dictionary_count++;
            if (dictionary_count > (1 << bits)) {
                bits++;
            }
            while (rest_length > 7) {
                rest_length -= 8;
                ret += (char) (rest >> rest_length);
                rest &= (1 << rest_length) - 1;
            }
        }
        return ret + (rest_length > 0 ? (char) (rest << (8 - rest_length)) : "");
    }

    protected static List<Integer> string_to_codes(String str) {
        int dictionary_count = 256,
                bits = 8,
                rest = 0,
                rest_length = 0;
        List<Integer> codes = new ArrayList<Integer>();
        for (int i = 0; i < str.length(); i++) {
            rest = (rest << 8) + (int) str.charAt(i);
            rest_length += 8;
            if (rest_length >= bits) {
                rest_length -= bits;
                codes.add(rest >> rest_length);
                rest &= (1 << rest_length) - 1;
                dictionary_count++;
                if (dictionary_count > (1 << bits)) {
                    bits++;
                }
            }
        }
        return codes;
    }

    protected static String base64Decode(String str) {
        final Base64.Decoder decoder = Base64.getDecoder();
        try {
            return new String(decoder.decode(str), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static void test(String args[]) {
        String data = args[0];
        if (data == null || data == "" || data.length() < 1) {
            System.out.println("无效参数");
            return;
        }
        System.out.println("原始字符串长度 : " + data.length());

        String origin = "";
        //String origin = LZW.base64Decode(data);
        origin = "TOBEORNOTTOBEORTOBEORNOT";
        data = origin;
        System.out.println("json字符串长度 : " + data.length());

        data = LZW.encrypt(data);
        System.out.println("编码字符串长度 : " + data.length());
        System.out.println("编码压缩比(base64) : " + ((float) data.length() / (float) args[0].length()) + "%");
        System.out.println("编码压缩比(json) : " + ((float) data.length() / (float) origin.length()) + "%");
        System.out.println("编码后字符串 :\n" + data);

        data = LZW.decrypt(data);
        System.out.println("解码后字符串 :\n" + data);
        System.out.println("编码前字符串 :\n" + origin);

        System.out.println("编码前与解码后是否相等 : " + (origin.equals(data) ? "yes" : "no"));

    }

}
