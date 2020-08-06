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

# trigger for max 30 players
DELIMITER //
Create Trigger kiemtracauthu after INSERT ON cauthu FOR EACH ROW
	BEGIN
		DECLARE socauthu int;
		DECLARE maxcauthu int;
        SET socauthu := 0;
        SET maxcauthu := 30;
        SELECT COUNT(*) INTO socauthu 
        FROM cauthu
        WHERE cauthu.tendoi = NEW.tendoi;
	IF socauthu > maxcauthu THEN begin signal sqlstate '45000' set message_text ='MyTriggerError: Vuot qua so cau thu trong 1 doi';
    end;
	END IF;
END //

# trigger for moi doi ko da qua 1 tran o moi vong
DELIMITER //
Create Trigger kiemtratrandau before INSERT ON trandau FOR EACH ROW
	BEGIN
        DECLARE sovong int;
        set sovong := 0;
        SELECT COUNT(*) INTO sovong 
        FROM trandau
        WHERE (trandau.doiCN = NEW.doiCN and trandau.mavong = new.mavong)
        OR (trandau.doiCN = NEW.doiK and trandau.mavong = new.mavong)
        or (trandau.doiK = NEW.doiK and trandau.mavong = new.mavong)
        or (trandau.doiK = NEW.doiCN and trandau.mavong = new.mavong);
	IF sovong = 1 THEN begin signal sqlstate '45000' set message_text ='MyTriggerError: Vong nay da co doi nay tham gia';
    end;
	END IF;
END //

# trigger for 2 doi gap nhau ko qua 4 lan
DELIMITER //
Create Trigger kiemtracaptrandau after INSERT ON trandau FOR EACH ROW
	BEGIN
        DECLARE sotrandau int;
        set sotrandau := 0;
        SELECT COUNT(*) INTO sotrandau 
        FROM trandau
        WHERE (trandau.doiCN = NEW.doiCN and trandau.doiK = new.doiK)
        OR (trandau.doiCN = NEW.doiK and trandau.doiK = new.doiCN);
	IF sotrandau > 4 THEN begin signal sqlstate '45000' set message_text ='MyTriggerError: 2 doi nay da tham gia du 4 tran dau';
    end;
	END IF;
END //

# moi tran dau co toi da 5 trong tai
DELIMITER //
Create Trigger kiemtratrongtai after INSERT ON dieukhien FOR EACH ROW
	BEGIN
		DECLARE sotrongtai int;
		DECLARE maxtrongtai int;
        SET sotrongtai := 0;
        SET maxtrongtai := 5;
        SELECT COUNT(*) INTO sotrongtai 
        FROM dieukhien
        WHERE dieukhien.matrongtai = NEW.matrongtai;
	IF sotrongtai > maxtrongtai THEN begin signal sqlstate '45000' set message_text ='MyTriggerError: Vuot qua so trong tai trong 1 tran dau';
    end;
	END IF;
END //

# Cap nhat diem va so loi
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
deilimiter ;
# Moi tran dau chi co toi da 10 cau thu
DELIMITER //
Create Trigger kiemtramaxcauthu1trandau after INSERT ON thamgia FOR EACH ROW
	BEGIN
		DECLARE socauthu1tran int;
		DECLARE maxcauthu1tran int;
        SET socauthu1tran := 0;
        SET maxcauthu1tran := 10;
        SELECT COUNT(thamgia.soao) INTO socauthu1tran 
        FROM thamgia
WHERE thamgia.matran = NEW.matran AND thamgia.tendoi = new.tendoi AND thamgia.mavong = new.mavong;
	IF socauthu1tran > maxcauthu1tran THEN begin signal sqlstate '45000' set message_text ='MyTriggerError: Vuot qua so cau thu trong 1 tran dau';
    end;
	END IF;
END //
# 1 mua giai co toi da 40 vong dau
DELIMITER //
Create Trigger kiemtravongdau after INSERT ON thamgia FOR EACH ROW
	BEGIN
		DECLARE sovongdau int;
		DECLARE maxvongdau int;
        SET sovongdau := 0;
        SET maxvongdau := 40;
        SELECT COUNT(*) INTO sovongdau 
        FROM thamgia
		GROUP BY thamgia.mavong;
	IF sovongdau > maxvongdau THEN begin signal sqlstate '45000' set message_text ='MyTriggerError: Vuot qua so vong dau trong 1 mua giai';
    end;
	END IF;
END //

