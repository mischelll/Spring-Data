package com.cardealer.cardealer.services;

import com.cardealer.cardealer.domain.dtos.LocalSupplierDto;
import com.cardealer.cardealer.domain.dtos.SupplierSeedDto;
import com.cardealer.cardealer.domain.entities.PartSupplier;
import com.cardealer.cardealer.repositories.SupplierRepository;
import com.cardealer.cardealer.services.interfaces.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service

public class SupplierServiceImpl implements SupplierService {
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(ModelMapper modelMapper, SupplierRepository supplierRepository) {
        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedSuppliers(SupplierSeedDto[] supplierSeedDtos) {
        Arrays.stream(supplierSeedDtos).filter(supplierSeedDto -> {
            PartSupplier byName = this.supplierRepository.findByName(supplierSeedDto.getName());
            if (byName == null) {
                return true;
            }

            return false;
        }).forEach(supplierSeedDto -> {
            PartSupplier supplier = this.modelMapper.map(supplierSeedDto, PartSupplier.class);

            this.supplierRepository.saveAndFlush(supplier);
        });
    }

    @Override
    public PartSupplier getRandomSupplier() {
        Random random = new Random();
        long number = this.supplierRepository.count();
        int i = random.nextInt((int) number) + 1;

        return this.supplierRepository.getOne((long) i);

    }

    @Override
    public List<LocalSupplierDto> localSuppliers() {
        List<PartSupplier> allLocalSuppliers = this.supplierRepository.getAllLocalSuppliers();

        List<LocalSupplierDto> supplierDtoList = new ArrayList<>();
        allLocalSuppliers.forEach(partSupplier -> {
            LocalSupplierDto localSupplierDto = this.modelMapper.map(partSupplier, LocalSupplierDto.class);
            localSupplierDto.setPartsCount(partSupplier.getParts().size());
            supplierDtoList.add(localSupplierDto);
        });
        return supplierDtoList;
    }
}
