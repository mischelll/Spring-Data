package com.cardealer.cardealer.repositories;

import com.cardealer.cardealer.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
