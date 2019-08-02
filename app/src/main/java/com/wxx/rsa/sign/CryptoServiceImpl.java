package com.wxx.rsa.sign;

import android.util.Base64;

/**
 * Created by luluma on 2017-04-11 10:30.
 * Email: 316654669@qq.com
 */

public class CryptoServiceImpl {
    private static CryptoServiceImpl gInstance = null;

    public static synchronized CryptoServiceImpl getInstance() {
        if (gInstance == null) {
            gInstance = new CryptoServiceImpl();
        }
        return gInstance;
    }

    public synchronized String SignWithPrivateKey(String data, byte[] key_bytes) {

        if (data == null || data.length() == 0 || key_bytes == null || key_bytes.length <= 0) {

            return "";
        }

        byte[] signbytes = RSA.sign(data.getBytes(), key_bytes);

        return signbytes != null ? Base64.encodeToString(signbytes, Base64.NO_WRAP) : "";
    }

    public synchronized boolean VerifySign(String data, byte[] publicKey, String sign) {
        if (sign == null || sign.length() == 0 || data == null || data.length() == 0) {
            return false;
        }
        return RSA.verify(data.getBytes(), publicKey, Base64.decode(sign, Base64.DEFAULT));
    }
}
