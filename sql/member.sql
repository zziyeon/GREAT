--drop
drop table withdrawal_member;
drop table member;
drop sequence mem_num;


--Ż��ȸ�� ���̺�
create table withdrawal_member (
mem_number number(9),
mem_type varchar2(15),
mem_id varchar2(30),
mem_password varchar2(18),
mem_name varchar2(18),
mem_nickname varchar2(18),
mem_email varchar2(30),
mem_businessnumber varchar2(10),
mem_store_name varchar2(45),
mem_store_phonenumber varchar2(15),
mem_store_location varchar2(150),
mem_store_latitude number(15, 9),
mem_store_longitude number(15, 9),
mem_store_introduce varchar2(150),
mem_store_sns varchar2(150),
mem_regtime date,
mem_lock_expiration date,
mem_admin varchar2(3)
);
--primary key
alter table withdrawal_member add constraint withdrawal_member_mem_number_pk primary key (mem_number);
--unique
alter table withdrawal_member add constraint withdrawal_member_mem_id_un unique (mem_id);
alter table withdrawal_member add constraint withdrawal_member_mem_nickname_un unique (mem_nickname);
alter table withdrawal_member add constraint withdrawal_member_mem_email_un unique (mem_email);
alter table withdrawal_member add constraint withdrawal_member_mem_businessnumber_un unique (mem_businessnumber);
alter table withdrawal_member add constraint withdrawal_member_mem_store_location_un unique (mem_store_location);
alter table withdrawal_member add constraint withdrawal_member_mem_store_latitude_un unique (mem_store_latitude);
alter table withdrawal_member add constraint withdrawal_member_mem_store_longitude_un unique (mem_store_longitude);
--not null
alter table withdrawal_member modify mem_number constraint withdrawal_member_mem_number_nn not null;
alter table withdrawal_member modify mem_type constraint withdrawal_member_mem_type_nn not null;
alter table withdrawal_member modify mem_id constraint withdrawal_member_mem_id_nn not null;
alter table withdrawal_member modify mem_password constraint withdrawal_member_mem_password_nn not null;
alter table withdrawal_member modify mem_name constraint withdrawal_member_mem_name_nn not null;
alter table withdrawal_member modify mem_nickname constraint withdrawal_member_mem_nickname_nn not null;
alter table withdrawal_member modify mem_email constraint withdrawal_member_mem_email_nn not null;
alter table withdrawal_member modify mem_regtime constraint withdrawal_member_mem_regtime_nn not null;


--ȸ����ȣ ������
create sequence mem_num
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;

--ȸ�� ���̺�
create table member (
mem_number number(9),
mem_type varchar2(15),
mem_id varchar2(30),
mem_password varchar2(18),
mem_name varchar2(18),
mem_nickname varchar2(18),
mem_email varchar2(30),
mem_businessnumber varchar2(10),
mem_store_name varchar2(45),
mem_store_phonenumber varchar2(15),
mem_store_location varchar2(150),
mem_store_latitude number(15, 9),
mem_store_longitude number(15, 9),
mem_store_introduce varchar2(150),
mem_store_sns varchar2(150),
mem_regtime date,
mem_lock_expiration date,
mem_admin varchar2(3)
);
--primary key
alter table member add constraint member_mem_number_pk primary key (mem_number);
--unique
alter table member add constraint member_mem_id_un unique (mem_id);
alter table member add constraint member_mem_nickname_un unique (mem_nickname);
alter table member add constraint member_mem_email_un unique (mem_email);
alter table member add constraint member_mem_businessnumber_un unique (mem_businessnumber);
alter table member add constraint member_mem_store_location_un unique (mem_store_location);
alter table member add constraint member_mem_store_latitude_un unique (mem_store_latitude);
alter table member add constraint member_mem_store_longitude_un unique (mem_store_longitude);
--not null
alter table member modify mem_number constraint member_mem_number_nn not null;
alter table member modify mem_type constraint member_mem_type_nn not null;
alter table member modify mem_id constraint member_mem_id_nn not null;
alter table member modify mem_password constraint member_mem_password_nn not null;
alter table member modify mem_name constraint member_mem_name_nn not null;
alter table member modify mem_nickname constraint member_mem_nickname_nn not null;
alter table member modify mem_email constraint member_mem_email_nn not null;
alter table member modify mem_regtime constraint member_mem_regtime_nn not null;
--default
alter table member modify mem_number number(9) default mem_num.nextval;
alter table member modify mem_regtime date default sysdate;
alter table member modify mem_admin varchar2(3) default 'n';


