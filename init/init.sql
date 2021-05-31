#drop database if exists blog;

#create database blog;

use blog;
# spring security 自带的记住我需要的表
drop table if exists persistent_logins;
create table persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
)charset utf8mb4 engine=InnoDB;

# 创建一个管理员和用户账户
insert into
    users
(user_id, can_edit, create_time, is_deleted,update_time, avatar_file_mark_id, description, email, nickname,password, status, username)
values
(1,1,now(),0,now(),null,null,'test@email.com','管理员',
 '$2a$10$5sxBIC594gfXe9mFeDJVLOlRfOvbHZlhuX8aKMjPt5w7QvMbBE1qa','1','admin');

# 添加系统角色
insert into
    actor
(actor_id, can_edit, create_time, is_deleted,update_time, role_description, role_name)
values(1,1,now(),0,now(),'普通管理员','ADMIN');
insert into
    actor
(actor_id, can_edit, create_time, is_deleted,update_time, role_description, role_name)
values(2,1,now(),0,now(),'普通用户','USER');

# 角色与用户关联
insert into
    users_actors (user_user_id, actors_actor_id)
values (1,1);
insert into
    users_actors (user_user_id, actors_actor_id)
values (1,2);


#默认的分组
use blog;
insert into
post_group (can_edit,create_time,is_deleted,update_time,group_name)
values (1,now(),false,now(),'默认');