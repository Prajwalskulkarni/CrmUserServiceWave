package com.CrmUserServiceWave.Serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.CrmUserServiceWave.Dto.EmployeeDto;
import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Mapper.EmployeeMapper;
import com.CrmUserServiceWave.Model.Employee;
import com.CrmUserServiceWave.Repository.EmployeeRepository;
import com.CrmUserServiceWave.Service.EmployeeService;
import com.CrmUserServiceWave.Util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService{
	private final EmployeeMapper employeeMapperImpl;
	private final EmployeeRepository employeeRepository;

	@Override
	public Message<EmployeeDto> registerUser(EmployeeDto request) {
		Message<EmployeeDto> response = new Message<>();
		
		try {
			Optional<Employee> existingEmployee = employeeRepository.findByEmployeeId(request.getEmployeeId());

            if (existingEmployee.isPresent()) {

                response.setStatus(HttpStatus.CONFLICT);
                response.setResponseMessage(Constants.EMPLOYEE_ALREADY_EXISTS);
                return response;
            }

            Employee employee = employeeMapperImpl.employeeDtoToEmployee(request);

            Employee savedEmployee = employeeRepository.save(employee);

            response.setStatus(HttpStatus.CREATED);
            response.setResponseMessage(Constants.EMPLOYEE_REGISTERED_SUCCESSFULLY);
            response.setData(employeeMapperImpl.employeeToEmployeeDto(savedEmployee));

            return response;

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(e.getMessage());
            return response;
        }

	}

	@Override
	public Message<EmployeeDto> updateEmployee(EmployeeDto request) {
		Message<EmployeeDto> response = new Message<>();

        try {

            Optional<Employee> existingEmployee = employeeRepository.findById(request.getEId());

            if (existingEmployee.isEmpty()) {

                response.setStatus(HttpStatus.NOT_FOUND);
                response.setResponseMessage(Constants.EMPLOYEE_NOT_FOUND);
                return response;
            }

            Employee employee = employeeMapperImpl.employeeDtoToEmployee(request);

            employee.setEId(request.getEId());
            employee.setEmployeeName(request.getEmployeeName());
            employee.setEmployeeId(request.getEmployeeId());
            employee.setGender(request.getGender());
            employee.setEmployeeStatus(request.getEmployeeStatus());
            employee.setDesignation(request.getDesignation());
            employee.setDepartment(request.getDepartment());
            employee.setDateOfJoining(request.getDateOfJoining());
            employee.setContactNumber(request.getContactNumber());
            employee.setEmail(request.getEmail());
            employee.setDateOfBirth(request.getDateOfBirth());
            employee.setEmployeeSalary(request.getEmployeeSalary());
            employee.setBloodGroup(request.getBloodGroup());
            employee.setTeamName(request.getTeamName());
            employee.setCompanyName(request.getCompanyName());
            employee.setEmployementType(request.getEmployementType());

            Employee updatedEmployee = employeeRepository.save(employee);

            response.setStatus(HttpStatus.OK);
            response.setResponseMessage(Constants.EMPLOYEE_UPDATED_SUCCESSFULLY);
            response.setData(employeeMapperImpl.employeeToEmployeeDto(updatedEmployee));

            return response;

        } catch (Exception e) {

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(e.getMessage());
            return response;

        }
	}

	@Override
	public Message<EmployeeDto> getByemployeeId(String employeeId) {
		Message<EmployeeDto> response = new Message<>();

        try {

            Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);

            if (employee == null) {

                response.setStatus(HttpStatus.NOT_FOUND);
                response.setResponseMessage(Constants.EMPLOYEE_NOT_FOUND);
                return response;
            }
            

            response.setStatus(HttpStatus.OK);
            response.setResponseMessage(Constants.EMPLOYEE_FOUND);
            response.setData(employeeMapperImpl.employeeToEmployeeDto(employee.get()));
            return response;

        } catch (Exception e) {

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setResponseMessage(e.getMessage());
            return response;
        }

	}

	@Override
	public List<Message<EmployeeDto>> getAllEmployee() {
	    List<Message<EmployeeDto>> message = new ArrayList<>();
	    try {
	        List<Employee> employees = employeeRepository.findAll();
	        for (Employee employee : employees) {
	            EmployeeDto dto = employeeMapperImpl.employeeToEmployeeDto(employee);
	            message.add(new Message<EmployeeDto>(HttpStatus.OK, Constants.EMPLOYEE_FOUND, dto));
	        }

	        if (employees.isEmpty()) {
	            message.add(new Message<EmployeeDto>(HttpStatus.NOT_FOUND, Constants.EMPLOYEE_NOT_FOUND,null));
	        }
	        log.info("All employee records fetched successfully");
	        return message;

	    } catch (Exception e) {

	        message.add(new Message<EmployeeDto>(HttpStatus.INTERNAL_SERVER_ERROR,Constants.SOMETHING_WENT_WRONG, null));
	        return message;
	    }
	}
	@Override
	public Message<EmployeeDto> deleteEmployee(String employeeId) {
		Message<EmployeeDto> response = new Message<>();
	    try {
	        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
	        
	        if (employee.isEmpty()) {
	            response.setStatus(HttpStatus.BAD_REQUEST);
	            response.setResponseMessage(Constants.EMPLOYEE_NOT_FOUND);
	            return response;
	        }

	        EmployeeDto dto = employeeMapperImpl.employeeToEmployeeDto(employee.get());
	        employeeRepository.deleteById(employee.get().getEId());

	        response.setStatus(HttpStatus.OK);
	        response.setResponseMessage(Constants.EMPLOYEE_DELETED);
	        response.setData(dto);

	        return response;

	    } catch (Exception e) {

	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setResponseMessage(e.getMessage());

	        return response;
	    }
	}

}
