package com.cardealer.cardealer.services.interfaces;

import com.cardealer.cardealer.domain.dtos.SalesDiscountDto;
import com.cardealer.cardealer.domain.dtos.SalesSeedDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleService {
    void seedSales();

    List<SalesDiscountDto> getAllSalesDiscounts();
}
