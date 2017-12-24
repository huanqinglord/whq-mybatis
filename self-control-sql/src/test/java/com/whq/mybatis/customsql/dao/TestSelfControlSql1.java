package com.whq.mybatis.customsql.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @author: wanghuanqing
 * @date: Create in 2017/12/24 19:13
 * @desc: 自定义sql测试，写死mapper.xml文件测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestSelfControlSql1 {
    Logger logger = LoggerFactory.getLogger(TestSelfControlSql1.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @org.junit.Test
    public void testSelfControlSql1(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        list = sqlSession.selectList("com.whq.mybatis.customsql.dao.SelfControlSqlMapper.selfControlSqlMapper1");

        Iterator<Map<String,Object>> it = list.iterator();
        while (it.hasNext()){
            Map<String,Object> map = it.next();
            Set<String> set = map.keySet();
            for (String s : set) {
                //打印自定义sql查询结果
                logger.info("key:"+s);
                logger.info("value:"+map.get(s));
            }
        }
    }
}
