package com.restfront.model;

public class RegistrationForm {
	private String login_account;
	private String login_password;
	private String login_question;
	private String login_answer;
	private String member_phone; // 電話號碼
	private String member_name; // 姓名等其他資料
//	private String member_mail; // 電子信箱

	public String getLogin_account() {
		return login_account;
	}

	public void setLogin_account(String login_account) {
		this.login_account = login_account;
	}

	public String getLogin_password() {
		return login_password;
	}

	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}

	public String getLogin_question() {
		return login_question;
	}

	public void setLogin_question(String login_question) {
		this.login_question = login_question;
	}

	public String getLogin_answer() {
		return login_answer;
	}

	public void setLogin_answer(String login_answer) {
		this.login_answer = login_answer;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

//	public String getMember_mail() {
//		return member_mail;
//	}
//
//	public void setMember_mail(String member_mail) {
//		this.member_mail = member_mail;
//	}

}
