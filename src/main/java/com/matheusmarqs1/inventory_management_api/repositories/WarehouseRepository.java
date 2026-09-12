package com.matheusmarqs1.inventory_management_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusmarqs1.inventory_management_api.entities.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

}
