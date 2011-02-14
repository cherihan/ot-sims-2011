-- sims SQL V2, Sprint 2

-- Procedures stokes - position

-- From Simon Minotto


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

