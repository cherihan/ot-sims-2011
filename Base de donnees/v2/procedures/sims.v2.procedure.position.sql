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
	
	call _position_get_by_address(_address,__pos_id);
	
	SELECT * FROM position_pos WHERE pos_id=__pos_id;
end //

-- Return position id of an address or nothing if it's not found
DROP PROCEDURE IF EXISTS _position_get_by_address //
CREATE PROCEDURE _position_get_by_address (
	IN _address VARCHAR(255),
	OUT _pos_id INT(11)
)
BEGIN
	SELECT pos_id INTO _pos_id FROM position_pos WHERE pos_address = _address LIMIT 1;
end //



-- Store a new position
DROP PROCEDURE IF EXISTS position_create_or_update //
CREATE PROCEDURE position_create_or_update (
	IN _address VARCHAR(255),
	IN _latitude FLOAT(10,6),
	IN _longitude FLOAT(10,6)
)
BEGIN
	
	DECLARE __pos_id INT(11);
	SELECT NULL INTO __pos_id;
	
	IF _address IS NOT NULL THEN
		SELECT pos_id INTO __pos_id FROM position_pos WHERE pos_address IS NOT NULL AND pos_address= _address;
	END IF;
	
	IF __pos_id IS NULL THEN
		SELECT pos_id INTO __pos_id FROM position_pos WHERE pos_latitude= _latitude AND pos_longitude= _longitude;
	END IF;
	
	IF __pos_id IS NULL THEN
		INSERT INTO position_pos 	(pos_id, pos_address, pos_latitude, pos_longitude) VALUES 
									(NULL, _address, _latitude, _longitude);
		SELECT LAST_INSERT_ID() INTO __pos_id;
	END IF;
	
	SELECT * FROM position_pos WHERE pos_id = __pos_id;
	
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

	SELECT * FROM googlecache_gch WHERE gch_address = _address;
	
end //



-- Return position of an address or nothing if it's not found
DROP PROCEDURE IF EXISTS googlecache_get_by_coords //
CREATE PROCEDURE googlecache_get_by_coords (
	IN _longitude FLOAT(10,6),
	IN _latitude FLOAT(10,6)
)
BEGIN

	SELECT * FROM googlecache_gch WHERE gch_longitude = _longitude AND gch_latitude = _latitude;
	
end //

DELIMITER ;

