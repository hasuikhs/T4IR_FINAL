package com.chavis.biz.vo;

public class WebTableVO {
	private String reservation_time;
	private String member_mname;
	private String member_phonenumber;
	private String car_id;
	private String car_type;
	private String key;
	private String repaired_person;
	private String repaired_time;
	
	public WebTableVO() {}
	
	
	public WebTableVO(String reservation_time, String member_mname, String member_phonenumber, String car_id,
			String car_type, String key, String repaired_person, String repaired_time) {
		super();
		this.reservation_time = reservation_time;
		this.member_mname = member_mname;
		this.member_phonenumber = member_phonenumber;
		this.car_id = car_id;
		this.car_type = car_type;
		this.key = key;
		this.repaired_person = repaired_person;
		this.repaired_time = repaired_time;
	}

	public String getReservation_time() {
		return reservation_time;
	}
	public void setReservation_time(String reservation_time) {
		this.reservation_time = reservation_time;
	}
	public String getMember_mname() {
		return member_mname;
	}
	public void setMember_mname(String member_mname) {
		this.member_mname = member_mname;
	}
	public String getMember_phonenumber() {
		return member_phonenumber;
	}
	public void setMember_phonenumber(String member_phonenumber) {
		this.member_phonenumber = member_phonenumber;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		if(key.equals("YES"))
			this.key = "예";
		else
			this.key="아니오";
	}
	public String getRepaired_person() {
		return repaired_person;
	}
	public void setRepaired_person(String repaired_person) {
		if(!repaired_person.equals("NO"))
			this.repaired_person = repaired_person;
		else
			this.repaired_person = "";
	}
	public String getRepaired_time() {
		return repaired_time;
	}
	public void setRepaired_time(String repaired_time) {
		if(!repaired_time.equals("NO"))
			this.repaired_time = repaired_time;
		else
			this.repaired_time = "";
	}


	@Override
	public String toString() {
		return "WebTableVO [reservation_time=" + reservation_time + ", member_mname=" + member_mname
				+ ", member_phonenumber=" + member_phonenumber + ", car_id=" + car_id + ", car_type=" + car_type
				+ ", key=" + key + ", repaired_person=" + repaired_person + ", repaired_time=" + repaired_time + "]";
	}
	
}
