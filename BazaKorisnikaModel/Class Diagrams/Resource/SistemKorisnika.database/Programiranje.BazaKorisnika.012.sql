create database if not exists baza_korisnika default charset 'UTF8';  
use baza_korisnika; 

delete from tabela_baza_korisnika where ime_baze_korisnika='bk_programiranje_data'; 
insert into tabela_baza_korisnika(ime_baze_korisnika) values('bk_programiranje_data');

drop table if exists bk_programiranje_data;
create table bk_programiranje_data(
	identifikator_korisnika varchar(100) primary key,
    ime_korisnika varchar(100)  not null,
    prezime_korisnika varchar(100) not null,
    korisnicko_ime_korisnika varchar(100) unique not null,
    hash_sifre_korisnika varchar(100) not null,
    salt_sifre_korisnika varchar(100) not null unique check(length(salt_sifre_korisnika)>=8),
    adresa_korisnika varchar(100) default '',
    telefon_korisnika varchar(100) default '',
    radno_mjesto_korisnika varchar(100) default '',
    elpostakorisnika varchar(100) default '', 
    vebsajtovikorisnika varchar(100) default '', 
    tip_korisnika set('korisnik','administrator') 
		default 'korisnik'
);

alter table bk_programiranje_data add constraint jedan_administrator check (
(select count(*) from bk_programiranje_data where tip_korisnika='administrator')<2); 

drop procedure if exists bk_programiranje_promena_parametara; 
drop procedure if exists bk_programiranje_promena_sifre; 
drop procedure if exists bk_programiranje_promena_tipa; 

delimiter %% 
create procedure bk_programiranje_promena_parametara(
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
end %%
delimiter ; 


delimiter %% 
create procedure bk_programiranje_promena_sifre(
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
end %%
delimiter ;


delimiter %% 
create procedure bk_programiranje_promena_tipa(
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
end %%  
delimiter ;

delimiter %%
create trigger bk_programiranje_brisanje 
after delete on bk_programiranje_data
for each row 
begin 
	declare kraj boolean default false;
	declare continue handler for sqlexception set kraj=true;
	declare continue handler for not found set kraj=true;
	declare continue handler for sqlwarning set kraj=true;
    call sk_programiranje_osvezi();
end%%
delimiter ;



drop procedure if exists bk_programiranje_provera_sifre_korisnika;

delimiter %% 
create procedure bk_programiranje_provera_sifre_korisnika(
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
end %%
delimiter ;