# doi nha va doi khach khong duoc trung ten
DELIMITER //
Create Trigger kiemtraCNvaK before INSERT ON trandau FOR EACH ROW
	BEGIN
	IF (new.doiCN = new.doiK) THEN begin signal sqlstate '45000' set message_text ='MyTriggerError: Doi nha va doi khach khong the trung ten';
    end;
	END IF;
END //
/*nhập đội bóng rổ*/
insert into doi values('1');
insert into doi values('2');
insert into doi values('3');
insert into doi values('4');
insert into doi values('5');
insert into doi values('6');

/*cầu thủ đội 1*/
insert into cauthu values('CMT001','1','01','Nghiem Tuan Anh1A');
insert into cauthu values('CMT002','1','02','Nghiem Tuan Anh1B');
insert into cauthu values('CMT003','1','03','Nghiem Tuan Anh1C');
insert into cauthu values('CMT004','1','04','Nghiem Tuan Anh1D');
insert into cauthu values('CMT005','1','05','Nghiem Tuan Anh1E');
insert into cauthu values('CMT006','1','06','Nghiem Tuan Anh1F');
insert into cauthu values('CMT007','1','07','Nghiem Tuan Anh1G');
insert into cauthu values('CMT008','1','08','Nghiem Tuan Anh1H');
insert into cauthu values('CMT009','1','09','Nghiem Tuan Anh1I');
insert into cauthu values('CMT010','1','10','Nghiem Tuan Anh1J');
insert into cauthu values('CMT011','1','11','Nghiem Tuan Anh1K');
insert into cauthu values('CMT012','1','12','Nghiem Tuan Anh1L');
insert into cauthu values('CMT013','1','13','Nghiem Tuan Anh1M');
insert into cauthu values('CMT014','1','14','Nghiem Tuan Anh1N');
insert into cauthu values('CMT015','1','15','Nghiem Tuan Anh1O');

/*cầu thủ đội 2*/
insert into cauthu values('CMT016','2','01','Nghiem Tuan Anh2A');
insert into cauthu values('CMT017','2','02','Nghiem Tuan Anh2B');
insert into cauthu values('CMT018','2','03','Nghiem Tuan Anh2C');
insert into cauthu values('CMT019','2','04','Nghiem Tuan Anh2D');
insert into cauthu values('CMT020','2','05','Nghiem Tuan Anh2E');
insert into cauthu values('CMT021','2','06','Nghiem Tuan Anh2F');
insert into cauthu values('CMT022','2','07','Nghiem Tuan Anh2G');
insert into cauthu values('CMT023','2','08','Nghiem Tuan Anh2H');
insert into cauthu values('CMT024','2','09','Nghiem Tuan Anh2I');
insert into cauthu values('CMT025','2','10','Nghiem Tuan Anh2J');
insert into cauthu values('CMT026','2','11','Nghiem Tuan Anh2K');
insert into cauthu values('CMT027','2','12','Nghiem Tuan Anh2L');
insert into cauthu values('CMT028','2','13','Nghiem Tuan Anh2M');
insert into cauthu values('CMT029','2','14','Nghiem Tuan Anh2N');
insert into cauthu values('CMT030','2','15','Nghiem Tuan Anh2O');

/*cầu thủ đội 3*/
insert into cauthu values('CMT031','3','01','Nghiem Tuan Anh3A');
insert into cauthu values('CMT032','3','02','Nghiem Tuan Anh3B');
insert into cauthu values('CMT033','3','03','Nghiem Tuan Anh3C');
insert into cauthu values('CMT034','3','04','Nghiem Tuan Anh3D');
insert into cauthu values('CMT035','3','05','Nghiem Tuan Anh3E');
insert into cauthu values('CMT036','3','06','Nghiem Tuan Anh3F');
insert into cauthu values('CMT037','3','07','Nghiem Tuan Anh3G');
insert into cauthu values('CMT038','3','08','Nghiem Tuan Anh3H');
insert into cauthu values('CMT039','3','09','Nghiem Tuan Anh3I');
insert into cauthu values('CMT040','3','10','Nghiem Tuan Anh3J');
insert into cauthu values('CMT041','3','11','Nghiem Tuan Anh3K');
insert into cauthu values('CMT042','3','12','Nghiem Tuan Anh3L');
insert into cauthu values('CMT043','3','13','Nghiem Tuan Anh3M');
insert into cauthu values('CMT044','3','14','Nghiem Tuan Anh3N');
insert into cauthu values('CMT045','3','15','Nghiem Tuan Anh3O');

