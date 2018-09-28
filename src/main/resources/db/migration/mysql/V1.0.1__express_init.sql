CREATE TABLE ex_customer (
  id      INT(11)      NOT NULL AUTO_INCREMENT,
  name    VARCHAR(32)  NULL,
  phone   VARCHAR(32)  NULL,
  address VARCHAR(255) NULL,
  PRIMARY KEY (id),
  KEY (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE ex_company (
  id      INT(11)      NOT NULL AUTO_INCREMENT,
  name    VARCHAR(32)  NULL,
  phone   VARCHAR(32)  NULL,
  address VARCHAR(255) NULL,
  PRIMARY KEY (id),
  KEY (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;


CREATE TABLE ex_waybill (
  id          INT(11)     NOT NULL AUTO_INCREMENT,
  customer_id INT(11)     NULL,
  company_id INT(11)     NULL,
  from_city   VARCHAR(32) NULL,
  to_city     VARCHAR(32) NULL,
  number      VARCHAR(32) NULL,
  process_instance_id int(11) NULL,
  PRIMARY KEY (id),
  KEY (customer_id),
  KEY (number)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;








