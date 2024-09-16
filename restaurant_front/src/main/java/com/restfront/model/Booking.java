package com.restfront.model;

import java.util.Objects;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {

	@Id
//		@NotNull
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String booking_id;
//		private Integer member_id;
	private String booking_date;

	private String booking_time;
	private Integer booking_adult;
	private Integer booking_kids;
	private String booking_remark;

	private String booking_name;
	private String booking_sex;
	private String booking_phone;
	private String booking_purpose;
	private String booking_states;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false) // 外鍵關聯到 Member 表
	private Member member;

	public Booking() {
	}

	public Booking(String booking_id, String booking_date, String booking_time, Integer booking_adult,
			Integer booking_kids, String booking_remark, String booking_name, String booking_sex, String booking_phone,
			String booking_purpose, String booking_states, Member member) {
		super();
		this.booking_id = booking_id;
		this.booking_date = booking_date;
		this.booking_time = booking_time;
		this.booking_adult = booking_adult;
		this.booking_kids = booking_kids;
		this.booking_remark = booking_remark;
		this.booking_name = booking_name;
		this.booking_sex = booking_sex;
		this.booking_phone = booking_phone;
		this.booking_purpose = booking_purpose;
		this.booking_states = booking_states;
		this.member = member;
	}

//		public Booking(String booking_id, Integer member_id, String booking_date, String booking_time,
//				Integer booking_adult, Integer booking_kids, String booking_remark, String booking_name,
//				String booking_sex, String booking_phone, String booking_purpose,String booking_states) {
//			super();
//			this.booking_id = booking_id;
//			this.member_id = member_id;
//			this.booking_date = booking_date;
//			this.booking_time = booking_time;
//			this.booking_adult = booking_adult;
//			this.booking_kids = booking_kids;
//			this.booking_remark = booking_remark;
//			this.booking_name = booking_name;
//			this.booking_sex = booking_sex;
//			this.booking_phone = booking_phone;
//			this.booking_purpose = booking_purpose;
//			this.booking_states = booking_states;
//		}

	public String getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(String booking_id) {
		this.booking_id = booking_id;
	}

//	public Integer getMember_id() {
//		return member_id;
//	}
//
//	public void setMember_id(Integer member_id) {
//		this.member_id = member_id;
//	}

	public String getBooking_date() {
		return booking_date;
	}

	public void setBooking_date(String booking_date) {
		this.booking_date = booking_date;
	}

	public String getBooking_time() {
		return booking_time;
	}

	public void setBooking_time(String booking_time) {
		this.booking_time = booking_time;
	}

	public Integer getBooking_adult() {
		return booking_adult;
	}

	public void setBooking_adult(Integer booking_adult) {
		this.booking_adult = booking_adult;
	}

	public Integer getBooking_kids() {
		return booking_kids;
	}

	public void setBooking_kids(Integer booking_kids) {
		this.booking_kids = booking_kids;
	}

	public String getBooking_remark() {
		return booking_remark;
	}

	public void setBooking_remark(String booking_remark) {
		this.booking_remark = booking_remark;
	}

	public String getBooking_name() {
		return booking_name;
	}

	public void setBooking_name(String booking_name) {
		this.booking_name = booking_name;
	}

	public String getBooking_sex() {
		return booking_sex;
	}

	public void setBooking_sex(String booking_sex) {
		this.booking_sex = booking_sex;
	}

	public String getBooking_phone() {
		return booking_phone;
	}

	public void setBooking_phone(String booking_phone) {
		this.booking_phone = booking_phone;
	}

	public String getBooking_purpose() {
		return booking_purpose;
	}

	public void setBooking_purpose(String booking_purpose) {
		this.booking_purpose = booking_purpose;
	}

	public String getBooking_states() {
		return booking_states;
	}

	public void setBooking_states(String booking_states) {
		this.booking_states = booking_states;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	
	
	
//	@Override
//	public String toString() {
//		return "Booking [booking_id=" + booking_id + ", member_id=" + member_id + ", booking_date=" + booking_date
//				+ ", booking_time=" + booking_time + ", booking_adult=" + booking_adult + ", booking_kids="
//				+ booking_kids + ", booking_remark=" + booking_remark + ", booking_name=" + booking_name
//				+ ", booking_sex=" + booking_sex + ", booking_phone=" + booking_phone + ", booking_purpose="
//				+ booking_purpose + ", booking_states=" + booking_states + "]";
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(booking_adult, booking_date, booking_id, booking_kids, booking_name, booking_phone,
//				booking_purpose, booking_remark, booking_sex, booking_states, booking_time, member_id);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Booking other = (Booking) obj;
//		return Objects.equals(booking_adult, other.booking_adult) && Objects.equals(booking_date, other.booking_date)
//				&& Objects.equals(booking_id, other.booking_id) && Objects.equals(booking_kids, other.booking_kids)
//				&& Objects.equals(booking_name, other.booking_name)
//				&& Objects.equals(booking_phone, other.booking_phone)
//				&& Objects.equals(booking_purpose, other.booking_purpose)
//				&& Objects.equals(booking_remark, other.booking_remark)
//				&& Objects.equals(booking_sex, other.booking_sex)
//				&& Objects.equals(booking_states, other.booking_states)
//				&& Objects.equals(booking_time, other.booking_time) && Objects.equals(member_id, other.member_id);
//	}

}