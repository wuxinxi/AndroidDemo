package com.wxx.rsa.sign;

import android.util.Base64;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.util.sign
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class ParamSingUtil {

    public static String getSign(String app_id, String timestamp, JSONObject object, String private_key) {
        String sign = null;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("app_id", app_id);
        map.put("charset", "UTF-8");
        map.put("format", "json");
        map.put("version", "1.0");
        map.put("sign_type", "sha1withrsa");
        map.put("timestamp", timestamp);
        if (object != null) {
            map.put("biz_data", object.toJSONString());
        }

        byte[] key = Base64.decode(private_key, Base64.NO_WRAP);
        sign = SignUtil.paramSign(map, key);
        return sign;
    }
}
