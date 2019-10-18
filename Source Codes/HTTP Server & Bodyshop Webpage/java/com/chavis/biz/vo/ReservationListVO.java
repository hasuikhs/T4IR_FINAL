package com.chavis.biz.vo;

public class ReservationListVO {
	private int reservation_no;
	private String key;
	private String key_expire_time;
	private String member_mname;
	private String member_no;
	private String car_type;
	private String car_id;
	private String reservation_time;
	private String repaired_time;
	private String repaired_person;

	public ReservationListVO() {
	}

	public ReservationListVO(int reservation_no, String key, String key_expire_time, String member_mname,
			String member_no, String car_type, String car_id, String reservation_time, String repaired_time,
			String repaired_person) {
		super();
		this.reservation_no = reservation_no;
		this.key = key;
		this.key_expire_time = key_expire_time;
		this.member_mname = member_mname;
		this.member_no = member_no;
		this.car_type = car_type;
		this.car_id = car_id;
		this.reservation_time = reservation_time;
		this.repaired_time = repaired_time;
		this.repaired_person = repaired_person;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public int getReservation_no() {
		return reservation_no;
	}

	public void setReservation_no(int reservation_no) {
		this.reservation_no = reservation_no;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey_expire_time() {
		return key_expire_time;
	}

	public void setKey_expire_time(String key_expire_time) {
		this.key_expire_time = key_expire_time;
	}

	public String getMember_mname() {
		return member_mname;
	}

	public void setMember_mname(String member_mname) {
		this.member_mname = member_mname;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getReservation_time() {
		return reservation_time;
	}

	public void setReservation_time(String reservation_time) {
		this.reservation_time = reservation_time;
	}

	public String getRepaired_time() {
		return repaired_time;
	}

	public void setRepaired_time(String repaired_time) {
		this.repaired_time = repaired_time;
	}

	public String getRepaired_person() {
		return repaired_person;
	}

	public void setRepaired_person(String repaired_person) {
		this.repaired_person = repaired_person;
	}

}