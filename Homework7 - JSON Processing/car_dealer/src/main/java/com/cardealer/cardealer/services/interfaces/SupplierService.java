package com.cardealer.cardealer.services.interfaces;

import com.cardealer.cardealer.domain.dtos.LocalSupplierDto;
import com.cardealer.cardealer.domain.dtos.SupplierSeedDto;
import com.cardealer.cardealer.domain.entities.PartSupplier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {
    void seedSuppliers(SupplierSeedDto[] supplierSeedDtos);

    PartSupplier getRandomSupplier();

    List<LocalSupplierDto> localSuppliers();
}
