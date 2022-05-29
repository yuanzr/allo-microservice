package com.dc.allo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liuguofu on 2017/6/29.
 */
public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;

    static {
        loadProps();
    }
    public static void main(String args[]){
        String value=getProperty("API_PROTOCAL");
        System.out.println(value);
    }

    synchronized static public void loadProps() {
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
//            String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
//            logger.info("loadProps path:{}",path);
//            in=new FileInputStream(path);

            //兼容处理jar中的config.properties文件
            in = PropertyUtil.class.getResourceAsStream("/config.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("config.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("config.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key) {
        if (props.isEmpty()) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
