package org.apache.ibatis.session;

import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.decorators.WeakCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.datasource.jndi.JndiDataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.executor.loader.CglibProxyFactory;
import org.apache.ibatis.executor.loader.JavassistProxyFactory;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.commons.JakartaCommonsLoggingImpl;
import org.apache.ibatis.logging.jdk14.Jdk14LoggingImpl;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.ibatis.type.LocalTypeAliasRegistry;

import java.lang.reflect.Field;

/**
 * Created by zhonghua.zhi
 * Created by 2016/12/8 - 上午9:59.
 * ----------------------------------
 * Project Name nais
 * Package Name org.apache.ibatis.session
 */
public class LocalConfiguration extends Configuration {

    private static final Log logger = LogFactory.getLog(LocalConfiguration.class);

    private LocalTypeAliasRegistry localTypeAliasRegistry = new LocalTypeAliasRegistry();

    {
        Field field;
        try {
            field = this.getClass().getSuperclass().getDeclaredField("typeAliasRegistry");
            field.setAccessible(true);
            field.set(this, localTypeAliasRegistry);
        } catch (SecurityException e) {
            logger.error("", e);
        } catch (NoSuchFieldException e) {
            logger.error("", e);
        } catch (IllegalArgumentException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        }
    }

    public LocalConfiguration() {

        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class.getName());
        typeAliasRegistry.registerAlias("MANAGED", ManagedTransactionFactory.class.getName());
        typeAliasRegistry.registerAlias("JNDI", JndiDataSourceFactory.class.getName());
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class.getName());
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class.getName());

        typeAliasRegistry.registerAlias("PERPETUAL", PerpetualCache.class.getName());
        typeAliasRegistry.registerAlias("FIFO", FifoCache.class.getName());
        typeAliasRegistry.registerAlias("LRU", LruCache.class.getName());
        typeAliasRegistry.registerAlias("SOFT", SoftCache.class.getName());
        typeAliasRegistry.registerAlias("WEAK", WeakCache.class.getName());

        typeAliasRegistry.registerAlias("VENDOR", VendorDatabaseIdProvider.class.getName());

        typeAliasRegistry.registerAlias("XML", XMLLanguageDriver.class.getName());
        typeAliasRegistry.registerAlias("RAW", RawLanguageDriver.class.getName());

        typeAliasRegistry.registerAlias("SLF4J", Slf4jImpl.class.getName());
        typeAliasRegistry.registerAlias("COMMONS_LOGGING", JakartaCommonsLoggingImpl.class.getName());
        typeAliasRegistry.registerAlias("LOG4J", Log4jImpl.class.getName());
        typeAliasRegistry.registerAlias("JDK_LOGGING", Jdk14LoggingImpl.class.getName());
        typeAliasRegistry.registerAlias("STDOUT_LOGGING", StdOutImpl.class.getName());
        typeAliasRegistry.registerAlias("NO_LOGGING", NoLoggingImpl.class.getName());

        typeAliasRegistry.registerAlias("CGLIB", CglibProxyFactory.class.getName());
        typeAliasRegistry.registerAlias("JAVASSIST", JavassistProxyFactory.class.getName());

    }
}