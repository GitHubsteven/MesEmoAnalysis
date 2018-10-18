```text
create DATABASE mes_emo_ana;

CREATE TABLE mes_emo_ana.account(
  id INT  AUTO_INCREMENT NOT NULL COMMENT 'id',
  name VARCHAR(50) NOT NULL COMMENT '名称',
  password VARCHAR(100) NOT NULL COMMENT '密码',
  created TIMESTAMP NOT NULL DEFAULT now() COMMENT '创建日期',
  creator VARCHAR(50) NOT NULL  COMMENT '创建人',
  modified TIMESTAMP NOT NULL NULL DEFAULT now() COMMENT '修改日期',
  modifier VARCHAR(50) NOT NULL COMMENT '修改人',
  PRIMARY KEY (id)
);
```
