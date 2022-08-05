package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import controller.library;
import javafx.scene.control.CheckBox;

public class ModelDichVu {
	int maPhong;
	int maDichVu;
	int soCu, soMoi;
	Date thang;
	boolean daDong;
	public int tienDien;
	public int tienNuoc;
	public int tienVS;
	CheckBox select;
		
	private ModelDonGiaDichVu dichVu1, dichVu2, dichVu3;

	
	public ModelDichVu(int maPhong, int maDichVu, int socu, int soMoi,	Date thang,	boolean daDong) {
		this.maPhong = maPhong;
		this.maDichVu = maDichVu;
		this.soCu = socu;
		this.soMoi = soMoi;
		this.thang = thang;
		this.daDong = daDong;
		this.select = new CheckBox();
		select.setSelected(daDong);
		select.setDisable(true);
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			Statement statement = connection.createStatement();
			String queryDonGiaDichVu = "SELECT * FROM apartment_manager.don_gia_dich_vu where apartment_manager.don_gia_dich_vu.Ma_dich_vu = 1;";
			ResultSet rs = statement.executeQuery(queryDonGiaDichVu);
			rs.next();
			dichVu1 = new ModelDonGiaDichVu(1, rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8));
			queryDonGiaDichVu = "SELECT * FROM apartment_manager.don_gia_dich_vu where apartment_manager.don_gia_dich_vu.Ma_dich_vu = 2;";
			rs = statement.executeQuery(queryDonGiaDichVu);
			rs.next();
			dichVu2 = new ModelDonGiaDichVu(2, rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), 0, 0);
			queryDonGiaDichVu = "SELECT * FROM apartment_manager.don_gia_dich_vu where apartment_manager.don_gia_dich_vu.Ma_dich_vu = 3;";
			rs = statement.executeQuery(queryDonGiaDichVu);
			rs.next();
			dichVu3 = new ModelDonGiaDichVu(3, rs.getString(2), rs.getDouble(3), 0, 0, 0, 0, 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		thanhTien(maDichVu, socu, soMoi);
	}
	
	public void thanhTien(int maDichVu,int soCu, int soMoi) {
		if (maDichVu == 1) {
			int sub = soMoi - soCu;
			if (sub > 400) {
				tienDien = (int)(((sub-400)*dichVu1.getDonGiaBac6() + 50*dichVu1.getDonGiaBac1()
						+ 50*dichVu1.getDonGiaBac2() + 100*dichVu1.getDonGiaBac3() + 100*dichVu1.getDonGiaBac4()
						+ 100*dichVu1.getDonGiaBac5())*1000);
			} else if (sub > 300) {
				tienDien = (int)(((sub-300)*dichVu1.getDonGiaBac5() + 50*dichVu1.getDonGiaBac1()
				+ 50*dichVu1.getDonGiaBac2() + 100*dichVu1.getDonGiaBac3() + 100*dichVu1.getDonGiaBac4())*1000);
			} else if (sub > 200) {
				tienDien = (int)(((sub-200)*dichVu1.getDonGiaBac4() + 50*dichVu1.getDonGiaBac1()
				+ 50*dichVu1.getDonGiaBac2() + 100*dichVu1.getDonGiaBac3())*1000);
			} else if (sub > 100) {
				tienDien = (int)(((sub-100)*dichVu1.getDonGiaBac3() + 50*dichVu1.getDonGiaBac1() + 50*dichVu1.getDonGiaBac2())*1000);
			} else if (sub > 50) {
				tienDien = (int)(((sub-50)*dichVu1.getDonGiaBac2() + 50*dichVu1.getDonGiaBac1())*1000);
			} else {
				tienDien = (int)((sub*dichVu1.getDonGiaBac1())*1000);
			}
			
			this.tienNuoc = 0;
			this.tienVS = 0;
		}
		else if (maDichVu == 2) {
			int sub = soMoi - soCu;
			if (sub > 30) {
				tienNuoc = (int)(((sub - 30)*dichVu2.getDonGiaBac4() + 10*dichVu2.getDonGiaBac3() + 
						10*dichVu2.getDonGiaBac2() + 10*dichVu2.getDonGiaBac1())*1000);
			} else if (sub > 20) {
				tienNuoc = (int)(((sub - 20)*dichVu2.getDonGiaBac3() + 10*dichVu2.getDonGiaBac2() + 	10*dichVu2.getDonGiaBac1())*1000);
			} else if (sub > 10) {
				tienNuoc = (int)(((sub - 10)*dichVu2.getDonGiaBac2() + 10*dichVu2.getDonGiaBac1())*1000);
			} else {
				tienNuoc = (int) ((sub * dichVu2.getDonGiaBac1())*1000);
			}
			
			this.tienDien = 0;
			this.tienVS = 0;
		}
		else {
			this.tienDien = 0;
			this.tienNuoc = 0;
			this.tienVS = (int)dichVu3.getDonGiaBac1();
		}
	}
	
	public int getTienDien() {
		return tienDien;
	}
	
	public int getTienNuoc() {
		return tienNuoc;
	}
	
	public int getTienVS() {
		return tienVS;
	}
	
	public CheckBox getSelect() {
		return select;
	}
	
	
	public int getTien() {
		return tienDien+tienNuoc+tienVS;
	}
	
	public void setMaPhong(int maPhong) {
		this.maPhong = maPhong;
	}
	
	public void setMaDichVu(int maDichVu) {
		this.maDichVu = maDichVu;
	}
	
	public void setSoCu(int soCu) {
		this.soCu = soCu;
	}
	
	public void setSoMoi(int soMoi) {
		this.soMoi = soMoi;
	}
	
	public void setThang(Date thang) {
		this.thang = thang;
	}
	
	public void setDaDong(boolean daDong) {
		this.daDong = daDong;
	}
	
	public int getMaDichVu() {
		return maDichVu;
	}
	
	public int getMaPhong() {
		return maPhong;
	}
	
	public int getSoCu() {
		return soCu;
	}
	
	public int getSoMoi() {
		return soMoi;
	}
	
	public Date getThang() {
		return thang;
	}
	
	public boolean getDaDong() {
		return daDong;
	}


}
