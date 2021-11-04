package com.kh.spring.order;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kh.spring.food.Food;

@Service
public class OrderService {
	
	public Map<String, Object> order(List<String> foods){
	
		Map<String, Object> commandMap = new HashMap<String, Object>();
	
		//List<Food> orders = new ArrayList<>();
		List<Food> orders = foods.stream().map(e -> Food.createFood(e)).collect(Collectors.toList());
		int payPrice = orders.stream().map(e -> e.getPrice()).reduce((a,b) -> a+b).orElse(0);
		
		return Map.of("orders", orders, "payPrice", payPrice);
	}

}
