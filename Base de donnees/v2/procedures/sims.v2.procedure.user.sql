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
end //


-- Check if the tuple user - password is valid
-- Return the user if valid or NULL if not
DROP PROCEDURE IF EXISTS user_check_authentification //
CREATE PROCEDURE user_check_authentification (
	IN _usr_email VARCHAR(100),
	IN _usr_password_not_encrypted VARCHAR(100)
)
BEGIN
	
	SELECT * FROM user_usr WHERE usr_email=_usr_email AND usr_password = MD5(CONCAT(usr_id,_usr_password_not_encrypted));
	
end //


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
	
end //



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
	INSERT INTO user_usr 	(usr_email, usr_password, usr_firstname, usr_lastname, usr_genre) VALUES
							(_usr_email, '', _usr_firstname, _usr_lastname, _usr_genre);
	SELECT LAST_INSERT_ID() INTO _usr_id;
	
	call user_update_password(_usr_id, _usr_password_not_encrypted);
	
end //



DROP PROCEDURE IF EXISTS user_update_password //
CREATE PROCEDURE user_update_password (
	IN _usr_id INT(11),
	IN _usr_password_not_encrypted VARCHAR(100)
)
BEGIN	
	
	UPDATE user_usr SET usr_password = MD5(CONCAT(_usr_id,_usr_password_not_encrypted)) WHERE usr_id = _usr_id;
	
end //

