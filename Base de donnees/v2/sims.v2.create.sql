-- TODO constraint for criterion


-- sims SQL V2, Sprint 2

-- From Simon Minotto



CREATE TABLE IF NOT EXISTS _criterion_type_ctt (
	ctt_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	ctt_label VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
								  
	INDEX(ctt_label)

) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE IF NOT EXISTS _criterion_crt (
	crt_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	crt_type INT(11) NOT NULL,
	crt_label VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	crt_root_criterion INT(11) NULL DEFAULT NULL COMMENT 'Father, used for arborescence criterions. NULL for root element',
	crt_order INT(11) NOT NULL DEFAULT 1,
							 
	INDEX(crt_type),
	INDEX(crt_root_criterion),
	INDEX(crt_label),
	INDEX(crt_order)

) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- Table de cache permettant de faire des recherches arborescentes plus facilement
CREATE TABLE IF NOT EXISTS crt_crt (
	crt_container INT(11) NOT NULL,
	crt_contained INT(11) NOT NULL,
					  
	PRIMARY KEY (crt_container, crt_contained),
	INDEX(crt_container),
	INDEX(crt_contained)
	
)  ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;



CREATE TABLE IF NOT EXISTS user_usr (
	usr_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	usr_firstname VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
	usr_lastname VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
	usr_email VARCHAR(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'email is login',
	usr_password VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password is md5(concat(usr_id,password_clear))' ,
	usr_current_position INT(11) NULL DEFAULT NULL,
	usr_genre ENUM('male','female') NOT NULL,
	usr_birthdate BIGINT(20) NULL DEFAULT NULL,
	usr_description TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
	usr_mobilphone VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
--	usr_reputation INT(11) NOT NULL,
	usr_note INT(2) NULL DEFAULT NULL COMMENT 'Member review average of this member, null for none', 
	usr_registrationdate BIGINT(20) NOT NULL DEFAULT 0,
	usr_lastlogindate BIGINT(20) NOT NULL DEFAULT 0,
					   
					   
	UNIQUE(usr_email),
	INDEX(usr_current_position)
)  ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE IF NOT EXISTS usr_crt (
	usr_id INT(11) NOT NULL,
	crt_id INT(11) NOT NULL,
					  
	PRIMARY KEY(usr_id, crt_id),
	INDEX(usr_id),
	INDEX(crt_id)

) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE IF NOT EXISTS position_pos (
	pos_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	pos_address VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
	pos_latitude FLOAT(10,6) NOT NULL,
	pos_longitude FLOAT(10,6) NOT NULL,
	
	INDEX(pos_latitude),
	INDEX(pos_longitude),
	INDEX(pos_address)
	
) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS car_car (
	car_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	car_name VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
	car_seat INT(2) NULL DEFAULT NULL,
	car_owner INT(11) NOT NULL,
					  
	INDEX(car_owner)

) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE TABLE IF NOT EXISTS _route_type_rtp (
	rtp_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	rtp_label VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
							  
	INDEX(rtp_label)
) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE IF NOT EXISTS route_rte (
	rte_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	rte_type INT(11) NOT NULL COMMENT 'Type of traject : I want one or I propose one',
	rte_pos_begin INT(11) NOT NULL,
	rte_pos_end INT(11) NOT NULL,
	rte_date_begin BIGINT(20) NOT NULL,
	rte_date_end BIGINT(20) NULL DEFAULT NULL,
	rte_comment TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'textual comment of the owner about the route' ,
	rte_owner INT(11) NOT NULL COMMENT 'Proprietaire du trajet',
	rte_seat INT(2) NULL DEFAULT NULL COMMENT 'Number of seat needed / proposed. NULL <=> not set',
	rte_car INT(11) NULL DEFAULT NULL COMMENT 'Optional - Car used for this traject',
	rte_deletedate BIGINT(20) NULL DEFAULT NULL COMMENT 'Delete date, null if this route is not deleted',
	rte_price FLOAT(10,2) NULL DEFAULT NULL,
	

	INDEX(rte_type),
	INDEX(rte_pos_begin),
	INDEX(rte_pos_end),
	INDEX(rte_date_begin),
	INDEX(rte_date_end),
	INDEX(rte_owner),
	INDEX(rte_seat),
	INDEX(rte_car)

) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE IF NOT EXISTS user_fav_pos_ufp (
	ufp_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	ufp_user INT(11) NOT NULL,
	ufp_position INT(11) NOT NULL,
	ufp_label VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
	INDEX(ufp_user),
	INDEX(ufp_position)
	
) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE IF NOT EXISTS _passager_type_pgt (
	pgt_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	pgt_label VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL 

) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE IF NOT EXISTS passager_psg (
	psg_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	psg_route INT(11) NOT NULL,
	psg_user INT(11) NOT NULL,
	psg_type INT(11) NOT NULL,
	psg_askdate BIGINT(20) NOT NULL COMMENT 'Date de la demande',
	psg_pos_begin INT(11) NOT NULL,
	psg_pos_end INT(11) NOT NULL,
						   
	INDEX(psg_route),
	INDEX(psg_user),
	INDEX(psg_type),
	INDEX(psg_askdate),
	INDEX(psg_pos_begin),
	INDEX(psg_pos_end)

) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;




CREATE TABLE IF NOT EXISTS googlecache_gch (
	gch_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	gch_address VARCHAR(255) NULL DEFAULT NULL,
	gch_latitude FLOAT(10,6) NULL DEFAULT NULL,
	gch_longitude FLOAT(10,6) NULL DEFAULT NULL,
							  
	INDEX(gch_latitude),
	INDEX(gch_longitude),
	INDEX(gch_address)


) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;




CREATE TABLE IF NOT EXISTS comment_cmn (
	cmn_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	cmn_user_from INT(11) NOT NULL,
	cmn_user_to INT(11) NOT NULL,
	cmn_comment_text TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Textual comment about the owner' ,
	cmn_comment_note INT(2) NULL DEFAULT NULL COMMENT 'A value between 1-5 to evaluate the conductor',

	INDEX(cmn_user_from),
	INDEX(cmn_user_to),
	INDEX(cmn_comment_note)
						  
) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


CREATE TABLE segment_seg (
	seg_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	seg_route INT(11) NOT NULL,
	seg_pos_begin INT(11) NOT NULL,
	seg_pos_end INT(11) NOT NULL,
	seg_duration INT(11) NOT NULL COMMENT 'duration of this portion in second',
	seg_date_begin BIGINT(20) NOT NULL DEFAULT 0,
	seg_order INT(11) NOT NULL DEFAULT 0,
	
	INDEX(seg_route),
	INDEX(seg_pos_begin),
	INDEX(seg_pos_end),
	INDEX(seg_date_begin),
	INDEX(seg_order)
) ENGINE = InnoDb DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;



CREATE VIEW _view_user_usr AS (SELECT usr_id,usr_firstname, usr_lastname,usr_email,usr_password,	usr_current_position ,usr_genre, FROM_UNIXTIME(usr_birthdate) AS usr_birthdate,usr_description,usr_mobilphone,usr_note,	FROM_UNIXTIME(usr_registrationdate) AS usr_registrationdate,FROM_UNIXTIME(usr_lastlogindate) AS usr_lastlogindate FROM user_usr);
CREATE VIEW _view_passager_psg AS (SELECT psg_id, psg_route, psg_user, psg_type, FROM_UNIXTIME(psg_askdate) AS psg_askdate FROM passager_psg);

CREATE VIEW _view_route_rte AS ( SELECT
	rte_id,
	rte_type,
	rte_pos_begin,
	rte_pos_end,
	FROM_UNIXTIME(rte_date_begin) AS rte_date_begin,
	FROM_UNIXTIME(rte_date_end) AS rte_date_end,
	rte_comment,
	rte_owner,
	rte_seat,
	rte_car,
	FROM_UNIXTIME(rte_deletedate) AS rte_deletedate,
	rte_price FROM route_rte);

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
	
ALTER TABLE passager_psg
	ADD CONSTRAINT passager_pos_begin_constraint FOREIGN KEY (psg_pos_begin) REFERENCES position_pos (pos_id);
	
ALTER TABLE passager_psg
	ADD CONSTRAINT passager_pos_end_constraint FOREIGN KEY (psg_pos_end) REFERENCES position_pos (pos_id);
	
	
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
	
	
ALTER TABLE passager_psg
	ADD CONSTRAINT passager_type_constraint FOREIGN KEY (psg_type) REFERENCES _passager_type_pgt (pgt_id);
	
	
ALTER TABLE segment_seg
	ADD CONSTRAINT segment_pos_begin_constraint FOREIGN KEY (seg_pos_begin) REFERENCES position_pos (pos_id);
	
	
ALTER TABLE segment_seg
	ADD CONSTRAINT segment_pos_end_constraint FOREIGN KEY (seg_pos_end) REFERENCES position_pos (pos_id);

	
ALTER TABLE segment_seg
	ADD CONSTRAINT segment_route_constraint FOREIGN KEY (seg_route) REFERENCES route_rte (rte_id);
	
	
	
	