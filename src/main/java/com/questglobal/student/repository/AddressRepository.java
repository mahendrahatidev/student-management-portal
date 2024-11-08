package com.questglobal.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questglobal.student.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	
}
