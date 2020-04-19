package com.cardealer.cardealer.repositories;

import com.cardealer.cardealer.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
    Part findByName(String name);
}
