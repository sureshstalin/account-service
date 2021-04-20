CREATE TABLE user (
  id bigint NOT NULL AUTO_INCREMENT,
  is_deleted bit(1) NOT NULL,
  email_id varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  middle_name varchar(255) DEFAULT NULL,
  mobile_no varchar(255) NOT NULL,
  password varchar(10) NOT NULL,
  user_type varchar(10) NOT NULL,
  date_created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_modified datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE address (
  id bigint NOT NULL AUTO_INCREMENT,
  is_deleted bit(1) DEFAULT NULL,
  address1 varchar(255) DEFAULT NULL,
  address2 varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  country varchar(255) DEFAULT NULL,
  landmark varchar(255) DEFAULT NULL,
  mobile varchar(255) DEFAULT NULL,
  state varchar(255) DEFAULT NULL,
  user_id bigint DEFAULT NULL,
  date_created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_modified datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY address_fk_user (user_id),
  CONSTRAINT address_fk_user FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB;

CREATE TABLE customer (
  id bigint NOT NULL AUTO_INCREMENT,
  is_deleted bit(1) DEFAULT NULL,
  customer_code varchar(255) NOT NULL,
  full_name varchar(255) DEFAULT NULL,
  user_id bigint DEFAULT NULL,
  date_created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_modified datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX customer_code_UNIQUE (customer_code ASC) VISIBLE,
  KEY customer_fk_user (user_id),
  CONSTRAINT customer_fk_user FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB;

CREATE TABLE employee (
  id bigint NOT NULL AUTO_INCREMENT,
  is_deleted bit(1) DEFAULT NULL,
  employee_code varchar(255) NOT NULL,
  full_name varchar(255) NOT NULL,
  user_id bigint DEFAULT NULL,
  date_created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_modified datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX employee_code_UNIQUE (employee_code ASC) VISIBLE,
  KEY employee_fk_user (user_id),
  CONSTRAINT employee_fk_user FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB;

CREATE TABLE organization (
  id bigint NOT NULL AUTO_INCREMENT,
  is_deleted bit(1) DEFAULT NULL,
  org_code varchar(255) NOT NULL,
  org_name varchar(255) NOT NULL,
  user_id bigint NOT NULL,
  date_created datetime DEFAULT CURRENT_TIMESTAMP,
  date_modified datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX org_code_UNIQUE (org_code ASC) VISIBLE,
  UNIQUE INDEX org_name_UNIQUE (org_name ASC) VISIBLE,
  KEY ogranization_fk_user (user_id),
  CONSTRAINT ogranization_fk_user FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB;

CREATE TABLE role (
  id bigint NOT NULL AUTO_INCREMENT,
  is_deleted bit(1) DEFAULT NULL,
  role_description varchar(255) DEFAULT NULL,
  role_name varchar(255) DEFAULT NULL,
  date_created datetime DEFAULT CURRENT_TIMESTAMP,
  date_modified datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX role_name_UNIQUE (role_name ASC) VISIBLE
) ENGINE=InnoDB;

CREATE TABLE vendor (
  id bigint NOT NULL AUTO_INCREMENT,
  is_deleted bit(1) DEFAULT NULL,
  full_name varchar(255) NOT NULL,
  vendor_code varchar(255) NOT NULL,
  user_id bigint DEFAULT NULL,
  vendor_id bigint DEFAULT NULL,
  date_created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_modified datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX vendor_code_UNIQUE (vendor_code ASC) VISIBLE,
  KEY vendor_fk_user (user_id),
  CONSTRAINT vendor_fk_user FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB;

CREATE TABLE app_entity_code (
  id BIGINT NOT NULL AUTO_INCREMENT,
  code VARCHAR(10) NOT NULL,
  code_type VARCHAR(20) NOT NULL,
  date_created DATETIME NOT NULL,
  date_modified DATETIME NULL,
  is_deleted TINYINT NULL,
  UNIQUE INDEX code_UNIQUE (code ASC) VISIBLE,
  PRIMARY KEY (id));

  CREATE TABLE system_codes (
    id int NOT NULL AUTO_INCREMENT,
    code_prefix varchar(5) NOT NULL,
    code_type varchar(20) DEFAULT NULL,
    is_deleted bit(1) DEFAULT 0,
    date_created datetime DEFAULT CURRENT_TIMESTAMP,
    date_modified datetime DEFAULT NULL,
    PRIMARY KEY (id)
  ) ENGINE=InnoDB;

  CREATE TABLE jwt_token (
    id INT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    access_token VARCHAR(250) NOT NULL,
    refresh_token VARCHAR(250) NOT NULL,
    access_token_expiration DATETIME NOT NULL,
    refresh_token_expiration DATETIME NOT NULL,
    date_created DATETIME NULL,
    date_modified DATETIME NULL,
    PRIMARY KEY (id));
