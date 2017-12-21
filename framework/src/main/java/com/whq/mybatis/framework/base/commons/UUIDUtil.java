package com.whq.mybatis.framework.base.commons;

import java.util.UUID;

/**
 * Created by zhonghua.zhi
 * Created by 2016/12/6 - 下午3:14.
 * ----------------------------------
 * Project Name nais
 * Package Name com.boco.nais.framework.base.commons
 */
public final class UUIDUtil {

    /**
     * 生成UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}