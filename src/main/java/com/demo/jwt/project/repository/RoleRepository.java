package com.demo.jwt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.jwt.project.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
