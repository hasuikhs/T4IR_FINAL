package com.chavis.biz.vo;

public class CarVO {
	private int car_no;
	private String car_type;
	private String tire_change_distance;
	private String wiper_change_distance;
	private String engine_oil_viscosity;
	private String distance;
	private String car_id;
	private String car_color;
	private String cooler_left;
	private int member_no;

	public CarVO() {
	}

	public CarVO(int car_no, String car_type, String tire_change_distance, String wiper_change_distance,
			String engine_oil_viscosity, String distance, String car_id, String car_color, String cooler_left,
			int member_no) {
		this.car_no = car_no;
		this.car_type = car_type;
		this.tire_change_distance = tire_change_distance;
		this.wiper_change_distance = wiper_change_distance;
		this.engine_oil_viscosity = engine_oil_viscosity;
		this.distance = distance;
		this.car_id = car_id;
		this.car_color = car_color;
		this.cooler_left = cooler_left;
		this.member_no = member_no;
	}

	public int getCar_no() {
		return car_no;
	}

	public void setCar_no(int car_no) {
		this.car_no = car_no;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public String getTire_change_distance() {
		return tire_change_distance;
	}

	public void setTire_change_distance(String tire_change_distance) {
		this.tire_change_distance = tire_change_distance;
	}

	public String getWiper_change_distance() {
		return wiper_change_distance;
	}

	public void setWiper_change_distance(String wiper_change_distance) {
		this.wiper_change_distance = wiper_change_distance;
	}

	public String getEngine_oil_viscosity() {
		return engine_oil_viscosity;
	}

	public void setEngine_oil_viscosity(String engine_oil_viscosity) {
		this.engine_oil_viscosity = engine_oil_viscosity;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getCar_color() {
		return car_color;
	}

	public void setCar_color(String car_color) {
		this.car_color = car_color;
	}

	public String getCooler_left() {
		return cooler_left;
	}

	public void setCooler_left(String cooler_left) {
		this.cooler_left = cooler_left;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_color == null) ? 0 : car_color.hashCode());
		result = prime * result + ((car_id == null) ? 0 : car_id.hashCode());
		result = prime * result + car_no;
		result = prime * result + ((car_type == null) ? 0 : car_type.hashCode());
		result = prime * result + ((cooler_left == null) ? 0 : cooler_left.hashCode());
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((engine_oil_viscosity == null) ? 0 : engine_oil_viscosity.hashCode());
		result = prime * result + member_no;
		result = prime * result + ((tire_change_distance == null) ? 0 : tire_change_distance.hashCode());
		result = prime * result + ((wiper_change_distance == null) ? 0 : wiper_change_distance.hashCode());
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
		CarVO other = (CarVO) obj;
		if (car_color == null) {
			if (other.car_color != null)
				return false;
		} else if (!car_color.equals(other.car_color))
			return false;
		if (car_id == null) {
			if (other.car_id != null)
				return false;
		} else if (!car_id.equals(other.car_id))
			return false;
		if (car_no != other.car_no)
			return false;
		if (car_type == null) {
			if (other.car_type != null)
				return false;
		} else if (!car_type.equals(other.car_type))
			return false;
		if (cooler_left == null) {
			if (other.cooler_left != null)
				return false;
		} else if (!cooler_left.equals(other.cooler_left))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (engine_oil_viscosity == null) {
			if (other.engine_oil_viscosity != null)
				return false;
		} else if (!engine_oil_viscosity.equals(other.engine_oil_viscosity))
			return false;
		if (member_no != other.member_no)
			return false;
		if (tire_change_distance == null) {
			if (other.tire_change_distance != null)
				return false;
		} else if (!tire_change_distance.equals(other.tire_change_distance))
			return false;
		if (wiper_change_distance == null) {
			if (other.wiper_change_distance != null)
				return false;
		} else if (!wiper_change_distance.equals(other.wiper_change_distance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CarVO [car_color=" + car_color + ", car_id=" + car_id + ", car_no=" + car_no + ", car_type=" + car_type
				+ ", cooler_left=" + cooler_left + ", distance=" + distance + ", engine_oil_viscosity="
				+ engine_oil_viscosity + ", member_no=" + member_no + ", tire_change_distance=" + tire_change_distance
				+ ", wiper_change_distance=" + wiper_change_distance + "]";
	}

}
