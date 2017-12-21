package com.whq.mybatis.framework.base.commons;

import com.whq.mybatis.framework.base.commons.crypto.DESCoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhonghua.zhi
 * Created by 2016/12/6 - 下午3:27.
 * ----------------------------------
 * Project Name nais
 * Package Name com.boco.nais.framework.base.commons
 */
public class SysProperty extends PropertyPlaceholderConfigurer {

    public static final String ATTR_FILL_RULES = "attr_fill_rules";
    public static final String CONTEXT_PATH = "CONTEXT_PATH";
    public static final String BOOLEAN_TRUE = "1";
    public static final String BOOLEAN_FALSE = "2";
    public static final String DESCODER_KEY = "BOCO_ENCRYPT_PROPERTIES";

    private static SysProperty instance = new SysProperty();

    public static SysProperty getInstance() {
        return instance;
    }

    private Properties prop;

    /**
     * @param beanFactory
     * @throws BeansException
     */
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {

        try {
            prop = mergeProperties();

            // Convert the merged properties, if necessary.
            convertProperties(prop);

            // Let the subclass process the properties.
            processProperties(beanFactory, prop);

        } catch (IOException ex) {
            throw new BeanInitializationException("Could not load properties",
                    ex);
        }
    }

    /**
     * 私密字段在配置文件中做了加密处理，这里进行解密
     *
     * @author lonjack 20121031
     */
    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {

        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            if (StringUtils.isNotBlank(keyStr)) {
                if (keyStr.startsWith("encrypt.prop")) {
                    try {
                        String tempValue = props.getProperty(keyStr);
                        byte[] outputData = DESCoder.decrypt(DESCoder
                                .decryptBASE64(tempValue), DESCODER_KEY);
                        String value = new String(outputData);
                        props.setProperty(keyStr, value);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        super.processProperties(beanFactoryToProcess, props);
    }

    public void setValue(String key, String value) {
        instance.prop.put(key, value);
    }

    private Map<String, Resource> files;

    public void setFiles(Map<String, Resource> files) {
        this.files = files;
    }

    public String getValue(String key) {
        return instance.prop.getProperty(key);
    }

    public String getValue(String key, String defaultValue) {
        return instance.prop.getProperty(key, defaultValue);
    }

    /**
     * @param key
     * @return
     */
    public String getFilePath(String key) {
        try {
            return this.files.get(key).getFile().getPath();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param key
     * @return
     */
    public File getFile(String key) {
        try {
            return this.files.get(key).getFile();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Resource getResource(String key) {
        return this.files.get(key);
    }
}