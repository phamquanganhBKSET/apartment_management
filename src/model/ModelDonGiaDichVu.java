package model;

// Class store values in Don_gia_dich_vu table
public class ModelDonGiaDichVu {
	double maDichVu;
	String tenDichVu;
	double donGiaBac1;
	double donGiaBac2;
	double donGiaBac3;
	double donGiaBac4;
	double donGiaBac5;
	double donGiaBac6;
	
	public ModelDonGiaDichVu(double maDichVu, String tenDichVu, double donGiaBac1, double donGiaBac2,double donGiaBac3, double donGiaBac4,	double donGiaBac5,	double donGiaBac6) {
		// TODO Auto-generated constructor stub
		this.maDichVu = maDichVu;
		this.donGiaBac1 = donGiaBac1;
		this.donGiaBac2 = donGiaBac2;
		this.donGiaBac3 = donGiaBac3;
		this.donGiaBac4 = donGiaBac4;
		this.donGiaBac5 = donGiaBac5;
		this.donGiaBac6 = donGiaBac6;
	}
	
	public void setMaDichVu(double maDichVu) {
		this.maDichVu = maDichVu;
	}
	
	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}
	
	public void setDonGiaBac1(double donGiaBac1) {
		this.donGiaBac1 = donGiaBac1;
	}
	
	public void setDonGiaBac2(double donGiaBac2) {
		this.donGiaBac2 = donGiaBac2;
	}
	
	public void setDonGiaBac3(double donGiaBac3) {
		this.donGiaBac3 = donGiaBac3;
	}
	
	public void setDonGiaBac4(double donGiaBac4) {
		this.donGiaBac4 = donGiaBac4;
	}
	
	public void setDonGiaBac5(double donGiaBac5) {
		this.donGiaBac5 = donGiaBac5;
	}
	
	public void setDonGiaBac6(double donGiaBac6) {
		this.donGiaBac6 = donGiaBac6;
	}
	
	public double getDonGiaBac1() {
		return donGiaBac1;
	}
	
	public double getDonGiaBac2() {
		return donGiaBac2;
	}
	
	public double getDonGiaBac3() {
		return donGiaBac3;
	}
	
	public double getDonGiaBac4() {
		return donGiaBac4;
	}
	
	public double getDonGiaBac5() {
		return donGiaBac5;
	}
	
	public double getDonGiaBac6() {
		return donGiaBac6;
	}
	
	public double getMaDichVu() {
		return maDichVu;
	}
	
	public String getTenDichVu() {
		return tenDichVu;
	}
	
}
