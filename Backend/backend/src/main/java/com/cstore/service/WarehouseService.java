package com.cstore.service;

import com.cstore.dto.WarehouseDTO;
import com.cstore.exception.NoSuchWarehouseException;
import com.cstore.model.warehouse.Warehouse;
import com.cstore.model.warehouse.WarehouseContact;
import com.cstore.repository.WarehouseContactRepository;
import com.cstore.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseContactRepository warehouseContactRepository;

    @Autowired
    public WarehouseService(WarehouseContactRepository warehouseContactRepository, WarehouseRepository warehouseRepository) {
        this.warehouseContactRepository = warehouseContactRepository;
        this.warehouseRepository = warehouseRepository;
    }

    private WarehouseDTO convert(Warehouse warehouse) {
        List<WarehouseContact> warehouseContacts = warehouseContactRepository.findByWarehouse(warehouse);

        WarehouseDTO warehouseDTO = new WarehouseDTO();
        List<Integer> telephoneNumbers = new ArrayList<>();

        warehouseDTO.setWarehouseId(warehouse.getWarehouseId());
        warehouseDTO.setStreetNumber(warehouse.getStreetNumber());
        warehouseDTO.setStreetName(warehouse.getStreetName());
        warehouseDTO.setCity(warehouse.getCity());
        warehouseDTO.setZipcode(warehouse.getZipcode());

        for (WarehouseContact warehouseContact : warehouseContacts) {
            telephoneNumbers.add(warehouseContact.getTelephoneNumber());
        }
        warehouseDTO.setTelephoneNumbers(telephoneNumbers);

        return warehouseDTO;
    }

    public WarehouseDTO getWarehouseById(Long warehouseId) {
        Optional<Warehouse> tempWarehouse = warehouseRepository.findById(warehouseId);

        if (tempWarehouse.isEmpty()) {
            throw new NoSuchWarehouseException("Warehouse with id " + warehouseId + " does not exist.");
        }
        Warehouse warehouse = tempWarehouse.get();

        return convert(warehouse);
    }

    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
