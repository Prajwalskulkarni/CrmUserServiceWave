package com.CrmUserServiceWave.Serviceimpl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.CrmUserServiceWave.Dto.ChangePasswordDto;
import com.CrmUserServiceWave.Dto.LoginDto;
import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Dto.UserLoginResponseDto;
import com.CrmUserServiceWave.Dto.UserRequestDto;
import com.CrmUserServiceWave.Dto.UserResponseDto;
import com.CrmUserServiceWave.Mapper.UserMapperImpl;
import com.CrmUserServiceWave.Model.Roll;
import com.CrmUserServiceWave.Model.User;
import com.CrmUserServiceWave.Repository.RoleRepository;
import com.CrmUserServiceWave.Repository.UserRepository;
import com.CrmUserServiceWave.Service.UserService;
import com.CrmUserServiceWave.Util.Constants;
import com.CrmUserServiceWave.Util.OTPGenerateService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
public final UserMapperImpl usserMapperImpl;
public final UserRepository userRepository;
public final EmailService emailService;
public final OTPGenerateService otpService;
private final Configuration config;
private final RoleRepository roleRepository;

@Value("${spring.mail.username}")
private String sender;
@Value("${spring.mail.password}")
private String password;

	@Override
	public Message<UserResponseDto> registerUser(UserRequestDto userDto) {
		Message<UserResponseDto> message = new Message<UserResponseDto>();
		User user=userRepository.findByEmail(userDto.getEmail());
		Roll role = roleRepository.findById(userDto.getRole().getId()).orElse(null);
		try {
			if (user != null && user.getEmail().equals(userDto.getEmail())) {
				message.setStatus(HttpStatus.CONFLICT);
				message.setResponseMessage(Constants.USER_ALREADY_EXISTS);
				return message;	
			}
			
			if (role==null) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.INVALID_ROLE);
				return message;
			}
			user = usserMapperImpl.userDtoToUser(userDto);
			String otp = otpService.generateOTP();
			Properties props = new Properties();

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sender, password);
				}
			});
			MimeMessage mailMessage = new MimeMessage(session);

			MimeMessageHelper helper = new MimeMessageHelper(mailMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("request1", otp);
			model.put("user", user);
			Template t = config.getTemplate("Otp.html");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//			 SimpleMailMessage mailMessage = new SimpleMailMessage();
	            helper.setFrom(sender);
	            helper.setTo(user.getEmail());
	            helper.setSubject("Forgot Password!!");
	            helper.setText(html,true);
			 
			user.setOtp(otp);
			emailService.sendEmail(mailMessage);
			userRepository.save(user);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.USER_CREATED_SUCCESSFULLY);
			message.setData(usserMapperImpl.userToUserResponseDto(user));
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<UserResponseDto> updateUser(UserRequestDto userDto) {
		Message<UserResponseDto> message = new Message<UserResponseDto>();
		User user= userRepository.findByEmail(userDto.getEmail());
		try {
			if (user == null ||!user.isActive()) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
			
			user = usserMapperImpl.userDtoToUser(userDto);
			userRepository.save(user);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.USER_UPDATED_SUCCESSFULLY);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<UserResponseDto> deleteUser(int id) {
		Message<UserResponseDto> message = new Message<UserResponseDto>();
		try {
			User user= userRepository.findById(id).get();
			user.setActive(false);
			userRepository.save(user);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.USER_DELETED_SUCCESSFULLY);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<UserResponseDto> getUser(int id) {
		Message<UserResponseDto> message = new Message<UserResponseDto>();
		User user = userRepository.findById(id).orElse(null);
		try {
			if (user == null ||!user.isActive()) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.USER_FOUND_SUCCESSFULLY);
			message.setData(usserMapperImpl.userToUserResponseDto(user));
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public List<Message<UserResponseDto>> getAllUsers() {
		List<Message<UserResponseDto>> response = new ArrayList<>();
		try {
			List<User> users = userRepository.findAll().stream().filter(user -> user.isActive() == true).collect(Collectors.toList());
			for (User user : users) {
				Message<UserResponseDto> msg = new Message<UserResponseDto>();
				msg.setStatus(HttpStatus.OK);
				msg.setResponseMessage(Constants.USER_FOUND_SUCCESSFULLY);
				msg.setData(usserMapperImpl.userToUserResponseDto(user));
				response.add(msg);
			}
			return response;
		} catch (Exception e) {
			response.get(0).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.get(0).setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public Message<UserResponseDto> getUserByEmail(String email) {
		Message<UserResponseDto> message = new Message<UserResponseDto>();
		User user = userRepository.findByEmail(email);
		try {
			if (user == null ||!user.isActive()) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.USER_FOUND_SUCCESSFULLY);
			message.setData(usserMapperImpl.userToUserResponseDto(user));
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<UserResponseDto> sendOTP(String email) {
		Message<UserResponseDto> message = new Message<UserResponseDto>();
		User user = userRepository.findByEmail(email);
		try {
			if (user == null ||!user.isActive()) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
			String otp = otpService.generateOTP();
			Properties props = new Properties();

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sender, password);
				}
			});
			MimeMessage mailMessage = new MimeMessage(session);

			MimeMessageHelper helper = new MimeMessageHelper(mailMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("request1", otp);
			model.put("user", user);
			Template t = config.getTemplate("Otp.html");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//			 SimpleMailMessage mailMessage = new SimpleMailMessage();
	            helper.setFrom(sender);
	            helper.setTo(user.getEmail());
	            helper.setSubject("Forgot Password!!");
	            helper.setText(html,true);
			 
			user.setOtp(otp);
			emailService.sendEmail(mailMessage);
			userRepository.save(user);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.USER_FOUND_SUCCESSFULLY);
			message.setData(usserMapperImpl.userToUserResponseDto(user));
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<UserResponseDto> verifyOTP(String email, String otp) {
		Message<UserResponseDto> message = new Message<UserResponseDto>();
		User user = userRepository.findByEmail(email);
		try {
			if (user == null ||!user.isActive()) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
			if (user.getOtp().equals(otp)) {
				user.setOtp(null);
				user.setVerified(true);
				user.setActive(true);
				userRepository.save(user);
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage(Constants.USER_FOUND_SUCCESSFULLY);
				message.setData(usserMapperImpl.userToUserResponseDto(user));
				return message;
			}
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setResponseMessage(Constants.INVALID_OTP);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<UserResponseDto> resetPassword(ChangePasswordDto changePasswordDto) {
    Message<UserResponseDto> message = new Message<UserResponseDto>();
		User user = userRepository.findByEmail(changePasswordDto.getEmail());
		try {
			if (user == null ||!user.isActive()) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
			if (!user.getOtp().equals(changePasswordDto.getOtp())) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.INVALID_OTP);
				return message;
			}
			if (!user.getPassword().equals(changePasswordDto.getConfirmPassword())) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.PASSWORD_NOT_MATCH);
				return message;
			}
			user.setPassword(changePasswordDto.getConfirmPassword());
			userRepository.save(user);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.USER_FOUND_SUCCESSFULLY);
			message.setData(usserMapperImpl.userToUserResponseDto(user));
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
 	}

	@Override
	public Message<UserLoginResponseDto> loginUser(LoginDto userDto) {
		Message<UserLoginResponseDto> message = new Message<UserLoginResponseDto>();
		User user = userRepository.findByEmail(userDto.getEmail());
		try {
			if (user == null ||!user.isActive() ) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				return message;
			}
			if (user.getPassword().equals(userDto.getPassword())) {
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage(Constants.USER_FOUND_SUCCESSFULLY);
				message.setData(usserMapperImpl.useToUserLoginResponseDto(user));
				return message;
			}
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setResponseMessage(Constants.INVALID_PASSWORD);
			return message;
		} catch (Exception e) {
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

}
