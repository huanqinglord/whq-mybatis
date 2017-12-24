package com.whq.mybatis.customsql.dao;

import com.whq.mybatis.customsql.entity.TaiInasIpsNaNe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author: wanghuanqing
 * @date: Create in 2017/12/21 17:04
 * @desc: 测试是否可以连接数据库
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestTaiInasIpsNaNeMapper {
    Logger logger = LoggerFactory.getLogger(TestTaiInasIpsNaNeMapper.class);

    @Autowired
    private TaiInasIpsNaNeMapper taiInasIpsNaNeMapper;

    @Test
    public void sqlOperateTest(){
        BigDecimal bigDecimal = new BigDecimal(9999);
        TaiInasIpsNaNe taiInasIpsNaNe = new TaiInasIpsNaNe();
        taiInasIpsNaNe.setIpsneId(bigDecimal);
        taiInasIpsNaNe.setNeAlias("test");

        try {
            taiInasIpsNaNeMapper.insert(taiInasIpsNaNe);
        } catch (DuplicateKeyException e) {
            //主键冲突异常，先删除数据
            taiInasIpsNaNeMapper.deleteByPrimaryKey(bigDecimal);
            //重新入库
            taiInasIpsNaNeMapper.insert(taiInasIpsNaNe);
        }

        logger.info("数据入库成功");

        TaiInasIpsNaNe taiInasIpsNaNeSelect = taiInasIpsNaNeMapper.selectByPrimaryKey(bigDecimal);
        Assert.assertEquals("test",taiInasIpsNaNeSelect.getNeAlias());
        logger.info("查询入库数据成功");

        taiInasIpsNaNeMapper.deleteByPrimaryKey(bigDecimal);
        logger.info("数据删除成功");

    }

}
