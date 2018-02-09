use baza_korisnika;

## TABELE ZA GRUPE KORISNIKA ##

drop table if exists sk_programiranje_grupe;
drop table if exists sk_programiranje_pripadnost_grupi;

-- TABELA PODATAKA O GRUPI -- 
create table sk_programiranje_grupe(
	identifikator integer primary key auto_increment, 
	naziv_grupe varchar(100) unique not null, 
	administrator varchar(100) not null,
	
	constraint administracija
	foreign key (administrator) 
	references bk_programiranje_data(identifikator_korisnika)
);


-- TABELA VEZA KORISNIKA I GRUPE -- 
create table sk_programiranje_pripadnost_grupi(
	id_veze integer primary key auto_increment, 
	id_grupe integer not null,
	id_korisnika varchar(100) not null, 
	
	constraint korisnik
	foreign key (id_korisnika) 
	references bk_programiranje_data(identifikator_korisnika),

	constraint grupa
	foreign key (id_grupe) 
	references sk_programiranje_grupe(identifikator),

	constraint veza
	unique (id_grupe, id_korisnika)
);


## AUTOMATIZACIJA UNOSENJA I BRISANJA REKORDA ##

drop trigger if exists sk_progremiranje_dodavanje_grupe; 
drop trigger if exists sk_programiranje_brisanje_grupe; 

-- AKTIVNOSTI PRI DODAVANJU GRUPE -- 
-- DODAVANJE ADMINISTRATORA U CLANSTVO GRUPE -- 
delimiter %%
create trigger sk_programiranje_dodavanje_grupe 
after insert on sk_programiranje_grupe 
for each row 
begin 
	insert into sk_programiranje_pripadnost_grupi(id_grupe,id_korisnika)
    values (new.identifikator, new.administrator);
end%%
delimiter ;


-- AKTIVNOSTI PRI BRISANJU GRUPE -- 
-- BRISANJE SVIH CLANOVA IZ GRUPE -- 
delimiter %%
create trigger sk_programiranje_brisanje_grupe 
before delete on sk_programiranje_grupe
for each row 
begin 
	delete from sk_programiranje_pripadnost_grupi
    where id_grupe=old.identifikator;
end%%
delimiter ;


## PRILAGODJAVANJE PREGLEDA PODATAKA O GRUPAMA I NJENIM CLANOVIMA ##

drop view if exists sk_programiranje_pogled_pripadnosti;
drop view if exists sk_programiranje_pogeld_podaci_korisnika;
drop view if exists sk_programiranje_pogeld_podaci_administratora;


-- PREGLED VEZA PRIPADNSTI GRUPI UZ PODATKE U GRUPI -- 
create view sk_programiranje_pogled_pripadnosti as 
select id_veze, id_grupe, id_korisnika, naziv_grupe, administrator
from sk_programiranje_grupe join sk_programiranje_pripadnost_grupi
where identifikator = id_grupe;


-- PREGLED VEZA PRIPADNOSTI GRUPI UZ DETALJNE PODATKE O CLANOVIMA --
create view  sk_programiranje_pogeld_podaci_korisnika as 
select * from  sk_programiranje_pogled_pripadnosti join bk_programiranje_data
where id_korisnika = identifikator_korisnika;


-- PREGLED VEZA PRIPADNOSTI GRUPI UZ DETALJNE PODATKE O ADMINISTRATORU --
create view  sk_programiranje_pogeld_podaci_administratora as 
select * from  sk_programiranje_pogled_pripadnosti join bk_programiranje_data
where administrator = identifikator_korisnika;


## OSNOVNE FUNKCIONALNOSTI BAZE PODATAKA SISTEMA KORISNIKA ##

drop procedure if exists sk_programiranje_vlasnistvo; 
drop procedure if exists sk_programiranje_clanstvo;
drop procedure if exists sk_programiranje_kreiranje_grupe; 
drop procedure if exists sk_programiranje_brisanje_grupe;
drop procedure if exists sk_programiranje_uclanjenje; 
drop procedure if exists sk_programiranje_isclanjenje;


-- DOBIJANJE NAZIVA GRUPA CIJI JE ADMINISTRATOR DAT PARAMETROM-- 
-- PARAMETAR SE ODNOSI NA KORISNICKO IME ADMINISTRATORA --
delimiter %%
create procedure sk_programiranje_vlasnistvo
(in param_korisnik_username varchar(100))
begin 
	select distinct naziv_grupe 
    from sk_programiranje_pogeld_podaci_administratora
    where korisnicko_ime_korisnika = param_korisnik_username
    order by naziv_grupe;
end %%
delimiter ; 


-- DOBIJANJE NAZIVA GRUPA CIJI JE CLAN DAT PARAMETROM --
-- PARAMETAR SE ODNOSI NA KORISNICKO IME ADMINISTRATORA --
delimiter %%
create procedure sk_programiranje_clanstvo
(in param_korisnik_username varchar(100))
begin 
	select distinct naziv_grupe 
    from sk_programiranje_pogeld_podaci_korisnika
    where korisnicko_ime_korisnika = param_korisnik_username
    order by naziv_grupe;
