package com.haru.mybatis.util;

import com.haru.mybatis.exception.CustomException;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.util.StringUtil;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.*;
import java.lang.reflect.Array;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HARU
 * @since 2018/5/28
 */
public class FTPUtils {
    static final Logger logger = LoggerFactory.getLogger(FTPUtils.class);

    private String userName;
    private String password;
    private String key_pw;           //FTPS证书密码
    private String key_path;         //FTPS证书路径
    private String ip;
    private int port;

    private Charset serverCharset = Charset.forName("GBK");

    private static ThreadLocal<Map<String, FTPClient>> threadFtpClient = new ThreadLocal<Map<String, FTPClient>>() {
    };

    /**
     * 得到ftp连接的自定义key，防止不同ftp连接公用同一个客户端对象
     *
     * @return
     */
    String getKey() {
        return StringUtils.format("userName:{0},password:{1},ip:{2},port:{3}", userName, password, ip, port);
    }

    /**
     * 创建FTP连接
     *
     * @param userName
     * @param password
     * @param ip
     * @param port
     */
    public FTPUtils(String userName, String password, String ip, int port) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    /**
     * 创建FTPS连接
     *
     * @param userName
     * @param password
     * @param ip
     * @param port
     * @param key_path
     * @param key_pw
     */
    public FTPUtils(String userName, String password, String ip, int port, String key_path, String key_pw) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.key_path = key_path;
        this.key_pw = key_pw;
    }

    /**
     * 创建连接
     *
     * @param isSafe 是否为FTPS
     * @return
     */
    public boolean connectServer(boolean isSafe) {
        boolean flag = true;
        Map<String, FTPClient> ftpClientMap = threadFtpClient.get();
        if (ftpClientMap == null || ftpClientMap.get(getKey()) == null) {
            int reply;
            try {
                if (isSafe) {
                    FTPSClient ftpsClient = new FTPSClient();
                    ftpsClient = new FTPSClient(true);      //隐式——TSL模式
                    ftpsClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
                    KeyStore key_ks = KeyStore.getInstance("JKS");
                    key_ks.load(new FileInputStream(key_path), key_pw.toCharArray());
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    kmf.init(key_ks, key_pw.toCharArray());
                    KeyManager[] km = kmf.getKeyManagers();
                    System.out.println("km len: " + km.length);
                    ftpsClient.setKeyManager(km[0]);
                    if (!ftpsClient.login(userName, password)) {
                        throw new CustomException("ftp登录出错");
                    }
                    reply = ftpsClient.getReplyCode();
                    if (!FTPReply.isPositiveCompletion(reply)) {
                        ftpsClient.disconnect();
                        logger.error("FTP server refused connection  " + ip + ":" + port);
                        flag = false;
                    }
                    ftpsClient.execPBSZ(0);
                    ftpsClient.execPROT("P");
                    ftpsClient.setDataTimeout(30000);
                    if (FTPReply.isPositiveCompletion(ftpsClient.sendCommand("OPTS UTF8", "ON"))) {
                        serverCharset = StandardCharsets.UTF_8;
                    }
                    ftpsClient.setControlEncoding(serverCharset.name());
                    ftpsClient.enterLocalPassiveMode();     //被动模式
                    ftpsClient.setFileTransferMode(FTPSClient.STREAM_TRANSFER_MODE);

                    if (!ftpsClient.setFileType(FTPSClient.BINARY_FILE_TYPE)) {
                        throw new CustomException("错误的FileType");
                    }
                    threadFtpClient.set(HashUtils.getMap(getKey(), ftpsClient));
                } else {
                    FTPClient ftpClient = new FTPClient();
                    ftpClient.setDefaultPort(port);
                    ftpClient.connect(ip);
                    if (!ftpClient.login(userName, password)) {
                        throw new CustomException("ftp登录出错");
                    }
                    reply = ftpClient.getReplyCode();
                    if (!FTPReply.isPositiveCompletion(reply)) {
                        ftpClient.disconnect();
                        logger.error("FTP server refused connection  " + ip + ":" + port);
                        flag = false;
                    }
                    ftpClient.setDataTimeout(30000);
                    //开启UTF-8支持
                    if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                        serverCharset = StandardCharsets.UTF_8;
                    }
                    ftpClient.setControlEncoding(serverCharset.name()); //用于传输时，IO的字符集,所有读取服务器上的文件名，用于放回显示时；默认ISO-8859-1不支持中文,所以使用GBK或UTF-8，解决中文乱码
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
                    if (!ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)) {
                        throw new CustomException("错误的FileType");
                    }
                    threadFtpClient.set(HashUtils.getMap(getKey(), ftpClient));
                }
            } catch (KeyStoreException e) {
                flag = false;
                e.printStackTrace();
                throw new CustomException("创建keystore失败！");
            } catch (CertificateException e) {
                flag = false;
                e.printStackTrace();
                throw new CustomException("加载keystore失败！");
            } catch (NoSuchAlgorithmException e) {
                flag = false;
                e.printStackTrace();
                throw new CustomException("获取KeyManagerFactory失败！");
            } catch (UnrecoverableKeyException e) {
                flag = false;
                e.printStackTrace();
                throw new CustomException("初始化证书失败！");
            } catch (SocketException e) {
                flag = false;
                e.printStackTrace();
                throw new CustomException("登录ftp服务器 " + ip + " 失败,连接超时！");
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
                throw new CustomException("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！");
            }
        }
        return flag;
    }

    /**
     * 关闭连接
     *
     * @version 1.0
     */
    public void closeConnect() {
        try {
            FTPClient ftpClient = threadFtpClient.get().get(getKey());
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
                ftpClient = null;
                threadFtpClient.remove();
                logger.info("关闭ftp服务器");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出指定远程目录及子目录下所有文件(及目录)名称
     *
     * @param remotePath 远程目录 目录以"/"分割路径
     * @param filter     过滤器
     * @return 文件名列表
     */
    public List<String> listAllFileNames(String remotePath, FTPFileFilter filter) {
        Map<String, FTPClient> ftpClientMap = threadFtpClient.get();
        List<String> fileNames = new LinkedList<>();
        if (ftpClientMap != null && ftpClientMap.get(getKey()) != null) {
            try {
                String path = remotePath;
                FTPClient ftpClient = ftpClientMap.get(getKey());
                FTPFile[] ftpFiles = ftpClient.listFiles(new String(path.getBytes(serverCharset.toString()), "iso-8859-1"), filter);
                getFileName(ftpClient, path, ftpFiles, fileNames, filter);
            } catch (CustomException e) {
                throw new CustomException(e.getMessage());
            } catch (IOException e) {
                throw new CustomException("无法列出远程目录下的文件" + e);
            }
        }
        return fileNames;
    }

    /**
     * 递归文件夹
     *
     * @param ftpClient
     * @param path
     * @param ftpFiles
     * @param fileNames
     * @param filter
     * @throws IOException
     */
    private void getFileName(FTPClient ftpClient, String path, FTPFile[] ftpFiles, List<String> fileNames, FTPFileFilter filter) throws IOException {
        for (int i = 0; i < ftpFiles.length; i++) {
            FTPFile f = ftpFiles[i];
            String fileFullName = path + (StringUtil.isNotEmpty(path) ? "/" : "") + f.getName();
            //传输到ftp服务器的文件名使用iso-8859-1
            String currentPath = new String(fileFullName.getBytes(serverCharset.toString()), "iso-8859-1");
            fileNames.add(fileFullName);
            if (f.isDirectory()) {
                FTPFile[] currentFtpFiles = ftpClient.listFiles(currentPath, filter);
                getFileName(ftpClient, currentPath, currentFtpFiles, fileNames, filter);
            }
        }
    }

    /**
     * 下载文件到本地
     *
     * @param remotePath
     * @param fileName   服务器上文件名
     * @param localPath
     */
    public boolean downFile(String path, String fileName, String localPath) {
        FTPClient ftpClient = threadFtpClient.get().get(getKey());
        FTPFile[] fs;
        boolean existFile = false;
        try {
            String remotePath = new String(path.getBytes(serverCharset.toString()), "ISO-8859-1");
            if (!ftpClient.changeWorkingDirectory(remotePath)) {                //转移到FTP服务器目录
                logger.error(path + "目录不存在");
                return false;
            }
            fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    existFile = true;
                    File localFile = new File(localPath + File.separator + ff.getName());
                    FileOutputStream is = new FileOutputStream(localFile);
                    is.close();
                    return ftpClient.retrieveFile(ff.getName(), is);
                }
            }
            if (!existFile) {
                logger.error(fileName + "文件不存在");
            }
            return existFile;
        } catch (IOException e) {
            logger.error("文件下载失败", e);
            return false;
        }
    }

    /**
     * 下载文件到输出流
     *
     * @param path
     * @param fileName 服务器上文件名
     * @param os
     * @return
     */
    public boolean downFile(String path, String fileName, OutputStream os) {
        FTPClient ftpClient = threadFtpClient.get().get(getKey());
        FTPFile[] fs;
        boolean existFile = false;
        try {
            String remotePath = new String(path.getBytes(serverCharset.toString()), "ISO-8859-1");
            if (!ftpClient.changeWorkingDirectory(remotePath)) {                //转移到FTP服务器目录
                logger.error(path + "目录不存在");
                return false;
            }
            fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    existFile = true;
                    return ftpClient.retrieveFile(ff.getName(), os);
                }
            }
            if (!existFile) {
                logger.error(fileName + "文件不存在");
            }
            return existFile;
        } catch (Exception e) {
            logger.error("下载文件出错", e);
            return false;
        }
    }

    /**
     * 上传本地文件
     *
     * @param path
     * @param filename      上传到服务器上的文件名
     * @param localFilePath
     * @return
     */
    public boolean upFile(String path, String fileName, String localFilePath) {
        boolean flag = false;
        try {
            String remotePath = new String(path.getBytes(serverCharset.toString()), "ISO-8859-1");
            String remoteFilename = new String(fileName.getBytes(serverCharset.toString()), "ISO-8859-1");
            FTPClient ftpClient = threadFtpClient.get().get(getKey());
            FileInputStream in = new FileInputStream(new File(localFilePath));
            if (StringUtil.isNotEmpty(path)) {
                if (!ftpClient.changeWorkingDirectory(remotePath)) {
                    if (mkdir(path)) {
                        ftpClient.changeWorkingDirectory(remotePath);
                    } else {
                        throw new CustomException("文件夹创建失败");
                    }
                }
            }
            flag = ftpClient.storeFile(remoteFilename, in);
            in.close();
        } catch (UnsupportedEncodingException e) {
            throw new CustomException("中文转码失败");
        } catch (Exception e) {
            throw new CustomException("上传文件失败");
        }
        return flag;
    }

    /**
     * 上传输入流文件
     *
     * @param path
     * @param filename 上传到服务器上的文件名
     * @param local
     * @return
     */
    public boolean upFile(String path, String filename, InputStream local) {
        boolean flag = false;
        try {
            String remotePath = new String(path.getBytes(serverCharset.toString()), "ISO-8859-1");
            String remoteFilename = new String(filename.getBytes(serverCharset.toString()), "ISO-8859-1");
            FTPClient ftpClient = threadFtpClient.get().get(getKey());
            if (StringUtil.isNotEmpty(path)) {
                if (!ftpClient.changeWorkingDirectory(remotePath)) {
                    if (mkdir(path)) {
                        ftpClient.changeWorkingDirectory(remotePath);
                    } else {
                        throw new CustomException("文件夹创建失败");
                    }
                }
            }
            flag = ftpClient.storeFile(filename, local);
            local.close();
        } catch (UnsupportedEncodingException e) {
            throw new CustomException("中文转码失败");
        } catch (Exception e) {
            throw new CustomException("上传文件失败");
        }
        return flag;
    }

    /**
     * 创建文件夹 以"/"分割
     * path.getBytes()转成字节数组（编码），默认是utf-8;new String(path.getBytes("UTF-8"),"ISO-8859-1")将字节数组按ISO-8859-1编码方式解码
     * ftp的编码方式为ISO-8859-1，root/test:若root文件夹存在，则会创建子文件夹test,root不存在则创建此父文件夹
     *
     * @param path
     * @return
     */
    public boolean mkdir(String path) {
        List<String> dirList = Arrays.asList(path.split("/"));
        try {
            final boolean[] mkSuccess = {false};
            if (StringUtil.isEmpty(path)) {
                throw new CustomException("无法创建空文件夹");
            }
            FTPClient ftpClient = threadFtpClient.get().get(getKey());
            String[] dirs = path.split("/");
            mkSuccess[0] = ftpClient.makeDirectory(new String(dirList.get(0).getBytes(serverCharset.toString()), "ISO-8859-1"));
            if (dirList.size() > 1) {
                dirList.stream().reduce((d1, d2) -> {
                    String currentPath = d1 + "/" + d2;
                    try {
                        mkSuccess[0] = ftpClient.makeDirectory(currentPath);
                    } catch (IOException e) {
                        throw new CustomException("文件夹【" + currentPath + "】创建失败");
                    }
                    return currentPath;
                }).orElse(null);
            }
            return mkSuccess[0];
        } catch (CustomException e) {
            logger.error(e.getMessage());
            return false;
        } catch (IOException e) {
            logger.error("文件夹【" + dirList.get(0) + "】创建失败");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    public int sendCommands(String command) {
        try {
            Map<String, FTPClient> ftpClientMap = threadFtpClient.get();
            if (ftpClientMap == null && ftpClientMap.get(getKey()) == null) {
                return ftpClientMap.get(getKey()).sendCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}