/*cầu thủ đội 4*/
insert into cauthu values('CMT046','4','01','Nghiem Tuan Anh4A');
insert into cauthu values('CMT047','4','02','Nghiem Tuan Anh4B');
insert into cauthu values('CMT048','4','03','Nghiem Tuan Anh4C');
insert into cauthu values('CMT049','4','04','Nghiem Tuan Anh4D');
insert into cauthu values('CMT050','4','05','Nghiem Tuan Anh4E');
insert into cauthu values('CMT051','4','06','Nghiem Tuan Anh4F');
insert into cauthu values('CMT052','4','07','Nghiem Tuan Anh4G');
insert into cauthu values('CMT053','4','08','Nghiem Tuan Anh4H');
insert into cauthu values('CMT054','4','09','Nghiem Tuan Anh4I');
insert into cauthu values('CMT055','4','10','Nghiem Tuan Anh4J');
insert into cauthu values('CMT056','4','11','Nghiem Tuan Anh4K');
insert into cauthu values('CMT057','4','12','Nghiem Tuan Anh4L');
insert into cauthu values('CMT058','4','13','Nghiem Tuan Anh4M');
insert into cauthu values('CMT059','4','14','Nghiem Tuan Anh4N');
insert into cauthu values('CMT060','4','15','Nghiem Tuan Anh4O');

/*cầu thủ đội 5*/
insert into cauthu values('CMT061','5','01','Nghiem Tuan Anh5A');
insert into cauthu values('CMT062','5','02','Nghiem Tuan Anh5B');
insert into cauthu values('CMT063','5','03','Nghiem Tuan Anh5C');
insert into cauthu values('CMT064','5','04','Nghiem Tuan Anh5D');
insert into cauthu values('CMT065','5','05','Nghiem Tuan Anh5E');
insert into cauthu values('CMT066','5','06','Nghiem Tuan Anh5F');
insert into cauthu values('CMT067','5','07','Nghiem Tuan Anh5G');
insert into cauthu values('CMT068','5','08','Nghiem Tuan Anh5H');
insert into cauthu values('CMT069','5','09','Nghiem Tuan Anh5I');
insert into cauthu values('CMT070','5','10','Nghiem Tuan Anh5J');
insert into cauthu values('CMT071','5','11','Nghiem Tuan Anh5K');
insert into cauthu values('CMT072','5','12','Nghiem Tuan Anh5L');
insert into cauthu values('CMT073','5','13','Nghiem Tuan Anh5M');
insert into cauthu values('CMT074','5','14','Nghiem Tuan Anh5N');
insert into cauthu values('CMT075','5','15','Nghiem Tuan Anh5O');
                                    
/*cầu thủ đội 6*/                   
insert into cauthu values('CMT076','6','01','Nghiem Tuan Anh6A');
insert into cauthu values('CMT077','6','02','Nghiem Tuan Anh6B');
insert into cauthu values('CMT078','6','03','Nghiem Tuan Anh6C');
insert into cauthu values('CMT079','6','04','Nghiem Tuan Anh6D');
insert into cauthu values('CMT080','6','05','Nghiem Tuan Anh6E');
insert into cauthu values('CMT081','6','06','Nghiem Tuan Anh6F');
insert into cauthu values('CMT082','6','07','Nghiem Tuan Anh6G');
insert into cauthu values('CMT083','6','08','Nghiem Tuan Anh6H');
insert into cauthu values('CMT084','6','09','Nghiem Tuan Anh6I');
insert into cauthu values('CMT085','6','10','Nghiem Tuan Anh6J');
insert into cauthu values('CMT086','6','11','Nghiem Tuan Anh6K');
insert into cauthu values('CMT087','6','12','Nghiem Tuan Anh6L');
insert into cauthu values('CMT088','6','13','Nghiem Tuan Anh6M');
insert into cauthu values('CMT089','6','14','Nghiem Tuan Anh6N');
insert into cauthu values('CMT090','6','15','Nghiem Tuan Anh6O');

/*1 gap 2*/

/*các trận vòng 1*/
insert into trandau values('vong1','tran1','1','2',0,0,0,0);
insert into trandau values('vong1','tran2','3','4',0,0,0,0);
insert into trandau values('vong1','tran3','5','6',0,0,0,0);

/*các trận vòng 2*/
insert into trandau values('vong2','tran1','1','2',0,0,0,0);
insert into trandau values('vong2','tran2','3','4',0,0,0,0);
insert into trandau values('vong2','tran3','5','6',0,0,0,0);

