drop table if exists wx_account;
drop table if exists wx_user;
drop table if exists gm_user;
drop table if exists gm_room;
drop table if exists gm_game;
drop table if exists gm_action;
drop table if exists gm_user_room;
drop table if exists gm_money_detail;
drop table if exists gm_advice;
drop table if exists gm_order;
drop table if exists gm_dict;




CREATE TABLE wx_account (  
  id int NOT NULL AUTO_INCREMENT,
  account varchar(128) NOT NULL UNIQUE , 
  appid varchar(128) NOT NULL UNIQUE,  
  secret varchar(128) NOT NULL,  
  name varchar(32) NOT NULL , 
  account_type char(1) NOT NULL,  
  token varchar(64) ,
  keyinfo varchar(64) ,
  access_token varchar(256),  
  token_expire_time datetime,
  ticket varchar(256),
  ticket_expire_time datetime,
  asyn_flag char(1) NOT NULL ,  
  xiaoi_channel varchar(32) ,
  description varchar(64),
	mach_id varchar(64),
	mach_name varchar(64),
	pay_secret varchar(64),
  remark varchar(64),
	PRIMARY KEY (id)  
) ENGINE=InnoDB ;

CREATE TABLE wx_user (  
  id int NOT NULL AUTO_INCREMENT,
  appid varchar(128)  ,  
	subscribe char(1),
	openid varchar(128) ,
	nickname varchar(128),
	sex varchar(128) ,
	language varchar(128) ,
	city varchar(128) ,
	province varchar(128) ,
	country varchar(128) ,
	headimgurl varchar(1024) ,
	subscribe_time1 datetime,
	unionid varchar(128) ,
	remark varchar(128) ,
	groupid varchar(128) ,
	tagid_list varchar(128) ,
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;

create table gm_user (
	id int NOT NULL AUTO_INCREMENT,
	openid varchar(128),
	win int ,
	lose int,
  ping int,
  score int,
  money int,
	recommend tinyint(1),
	rec_room int,
  rec_money int,
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;

CREATE TABLE gm_room (  
  id int NOT NULL AUTO_INCREMENT,
  roomid varchar(128)  ,
	create_account varchar(128),
	create_time datetime,
	end_time datetime,
  users varchar(2048),
	room_info varchar(2048),
  recommend varchar(256),
	name varchar(32),
  total_ju int,
  player_num int ,
  end_reason varchar(1024),
	end_ju int ,
  time_range int,
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;

CREATE TABLE gm_game (  
  id int NOT NULL AUTO_INCREMENT,
	create_time datetime,
	users varchar(2048),
	end_time datetime,
	game_info varchar(1024),
  roomid int, 
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;

CREATE TABLE gm_action (  
  id int NOT NULL AUTO_INCREMENT,
	account varchar(128),
	action int ,
	cards varchar(1024),
  useraction tinyint(1),
  attribute varchar(10240),
	actionindex int,
  gameid int,
	action_time datetime,
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;


CREATE TABLE gm_user_room (  
  id int NOT NULL AUTO_INCREMENT,
  account varchar(128),
	roomid int not null,
	overtime datetime,
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;



create table gm_money_detail (
	id int NOT NULL AUTO_INCREMENT,
	openid varchar(128),
	money int ,
	action varchar(2), 
	remark varchar(128),
	relate bigint ,
	time datetime,
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;


create table gm_advice (
	id int NOT NULL AUTO_INCREMENT,
	openid varchar(128),
	type varchar(256),
	game_type varchar(32),
	advice varchar(1024),
	name varchar(32),
	phone varchar(32),
	time datetime,
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;


create table gm_dict (
	id int NOT NULL AUTO_INCREMENT,
	dkey varchar(32) UNIQUE,
	dvalue varchar(256),
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;







CREATE TABLE gm_order (  
  id int NOT NULL AUTO_INCREMENT,
  appid varchar(128)  ,  
  mch_id varchar(128) ,  
  device_info varchar(128)  , 
	openid varchar(128) ,  
	is_subscribe CHAR(1),
	trade_type varchar(128) ,  
	bank_type varchar(128),  
	total_fee varchar(128) ,  
	settlement_total_fee varchar(128) ,
	fee_type varchar(128) ,  
	cash_fee varchar(128),  
  cash_fee_type  varchar(128),
	coupon_fee  varchar(128),
	coupon_count  varchar(128),
  coupon_type_$n  varchar(128),
  coupon_id_$n  varchar(128),
	coupon_fee_$n varchar(128),
	transaction_id varchar(128)NOT NULL UNIQUE,
	out_trade_no varchar(128) NOT NULL UNIQUE,
	attach varchar(1024),
	time_end varchar(32),
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;












ALTER TABLE wx_user ADD UNIQUE (appid,openid);
ALTER TABLE gm_room ADD INDEX (roomid);
ALTER TABLE gm_game ADD INDEX (roomid);
ALTER TABLE gm_user ADD UNIQUE (openid);
ALTER TABLE gm_advice ADD INDEX (openid);
ALTER TABLE gm_money_detail ADD INDEX (openid);
ALTER TABLE gm_user_room ADD INDEX (account);
ALTER TABLE gm_action ADD INDEX (gameid);


ALTER TABLE wx_user MODIFY `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE wx_user  CHARSET=utf8mb4 ;



ALTER TABLE gm_room modify column recommend varchar(32);
ALTER TABLE gm_room ADD INDEX (recommend);


alter table gm_room add column cost_money int;

ALTER TABLE gm_room modify column create_account varchar(32);
ALTER TABLE gm_room ADD INDEX (create_account);


ALTER TABLE gm_money_detail ADD INDEX (openid);
ALTER TABLE gm_money_detail modify  column relate varchar(32);
ALTER TABLE gm_money_detail ADD INDEX (relate);


-- 2018-04-28
drop table if exists gm_win_lose;
create table gm_win_lose (
	id int NOT NULL AUTO_INCREMENT,
	openid varchar(128) NOT NULL UNIQUE,
	winlose char,
	ratio int, 
	PRIMARY KEY (id) 
)ENGINE=InnoDB ;


