-- sims SQL V2, Sprint 2

-- DROP DATABASES

-- From Simon Minotto

set foreign_key_checks = 0;

DROP TABLE IF EXISTS comment_cmn;
DROP TABLE IF EXISTS googlecache_gch;
DROP TABLE IF EXISTS passager_psg;
DROP TABLE IF EXISTS user_fav_pos_ufp;
DROP TABLE IF EXISTS route_rte;
DROP TABLE IF EXISTS _route_type_rtp;
DROP TABLE IF EXISTS car_car;
DROP TABLE IF EXISTS position_pos;
DROP TABLE IF EXISTS usr_crt;
DROP TABLE IF EXISTS user_usr;
DROP TABLE IF EXISTS crt_crt;
DROP TABLE IF EXISTS _criterion_crt;
DROP TABLE IF EXISTS _criterion_type_ctt;

set foreign_key_checks = 1;-- TODO constraint for criterion


-- sims SQL V2, Sprint 2

-- From Simon Minotto



CREATE TABLE IF NOT EXISTS _criterion_type_ctt (
	ctt_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	ctt_label VARCHAR(100) NOT NULL,
								  
	INDEX(ctt_label)

) ENGINE = InnoDb;


CREATE TABLE IF NOT EXISTS _criterion_crt (
	crt_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	crt_type INT(11) NOT NULL,
	crt_label VARCHAR(100) NOT NULL,
	crt_root_criterion INT(11) NULL DEFAULT NULL COMMENT 'Father, used for arborescence criterions. NULL for root element',
	crt_order INT(11) NOT NULL DEFAULT 1,
							 
	INDEX(crt_type),
	INDEX(crt_root_criterion),
	INDEX(crt_label),
	INDEX(crt_order)

) ENGINE = InnoDb;


-- Table de cache permettant de faire des recherches arborescentes plus facilement
CREATE TABLE IF NOT EXISTS crt_crt (
	crt_container INT(11) NOT NULL,
	crt_contained INT(11) NOT NULL,
					  
	PRIMARY KEY (crt_container, crt_contained),
	INDEX(crt_container),
	INDEX(crt_contained)
	
)  ENGINE = InnoDb;



CREATE TABLE IF NOT EXISTS user_usr (
	usr_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	usr_firstname VARCHAR(100) NOT NULL,
	usr_lastname VARCHAR(100) NOT NULL,
	usr_email VARCHAR(100) NOT NULL COMMENT 'email is login',
	usr_password VARCHAR(100) NOT NULL COMMENT 'password is md5(concat(usr_id,password_clear))',
	usr_current_position INT(11) NULL DEFAULT NULL,
	usr_genre ENUM('male','female') NOT NULL,
	usr_birthdate BIGINT(20) NOT NULL,
	usr_description TEXT NOT NULL,
	usr_mobilphone VARCHAR(100) NOT NULL,
--	usr_reputation INT(11) NOT NULL,
	usr_note INT(2) NULL DEFAULT NULL COMMENT 'Member review average of this member, null for none', 
	usr_registrationdate BIGINT(20) NOT NULL,
	usr_lastlogindate BIGINT(20) NOT NULL,
					   
					   
	UNIQUE(usr_email),
	INDEX(usr_current_position)
)  ENGINE = InnoDb;


CREATE TABLE IF NOT EXISTS usr_crt (
	usr_id INT(11) NOT NULL,
	crt_id INT(11) NOT NULL,
					  
	PRIMARY KEY(usr_id, crt_id),
	INDEX(usr_id),
	INDEX(crt_id)

) ENGINE = InnoDb;


CREATE TABLE IF NOT EXISTS position_pos (
	pos_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	pos_address VARCHAR(255) NOT NULL,
	pos_latitude FLOAT(10,6) NOT NULL,
	pos_longitude FLOAT(10,6) NOT NULL,
	
	INDEX(pos_latitude),
	INDEX(pos_longitude),
	UNIQUE(pos_address)
) ENGINE = InnoDb;

