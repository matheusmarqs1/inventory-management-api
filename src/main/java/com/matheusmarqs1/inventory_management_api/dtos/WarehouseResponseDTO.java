package com.matheusmarqs1.inventory_management_api.dtos;

import com.matheusmarqs1.inventory_management_api.entities.Warehouse;

public record WarehouseResponseDTO(
		
		Long id,
		String code,
		String name,
		String location,
		String managerName,
		Boolean isActive
		) {
		
			public static WarehouseResponseDTO fromEntity(Warehouse warehouse) {
				return new WarehouseResponseDTO(warehouse.getId(), 
						warehouse.getCode(), 
						warehouse.getName(), 
						warehouse.getLocation(), 
						warehouse.getManagerName(), 
						warehouse.getIsActive());
			}
}
