package com.cardealer.cardealer.repositories;

import com.cardealer.cardealer.domain.dtos.LocalSupplierDto;
import com.cardealer.cardealer.domain.entities.PartSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<PartSupplier,Long> {
    PartSupplier findByName(String name);

    @Query("from PartSupplier as s " +
            " join Part as p on s.id = p.partSupplier.id where s.importer = false group by s.id  ")
    List<PartSupplier> getAllLocalSuppliers();


}
