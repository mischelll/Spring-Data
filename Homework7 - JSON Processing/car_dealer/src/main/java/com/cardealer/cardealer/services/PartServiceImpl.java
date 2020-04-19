package com.cardealer.cardealer.services;

import com.cardealer.cardealer.domain.dtos.PartsSeedDto;
import com.cardealer.cardealer.domain.entities.Part;
import com.cardealer.cardealer.domain.entities.PartSupplier;
import com.cardealer.cardealer.repositories.PartRepository;
import com.cardealer.cardealer.services.interfaces.PartService;
import com.cardealer.cardealer.services.interfaces.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PartServiceImpl  implements PartService {
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;
    private final SupplierService supplierService;

    public PartServiceImpl(ModelMapper modelMapper, PartRepository partRepository,  SupplierService supplierService) {
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts(PartsSeedDto[] partsSeedDtos) {
        Arrays.stream(partsSeedDtos).filter(partsSeedDto -> {
            Part byName = this.partRepository.findByName(partsSeedDto.getName());
            if (byName == null){
                return true;
            }
            return false;
        }).forEach(partsSeedDto -> {
            Part part = this.modelMapper.map(partsSeedDto, Part.class);
            part.setPartSupplier(setRandomSupplier());

            this.partRepository.saveAndFlush(part);
        });

    }

    @Override
    public List<Part> generateRandomParts() {
        Random random = new Random();
        int randomCounter = (int)(Math.random() * ((20 - 10) + 1))+10;
        List<Part> parts = new ArrayList<>();

        for (int i = 0; i < randomCounter; i++) {
            long randomId = random.nextInt((int) this.partRepository.count()) + 1;
            parts.add(this.partRepository.getOne(randomId));
        }

        return parts;

    }

    private PartSupplier setRandomSupplier() {
        return this.supplierService.getRandomSupplier();
    }
}
