package com.lcwd.electronicstore.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
	public String roleId;
	public String name;
	
	@ManyToMany(mappedBy = "roles", fetch= FetchType.LAZY)
	private List<User> users = new ArrayList<>();
}
