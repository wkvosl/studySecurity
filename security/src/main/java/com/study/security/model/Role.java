package com.study.security.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="roleTBL")
@SequenceGenerator(sequenceName = "roleTBL_seq", allocationSize = 1, name = "Role_SEQ")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="Role_SEQ" )
	private Long id;
	@Column(name="name")
	private String name;
	
	
	@ManyToMany(mappedBy = "roles") //User -> private List<Role> roles의 
									//roles와 동일한 설정으로 가져오겠다. 양방향 mapping
	private List<User> users;
	
	
}
