package com.CrmUserServiceWave.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Dto.RoleRequest;

@Service
public interface RoleService {
	public Message<RoleRequest> createRole(RoleRequest rolesDto);
	public Message<RoleRequest> updateRole(RoleRequest rolesDto);
	public Message<RoleRequest> deleteRole(String name);
	public Message<RoleRequest> getRole(String name);
	public List<Message<RoleRequest>> getAllRoles();

}
