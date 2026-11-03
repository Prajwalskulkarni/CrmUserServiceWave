package com.CrmUserServiceWave.Mapper;

import com.CrmUserServiceWave.Dto.RoleRequest;
import com.CrmUserServiceWave.Model.Roll;

public interface RoleMapper {
	public Roll roleDtoToRole(RoleRequest rolesDto);
	public RoleRequest roleToRoleDto(Roll role);

}
