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



