package com.automapping.exe.automappingexe.services.impls;

import com.automapping.exe.automappingexe.domain.entities.Order;
import com.automapping.exe.automappingexe.repositories.OrderRepository;
import com.automapping.exe.automappingexe.services.interfaces.GameService;
import com.automapping.exe.automappingexe.services.interfaces.OrderService;
import com.automapping.exe.automappingexe.services.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final GameService gameService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserService userService, GameService gameService, OrderRepository orderRepository) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderRepository = orderRepository;

    }

    @Override
    public void createOrder(Long userId, Long gameId) {
        if (!this.userService.toCreateOrder()){
            return;
        }
        Order order = new Order();
        order.setUser(this.userService.getUserById(userId));
        order.getGames().add(this.gameService.getGameById(gameId));
        this.orderRepository.saveAndFlush(order);
    }


}
