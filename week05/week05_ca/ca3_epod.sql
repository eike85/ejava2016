create table ack (
	ack_id varchar(16) not null primary key,
	team_id varchar(10) not null,
	pod_id integer not null,
	img_path varchar(512) not null
);
