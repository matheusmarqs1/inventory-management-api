package com.matheusmarqs1.inventory_management_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matheusmarqs1.inventory_management_api.entities.Warehouse;
import com.matheusmarqs1.inventory_management_api.exceptions.BusinessException;
import com.matheusmarqs1.inventory_management_api.exceptions.ResourceNotFoundException;
import com.matheusmarqs1.inventory_management_api.repositories.WarehouseRepository;

@Service
public class WarehouseService {
	
	private final WarehouseRepository warehouseRepository;
	
	public WarehouseService(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}
	
	public List<Warehouse> getAllWarehouses() {
		return warehouseRepository.findAll();
	}
	
	public Warehouse getWarehouseById(Long id) {
		
		return warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + id + " not found!"));
	}
	
	public List<Warehouse> getWarehousesByStatus(Boolean isActive){
		return isActive 
				? warehouseRepository.findByIsActiveTrue()
				: warehouseRepository.findByIsActiveFalse();
				
	}
	
	public Warehouse createWarehouse(Warehouse warehouse) {
		if(warehouseRepository.findByCode(warehouse.getCode()).isPresent()) {
			throw new BusinessException("Warehouse code already registered");
		}
		return warehouseRepository.save(warehouse);
	}	
	
	public Warehouse updateWarehouse(Long id, Warehouse warehouseReq) {
		Warehouse warehousePersisted = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + id + " not found!"));
		
		warehouseRepository.findByCode(warehouseReq.getCode()).ifPresent(existingWarehouse -> {
			if(!existingWarehouse.getId().equals(id)) {
				throw new BusinessException("Warehouse code already registered");
			}
		});
		
		warehousePersisted.setCode(warehouseReq.getCode());
		warehousePersisted.setName(warehouseReq.getName());
		warehousePersisted.setLocation(warehouseReq.getLocation());
		warehousePersisted.setIsActive(warehouseReq.getIsActive());
		warehousePersisted.setManagerName(warehouseReq.getManagerName());
		
		return warehouseRepository.save(warehousePersisted);
	}
	
	public void deleteWarehouse(Long id) {
		
		if(!warehouseRepository.existsById(id)) {
			throw new ResourceNotFoundException("Warehouse with ID: " + id + " not found!");
		}
		warehouseRepository.deleteById(id);
	}
}
