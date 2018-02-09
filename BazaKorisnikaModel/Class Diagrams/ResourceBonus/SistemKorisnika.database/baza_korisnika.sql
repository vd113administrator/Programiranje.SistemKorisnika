-- Adminer 4.2.5 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DELIMITER ;;

DROP PROCEDURE IF EXISTS `bk_programiranje_promena_parametara`;;
CREATE PROCEDURE `bk_programiranje_promena_parametara`(
	in param_staro_korisnicko_ime varchar(100), 
    in param_novo_korisnicko_ime varchar(100), 
	in param_ime varchar(100), 
	in param_prezime varchar(100), 
    in param_adresa varchar(100), 
    in param_telefon varchar(100), 
    in param_radno_mjesto varchar(100),
    in param_veb varchar(100),
    in param_email varchar(100)
)
begin 
	if param_staro_korisnicko_ime is null then
		select 0; 
    elseif param_staro_korisnicko_ime not in 
    (select korisnicko_ime_korisnika from bk_programiranje_data) then
		select 0; 
	elseif param_novo_korisnicko_ime <> param_staro_korisnicko_ime and param_novo_korisnicko_ime in 
    (select korisnicko_ime_korisnika from bk_programiranje_data) then
		select 0; 
    else if param_novo_korisnicko_ime is not null then
		update bk_programiranje_data 
			set korisnicko_ime_korisnika = param_novo_korisnicko_ime
			where korisnicko_ime_korisnika = param_staro_korisnicko_ime; 
		end if; 
		if param_ime is not null then 
			update bk_programiranje_data 
			set ime_korisnika = param_ime
			where korisnicko_ime_korisnika = param_novo_korisnicko_ime;
		end if;
		if param_prezime is not null then 
			update bk_programiranje_data 
			set prezime_korisnika = param_prezime
			where korisnicko_ime_korisnika = param_novo_korisnicko_ime;
		end if;
		if param_adresa is not null then 
			update bk_programiranje_data 
			set adresa_korisnika = param_adresa
			where korisnicko_ime_korisnika = param_novo_korisnicko_ime;
		end if;
		if param_telefon is not null then
			update bk_programiranje_data 
			set telefon_korisnika = param_telefon
			where korisnicko_ime_korisnika = param_novo_korisnicko_ime;
		end if;
		if param_veb is not null then
			update bk_programiranje_data 
			set vebsajtovikorisnika = param_veb
			where korisnicko_ime_korisnika = param_novo_korisnicko_ime;
		end if; 
		if param_email is not null then 
			update bk_programiranje_data 
			set elpostakorisnika = param_email
			where korisnicko_ime_korisnika = param_novo_korisnicko_ime;
		end if; 
        if param_radno_mjesto is not null then 
			update bk_programiranje_data
			set radno_mjesto_korisnika = param_radno_mjesto
			where korisnicko_ime_korisnika = param_novo_korisnicko_ime; 
		end if; 
		
		select 1;  
	end if; 
end;;

DROP PROCEDURE IF EXISTS `bk_programiranje_promena_sifre`;;
CREATE PROCEDURE `bk_programiranje_promena_sifre`(
	in param_staro_korisnicko_ime varchar(100), 
	in param_nova_sifra varchar(100),
    in param_novi_salt varchar(100)
)
begin 
	if param_staro_korisnicko_ime is null then 
		select 0; 
	elseif param_nova_sifra is null then 
		select 0;
	elseif param_novi_salt is null then
		select 0; 
	elseif (param_staro_korisnicko_ime, param_nova_sifra) in 
	(select korisnicko_ime_korisnika, hash_sifre_korisnika
     from bk_programiranje_data) then 
		select 0; 
	else 
		update bk_programiranje_data 
        set hash_sifre_korisnika = param_nova_sifra, 
		    salt_sifre_korisnika = param_novi_salt
		where korisnicko_ime_korisnika = param_staro_korisnicko_ime; 
		select 1; 
	end if;
end;;

