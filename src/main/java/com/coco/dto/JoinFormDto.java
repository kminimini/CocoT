package com.coco.dto;

import com.coco.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class JoinFormDto {

	
	private String id;
	private String name;
	private String ename;
	private String rrnumber;
	private String email;
	private String password;
	private String address;
	private String detailAddress;
	private Role role;
	private String enabled;
	private String phone;
	private String provider;
	
	@Builder
	public void JoinFormDto(String id, String name, String ename, String rrnumber, String email, String password,
			String address, String detailAddress, Role role, String enabled, String phone, String provider) {
		this.id = id;
		this.name = name;
		this.ename = ename;
		this.rrnumber = rrnumber;
		this.email = email;
		this.password = password;
		this.address = address;
		this.detailAddress = detailAddress;
		this.role = role;
		this.enabled = enabled;
		this.phone = phone;
		this.provider = provider;
	}

}