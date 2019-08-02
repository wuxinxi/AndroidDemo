package com.wxx.androiddemo.ftp;

/**
 * 作者：Tangren on 2019-04-29
 * 包名：com.wxx.androiddemo.ftp
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class FTPUser {
    private String path;
    private String fileName;
    private String user;
    private String psw;
    private int port;
    private String ip;
    private boolean activeMode = true;

    public void setUser(String path, String fileName, String ip, int port, String user, String psw) {
        this.path = path;
        this.fileName = fileName;
        this.user = user;
        this.psw = psw;
        this.port = port;
        this.ip = ip;
    }

    public boolean isActiveMode() {
        return activeMode;
    }

    public void setActiveMode(boolean activeMode) {
        this.activeMode = activeMode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
