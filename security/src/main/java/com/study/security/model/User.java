package com.study.security.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="userTBL")
@SequenceGenerator(sequenceName = "userTBL_seq", allocationSize = 1, name = "User_SEQ")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "User_SEQ")
	private Long id;
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="enabled")
	private Boolean enabled;
	
	
	@ManyToMany
	@JoinTable(
		name = "user_roleTBL",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"))
	
	//기본 생성됐을때 nullPointException이 자주 발생되기때문에 
	//ArrayList를 통해서 기본을 채우고 간다(?)
	private List<Role> roles = new ArrayList<>();
}
