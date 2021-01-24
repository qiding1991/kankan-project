-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

use kankan;
-- 用户信息
drop table if exists user_info;
create table user_info(
   id bigint(20) unsigned not null auto_increment comment '主键',
   user_email varchar(20) not null default  '' comment '邮箱',
   password varchar(64) not null default '' comment '邮箱',
   username varchar(20) not null default '' comment '用户名',
   user_photo varchar(512) not null default '' comment '用户头像',
   status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
   create_time bigint(20) unsigned not null  comment '创建时间',
   update_time bigint(20) unsigned not null  comment '更新时间',
   primary key (id),
   unique key uqk_user_email(user_email),
   key idx_username(username)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tab信息
drop table if exists tab_info;
create table tab_info(
    id bigint(20) unsigned not null auto_increment comment '主键',
    tab_name varchar(20)  not null default '' comment '名称',
    tab_type tinyint(1) unsigned  not null  default '1' comment '0 其他 1-热点 2-推广 3-新闻 4-文章 5-视频',
    tab_order  tinyint(1) unsigned not null  default '1' comment '排序',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
    unique key uk_tab_name(`tab_name`),
    primary key (id),
    key idx_order(tab_order)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新闻信息
drop table if exists news_info;
create  table news_info(
     id bigint(20) unsigned not null auto_increment comment '主键id',
     tab_id bigint(20) unsigned not null default '0' comment '关联的主键',
     resource_id varchar(64) not null default '' comment '资源id',
     title varchar(20)  not null default '' comment '标题',
     picture varchar(20)  not null default '' comment '图片',
     read_count int(10) unsigned  not null default '0' comment '查看次数',
     status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
     create_time bigint(20) unsigned not null  comment '创建时间',
     update_time bigint(20) unsigned not null  comment '更新时间',
     hot_status tinyint not null default 1 comment '1 非热点 2热点',
     head_status tinyint not null default 1 comment '1 非头条 2头条',
     primary key (id),
     key idx_tab_id(tab_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- headline
drop table if exists header_line_info;
create  table header_line_info(
     id bigint(20) unsigned not null auto_increment comment '主键id',
     tab_id bigint(20) unsigned not null default '0' comment '关联的主键',
     title varchar(20)  not null default '' comment '标题',
     picture varchar(20)  not null default '' comment '图片',
     status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
     create_time bigint(20) unsigned not null  comment '创建时间',
     update_time bigint(20) unsigned not null  comment '更新时间',
     primary key (id),
     key idx_tab_id(tab_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- headline
drop table if exists header_line_item;
create table header_line_item(
      id bigint(20) unsigned not null auto_increment comment '主键id',
      header_line_id bigint(20) unsigned not null default '0' comment '关联的主键',
      resource_id varchar(64) not null default '' comment '资源id',
      status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
      create_time bigint(20) unsigned not null  comment '创建时间',
      update_time bigint(20) unsigned not null  comment '更新时间',
      primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 推广信息
drop table if exists ad_info;
create table ad_info(
     id bigint(20) unsigned not null auto_increment comment '主键id',
     resource_id varchar(64) not null default '' comment '资源id',
     title varchar(20)  not null default '' comment '标题',
     picture varchar(20)  not null default '' comment '图片',
     read_count int(10) unsigned  not null default '0' comment '查看次数',
     status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
     create_time bigint(20) unsigned not null  comment '创建时间',
     update_time bigint(20) unsigned not null  comment '更新时间',
     primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 专栏文章信息
drop table if exists work_info;
create table work_info(
    id bigint(20) unsigned not null auto_increment comment '主键id',
    user_id bigint(20)  unsigned not null default '0' comment '用户id',
    resource_id varchar(64) not null default '' comment '资源id',
    title varchar(20)  not null default '' comment '标题',
    type  tinyint(1) unsigned not null  default '0' comment '0 文章  1 视频',
    picture varchar(512)  not null default '' comment '图片',
    video_url varchar(512) not null default '' comment '视频地址',
    read_count int(10) unsigned  not null default '0' comment '查看次数',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
    audit_status tinyint not null default 1 comment '1 待审核 2 审核通过 3 不通过',
    hot_status tinyint not null default 1 comment '1 非热点 2热点',
    head_status tinyint not null default 1 comment '1 非头条 2头条',
    audit_status tinyint not null default 1 comment '1 待审核 2 审核通过 3 不通过',
    primary key (id),
    key idx_user_id(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- hot 热点数据
drop table if exists hot_info;
create table hot_info(
    id bigint(20) unsigned not null auto_increment comment '主键id',
    item_id bigint(20) unsigned not null  default '0' comment '关联的id',
    item_type int(10) unsigned not null   default '0' comment '2-推广 3-新闻 4-文章 5-视频',
    item_order int unsigned not null default '0' comment '排序规则',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
    primary key (id),
    key idx_item(item_id,item_type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 评论
drop table if exists comment;
create table  comment(
      id bigint(20) unsigned not null auto_increment comment '主键id',
      resource_id varchar(64) not null default '' comment '资源id',
      parent_id  bigint(20) unsigned not null default '0' comment '评论数',
      comment_text varchar(64) not null default '' comment '评论内容',
      thump_count int(10) unsigned not null default '0' comment'评论的点赞数',
      user_id   bigint(20) unsigned not null default '0' comment '评论的用户id',
      status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
      create_time bigint(20) unsigned not null  comment '创建时间',
      update_time bigint(20) unsigned not null  comment '更新时间',
      primary key (id),
      key idx_resource_id(resource_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 点赞
drop table if exists thumbs;
create table  thumbs(
      id bigint(20) unsigned not null auto_increment comment '主键id',
      resource_id varchar(64) not null default '' comment '资源id',
      comment_id bigint(20) unsigned not null default '0' comment '评论id',
      user_id   bigint(20) unsigned not null default '0' comment '点赞的用户ID',
      status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
      create_time bigint(20) unsigned not null  comment '创建时间',
      update_time bigint(20) unsigned not null  comment '更新时间',
      primary key (id),
     key idx_resource_id(resource_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 收藏
drop table if exists favourite;
create table favourite(
      id bigint(20) unsigned not null auto_increment comment '主键id',
      user_id   bigint(20) unsigned not null default '0' comment '收藏的用户',
      resource_id varchar(64) not null default '' comment '资源id',
      status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
      create_time bigint(20) unsigned not null  comment '创建时间',
      update_time bigint(20) unsigned not null  comment '更新时间',
      primary key (id),
      key idx_user_id(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- kankan 信息
drop table if exists kankan_user;
create table kankan_user(
     id bigint(20) unsigned not null auto_increment comment '主键id',
     user_name varchar(20)  not null default '' comment '作者名称',
     user_id bigint(20) unsigned not null  default '0' comment '关联的用户信息',
     user_type bigint(20) unsigned not null  default '0' comment '分类表',
     remark  varchar(20)  not null default '' comment '简介',
     picture varchar(1024) not null default '111' comment '作者头像',
     fans_count bigint(20) unsigned not null  default '0' comment '粉丝数',
     read_count bigint(20) unsigned not null  default '0' comment '阅读数',
     follow_count bigint(20) unsigned not null  default '0' comment '关注数',
     status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
     create_time bigint(20) unsigned not null  comment '创建时间',
     update_time bigint(20) unsigned not null  comment '更新时间',
     primary key (id),
    key idx_user_id(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 作者分类
drop table if exists kankan_type;
create table kankan_type(
     id bigint(20) unsigned not null auto_increment comment '主键id',
     type_desc   varchar(20)  not null default '' comment '分类描述',
     type_order  int(10) not null default '0' comment '账号分类',
     status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
     create_time bigint(20) unsigned not null  comment '创建时间',
     update_time bigint(20) unsigned not null  comment '更新时间',
     primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 作者推荐
drop table if exists kankan_recommend;
create table kankan_recommend(
    id bigint(20) unsigned not null auto_increment comment '主键id',
    user_id bigint(20) unsigned not null  default '0' comment '关联的用户信息',
    recommend_order  int(10) not null default '0' comment '账号分类',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 关注
drop table if exists kankan_follow;
create table kankan_follow(
     id bigint(20) unsigned not null auto_increment comment '主键id',
     user_id bigint(20) unsigned not null  default '0' comment '关联的用户信息',
     follow_id bigint(20) unsigned not null  default '0' comment '关联的用户信息',
     status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
     create_time bigint(20) unsigned not null  comment '创建时间',
     update_time bigint(20) unsigned not null  comment '更新时间',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 反馈
drop table if exists feedback;
create table feedback(
    id bigint(20) unsigned not null auto_increment comment '主键id',
    user_id bigint(20) unsigned not null  default '0' comment '关联的用户信息',
    feedback varchar(200) not null default  '' comment '反馈内容',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists kankan_apply;
create table kankan_apply(
    id bigint(20) unsigned not null auto_increment comment '主键id',
    user_id bigint(20) unsigned not null  default '0' comment '关联的用户信息',
    id_url varchar(200) not null default  '' comment '身份证地址',
    photo varchar(200)  not null default  '' comment '头像地址',
    username  varchar(200)  not null default  '' comment '用户名',
    remark varchar(200)  not null default  '' comment '描述信息',
     email varchar(200)  not null default  '' comment '邮箱地址',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if  exists kankan_user_role;
create  table kankan_user_role(
    id bigint(20) unsigned not null auto_increment comment '主键id',
    user_id bigint(20) unsigned not null  default '0' comment '关联的用户信息',
    role_id varchar(30) not null default '' comment '用户的权限',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table  if exists  kankan_company_apply;
create table kankan_company_apply(
     id bigint(20) unsigned not null auto_increment comment '主键id',
     admin_name varchar(30) not null default '' comment '管理员名称',
     phone varchar(30) not null default '' comment '手机号',
     city  varchar(30) not null default '' comment '城市',
     topic_photo varchar(60) not null default '' comment '图片',
     apply_photo varchar(60) not null default '' comment '图片',
     other_photo varchar(60) not null default '' comment '图片',
    status tinyint(1) unsigned not null  default '1' comment '0 无效  1 有效',
    create_time bigint(20) unsigned not null  comment '创建时间',
    update_time bigint(20) unsigned not null  comment '更新时间',
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



