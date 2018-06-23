CREATE TABLE sb_user_product (
  user_id varchar(40) NOT NULL,
  product_name varchar(30) NOT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sb_user (
  id varchar(40) NOT NULL,
  union_id varchar(60) DEFAULT NULL,
  name varchar(30) DEFAULT NULL,
  phone_number varchar(30) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sb_trans (
  name varchar(100) NOT NULL,
  product_name varchar(30) NOT NULL,
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sb_surfboard_operation_log (
  id varchar(40) NOT NULL,
  create_time datetime NOT NULL,
  type varchar(10) NOT NULL,
  user_name varchar(30) NOT NULL,
  surfboard_id varchar(40) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sb_surfboard (
  id varchar(40) NOT NULL,
  status varchar(2) NOT NULL,
  create_time datetime NOT NULL,
  update_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sb_product (
  name varchar(30) NOT NULL,
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

