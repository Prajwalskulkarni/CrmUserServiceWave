package com.CrmUserServiceWave.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrmUserServiceWave.Dto.Message;
import com.CrmUserServiceWave.Dto.RoleRequest;
import com.CrmUserServiceWave.Service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Role")
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;
	
	@PostMapping("/createRole")
	public ResponseEntity<Message<RoleRequest>> createRole(RoleRequest rolesDto) {
		Message<RoleRequest>response=roleService.createRole(rolesDto);
		return ResponseEntity.ok(response);
	}
	@PutMapping("/updateRole")
	public ResponseEntity<Message<RoleRequest>> updateRole(RoleRequest rolesDto) {
		Message<RoleRequest>response=roleService.updateRole(rolesDto);
		return ResponseEntity.ok(response);
	}
	@DeleteMapping("/deleteRole")
	public ResponseEntity<Message<RoleRequest>>DeleteRole(String name){
		Message<RoleRequest>response=roleService.deleteRole(name);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/getByName")
	public ResponseEntity<Message<RoleRequest>>getRoleByName(String name){
		Message<RoleRequest>response=roleService.getRole(name);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/GetAllRoles")
	public ResponseEntity<List<Message<RoleRequest>>>getAllRoles() {
		List<Message<RoleRequest>> response=roleService.getAllRoles();
		return ResponseEntity.ok(response);
	} 

}
