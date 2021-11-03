package com.kh.spring.order;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.spring.food.Food;

@Controller
public class OrderController {
	
	@GetMapping("order/order-form")
	public void sendDataOrderForm(Model model, HttpSession session) {
		
		Food food = new Food();
		food.setName("피자");
		food.setPrice(30000);
		model.addAttribute("pizza", food);
		
		food.setName("햄버거");
		food.setPrice(5000);
		model.addAttribute("ham", food);
	
		
	}
	
	@PostMapping("order/receipt")
	public void sendReceipt() {
		
	}
	
}
