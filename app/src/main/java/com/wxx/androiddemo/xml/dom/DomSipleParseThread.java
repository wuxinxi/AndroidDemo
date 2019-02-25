package com.wxx.androiddemo.xml.dom;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 作者: TangRen on 2019/2/25
 * 包名：com.wxx.androiddemo.xml.dom
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 * /**
 * <?xml version="1.0" encoding="UTF-8" ?>
 * <result>
 * <resultCode>200</resultCode>
 * <resultMsg>登录成功</resultMsg>
 * <res>
 * <user>张三</user>
 * <content>2019年注册</content>
 * </res>
 * </result>
 */
public class DomSipleParseThread extends Thread {
    private Context context;

    public DomSipleParseThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        AssetManager manager = context.getAssets();
        InputStream open = null;
        try {
            open = manager.open("res.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document parse = builder.parse(open);

            String resultCode = "default";
            String resultMsg = "default";
            NodeList resultCodeNodeList = parse.getElementsByTagName("resultCode");
            if (resultCodeNodeList.getLength() == 1) {
                Node child = resultCodeNodeList.item(0).getFirstChild();

                //判断此节点是否是文本节点
                if (child.getNodeType() == Node.TEXT_NODE) {
                    resultCode = child.getNodeValue();
                }
            }

            NodeList resultMsgNodeList = parse.getElementsByTagName("resultMsg");
            if (resultMsgNodeList.getLength() == 1) {
                Node child = resultMsgNodeList.item(0).getFirstChild();
                if (child.getNodeType() == Node.TEXT_NODE) {
                    resultMsg = child.getNodeValue();
                }
            }

            String name = "default";
            String content = "default";
            NodeList res = parse.getElementsByTagName("res");
            for (int i = 0; i < res.getLength(); i++) {
                Node node = res.item(i);
                NodeList childNodes = node.getChildNodes();
                for (int i1 = 0; i1 < childNodes.getLength(); i1++) {
                    Node node1 = childNodes.item(i1);
                    if (node1.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node1;
                        if ("name".equals(element.getNodeName())) {
                            name = element.getNodeValue();
                        } else if ("content".equals(element.getNodeName())) {
                            content = element.getNodeValue();
                        }
                    }
                }

            }
            Log.e("DemoThread",
                    "run(DemoThread.java:173)resultCode=" + resultCode + ",resultMsg=" + resultMsg + ",name=" + name + ",content=" + content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
