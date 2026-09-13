package com.matheusmarqs1.inventory_management_api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matheusmarqs1.inventory_management_api.dtos.WarehouseRequestDTO;
import com.matheusmarqs1.inventory_management_api.dtos.WarehouseResponseDTO;
import com.matheusmarqs1.inventory_management_api.entities.Warehouse;
import com.matheusmarqs1.inventory_management_api.services.WarehouseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/warehouses")
public class WarehouseController {
	
	private final WarehouseService warehouseService;
	
	public WarehouseController(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}
	
	@GetMapping
	public ResponseEntity<List<Warehouse>> getAllWarehouses(@RequestParam(required = false) Boolean isActive){
		if(isActive == null) {
			return ResponseEntity.ok().body(warehouseService.getAllWarehouses());
		}
		return ResponseEntity.ok().body(warehouseService.getWarehousesByStatus(isActive));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id){
		Warehouse warehouse = warehouseService.getWarehouseById(id);
		return ResponseEntity.ok().body(warehouse);
	}
	
	@PostMapping
	public ResponseEntity<WarehouseResponseDTO> createWarehouse(@RequestBody @Valid WarehouseRequestDTO warehouseRequestDTO){
		WarehouseResponseDTO warehouseResponseDTO = warehouseService.createWarehouse(warehouseRequestDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(warehouseResponseDTO.id()).toUri();
		return ResponseEntity.created(uri).body(warehouseResponseDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<WarehouseResponseDTO> updateWarehouse(@PathVariable Long id, @RequestBody @Valid WarehouseRequestDTO warehouseRequestDTO){
		WarehouseResponseDTO warehouseResponseDTO = warehouseService.updateWarehouse(id, warehouseRequestDTO);
		return ResponseEntity.ok().body(warehouseResponseDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id){
		warehouseService.deleteWarehouse(id);
		return ResponseEntity.noContent().build();
	}
}
