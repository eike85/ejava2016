drop schema if exists authdb;

create schema authdb;

use authdb;

create table users (
	userid varchar(32) not null,
	password varchar(128) not null,
	primary key (userid)
) engine=InnoDB default charset=utf8;

create table groups (
	groupid varchar(32) not null,
	userid varchar(32) not null,
	primary key (groupid, userid)
) engine=InnoDB default charset=utf8;


SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `notes`
-- ----------------------------
DROP TABLE IF EXISTS notes;
CREATE TABLE notes (
  note_id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(80) NOT NULL,
  category varchar(40) NOT NULL,
  content varchar(200) DEFAULT NULL,
  userid varchar(32) NOT NULL,
  posted_datetime datetime NOT NULL,
  PRIMARY KEY (note_id),
  KEY userid_userid (userid),
  CONSTRAINT userid_userid FOREIGN KEY (userid) REFERENCES users (userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into users values ('user1', SHA2('123', 256));
insert into groups values ('USER_ROLE', 'user1');
/*
	Create jdbcRealm
	Add jdbc connection pool and jdbc resource
	Assume jdbc resource is jdbc/authdb

	# realm name referenced in web.xml
	name: authdb-realm 
	# hard coded to jdbcRealm
	JAAS context: jdbcRealm (must be this)
	JNDI: jdbc/authdb
	User Table: users
	User Name Column: userid
	Password Column: password
	Group Table: groups
	Group Table user Name Column: userid
	Group Name Column: groupid
	# Cannot find the use of this in the source code.
	# Need to have a value, enter NONE
	Password Encryption Algorithm: AES
	# digest is use to hash password, user the same digest before updating password
	Digest Algorithm: SHA-256 
	Encoding: Hex
*/

-- ----------------------------
-- Records of notes
-- ----------------------------
