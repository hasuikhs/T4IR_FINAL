package com.chavis.biz.vo;

public class MemberListVO {

	private int reservation_no;
	private int bodyshop_no;
	private String repaired_person;
	private String reservation_time;
	private String repaired_time;
	private String key;

	public MemberListVO() {

	}

	public MemberListVO(int reservation_no, int bodyshop_no, String repaired_person, String reservation_time,
			String repaired_time, String key) {
		this.reservation_no = reservation_no;
		this.bodyshop_no = bodyshop_no;
		this.repaired_person = repaired_person;
		this.reservation_time = reservation_time;
		this.repaired_time = repaired_time;
		this.key = key;
	}

	public int getReservation_no() {
		return reservation_no;
	}

	public void setReservation_no(int reservation_no) {
		this.reservation_no = reservation_no;
	}

	public int getBodyshop_no() {
		return bodyshop_no;
	}

	public void setBodyshop_no(int bodyshop_no) {
		this.bodyshop_no = bodyshop_no;
	}

	public String getRepaired_person() {
		return repaired_person;
	}

	public void setRepaired_person(String repaired_person) {
		this.repaired_person = repaired_person;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}