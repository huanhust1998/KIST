Create database `quanlybongro`;
use `quanlybongro`;
create table `doi`(
    tendoi varchar(255),
    primary key(tendoi)
);

create table `cauthu`(
    cmt varchar(255) not null unique,
    tendoi varchar(255) not null,
    soao int not null check(soao>=0 and soao<=99),
    tencauthu varchar(255) not null,
    primary key(tendoi,soao),
    foreign key(tendoi) references `doi`(tendoi)
);

create table `trandau`(
    mavong varchar(255) not null,
    matran varchar(255) not null,
    doiCN varchar(255) not null,
    doiK varchar(255) not null,
    diemCN int ,
    diemK int,
    soLoiCN int,
    soLoiK int,
    primary key(mavong,matran),
    foreign key(doiCN) references `doi`(tendoi),
    foreign key(doiK) references `doi`(tendoi)
);

create table `thamgia`(
    mavong varchar(255) not null,
    matran varchar(255) not null,
    tendoi varchar(255) not null,
    soao int not null,
    sodiem int not null,
    soloi int not null,
    primary key(mavong,matran,tendoi,soao),
    foreign key(mavong,matran) references `trandau`(mavong,matran),
    foreign key(tendoi,soao) references `cauthu`(tendoi,soao)
);

create table `trongtai`(
    matrongtai varchar(255) not null,
    tentrongtai varchar(255) not null,
    primary key(matrongtai)
);

create table `dieukhien`(
    matrongtai varchar(255) not null,
    mavong varchar(255) not null,
    matran varchar(255) not null,
    primary key(matrongtai,mavong,matran),
    foreign key(matrongtai) references `trongtai`(matrongtai),
    foreign key(mavong,matran) references `trandau`(mavong,matran)
);
/*mỗi đội bóng có tối đa 30 cầu thủ*/
delimiter //
create trigger kiemTraSocauThu after
insert on `cauthu`
for each row
begin
     declare soCauThu int;
     declare maxSoCauThu int;
	 set soCauThu := 0;
	 set maxSoCauThu := 30;
     
	Select count(*) into soCauThu
	from cauThu
	where cauthu.tendoi = new.tendoi;
	if soCauThu>maxSoCauThu
          then begin
               signal sqlstate '45000' set message_text='socauthu>30';
               #auto rollback;
		  end;
	end if;
end//
#dilimiter ;

/*1 đội chỉ được đá 1 trận trong 1 vòng*/

delimiter //
create trigger soTranTrongVong after
insert on `trandau`
for each row
begin
     declare soLanDa int;
     declare maxSoLanDa int;
	 set soLanDa := 0;
	 set maxSoLanDa := 1;
     
	Select count(*) into soLanDa
	from trandau
	where (trandau.doiCN = new.doiCN and trandau.mavong = new.mavong)
	    or(trandau.doiK = new.doiK and trandau.mavong = new.mavong)
        or(trandau.doiK = new.doiCN and trandau.mavong = new.mavong)
        or(trandau.doiCN = new.doiK and trandau.mavong = new.mavong);
	if soLanDa>maxSoLanDa
          then begin
               signal sqlstate '45000' set message_text='so lan da trong 1 vòng > 1';
               #auto rollback;
		  end;
	end if;
end//
#dilimiter ;
/*1 đội không được gặp đội khác quá 4 lần*/

delimiter //
create trigger soTranTrongMuaGiai after
insert on `trandau`
for each row
begin
     declare soLanDa int;
     declare maxSoLanDa int;
	 set soLanDa := 0;
	 set maxSoLanDa := 4;
     
	Select count(*) into soLanDa
	from trandau
	where (trandau.doiCN = new.doiCN )
	    or(trandau.doiK = new.doiK );
	if soLanDa>maxSoLanDa
          then begin
               signal sqlstate '45000' set message_text='so lan da trong mua giai > 4';
               #auto rollback;
		  end;
	end if;
end//
#dilimiter;

/*nhập điểm*/
delimiter //
create trigger updateDiem after
insert on `thamgia`
for each row
begin  
	update trandau set diemCN = diemCN + new.sodiem, soLoiCN = soLoiCN + new.soloi
    where trandau.mavong = new.mavong
    and trandau.matran = new.matran
    and trandau.doiCN = new.tendoi;
    
    update trandau set diemK = diemK + new.sodiem, soLoiK = soLoiK + new.soloi
    where trandau.mavong = new.mavong
    and trandau.matran = new.matran
    and trandau.doiK = new.tendoi;
end//
#dilimiter ;
/*tối đa 5 trọng tài*/
delimiter //
create trigger kiemTraSocauThu after
insert on `cauthu`
for each row
begin
     declare soCauThu int;
     declare maxSoCauThu int;
	 set soCauThu := 0;
	 set maxSoCauThu := 30;
     
	Select count(*) into soCauThu
	from cauThu
	where cauthu.tendoi = new.tendoi;
	if soCauThu>maxSoCauThu
          then begin
               signal sqlstate '45000' set message_text='socauthu>30';
               #auto rollback;
		  end;
	end if;
end//
#dilimiter ;

insert into doi values('hanoi');
insert into doi values('hanam');
insert into doi values('namdinh');
insert into doi values('ninhbinh');
insert into doi values('haiphong');
insert into doi values('thaibinh');



insert into cauthu values('CMT001','hanoi','20','Nghiem Tuan AnhA');
insert into cauthu values('CMT002','hanoi','30','Nghiem Tuan AnhB');
insert into cauthu values('CMT003','hanoi','40','Nghiem Tuan Bình');
insert into cauthu values('CMT004','hanoi','50','Nghiem Tuan Chieu');
insert into cauthu values('CMT005','hanoi','60','Nghiem Tuan Cuong');

insert into cauthu values('CMT006','hanam','21','Duong Tuan AnhA');
insert into cauthu values('CMT007','hanam','31','DuongTuan AnhB');
insert into cauthu values('CMT008','hanam','41','Duong Tuan Bình');
insert into cauthu values('CMT009','hanam','51','Duong Tuan Chieu');
insert into cauthu values('CMT010','hanam','61','Duong Tuan Cuong');

insert into cauthu values('CMT011','namdinh','22','Ha Tuan AnhA');
insert into cauthu values('CMT012','namdinh','32','Ha Tuan AnhB');
insert into cauthu values('CMT013','namdinh','42','Ha Tuan Bình');
insert into cauthu values('CMT014','namdinh','52','Ha Tuan Chieu');
insert into cauthu values('CMT015','namdinh','62','Ha Tuan Cuong');

insert into trandau values('V001','TD001','hanoi','hanam',0,0,0,0);
insert into trandau values('V002','TD001','hanoi','namdinh',0,0,0,0);
insert into trandau values('V003','TD001','hanoi','ninhbinh',0,0,0,0);
insert into trandau values('V004','TD001','hanoi','haiphong',0,0,0,0);

insert into thamgia values('V001','TD001','hanoi',20,50,3);
insert into thamgia values('V001','TD001','hanoi',30,50,3);
insert into thamgia values('V001','TD001','hanam',21,10,1);
insert into thamgia values('V001','TD001','hanam',31,80,10);

insert into thamgia values('V002','TD001','hanoi',40,50,3);
insert into thamgia values('V002','TD001','hanoi',50,50,3);
insert into thamgia values('V002','TD001','namdinh',22,10,1);
insert into thamgia values('V002','TD001','namdinh',32,50,1);


