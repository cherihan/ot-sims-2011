-- sims SQL V2, Sprint 2

-- Procedures stokes, route

-- From Simon Minotto


DELIMITER //


DROP PROCEDURE IF EXISTS comment_create_or_update //
CREATE PROCEDURE comment_create_or_update (
	IN _cmn_user_from INT(11),
	IN _cmn_user_to INT(11),
	IN _cmn_text TEXT,
	IN _cmn_note INT(11)
)
BEGIN
	DECLARE __cmn_id INT(11);
	
	INSERT INTO comment_cmn(
		cmn_id,
		cmn_user_from,
		cmn_user_to,
		cmn_text,
		cmn_note
	) VALUES (
		NULL,
		_cmn_user_from,
		_cmn_user_to,
		_cmn_text,
		_cmn_note
	) ON DUPLICATE KEY UPDATE cmn_text = _cmn_text, cmn_note = _cmn_note;
	
	SELECT cmn_id INTO __cmn_id FROM comment_cmn WHERE cmn_user_from = _cmn_user_from AND cmn_user_to = _cmn_user_to;
	
	
	call _comment_update_note_of_user(_cmn_user_to);
	
	SELECT * FROM comment_cmn WHERE cmn_id=__cmn_id;
	
END //


DROP PROCEDURE IF EXISTS _comment_update_note_of_user //
CREATE PROCEDURE _comment_update_note_of_user (
	IN _usr_id INT(11)
)
BEGIN
	DECLARE __avg_float DOUBLE;
	
	SELECT AVG(cmn_note) INTO __avg_float
	FROM comment_cmn 
	WHERE cmn_user_to = _usr_id 
		AND cmn_note IS NOT NULL 
		AND cmn_note <> 0;
		
		
	UPDATE user_usr SET usr_note = ROUND(__avg) WHERE usr_id = _usr_id;
	
END //


DROP PROCEDURE IF EXISTS comment_get_posted_by //
CREATE PROCEDURE comment_get_posted_by (
	IN _usr_id INT(11)
)
BEGIN

	SELECT * FROM comment_cmn WHERE cmn_user_from = _usr_id;
	
END //


DROP PROCEDURE IF EXISTS comment_get_posted_about //
CREATE PROCEDURE comment_get_posted_about (
	IN _usr_id INT(11)
)
BEGIN

	SELECT * FROM comment_cmn WHERE cmn_user_to = _usr_id;
	
END //

DROP PROCEDURE IF EXISTS comment_get_from_and_about //
CREATE PROCEDURE comment_get_from_and_about (
	IN _user_from_id INT(11),
	IN _user_to_id INT(11)
)
BEGIN

	SELECT * FROM comment_cmn WHERE cmn_user_from = _user_from_id AND cmn_user_to = _user_to_id;
	
END //




DROP PROCEDURE IF EXISTS comment_delete //
CREATE PROCEDURE comment_delete (
	IN _cmn_id INT(11)
)
BEGIN
	
	DECLARE __cmn_user_to INT(11);
	
	SELECT cmn_user_to INTO __cmn_user_to FROM comment_cmn WHERE cmn_id = _cmn_id;
	
	DELETE FROM comment_cmn WHERE cmn_id = _cmn_id;
	
	call _comment_update_note_of_user(__cmn_user_to);
	
END //




DELIMITER ;



