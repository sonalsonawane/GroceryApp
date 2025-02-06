package com.grocery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.entities.GroceryItem;
import com.grocery.repositories.GroceryItemRepository;
import com.grocery.service.GroceryItemService;

@Service
public class GroceryItemServiceImpl implements GroceryItemService{
	
	@Autowired
    private GroceryItemRepository groceryItemRepository;

	@Override
    public GroceryItem addItem(GroceryItem item) {
        return groceryItemRepository.save(item);
    }
	
	@Override
    public List<GroceryItem> getAllItems() {
        return groceryItemRepository.findAll();
    }

	@Override
    public GroceryItem updateItem(Long id, GroceryItem updatedItem) {
        GroceryItem item = groceryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setName(updatedItem.getName());
        item.setPrice(updatedItem.getPrice());
        item.setQuantity(updatedItem.getQuantity());

        return groceryItemRepository.save(item);
    }

	@Override
    public void deleteItem(Long id) {
        groceryItemRepository.deleteById(id);
    }
}
