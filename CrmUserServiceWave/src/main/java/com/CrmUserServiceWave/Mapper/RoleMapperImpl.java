package com.CrmUserServiceWave.Mapper;

import org.springframework.stereotype.Component;

import com.CrmUserServiceWave.Dto.RoleRequest;
import com.CrmUserServiceWave.Model.Roll;
@Component
public class RoleMapperImpl implements RoleMapper {

	@Override
	public Roll roleDtoToRole(RoleRequest rolesDto) {
		return new Roll().setName(rolesDto.getName());
	}

	@Override
	public RoleRequest roleToRoleDto(Roll role) {
		return new RoleRequest().setName(role.getName()).setId(role.getId());
	}

}
