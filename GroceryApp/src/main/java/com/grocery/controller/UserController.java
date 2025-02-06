package com.grocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.entities.GroceryItem;
import com.grocery.entities.Order;
import com.grocery.service.GroceryItemService;
import com.grocery.service.OrderService;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
	
	@Autowired
    private GroceryItemService groceryItemService;
	
	@Autowired
    private OrderService orderService;
	
    @GetMapping("/viewGroceryItem")
    public ResponseEntity<List<GroceryItem>> viewItems() {
        return ResponseEntity.ok(groceryItemService.getAllItems());
    }
    
    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.placeOrder(order));
    }
}
