package com.grocery.service;

import java.util.List;

import com.grocery.entities.GroceryItem;

public interface GroceryItemService {

	GroceryItem addItem(GroceryItem item);

	List<GroceryItem> getAllItems();

	GroceryItem updateItem(Long id, GroceryItem updatedItem);

	void deleteItem(Long id);

}
