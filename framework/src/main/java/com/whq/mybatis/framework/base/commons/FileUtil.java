package com.whq.mybatis.framework.base.commons;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhonghua.zhi
 * Created by 2016/12/6 - 下午3:08.
 * ----------------------------------
 * Project Name nais
 * Package Name com.boco.nais.framework.base.commons
 */
public final class FileUtil {

    private static final String DEFAULT_ENCOD = "UTF-8";

    /**
     * 读文件
     *
     * @param file 文件对象
     * @return
     * @throws IOException
     */
    public static String readFile(File file) throws IOException {
        return readFile(file, DEFAULT_ENCOD);
    }

    /**
     * @param filePath
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFile(String filePath, String encoding) throws IOException {
        return readFile(new File(filePath), encoding);
    }

    /**
     * 读文件
     *
     * @param file     文件对象
     * @param encoding 字符集
     * @return
     * @throws IOException
     */
    public static String readFile(File file, String encoding) throws IOException {
        return FileUtils.readFileToString(file, encoding);
    }

    /**
     * 写文件
     *
     * @param file    文件对象
     * @param message
     * @throws IOException
     */
    public static void writeStrToFile(File file, String message) throws IOException {
        FileUtils.writeStringToFile(file, message, DEFAULT_ENCOD);
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static boolean contains(String filePath) throws IOException {
        return contains(new File(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件路径
     * @return
     * @throws IOException
     */
    public static boolean contains(File file) throws IOException {
        return file.exists();
    }
}