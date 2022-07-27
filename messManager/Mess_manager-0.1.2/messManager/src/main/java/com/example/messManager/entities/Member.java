package com.example.messManager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Member")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	@Column(unique = true)
	private String email;
	private String phone;
	private String mess_name;
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String password;
	private String role;
	private String imangeURL;
	
	@ManyToOne
	private Manager manager;
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Member() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getImangeURL() {
		return imangeURL;
	}

	public void setImangeURL(String imangeURL) {
		this.imangeURL = imangeURL;
	}

	public String getMessName() {
		return mess_name;
	}

	public void setMessName(String messName) {
		this.mess_name = messName;
	}
	
	public boolean hasRole(String roleName) {
		if (this.role.equals(roleName) ) {
			return true;
		}
		System.out.println(this.role.toString());
		return false;
	}
	
}
