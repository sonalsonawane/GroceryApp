package com.grocery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.entities.GroceryItem;
import com.grocery.entities.Order;
import com.grocery.entities.OrderItem;
import com.grocery.repositories.GroceryItemRepository;
import com.grocery.repositories.OrderRepository;
import com.grocery.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
    private GroceryItemRepository groceryItemRepository;

	@Override
    public Order placeOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            GroceryItem groceryItem = groceryItemRepository.findById(item.getGroceryItem().getId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            if (groceryItem.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for item: " + groceryItem.getName());
            }

            groceryItem.setQuantity(groceryItem.getQuantity() - item.getQuantity());
            groceryItemRepository.save(groceryItem);
        }
        return orderRepository.save(order);
    }
}
