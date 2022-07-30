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
	Ma_phong varchar(20) not null,
    Id_chu_so_huu varchar(20),
    So_nguoi int,
    Loai_phong varchar(20),
    Trang_thai_phong bool,
    primary key (Ma_phong),
    foreign key (Id_chu_so_huu) references Chu_so_huu(Id_chu_so_huu)
);

create table Dich_Vu(
	Ma_dich_vu int not null,
    Ten_dich_vu varchar(20),
    Don_gia_bac_1 int,
	Don_gia_bac_2 int,
	Don_gia_bac_3 int,
    Don_gia_bac_4 int,
    primary key (Ma_dich_vu)
);

create table Hoa_Don_Dich_Vu(
	Ma_phong varchar(20) not null,
    Ma_dich_vu int not null,
    So_cu int,
    So_moi int,
    Tien_ve_sinh int,
    No_cu float,
    primary key (Ma_phong, Ma_dich_vu),
    foreign key (Ma_phong) references Phong(Ma_phong),
    foreign key (Ma_dich_vu) references Dich_Vu(Ma_dich_vu)
);

create table Xe(
	Ten_chu_xe varchar(40) not null,
    Loai_xe varchar(20),
    Bien_so_xe varchar(20),
    Mau_sac varchar(20),
    Don_gia_o_to int,
    Don_gia_xe_may int,
    Don_gia_xe_dap int,
    primary key (Ten_chu_xe)
);

create table Hoa_Don_Xe(
	Ten_chu_xe varchar(40) not null,
	Ma_phong varchar(20) not null,
    Tong_tien_xe int,
    primary key (Ten_chu_xe, Ma_phong),
    foreign key (Ten_chu_xe) references Xe(Ten_chu_xe),
    foreign key (Ma_phong) references Phong(Ma_phong)
);