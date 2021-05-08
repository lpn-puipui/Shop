package cn.edu.guet.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Description TODO
 * @Author YZQ 2020/7/18 15:04
 */
public class MD5 {
    /**
     * 使用Base64进行编码
     *
     * @param encodeContent 需要编码的内容
     * @return 编码后的内容
     */
    public static String encode(String encodeContent) throws UnsupportedEncodingException {
        if (encodeContent == null) {
            return null;
        }
        return  Base64.getEncoder().encodeToString(encodeContent.getBytes("UTF-8"));
    }

    /**
     * 使用Base64进行编码
     *
     * @param encodeText 需要编码的内容
     * @return 编码后的内容
     */
    public static String encode(byte[] encodeText) throws UnsupportedEncodingException {
        return encode(new String(encodeText));
    }

    /**
     * 使用Base64进行解码
     *
     * @param decodeContent 需要解码的内容
     * @return 解码后的内容
     */
    public static String decode(String decodeContent) throws UnsupportedEncodingException {
        byte[] bytes = null;
        if (decodeContent == null) {
            return null;
        }
        //处理url  用Base64.getUrlDecoder()  Base64.getUrlEecoder()
        bytes = Base64.getDecoder().decode(decodeContent);

        return new String(bytes,"UTF-8");
    }
    public static boolean checkpassword(String newpasswd,String oldpasswd) throws UnsupportedEncodingException{
        if(encode(newpasswd).equals(oldpasswd))
            return true;
        else
            return false;
    }
/*
    public static void main(String[] args) throws Exception{
        System.out.println(encode("lpn1234"));
        boolean b=checkpassword("lpn1234","bHBuMTIzNA==");
        System.out.println(b);
    }

 */
}

