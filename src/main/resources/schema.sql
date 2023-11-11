DROP DATABASE IF EXISTS `parking`;
CREATE DATABASE IF NOT EXISTS `parking` default character set utf8 collate utf8_general_ci;
USE `parking`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

create table car
(
    tid      int auto_increment comment '车辆编号'
        primary key,
    card     varchar(50)  null comment '车牌号',
    car_type varchar(20)  null comment '车类别',
    person   varchar(100) null comment '用户',
    xtime    datetime     null comment '录入时间'
)
    row_format = DYNAMIC;

create table comment
(
    rid      int          not null comment '评论编号',
    res_cont varchar(255) null comment '评论内容',
    person   varchar(100) null comment '用户',
    com_area varchar(255) null comment '社区',
    rtime    datetime     null comment '评论时间'
)
    row_format = DYNAMIC;

alter table comment
    add primary key (rid);

create table community
(
    zid   int auto_increment comment '社区编号'
        primary key,
    zname varchar(100) null comment '社区名称'
)
    row_format = DYNAMIC;

create table fee
(
    fid        int auto_increment comment '收费标准ID'
        primary key,
    car_type   varchar(255)   null comment '车位类型',
    money      double(255, 2) null comment '价格',
    money_desc varchar(255)   null comment '价格描述',
    fee_time   datetime       null comment '创建时间'
)
    row_format = DYNAMIC;

create table login_info
(
    yid         int auto_increment comment '登录日志编号                                                                                      '
        primary key,
    person      varchar(100) null comment '用户',
    ip          varchar(255) null comment 'IP地址',
    ip_location varchar(255) null comment '网络地址',
    browser     varchar(255) null comment '浏览器',
    os          varchar(255) null comment '操作系统',
    login_time  datetime     null comment '登录时间'
)
    row_format = DYNAMIC;

create table message
(
    mid       int auto_increment comment '消息编号'
        primary key,
    msg_title varchar(100) null comment '消息标题',
    msg_cont  varchar(500) null comment '消息内容',
    person    varchar(100) null comment '用户',
    msg_see   varchar(5)   null comment '是否查看',
    mtime     datetime     null comment '创建时间'
)
    row_format = DYNAMIC;

create table recharge
(
    cid    int auto_increment comment '充值编号'
        primary key,
    person varchar(100)   null comment '用户',
    money  double(255, 2) null comment '充值金额',
    ctime  datetime       null comment '充值时间'
)
    row_format = DYNAMIC;

create table stall
(
    sid         int auto_increment comment '车位编号'
        primary key,
    stall_num   varchar(20)    null comment '车位号',
    stall_area  varchar(20)    null comment '车位区域',
    stall_type  varchar(20)    null comment '车位类型',
    stall_state varchar(20)    null comment '车位状态',
    stall_money double(255, 0) null comment '车位收费',
    stall_live  varchar(5)     null comment '逻辑删除标志，1为可见，0为不可见',
    user_id     int            null comment '用户编号'
)
    row_format = DYNAMIC;

create table stall_res
(
    pid         int auto_increment comment '停车记录ID'
        primary key,
    person      varchar(100)   null comment '停车用户',
    stall_id    int            null comment '车位编码',
    create_time datetime       null comment '创建时间',
    over_time   datetime       null comment '结束时间',
    pay_money   double(255, 2) null comment '缴费金额'
)
    row_format = DYNAMIC;

create table stall_type
(
    oid    int            not null comment '车位类型编号',
    otype  varchar(50)    null comment '车位类型',
    omoney double(255, 0) null comment '车位价格'
)
    row_format = DYNAMIC;

alter table stall_type
    add primary key (oid);

create table user
(
    uid         int auto_increment comment '用户ID'
        primary key,
    username    varchar(100)   not null comment '用户名',
    password    varchar(150)   null comment '密码',
    nike        varchar(100)   null comment '昵称',
    age         int            null comment '年龄',
    sex         varchar(10)    null comment '性别',
    phone       varchar(20)    null comment '联系方式',
    card        varchar(30)    null comment '车牌号',
    money       double(255, 2) null comment '余额',
    create_time datetime       null comment '创建时间'
)
    row_format = DYNAMIC;


