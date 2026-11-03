package com.CrmUserServiceWave.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class ChangePasswordDto {
	public String email;
	public String password;
	public String confirmPassword;
	public String otp;

}
