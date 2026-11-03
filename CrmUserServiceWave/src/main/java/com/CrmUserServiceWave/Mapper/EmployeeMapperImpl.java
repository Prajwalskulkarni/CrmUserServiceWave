package com.CrmUserServiceWave.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.CrmUserServiceWave.Dto.EmployeeDto;
import com.CrmUserServiceWave.Model.Employee;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {

	@Override
	public Employee employeeDtoToEmployee(EmployeeDto dto) {
		    Employee emp = new Employee();
		    emp.setEmployeeName(dto.getEmployeeName());
		    emp.setEmployeeId(dto.getEmployeeId());
		    emp.setGender(dto.getGender());
		    emp.setEmployeeStatus(dto.getEmployeeStatus());
		    emp.setDesignation(dto.getDesignation());
		    emp.setDepartment(dto.getDepartment());
		    emp.setDateOfJoining(dto.getDateOfJoining());
		    emp.setDateOfLiving(dto.getDateOfLiving());
		    emp.setContactNumber(dto.getContactNumber());
		    emp.setEmail(dto.getEmail());
		    emp.setDateOfBirth(dto.getDateOfBirth());
		    emp.setEmployeeSalary(dto.getEmployeeSalary());
		    emp.setBloodGroup(dto.getBloodGroup());
		    emp.setTeamName(dto.getTeamName());
		    emp.setCompanyName(dto.getCompanyName());
		    emp.setEmployementType(dto.getEmployementType());
		    return emp;
		
	}

	@Override
	public EmployeeDto employeeToEmployeeDto(Employee emp) {
		 EmployeeDto dto = new EmployeeDto();

		    dto.setEId(emp.getEId());
		    dto.setEmployeeName(emp.getEmployeeName());
		    dto.setEmployeeId(emp.getEmployeeId());
		    dto.setGender(emp.getGender());
		    dto.setEmployeeStatus(emp.getEmployeeStatus());
		    dto.setDesignation(emp.getDesignation());
		    dto.setDepartment(emp.getDepartment());
		    dto.setDateOfJoining(emp.getDateOfJoining());
		    dto.setDateOfLiving(emp.getDateOfLiving());
		    dto.setContactNumber(emp.getContactNumber());
		    dto.setEmail(emp.getEmail());
		    dto.setDateOfBirth(emp.getDateOfBirth());
		    dto.setEmployeeSalary(emp.getEmployeeSalary());
		    dto.setBloodGroup(emp.getBloodGroup());
		    dto.setTeamName(emp.getTeamName());
		    dto.setCompanyName(emp.getCompanyName());
		    dto.setEmployementType(emp.getEmployementType());
		    return dto;
	}
	
	private String formatDate(String date) {
	    if (date == null || date.trim().isEmpty()) {
	        return null;
	    }

	    DateTimeFormatter inputFormatter =
	            DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    DateTimeFormatter outputFormatter =
	            DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

	    LocalDate localDate =
	            LocalDate.parse(date, inputFormatter);

	    return localDate.format(outputFormatter);
	}

}
