package com.CrmUserServiceWave.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class RoleRequest {
	private int id;
	private String name;	


}
