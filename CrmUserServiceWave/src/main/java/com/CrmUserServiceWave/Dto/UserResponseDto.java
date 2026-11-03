package com.CrmUserServiceWave.Dto;

import com.CrmUserServiceWave.Model.Roll;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class UserResponseDto {
	private int id;
	private String email;
	private String otp;
	private boolean isActive;
	private boolean isVerified;
	private Roll role;
}