CREATE TABLE IF NOT EXISTS car_car (
	car_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	car_name VARCHAR(100) NOT NULL,
	car_seat INT(2) NULL DEFAULT NULL,
	car_owner INT(11) NOT NULL,
					  
	INDEX(car_owner)

) ENGINE = InnoDb;

CREATE TABLE IF NOT EXISTS _route_type_rtp (
	rtp_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	rtp_label VARCHAR(100) NOT NULL,
							  
	INDEX(rtp_label)
) ENGINE = InnoDb;


CREATE TABLE IF NOT EXISTS route_rte (
	rte_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	rte_type INT(11) NOT NULL COMMENT 'Type of traject : I want one or I propose one',
	rte_pos_begin INT(11) NOT NULL,
	rte_pos_end INT(11) NOT NULL,
	rte_date_begin BIGINT(20) NOT NULL,
	rte_date_end BIGINT(20) NULL DEFAULT NULL,
	rte_comment TEXT NOT NULL COMMENT 'textual comment of the owner about the route',
	rte_owner INT(11) NOT NULL COMMENT 'Proprietaire du trajet',
	rte_seat INT(2) NULL DEFAULT NULL COMMENT 'Number of seat needed / proposed. NULL <=> not set',
	rte_car INT(11) NULL DEFAULT NULL COMMENT 'Optional - Car used for this traject',
	rte_deletedate BIGINT(20) NULL DEFAULT NULL COMMENT 'Delete date, null if this route is not deleted',
	

	INDEX(rte_type),
	INDEX(rte_pos_begin),
	INDEX(rte_pos_end),
	INDEX(rte_date_begin),
	INDEX(rte_date_end),
	INDEX(rte_owner),
	INDEX(rte_seat),
	INDEX(rte_car)

) ENGINE = InnoDb;


CREATE TABLE IF NOT EXISTS user_fav_pos_ufp (
	ufp_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	ufp_user INT(11) NOT NULL,
	ufp_position INT(11) NOT NULL,
	ufp_label VARCHAR(100) NOT NULL,
	INDEX(ufp_user),
	INDEX(ufp_position)
	
) ENGINE = InnoDb;

CREATE TABLE IF NOT EXISTS passager_psg (
	psg_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	psg_route INT(11) NOT NULL,
	psg_user INT(11) NOT NULL,
						   
	INDEX(psg_route),
	INDEX(psg_user)

) ENGINE = InnoDb;




CREATE TABLE IF NOT EXISTS googlecache_gch (
	gch_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	gch_address VARCHAR(255) NOT NULL,
	gch_latitude FLOAT(10,6) NULL DEFAULT NULL,
	gch_longitude FLOAT(10,6) NULL DEFAULT NULL,
							  
	UNIQUE(gch_address),
	INDEX(gch_latitude),
	INDEX(gch_longitude)


) ENGINE = InnoDb;




CREATE TABLE IF NOT EXISTS comment_cmn (
	cmn_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	cmn_user_from INT(11) NOT NULL,
	cmn_user_to INT(11) NOT NULL,
	cmn_comment_text TEXT NULL COMMENT 'Textual comment about the owner',
	cmn_comment_note INT(2) NULL DEFAULT NULL COMMENT 'A value between 1-5 to evaluate the conductor',

	INDEX(cmn_user_from),
	INDEX(cmn_user_to),
	INDEX(cmn_comment_note)
						  
) ENGINE = InnoDb;






ALTER TABLE user_usr
	ADD CONSTRAINT usr_ufp_constraint FOREIGN KEY (usr_current_position) REFERENCES user_fav_pos_ufp (ufp_id);
	
ALTER TABLE car_car
	ADD CONSTRAINT car_owner_constraint FOREIGN KEY (car_owner) REFERENCES user_usr (usr_id);
	
ALTER TABLE route_rte
	ADD CONSTRAINT route_rte_type_contraint FOREIGN KEY (rte_type) REFERENCES _route_type_rtp(rtp_id);
	
