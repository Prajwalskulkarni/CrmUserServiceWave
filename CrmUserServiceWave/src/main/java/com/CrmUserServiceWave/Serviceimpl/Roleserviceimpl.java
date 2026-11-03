package com.CrmUserServiceWave.Serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Dto.RoleRequest;
import com.CrmUserServiceWave.Mapper.RoleMapperImpl;
import com.CrmUserServiceWave.Model.Roll;
import com.CrmUserServiceWave.Repository.RoleRepository;
import com.CrmUserServiceWave.Service.RoleService;
import com.CrmUserServiceWave.Util.Constants;
@Service
public class Roleserviceimpl implements RoleService {
	private final RoleRepository roleRepository;
	private final RoleMapperImpl roleMapperImpl;
	
	public Roleserviceimpl(RoleRepository roleRepository, RoleMapperImpl roleMapperImpl) {
		this.roleRepository = roleRepository;
		this.roleMapperImpl = roleMapperImpl;
	}
	
	

	@Override
	public Message<RoleRequest> createRole(RoleRequest rolesDto) {
		Message<RoleRequest> response = new Message<RoleRequest>();
		Roll role=roleRepository.findByName(rolesDto.getName());
		try {
			if(!(role==null)) {
				response.setStatus(HttpStatus.CONFLICT);
				response.setResponseMessage(Constants.ROLE_ALREADY_EXISTS);
				return response;
			}
			role=roleMapperImpl.roleDtoToRole(rolesDto);
			role=roleRepository.save(role);
			response.setStatus(HttpStatus.CREATED);
			response.setResponseMessage(Constants.ROLE_CREATED_SUCCESSFULLY);
			response.setData(roleMapperImpl.roleToRoleDto(role));
			return response;	
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			System.out.println(e.getMessage());
		return response;
		}
	}

	@Override
	public Message<RoleRequest> updateRole(RoleRequest rolesDto) {
		Message<RoleRequest> response = new Message<RoleRequest>();
		Roll role=roleRepository.getById(rolesDto.getId());
		try {
			if(role!=null) {
				role.setName(rolesDto.getName());
				role=roleRepository.save(role);
				response.setStatus(HttpStatus.OK);
				response.setResponseMessage(Constants.ROLE_UPDATED_SUCCESSFULLY);
				response.setData(roleMapperImpl.roleToRoleDto(role));
				return response;
			}
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setResponseMessage(Constants.ROLE_NOT_FOUND);
		return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
		return response;
		}
	}

	@Override
	public Message<RoleRequest> deleteRole(String name) {
		Message<RoleRequest> response = new Message<RoleRequest>();
		Roll role=roleRepository.findByName(name);
		try {
			if(role!=null) {
				roleRepository.delete(role);
				response.setStatus(HttpStatus.OK);
				response.setResponseMessage(Constants.ROLE_DELETED_SUCCESSFULLY);
				return response;
			}
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setResponseMessage(Constants.ROLE_NOT_FOUND);
		return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
		return response;
		}
	}

	@Override
	public Message<RoleRequest> getRole(String name) {
		Message<RoleRequest> response = new Message<RoleRequest>();
		Roll role=roleRepository.findByName(name);
		if(role!=null) {
			response.setStatus(HttpStatus.OK);
			response.setResponseMessage(Constants.ROLE_FOUND_SUCCESSFULLY);
			response.setData(roleMapperImpl.roleToRoleDto(role));
			return response;
		}
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setResponseMessage(Constants.ROLE_NOT_FOUND);
		return null;
	}

	@Override
	public List<Message<RoleRequest>> getAllRoles() {
		List<Message<RoleRequest>>response=new ArrayList<>();
		try {
			List<Roll> roles=roleRepository.findAll();
			for(Roll role:roles) {
				Message<RoleRequest> msg=new Message<RoleRequest>();
				msg.setStatus(HttpStatus.OK);
				msg.setResponseMessage(Constants.ROLE_FOUND_SUCCESSFULLY);
				msg.setData(roleMapperImpl.roleToRoleDto(role));
				response.add(msg);
			}
			return response;
		} catch (Exception e) {
			response.get(0).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.get(0).setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
