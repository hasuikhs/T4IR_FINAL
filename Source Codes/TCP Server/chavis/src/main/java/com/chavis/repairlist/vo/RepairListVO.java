package com.chavis.repairlist.vo;

public class RepairListVO {
	int repaires_list_no;
	String tire; 
	String wiper; 
	String cooler;
	String engine_oil;
	int reservation_no;
	
	public RepairListVO() {
		super();
	}

	public RepairListVO(String tire, String wiper, String cooler, String engine_oil,
			int reservation_no) {
		super();
		this.tire = tire;
		this.wiper = wiper;
		this.cooler = cooler;
		this.engine_oil = engine_oil;
		this.reservation_no = reservation_no;
	}

	public int getRepaires_list_no() {
		return repaires_list_no;
	}

	public void setRepaires_list_no(int repaires_list_no) {
		this.repaires_list_no = repaires_list_no;
	}

	public String getTire() {
		return tire;
	}

	public void setTire(String tire) {
		this.tire = tire;
	}

	public String getWiper() {
		return wiper;
	}

	public void setWiper(String wiper) {
		this.wiper = wiper;
	}

	public String getCooler() {
		return cooler;
	}

	public void setCooler(String cooler) {
		this.cooler = cooler;
	}

	public String getEngine_oil() {
		return engine_oil;
	}

	public void setEngine_oil(String engine_oil) {
		this.engine_oil = engine_oil;
	}

	public int getReservation_no() {
		return reservation_no;
	}

	public void setReservation_no(int reservation_no) {
		this.reservation_no = reservation_no;
	}
		
	
}
