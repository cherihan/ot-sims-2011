-- sims SQL V2, Sprint 2

-- From Simon Minotto

CREATE TABLE user_usr (
	usr_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	usr_firstname VARCHAR(100) NOT NULL,
	usr_lastname VARCHAR(100) NOT NULL,
	usr_email VARCHAR(100) NOT NULL COMMENT 'email is login',
	usr_password VARCHAR(100) NOT NULL COMMENT 'password is md5(concat(usr_id,password_clear))',
	usr_current_position INT(11) NULL DEFAULT NULL,
					   
	UNIQUE(usr_email),
	INDEX(usr_current_position)
) type=InnoDb;


CREATE TABLE position_pos (
	pos_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	pos_address VARCHAR(255) NOT NULL,
	pos_latitude FLOAT(10,6) NOT NULL,
	pos_longitude FLOAT(10,6) NOT NULL,
	
	INDEX(pos_latitude),
	INDEX(pos_longitude),
	INDEX(pos_address)
)type=InnoDb;

CREATE TABLE car_car (
	car_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	car_name VARCHAR(100) NOT NULL,
	car_seat INT(2) NULL DEFAULT NULL,
	car_owner INT(11) NOT NULL,
					  
	INDEX(car_owner)

)type=InnoDb;

CREATE TABLE _route_type_rtp (
	rtp_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	rtp_label VARCHAR(100) NOT NULL,
							  
	INDEX(rtp_label)
)type=InnoDb;


CREATE TABLE route_rte (
	rte_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	rte_type INT(11) NOT NULL COMMENT 'Type of traject : I want one or I propose one',
	rte_pos_begin INT(11) NOT NULL,
	rte_pos_end INT(11) NOT NULL,
	rte_date_begin BIGINT(20) NOT NULL,
	rte_date_end BIGINT(20) NULL DEFAULT NULL,
	rte_comment TEXT NOT NULL DEFAULT '' COMMENT 'textual comment of the owner about the route',
	rte_owner INT(11) NOT NULL COMMENT 'Proprietaire du trajet',
	rte_seat INT(2) NULL DEFAULT NULL COMMENT 'Number of seat needed / proposed. NULL <=> not set',
	rte_car INT(11) NULL DEFAULT NULL COMMENT 'Optional - Car used for this traject',
	

	INDEX(rte_type),
	INDEX(rte_pos_begin),
	INDEX(rte_pos_end),
	INDEX(rte_date_begin),
	INDEX(rte_date_end),
	INDEX(rte_owner),
	INDEX(rte_seat),
	INDEX(rte_car)

)type=InnoDb;


CREATE TABLE user_fav_pos_ufp (
	ufp_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	ufp_user INT(11) NOT NULL,
	ufp_position INT(11) NOT NULL,
	ufp_label VARCHAR(100) NOT NULL,
	INDEX(ufp_user),
	INDEX(ufp_position)
	
)type=InnoDb;

CREATE TABLE passager_psg (
	psg_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	psg_route INT(11) NOT NULL,
	psg_user INT(11) NOT NULL,
						   
	INDEX(psg_route),
	INDEX(psg_user)

)type=InnoDb;




CREATE TABLE googlecache_gch (
	gch_id INT(11) PRIMARY KEY AUTO_INCREMENT,
	gch_address VARCHAR(255) NOT NULL,
	gch_latitude FLOAT(10,6) NULL DEFAULT NULL,
	gch_longitude FLOAT(10,6) NULL DEFAULT NULL,
							  
	INDEX(gch_address),
	INDEX(gch_latitude),
	INDEX(gch_longitude)


)type=InnoDb;





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
	ADD CONSTRAINT passager_user_constraint FOREIGN KEY (psg_user) REFERENCES passager_psg (psg_id);
	