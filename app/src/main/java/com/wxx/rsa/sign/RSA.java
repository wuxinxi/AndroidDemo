package com.wxx.rsa.sign;

import com.yanzhenjie.nohttp.Logger;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by luluma on 2017-04-11 10:21.
 * Email: 316654669@qq.com
 */

public class RSA {
    /**
     * 密钥算法
     */
//    private static final String KEY_ALGORITHM = "RSA";
    private static final String KEY_ALGORITHM = "RSA2";

    /**
     * 签名算法
     */
//    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";


    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    /*package*/
    static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, "BC");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将私钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchProviderException
     */
    /*package*/
    static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * <p>
     * 用用户私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     * @return
     * @throws Exception
     */
    /*package*/
    static byte[] sign(byte[] data, byte[] key_bytes) {
        try {
            if (key_bytes != null) {
                PrivateKey privateCrtKey = getPrivateKey(key_bytes);
                Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
                signature.initSign(privateCrtKey);
                signature.update(data);
                return signature.sign();
            }

            return null;
        } catch (Exception e) {
            Logger.e(e.getMessage());
            return null;
        }
    }

    /**
     * <p>
     * 用服务器公钥校验数字签名
     * </p>
     *
     * @param data    已加密数据
     * @param keyByte 公钥
     * @param sign    数字签名
     * @return
     * @throws Exception
     */
    /*package*/
    static boolean verify(byte[] data, byte[] keyByte, byte[] sign) {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) getPublicKey(keyByte);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (Exception e) {
            return false;
        }
    }
}
