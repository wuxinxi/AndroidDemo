package com.wxx.rsa.sign;

import android.text.TextUtils;

import com.yanzhenjie.nohttp.Logger;

import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者：Tangren_ on 2017/5/10 0010.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class SignUtil {
    /**
     * 对map中的参数（不计空的value）排序
     *
     * @param param
     * @param isDecode 请求需要decode，cgi返回的数据排序签名不用decode
     * @return
     */
    public static String sortConstructVars(HashMap<String, String> param, boolean isDecode, ArrayList<String> ignorekeylist) {
        if (param == null) {
            return "";
        }

        String s = "";
        Map.Entry<String, String> entry;
        Iterator<Map.Entry<String, String>> itr;
        boolean first_param;

        List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(param.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().toString().compareTo(o2.getKey().toString());
            }
        });

        first_param = true;

        for (itr = list.iterator(); itr.hasNext(); ) {
            entry = itr.next();
            if (entry != null && !TextUtils.isEmpty(entry.getKey()) && entry.getKey().length() > 0 && !TextUtils.isEmpty(entry.getValue()) && entry.getValue().length() > 0) {
                if (first_param) {
                    s = entry.getKey();
                    s += "=";
                    s += (isDecode ? URLDecoder.decode(entry.getValue()) : entry.getValue());
                    first_param = false;
                } else {
                    s += "&";
                    s += entry.getKey();
                    s += "=";
                    s += (isDecode ? URLDecoder.decode(entry.getValue()) : entry.getValue());
                }
            }
        }
        Logger.d("sortConstructVars(SignUtil.java:69)s:" + s);
        return s;
    }

    /**
     * 用服务器公钥验签
     *
     * @param data
     * @param srcSign
     * @return
     */
    public static boolean checkSignByString(JSONObject data, String srcSign, byte[] serverPublicKey) {
        if (data == null || TextUtils.isEmpty(srcSign) || serverPublicKey == null) {
            return false;
        }
        ArrayList<String> ignores = new ArrayList<String>();
        ignores.add("sign");
        ignores.add("retcode");
        ignores.add("retmsg");
        HashMap<String, String> result = json2Map(data, ignores);

        String src = sortConstructVars(result, false, null);

        return CryptoServiceImpl.getInstance().VerifySign(src, serverPublicKey, srcSign);
    }

    public static HashMap<String, String> json2Map(JSONObject data, ArrayList<String> ignores) {
        HashMap<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = data.keys();
        if (iterator != null) {
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (!TextUtils.isEmpty(key)) {
                    String val = data.optString(key);
                    if (ignores != null && !ignores.contains(key)) {
                        result.put(key, val);
                    }

                }
            }
        }

        return result;
    }

    @Deprecated
    public static String paramSign(HashMap<String, String> params, byte[] clientPriKey) {
        String parmsSortStr = SignUtil.sortConstructVars(params, false, null);
        Logger.d("paramSign(SignUtil.java:116)签名需要的字符串:" + parmsSortStr);
        if (!TextUtils.isEmpty(parmsSortStr)) {
            //用户私钥签名
            String sign = CryptoServiceImpl.getInstance().SignWithPrivateKey(parmsSortStr, clientPriKey);
            Logger.d("paramSign(SignUtil.java:120)用户私钥签名sign:" + sign);
            return sign;
        } else {
            return null;
        }
    }

    public static String paramSign(HashMap<String, String> params, byte[] clientPriKey, String sign_type) {
        String signData = SignUtil.sortConstructVars(params, false, null);
        Logger.d("paramSign(SignUtil.java:116)签名需要的字符串:" + signData);
        if (!TextUtils.isEmpty(signData)) {
            //用户私钥签名
            String sign = RSAPlus.getSign(sign_type, clientPriKey, signData);
            Logger.d("paramSign(SignUtil.java:120)用户私钥签名sign:" + sign);
            return sign;
        } else {
            return null;
        }
    }


}