DROP PROCEDURE IF EXISTS `bk_programiranje_promena_tipa`;;
CREATE PROCEDURE `bk_programiranje_promena_tipa`(
	in param_staro_korisnicko_ime varchar(100),
    in param_novi_tip varchar(100)
)
begin 
	if param_staro_korisnicko_ime is null then 
		select 0;
	elseif param_novi_tip is null then 
		select 0;
	else 
		update bk_tabela_korisnika
        set tip_korisnika = param_novi_tip
		where korisnicko_ime_korisnika = param_staro_korisnicko_ime; 
		select 1;
	end if; 
end;;

DROP PROCEDURE IF EXISTS `bk_programiranje_provera_sifre_korisnika`;;
CREATE PROCEDURE `bk_programiranje_provera_sifre_korisnika`(
	in param_korisnicko_ime varchar(100), 
	in param_kandidatska_sifra varchar(100)
)
begin 
	if param_korisnicko_ime is null then 
		select 0; 
	elseif param_kandidatska_sifra is null then 
		select 0;
	elseif (param_korisnicko_ime, param_kandidatska_sifra) in 
	(select korisnicko_ime_korisnika, hash_sifre_korisnika
     from bk_programiranje_data) then 
		select 1; 
	else 
		select 0;
	end if;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_brisanje_grupe`;;
CREATE PROCEDURE `sk_programiranje_brisanje_grupe`( 
    in param_administrator_username varchar(100), 
    in param_naziv_grupe varchar(100)
)
begin 
	declare ok integer;

	if not (param_administrator_username, param_naziv_grupe) in
	   (select korisnicko_ime_korisnika, naziv_grupe 
        from sk_programiranje_pogeld_podaci_administratora) then
			set ok = 0;
	else 
			delete from sk_programiranje_grupe 
			where naziv_grupe=param_naziv_grupe;
	
            set ok = 1; 
	end if;
    select ok; 
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_clanstvo`;;
CREATE PROCEDURE `sk_programiranje_clanstvo`(in param_korisnik_username varchar(100))
begin 
	select distinct naziv_grupe 
    from sk_programiranje_pogeld_podaci_korisnika
    where korisnicko_ime_korisnika = param_korisnik_username
    order by naziv_grupe;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_grupe`;;
CREATE PROCEDURE `sk_programiranje_grupe`()
begin 
	call sk_programiranje_osvezi();
    select distinct naziv_grupe from sk_programiranje_grupe
    order by naziv_grupe;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_isclanjenje`;;
CREATE PROCEDURE `sk_programiranje_isclanjenje`(
	in param_naziv_grupe varchar(100),
    in param_korisnik_username varchar(100) 
)
begin 
	declare var_id_user varchar(100);
    declare var_id_grupe integer; 
    declare ok integer;
    
    select identifikator_korisnika
	into var_id_user
	from bk_programiranje_data
	where korisnicko_ime_korisnika=param_korisnik_username;
	
    
    if var_id_user is null then 
		set ok = 0; 
	elseif not (param_korisnik_username, param_naziv_grupe) in
	   (select korisnicko_ime_korisnika, naziv_grupe 
        from sk_programiranje_pogeld_podaci_korisnika) then		
			set ok = 0;
	elseif (var_id_user, param_naziv_grupe) in 
		(select administrator, naziv_grupe 
         from sk_programiranje_grupe) then 
			 set ok = 0; 
	else 
			select identifikator
            into var_id_grupe
            from sk_programiranje_grupe 
            where naziv_grupe=param_naziv_grupe;
            
			delete from sk_programiranje_pripadnost_grupi
            where id_grupe=var_id_grupe and id_korisnika=var_id_user;
            set ok = 1; 
	end if;
    select ok;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_korisnici_grupe`;;
CREATE PROCEDURE `sk_programiranje_korisnici_grupe`(
	in param_korisnik_podataka varchar(100),
    in param_naziv_grupe varchar(100)
)
begin 
	declare ok integer; 
    call sk_programiranje_osvezi();
	if (param_naziv_grupe, param_korisnik_podataka) in 
    (select naziv_grupe, korisnicko_ime_korisnika from
    sk_programiranje_pogeld_podaci_korisnika) then 
		select korisnicko_ime_korisnika 
        from sk_programiranje_pogeld_podaci_korisnika 
        where naziv_grupe = param_naziv_grupe
        order by korisnicko_ime_korisnika;
	else 
		select korisnicko_ime_korisnika 
        from sk_programiranje_pogeld_podaci_korisnika 
        where false;
	end if;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_kreiranje_grupe`;;
