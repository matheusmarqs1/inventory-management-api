package com.matheusmarqs1.inventory_management_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusmarqs1.inventory_management_api.entities.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
	
	Optional<Warehouse> findByCode(String code);
	List<Warehouse> findByIsActiveTrue();
	List<Warehouse> findByIsActiveFalse();
	
}