ALTER TABLE route_rte
	ADD CONSTRAINT route_pos_begin_constraint FOREIGN KEY (rte_pos_begin) REFERENCES position_pos (pos_id);
	
ALTER TABLE route_rte
	ADD CONSTRAINT route_pos_end_constraint FOREIGN KEY (rte_pos_end) REFERENCES position_pos (pos_id);

ALTER TABLE route_rte
	ADD CONSTRAINT route_owner_constraint FOREIGN KEY (rte_owner) REFERENCES user_usr (usr_id);
		
ALTER TABLE route_rte
	ADD CONSTRAINT route_car_constraint FOREIGN KEY (rte_car) REFERENCES car_car (car_id);

ALTER TABLE user_fav_pos_ufp
	ADD CONSTRAINT user_fav_user_constraint FOREIGN KEY (ufp_user) REFERENCES user_usr (usr_id);
	
ALTER TABLE user_fav_pos_ufp
	ADD CONSTRAINT user_fav_position_constraint FOREIGN KEY (ufp_position) REFERENCES position_pos(pos_id);	
	
ALTER TABLE passager_psg
	ADD CONSTRAINT passager_route_constraint FOREIGN KEY (psg_route) REFERENCES route_rte (rte_id);
	
ALTER TABLE passager_psg
	ADD CONSTRAINT passager_user_constraint FOREIGN KEY (psg_user) REFERENCES user_usr (usr_id);
	
ALTER TABLE comment_cmn
	ADD CONSTRAINT comment_user_from_constraint FOREIGN KEY (cmn_user_from) REFERENCES user_usr (usr_id);
	
ALTER TABLE comment_cmn
	ADD CONSTRAINT comment_user_to_constraint FOREIGN KEY (cmn_user_to) REFERENCES user_usr (usr_id);
	
ALTER TABLE _criterion_crt
	ADD CONSTRAINT criterion_type_constraint FOREIGN KEY (crt_type) REFERENCES _criterion_type_ctt (ctt_id);
	
ALTER TABLE _criterion_crt
	ADD CONSTRAINT criterion_root_constraint FOREIGN KEY (crt_root_criterion) REFERENCES _criterion_crt (crt_id);

ALTER TABLE crt_crt
	ADD CONSTRAINT crt_crt_container_constraint FOREIGN KEY (crt_container) REFERENCES _criterion_crt (crt_id);

ALTER TABLE crt_crt
	ADD CONSTRAINT crt_crt_contained_constraint FOREIGN KEY (crt_contained) REFERENCES _criterion_crt (crt_id);

ALTER TABLE usr_crt
	ADD CONSTRAINT usr_crt_user_constraint FOREIGN KEY (usr_id) REFERENCES user_usr (usr_id);

ALTER TABLE usr_crt
	ADD CONSTRAINT usr_crt_criterion_constraint FOREIGN KEY (crt_id) REFERENCES _criterion_crt (crt_id);
	
	-- sims SQL V2, Sprint 2

-- MINIMAL INSERT

-- From Simon Minotto


