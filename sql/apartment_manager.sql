drop database apartment_manager;
create database apartment_manager;
use apartment_manager;

create table Admin (
	Id_admin varchar(20),
    So_dien_thoai varchar(15),
    Email varchar(40),
    Password varchar(20),
    primary key (Id_admin)
);

create table Chu_so_huu(
	Id_chu_so_huu varchar(20),
    CCCD varchar(20),
    Ten varchar(40),
    So_dien_thoai varchar(15),
    Email varchar(40),
    Gioi_tinh varchar(10),
    Password varchar(20),
    primary key (Id_chu_so_huu)
);

create table Phong(
	Ma_phong int not null,
    Id_chu_so_huu varchar(20),
    So_nguoi int,
    Loai_phong varchar(20),
    Trang_thai_phong bool,
    primary key (Ma_phong),
    foreign key (Id_chu_so_huu) references Chu_so_huu(Id_chu_so_huu)
);

create table Don_Gia_Dich_Vu(
	Ma_dich_vu int not null,
    Ten_dich_vu varchar(20),
    Don_gia_bac_1 float,
	Don_gia_bac_2 float,
	Don_gia_bac_3 float,
    Don_gia_bac_4 float,
    Don_gia_bac_5 float,
    Don_gia_bac_6 float,
    primary key (Ma_dich_vu)
);

create table Dich_Vu(
	Ma_phong int not null,
    Ma_dich_vu int not null,
    So_cu int,
    So_moi int,
    Thang date,
    Da_dong bool,
    primary key (Ma_phong, Ma_dich_vu, Thang),
    foreign key (Ma_phong) references Phong(Ma_phong),
    foreign key (Ma_dich_vu) references Don_Gia_Dich_Vu(Ma_dich_vu)
);

create table Don_Gia_Gui_Xe (
	Loai_xe varchar(20),
    Don_gia int,
    primary key (Loai_xe)
);

create table Xe(
	Ten_chu_xe varchar(40) not null,
    Ma_phong int not null,
    Loai_xe varchar(20),
    Bien_so_xe varchar(20),
    Mau_sac varchar(20),
    Thang date,
    Da_dong bool,
    primary key (Ten_chu_xe, Ma_phong, Loai_xe, Thang),
    foreign key (Ma_phong) references Phong(Ma_phong),
    foreign key (Loai_xe) references Don_Gia_Gui_Xe (Loai_xe)
);