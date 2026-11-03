package com.CrmUserServiceWave.Service;

import java.util.List;

import com.CrmUserServiceWave.Dto.EmployeeDto;
import com.CrmUserServiceWave.Dto.Message;

public interface EmployeeService {
	
	public Message<EmployeeDto> registerUser(EmployeeDto request);

	public Message<EmployeeDto> updateEmployee(EmployeeDto request);

	public Message<EmployeeDto> getByemployeeId(String employeeId);

	public List<Message<EmployeeDto>> getAllEmployee();
	
   public Message<EmployeeDto> deleteEmployee(String employeeId);

}
