package com.whq.mybatis.framework.base.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhonghua.zhi
 * Created by 2016/12/6 - 下午3:22.
 * ----------------------------------
 * Project Name nais
 * Package Name com.boco.nais.framework.base.commons
 */
public class DateUtil {

    private static final String DEFAULT_TEMP = "yyyy-MM-dd HH:mm:ss";

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * @param timeStr
     * @return
     * @throws ParseException
     */
    public static Date getSystime(String timeStr) {

        DateFormat df = new SimpleDateFormat(DEFAULT_TEMP);
        try {
            return df.parse(timeStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取系统当前时间
     *
     * @param dateFormat 时间格式化样式
     * @return
     */
    public static String getSysDate(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static String format(Date date) {
        return new SimpleDateFormat(DEFAULT_TEMP).format(date);
    }
}