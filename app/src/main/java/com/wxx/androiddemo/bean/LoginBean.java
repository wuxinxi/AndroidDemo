package com.wxx.androiddemo.bean;

/**
 * 作者：Tangren on 2019-02-25
 * 包名：com.wxx.androiddemo.bean
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class LoginBean {
    private int resultCode;
    private String resultMsg;
    private Res res;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    class Res {
        private String user;
        private String content;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Res{" +
                    "user='" + user + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", res=" + res +
                '}';
    }
}
