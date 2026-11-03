package com.CrmUserServiceWave.Dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class EmployeeDto {
	private int eId;
	private String employeeName;
	private String employeeId;
	private String gender;
	private String employeeStatus;
	private String designation;
	private String department;
	private String dateOfJoining;
	private String dateOfLiving;
	private String contactNumber;
	private String email;
	private String dateOfBirth;
	private double employeeSalary;
	private String bloodGroup;
	private String teamName;
	private String companyName;
	public String employementType;	
}
