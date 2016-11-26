DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createEntity`( header varchar(150),  description text,  date datetime,  user_id int)
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN 

INSERT INTO `freecity`.`entity` ( `header`, `description`, `date`, `user_id`) VALUES ( header, description, date, user_id);
set @entity_id = (select `entity_id` from `freecity`.`entity`  order by `entity_id` DESC  limit 1); 
select @entity_id;

END$$
DELIMITER ;


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createItem`( header varchar(150),  description text,  date datetime,  user_id int,  url_link varchar(250), map_url varchar(1000), city_id int, img_id int, section_id int)
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN 

INSERT INTO `freecity`.`entity` ( `header`, `description`, `date`, `user_id`) VALUES ( header, description, date, user_id);
set @entity_id = (select `entity_id` from `freecity`.`entity`  order by `entity_id` DESC  limit 1);
INSERT INTO `freecity`.`url` (`entity_id`, `url_link`) VALUES (@entity_id, url_link);



INSERT INTO `freecity`.`map` ( `map_id`,`map_url`) VALUES (@entity_id, map_url);
set @map_id = (select `map_id` from `freecity`.`map`  order by `map_id` DESC  limit 1);
INSERT INTO `freecity`.`map_list` (`entity_id`, `map_id`) VALUES (@entity_id, @map_id);




INSERT INTO `freecity`.`city_list` (`entity_id`, `city_id`) VALUES (@entity_id, city_id);
INSERT INTO `freecity`.`img_list` (`entity_id`, `img_id`) VALUES (@entity_id, img_id);
INSERT INTO `freecity`.`section_list` (`entity_id`, `section_id`) VALUES (@entity_id, section_id);



END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createSimpleEntity`(IN header varchar(150), IN description text, IN date datetime, IN user_id int, IN url_link varchar(250), IN adress varchar(250), IN contacts varchar(250), IN map_lat_url varchar(45), IN map_lng_url varchar(45), IN city_id int(11), IN section_id int(11), IN date_from date, IN date_to date, IN month_from int(11), IN month_to int(11), IN day_from int(11), IN day_to int(11), IN time_from time, IN time_to time)
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN  
INSERT INTO `freecity`.`entity` ( `header`, `description`, `date`, `user_id`) VALUES ( header, description, date, user_id);
set @entity_id = (select `entity_id` from `freecity`.`entity`  order by `entity_id` DESC  limit 1); 
INSERT INTO `freecity`.`url` (`entity_id`, `url_link`) VALUES (@entity_id, url_link);
INSERT INTO `freecity`.`adress` (`entity_id`, `adress`, `contacts`) VALUES (@entity_id, adress,contacts); 
INSERT INTO `freecity`.`map` (`map_lat`, `map_lng`) VALUES ( map_lat_url,map_lng_url);
set @map_id = (select `map_id` from `freecity`.`map`  order by `map_id` DESC  limit 1); 
INSERT INTO `freecity`.`map_list` (`entity_id`, `map_id`) VALUES (@entity_id, @map_id);
INSERT INTO `freecity`.`city_list` (`entity_id`, `city_id`) VALUES (@entity_id, city_id);
INSERT INTO `freecity`.`section_list` (`entity_id`, `section_id`) VALUES (@entity_id, section_id);
INSERT INTO `freecity`.`date` (`entity_id`,`date_from`, `date_to`, `month_from`, `month_to`, `day_from`, `day_to`, `time_from`, `time_to`) VALUES (@entity_id, date_from, date_to,month_from, month_to, day_from, day_to, time_from, time_to);
select @entity_id as ID;
END$$
DELIMITER ;




DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delItemsById`(in id int)
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN


set @map_id = (select `map_id` from `freecity`.`map_list` where `entity_id`=id );
DELETE FROM `freecity`.`map_list` WHERE `entity_id`=id ;
DELETE FROM `freecity`.`map` WHERE `map_id`=@map_id ;

DELETE FROM `freecity`.`url` WHERE `entity_id`=id ;
DELETE FROM `freecity`.`city_list` WHERE `entity_id`=id ;
DELETE FROM `freecity`.`img_list` WHERE `entity_id`=id ;
DELETE FROM `freecity`.`section_list` WHERE `entity_id`=id ;
DELETE FROM `freecity`.`adress` WHERE `entity_id`=id ;
DELETE FROM `freecity`.`date` WHERE `entity_id`=id ;
DELETE FROM `freecity`.`entity` WHERE `entity_id`=id ;




 END$$
DELIMITER ;




DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `executeSelectAll`(IN  condition1 varchar(250), IN condition2 varchar(250), IN condition3 varchar(250), out counts int(11) )
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

SET @var = CONCAT('SELECT SQL_CALC_FOUND_ROWS entity.status, entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress,"@",contacts SEPARATOR \';\') as adress,
GROUP_CONCAT(distinct  map.map_lat,",", map.map_lng,",", map.map_id SEPARATOR \';\') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct DATE_FORMAT(date_from, \'%d-%m-%Y\'),",",DATE_FORMAT(date_to, \'%d-%m-%Y\'),",",month_from,",",month_to,",",day_from,",",day_to,",",date_format(time_from, \'%H:%i\'),",",date_format(time_to, \'%H:%i\') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name,
GROUP_CONCAT(distinct rating.stars,",",rating.voices) as rating,
GROUP_CONCAT(distinct img.img_name) as img
 FROM
freecity.entity LEFT JOIN
 freecity.user on entity.user_id = user.user_id LEFT JOIN
 freecity.url on entity.entity_id = url.entity_id LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.city on city.city_id = city_list.city_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.section on section.section_id= section_list.section_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id  LEFT JOIN
 freecity.rating on rating.entity_id = entity.entity_id LEFT JOIN



 freecity.head_img_list on head_img_list.entity_id = entity.entity_id LEFT JOIN
 freecity.img on head_img_list.img_id = img.img_id

 ', condition1,condition3, '
 group by entity.entity_id
 order by entity.entity_id  desc ',condition2,';' );
PREPARE zxc FROM @var;
EXECUTE zxc;
 
set counts = (SELECT FOUND_ROWS());
END$$
DELIMITER ;


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsByProperties`(column_name CHAR(64), operation CHAR(64), id CHAR(250))
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

SET @var = CONCAT('SELECT entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress SEPARATOR \';\') as adress,
GROUP_CONCAT(distinct map.map_url SEPARATOR \';\') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct date_from,",",date_to,",",day_from,",",day_to,",",date_format(time_from, \'%H:%i\'),",",date_format(time_to, \'%H:%i\') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name
 FROM
 freecity.user,  freecity.url,  freecity.city, freecity.entity LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id  where
 freecity.url.entity_id = freecity.entity.entity_id and  
 freecity.city_list.city_id = freecity.city.city_id and ', column_name, operation, id,'
 group by entity.entity_id
 order by entity.entity_id ;' );
PREPARE zxc FROM @var;
EXECUTE zxc;



END$$
DELIMITER ;




DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsByPropertiesCity`(column_name CHAR(64), operation CHAR(64), id CHAR(250), city int(11))
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

 SET @var = CONCAT('SELECT entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress SEPARATOR \';\') as adress,
GROUP_CONCAT(distinct map.map_url SEPARATOR \';\') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct date_from,",",date_to,",",day_from,",",day_to,",",date_format(time_from, \'%H:%i\'),",",date_format(time_to, \'%H:%i\') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name
 FROM
 freecity.user,  freecity.url,  freecity.city, freecity.entity LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id  where
 freecity.url.entity_id = freecity.entity.entity_id and  
 freecity.city_list.city_id = freecity.city.city_id and ',
 column_name, operation, id,' and city.city_id = ', city, ' group by entity.entity_id
 order by entity.entity_id');
 PREPARE zxc FROM @var;
 EXECUTE zxc;



 END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsByPropertiesLimit`(column_name CHAR(64), operation CHAR(64), id CHAR(250), ord CHAR(250))
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

SET @var = CONCAT('SELECT entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress SEPARATOR \';\') as adress,
GROUP_CONCAT(distinct map.map_url SEPARATOR \';\') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct date_from,",",date_to,",",day_from,",",day_to,",",date_format(time_from, \'%H:%i\'),",",date_format(time_to, \'%H:%i\') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name,
GROUP_CONCAT(distinct rating.stars,",",rating.voices) as rating
 FROM
 freecity.user,  freecity.url,  freecity.city, freecity.entity LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id  LEFT JOIN
 freecity.rating on rating.entity_id = entity.entity_id  where
 freecity.url.entity_id = freecity.entity.entity_id and  
 freecity.city_list.city_id = freecity.city.city_id and ', column_name, operation, id,'
 group by entity.entity_id
 order by entity_id limit ',ord,';' );
PREPARE zxc FROM @var;
EXECUTE zxc;



END$$
DELIMITER ;





DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsByPropertiesLimitCity`(column_name CHAR(64), operation CHAR(64), id CHAR(250), ord CHAR(250), city int(11))
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

SET @var = CONCAT('SELECT entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress SEPARATOR \';\') as adress,
GROUP_CONCAT(distinct map.map_url SEPARATOR \';\') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct date_from,",",date_to,",",day_from,",",day_to,",",date_format(time_from, \'%H:%i\'),",",date_format(time_to, \'%H:%i\') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name,
GROUP_CONCAT(distinct rating.stars,",",rating.voices) as rating
 FROM
 freecity.user,  freecity.url,  freecity.city, freecity.entity LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id  LEFT JOIN
 freecity.rating on rating.entity_id = entity.entity_id  where
 freecity.url.entity_id = freecity.entity.entity_id and  
 freecity.city_list.city_id = freecity.city.city_id and ', column_name, operation, id,' and city.city_id = ', city, '
 group by entity.entity_id
 order by entity_id limit ',ord,';' );
PREPARE zxc FROM @var;
EXECUTE zxc;



END$$
DELIMITER ;




DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsByPropertiesOrder`(column_name CHAR(64), operation CHAR(64), id CHAR(250), ord CHAR(250))
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

SET @var = CONCAT('SELECT entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress SEPARATOR \';\') as adress,
GROUP_CONCAT(distinct map.map_url SEPARATOR \';\') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct date_from,",",date_to,",",day_from,",",day_to,",",date_format(time_from, \'%H:%i\'),",",date_format(time_to, \'%H:%i\') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name,
GROUP_CONCAT(distinct rating.stars,",",rating.voices) as rating
 FROM
 freecity.user,  freecity.url,  freecity.city, freecity.entity LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id  LEFT JOIN
 freecity.rating on rating.entity_id = entity.entity_id  where
 freecity.url.entity_id = freecity.entity.entity_id and  
 freecity.city_list.city_id = freecity.city.city_id and ', column_name, operation, id,'
 group by entity.entity_id
 order by ',ord,' ;' );
PREPARE zxc FROM @var;
EXECUTE zxc;



END$$
DELIMITER ;


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAll`()
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

SELECT entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress SEPARATOR ';') as adress,
GROUP_CONCAT(distinct map.map_url SEPARATOR ';') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct DATE_FORMAT(date_from, '%d-%m-%Y'),",",DATE_FORMAT(date_to, '%d-%m-%Y'),",",day_from,",",day_to,",",date_format(time_from, '%H:%i'),",",date_format(time_to, '%H:%i') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name
 FROM
freecity.entity LEFT JOIN 
 freecity.user on entity.user_id = user.user_id LEFT JOIN
 freecity.url on entity.entity_id = url.entity_id LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.city on city.city_id = city_list.city_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id   
 group by entity.entity_id
 order by entity.entity_id desc;
 



END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectAllLimitFrom`(fro int(11), coun int(11))
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN
SELECT entity.entity_id, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress SEPARATOR ';') as adress,
GROUP_CONCAT(distinct map.map_url SEPARATOR ';') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct date_from,",",date_to,",",day_from,",",day_to,",",date_format(time_from, '%H:%i'),",",date_format(time_to, '%H:%i') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name
 FROM
 freecity.user,  freecity.url,  freecity.city, freecity.entity LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id  where
 freecity.url.entity_id = freecity.entity.entity_id and  
 freecity.city_list.city_id = freecity.city.city_id      
 group by entity.entity_id
 order by entity.entity_id limit fro, coun ;
 END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectById`(id int(11))
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN

SELECT entity.entity_id, entity.status, header, description, date, first_name, last_name,
user.user_id, email, login,
GROUP_CONCAT(distinct url.url_link) as  url_link,
GROUP_CONCAT(distinct adress,"@",contacts SEPARATOR ';') as adress,
GROUP_CONCAT(distinct map.map_lat,",", map.map_lng,",", map.map_id SEPARATOR ';') as map_url,
GROUP_CONCAT(distinct city.city_id) as city_ids,
GROUP_CONCAT(distinct city.name) as city_names,
GROUP_CONCAT(distinct section_list.section_id) as  section_id,
GROUP_CONCAT(distinct DATE_FORMAT(date_from, '%d-%m-%Y'),",",DATE_FORMAT(date_to, '%d-%m-%Y'),",",month_from,",",month_to,",",day_from,",",day_to,",",date_format(time_from, '%H:%i'),",",date_format(time_to, '%H:%i') SEPARATOR ";") as work_date,
GROUP_CONCAT(distinct tags.tag_id,",",tag_name SEPARATOR ";") as tag_name,
GROUP_CONCAT(distinct rating.stars,",",rating.voices) as rating,
GROUP_CONCAT(distinct img.img_name) as img
 FROM
freecity.entity LEFT JOIN 
 freecity.user on entity.user_id = user.user_id LEFT JOIN
 freecity.url on entity.entity_id = url.entity_id LEFT JOIN
 freecity.adress on entity.entity_id = adress.entity_id LEFT JOIN
 freecity.map_list on entity.entity_id= map_list.entity_id LEFT JOIN 
 freecity.map on map.map_id= map_list.map_id LEFT JOIN 
 freecity.city_list on entity.entity_id = city_list.entity_id LEFT JOIN
 freecity.city on city.city_id = city_list.city_id LEFT JOIN
 freecity.section_list on entity.entity_id= section_list.entity_id LEFT JOIN
 freecity.date on entity.entity_id = date.entity_id LEFT JOIN
 freecity.tags_list on entity.entity_id = tags_list.entity_id LEFT JOIN
 freecity.tags on tags.tag_id = tags_list.tag_id LEFT JOIN
 freecity.rating on rating.entity_id = entity.entity_id LEFT JOIN
 freecity.head_img_list on head_img_list.entity_id = entity.entity_id LEFT JOIN
 freecity.img on head_img_list.img_id = img.img_id  where
 freecity.entity.entity_id = id  
 group by entity.entity_id
 order by entity.entity_id desc;
 



END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSimpleEntity`( IN id int(11), IN header varchar(150), IN description text, IN date datetime, IN user_id int, IN url_link varchar(250), IN adress varchar(250),  IN contacts varchar(250), IN map_lat varchar(45), IN map_lng varchar(45), IN city_id int(11), IN section_id int(11), IN date_from date, IN date_to date, IN month_from int(11), IN month_to int(11), IN day_from int(11), IN day_to int(11), IN time_from time, IN time_to time, IN c_header varchar(150), IN c_description text, IN c_date datetime,  IN c_url_link varchar(250), IN c_adress varchar(250), IN c_contacts varchar(250), IN c_map_lat varchar(45), IN c_map_lng varchar(45), IN c_city_id int(11), IN c_section_id int(11),IN c_date_from date, IN c_date_to date, IN c_month_from int(11),IN c_month_to int(11), IN c_day_from int(11), IN c_day_to int(11), IN c_time_from time, IN c_time_to time)
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN 
UPDATE `freecity`.`entity` SET `entity`.`header` = header, `entity`.`description` = description WHERE `entity`.`entity_id` = id ;
UPDATE `freecity`.`url` SET `url`.`url_link`= url_link  WHERE `url`.`entity_id`= id and `url`.`url_link` = c_url_link;
UPDATE `freecity`.`adress` SET  `adress` = adress , `contacts` = contacts WHERE `entity_id`=id and `adress`= c_adress;
UPDATE `freecity`.`adress` SET  `adress`.`adress` = adress , `adress`.`contacts` = contacts WHERE `adress`.`entity_id`=id and `adress`.`adress`= c_adress;
UPDATE `freecity`.`city_list` SET  `city_list`.`city_id` = city_id  WHERE `city_list`.`entity_id` = id and `city_list`.`city_id` = c_city_id;
UPDATE `freecity`.`section_list` SET  `section_list`.`section_id` = section_id  WHERE `section_list`.`entity_id` = id and `section_list`.`section_id` = c_section_id;
UPDATE `freecity`.`date` SET `date`.`date_from`=date_from, `date`.`date_to`=date_to, `date`.`month_from`=month_from, `date`.`month_to`=month_to, `date`.`day_from`=day_from, `date`.`day_to`=day_to, `date`.`time_from`=time_from, `date`.`time_to`=time_to WHERE `date`.`entity_id`=id and`date`.`date_from`=c_date_from and`date`.`date_to`=c_date_to and`date`.`month_from`=c_month_from and`date`.`month_to`=c_month_to and`date`.`day_from`=c_day_from and`date`.`day_to`=c_day_to and`date`.`time_from`=c_time_from and`date`.`time_to`=c_time_to;
UPDATE `freecity`.`map` SET  `map`.`map_lat`= map_lat, `map`.`map_lng`= map_lng   WHERE `map`.`map_lat`= c_map_lat and `map`.`map_lng`= c_map_lng  and `map`.`map_id` in (select `map_id` from `freecity`.`map_list` where `map_list`.`entity_id` = id);
END$$
DELIMITER ;


