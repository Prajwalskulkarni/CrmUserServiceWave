package com.CrmUserServiceWave.Service;

import java.util.List;

import com.CrmUserServiceWave.Dto.ChangePasswordDto;
import com.CrmUserServiceWave.Dto.LoginDto;
import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Dto.UserLoginResponseDto;
import com.CrmUserServiceWave.Dto.UserRequestDto;
import com.CrmUserServiceWave.Dto.UserResponseDto;

public interface UserService {
	public Message<UserResponseDto> registerUser(UserRequestDto userDto);
	public Message<UserResponseDto> updateUser(UserRequestDto userDto);
	public Message<UserResponseDto> deleteUser(int id);
	public Message<UserResponseDto> getUser(int id);
	public List<Message<UserResponseDto>> getAllUsers();
	public Message<UserResponseDto> getUserByEmail(String email);
	public Message<UserResponseDto> sendOTP(String email);
	public Message<UserResponseDto> verifyOTP(String email,String otp);
	public Message<UserResponseDto> resetPassword(ChangePasswordDto changePasswordDto);
	public Message<UserLoginResponseDto> loginUser(LoginDto userDto);

}
