package com.wxx.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 作者：Tangren on 2019-08-02
 * 包名：com.wxx.ftp
 * 邮箱：996489865@qq.com
 * TODO:ftp管理
 */
public class FTPManage {
    /**
     * IP
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 服务器文件路径
     */
    private String servicePath;
    /**
     * 文件本地保存路径
     */
    private String localPath;
    /**
     * 是否是主动模式
     */
    private boolean isMainModel;
    /**
     * 下载重试次数
     */
    private int retryCnt;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPsw;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 批量上传
     */
    private List<String> listPath;

    /**
     * 如果文件已存在是否删除
     * 不删除则默认支持增量
     */
    private boolean isDelOld;

    static class Builder {
        private String ip;
        private String userName;
        private String userPsw;
        private String fileName;
        private int port = 21;
        private String localPath = "";
        private String servicePath = "/";
        private boolean isMainModel = true;
        private int retryCnt = 0;
        private boolean isDelOld = true;
        private List<String> listPath;

        public Builder(String ip, String userName, String userPsw, String fileName) {
            this.ip = ip;
            this.userName = userName;
            this.userPsw = userPsw;
            this.fileName = fileName;
        }

        public Builder setServicePath(String servicePath) {
            this.servicePath = servicePath;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setMainModel(boolean mainModel) {
            isMainModel = mainModel;
            return this;
        }

        public Builder setRetryCnt(int retryCnt) {
            this.retryCnt = retryCnt;
            return this;
        }

        public Builder setLocalPath(String localPath) {
            this.localPath = localPath;
            return this;
        }

        public Builder setDeleteOld(boolean isDelOld) {
            this.isDelOld = isDelOld;
            return this;
        }

        public Builder setListPath(List<String> listPath) {
            this.listPath = listPath;
            return this;
        }

        public FTPManage create() {
            return new FTPManage(this);
        }
    }

    private FTPManage(Builder builder) {
        ip = builder.ip;
        port = builder.port;
        userName = builder.userName;
        userPsw = builder.userPsw;
        localPath = builder.localPath;
        servicePath = builder.servicePath;
        isMainModel = builder.isMainModel;
        retryCnt = builder.retryCnt;
        fileName = builder.fileName;
        isDelOld = builder.isDelOld;
        listPath = builder.listPath;
    }

    private FTPClient ftpClient;

