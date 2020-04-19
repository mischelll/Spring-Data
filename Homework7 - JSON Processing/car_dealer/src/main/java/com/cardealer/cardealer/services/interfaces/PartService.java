package com.cardealer.cardealer.services.interfaces;

import com.cardealer.cardealer.domain.dtos.PartsSeedDto;
import com.cardealer.cardealer.domain.entities.Part;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PartService {
    void seedParts(PartsSeedDto[] partsSeedDtos);

    List<Part> generateRandomParts();
}
