package model;

import java.sql.Date;

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
}
