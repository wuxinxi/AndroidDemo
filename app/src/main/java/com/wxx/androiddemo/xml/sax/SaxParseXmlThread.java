package com.wxx.androiddemo.xml.sax;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.wxx.androiddemo.bean.LoginBean;
import com.wxx.androiddemo.bean.User;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 作者：Tangren on 2019-02-26
 * 包名：com.wxx.androiddemo.xml.sax
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class SaxParseXmlThread extends Thread {
    private Context context;

    public SaxParseXmlThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        InputStream in = null;
        InputStream in2 = null;
        try {
            AssetManager manager = context.getAssets();
            in = manager.open("demo.xml");
            List<User> list = doParseToList(in);
            for (User user : list) {
                Log.e("SaxParseXmlThread",
                        "run(SaxParseXmlThread.java:32)" + user);

            }
            in2 = manager.open("res.xml");
            parseTo(in2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (in != null) {
                    in.close();
                }
                if (in2 != null) {
                    in2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<User> doParseToList(InputStream in) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MyContentHandler handler = new MyContentHandler();
        saxParser.parse(in, handler);
        return handler.list;
    }

    /**
     * <?xml version="1.0" encoding="UTF-8" ?>
     * <users>
     * <user id="1">
     * <age>25</age>
     * <name>王五</name>
     * <content>大家好,我是王五</content>
     * <headImgUrl>http://wxinxi.github.io</headImgUrl>
     * </user>
     * <user id="2">
     * <age>27</age>
     * <name>燕小六</name>
     * <content>大家好,我是燕小六</content>
     * <headImgUrl>http://wxinxi.github.io</headImgUrl>
     * </user>
     * </users>
     */
    private class MyContentHandler extends DefaultHandler {
        private List<User> list;
        private User user;
        private String tagName;

        public List<User> getList() {
            return list;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            list = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if ("user".equals(localName)) {
                user = new User();
                String id = attributes.getValue("id");
                user.setId(Integer.valueOf(id));
            }
            tagName = localName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            if (TextUtils.isEmpty(tagName) || user == null) {
                return;
            }
            String data = new String(ch, start, length);
            switch (tagName) {
                case "age":
                    user.setAge(Integer.valueOf(data));
                    break;
                case "name":
                    user.setName(data);
                    break;
                case "content":
                    user.setContent(data);
                    break;
                case "headImgUrl":
                    user.setHeadImgUrl(data);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if ("user".equals(localName)) {
                if (list != null) {
                    list.add(user);
                }
                user = null;
            }
            tagName = null;
        }
    }

    /**
     * <?xml version="1.0" encoding="UTF-8" ?>
     * <result><resultCode>200</resultCode>
     * <resultMsg>登录成功</resultMsg>
     * <res>
     * <user>张三</user>
     * <content>2019年注册</content>
     * </res>
     * </result>
     *
     * @param stream .
     * @throws Exception .
     */
    private void parseTo(InputStream stream) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MyParseHandler handler = new MyParseHandler();
        saxParser.parse(stream, handler);
    }

    private class MyParseHandler extends DefaultHandler {
        private LoginBean loginBean;
        private LoginBean.Res res;
        private String tagName;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            loginBean = new LoginBean();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if ("res".equals(localName)) {
                res = new LoginBean.Res();
                loginBean.setRes(res);
            }
            tagName = localName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            if (TextUtils.isEmpty(tagName) || loginBean == null) {
                return;
            }
            String data = new String(ch, start, length);
            switch (tagName) {
                case "resultCode":
                    loginBean.setResultCode(Integer.valueOf(data));
                    break;
                case "resultMsg":
                    loginBean.setResultMsg(data);
                    break;
                case "user":
                    res.setUser(data);
                    break;
                case "content":
                    res.setContent(data);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            tagName = null;
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            Log.e("MyParseHandler",
                    "endDocument(MyParseHandler.java:214)" + loginBean.toString());
        }
    }
}
