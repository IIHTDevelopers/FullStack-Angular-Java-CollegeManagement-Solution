package com.collegemanagement.dto;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DepartmentDTO {
	private Long id;
	@NotEmpty
	@Size(min = 3, max = 100)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentDTO other = (DepartmentDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

}
