package com.CrmUserServiceWave.Mapper;

import org.springframework.stereotype.Component;

import com.CrmUserServiceWave.Dto.RoleRequest;
import com.CrmUserServiceWave.Model.Roll;

@Component
public interface RoleMapper {
	public RoleRequest roleDtoToRole(RoleRequest rolesDto);
	public RoleRequest roleToRoleDto(Roll role);

}
