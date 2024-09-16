package com.restfront.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "loginformember")
public class LoginForMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer login_id;
	
	private String login_account;
	private String login_password;
	private String login_question;
	private String login_answer;
	private Boolean member_state;
	
	public LoginForMember() {
	}

	
	
	
	public LoginForMember(Integer login_id, String login_account, String login_password, String login_question,
			String login_answer, Boolean member_state) {
		super();
		this.login_id = login_id;
		this.login_account = login_account;
		this.login_password = login_password;
		this.login_question = login_question;
		this.login_answer = login_answer;
		this.member_state = member_state;
	}



	public Integer getLogin_id() {
		return login_id;
	}

	public void setLogin_id(Integer login_id) {
		this.login_id = login_id;
	}

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
	
	

	public Boolean getMember_state() {
		return member_state;
	}




	public void setMember_state(Boolean member_state) {
		this.member_state = member_state;
	}




	@Override
	public String toString() {
		return "LoginForMember [login_id=" + login_id + ", login_account=" + login_account + ", login_password="
				+ login_password + ", login_question=" + login_question + ", login_answer=" + login_answer + "]";
	}
	
}