end %%
delimiter ;


-- KREIRANJE NOVIH GRUPA --
-- PARAMETAR SE ODNOSI NA KORISNICKO IME ADMINISTRATORA --
-- ADMINISTRTOR MOZE BITI BILO KOJI REGISTROVAN KORISNIK --
-- NAZIV GRUPE NE SMIJE BITI VEC UPOTRIJEBLJEN --
delimiter %% 
create procedure sk_programiranje_kreiranje_grupe(
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
end %%
delimiter ; 



-- BRISANJE STARIH GRUPA --
-- NAZIV GRUPE NE SMIJE BITI POSTOJECI --
-- NE RADI SE PROVJERA PARAMETRA ADMINISTRATORA -- 
delimiter %% 
create procedure sk_programiranje_brisanje_grupe( 
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
end %%
delimiter ;





-- UCLANJENJE ZA BILO U GRUPU ZA BILO KOG REGISTROVANOG KORISNIKA --
-- PARAMETAR NAZIV GRUPE MORA BITI VAZECI -- 
-- KORISNIK SA PARAMETROM KORISNICKOG IMENA MORA EGZISTIRATI -- 
-- ISTI KORISNIK NE SMIJE BITI VEC CLAN TE GRUPE --  
delimiter %%
create procedure sk_programiranje_uclanjenje(
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
end %% 
delimiter ; 





-- ISCLANJENJE KORISNIKA CLANA GRUPE --
-- PARAMETAR NAZIV GRUPE MORA BITI VAZECI -- 
-- KORISNIK SA PARAMETROM KORISNICKOG IMENA MORA EGZISTIRATI U GRUPI -- 
delimiter %%
create procedure sk_programiranje_isclanjenje(
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
end %% 
delimiter ; 


## DODATNE PROCEDURE PROVJERE NA ZAHTIJEV ##

drop procedure if exists sk_programiranje_korisnici_grupe; 
drop procedure if exists sk_programiranje_podaci_grupe;
drop procedure if exists sk_programiranje_podaci_korisnika;
drop procedure if exists sk_programiranje_grupe; 


-- PROSLEDJUJE SE KORISNICKO IME KORISNIKA KOJI ZAHTIJEVA PODATKE -- 
-- PROSLEDJUJE SE NAZIV GRUPE U KOJOJ KORISNIK MORA BITI CLAN -- 
-- DOBIJA SE LISTA SVIH KORISNIKA GRUPE -- 
delimiter %%
create procedure sk_programiranje_korisnici_grupe(
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
end %% 
delimiter ; 





-- PROSLEDJUJE SE KORISNICKO IME KORISNIKA KOJI ZAHTIJEVA PODATKE -- 
-- PROSLEDJUJE SE NAZIV GRUPE U KOJOJ KORISNIK MORA BITI CLAN -- 
-- DOBIJA SE LISTA PODATAKA O GRUPI IZ TABELE GRUPA -- 
delimiter %%
create procedure sk_programiranje_podaci_grupe(
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
end %% 
delimiter ; 



-- PROSLEDJUJE SE KORISNICKO IME KORISNIKA KOJI ZAHTIJEVA PODATKE -- 
-- PROSLEDJUJE SE NAZIV GRUPE U KOJOJ KORISNIK MORA BITI CLAN --
-- DODATNO SE PROSLEDJUJE KORISNICKO IME CILJANOG KORISNIKA GRUPE --  
-- DOBIJA SE NJEGOVI PODACI IZ BAZE KORISNIKA -- 
delimiter %%
create procedure sk_programiranje_podaci_korisnika(
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
end %% 
delimiter ;


-- DOBIJANJE NAZIVA SVIH GRUPA -- 
delimiter %%
create procedure sk_programiranje_grupe()
begin 
	call sk_programiranje_osvezi();
    select distinct naziv_grupe from sk_programiranje_grupe
    order by naziv_grupe;
end %%
delimiter ;

## INTERNE POMOCNE PROCEDURE ##

drop procedure if exists sk_programiranje_osvezi; 

-- PROCEDURA ZA BRISANJE GRUPA NEPOSTOJECIH ADMINISTRATORA --
delimiter %%
create procedure sk_programiranje_osvezi()
begin
	delete from sk_programiranje_grupe
	where administrator not in 
	(select identifikator_korisnika from bk_programiranje_data);
    delete from sk_programiranje_pripadnost_grupi 
    where id_korisnika not in
    (select identifikator_korisnika from bk_programiranje_data); 
end %%
delimiter ;


## PROCEDURE PREIMENOVANJA ## 

drop procedure if exists sk_programiranje_preimenovanje_grupe;

-- PREIMENOVANJE GRUPE -- 
delimiter %% 
create procedure sk_programiranje_preimenovanje_grupe(
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
end %%
delimiter ;