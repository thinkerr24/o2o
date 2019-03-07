CREATE DATABASE o2o  DEFAULT CHARSET utf8;

USE o2o;

DROP TABLE  IF EXISTS tb_area;
CREATE TABLE tb_area (
	area_id int(2) NOT NULL AUTO_INCREMENT,
	area_name varchar(200) NOT NULL,
	priority int(2) NOT NULL DEFAULT 0,
	create_time datetime DEFAULT NULL,
	last_edit_time datetime DEFAULT NULL,
	primary key (area_id),
	unique key UK_AREA (area_name)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE  IF EXISTS tb_person_info;
CREATE TABLE tb_person_info(
	user_id int(10) NOT NULL AUTO_INCREMENT,
	name varchar(32) DEFAULT NULL,
	profile_img varchar(1024) DEFAULT NULL,
	email varchar(1024) DEFAULT NULL,
	gender varchar(2) DEFAULT NULL,
	enable_status int(2) NOT NULL DEFAULT '0' COMMENT '0:Permission  deny, 1: can use',
	user_type int(2) NOT NULL DEFAULT '1' COMMENT '1:Customer, 2:Seller, 3:Administer',
	create_time datetime DEFAULT NULL,
	last_edit_time datetime DEFAULT NULL,
	primary key(user_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_wechat_auth;
CREATE TABLE tb_wechat_auth(
	wechat_auth_id int(10) NOT NULL AUTO_INCREMENT,
	user_id int(10) NOT NULL,
	open_id varchar(255) NOT NULL,
	create_time datetime DEFAULT NULL,
	primary key(wechat_auth_id),
	unique index(open_id),
	constraint fk_wechatauth_profile foreign key(user_id) references tb_person_info(user_id)
)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS tb_local_auth;
CREATE TABLE tb_local_auth(
	local_auth_id int(10) NOT NULL AUTO_INCREMENT,
	user_id int(10) NOT NULL,
	username varchar(128) NOT NULL,
	password varchar(128) NOT NULL,
	create_time datetime DEFAULT NULL,
	last_edit_time datetime DEFAULT NULL,
	PRIMARY KEY(local_auth_id),
	UNIQUE KEY(username),
	constraint fk_localauth_profile foreign key(user_id) references tb_person_info(user_id)
)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS tb_head_line;
CREATE TABLE tb_head_line(
	line_id int(100) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	line_name varchar(1000) DEFAULT NULL,
	line_link varchar(2000) NOT NULL,
	line_img  varchar(2000) NOT NULL,
	priority int(2) DEFAULT NULL,
	enable_status int(2) NOT NULL DEFAULT '0',
	create_time datetime DEFAULT NULL,
	last_edit_time datetime DEFAULT NULL

)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS tb_shop_category;
CREATE TABLE tb_shop_category(
	shop_category_id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	shop_category_name varchar(100) NOT NULL DEFAULT '',
	shop_category_desc varchar(1000) DEFAULT '',
	shop_category_img varchar(2000) DEFAULT NULL,
	priority int(2) NOT NULL DEFAULT '0',
	create_time datetime DEFAULT NULL,
	last_edit_time datetime DEFAULT NULL,
	parent_id int(11) DEFAULT NULL,
	constraint fk_shopcategory_self foreign key(parent_id) references tb_shop_category(shop_category_id)

)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS tb_shop;
CREATE TABLE tb_shop(
	shop_id int(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	owner_id int(10) NOT NULL COMMENT 'shop founder',
	area_id int(5) DEFAULT NULL,
	shop_category_id int(11) DEFAULT NULL,
	shop_name varchar(256) NOT NULL,
	shop_desc varchar(1024) DEFAULT NULL,
	shop_addr varchar(200) DEFAULT NULL,
	phone varchar(128) DEFAULT NULL,
	shop_img varchar(1024) DEFAULT NULL,
	priority int(3) DEFAULT '0',
	create_time datetime DEFAULT NULL,
	last_edit_time datetime DEFAULT NULL,
	enable_status int(2) NOT NULL DEFAULT '0',
	advice varchar(255) DEFAULT NULL,
	constraint fk_shop_area foreign key(area_id) references tb_area(area_id),
	constraint fk_shop_profile foreign key(owner_id) references tb_person_info(user_id),
	constraint fk_shop_shopcategory foreign key(shop_category_id) references tb_shop_category(shop_category_id)

)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS tb_product_category;
CREATE TABLE tb_product_category(
	product_category_id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	product_category_name varchar(100) NOT NULL,
	priority int(2) DEFAULT '0',
	create_time datetime DEFAULT NULL,
	shop_id int(20) NOT NULL DEFAULT '0',
	CONSTRAINT fk_procate_shop foreign key(shop_id) references tb_shop(shop_id)

)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS tb_product;
CREATE TABLE tb_product(
	product_id int(100) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	product_name varchar(100) NOT NULL,
	product_desc varchar(2000) DEFAULT NULL,
	img_addr varchar(2000) DEFAULT NULL,
	nomal_price varchar(100) DEFAULT NULL,
	promotion_price varchar(100) DEFAULT NULL,
	priority int(2) NOT NULL DEFAULT '0',
	create_time datetime DEFAULT NULL,
	last_edit_time datetime DEFAULT NULL,
	enable_status int(2) NOT NULL DEFAULT '0',
	product_category_id int(11) DEFAULT NULL,
	shop_id int(20) NOT NULL DEFAULT '0',
	CONSTRAINT fk_product_procate foreign key(product_category_id) references tb_product_category(product_category_id),
	CONSTRAINT fk_product_shop foreign key(shop_id) references tb_shop(shop_id)

)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS tb_product_img;
CREATE TABLE tb_product_img(
	product_img_id int(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	img_addr varchar(2000) NOT NULL,
	img_desc varchar(2000) DEFAULT NULL,
	priority int(2) DEFAULT '0',
	create_time datetime DEFAULT NULL,
	product_id int(20) DEFAULT NULL,
	CONSTRAINT fk_proimg_product foreign key(product_id) references tb_product(product_id)

)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

// tb_area test data
/* if truncate table with foreign key
SET FOREIGN_KEY_CHECKS=0; 取消外键约束
TRUNCATE tb_area;
SET FOREIGN_KEY_CHECKS=1; 设置外键约束
*/
INSERT tb_area(area_name, priority) VALUES('南苑', 1), ('北苑', 2);

INSERT tb_person_info VALUES(NULL, '测试', 'test', 'test@test.com', '1', 1, 2, NULL, NULL);
-- INSERT tb_shop_category VALUES(1, '咖啡奶茶', 'test desc', 'test.img', 1, NULL, NULL, NULL);
-- 4-11 
INSERT tb_shop_category VALUES(2,'蜜雪冰城', 'test2 desc', 'test2.img', 0, NULL, NULL, 1);