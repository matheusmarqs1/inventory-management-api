package com.matheusmarqs1.inventory_management_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WarehouseRequestDTO(
		
		@NotBlank(message = "Code is required")
		@Size(min = 2, max = 20, message = "Code must be between 2 and 20 characters")
		String code,
		
		@NotBlank(message = "Name is required")
		@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
		String name,
		
		@NotBlank(message = "Location is required")
		@Size(min = 2, max = 255, message = "Location must be between 2 and 255 characters")
		String location,
		
		@NotBlank(message = "Manager name is required")
		@Size(min = 2, max = 255, message = "Manager name must be between 2 and 255 characters")
		String managerName,
		
		@NotNull(message = "Activity status is required")
		Boolean isActive
		) {
	
	
	
}
