-- sims SQL V2, Sprint 2

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


DROP PROCEDURE IF EXISTS get_criterions_of_user //
CREATE PROCEDURE get_criterions_of_user (
	IN _usr_id INT(11)
)
BEGIN

	SELECT crt.*
	FROM usr_crt uc
		INNER JOIN _criterion_crt crt ON uc.crt_id = crt.crt_id
	WHERE uc.usr_id = _usr_id
	ORDER BY crt_order;

END //




DROP PROCEDURE IF EXISTS get_criterions_of_user_of_type //
CREATE PROCEDURE get_criterions_of_user_of_type (
	IN _usr_id INT(11),
	IN _ctt_id INT(11)
)
BEGIN
	SELECT crt.*
	FROM usr_crt uc
		INNER JOIN _criterion_crt crt ON uc.crt_id = crt.crt_id
	WHERE uc.usr_id = _usr_id
	AND crt.crt_type = _ctt_id
	ORDER BY crt_order;

END //

DELIMITER ;

