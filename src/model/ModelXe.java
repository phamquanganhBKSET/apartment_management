package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import controller.library;

public class ModelXe {
	String tenChuXe;
	int maPhong;
	String loaiXe;
	String bienSoXe;
	String mauSac;
	Date thang;
	boolean daDong;
	int tienXe;
	
	public ModelXe(String tenChuXe, int maPhong, String loaiXe, String bienSoXe, String mauSac, Date thang, boolean daDong) {
		this.tenChuXe = tenChuXe;
		this.maPhong = maPhong;
		this.loaiXe = loaiXe;
		this.bienSoXe = bienSoXe;
		this.mauSac = mauSac;
		this.thang = thang;
		this.daDong = daDong;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment_manager", "root", library.password);
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM apartment_manager.don_gia_gui_xe where apartment_manager.don_gia_gui_xe.Loai_xe = \'" + loaiXe + "\'";
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			this.tienXe = rs.getInt(2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int getTienXe() {
		return tienXe;
	}
	
	public void setTenChuXe(String tenChuXe) {
		this.tenChuXe = tenChuXe;
	}
	
	public void setMaPhong(int maPhong) {
		this.maPhong = maPhong;
	}
	
	public void setLoaiXe(String loaiXe) {
		this.loaiXe = loaiXe;
	}
	
	public void setBienSoXe(String bienSoXe) {
		this.bienSoXe = bienSoXe;
	}
	
	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}
	
	public void setThang(Date thang) {
		this.thang = thang;
	}
	
	public void setDaDong(boolean daDong) {
		this.daDong = daDong;
	}
	
	public boolean getDaDong() {
		return daDong;
	}
	
	public String getBienSoXe() {
		return bienSoXe;
	}
	
	public String getLoaiXe() {
		return loaiXe;
	}
	
	public int getMaPhong() {
		return maPhong;
	}
	
	public String getMauSac() {
		return mauSac;
	}
	
	public String getTenChuXe() {
		return tenChuXe;
	}
	
	public Date getThang() {
		return thang;
	}
		
}
