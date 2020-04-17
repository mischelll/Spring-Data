package com.automapping.exe.automappingexe.services.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    void createOrder(Long userId, Long gameId);


}
