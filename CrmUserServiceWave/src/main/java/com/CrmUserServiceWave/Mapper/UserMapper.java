package com.CrmUserServiceWave.Mapper;

import com.CrmUserServiceWave.Dto.UserLoginResponseDto;
import com.CrmUserServiceWave.Dto.UserRequestDto;
import com.CrmUserServiceWave.Dto.UserResponseDto;
import com.CrmUserServiceWave.Model.User;

public interface UserMapper {
	public UserResponseDto userToUserResponseDto(User user);
	public User userDtoToUser(UserRequestDto userDto);
	public UserLoginResponseDto useToUserLoginResponseDto(User userDto);

}
