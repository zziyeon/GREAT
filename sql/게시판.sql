--테이블 삭제
drop table uploadfile;
drop table comments;
drop table article;

--시퀀스 삭제
drop sequence article_article_num_seq;
drop sequence comments_comment_num_seq;
drop sequence uploadfile_uploadfile_id_seq;

--커뮤니티 테이블 생성
create table article (
  article_num           number(6),        -- 게시글 번호
  mem_number            number(6),        -- 회원 번호
  article_category      varchar2(10),     -- 게시글 카테고리
  article_title         varchar2(90),     -- 게시글 제목
  article_contents      varchar2(1500),   -- 게시글 내용
  attachment            varchar2(1),      -- 첨부파일 유무
  create_date           date,             -- 게시글 작성일
  views                 number(5)         -- 조회수
);
--primary key
alter table article add constraint article_article_num_pk primary key(article_num);
--foreign key
alter table article add constraint article_mem_number_fk foreign key(mem_number) references member(mem_number) on delete cascade;
--default
alter table article modify create_date date default sysdate;
alter table article modify views number(5) default 0;
--not null
alter table article modify article_category constraint article_article_category_nn not null;
alter table article modify article_title constraint article_article_title_nn not null;
alter table article modify article_contents constraint article_article_contents_nn not null;
alter table article modify attachment constraint article_attachment_nn not null;

--게시글 번호 시퀀스 생성
create sequence article_article_num_seq
increment by 1
start with 1
minvalue 1
maxvalue 999999
nocycle
nocache
noorder;

--댓글 테이블 생성
create table comments (
  article_num          number(6),       -- 게시글 번호
  comment_group        number(6),       -- 댓글 그룹
  comment_num          number(6),       -- 댓글 번호
  p_comment_num        number(6),       -- 부모 댓글 번호
  mem_number           number(6),       -- 회원 번호
  comment_contents     varchar2(300),   -- 댓글 내용
  create_date          date,            -- 댓글 생성일
  comment_indent       number(3)        -- 대댓글 들여쓰기
);

--primary key
alter table comments add constraint comments_comment_num_pk primary key(comment_num);
--foreign key
alter table comments add constraint comments_article_num_fk foreign key(article_num) references article(article_num) on delete cascade;
alter table comments add constraint comments_mem_number_fk foreign key(mem_number) references member(mem_number) on delete cascade;
alter table comments add constraint comments_p_comment_num_fk foreign key(p_comment_num) references comments(comment_num) on delete set null;
--default
alter table comments modify create_date date default sysdate;
alter table comments modify comment_indent number default 0;
--not null
alter table comments modify comment_contents constraint comments_comment_contents_nn not null;

--댓글 번호 시퀀스 생성
create sequence comments_comment_num_seq
increment by 1
start with 1
minvalue 1
maxvalue 999999
nocycle
nocache
noorder;