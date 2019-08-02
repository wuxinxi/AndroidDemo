package com.wxx.rsa.sign;

import android.text.TextUtils;
import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 作者：Tangren on 2019-07-17
 * 包名：com.wxx.androiddemo.rsa.sign
 * 邮箱：996489865@qq.com
 * TODO:支持RSA RSA2
 */
public class RSAPlus {
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_RSA_ALGORITHM = "SHA1WithRSA";
    public static final String SIGN_TYPE_RSA2 = "RSA2";
    public static final String SIGN_RSA2_ALGORITHM = "SHA256WithRSA";

    /**
     * @param signType   签名类型
     * @param privateKey 签名私钥
     * @param signData   需要签名的数据串
     * @return 签名结果
     */
    public static String getSign(String signType, byte[] privateKey, String signData) {
        try {
            PrivateKey priKey;
            Signature signature;
            if (TextUtils.equals(SIGN_TYPE_RSA, signType) ||
                    TextUtils.equals(SIGN_RSA_ALGORITHM.toLowerCase(), signType.toLowerCase())) {
                priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, privateKey);
                signature = Signature.getInstance(SIGN_RSA_ALGORITHM);
            } else if (TextUtils.equals(SIGN_TYPE_RSA2, signType) ||
                    TextUtils.equals(SIGN_RSA2_ALGORITHM.toLowerCase(), signType.toLowerCase())) {
                priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, privateKey);
                signature = Signature.getInstance(SIGN_RSA2_ALGORITHM);
            } else {
                return "";
            }
            signature.initSign(priKey);
            signature.update(signData.getBytes());
            byte[] signed = signature.sign();
            return Base64.encodeToString(signed, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param algorithm  算法
     * @param privateKey 私钥
     * @return 私钥
     * @throws Exception 异常信息
     */
    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, byte[] privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm,"BC");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
    }

    /**
     * @param algorithm 算法
     * @param publicKey 公钥
     * @return 私钥
     * @throws Exception 异常信息
     */
    public static PublicKey getPublicKeyFromX509(String algorithm, byte[] publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
    }


}