/*các trận vòng 3*/
insert into trandau values('vong3','tran1','1','2',0,0,0,0);
insert into trandau values('vong3','tran2','3','4',0,0,0,0);
insert into trandau values('vong3','tran3','5','6',0,0,0,0);

/*các trận vòng 4*/
insert into trandau values('vong4','tran1','1','2',0,0,0,0);
insert into trandau values('vong4','tran2','3','4',0,0,0,0);
insert into trandau values('vong4','tran3','5','6',0,0,0,0);

/*1 gap 3*/
/*các trận vòng 5*/
insert into trandau values('vong5','tran1','1','3',0,0,0,0);
insert into trandau values('vong5','tran2','2','5',0,0,0,0);
insert into trandau values('vong5','tran3','4','6',0,0,0,0);
                                 
/*các trận vòng 6*/
insert into trandau values('vong6','tran1','1','3',0,0,0,0);
insert into trandau values('vong6','tran2','2','5',0,0,0,0);
insert into trandau values('vong6','tran3','4','6',0,0,0,0);

/*các trận vòng 7*/
insert into trandau values('vong7','tran1','1','3',0,0,0,0);
insert into trandau values('vong7','tran2','2','5',0,0,0,0);
insert into trandau values('vong7','tran3','4','6',0,0,0,0);


/*các trận vòng 8*/
insert into trandau values('vong8','tran1','1','3',0,0,0,0);
insert into trandau values('vong8','tran2','2','5',0,0,0,0);
insert into trandau values('vong8','tran3','4','6',0,0,0,0);

/*1 gap 4*/
/*các trận vòng 9*/
insert into trandau values('vong9','tran1','1','4',0,0,0,0);
insert into trandau values('vong9','tran2','3','5',0,0,0,0);
insert into trandau values('vong9','tran3','2','6',0,0,0,0);

/*các trận vòng 10*/
insert into trandau values('vong10','tran1','1','4',0,0,0,0);
insert into trandau values('vong10','tran2','3','5',0,0,0,0);
insert into trandau values('vong10','tran3','2','6',0,0,0,0);

/*các trận vòng 11*/
insert into trandau values('vong11','tran1','1','4',0,0,0,0);
insert into trandau values('vong11','tran2','3','5',0,0,0,0);
insert into trandau values('vong11','tran3','2','6',0,0,0,0);

/*các trận vòng 12*/
insert into trandau values('vong12','tran1','1','4',0,0,0,0);
insert into trandau values('vong12','tran2','3','5',0,0,0,0);
insert into trandau values('vong12','tran3','2','6',0,0,0,0);

/*1 gap 5*/
/*các trận vòng 13*/
insert into trandau values('vong13','tran1','1','5',0,0,0,0);
insert into trandau values('vong13','tran2','2','4',0,0,0,0);
insert into trandau values('vong13','tran3','3','6',0,0,0,0);

/*các trận vòng 14*/
insert into trandau values('vong14','tran1','1','5',0,0,0,0);
insert into trandau values('vong14','tran2','2','4',0,0,0,0);
insert into trandau values('vong14','tran3','3','6',0,0,0,0);

/*các trận vòng 15*/
insert into trandau values('vong15','tran1','1','5',0,0,0,0);
insert into trandau values('vong15','tran2','2','4',0,0,0,0);
insert into trandau values('vong15','tran3','3','6',0,0,0,0);

/*các trận vòng 16*/
insert into trandau values('vong16','tran1','1','5',0,0,0,0);
insert into trandau values('vong16','tran2','2','4',0,0,0,0);
insert into trandau values('vong16','tran3','3','6',0,0,0,0);

/*1 gap 6*/
/*các trận vòng 17*/
insert into trandau values('vong17','tran1','1','6',0,0,0,0);
insert into trandau values('vong17','tran2','2','3',0,0,0,0);
insert into trandau values('vong17','tran3','4','5',0,0,0,0);

/*các trận vòng 18*/
insert into trandau values('vong18','tran1','1','6',0,0,0,0);
insert into trandau values('vong18','tran2','2','3',0,0,0,0);
insert into trandau values('vong18','tran3','4','5',0,0,0,0);

/*các trận vòng 19*/
insert into trandau values('vong19','tran1','1','6',0,0,0,0);
insert into trandau values('vong19','tran2','2','3',0,0,0,0);
insert into trandau values('vong19','tran3','4','5',0,0,0,0);

