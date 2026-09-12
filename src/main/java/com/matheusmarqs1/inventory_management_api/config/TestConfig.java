package com.matheusmarqs1.inventory_management_api.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.matheusmarqs1.inventory_management_api.entities.Warehouse;
import com.matheusmarqs1.inventory_management_api.repositories.WarehouseRepository;

@Configuration()
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	private final WarehouseRepository warehouseRepository;
	
	public TestConfig(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Warehouse w1 = new Warehouse(null, "WH-CEN-01", "Centro de Distribuição Central", "Goiânia - GO", "Carlos Eduardo", true);
		Warehouse w2 = new Warehouse(null, "WH-SUL-02", "Armazém Logístico Sul", "Aparecida de Goiânia - GO", "Fernanda Tavares", true);
		Warehouse w3 = new Warehouse(null, "WH-AUX-03", "Depósito Auxiliar Desativado", "Anápolis - GO", "Roberto Alves", false);
		
		warehouseRepository.saveAll(Arrays.asList(w1, w2, w3));
	}

}
