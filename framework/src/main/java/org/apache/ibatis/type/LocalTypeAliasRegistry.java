package org.apache.ibatis.type;

/**
 * Created by zhonghua.zhi
 * Created by 2016/12/8 - 上午10:00.
 * ----------------------------------
 * Project Name nais
 * Package Name org.apache.ibatis.type
 */
public class LocalTypeAliasRegistry extends TypeAliasRegistry {

    @Override
    public void registerAlias(Class<?> type) {
        String alias = type.getName();
        Alias aliasAnnotation = type.getAnnotation(Alias.class);
        if (aliasAnnotation != null) {
            alias = aliasAnnotation.value();
        }
        registerAlias(alias, type);
    }
}