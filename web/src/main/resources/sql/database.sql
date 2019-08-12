CREATE DATABASE game1 ;
create user game1 identified by 'game1'; 
grant all privileges on game1.* to 'game1'@'localhost' identified by 'game1';
grant all privileges on game1.* to 'game1'@'%' identified by 'game1';
