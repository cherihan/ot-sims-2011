-- sims SQL V2, Sprint 2

-- DROP DATABASES

-- From Simon Minotto

set foreign_key_checks = 0;

DROP TABLE IF EXISTS comment_cmn;
DROP TABLE IF EXISTS googlecache_gch;
DROP TABLE IF EXISTS passager_psg;
DROP TABLE IF EXISTS user_fav_pos_ufp;
DROP TABLE IF EXISTS route_rte;
DROP TABLE IF EXISTS _route_type_rtp;
DROP TABLE IF EXISTS car_car;
DROP TABLE IF EXISTS position_pos;
DROP TABLE IF EXISTS usr_crt;
DROP TABLE IF EXISTS user_usr;
DROP TABLE IF EXISTS crt_crt;
DROP TABLE IF EXISTS _criterion_crt;
DROP TABLE IF EXISTS _criterion_type_ctt;

set foreign_key_checks = 1;