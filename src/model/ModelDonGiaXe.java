package model;

// Class store values in Don_gia_xe table
public class ModelDonGiaXe {
	String loaiXe;
	int donGiaXe;
	
	public ModelDonGiaXe(String loaiXe, int donGiaXe) {
		this.loaiXe = loaiXe;
		this.donGiaXe = donGiaXe;
	}
	
	public void setDonGiaXe(int donGiaXe) {
		this.donGiaXe = donGiaXe;
	}
	
	public void setLoaiXe(String loaiXe) {
		this.loaiXe = loaiXe;
	}
	
	public int getDonGiaXe() {
		return donGiaXe;
	}
	
	public String getLoaiXe() {
		return loaiXe;
	}
	
}
