package com.whq.mybatis.customsql.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mybatis.spring.LocalSqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author: wanghuanqing
 * @date: Create in 2017/12/24 19:30
 * @desc: 自定义sql测试，动态加载mapper.xml文件测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestSelfControlSql2 {
    Logger logger = LoggerFactory.getLogger(TestSelfControlSql2.class);

    @Autowired
    private LocalSqlSessionFactoryBean localSqlSessionFactoryBean;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public static Map<String,String> map = new HashMap<String,String>();
    //执行sql代理接口
    public final String SQL_NAMESPACE = "com.whq.mybatis.customsql.dao.SelfControlSqlMapper.selfControlSqlMapper1";

    /**
     * @author: wanghuanqing
     * @date: 2017/12/24 19:37
     * @desc: 动态加载mapperXML配置文件
     * @param
     * @return void
     */
    @Before
    public void loadSelfMapperXMLFile(){
       InputStream sqlCfg = TestSelfControlSql2.class.getResourceAsStream("/test-sql.xml");
        try {
            JAXBContext context = JAXBContext.newInstance(TestSql.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            TestSql testSql = (TestSql) unmarshaller.unmarshal(sqlCfg);
            //查询获取到的sql
            List<String> sqlList = testSql.getSql();

            for (String sql : sqlList
                 ) {
                logger.info(sql);
                //uuid为mapperXML执行sql节点ID值
                map.put(UUID.randomUUID().toString().replace("-", ""),sql);
            }

            StringBuffer mapperXML = makeMapperXMLFile(map);

            logger.info("生成apperXML文件成功：\r\n"+mapperXML);

            //mybatis框架加载自定义mapperXML文件
            localSqlSessionFactoryBean.loadMapper(new ByteArrayInputStream(mapperXML.toString().getBytes("UTF-8")));

        } catch (JAXBException e) {
            e.printStackTrace();
            logger.error("解析sql配置文件异常！");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * @author: wanghuanqing
     * @date: 2017/12/24 19:38
     * @desc: 执行自定义mapperXML中的sql
     * @param
     * @return void
     */
    @org.junit.Test
    public void testSelfControlSql2(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过设置的uuid进行sql查询
        Set<String> uuidKey = map.keySet();
        Iterator<String> uuidIt = uuidKey.iterator();
        while (uuidIt.hasNext()){
            String key = uuidIt.next();
            //执行sql
            List<Map<String,Object>> queryRes = sqlSession.selectList(SQL_NAMESPACE+"."+key);
            Iterator<Map<String,Object>> it = queryRes.iterator();
            while (it.hasNext()){
                Map<String,Object> map = it.next();
                Set<String> set = map.keySet();
                for (String s : set) {
                    //打印自定义sql查询结果
                    logger.info("key:"+s);
                    logger.info("value:"+map.get(s));
                }
            }

            logger.info("sql执行完成。");
        }
    }
    /**
     * @author: wanghuanqing
     * @date: 2017/12/24 21:51
     * @desc: 构造mapperXML文件
     * @param map
     * @return java.lang.String
     */
    public StringBuffer makeMapperXMLFile(Map<String,String> map){
        StringBuffer tempXmlString = new StringBuffer();
       //mapperXML文件头
        final String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" " +
                "\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">" +
                "\n<mapper namespace=\"" + SQL_NAMESPACE + "\">\n";
        //mapperXML文件尾部
        final String postfix = "</mapper>";

        tempXmlString.append(prefix);
        Set<String> uuidKey = map.keySet();
        Iterator<String> uuidIt = uuidKey.iterator();
        while(uuidIt.hasNext()){
            String key = uuidIt.next();
            tempXmlString.append("	<select id=\"");
            tempXmlString.append(key);
            tempXmlString.append("\" parameterType=\"java.util.Map\" resultType=\"java.util.HashMap\">");
            tempXmlString.append("\n<![CDATA[");
            tempXmlString.append(map.get(key).trim());
            tempXmlString.append("]]>\n");
            tempXmlString.append("	</select>\n");
        }
        tempXmlString.append(postfix);

        return tempXmlString;
    }

    //sql配置文件实体类
    @XmlRootElement
    static class TestSql{
        private List<String> sql;


        public List<String> getSql() {
            return sql;
        }

        public void setSql(List<String> sql) {
            this.sql = sql;
        }
    }
}
