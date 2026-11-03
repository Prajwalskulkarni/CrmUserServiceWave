package com.CrmUserServiceWave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CrmUserServiceWave.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
User findByEmail(String email);

}
