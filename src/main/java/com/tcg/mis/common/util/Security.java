package com.tcg.mis.common.util;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密类(MD5)
 * 
 * @author jacky
 * @since
 */
public final class Security {
    
    private static Logger logger=Logger.getLogger(Security.class);

    /**
     * Description: 得到一个字符串加密后的结果<br>
     * [参数列表，说明每个参数用途]
     * 
     * @param s
     * @return String
     */
    public static final String getSecurity ( String s ) {
        final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            final byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            final byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j << 1];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Automatically generated method: Security
     */
    private Security() {

    }

    // MD5
    public static String md5 ( String inputText ) {
        return hash(inputText, "md5");
    }

    public static String hash( String inputText , String algorithmName ) {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException(
                    "Please enter content to encrypt");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptText;
    }
    
    public static String encrypt(String inputText, String algorithmName, String salt){
        try {
            Key key = new SecretKeySpec(salt.getBytes(), algorithmName);
            Cipher cipher = Cipher.getInstance(algorithmName);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = Base64.encode(cipher.doFinal(inputText.getBytes()));
            return new String(encrypted);
        } catch (NoSuchAlgorithmException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        } catch (NoSuchPaddingException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        } catch (InvalidKeyException e) {
            logger.debug("Incorrect Key on dev level", e);
        } catch (IllegalBlockSizeException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        } catch (BadPaddingException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        }
        
        return "";
    }
    
    public static String decrypt(String inputText, String algorithmName, String salt){
        try {
            Key key = new SecretKeySpec(salt.getBytes(), algorithmName);
            Cipher cipher = Cipher.getInstance(algorithmName);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encrypted = Base64.encode(cipher.doFinal(inputText.getBytes()));
            return new String(encrypted);
        } catch (NoSuchAlgorithmException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        } catch (NoSuchPaddingException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        } catch (InvalidKeyException e) {
            logger.debug("Incorrect Key on dev level", e);
        } catch (IllegalBlockSizeException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        } catch (BadPaddingException e) {
            logger.debug("Incorrect Algorithm on dev level", e);
        }
        return "";
    }

    private static String hex ( byte[] arr ) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}
