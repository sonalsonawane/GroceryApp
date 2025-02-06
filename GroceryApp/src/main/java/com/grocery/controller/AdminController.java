package com.grocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.entities.GroceryItem;
import com.grocery.service.GroceryItemService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
	@Autowired
    private GroceryItemService groceryItemService;

    @PostMapping("/addgrocery")
    public ResponseEntity<GroceryItem> addItem(@RequestBody GroceryItem item) {
        return ResponseEntity.ok(groceryItemService.addItem(item));
    }

    @GetMapping("/getAllItems")
    public ResponseEntity<List<GroceryItem>> viewAllItems() {
        return ResponseEntity.ok(groceryItemService.getAllItems());
    }

    @PostMapping("/updateItem")
    public ResponseEntity<GroceryItem> updateItem(@RequestParam Long id, @RequestBody GroceryItem item) {
        return ResponseEntity.ok(groceryItemService.updateItem(id, item));
    }

    @GetMapping("/deleteItem")
    public ResponseEntity<Void> deleteItem(@RequestParam Long id) {
        groceryItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}