--��ȸ�� ȸ������ ����
insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email)
values ('customer', 'customer123', '@pwcust123', '���', '����1', 'test1@test.com');
insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email)
values ('customer', 'customer456', 'pwcust456', '�̰�', '����2', 'test2@test.com');
insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email)
values ('customer', 'customer789', 'pwcust789', '�ڰ�', '����3', 'test3@test.com');
--����ȸ�� ȸ������ ����
insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email,
mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_introduce, mem_store_sns)
values ('owner', 'owner123', '@pwown123', '������', '��Ī1', 'tester1@test.com',
'1112233333', '�δٱ���������', '0521112222', '��걤���� �߱� �޵�', null, null);
insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email,
mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_introduce, mem_store_sns)
values ('owner', 'owner456', '@pwown456', '������', '��Ī2', 'tester2@test.com',
'4445566666', '�׷���Ʈ����', '0523334444', '��걤���� ���� �ϻ굿', '���� �����̾ �ż��ϰ� �����ϴ�', 'https://www.instagram.com/greatsushi/');
insert into member (mem_type, mem_id, mem_password, mem_name, mem_nickname, mem_email,
mem_businessnumber, mem_store_name, mem_store_phonenumber, mem_store_location, mem_store_introduce, mem_store_sns)
values ('owner', 'owner789', '@pwown789', '������', '��Ī3', 'tester3@test.com',
'7778899999', '����Ŀ��', '0525556666', '��걤���� ���� ������', '���ν���� �����Դϴ�', 'https://www.instagram.com/3teamcoffee/');
--[ȸ������ Ȯ��]
select * from member;
--���̵� �ߺ�Ȯ�� ����
select count(*) "���̵� �ߺ�Ȯ��: 1�̸� �ߺ�, 0�̸� �ߺ�X"
  from member
 where mem_id = 'customer123'; --ȸ�����Խ� �Է¹��� ���̵�
--�г��� �ߺ�Ȯ�� ����
select count(*) "�г��� �ߺ�Ȯ��: 1�̸� �ߺ�, 0�̸� �ߺ�X"
  from member
 where mem_nickname = '��Ī1004'; --ȸ�����Խ� �Է¹��� �г���

--�α��� ���� (ȸ�� ��ȸ)
--���̵�� �н����带 �Է¹޾� �ʿ��� ȸ������ ��ȸ (select �ʿ��� ȸ������)
select *
  from member
 where mem_id = 'owner123' --�α��ν� �Է¹��� ���̵�
   and mem_password = '@pwown123'; --�α��ν� �Է¹��� �н�����
--�α��� ���� 2
--'�Է¹��� �н�����'�� ������ ����� '�Է¹��� ���̵�� ��Ī�Ǵ� �н�����'�� ���� �� �α��� ����
select mem_password "�Է¾��̵�� ��Ī�Ǵ� �н�����"
  from member
 where mem_id = 'owner123'; --�α��ν� �Է¹��� ���̵�
--���̵� ã�� ����
select mem_id "ã�� ���̵�", mem_regtime "ã�� ���̵��� ��������"
  from member
 where mem_name = '���'  --���̵�ã��� �Է¹��� �̸�
   and mem_email = 'test1@test.com'; --���̵�ã��� �Է¹��� �̸���
--��й�ȣ ã�� ����
--'�Է¹��� �̸���'�� ������ ����� '�Է¹��� ���̵�� ��Ī�Ǵ� �̸���'�� ���� �� ������ȣ �߼�
--�Է��� ������ȣ�� �߼��� ������ȣ�� ��ġ�ϸ� ��й�ȣ �缳�� ȭ������ �̵�
select mem_email "�Է¾��̵�� ��Ī�Ǵ� �̸���"
  from member
 where mem_id = 'owner123'; --�Է¹��� ���̵�
--��й�ȣ �缳�� ����
update member
   set mem_password = '@pwown123123' --�Է¹��� �н�����
 where mem_id = 'owner123'; --��й�ȣ ã�� ȭ�鿡�� �Է¹��� ���̵�
 
--ȸ������ ��ȸ��������Ż�� �õ��� ���� ����
--'�Է¹��� �н�����'�� ������ ����� '�α��ε� ���̵�� ��Ī�Ǵ� �н�����'�� ���� �� ��ȸ��������Ż�� ����
select mem_password "�α��ε� ���̵�� ��Ī�Ǵ� �н�����"
  from member
 where mem_id = 'owner123'; --�α��ε� ���̵�(�α��ν� �Է¹��� ���̵�)
--ȸ������ ���� ����
update member
   set mem_password = '@pwown456456',
       mem_store_introduce = '�ż����� ù°�� �׷���Ʈ �����Դϴ�' --�Է¹��� �������׵�
 where mem_id = 'owner456'; --�α��ε� ���̵�
--[�������� Ŀ��]
commit;
--ȸ��Ż�� ����
insert into withdrawal_member --ȸ�� ���̺��� Ż��ȸ�� ���̺�� ������ �̵�
     select *
       from member
      where mem_id = 'owner789'; --�α��ε� ���̵�
delete --ȸ������ ����
  from member
 where mem_id = 'owner789'; --�α��ε� ���̵�
--[Ż��� �ѹ�]
rollback;
 
--��ȸ�� ��ȸ
select * from member where mem_type = 'customer';
--����ȸ�� ��ȸ
select * from member where mem_type = 'owner';
--Ż��ȸ�� ���̺� ��ȸ
select * from withdrawal_member;
--ȸ�� ���̺� ��ȸ
select * from member;