INSERT IGNORE INTO _route_type_rtp (rtp_id, rtp_label) VALUES 
(1, 'WantCar'),
(2,'NeedCar');


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(1, 'Preference');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(1, 1, 'Trip in family', NULL, 1) ,
(2, 1, 'Hide my infos', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(2, 'Preference_music');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(3, 2, 'Preference_music_yes', NULL, 1) ,
(4, 2, 'Preference_music_no', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(3, 'Preference_smoke');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(5, 3, 'Preference_smoke_yes', NULL, 1) ,
(6, 3, 'Preference_smoke_no', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(4, 'Preference_animal');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(7, 4, 'Preference_animal_yes', NULL, 1) ,
(8, 4, 'Preference_animal_no', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(5, 'Preference_talking');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(9, 5, 'Preference_talking_discret', NULL, 1) ,
(10, 5, 'Preference_talking_normal', NULL, 1),
(11, 5, 'Preference_talking_passionate', NULL, 1);-- sims SQL V2, Sprint 2

-- Procedures stokes

-- From Simon Minotto

DELIMITER //

DROP PROCEDURE IF EXISTS _criterion_update_crt_crt //
CREATE PROCEDURE _criterion_update_crt_crt ()
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE __crt_id INT(11);
	DECLARE __crt_root_criterion INT(11);
	
	DECLARE cur1 CURSOR FOR SELECT crt_id FROM _criterion_crt;
	DECLARE cur2 CURSOR FOR SELECT crt_id, crt_root_criterion FROM _criterion_crt WHERE crt_root_criterion IS NOT NULL ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	
	OPEN cur2;
	
	
	TRUNCATE TABLE crt_crt;
	
	OPEN cur1;
	read_loop: LOOP
		FETCH cur1 INTO __crt_id;
		IF done THEN
			LEAVE read_loop;
		END IF;
		
		INSERT INTO crt_crt (crt_container, crt_contained) VALUES (__crt_id, __crt_id);
	END LOOP;
	CLOSE cur1;
	
	
	OPEN cur2;
	read_loop2: LOOP
		FETCH cur2 INTO __crt_id, __crt_root_criterion ;
		IF done THEN
			LEAVE read_loop2;
		END IF;
		
		INSERT INTO crt_crt (crt_container, crt_contained) VALUES (__crt_root_criterion, __crt_id);
	END LOOP;
	CLOSE cur1;
    
end //

DELIMITER ;

-- sims SQL V2, Sprint 2

-- Procedures stokes - position

-- From Simon Minotto

DELIMITER //

-- Return position of an address or nothing if it's not found
DROP PROCEDURE IF EXISTS position_get_by_address //
CREATE PROCEDURE position_get_by_address (
	IN _address VARCHAR(255)
)
BEGIN
	DECLARE __pos_id INT(11);
	
	call _position_get_by_address(_address);
	
	SELECT * FROM position_pos WHERE pos_id=__pos_id;
end //

-- Return position id of an address or nothing if it's not found
DROP PROCEDURE IF EXISTS _position_get_by_address //
CREATE PROCEDURE _position_get_by_address (
	IN _address VARCHAR(255),
	OUT _pos_id INT(11)
)
BEGIN
	SELECT pos_id INTO _pos_id FROM position_pos WHERE pos_address = _address;
end //



-- Store a new position
DROP PROCEDURE IF EXISTS position_create_or_update //
CREATE PROCEDURE position_create_or_update (
	IN _address VARCHAR(255),
	IN _latitude FLOAT(10,6),
	IN _longitude FLOAT(10,6)
)
BEGIN

	INSERT INTO position_pos 	(pos_id, pos_address, pos_latitude, pos_longitude) VALUES 
								(NULL, _address, _latitude, _longitude) 
		ON DUPLICATE KEY 
			UPDATE pos_latitude=_latitude, pos_longitude=_longitude;
	
END //


-- Store a new position in googlecache
DROP PROCEDURE IF EXISTS googlecache_create_or_update //
CREATE PROCEDURE googlecache_create_or_update (
	IN _address VARCHAR(255),
	IN _latitude FLOAT(10,6),
	IN _longitude FLOAT(10,6)
)
BEGIN

	INSERT INTO googlecache_gch 	(gch_id, gch_address, gch_latitude, gch_longitude) VALUES 
									(NULL, _address, _latitude, _longitude) 
		ON DUPLICATE KEY 
			UPDATE gch_latitude=_latitude, gch_longitude=_longitude;
	
END //


-- Return position of an address or nothing if it's not found
DROP PROCEDURE IF EXISTS googlecache_get_by_address //
CREATE PROCEDURE googlecache_get_by_address (
	IN _address VARCHAR(255)
)
BEGIN
	DECLARE __gch_id INT(11);
	
	SELECT * FROM googlecache_gch WHERE gch_address = _address;
	
end //

DELIMITER ;

-- sims SQL V2, Sprint 2

-- Procedures stokes, route

-- From Simon Minotto


DELIMITER //


-- Return user_id of associated user or null if not
DROP PROCEDURE IF EXISTS route_create //
CREATE PROCEDURE route_create (
	IN _rte_type INT(11),
	IN _rte_pos_begin INT(11),
	IN _rte_pos_end INT(11),
	IN _rte_date_begin INT(11),
	IN _rte_date_end INT(11),
	IN _rte_comment TEXT,
	IN _rte_owner INT(11),
	IN _rte_seat INT(2),
	IN _rte_car INT(11)
)
BEGIN
	DECLARE __rte_id INT(11);
	
	call _route_create(rte_type,
						rte_pos_begin,
						rte_pos_end,
						rte_date_begin,
						rte_date_end,
						rte_comment,
						rte_owner,
						rte_seat,
						rte_car,
						__rte_id
					);
					
	SELECT * FROM route_rte WHERE rte_id=__rte_id;
	
END //

DROP PROCEDURE IF EXISTS _route_create //
CREATE PROCEDURE _route_create (
	IN _rte_type INT(11),
	IN _rte_pos_begin INT(11),
	IN _rte_pos_end INT(11),
	IN _rte_date_begin INT(11),
	IN _rte_date_end INT(11),
	IN _rte_comment TEXT,
	IN _rte_owner INT(11),
	IN _rte_seat INT(2),
	IN _rte_car INT(11),
	OUT _rte_id INT(11)
)
BEGIN
	
	INSERT INTO route_rte
		(
			rte_id,
			rte_type,
			rte_pos_begin,
			rte_pos_end,
			rte_date_begin,
			rte_date_end,
			rte_comment,
			rte_owner,
			rte_seat,
			rte_car
		)
		VALUES
		(
			NULL,
			_rte_type,
			_rte_pos_begin,
			_rte_pos_end,
			_rte_date_begin,
			_rte_date_end,
			_rte_comment,
			_rte_owner,
			_rte_seat,
			_rte_car
		);
	SELECT LAST_INSERT_ID() INTO _rte_id;
	
END //





DROP PROCEDURE IF EXISTS route_join //
CREATE PROCEDURE route_join (
	IN _rte_id INT(11),
	IN _usr_id INT(11)
)
BEGIN

	INSERT IGNORE INTO passager_psg (psg_id,psg_route,psg_user) VALUES
									(NULL, _rte_id, _usr_id);


END //




DROP PROCEDURE IF EXISTS route_search //
CREATE PROCEDURE route_search (
	IN _position_begin_id INT(11),
	IN _position_end_id INT(11)
)
BEGIN

	SELECT * 
		FROM route_rte 
		WHERE 
				rte_pos_begin = _position_begin_id
			AND	rte_pos_end = _position_end_id
			AND	rte_deletedate IS NULL;
		

END //


DROP PROCEDURE IF EXISTS route_search_with_date //
CREATE PROCEDURE route_search_with_date (
	IN _position_begin_id INT(11),
	IN _position_end_id INT(11),
	IN _begin_date_departure BIGINT(11),
	IN _end_date_departure BIGINT(11)
)
BEGIN

	SELECT * 
		FROM route_rte 
		WHERE 
				rte_pos_begin = _position_begin_id
			AND	rte_pos_end = _position_end_id
			AND	rte_deletedate IS NULL
			AND rte_date_begin BETWEEN _begin_date_departure AND _end_date_departure;
		

END //



DROP PROCEDURE IF EXISTS route_search_with_date_and_delta //
CREATE PROCEDURE route_search_with_date_and_delta (
	IN _position_begin_id INT(11),
	IN _position_end_id INT(11),
	IN _begin_date_departure BIGINT(11),
	IN _end_date_departure BIGINT(11),
	IN _location_approximate_nb_meters INT(11)
)
BEGIN
	
	DECLARE __delta_deg_x FLOAT(10,6);
	DECLARE __delta_deg_y FLOAT(10,6);
	
	SELECT (_location_approximate_nb_meters * 0.0009) INTO __delta_deg_x;
	SELECT (_location_approximate_nb_meters * 0,0014) INTO __delta_deg_y;
	
	SELECT * 
		FROM route_rte 
			INNER JOIN position_pos AS posbeg ON posbeg.pos_id = rte_pos_begin
			INNER JOIN position_pos AS posend ON posend.pos_id = rte_pos_end
			INNER JOIN position_pos AS posbegask ON posbegask = _position_begin_id
			INNER JOIN position_pos AS posendask ON posendask = _position_end_id
		WHERE 
				posbeg.pos_latitude BETWEEN (posbegask.pos_latitude -  __delta_deg_x) AND (posbegask.pos_latitude +  __delta_deg_x)
			AND	posbeg.pos_latitude BETWEEN (posbegask.pos_longitude -  __delta_deg_y) AND (posbegask.pos_longitude +  __delta_deg_y)
			
			AND	posend.pos_latitude BETWEEN (posendask.pos_latitude -  __delta_deg_x) AND (posendask.pos_latitude +  __delta_deg_x)
			AND	posend.pos_latitude BETWEEN (posendask.pos_longitude -  __delta_deg_y) AND (posendask.pos_longitude +  __delta_deg_y)	
			
			AND	rte_deletedate IS NULL
			AND rte_date_begin BETWEEN _begin_date_departure AND _end_date_departure;
		

END //



DROP PROCEDURE IF EXISTS route_delete //
CREATE PROCEDURE route_delete (
	IN _rte_id INT(11)
)
BEGIN
	
	UPDATE route_rte SET rte_deletedate=UNIX_TIMESTAMP() WHERE rte_id = _rte_id;
		

END //

DELIMITER ;

-- sims SQL V2, Sprint 2

-- Procedures stokes

-- From Simon Minotto


DELIMITER //


-- Return user_id of associated user or null if not
DROP PROCEDURE IF EXISTS user_get_user_by_email //
CREATE PROCEDURE user_get_user_by_email (
	IN _usr_email VARCHAR(100)
)
BEGIN
	SELECT * FROM user_usr WHERE usr_email=_usr_email;
END //


-- Check if the tuple user - password is valid
-- Return the user if valid or NULL if not
DROP PROCEDURE IF EXISTS user_check_authentification //
CREATE PROCEDURE user_check_authentification (
	IN _usr_email VARCHAR(100),
	IN _usr_password_not_encrypted VARCHAR(100)
)
BEGIN
	
	UPDATE user_usr SET usr_lastlogindate=UNIX_TIMESTAMP() WHERE usr_email=_usr_email AND usr_password = MD5(CONCAT(usr_id,_usr_password_not_encrypted));
	
	SELECT * FROM user_usr WHERE usr_email=_usr_email AND usr_password = MD5(CONCAT(usr_id,_usr_password_not_encrypted));
	
END //


DROP PROCEDURE IF EXISTS user_create //
CREATE PROCEDURE user_create (
	IN _usr_email VARCHAR(100),
	IN _usr_password_not_encrypted VARCHAR(100),
	IN _usr_firstname VARCHAR(100),
	IN _usr_lastname VARCHAR(100),
	IN _usr_genre ENUM('male','female')
)
BEGIN
	DECLARE __usr_id INT(11);
	
	call _user_create(_usr_email, _usr_password_not_encrypted, _usr_firstname, _usr_lastname, _usr_genre, __usr_id);
	
	SELECT * FROM user_usr WHERE usr_id=__usr_id;
	
END //



DROP PROCEDURE IF EXISTS user_create_short //
CREATE PROCEDURE user_create_short (
	IN _usr_email VARCHAR(100),
	IN _usr_password_not_encrypted VARCHAR(100)
)
BEGIN
	DECLARE __usr_id INT(11);
	
	call _user_create(	_usr_email, 
						_usr_password_not_encrypted, 
						'', -- _usr_firstname, 
						'', -- _usr_lastname, 
						'male', -- _usr_genre, 
						__usr_id
					);
	
	SELECT * FROM user_usr WHERE usr_id=__usr_id;
	
END //



DROP PROCEDURE IF EXISTS _user_create //
CREATE PROCEDURE _user_create (
	IN _usr_email VARCHAR(100),
	IN _usr_password_not_encrypted VARCHAR(100),
	IN _usr_firstname VARCHAR(100),
	IN _usr_lastname VARCHAR(100),
	IN _usr_genre ENUM('male','female'),
	OUT _usr_id INT(11)
)
BEGIN
	INSERT INTO user_usr 	(usr_email, usr_password, usr_firstname, usr_lastname, usr_genre, usr_registrationdate, usr_lastlogindate) VALUES
							(_usr_email, '', _usr_firstname, _usr_lastname, _usr_genre, UNIX_TIMESTAMP(), UNIX_TIMESTAMP() );
	SELECT LAST_INSERT_ID() INTO _usr_id;
	
	call user_update_password(_usr_id, _usr_password_not_encrypted);
	
END //



DROP PROCEDURE IF EXISTS user_update_password //
CREATE PROCEDURE user_update_password (
	IN _usr_id INT(11),
	IN _usr_password_not_encrypted VARCHAR(100)
)
BEGIN	
	
	UPDATE user_usr SET usr_password = MD5(CONCAT(_usr_id,_usr_password_not_encrypted)) WHERE usr_id = _usr_id;
	
END //





DROP PROCEDURE IF EXISTS user_update //
CREATE PROCEDURE user_update (
	IN _usr_id INT(11),
	IN _usr_email VARCHAR(100),
	IN _usr_password_not_encrypted VARCHAR(100),
	IN _usr_firstname VARCHAR(100),
	IN _usr_lastname VARCHAR(100),
	IN _usr_genre ENUM('male','female'),
	IN _usr_birthdate BIGINT(20),
	IN _usr_description TEXT,
	IN _usr_mobilphone VARCHAR(100)
)
BEGIN

	UPDATE user_usr SET
		usr_email=_usr_email,
		usr_firstname = _usr_firstname,
		usr_lastname = _usr_lastname,
		usr_genre = _usr_genre,
		usr_birthdate = _usr_birthdate,
		usr_description = _usr_description,
		usr_mobilphone = _usr_mobilphone
	WHERE usr_id = _usr_id;
	
END //



DROP PROCEDURE IF EXISTS user_update_current_position //
CREATE PROCEDURE user_update_current_position (
	IN _usr_id INT(11),
	IN _pos_id INT(11)
)
BEGIN
	UPDATE user_usr SET
		usr_current_position=_pos_id
	WHERE usr_id = _usr_id;
	
END //



-- Insert or update name of a user favorite place
DROP PROCEDURE IF EXISTS user_add_pos_fav //
CREATE PROCEDURE user_add_pos_fav (
	IN _usr_id INT(11),
	IN _pos_id INT(11),
	IN _label VARCHAR(100)
)
BEGIN

	INSERT INTO user_fav_pos_ufp (ufp_id, ufp_user, ufp_position, ufp_label ) VALUES
	(
		NULL,
		_usr_id,
		_pos_id,
		_label
	) ON DUPLICATE KEY UPDATE ufp_label=_label;
	
	SELECT * FROM user_fav_pos_ufp WHERE ufp_user = _usr_id AND ufp_position = _pos_id;
	
END //



-- Delete a user favorite place
DROP PROCEDURE IF EXISTS user_add_pos_fav //
CREATE PROCEDURE user_add_pos_fav (
	IN _ufp_id INT(11)
)
BEGIN

	DELETE FROM user_fav_pos_ufp  WHERE ufp_id = _ufp_id;
	
END //





DELIMITER ;


