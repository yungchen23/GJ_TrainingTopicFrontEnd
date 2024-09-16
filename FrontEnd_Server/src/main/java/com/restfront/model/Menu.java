package com.restfront.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

	@Id
	private Integer menu_id;

	private String menu_name;
	private Integer menu_price;
	private String menu_describe;
	private String menu_image;
	private Boolean menu_states;

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Menu(Integer menu_id, String menu_name, Integer menu_price, String menu_describe, String menu_image,
			Boolean menu_states) {
		super();
		this.menu_id = menu_id;
		this.menu_name = menu_name;
		this.menu_price = menu_price;
		this.menu_describe = menu_describe;
		this.menu_image = menu_image;
		this.menu_states = menu_states;
	}



	public Integer getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public Integer getMenu_price() {
		return menu_price;
	}

	public void setMenu_price(Integer menu_price) {
		this.menu_price = menu_price;
	}

	public String getMenu_describe() {
		return menu_describe;
	}

	public void setMenu_describe(String menu_describe) {
		this.menu_describe = menu_describe;
	}

	public String getMenu_image() {
		return menu_image;
	}

	public void setMenu_image(String menu_image) {
		this.menu_image = menu_image;
	}

	
	
	
	public Boolean getMenu_states() {
		return menu_states;
	}



	public void setMenu_states(Boolean menu_states) {
		this.menu_states = menu_states;
	}



	@Override
	public String toString() {
		return "Menu [menu_id=" + menu_id + ", menu_name=" + menu_name + ", menu_price=" + menu_price
				+ ", menu_describe=" + menu_describe + ", menu_image=" + menu_image + ", menu_states=" + menu_states
				+ "]";
	}





}
