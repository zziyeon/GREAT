drop table product_info;
--상품 테이블 생성
create table product_info(
    P_NUMBER NUMBER(30, 0)
,    OWNER_NUMBER NUMBER(6, 0)
,    P_TITLE VARCHAR2(300 BYTE)
,    P_NAME VARCHAR2(30 BYTE)
,    DEADLINE_TIME DATE
,    CATEGORY VARCHAR2(17 BYTE)
,    TOTAL_COUNT NUMBER(5, 0)
,    REMAIN_COUNT NUMBER(5, 0)
,    NORMAL_PRICE NUMBER(8, 0)
,    SALE_PRICE NUMBER(8, 0)
,    DISCOUNT_RATE NUMBER(2, 0)
,    PAYMENT_OPTION VARCHAR2(32 BYTE)
,    DETAIL_INFO VARCHAR2(4000 BYTE)
,    R_DATE DATE default sysdate
,    U_DATE DATE default sysdate
,    P_STATUS NUMBER(1, 0) default 0
);

--기본키 설정
ALTER TABLE PRODUCT_INFO ADD CONSTRAINT product_info_p_id_pk PRIMARY key(p_NUMBER);
 --외래키 설정
 alter table PRODUCT_INFO ADD CONSTRAINT product_info_p_num_fk FOREIGN key(OWNER_NUMBER) REFERENCES member(mem_number) on delete cascade;

drop sequence PRODUCT_P_NUMBER_SEQ;
-- 상품번호 시퀀스 생성
create sequence PRODUCT_P_NUMBER_SEQ;

-- 판매상태(0,1만 선택 가능)
ALTER TABLE PRODUCT_INFO ADD CONSTRAINT porduct_info_p_status_ck CHECK (P_STATUS =0 OR P_STATUS =1);
