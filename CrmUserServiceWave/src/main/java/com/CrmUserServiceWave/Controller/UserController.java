package com.CrmUserServiceWave.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrmUserServiceWave.Dto.ChangePasswordDto;
import com.CrmUserServiceWave.Dto.LoginDto;
import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Dto.UserLoginResponseDto;
import com.CrmUserServiceWave.Dto.UserRequestDto;
import com.CrmUserServiceWave.Dto.UserResponseDto;
import com.CrmUserServiceWave.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@RequiredArgsConstructor
public class UserController {
	public final UserService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<Message<UserResponseDto>> registerUser(@RequestBody UserRequestDto userDto) {
		Message<UserResponseDto> response = userService.registerUser(userDto);
		return ResponseEntity.ok(response);
	}
	@PostMapping("/login")
	public ResponseEntity<Message<UserLoginResponseDto>> loginUser(@RequestBody LoginDto userDto) {
		Message<UserLoginResponseDto> response = userService.loginUser(userDto);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/update")
	public ResponseEntity<Message<UserResponseDto>> updateUser(@RequestBody UserRequestDto userDto) {
		Message<UserResponseDto> response = userService.updateUser(userDto);
		return ResponseEntity.ok(response);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Message<UserResponseDto>> deleteUser(@PathVariable int id) {
		Message<UserResponseDto> response = userService.deleteUser(id);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/get/{email}")
	public ResponseEntity<Message<UserResponseDto>> getUserByEmail(@PathVariable String email) {
		Message<UserResponseDto> response = userService.getUserByEmail(email);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Message<UserResponseDto>>> getAllUsers() {
		List<Message<UserResponseDto>> response = userService.getAllUsers();
		return ResponseEntity.ok(response);
	}

	@PutMapping("/sendOTP/{email}")
	public ResponseEntity<Message<UserResponseDto>> sendOTP(@PathVariable String email) {
		Message<UserResponseDto> response = userService.sendOTP(email);
		return ResponseEntity.ok(response);
	}
	@PostMapping("/verifyOTP/{email}/{otp}")
	public ResponseEntity<Message<UserResponseDto>> verifyOTP(@PathVariable String email, @PathVariable String otp) {
		Message<UserResponseDto> response = userService.verifyOTP(email, otp);
		return ResponseEntity.ok(response);
	}
	@PostMapping("/resetPassword")
	public ResponseEntity<Message<UserResponseDto>> resetPassword(@RequestBody ChangePasswordDto changePasswordDto) {
		Message<UserResponseDto> response = userService.resetPassword(changePasswordDto);
		return ResponseEntity.ok(response);
	}
}
