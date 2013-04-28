create database pok charset=utf8;

use pok;

SET NAMES 'utf8';

CREATE USER pok@localhost IDENTIFIED BY 'goods';

grant all privileges on *.* to pok@localhost with grant option;

FLUSH PRIVILEGES;

create table category(id int auto_increment primary key, name varchar(100));

-- predefined categories
insert into category (name) values ("Продукты");
insert into category (name) values ("Хозяйственные товары");
insert into category (name) values ("Детские товары");
insert into category (name) values ("Лекарства");
insert into category (id, name) values (100, "Другое");

create table good(id int auto_increment primary key,
  f_category int, foreign key (f_category) references category(id),
  tm timestamp default CURRENT_TIMESTAMP, name varchar(100),
  amount varchar(10), comment varchar(100), deleted bool default false);

--
-- Dumping data for table `good`
--

LOCK TABLES `good` WRITE;
/*!40000 ALTER TABLE `good` DISABLE KEYS */;
INSERT INTO `good` VALUES (1,1,'2013-04-27 16:59:04','Конфеты','200 гр.',NULL,NULL),(2,1,'2013-04-27 16:59:59','Колбаса','500 гр.','сырокопченная',NULL),(3,1,'2013-04-27 17:01:03','Белый хлеб','1','',NULL),(4,2,'2013-04-27 17:01:42','Стиральный порошок','','\"Миф\"',NULL),(5,2,'2013-04-27 17:02:12','Зубная паста','','',NULL),(6,3,'2013-04-27 17:02:42','Игрушка','','какая-нибудь мягкая',NULL),(7,100,'2013-04-27 17:05:16','Счастье :)','много','наверное не купить',0);
/*!40000 ALTER TABLE `good` ENABLE KEYS */;
UNLOCK TABLES;