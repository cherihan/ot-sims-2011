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
	
	call _route_create(_rte_type,
						_rte_pos_begin,
						_rte_pos_end,
						_rte_date_begin,
						_rte_date_end,
						_rte_comment,
						_rte_owner,
						_rte_seat,
						_rte_car,
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

	INSERT IGNORE INTO passager_psg (psg_id	,psg_route	,psg_user, psg_type	, psg_askdate		) VALUES
									(NULL	, _rte_id	, _usr_id, 3		, UNIX_TIMESTAMP()	);
									-- 3 waiting


END //




DROP PROCEDURE IF EXISTS route_passager_edit_type //
CREATE PROCEDURE route_passager_edit_type (
	IN _rte_id INT(11),
	IN _usr_id INT(11),
	in _pgt_id INT(11)
)
BEGIN

	UPDATE passager_psg SET psg_type = _pgt_id WHERE psg_user = _usr_id AND psg_route = _rte_id;
									-- 3 waiting


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
	IN _location_approximate_nb_meters INT(11),
	IN _rtp_id INT(11)
)
BEGIN
	
	DECLARE __delta_deg_x FLOAT(10,6);
	DECLARE __delta_deg_y FLOAT(10,6);
	
	SELECT ( ( _location_approximate_nb_meters / 100 ) * 0.0009) INTO __delta_deg_x;
	SELECT ( ( _location_approximate_nb_meters / 100 ) * 0,0014) INTO __delta_deg_y;
	
	SELECT * 
		FROM route_rte 
			INNER JOIN position_pos AS posbeg ON posbeg.pos_id = rte_pos_begin
			INNER JOIN position_pos AS posend ON posend.pos_id = rte_pos_end
			INNER JOIN position_pos AS posbegask ON posbegask = _position_begin_id
			INNER JOIN position_pos AS posendask ON posendask = _position_end_id
		WHERE 
				posbeg.pos_latitude BETWEEN (posbegask.pos_latitude -  __delta_deg_x) AND (posbegask.pos_latitude +  __delta_deg_x)
			AND	posbeg.pos_longitude BETWEEN (posbegask.pos_longitude -  __delta_deg_y) AND (posbegask.pos_longitude +  __delta_deg_y)
			
			AND	posend.pos_latitude BETWEEN (posendask.pos_latitude -  __delta_deg_x) AND (posendask.pos_latitude +  __delta_deg_x)
			AND	posend.pos_longitude BETWEEN (posendask.pos_longitude -  __delta_deg_y) AND (posendask.pos_longitude +  __delta_deg_y)	
			
			AND	rte_deletedate IS NULL
			AND rte_date_begin BETWEEN _begin_date_departure AND _end_date_departure
			AND (rte_type = _rtp_id OR _rtp_id = 0);
		

END //


DROP PROCEDURE IF EXISTS route_search_of_owner //
CREATE PROCEDURE route_search_of_owner (
	IN _owner_id INT(11),
	IN _begin_date_departure BIGINT(11),
	IN _end_date_departure BIGINT(11),
	IN _location_approximate_nb_meters INT(11),
	IN _rtp_id INT(11)
)
BEGIN
	
	SELECT rte.* 
		FROM route_rte rte
		WHERE 
				rte_owner = _owner_id
			AND	rte_deletedate IS NULL
			AND rte_date_begin BETWEEN _begin_date_departure AND _end_date_departure
			AND (rte_type = _rtp_id OR _rtp_id = 0);
		

END //





DROP PROCEDURE IF EXISTS route_delete //
CREATE PROCEDURE route_delete (
	IN _rte_id INT(11)
)
BEGIN
	
	UPDATE route_rte SET rte_deletedate=UNIX_TIMESTAMP() WHERE rte_id = _rte_id;
		

END //



DROP PROCEDURE IF EXISTS route_get_passagers //
CREATE PROCEDURE route_get_passagers (
	IN _rte_id INT(11)
)
BEGIN

	SELECT usr.*, psg.*
		FROM  passager_psg as psg
			INNER JOIN user_usr ON psg.psg_user = usr.usr_id		
		WHERE 
				psg_route = _rte_id;
		

END //



DROP PROCEDURE IF EXISTS route_get_passagers_of_type //
CREATE PROCEDURE route_get_passagers_of_type (
	IN _rte_id INT(11),
	IN _pgt_id INT(11)
)
BEGIN

	SELECT usr.*, psg.*
		FROM  passager_psg as psg
			INNER JOIN user_usr ON psg.psg_user = usr.usr_id		
		WHERE 
				psg_route = _rte_id
			AND (psg_type = _pgt_id OR _pgt_id = 0 );
		

END //


DELIMITER ;



