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
DELETE FROM `freecity`.`entity` WHERE `entity_id`=id ;




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
 order by entity.entity_id;
 END$$
DELIMITER ;


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectById`(IN id INT)
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
 freecity.city_list.city_id = freecity.city.city_id and    
 freecity.entity.entity_id = id
 group by entity.entity_id
 order by entity.entity_id ;
 END$$
DELIMITER ;






DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `executeSelectAll`(condition1 varchar(250), condition2 varchar(250), condition3 varchar(250))
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
 freecity.city_list.city_id = freecity.city.city_id ', condition1, '
 group by entity.entity_id
 order by entity.entity_id  desc',condition2,';' );
PREPARE zxc FROM @var;
EXECUTE zxc;



END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createSimpleEntity`( header varchar(150),  description text,  date datetime,  user_id int,  url_link varchar(250), adress varchar(250), contacts varchar(250), map_url text, city_id int(11), section_id int(11), date_from date, date_to date, month_from int(11), month_to int(11), day_from int(11), day_to int(11), time_from time, time_to time)
    DETERMINISTIC
    COMMENT 'A procedure'
BEGIN 

INSERT INTO `freecity`.`entity` ( `header`, `description`, `date`, `user_id`) VALUES ( header, description, date, user_id);
set @entity_id = (select `entity_id` from `freecity`.`entity`  order by `entity_id` DESC  limit 1); 
select @entity_id;



INSERT INTO `freecity`.`url` (`entity_id`, `url_link`) VALUES (@entity_id, url_link);
INSERT INTO `freecity`.`adress` (`entity_id`, `adress`, `contacts`) VALUES (@entity_id, adress,contacts); 

INSERT INTO `freecity`.`map` (`map_id`, `map_url`) VALUES (@entity_id, map_url);
set @map_id = (select `map_id` from `freecity`.`map`  order by `map_id` DESC  limit 1); 
INSERT INTO `freecity`.`map_list` (`entity_id`, `map_id`) VALUES (@entity_id, @map_id);

INSERT INTO `freecity`.`city_list` (`entity_id`, `city_id`) VALUES (@entity_id, city_id);
INSERT INTO `freecity`.`section_list` (`entity_id`, `section_id`) VALUES (@entity_id, section_id);

INSERT INTO `freecity`.`date` (`entity_id`,`date_from`, `date_to`, `month_from`, `month_to`, `day_from`, `day_to`, `time_from`, `time_to`) VALUES (@entity_id, date_from, date_to,month_from, month_to, day_from, day_to, time_from, time_to);

END$$
DELIMITER ;
