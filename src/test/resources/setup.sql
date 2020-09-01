drop table employee if exists;

drop table department if exists;

create table employee ( id int auto_increment primary key, name varchar(200), dept_id int);

create table department ( id int auto_increment primary key, name varchar(200));

insert into employee (id,name,dept_id) values (101,'ABCD',1);

insert into employee (id,name,dept_id) values (102,'DEFG',1);

insert into employee (id,name,dept_id) values (103,'FGHI',1);

insert into employee (id,name,dept_id) values (104,'LMNO',1);

insert into department (id,name) values (1,'Alphabets');