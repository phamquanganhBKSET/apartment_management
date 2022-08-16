package model;

import java.sql.Date;

// Class summary for summary table of usermainpage, store electricity, water, environment bills for each month
public class ModelSummary {
	Date thang;
	int room;
	int electricityBill;
	int waterBill;
	int enviCharges;
	int vehicle;
	int total;
	
	public ModelSummary(Date thang, int room, int electricityBill, int waterBill, int enviCharges, int vehicle) {
		this.thang = thang;
		this.room = room;
		this.electricityBill = electricityBill;
		this.waterBill = waterBill;
		this.enviCharges = enviCharges;
		this.vehicle = vehicle;
		this.total = electricityBill + waterBill + enviCharges + vehicle;
	}
	
	public void updateTotal() {
		this.total = electricityBill + waterBill + enviCharges + vehicle;
	}
	
	public int getElectricityBill() {
		return electricityBill;
	}
	
	public int getEnviCharges() {
		return enviCharges;
	}
	
	public int getRoom() {
		return room;
	}
	
	public Date getThang() {
		return thang;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getVehicle() {
		return vehicle;
	}
	
	public int getWaterBill() {
		return waterBill;
	}
	
	public void updateElec(int money) {
		electricityBill += money;
	}
	
	public void updateWater(int money) {
		waterBill += money;
	}
	
	public void updateEnvi(int money) {
		enviCharges += money;
	}
	
	public void updateVehicle(int money) {
		vehicle += money;
	}
}