    /**
     * 登录
     *
     * @return FTPClient
     */
    private FTPClient login() {
        int reply;
        try {
            ftpClient = new FTPClient();
            // 连接FTP服务器
            ftpClient.connect(ip, port);
            // 登录
            ftpClient.login(userName, userPsw);
            //连接的状态码
            reply = ftpClient.getReplyCode();
            ftpClient.setDataTimeout(20 * 1000);
            ftpClient.setDefaultTimeout(20 * 1000);
            ftpClient.setConnectTimeout(20 * 1000);
            //判断是否连接上ftp
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return null;
            }

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
            if (isMainModel) {
                ftpClient.enterLocalActiveMode();
            } else {
                ftpClient.enterLocalPassiveMode();
            }
            return ftpClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param callBack 回调
     */
    public void download(OnFtpCallBack callBack) {
        try {
            if (ftpClient == null) {
                ftpClient = login();
            }
            if (ftpClient == null) {
                callBack.onFail("登录失败");
                return;
            }

            String serviceAllPath = servicePath + "/" + fileName;
            System.out.println("FTPManage.download " + serviceAllPath);
            FTPFile[] ftpFile = ftpClient.listFiles(serviceAllPath);
            if (ftpFile == null || ftpFile.length == 0) {
                callBack.onFail("文件不存在");
                return;
            }

            File localFile = new File(localPath);
            if (!localFile.exists()) {
                boolean mkdirs = localFile.mkdirs();
            }
            //文件完整路径
            String fileAllLocalPath = localPath + File.separator + fileName;
            File fileAllLocalPathFile = new File(fileAllLocalPath);
            //获取远程服务器文件大小
            long serverSize = ftpFile[0].getSize();
            long localSize = 0;
            if (fileAllLocalPathFile.exists()) {
                //如果文件存在
                if (isDelOld) {
                    //删除旧文件
                    boolean delete = fileAllLocalPathFile.delete();
                    boolean newFile = fileAllLocalPathFile.createNewFile();
                } else {
                    localSize = fileAllLocalPathFile.length();
                    if (localSize >= serverSize) {
                        boolean delete = fileAllLocalPathFile.delete();
                        boolean newFile = fileAllLocalPathFile.createNewFile();
                        localSize = 0;
                    }
                }
            } else {
                boolean newFile = fileAllLocalPathFile.createNewFile();
            }

            //开始准备下载文件，允许断点
            OutputStream out = new FileOutputStream(fileAllLocalPathFile, true);
            //设置追加的位置
            ftpClient.setRestartOffset(localSize);
            InputStream inputStream = ftpClient.retrieveFileStream(servicePath + File.separator + fileName);
            ioWork(serverSize, inputStream, out, callBack);
            if (ftpClient.completePendingCommand() && fileAllLocalPathFile.length() == serverSize) {
                callBack.onSuccess(fileAllLocalPath);
            } else if (fileAllLocalPathFile.length() != serverSize) {
                callBack.onFail("文件长度校验错误");
            } else {
                callBack.onFail("下载失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFail(e.getClass().getName());
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传
     *
     * @param callBack 回调
     */
    public void upLoad(OnFtpCallBack callBack) {
        if (ftpClient == null) {
            ftpClient = login();
        }
        if (ftpClient == null) {
            callBack.onFail("登录失败");
            return;
        }
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            String workPath = ftpClient.printWorkingDirectory();

            if (!ftpClient.changeWorkingDirectory(workPath + servicePath)) {
                String[] dirs = servicePath.split("/");
                String tempPath = workPath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        if (!ftpClient.makeDirectory(tempPath)) {
                            callBack.onFail("创建目录失败");
                            return;
                        } else {
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }

            InputStream inputStream = new FileInputStream(localPath + "/" + fileName);
            OutputStream out = ftpClient.storeFileStream(fileName);
            ioWork(inputStream.available(), inputStream, out, callBack);
            callBack.onSuccess(servicePath);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFail(e.getClass().getName());
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传文件夹
     *
     * @param callBack 回调
     */
    public void upLoadFolder(OnFtpCallBack callBack) {
        if (ftpClient == null) {
            ftpClient = login();
        }
        if (ftpClient == null) {
            callBack.onFail("登录失败");
            return;
        }
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            String workPath = ftpClient.printWorkingDirectory();

            if (!ftpClient.changeWorkingDirectory(workPath + servicePath)) {
                String[] dirs = servicePath.split("/");
                String tempPath = workPath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        if (!ftpClient.makeDirectory(tempPath)) {
                            callBack.onFail("创建目录失败");
                            return;
                        } else {
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }

            if (listPath == null || listPath.size() == 0) {
                callBack.onFail("数据为空");
                return;
            }

            for (String path : listPath) {
                System.out.println(path);
                String fileName = path.substring(path.lastIndexOf("\\") + 1);
                InputStream inputStream = new FileInputStream(path);
                try {
                    boolean b = ftpClient.storeFile(fileName, inputStream);
                    System.out.println("FTPManage.upLoadFolder 上传结果" + b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                inputStream.close();
            }
            callBack.onSuccess(servicePath);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFail(e.getClass().getName());
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * io处理
     *
     * @param available   大小
     * @param inputStream 输入流
     * @param out         输出流
     * @throws Exception .
     */
    private void ioWork(long available, InputStream inputStream, OutputStream out, OnFtpCallBack callBack) throws Exception {
        byte[] data = new byte[1024];
        int len;
        long step = available / 100;
        long process = 0;
        long currentSize = 0;
        while ((len = inputStream.read(data)) != -1) {
            out.write(data, 0, len);
            currentSize = currentSize + len;
            if (currentSize / step != process) {
                process = currentSize / step;
                if (process % 2 == 0) {
                    callBack.onProgress((int) process);
                }
            }
        }
        out.flush();
        out.close();
        inputStream.close();
    }
}


