# 单元测试前提条件
 首先需要在本地对模块framework  install操作
## 配置文件修改
修改数据库配置（文件名：syscfg.properties）

## 数据库建表
    -- Create table
    create table TAI_INAS_IPS_NA_NE
    (
      IPSNE_ID      INTEGER not null,
      NE_TYPE       VARCHAR2(32),
      NE_NAME       VARCHAR2(50),
      NE_DESC       VARCHAR2(255),
      NE_LOGIN_NAME VARCHAR2(50),
      NE_LOGIN_PWD  VARCHAR2(100),
      NE_ALIAS      VARCHAR2(50),
      NE_IP         VARCHAR2(50),
      NE_PORT       VARCHAR2(20)
    );
    -- Create/Recreate primary, unique and foreign key constraints
    alter table TAI_INAS_IPS_NA_NE
      add constraint XPK_IPS_NA_NE primary key (IPSNE_ID);

