drop table bookmark;
drop table good;
drop table review;
drop table profile;
drop table deal;

create table deal ( --거래테이블 생성
order_number number(10),  --주문번호
buyer_number number (6), --구매자번호
seller_number number (6),--핀매자번호
p_number number (30),   --판매번호
p_count number (3),      --상품수량
price number (6),        --가격
visittime date,          --방문예정시간
buy_type number(1),     --결제유형
r_status number(1) default 0,      --리뷰상태
o_status number(1) default 0,      --주문상태
orderdate date default sysdate,          --주문일자
pickup_status number (1) default 0 --픽업상태
);

--거래번호 시퀀스 생성
drop sequence deal_order_number_seq;
create sequence deal_order_number_seq;

--기본키설정
alter table deal add constraint deal_order_number_pk primary key (order_number);
--외래키설정
alter table deal add constraint deal_buyer_number_fk
    foreign key (buyer_number) references member (mem_number) on delete cascade;  --구매자 회원테이블 참조
alter table deal add constraint deal_seller_number_fk
    foreign key (seller_number) references member (mem_number)on delete cascade; --판매자 회원 테이블 참조
alter table deal add constraint deal_p_number_fk
    foreign key (p_number) references product_info (p_number) on delete cascade; --상품 번호 상품 테이블 참조
    
-- 리뷰상태(0,1만 선택 가능)
ALTER TABLE deal ADD CONSTRAINT deal_r_status_ck CHECK (r_status ='0' OR r_status ='1'); 
-- 주문상태
ALTER TABLE deal ADD CONSTRAINT deal_o_status_ck CHECK (o_status ='0' OR o_status ='1'); 
-- 픽업상태
ALTER TABLE deal ADD CONSTRAINT deal_pickup_status_ck CHECK (pickup_status ='0' OR pickup_status ='1'); 
  
    
create table profile (  --프로필 테이블
profile_number number(5),  --프로필 번호
mem_number number (6),-- 유저 번호
p_number varchar2(5)--상품 번호
);

--프로필번호 시퀀스 생성
drop sequence profile_profile_number_seq;
create sequence  profile_profile_number_seq;

--기본키설정
alter table profile add constraint profile_profile_number_pk primary key (profile_number);--프로필 번호 pk
--외래키설정
alter table profile add constraint profile_mem_number_fk
    foreign key (mem_number) references member (mem_number) on delete cascade;-- 유저번호 회원테이블 fk 참조
alter table profile add constraint profile_p_number_fk
    foreign key (p_number) references product_info (p_number) on delete cascade; --판매글 번호 판매테이블 fk 참조


create table review (   --리뷰테이블
review_number number(1),  --리뷰 번호
buyer_number number (6),-- 작성자 번호
seller_number number(6),-- 판매자 번호
content varchar2(150),--본문 내용
write_date date, --작성일
grade number(2), -- 평점
profile_number number (10) --프로필 번호
);    

--리뷰번호 시퀀스 생성
drop sequence review_review_number_seq;
create sequence  review_review_number_seq;

--기본키설정
alter table review add constraint review_review_number_pk primary key (review_number);--리뷰 번호 pk
--외래키설정
alter table review add constraint review_buyer_number_fk
    foreign key (buyer_number) references member (mem_number) on delete cascade;-- 작성자번호 회원테이블 fk 참조
alter table review add constraint review_seller_number_fk   
    foreign key (seller_number) references member (mem_number) on delete cascade;--판매자번호 회원테이블 fk 참조
alter table review add constraint review_profile_number_fk
    foreign key (profile_number) references profile (profile_number)on delete cascade; --프로필번호 프로필테이블 fk참조
  
    
create table good ( --좋아요 테이블
good_number number(10),  --좋아요 번호
mem_number number (6),-- 회원 번호
p_number varchar2(5)-- 상품 번호
);   

--좋아요번호 시퀀스 생성
drop sequence good_good_number_seq;
create sequence  good_good_number_seq;

--기본키설정
alter table good add constraint good_good_id_pk primary key (good_number);--좋아요 번호 pk
--외래키설정
alter table good add constraint good_mem_number_fk
    foreign key (mem_number) references member (mem_number) on delete cascade;-- 회원번호 회원테이블 fk 참조
alter table good add constraint good_p_number_fk
    foreign key (p_number) references product_info (p_number) on delete cascade;-- 상품번호 상품테이블 fk 참조
    
create table bookmark ( --즐겨찾기 테이블
bookmark_number number(10),  --즐겨찾기 번호
mem_number number (10),-- 회원 번호
profile_number number(10)-- 프로필 번호
);    

--좋아요번호 시퀀스 생성
drop sequence bookmark_bookmark_number_seq;
create sequence  bookmark_bookmark_number_seq;

--기본키설정
alter table bookmark add constraint bookmark_bookmark_number_pk primary key (bookmark_number);--좋아요 번호 pk
--외래키설정
alter table bookmark add constraint bookmark_mem_number_fk
    foreign key (mem_number) references member (mem_number) on delete cascade;-- 회원번호 회원테이블 fk 참조    
alter table bookmark add constraint bookmark_profile_number_fk
    foreign key (profile_number) references profile (profile_number) on delete cascade;-- 프로필번호 프로필테이블 fk 참조      
    

--거래데이터 생성
insert into deal values (deal_order_number_seq.nextval, 1, 3,'0001',1,'4000',sysdate,'1','0','0',sysdate,'0');
insert into deal values (deal_order_number_seq.nextval, 2, 3,'0002',1,'5000',sysdate,'1','0','0',sysdate,'0');

--프로필데이터 생성
insert into profile values (PROFILE_PROFILE_NUMBER_SEQ.nextval,'3','0001');

--리뷰데이터 생성
insert into review values(REVIEW_REVIEW_NUMBER_SEQ.nextval,'1','3','맛잇어요',sysdate,'5',1);
insert into review values(REVIEW_REVIEW_NUMBER_SEQ.nextval,'2','3','맛잇어요',sysdate,'5',1);


--좋아요데이터 생성
insert into good values (GOOD_GOOD_NUMBER_SEQ.nextval,'1','0001'); 
insert into good values (GOOD_GOOD_NUMBER_SEQ.nextval,'1','0002');

--즐겨찾기데이터 생성
insert into bookmark values (BOOKMARK_BOOKMARK_NUMBER_SEQ.nextval,'1','1');
    