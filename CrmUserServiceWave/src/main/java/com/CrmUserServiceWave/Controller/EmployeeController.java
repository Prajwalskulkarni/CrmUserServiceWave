 package com.CrmUserServiceWave.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmUserServiceWave.Dto.EmployeeDto;
import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Service.EmployeeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Employee")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	@PostMapping("/registerEmployee")
    public ResponseEntity<Message<EmployeeDto>> registerEmployee(@RequestBody EmployeeDto request) {
        Message<EmployeeDto> response = employeeService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<Message<EmployeeDto>> updateEmployee(@RequestBody EmployeeDto request) {
        Message<EmployeeDto> response = employeeService.updateEmployee(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getByEmployeeId")
    public ResponseEntity<Message<EmployeeDto>> getByEmployeeId(@RequestParam String employeeId) {
        Message<EmployeeDto> response = employeeService.getByemployeeId(employeeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<Message<EmployeeDto>>> getAllEmployee() {
        List<Message<EmployeeDto>> response = employeeService.getAllEmployee();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<Message<EmployeeDto>> deleteEmployee(@RequestParam String employeeId) {

        Message<EmployeeDto> response = employeeService.deleteEmployee(employeeId);

        return ResponseEntity.ok(response);
    }

}
