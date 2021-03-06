package com.wxx.androiddemo.xml.dom;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.wxx.androiddemo.bean.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 作者: TangRen on 2019/2/25
 * 包名：com.wxx.androiddemo.xml.dom
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class DomParseThread extends Thread {
    private Context context;

    public DomParseThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        try {
            AssetManager manager = context.getAssets();
            InputStream in = manager.open("demo.xml");
            List<User> list = doParseToList(in);
            if (list == null) {
                Log.e("ParseThread",
                        "run(ParseThread.java:56)解析错误");
                return;
            }
            for (User user : list) {
                Log.e("ParseThread",
                        "run(ParseThread.java:55)" + user);
            }

            doParse();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
     *
     * @param in 输入流
     * @return list集合
     */
    private List<User> doParseToList(InputStream in) {
        List<User> list = new ArrayList<>();
        //创建Dom工厂对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //1.DocumentBuilder对象
            DocumentBuilder builder = factory.newDocumentBuilder();

            //2.获取文档对象
            Document document = builder.parse(in);

            //3.获取文档对象的root
            Element root = document.getDocumentElement();

            //4.获取users根节点所有user节点对象
            NodeList userNodes = root.getElementsByTagName("user");

            //5.遍历所有user节点
            for (int i = 0; i < userNodes.getLength(); i++) {
                User user = new User();
                //根据索引取出对应的节点对象
                Element userNode = (Element) userNodes.item(i);
                //取出并设置id
                user.setId(Integer.valueOf(userNode.getAttribute("id")));

                //获取子节点
                NodeList userChildNodes = userNode.getChildNodes();
                for (int i1 = 0; i1 < userChildNodes.getLength(); i1++) {
                    //获取子节点
                    Node node = userChildNodes.item(i1);
                    //判断node节点是否是元素节点
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        switch (element.getNodeName()) {
                            case "age":
                                user.setAge(Integer.valueOf(element.getFirstChild().getNodeValue()));
                                break;
                            case "name":
                                user.setName(element.getFirstChild().getNodeValue());
                                break;
                            case "content":
                                user.setContent(element.getFirstChild().getNodeValue());
                                break;
                            case "headImgUrl":
                                user.setHeadImgUrl(element.getFirstChild().getNodeValue());
                                break;
                            default:
                                break;
                        }
                    }
                }
                list.add(user);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
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
    private void doParse() {
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
