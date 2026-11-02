package com.CrmUserServiceWave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CrmUserServiceWave.Model.Roll;

@Repository
public interface RoleRepository extends JpaRepository<Roll, Integer> {

	Roll findByName(String name);

}
