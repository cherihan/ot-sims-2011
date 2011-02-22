-- sims SQL V2, Sprint 2

-- MINIMAL INSERT

-- From Simon Minotto


INSERT IGNORE INTO _route_type_rtp (rtp_id, rtp_label) VALUES 
(1, 'WantCar'),
(2,'ProvideCar');


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(1, 'Preference');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(1, 1, 'Trip in family', NULL, 1) ,
(2, 1, 'Hide my infos', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(2, 'Preference_music');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(3, 2, 'Preference_music_yes', NULL, 1) ,
(4, 2, 'Preference_music_no', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(3, 'Preference_smoke');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(5, 3, 'Preference_smoke_yes', NULL, 1) ,
(6, 3, 'Preference_smoke_no', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(4, 'Preference_animal');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(7, 4, 'Preference_animal_yes', NULL, 1) ,
(8, 4, 'Preference_animal_no', NULL, 1);


INSERT IGNORE INTO  _criterion_type_ctt (ctt_id, ctt_label) VALUES
(5, 'Preference_talking');

INSERT IGNORE INTO _criterion_crt (crt_id, crt_type, crt_label, crt_root_criterion, crt_order) VALUES 
(9, 5, 'Preference_talking_discret', NULL, 1) ,
(10, 5, 'Preference_talking_normal', NULL, 1),
(11, 5, 'Preference_talking_passionate', NULL, 1);

INSERT IGNORE INTO _passager_type_pgt (pgt_id, pgt_label) VALUES 
(1, 'Accepted'),
(2, 'Rejected'),
(3, 'Pending');