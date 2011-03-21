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


	DROP PROCEDURE IF EXISTS route_add_segment //
	CREATE PROCEDURE route_add_segment (
		IN _rte_id INT(11),
		IN _pos_begin INT(11),
		IN _pos_end INT(11),
		IN _duration INT(11),
		IN _date_begin BIGINT(20),
		IN _order INT(11)
	)
	BEGIN
		
		INSERT INTO segment_seg (seg_id	, seg_route	, seg_pos_begin	, seg_pos_end	, seg_duration	, seg_date_begin, seg_order) VALUES
								(NULL	, _rte_id	, _pos_begin	, _pos_end	  	, _duration		, _date_begin	, _order);
		
	END //


	DROP PROCEDURE IF EXISTS route_del_all_segment //
	CREATE PROCEDURE route_del_all_segment (
		IN _rte_id INT(11)
	)
	BEGIN
		DELETE FROM segment_seg WHERE seg_route = _rte_id;	
	END //


	DROP PROCEDURE IF EXISTS route_del_by_id //
	CREATE PROCEDURE route_del_by_id (
		IN _rte_id INT(11)
	)
	BEGIN
		
		DELETE FROM  segment_seg WHERE seg_route = _rte_id;
		DELETE FROM passager_psg WHERE psg_route = _rte_id;
		DELETE FROM route_rte WHERE rte_id = _rte_id;
	END //

	DROP PROCEDURE IF EXISTS route_join //
	CREATE PROCEDURE route_join (
		IN _rte_id INT(11),
		IN _usr_id INT(11)
	)
	BEGIN
		
		DECLARE __pos_begin INT(11);
		DECLARE __pos_end INT(11);
		
		SELECT rte_pos_begin, rte_pos_end INTO __pos_begin, __pos_end FROM route_rte WHERE rte_id = _rte_id;
		
		INSERT IGNORE INTO passager_psg (psg_id	,psg_route	,psg_user, psg_type	, psg_askdate		, psg_pos_begin	, psg_pos_end 	) VALUES
										(NULL	, _rte_id	, _usr_id, 1		, UNIX_TIMESTAMP()	, __pos_begin	, __pos_end		);
										-- 3 waiting
										-- 1 accepted


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
		/*
		SELECT ( ( _location_approximate_nb_meters / 100 ) * 0.0009) As tmp  INTO __delta_deg_x;
		SELECT ( ( _location_approximate_nb_meters / 100 ) * 0,0014) As tmp INTO __delta_deg_y;
		*/
		SELECT  _location_approximate_nb_meters / 100 * 0.0019  INTO __delta_deg_x;
		SELECT  _location_approximate_nb_meters / 100 * 0.0014 INTO __delta_deg_y;
		
		SELECT * 
			FROM route_rte 
				INNER JOIN position_pos AS posbeg ON posbeg.pos_id = rte_pos_begin
				INNER JOIN position_pos AS posend ON posend.pos_id = rte_pos_end
				INNER JOIN position_pos AS posbegask ON posbegask.pos_id = _position_begin_id
				INNER JOIN position_pos AS posendask ON posendask.pos_id = _position_end_id
			WHERE 
					posbeg.pos_latitude BETWEEN (posbegask.pos_latitude -  __delta_deg_x) AND (posbegask.pos_latitude +  __delta_deg_x)
				AND	posbeg.pos_longitude BETWEEN (posbegask.pos_longitude -  __delta_deg_y) AND (posbegask.pos_longitude +  __delta_deg_y)
				
				AND	posend.pos_latitude BETWEEN (posendask.pos_latitude -  __delta_deg_x) AND (posendask.pos_latitude +  __delta_deg_x)
				AND	posend.pos_longitude BETWEEN (posendask.pos_longitude -  __delta_deg_y) AND (posendask.pos_longitude +  __delta_deg_y)	
				
				AND	rte_deletedate IS NULL
				AND rte_date_begin BETWEEN _begin_date_departure AND _end_date_departure
				AND (rte_type = _rtp_id OR _rtp_id = 0);
			

	END //




	DROP PROCEDURE IF EXISTS route_search_with_date_and_delta_using_subtraject //
	CREATE PROCEDURE route_search_with_date_and_delta_using_subtraject (
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
		/*
		SELECT ( ( _location_approximate_nb_meters / 100 ) * 0.0009) As tmp  INTO __delta_deg_x;
		SELECT ( ( _location_approximate_nb_meters / 100 ) * 0,0014) As tmp INTO __delta_deg_y;
		*/
		SELECT  _location_approximate_nb_meters / 100 * 0.0019  INTO __delta_deg_x;
		SELECT  _location_approximate_nb_meters / 100 * 0.0014 INTO __delta_deg_y;
		
			SELECT DISTINCT rte.* 
				FROM route_rte rte
					
					-- on jointe avec les positions de depart et d'arrive demandées
					INNER JOIN position_pos AS posbegask ON posbegask.pos_id = _position_begin_id
					INNER JOIN position_pos AS posendask ON posendask.pos_id = _position_end_id
					
					-- on jointe avec des segments
					INNER JOIN segment_seg AS segbeg ON segbeg.seg_route = rte.rte_id
					INNER JOIN position_pos AS posbeg ON posbeg.pos_id = segbeg.seg_pos_begin
					
					INNER JOIN segment_seg AS segend ON segend.seg_route = rte.rte_id
					INNER JOIN position_pos AS posend ON posend.pos_id = segend.seg_pos_end
					
					
				WHERE 
						posbeg.pos_latitude BETWEEN (posbegask.pos_latitude -  __delta_deg_x) AND (posbegask.pos_latitude +  __delta_deg_x)
					AND	posbeg.pos_longitude BETWEEN (posbegask.pos_longitude -  __delta_deg_y) AND (posbegask.pos_longitude +  __delta_deg_y)
					
					AND	posend.pos_latitude BETWEEN (posendask.pos_latitude -  __delta_deg_x) AND (posendask.pos_latitude +  __delta_deg_x)
					AND	posend.pos_longitude BETWEEN (posendask.pos_longitude -  __delta_deg_y) AND (posendask.pos_longitude +  __delta_deg_y)	
					AND ( (
					-- AND	
					rte.rte_deletedate IS NULL
					-- AND rte.rte_date_begin BETWEEN _begin_date_departure AND _end_date_departure
					AND segbeg.seg_date_begin BETWEEN _begin_date_departure AND _end_date_departure
					AND (rte_type = _rtp_id OR _rtp_id = 0)
					AND (segbeg.seg_order > segend.seg_order OR 1)
					) OR 0)
				
					;
		
			

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
				INNER JOIN user_usr usr ON psg.psg_user = usr.usr_id		
			WHERE 
					psg_route = _rte_id
				AND (psg_type = _pgt_id OR _pgt_id = 0 );
			

	END //


	DROP PROCEDURE IF EXISTS route_get_segments //
	CREATE PROCEDURE route_get_segments (
		IN _rte_id INT(11)
	)
	BEGIN

		SELECT seg.*, 
				pbeg.pos_id AS pbeg_pos_id,
				pbeg.pos_address AS pbeg_pos_address,
				pbeg.pos_latitude AS pbeg_pos_latitude,
				pbeg.pos_longitude AS pbeg_pos_longitude,
				
				pend.pos_id AS pend_pos_id,
				pend.pos_address AS pend_pos_address,
				pend.pos_latitude AS pend_pos_latitude,
				pend.pos_longitude AS pend_pos_longitude
				
				
			FROM  segment_seg seg
				INNER JOIN position_pos pbeg ON seg.seg_pos_begin = pbeg.pos_id
				INNER JOIN position_pos pend ON seg.seg_pos_end = pend.pos_id
			WHERE 
					seg.seg_route = _rte_id;
			

	END //




	DELIMITER ;



