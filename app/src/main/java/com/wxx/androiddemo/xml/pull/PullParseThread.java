package com.wxx.androiddemo.xml.pull;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.wxx.androiddemo.bean.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: TangRen on 2019/2/25
 * 包名：com.wxx.androiddemo.xml.pull
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class PullParseThread extends Thread {
    private Context context;

    public PullParseThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        AssetManager manager = context.getAssets();
        try {
//            List<User> users = toParseToList(manager.open("res.xml"));
//            for (User user : users) {
//                Log.e("PullParseThread",
//                        "run(PullParseThread.java:36)" + user);
//            }

            parseXml(context.getAssets().open("res.xml"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PullParseThread",
                "run(PullParseThread.java:44)"+e.toString());
        }
    }

    private List<User> toParseToList(InputStream inputStream) throws Exception {
        //获取Pull工厂对象
        XmlPullParserFactory factor = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = factor.newPullParser();
        xmlPullParser.setInput(inputStream, "UTF-8");

        List<User> list = new ArrayList<>();
        int eventType = xmlPullParser.getEventType();
        User user = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = xmlPullParser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("user".equals(nodeName)) {
                        user = new User();
                        int id = Integer.valueOf(xmlPullParser.getAttributeValue(null, "id"));
                        user.setId(id);
                    } else if ("age".equals(nodeName)) {
                        if (user != null) {
                            user.setAge(Integer.valueOf(xmlPullParser.nextText()));
                        }
                    } else if ("name".equals(nodeName)) {
                        if (user != null) {
                            user.setName(xmlPullParser.nextText());
                        }
                    } else if ("content".equals(nodeName)) {
                        if (user != null) {
                            user.setContent(xmlPullParser.nextText());
                        }
                    } else if ("headImgUrl".equals(nodeName)) {
                        if (user != null) {
                            user.setHeadImgUrl(nodeName);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("user".equals(nodeName)) {
                        list.add(user);
                    }
                    break;
                default:
                    break;
            }
            eventType = xmlPullParser.next();

        }
        if (inputStream != null) {
            inputStream.close();
        }
        return list;
    }


    private void parseXml(InputStream stream) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = factory.newPullParser();
        xmlPullParser.setInput(stream, "UTF-8");
        int eventType = xmlPullParser.getEventType();
        int resultCode = -1;
        String resultMsg = "default";
        String user = "default";
        String content = "default";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = xmlPullParser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("resultCode".equals(nodeName)) {
                        resultCode = Integer.valueOf(xmlPullParser.nextText());
                    } else if ("resultMsg".equals(nodeName)) {
                        resultMsg = xmlPullParser.nextText();
                    } else if ("user".equals(nodeName)) {
                        user = xmlPullParser.nextText();
                    } else if ("content".equals(nodeName)) {
                        content = xmlPullParser.nextText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
                default:
                    break;
            }
            eventType = xmlPullParser.next();
        }

        Log.e("PullParseThread",
                "parseXml(PullParseThread.java:129)resultCode=" + resultCode + ",resultMsg=" + resultMsg + ",user=" + user + ",content=" + content);
    }
}
