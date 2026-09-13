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

import com.matheusmarqs1.inventory_management_api.entities.Warehouse;
import com.matheusmarqs1.inventory_management_api.services.WarehouseService;

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
	public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse){
		Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdWarehouse.getId()).toUri();
		return ResponseEntity.created(uri).body(createdWarehouse);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse){
		Warehouse updatedWarehouse = warehouseService.updateWarehouse(id, warehouse);
		return ResponseEntity.ok().body(updatedWarehouse);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id){
		warehouseService.deleteWarehouse(id);
		return ResponseEntity.noContent().build();
	}
}
