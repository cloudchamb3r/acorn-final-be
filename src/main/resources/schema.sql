create table member (
    id number primary key,
    email varchar2(100) not null unique,
    nickname varchar2(30) not null,
    hashtag number(4) not null,
    status varchar2(100) null
);

create table channel (
    id number primary key,
    name varchar2(100) not null,
    thumbnail clob null
);

create table channel_member (
    id number primary key,
    channel_id number not null,
    member_id number not null
);

create table topic (
    id number primary key,
    title varchar2(100) not null,
--     topic_group varchar2(100),
    channel_id number not null -- id of the channel that references topic
);


create table message (
    id number primary key,
    author_id number not null,
    content varchar2(1000) not null,

    channel_id number not null, -- id of the channel that references message
    topic_id number not null,  -- id of the topic that references message

    created_at date not null,
    updated_at date not null
);


create table attachment (
    id number primary key,
    content blob
);


create table friends (
    id number primary key,
    nickname varchar2(100) not null
);


create table friends_member (
    id number primary key,
    friends_id varchar2(100) not null,
    member_id varchar2(100) not null
);


create sequence member_seq start with 10000;
create sequence channel_seq start with 10000;
create sequence channel_member_seq start with 10000;
create sequence topic_seq start with 10000;
create sequence message_seq start with 10000;
create sequence attachment_seq start with 10000;
create sequence friends_seq start with 10000;
create sequence friends_member_seq start with 10000;