CREATE PROCEDURE `sk_programiranje_kreiranje_grupe`(
	in param_administrator_username varchar(100), 
    in param_naziv_grupe varchar(100)
)
begin 
	declare ok integer;
	declare var_id_admin varchar(100); 
    if param_naziv_grupe in (select naziv_grupe from sk_programiranje_grupe) then 
		set ok = 0; 
	elseif (param_administrator_username,param_naziv_grupe) in
	   (select korisnicko_ime_korisnika, naziv_grupe 
        from sk_programiranje_pogeld_podaci_administratora) then		
			set ok = 0;
	elseif not param_administrator_username in 
		(select korisnicko_ime_korisnika from bk_programiranje_data) then
			set ok = 0;
    else 
			select identifikator_korisnika
            into var_id_admin
			from bk_programiranje_data
			where korisnicko_ime_korisnika=param_administrator_username;
		
			insert into sk_programiranje_grupe(naziv_grupe,administrator)
			values (param_naziv_grupe, var_id_admin);
        
			set ok = 1;
	end if; 
    select ok;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_osvezi`;;
CREATE PROCEDURE `sk_programiranje_osvezi`()
begin
	delete from sk_programiranje_grupe
	where administrator not in 
	(select identifikator_korisnika from bk_programiranje_data);
    delete from sk_programiranje_pripadnost_grupi 
    where id_korisnika not in
    (select identifikator_korisnika from bk_programiranje_data); 
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_podaci_grupe`;;
CREATE PROCEDURE `sk_programiranje_podaci_grupe`(
	in param_korisnik_podataka varchar(100),
    in param_naziv_grupe varchar(100)
)
begin 
	call sk_programiranje_osvezi();
	if (param_naziv_grupe, param_korisnik_podataka) in 
    (select naziv_grupe, korisnicko_ime_korisnika from
    sk_programiranje_pogeld_podaci_korisnika) then 
        select bk_programiranje_data.korisnicko_ime_korisnika, id_grupe, naziv_grupe
        from sk_programiranje_pogeld_podaci_korisnika, bk_programiranje_data
        where naziv_grupe=param_naziv_grupe 
        and sk_programiranje_pogeld_podaci_korisnika.korisnicko_ime_korisnika=param_korisnik_podataka
        and sk_programiranje_pogeld_podaci_korisnika.administrator=bk_programiranje_data.identifikator_korisnika
        order by naziv_grupe;
	else 
        select bk_programiranje_data.korisnicko_ime_korisnika, '' as id_grupe, naziv_grupe
        from sk_programiranje_pogeld_podaci_korisnika, bk_programiranje_data
		where naziv_grupe=param_naziv_grupe
        and sk_programiranje_pogeld_podaci_korisnika.korisnicko_ime_korisnika=param_korisnik_podataka;
	end if;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_podaci_korisnika`;;
CREATE PROCEDURE `sk_programiranje_podaci_korisnika`(
	in param_korisnik_podataka varchar(100),
    in param_naziv_grupe varchar(100),
    in param_trazeni_korisnik varchar(100) 
)
begin 
	call sk_programiranje_osvezi();
	if (param_naziv_grupe, param_korisnik_podataka) in 
    (select naziv_grupe, korisnicko_ime_korisnika from
            sk_programiranje_pogeld_podaci_korisnika) then 
            
		if  (param_naziv_grupe, param_trazeni_korisnik)  in 
		(select naziv_grupe, korisnicko_ime_korisnika from
				sk_programiranje_pogeld_podaci_korisnika) then
            select * from bk_programiranje_data 
            where korisnicko_ime_korisnika = param_trazeni_korisnik
            order by korisnicko_ime_korisnika;
        else
            select * from bk_programiranje_data 
            where false;
		end if;
	else 
		select * from bk_programiranje_data 
		where false;
	end if;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_preimenovanje_grupe`;;
