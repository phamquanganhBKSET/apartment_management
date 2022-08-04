package model;

import java.sql.Date;

public class ModelDichVu {
	int maPhong;
	int maDichVu;
	int socu, soMoi;
	Date thang;
	boolean daDong;
	
	public ModelDichVu(int maPhong, int maDichVu, int socu, int soMoi,	Date thang,	boolean daDong) {
		this.maPhong = maPhong;
		this.maDichVu = maDichVu;
		this.socu = socu;
		this.soMoi = soMoi;
		this.thang = thang;
		this.daDong = daDong;
	}
	
	
	
	
	public void setMaPhong(int maPhong) {
		this.maPhong = maPhong;
	}
	
	public void setMaDichVu(int maDichVu) {
		this.maDichVu = maDichVu;
	}
	
	public void setSocu(int socu) {
		this.socu = socu;
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
	
	public int getSocu() {
		return socu;
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
