package com.restfront.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer member_id;
	
	private String member_name;
	private String member_sex;
	private String member_phone;
	private String member_mail;
	private String member_birth;
	private String member_address;

	@OneToOne
//	@MapsId		// 新增這一行，表示使用相同的主鍵
	@JoinColumn(name="member_id",referencedColumnName = "login_id")
	private LoginForMember loginformember;

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(
			Integer member_id, String member_name, String member_sex, String member_phone, String member_mail,
			String member_birth, String member_address
			) {
		super();
		this.member_id = member_id;
		this.member_name = member_name;
		this.member_sex = member_sex;
		this.member_phone = member_phone;
		this.member_mail = member_mail;
		this.member_birth = member_birth;
		this.member_address = member_address;
	}

	public Integer getMember_id() {
		return member_id;
	}

	public void setMember_id(Integer member_id) {
		this.member_id = member_id;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_sex() {
		return member_sex;
	}

	public void setMember_sex(String member_sex) {
		this.member_sex = member_sex;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_mail() {
		return member_mail;
	}

	public void setMember_mail(String member_mail) {
		this.member_mail = member_mail;
	}

	public String getMember_birth() {
		return member_birth;
	}

	public void setMember_birth(String member_birth) {
		this.member_birth = member_birth;
	}

	public String getMember_address() {
		return member_address;
	}

	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}

	public LoginForMember getLoginformember() {
		return loginformember;
	}

	public void setLoginformember(LoginForMember loginformember) {
		this.loginformember = loginformember;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", member_name=" + member_name + ", member_sex=" + member_sex
				+ ", member_phone=" + member_phone + ", member_mail=" + member_mail + ", member_birth=" + member_birth
				+ ", member_address=" + member_address + ", loginformember=" + loginformember + "]";
	}
	
	
	
}
