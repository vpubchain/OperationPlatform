package com.jiuling.commonbusiness.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by HP on 2018/5/3.
 */

public class YASUOUtils {


    // 将一个字符串按照zip方式压缩和解压缩

        // 压缩
        public static String compress(String str) throws IOException {
            if (str == null || str.length() == 0) {
                return str;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            return out.toString("ISO-8859-1");
        }

        // 解压缩
        public static String uncompress(String str) throws IOException {
            if (str == null || str.length() == 0) {
                return str;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(str
                    .getBytes("ISO-8859-1"));
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
            return out.toString();
        }

//        // 测试方法
//        public static void main(String[] args) throws IOException {
//            System.out.println(ZipUtil.uncompress(ZipUtil.compress("中国China")));
//        }



//
//    public static String compress(byte input[]) {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        Deflater compressor = new Deflater(1);
//        try {
//            compressor.setInput(input);
//            compressor.finish();
//            final byte[] buf = new byte[2048];
//            while (!compressor.finished()) {
//                int count = compressor.deflate(buf);
//                bos.write(buf, 0, count);
//            }
//        } finally {
//            compressor.end();
//        }
//        try {
//            return new String(bos.toByteArray(),"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String uncompress(byte[] input) throws DataFormatException {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        Inflater decompressor = new Inflater();
//        try {
//            decompressor.setInput(input);
//            final byte[] buf = new byte[2048];
//            while (!decompressor.finished()) {
//                int count = decompressor.inflate(buf);
//                bos.write(buf, 0, count);
//            }
//        } finally {
//            decompressor.end();
//        }
//        return new String(bos.toByteArray());
//    }

}
