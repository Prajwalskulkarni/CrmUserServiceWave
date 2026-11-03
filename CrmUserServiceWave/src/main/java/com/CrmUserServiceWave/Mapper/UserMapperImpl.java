package com.CrmUserServiceWave.Mapper;

import org.springframework.stereotype.Component;

import com.CrmUserServiceWave.Dto.UserLoginResponseDto;
import com.CrmUserServiceWave.Dto.UserRequestDto;
import com.CrmUserServiceWave.Dto.UserResponseDto;
import com.CrmUserServiceWave.Model.User;

@Component
public class UserMapperImpl implements UserMapper {

	@Override
	public UserResponseDto userToUserResponseDto(User user) {
		return new UserResponseDto()
				.setId(user.getId())
				.setEmail(user.getEmail())
				.setRole(user.getRole())
				.setActive(user.isActive())
				.setVerified(user.isVerified());
	}

	@Override
	public User userDtoToUser(UserRequestDto userDto) {
		return new User()
				.setActive(false)
				.setEmail(userDto.getEmail())
				.setPassword(userDto.getPassword())
				.setRole(userDto.getRole())
				.setVerified(false);
	}

	@Override
	public UserLoginResponseDto useToUserLoginResponseDto(User userDto) {
		return new UserLoginResponseDto()
				.setId(userDto.getId())
				.setEmail(userDto.getEmail())
				.setRole(userDto.getRole())
				.setActive(userDto.isActive())
				.setVerified(userDto.isVerified());
	}

}
