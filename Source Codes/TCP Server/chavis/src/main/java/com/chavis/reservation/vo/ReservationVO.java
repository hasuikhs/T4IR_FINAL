package com.chavis.reservation.vo;

public class ReservationVO {
	private int reservation_no;
	private int bodyshop_no;
	private int member_no;
	private String key;
	private String key_expire_time;
	private String reservation_time;
	private String repaired_time;
	private String repaired_person;

	public ReservationVO() {
	}

	public ReservationVO(int reservation_no, int bodyshop_no, int member_no, String key, String key_expire_time,
			String reservation_time, String repaired_time, String repaired_person) {
		this.reservation_no = reservation_no;
		this.bodyshop_no = bodyshop_no;
		this.member_no = member_no;
		this.key = key;
		this.key_expire_time = key_expire_time;
		this.reservation_time = reservation_time;
		this.repaired_time = repaired_time;
		this.repaired_person = repaired_person;
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

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bodyshop_no;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((key_expire_time == null) ? 0 : key_expire_time.hashCode());
		result = prime * result + member_no;
		result = prime * result + ((repaired_time == null) ? 0 : repaired_time.hashCode());
		result = prime * result + ((repaired_person == null) ? 0 : repaired_person.hashCode());
		result = prime * result + reservation_no;
		result = prime * result + ((reservation_time == null) ? 0 : reservation_time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationVO other = (ReservationVO) obj;
		if (bodyshop_no != other.bodyshop_no)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (key_expire_time == null) {
			if (other.key_expire_time != null)
				return false;
		} else if (!key_expire_time.equals(other.key_expire_time))
			return false;
		if (member_no != other.member_no)
			return false;
		if (repaired_time == null) {
			if (other.repaired_time != null)
				return false;
		} else if (!repaired_time.equals(other.repaired_time))
			return false;
		if (repaired_person == null) {
			if (other.repaired_person != null)
				return false;
		} else if (!repaired_person.equals(other.repaired_person))
			return false;
		if (reservation_no != other.reservation_no)
			return false;
		if (reservation_time == null) {
			if (other.reservation_time != null)
				return false;
		} else if (!reservation_time.equals(other.reservation_time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReservationVO [bodyshop_no=" + bodyshop_no + ", car_no=" + key + ", key_expire_time="
				+ key_expire_time + ", member_no=" + member_no + ", repaired_time=" + repaired_time
				+ ", repaired_person=" + repaired_person + ", reservation_no=" + reservation_no + ", reservation_time="
				+ reservation_time + "]";
	}

}
