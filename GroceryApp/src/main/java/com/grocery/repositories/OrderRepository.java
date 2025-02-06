package com.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
}

