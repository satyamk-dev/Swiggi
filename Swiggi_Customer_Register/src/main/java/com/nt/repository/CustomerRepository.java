package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.CustomerRegister;

public interface CustomerRepository extends JpaRepository<CustomerRegister, Long>{

}
