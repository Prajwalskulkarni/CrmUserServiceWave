package com.CrmUserServiceWave.Mapper;

import com.CrmUserServiceWave.Dto.RoleRequest;
import com.CrmUserServiceWave.Model.Roll;

public class RoleMapperImpl implements RoleMapper {

	@Override
	public RoleRequest roleDtoToRole(RoleRequest rolesDto) {
		return new RoleRequest().setName(rolesDto.getName());
	}

	@Override
	public RoleRequest roleToRoleDto(Roll role) {
		return new RoleRequest().setName(role.getName());
	}

}