CREATE PROCEDURE `sk_programiranje_preimenovanje_grupe`(
	in admin varchar(100),
    in staro_ime varchar(100), 
    in novo_ime varchar(100)
)
begin
	call sk_programiranje_osvezi();
    if admin is null then 
		select 0;
	elseif (staro_ime, admin) not in 
		(select naziv_grupe, korisnicko_ime_korisnika from
		sk_programiranje_pogeld_podaci_administratora) then 
        select 0; 
    elseif staro_ime is null then 
		select 0;
	elseif novo_ime is null then 
		select 0;
	elseif novo_ime in (select naziv_grupe from sk_programiranje_grupe) then 
		select 0; 
	else 
		update sk_programiranje_grupe
		set naziv_grupe = novo_ime
        where naziv_grupe = staro_ime;
        select 1;
	end if;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_uclanjenje`;;
CREATE PROCEDURE `sk_programiranje_uclanjenje`(
	in param_naziv_grupe varchar(100),
    in param_korisnik_username varchar(100)
)
begin 
	declare ok integer; 
	declare var_id_user varchar(100);
    declare var_id_grupe integer; 
	if(param_korisnik_username, param_naziv_grupe) in
	   (select korisnicko_ime_korisnika, naziv_grupe 
        from sk_programiranje_pogeld_podaci_korisnika) then		
			set ok = 0;
	else 
            select identifikator_korisnika 
            into var_id_user
			from bk_programiranje_data
			where korisnicko_ime_korisnika=param_korisnik_username;
            
            select identifikator 
            into var_id_grupe
            from sk_programiranje_grupe 
            where naziv_grupe = param_naziv_grupe; 
            
            if var_id_grupe is null then
				set ok = 0; 
            else  
				insert into sk_programiranje_pripadnost_grupi(id_grupe,id_korisnika)
				values (var_id_grupe, var_id_user);
				set ok = 1; 
			end if;
	end if;
    select ok;
end;;

DROP PROCEDURE IF EXISTS `sk_programiranje_vlasnistvo`;;
CREATE PROCEDURE `sk_programiranje_vlasnistvo`(in param_korisnik_username varchar(100))
begin 
	select distinct naziv_grupe 
    from sk_programiranje_pogeld_podaci_administratora
    where korisnicko_ime_korisnika = param_korisnik_username
    order by naziv_grupe;
end;;

DELIMITER ;

DROP TABLE IF EXISTS `bk_programiranje_data`;
CREATE TABLE `bk_programiranje_data` (
  `identifikator_korisnika` varchar(100) NOT NULL,
  `ime_korisnika` varchar(100) NOT NULL,
  `prezime_korisnika` varchar(100) NOT NULL,
  `korisnicko_ime_korisnika` varchar(100) NOT NULL,
  `hash_sifre_korisnika` varchar(100) NOT NULL,
  `salt_sifre_korisnika` varchar(100) NOT NULL,
  `adresa_korisnika` varchar(100) DEFAULT '',
  `telefon_korisnika` varchar(100) DEFAULT '',
  `radno_mjesto_korisnika` varchar(100) DEFAULT '',
  `elpostakorisnika` varchar(100) DEFAULT '',
  `vebsajtovikorisnika` varchar(100) DEFAULT '',
  `tip_korisnika` set('korisnik','administrator') DEFAULT 'korisnik',
  PRIMARY KEY (`identifikator_korisnika`),
  UNIQUE KEY `korisnicko_ime_korisnika` (`korisnicko_ime_korisnika`),
  UNIQUE KEY `salt_sifre_korisnika` (`salt_sifre_korisnika`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `bk_programiranje_data` (`identifikator_korisnika`, `ime_korisnika`, `prezime_korisnika`, `korisnicko_ime_korisnika`, `hash_sifre_korisnika`, `salt_sifre_korisnika`, `adresa_korisnika`, `telefon_korisnika`, `radno_mjesto_korisnika`, `elpostakorisnika`, `vebsajtovikorisnika`, `tip_korisnika`) VALUES
('marko',	'Marko',	'Markovic',	'marko',	'Ks/jzEIiLaNI/aNBEpGN1y5eKUTYCp+JhU9z1yxbAJQZU/8GRBGbejs/o42ru1Qw',	'20547667',	'Staro mjesto',	'',	'',	'Radno mjesto, pozicija',	'',	'korisnik'),
('petar',	'Petar',	'Petrovic',	'petar',	'QD8NZIKTYtE7iOKPm0GAZ2vp5hAC4hwGFZvUJBao0D0gJi471XAfpSGfJSWyboKe',	'68920679',	'',	'',	'Sluzbenik',	'',	'',	'korisnik'),
('filip',	'Filip',	'Filipovic',	'filip',	'E6uOMcjq9HEHnIMxDLtQKcvIE80pkCzmTkt3+jV3GKsTgUtpOEv9JqsL2wsY8ZEI',	'40250050',	'Filip',	'',	'',	'',	'',	'korisnik'),
('pero',	'Pero',	'Peric',	'pero',	'fdgsurpPPSpnR96nhivY7yOK45lWm3ZPETg57GjQWUEXtlRAGnov+03NQ02yzN0n',	'62022381',	'',	'',	'',	'',	'',	'korisnik'),
('jovan',	'Jovan',	'Jovanovic',	'Jovan',	'YB5s4oIFEaQ9IS7R2t+ts0Ons+xQoHT7hgU228gNdJbLseoD6gJXRrH/9D80wIuh',	'59114208',	'Beograd',	'',	'Wev developer',	'',	'',	'korisnik'),
('mitar',	'Mitar',	'Mitrovic',	'mitar',	'no5lcLV8QtZpuu0Ot0PHsZqsNzu94qObgIZnVxCCIPByuTlr4yI+vYYMhRnxLlJE',	'92612566',	'Novi Sad',	'',	'Tehnicar elektroenergetike',	'',	'',	'korisnik'),
('mario',	'Mario',	'Marinovic',	'mario',	'qrt3MU1imkCQmPGKXYKNPXjIl+FaXpvx9zh6IN0+82qyRdAw7/44APmMmPygkaB6',	'07019870',	'Pula',	'',	'Hotelijerstvo',	'',	'',	'korisnik'),
('dario',	'Dario',	'Marinovic',	'dario',	'0dcXKSg5r4NO5CYmO0k5XCBNKlP1T+6hjHoBcKIftlcVbOcyfR1E3enWHaslKSCx',	'55432905',	'Pula',	'',	'Vozac autobusa',	'',	'',	'korisnik'),
('smiljka',	'Smiljka',	'Smiljic',	'smiljka',	'6zc/hGNyao5ET5zwgDyj8oiU/JqNgiFhERdSE0HF5R9wsSskG1Bvve5G8ze2LuWf',	'94278518',	'Nis',	'',	'',	'ucitelj',	'',	'korisnik'),
('stevo',	'Stevo',	'Stevic',	'stevo',	'S8Ns5GwKG5XCgL2/eF0ul3Yq/VQi7uRwhJitd2faodD8w49mQkCm5OX8fX+oxDrN',	'37090643',	'Nis',	'',	'',	'Programer',	'',	'korisnik'),
('kojo',	'Kojo',	'Kojic',	'kojo',	'oPeXfXulY8CCTKyYnuog92w80l7zK2H6maUDmcibpdfJdC28U6KOVEE7jftLweB5',	'55161354',	'',	'',	'',	'',	'',	'korisnik');

DROP TABLE IF EXISTS `sk_programiranje_grupe`;
CREATE TABLE `sk_programiranje_grupe` (
  `identifikator` int(11) NOT NULL AUTO_INCREMENT,
  `naziv_grupe` varchar(100) NOT NULL,
  `administrator` varchar(100) NOT NULL,
  PRIMARY KEY (`identifikator`),
  UNIQUE KEY `naziv_grupe` (`naziv_grupe`),
  KEY `administracija` (`administrator`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `sk_programiranje_grupe` (`identifikator`, `naziv_grupe`, `administrator`) VALUES
(1,	'grupa.1',	'marko'),
(2,	'grupa.2',	'marko'),
(6,	'filip.1',	'filip');

DELIMITER ;;

CREATE TRIGGER `sk_programiranje_dodavanje_grupe` AFTER INSERT ON `sk_programiranje_grupe` FOR EACH ROW
begin 
	insert into sk_programiranje_pripadnost_grupi(id_grupe,id_korisnika)
    values (new.identifikator, new.administrator);
end;;

CREATE TRIGGER `sk_programiranje_brisanje_grupe` BEFORE DELETE ON `sk_programiranje_grupe` FOR EACH ROW
begin 
	delete from sk_programiranje_pripadnost_grupi
    where id_grupe=old.identifikator;
end;;

DELIMITER ;

DROP VIEW IF EXISTS `sk_programiranje_pogeld_podaci_administratora`;
CREATE TABLE `sk_programiranje_pogeld_podaci_administratora` (`id_veze` int(11), `id_grupe` int(11), `id_korisnika` varchar(100), `naziv_grupe` varchar(100), `administrator` varchar(100), `identifikator_korisnika` varchar(100), `ime_korisnika` varchar(100), `prezime_korisnika` varchar(100), `korisnicko_ime_korisnika` varchar(100), `hash_sifre_korisnika` varchar(100), `salt_sifre_korisnika` varchar(100), `adresa_korisnika` varchar(100), `telefon_korisnika` varchar(100), `radno_mjesto_korisnika` varchar(100), `elpostakorisnika` varchar(100), `vebsajtovikorisnika` varchar(100), `tip_korisnika` set('korisnik','administrator'));


DROP VIEW IF EXISTS `sk_programiranje_pogeld_podaci_korisnika`;
CREATE TABLE `sk_programiranje_pogeld_podaci_korisnika` (`id_veze` int(11), `id_grupe` int(11), `id_korisnika` varchar(100), `naziv_grupe` varchar(100), `administrator` varchar(100), `identifikator_korisnika` varchar(100), `ime_korisnika` varchar(100), `prezime_korisnika` varchar(100), `korisnicko_ime_korisnika` varchar(100), `hash_sifre_korisnika` varchar(100), `salt_sifre_korisnika` varchar(100), `adresa_korisnika` varchar(100), `telefon_korisnika` varchar(100), `radno_mjesto_korisnika` varchar(100), `elpostakorisnika` varchar(100), `vebsajtovikorisnika` varchar(100), `tip_korisnika` set('korisnik','administrator'));


DROP VIEW IF EXISTS `sk_programiranje_pogled_pripadnosti`;
CREATE TABLE `sk_programiranje_pogled_pripadnosti` (`id_veze` int(11), `id_grupe` int(11), `id_korisnika` varchar(100), `naziv_grupe` varchar(100), `administrator` varchar(100));


DROP TABLE IF EXISTS `sk_programiranje_pripadnost_grupi`;
CREATE TABLE `sk_programiranje_pripadnost_grupi` (
  `id_veze` int(11) NOT NULL AUTO_INCREMENT,
  `id_grupe` int(11) NOT NULL,
  `id_korisnika` varchar(100) NOT NULL,
  PRIMARY KEY (`id_veze`),
  UNIQUE KEY `veza` (`id_grupe`,`id_korisnika`),
  KEY `korisnik` (`id_korisnika`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `sk_programiranje_pripadnost_grupi` (`id_veze`, `id_grupe`, `id_korisnika`) VALUES
(1,	1,	'marko'),
(2,	2,	'marko'),
(13,	2,	'filip'),
(10,	6,	'filip'),
(12,	6,	'marko');

DROP TABLE IF EXISTS `tabela_baza_korisnika`;
CREATE TABLE `tabela_baza_korisnika` (
  `ime_baze_korisnika` varchar(50) NOT NULL,
  PRIMARY KEY (`ime_baze_korisnika`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `tabela_baza_korisnika` (`ime_baze_korisnika`) VALUES
('bk_programiranje_data');

DROP TABLE IF EXISTS `sk_programiranje_pogeld_podaci_administratora`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `sk_programiranje_pogeld_podaci_administratora` AS select `sk_programiranje_pogled_pripadnosti`.`id_veze` AS `id_veze`,`sk_programiranje_pogled_pripadnosti`.`id_grupe` AS `id_grupe`,`sk_programiranje_pogled_pripadnosti`.`id_korisnika` AS `id_korisnika`,`sk_programiranje_pogled_pripadnosti`.`naziv_grupe` AS `naziv_grupe`,`sk_programiranje_pogled_pripadnosti`.`administrator` AS `administrator`,`bk_programiranje_data`.`identifikator_korisnika` AS `identifikator_korisnika`,`bk_programiranje_data`.`ime_korisnika` AS `ime_korisnika`,`bk_programiranje_data`.`prezime_korisnika` AS `prezime_korisnika`,`bk_programiranje_data`.`korisnicko_ime_korisnika` AS `korisnicko_ime_korisnika`,`bk_programiranje_data`.`hash_sifre_korisnika` AS `hash_sifre_korisnika`,`bk_programiranje_data`.`salt_sifre_korisnika` AS `salt_sifre_korisnika`,`bk_programiranje_data`.`adresa_korisnika` AS `adresa_korisnika`,`bk_programiranje_data`.`telefon_korisnika` AS `telefon_korisnika`,`bk_programiranje_data`.`radno_mjesto_korisnika` AS `radno_mjesto_korisnika`,`bk_programiranje_data`.`elpostakorisnika` AS `elpostakorisnika`,`bk_programiranje_data`.`vebsajtovikorisnika` AS `vebsajtovikorisnika`,`bk_programiranje_data`.`tip_korisnika` AS `tip_korisnika` from (`sk_programiranje_pogled_pripadnosti` join `bk_programiranje_data`) where (`sk_programiranje_pogled_pripadnosti`.`administrator` = `bk_programiranje_data`.`identifikator_korisnika`);

DROP TABLE IF EXISTS `sk_programiranje_pogeld_podaci_korisnika`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `sk_programiranje_pogeld_podaci_korisnika` AS select `sk_programiranje_pogled_pripadnosti`.`id_veze` AS `id_veze`,`sk_programiranje_pogled_pripadnosti`.`id_grupe` AS `id_grupe`,`sk_programiranje_pogled_pripadnosti`.`id_korisnika` AS `id_korisnika`,`sk_programiranje_pogled_pripadnosti`.`naziv_grupe` AS `naziv_grupe`,`sk_programiranje_pogled_pripadnosti`.`administrator` AS `administrator`,`bk_programiranje_data`.`identifikator_korisnika` AS `identifikator_korisnika`,`bk_programiranje_data`.`ime_korisnika` AS `ime_korisnika`,`bk_programiranje_data`.`prezime_korisnika` AS `prezime_korisnika`,`bk_programiranje_data`.`korisnicko_ime_korisnika` AS `korisnicko_ime_korisnika`,`bk_programiranje_data`.`hash_sifre_korisnika` AS `hash_sifre_korisnika`,`bk_programiranje_data`.`salt_sifre_korisnika` AS `salt_sifre_korisnika`,`bk_programiranje_data`.`adresa_korisnika` AS `adresa_korisnika`,`bk_programiranje_data`.`telefon_korisnika` AS `telefon_korisnika`,`bk_programiranje_data`.`radno_mjesto_korisnika` AS `radno_mjesto_korisnika`,`bk_programiranje_data`.`elpostakorisnika` AS `elpostakorisnika`,`bk_programiranje_data`.`vebsajtovikorisnika` AS `vebsajtovikorisnika`,`bk_programiranje_data`.`tip_korisnika` AS `tip_korisnika` from (`sk_programiranje_pogled_pripadnosti` join `bk_programiranje_data`) where (`sk_programiranje_pogled_pripadnosti`.`id_korisnika` = `bk_programiranje_data`.`identifikator_korisnika`);

DROP TABLE IF EXISTS `sk_programiranje_pogled_pripadnosti`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `sk_programiranje_pogled_pripadnosti` AS select `sk_programiranje_pripadnost_grupi`.`id_veze` AS `id_veze`,`sk_programiranje_pripadnost_grupi`.`id_grupe` AS `id_grupe`,`sk_programiranje_pripadnost_grupi`.`id_korisnika` AS `id_korisnika`,`sk_programiranje_grupe`.`naziv_grupe` AS `naziv_grupe`,`sk_programiranje_grupe`.`administrator` AS `administrator` from (`sk_programiranje_grupe` join `sk_programiranje_pripadnost_grupi`) where (`sk_programiranje_grupe`.`identifikator` = `sk_programiranje_pripadnost_grupi`.`id_grupe`);

-- 2018-02-09 13:05:56
