package com.CrmUserServiceWave.Mapper;

import org.springframework.stereotype.Component;

import com.CrmUserServiceWave.Dto.EmployeeDto;
import com.CrmUserServiceWave.Model.Employee;

@Component
public interface EmployeeMapper {
	public Employee employeeDtoToEmployee(EmployeeDto dto);
	public EmployeeDto employeeToEmployeeDto(Employee emp);

}
