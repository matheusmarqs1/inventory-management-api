package com.matheusmarqs1.inventory_management_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matheusmarqs1.inventory_management_api.dtos.WarehouseRequestDTO;
import com.matheusmarqs1.inventory_management_api.dtos.WarehouseResponseDTO;
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
	
	public WarehouseResponseDTO createWarehouse(WarehouseRequestDTO warehouseRequestDTO) {
		if(warehouseRepository.findByCode(warehouseRequestDTO.code()).isPresent()) {
			throw new BusinessException("Warehouse code already registered");
		}
		
		Warehouse warehouse = new Warehouse(null, 
				warehouseRequestDTO.code(),
				warehouseRequestDTO.name(),
				warehouseRequestDTO.location(),
				warehouseRequestDTO.managerName(),
				warehouseRequestDTO.isActive());
		
		Warehouse createdWarehouse = warehouseRepository.save(warehouse);
		
		return WarehouseResponseDTO.fromEntity(createdWarehouse);
	}	
	
	public WarehouseResponseDTO updateWarehouse(Long id, WarehouseRequestDTO warehouseRequestDTO) {
		Warehouse warehousePersisted = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + id + " not found!"));
		
		warehouseRepository.findByCode(warehouseRequestDTO.code()).ifPresent(existingWarehouse -> {
			if(!existingWarehouse.getId().equals(id)) {
				throw new BusinessException("Warehouse code already registered");
			}
		});
		
		warehousePersisted.setCode(warehouseRequestDTO.code());
		warehousePersisted.setName(warehouseRequestDTO.name());
		warehousePersisted.setLocation(warehouseRequestDTO.location());
		warehousePersisted.setIsActive(warehouseRequestDTO.isActive());
		warehousePersisted.setManagerName(warehouseRequestDTO.managerName());
		
		Warehouse updatedWarehouse = warehouseRepository.save(warehousePersisted);
		return WarehouseResponseDTO.fromEntity(updatedWarehouse);
	}
	
	public void deleteWarehouse(Long id) {
		
		if(!warehouseRepository.existsById(id)) {
			throw new ResourceNotFoundException("Warehouse with ID: " + id + " not found!");
		}
		warehouseRepository.deleteById(id);
	}
}