/*các trận vòng 20*/
insert into trandau values('vong20','tran1','1','6',0,0,0,0);
insert into trandau values('vong20','tran2','2','3',0,0,0,0);
insert into trandau values('vong20','tran3','4','5',0,0,0,0);


/*dữ liệu bảng tham gia*/
  /*vong 1 tran 1*/
     /*1 gap 2*/
insert into thamgia values('vong1','tran1','1','01',23,1);
insert into thamgia values('vong1','tran1','1','02',13,2);
insert into thamgia values('vong1','tran1','1','03',20,3);
insert into thamgia values('vong1','tran1','1','04',12,6);
insert into thamgia values('vong1','tran1','1','05',10,5);
insert into thamgia values('vong1','tran1','1','06',15,6);
insert into thamgia values('vong1','tran1','1','07',11,7);
insert into thamgia values('vong1','tran1','1','08',18,8);
insert into thamgia values('vong1','tran1','1','09',30,0);
insert into thamgia values('vong1','tran1','1','10',3,0);

insert into thamgia values('vong1','tran1','2','11',43,1);
insert into thamgia values('vong1','tran1','2','12',13,2);
insert into thamgia values('vong1','tran1','2','13',20,3);
insert into thamgia values('vong1','tran1','2','14',2,6);
insert into thamgia values('vong1','tran1','2','15',10,5);
insert into thamgia values('vong1','tran1','2','06',19,6);
insert into thamgia values('vong1','tran1','2','07',11,7);
insert into thamgia values('vong1','tran1','2','08',15,8);
insert into thamgia values('vong1','tran1','2','09',30,0);
insert into thamgia values('vong1','tran1','2','10',14,0);

   /*vong 1 tran 2*/
     /*3 gap 4*/

insert into thamgia values('vong1','tran2','3','01',10,1);
insert into thamgia values('vong1','tran2','3','02',20,2);
insert into thamgia values('vong1','tran2','3','03',20,3);
insert into thamgia values('vong1','tran2','3','14',2,6);
insert into thamgia values('vong1','tran2','3','15',50,5);
insert into thamgia values('vong1','tran2','3','06',15,6);
insert into thamgia values('vong1','tran2','3','07',11,8);
insert into thamgia values('vong1','tran2','3','11',18,0);
insert into thamgia values('vong1','tran2','3','09',11,0);
insert into thamgia values('vong1','tran2','3','10',13,0);

insert into thamgia values('vong1','tran2','4','11',3,1);
insert into thamgia values('vong1','tran2','4','12',33,2);
insert into thamgia values('vong1','tran2','4','13',29,0);
insert into thamgia values('vong1','tran2','4','14',21,6);
insert into thamgia values('vong1','tran2','4','15',0,5);
insert into thamgia values('vong1','tran2','4','06',9,8);
insert into thamgia values('vong1','tran2','4','07',11,7);
insert into thamgia values('vong1','tran2','4','08',15,8);
insert into thamgia values('vong1','tran2','4','09',21,0);
insert into thamgia values('vong1','tran2','4','10',14,0);


   /*vong 1 tran 3*/
     /*5 gap 6*/
insert into thamgia values('vong1','tran3','5','01',10,1);
insert into thamgia values('vong1','tran3','5','02',20,2);
insert into thamgia values('vong1','tran3','5','03',20,3);
insert into thamgia values('vong1','tran3','5','14',2,6);
insert into thamgia values('vong1','tran3','5','05',50,5);
insert into thamgia values('vong1','tran3','5','06',15,1);
insert into thamgia values('vong1','tran3','5','07',11,8);
insert into thamgia values('vong1','tran3','5','11',18,1);
insert into thamgia values('vong1','tran3','5','13',11,0);
insert into thamgia values('vong1','tran3','5','10',13,0);

insert into thamgia values('vong1','tran3','6','11',3,1);
insert into thamgia values('vong1','tran3','6','12',3,2);
insert into thamgia values('vong1','tran3','6','13',50,10);
insert into thamgia values('vong1','tran3','6','14',21,6);
insert into thamgia values('vong1','tran3','6','15',10,5);
insert into thamgia values('vong1','tran3','6','01',1,0);
insert into thamgia values('vong1','tran3','6','02',11,0);
insert into thamgia values('vong1','tran3','6','03',11,8);
insert into thamgia values('vong1','tran3','6','04',21,0);
insert into thamgia values('vong1','tran3','6','10',14,0);

