package com.huangxj.common.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLDecoder;
import java.util.Random;

/**
 * 文件工具类
 *
 * @author huangxj
 */
@Component
public class FileUtil {


    /**
     * 图片名称
     */
    public static String SAVE_PATH = "attachment";

    public static String BASE_URL = "";

    @Value("${local.savePath:attachment}")
    public void setSavePath(String val) {
        FileUtil.SAVE_PATH = val;
    }

    @Value("${base_url:}")
    public void setBaseUrl(String val) {
        FileUtil.BASE_URL = val;
    }

    public static final String DATETIME = "yyyyMMddHHmmssSSS";
    private static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 保存文件
     *
     * @return
     */
    public static String saveFile(InputStream inputStream, String filePath, String fileName) {
        try {
//            if (inputStream.available() == 0) {
//                return "";
//            }
            String name = filePath + "/" + createName(fileName);
            File file = new File(SAVE_PATH + name);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            System.out.println(file.getAbsolutePath());
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            return  name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 保存文件
     *
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(SAVE_PATH + filePath);
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            return false;
        }
        file.delete();
        return true;
    }


    /**
     * 创建图片名称
     *
     * @return
     */
    public static String createName(String originName) {
        String dateTime = DateUtils.formatCurrentDate(DATETIME);
        Random random = new Random();
        int num = 6;
        //补上6位数
        for (int i = 0; i < num; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            dateTime += strRand;
        }
        String[] originNames = originName.trim().split("\\.");
        String extension = originNames[originNames.length - 1];
        StringBuffer sb = new StringBuffer();
        sb.append(dateTime);
        sb.append(".");
        sb.append(extension);
        return sb.toString();
    }


    /**
     * 获得类的基路径，打成jar包也可以正确获得路径
     *
     * @return
     */
    public static String getBasePath() {
        /*
         * /D:/zhao/Documents/NetBeansProjects/docCompare/build/classes/
         * /D:/zhao/Documents/NetBeansProjects/docCompare/dist/bundles/
         * docCompare/app/docCompare.jar
         */

        String filePath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        if (filePath.endsWith(".jar")) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            try {
                // 解决路径中有空格%20的问题
                filePath = URLDecoder.decode(filePath, "UTF-8");
            } catch (UnsupportedEncodingException ex) {

            }

        }
        File file = new File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBasePath());
    }


    /**
     * 删除指定文件夹下所有文件
     * param path 文件夹完整绝对路径
     *
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                //再删除空文件夹
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 删除文件夹
     * param folderPath 文件夹完整绝对路径
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            //删除完里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            //删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}