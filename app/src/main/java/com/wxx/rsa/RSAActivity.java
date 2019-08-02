package com.wxx.rsa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.wxx.androiddemo.R;
import com.wxx.androiddemo.http.nohttp.CallServer;
import com.wxx.androiddemo.http.nohttp.HttpListener;
import com.wxx.androiddemo.http.nohttp.JsonRequest;
import com.wxx.rsa.sign.SignUtil;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Tangren on 2019-07-17
 * 包名：com.wxx.androiddemo.rsa
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class RSAActivity extends AppCompatActivity {

    String private_key = "MIIEpQIBAAKCAQEA1LFvRMzTWwMxIMPJYwBDl9F0Hwfe1SMkpGYwZHQwP53tn68rvUN0t9rvs3lI81QJ/QIs0YHaoNO16akQBlzK485PcXSQ0TXrvatuodKdxJKaV/61+sseUSrqWSXcDk90s4MrmKtU+yPc1omM6S8oknTmLHpuEvPoXPYzZ54MLSkYgkMsXF4xoN2BTlzoxa4StaL9OUiB4BMX3CDImd38TE/KneJBFsYak2cK7eVNsavDCP+1+/1ga8yErUPuM6wvwLrFomR1qLTAHGQFmIubDjYUK4z6uadkP/TEyXYp6aWpH1bPii9a/nwoeyY6gG5zSQRW/Xw+jDiQb1vt8dTrLwIDAQABAoIBAQDCRohSGZ185n97ZBqRWW5kQCeHKJM7r+wKVmUhfJeNpF2mnEShjfoQ7eRA1SnzSPIOrmvtumTOvlLNaWV/ykZwqsQZ59VXo57/EpXtLgp5wUdF1Ry4Rcwda8u/PjuwvpbkWY3615S6CNnxBJc3b6HplOmh+vqMSUoXj7MIlG6e19zVi+g6ciaFxSDBOJFdathxC48RgDffsmAglfjdJusHE0lOccKt+C4DxNn5ZE/xivQ1/W0mqTRlZxMhpG8E1Z5i31tFJE4LzsRK8jnrRuTvHl8DL0MdLgSJre4zPj4wtPrYpUXecI5qHBaoT7F41Wx/JGmERnHJS0nADDH+rLMxAoGBAP8ihOb7uhmN43Vr5Rh2BMcRxvcwnKzIYG5c+igzcNVnAkf1J5IFTh7Mu20UJ/tefeGnltUuqLYer+6bAkSj+faqbcHouilkeydhC5pyntrBWP6YTfqG4D0Ig7POUIeJ3rskAShjIFnx+Uw0K1mBdJULMR+DWK0Hy20AlXkw2c8DAoGBANVqEnWOGT2LkSrPkZtpMO38MeUvkJXPKtdUACPwxay94XEIUlUXfrBqblo/HfkvpW1CyG1r8dbcGmLEbz899gAWeRZrhKsM6MMAguXKtBqNL2gJXtejWzcpIWd2yZBHzEkkLLdPeED0PoBmMDvETHETu1nbdIfp038OK1R8+RVlAoGBAL49HAIroylYzkkI6atinM9e5w/8NGb1hknklDcTnEfTZjUHjLjpscVvfTWpeOKLI6v1zZcqEmyx1xdAh+Fsr38It4yGHwGsRdQoHYHNg7uzvcsg/8wVc/Cuf5278foOiIDcKOzXdRD8R5pVsBkuWSKlVLydsKpcjIe7jrjt/wrHAoGAQRk9DjzNODfVjUCJwuVAdqfCNI3gxcHO6KYH8O6l7b5yAT+vlaM4Euo0RTURmIxPItgHOVF/ELJNHimbYagt3PL99rfPXTnv2mPSifPeImdEubB6DL8YuOfD98KFU4yDJSByv2vvW6jlU9dYCTMkui1xrcRCdJ7/07bnwrroY/0CgYEAzTiZfq5PR6gV1vUn/4DajdxnE89XsGtOrgpUqGCyLBuHZukVN6n7pgpxUSLKRT+TfTUjvTGyY2o6GHE4N1/iNVyB/dXHxIhiixaUb8XpoXmT92CtC8LjGMMdvU1mefSR97BB4NXcb7v/Qwt8teR0ZdlBY4b9BRk7iH5QT7uugEM=";

    //    String private_key="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDGwI8HYsf/idY71pIBN6oJ6t7tkCVScXmh8judCzA1TUXwaXa4HBTx6h7mKfFCTjZSJW4yR+ICp9QB42Bkem1Dnx20v11PVsrGxu3058lXEwobeO6y53SS3dfQWNp8dty4lWBdhYDju99oGTdCCEOYK6j/TYmaEG/o8JZfYSRDVQ2rVNfWYNlwgADu+MTgpufuAhz+CHXpHYGBxeW1ki4OMhFTsIXTAwonTcvwCxtLpALPwylhYrsWXQ0mNR4EjYzRpLjDZgR6MVCFiF7ZFbNG/fzdp8bkvexdETj4oJ8cQx9pDxlPHGf34vySBCgTNjBg2C6tf1JHgzY19xS9CBJfAgMBAAECggEAWyTW8oYkPVWSfyJpFlHWknG5BayC0Qnkw5V4LQ6Cbw8esZ9LqCaInF/HB7nYP5XqZ0VvAXPhe2JHYQ5KmAlkrgv77XWMbuPYXcvAN/1LYbkDe5G7tSNbXDaLz6nPmCBSG/u0k26zqrvvOafGpWtwxAnRmDKQlaxQBby+2MKNhKpfjJI0YbU2fmNZeTB+mxOEVBnebAfGQAzPc7XX8ZdInZ2qvGI7uNPi2m73b0MqvGMeC+523ayanDxLCFfmD/DQXEUKXmDIlofeb98ZNcr+qEWMnvYX8GY8XwudNgLQt5nzjG2PsQDNqG/8g5mFn+AM9BWFS0ddBVROfkRvEL03wQKBgQDl14mVdgKXwFNAVguNHhI6WaAVblbZErrKAVkuJ/yOgxYTxP2tURQgrnX+gtM4vxsA/dVQtS39m3gqG15rV4bYMHhbrprPf6vIxs4puDo2DijPf76+ppUw9unA2WFJ4AXMSBnduEO+3G2qbvHRZ7BRPGGc573xkHw20fMyKolKjwKBgQDdXzf98Y1tdQKW435VDHKlKsZrEcdu8aa2M9Y5UjQbfVvfWia9ofs291Hd1JIS1THqVeK3DzlJTe2qqi98c5/bTyINJzvkUagn/OVMpcDkYZkeId6MIZ9QJYCvQPYfgA3eoiHqzLocAShgzvtLosTqMplYXxLke4a8LcPADBzjMQKBgAWXGneSKTn7qYq1DQ7fnl3g9C3RQzeAZx6mrb6bpl7o/U55yhm/ERhCZghMskp9C3EYu2l9HqM+dsu/YqqFtHfppePE+Wr2lf0KKNWG75OFAKUbXE5Syuon90ODhgQ/+KQhVM+5vZwUzG4KyMLpHLa9GzqvwETx2LOUo6+irTgtAoGBALZsHwH/KiKHCAk7kwGW7mq5YyUcU5JTRKRk9yXpXdNBbJIUAlTGxPO4vxfkcjNeyHFpMRjVTMnhk/bNo7AUwCjDfzYDnNE7kSzStEqDVP9ehgVUt8YWduhe3zqMd8XhioxqYzTQXJVzRsjwCBl+GnM8Gy29ux3ZxWAQ/nyl/rAhAoGBAIXs95FbP7pauIDCRlRcnaWh2b7xONIk2J+cRdcVnGVZprVbMcjcxZ9heIe6TZ0mDMnnk3srdCrH6fa5oz+rjjVz/vwQraib0aJZCdC5E3kKtBwVSdm7bnsYDd4Ndn1o+u2r1MPgTZUsUcsLRe198UVnm0QYHVv4bY9UC+SY3fwh";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://openapi.alipay.com/gateway.do";
        HashMap<String, String> map = new HashMap<>();
        map.put("app_id", "2018042660071299");
        map.put("method", "alipay.commerce.transport.offlinepay.key.query");
        map.put("format", "JSON");
        map.put("charset", "utf-8");
        map.put("sign_type", "RSA2");
        map.put("timestamp", "2019-07-19 18:07:50");
        map.put("version", "1.0");
        map.put("biz_data", "");
        byte[] key = Base64.decode(private_key, Base64.NO_WRAP);
        String sign = SignUtil.paramSign(map, key, "RSA2");
        JsonRequest request = new JsonRequest(url);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("app_id", "2018042660071299");
        map1.put("method", "alipay.commerce.transport.offlinepay.key.query");
        map1.put("format", "JSON");
        map1.put("charset", "utf-8");
        map1.put("sign_type", "RSA2");
        map1.put("timestamp", "2019-07-19 18:07:50");
        map1.put("version", "1.0");
        map1.put("biz_data", "");
        map1.put("sign", sign);
        request.set(map1);
        CallServer.getHttpclient().add(0, request, new HttpListener<JSONObject>() {
            @Override
            public void success(int what, Response<JSONObject> response) {
                Log.d("RSAActivity",
                        "success(RSAActivity.java:77)" + response.get().toJSONString());
            }

            @Override
            public void fail(int what, String e) {
                Log.d("RSAActivity",
                        "fail(RSAActivity.java:83)" + e);

            }
        });


    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
