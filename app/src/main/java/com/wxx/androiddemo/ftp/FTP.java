package com.wxx.androiddemo.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 作者：Tangren on 2019-04-28
 * 包名：com.wxx.androiddemo.ftp
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class FTP {

    private String url;
    private int port = 21;
    private String username;
    private String password;
    /**
     * 本地保存路径
     */
    private String savePath;
    /**
     * ftp路径：路径+/
     */
    private String ftpPath;
    /**
     * 文件名
     */
    private String fileName;
    private boolean deleteOld = true;
    private String tag = "TAG";
    private String msg = "";
    private FTPClient ftp;
    private boolean model;

    public FTP builder(String url) {
        this.url = url;
        return this;
    }

    public FTP setPort(int port) {
        this.port = port;
        return this;
    }

    public FTP setLogin(String username, String psw) {
        this.username = username;
        this.password = psw;
        return this;
    }

    public FTP setPath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public FTP setFTPPath(String ftpPath) {
        this.ftpPath = ftpPath;
        return this;
    }

    public FTP setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public FTP setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public FTP setDeleteOld(boolean deleteOld) {
        this.deleteOld = deleteOld;
        return this;
    }


    public FTP setModel(boolean model) {
        this.model = model;
        return this;
    }


    /**
     * @return ftp连接
     */
    private FTPClient ftpClient() {
        System.out.println("FTP.ftpClient");
        FTPClient ftp = new FTPClient();
        int reply;
        try {
            // 连接FTP服务器
            ftp.connect(url, port);
            // 登录
            ftp.login(username, password);
            //连接的状态码
            reply = ftp.getReplyCode();
            System.out.println("reply = " + reply);
            ftp.setDataTimeout(5 * 1000);
            //判断是否连接上ftp
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                msg = "连接失败reply=" + reply;
                return null;
            }

            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            ftp.setControlEncoding("GBK");
            if (model) {
                ftp.enterLocalActiveMode();
            } else {
                ftp.enterLocalPassiveMode();
            }
            return ftp;
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.toString();
        }
        return null;
    }


    /**
     * 下载
     *
     * @param onCallBack 回调
     */
    public void download(OnCallBack onCallBack) {
        OutputStream out = null;
        InputStream input = null;
        try {
            if (ftp == null) {
                ftp = ftpClient();
            }
            if (ftp == null) {
                onCallBack.onFail(msg);
                return;
            }
            System.out.println("FTP.download");
            FTPFile[] ftpFile = ftp.listFiles(ftpPath);
            System.out.println("FTP.download " + ftpFile.length + ",ftpPath=" + ftpPath);
            boolean isExist = false;

            for (FTPFile file : ftpFile) {
                //如果包含
                System.out.println("FTP.download >>" + file.getName());
                if (file.getName().contains(fileName)) {
                    isExist = true;
                    long fileSize = file.getSize();
                    int process;
                    long currentSize = 0;
                    long offset = 0;
                    File apkFile = new File(savePath + file.getName());
                    if (apkFile.exists()) {
                        if (deleteOld) {
                            boolean delete = apkFile.delete();
                        } else {
                            long apkFileSize = apkFile.length();
                            if (fileSize > apkFileSize) {
                                offset = apkFileSize;
                            } else {
                                boolean delete = apkFile.delete();
                            }
                        }
                    }
                    out = new FileOutputStream(apkFile, true);
                    //偏移
                    ftp.setRestartOffset(offset);
                    input = ftp.retrieveFileStream(ftpPath + file.getName());
                    byte[] b = new byte[1024];
                    int length;
                    int temp = 0;
                    while ((length = input.read(b)) != -1) {
                        out.write(b, 0, length);
                        currentSize = currentSize + length;
                        process = (int) ((((float) currentSize / fileSize)) * 100);
                        if (process % 2 == 0 && temp != process) {
                            temp = process;
                            onCallBack.onProgress(process);
                        }
                    }
                    out.flush();
                    break;
                }
            }

            ftp.logout();
            ftp.disconnect();
            //判断是否退出成功，不成功就再断开连接。
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    System.out.println("FTP.download IOException异常>>" + ioe.toString());
                }
            }
            if (isExist) {
                onCallBack.onSuccess(savePath + fileName);
            } else {
                onCallBack.onFail("文件不存在" + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            onCallBack.onFail(e.toString());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void upLoad(List<String> localPathList) {

    }


}
